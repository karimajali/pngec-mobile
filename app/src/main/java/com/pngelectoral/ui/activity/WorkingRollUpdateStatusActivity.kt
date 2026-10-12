package com.pngelectoral.ui.activity

import ApiConstants
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityWorkingRollUpdateStatusBinding
import com.pngelectoral.databinding.DialogDuplicateVotersListBinding
import com.pngelectoral.databinding.DialogUpdateStatusBinding
import com.pngelectoral.databinding.ItemSyncVoterBinding
import com.pngelectoral.ui.adapter.GenericAdapter
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.viewModel.EditVoterDetailsViewModel
import com.pngelectoral.utility.CommonMethods
import com.pngelectoral.utility.CommonMethods.getDisplayDate
import com.pngelectoral.utility.CommonMethods.getIntegerVoterDetails
import com.pngelectoral.utility.CommonMethods.getVoterDetails
import com.pngelectoral.utility.CommonMethods.getVoterDetailsBoolean
import com.pngelectoral.utility.helper.DialogSpinner
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class WorkingRollUpdateStatusActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityWorkingRollUpdateStatusBinding? = null
    private var editVoterDetailsViewModel: EditVoterDetailsViewModel? = null
    var voterDetailsResponse: VotersList = VotersList()
    lateinit var loggedInDPOUserData: DPOUserData
    lateinit var dataBaseInstance: AppDatabase

    var regionId = 0
    var provinceId = 0
    var districtId = 0
    var llgId = 0
    var wardId = 0
    var locationId = 0
    var llgName = ""
    var wardName = ""
    private var initialWardName = ""
    private var lastDateOfEnrolment = ""
    var occupationId = 0
    var duplicateId: Long = 0
    var clanId = 0

    private var filterType = ""
    private var mkaDistrictArray = ArrayList<MKAGeoListData>()
    private var mkaLlgArray = ArrayList<MKAGeoListModel.GeoListData.LlG>()
    private var mkaWardArray = ArrayList<MKAGeoListModel.GeoListData.LlG.Ward>()
    private var mkaLocationArray = ArrayList<MKAGeoListModel.GeoListData.LlG.Ward.Location>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_working_roll_update_status
        )
        editVoterDetailsViewModel =
            ViewModelProvider(this).get(EditVoterDetailsViewModel::class.java)
        binding?.editVoterDetailsViewModel = editVoterDetailsViewModel
        job = Job()
        lastDateOfEnrolment = if (securedSharedPreferenceWrapper.getString(
                ApiConstants.ROLE,
                ""
            ) == ApiConstants.ROLE_DPO
        ) securedSharedPreferenceWrapper.getString(
            "LAST_DATE_OF_ENROLMENT",
            ApiConstants.LAST_DATE_OF_ENROLMENT_DEFAULT
        ) else securedSharedPreferenceWrapper.getString(
            "LAST_DATE_OF_ENROLMENT",
            ApiConstants.LAST_DATE_OF_ENROLMENT_DEFAULT_MKA
        )
        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )
        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase
        intent?.apply {
            filterType =
                getStringExtra(ApiConstants.FILTER_TYPE) ?: ApiConstants.PNGEC_GEO_STRUCTURE
            voterDetailsResponse = getSerializableExtra(ApiConstants.VOTER_DETAILS) as VotersList
        }
        binding?.tvVoterName?.text = CommonMethods.getVoterName(voterDetailsResponse)

        setVoterData(voterDetailsResponse)
        setFooterData()
        showUpdateStatusDialog()
    }

    private fun setFooterData() {

        val dpoName = securedSharedPreferenceWrapper.getString(
            ApiConstants.DPO_NAME,
            ""
        )
        val version = packageManager.getPackageInfo(packageName, 0).versionName

        val districtName = securedSharedPreferenceWrapper.getString(
            ApiConstants.DPO_GEO_DISTRICT,
            ""
        )
        val llgName = securedSharedPreferenceWrapper.getString(
            ApiConstants.DPO_WORKING_ROLL_LLG,
            securedSharedPreferenceWrapper.getString(
                ApiConstants.DPO_GEO_LLG,
                ""
            )
        )
        val wardName = securedSharedPreferenceWrapper.getString(
            ApiConstants.DPO_WORKING_ROLL_WARD,
            securedSharedPreferenceWrapper.getString(
                ApiConstants.DPO_GEO_WARD,
                ""
            )
        )
        val tvBottomDataTitle: String = if (wardName.isEmpty())
            "Version: $version | District: $districtName | DPO: $dpoName"
        else {
            val wardNameDisplay = "$districtName/$llgName/$wardName"
            "Version: $version | Ward: $wardNameDisplay | DPO: $dpoName"
        }
        binding?.tvBottomData?.text = tvBottomDataTitle
    }


    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
    }

    private fun setVoterData(voterDetailsResponse: VotersList) {

        if (voterDetailsResponse.electorProfileImage != null
            && voterDetailsResponse.electorProfileImage.isNotEmpty()
        ) {

            val image =
                File(voterDetailsResponse.electorProfileImage)

            if (image.exists()) {
                Glide.with(this).load(Uri.fromFile(image))
                    .into(binding?.ivVoterImage!!)
            } else if (voterDetailsResponse.electorProfileImageUrl != null
                && voterDetailsResponse.electorProfileImageUrl.isNotEmpty()
            ) {

                Glide.with(this)
                    .load(voterDetailsResponse.electorProfileImageUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding?.ivVoterImage?.setImageResource(R.drawable.e_user)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                    }
                    )
                    .into(binding?.ivVoterImage!!)

            } else {
                binding?.ivVoterImage?.setImageResource(R.drawable.e_user)
            }

        } else if (voterDetailsResponse.status == "NEW") {

            val image =
                File(voterDetailsResponse.electorProfileImage)

            if (image.exists()) {
                Glide.with(this).load(Uri.fromFile(image))
                    .into(binding?.ivVoterImage!!)
            } else if (voterDetailsResponse.electorProfileImageUrl != null
                && voterDetailsResponse.electorProfileImageUrl.isNotEmpty()
            ) {

                Glide.with(this)
                    .load(voterDetailsResponse.electorProfileImageUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding?.ivVoterImage?.setImageResource(R.drawable.e_user)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                    }
                    )
                    .into(binding?.ivVoterImage!!)

            } else {
                binding?.ivVoterImage?.setImageResource(R.drawable.e_user)
            }

        } else {

            if (voterDetailsResponse.electorProfileImageUrl != null
                && voterDetailsResponse.electorProfileImageUrl.isNotEmpty()
            ) {
                Glide.with(this)
                    .load(voterDetailsResponse.electorProfileImageUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding?.ivVoterImage?.setImageResource(R.drawable.e_user)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                    }
                    )
                    .into(binding?.ivVoterImage!!)
            } else {
                binding?.ivVoterImage?.setImageResource(R.drawable.e_user)
            }
        }

        binding?.etFamilyNumber?.setText(getVoterDetails(voterDetailsResponse.familyNumber)!!)
        binding?.etFamilyName?.setText(getVoterDetails(voterDetailsResponse.familyName)!!)
        binding?.etFamilyOrderNo?.setText(getVoterDetails(voterDetailsResponse.familySortOrder)!!)

        binding?.etLastName?.setText(getVoterDetails(voterDetailsResponse.lName)!!)
        binding?.etGivenName?.setText(getVoterDetails(voterDetailsResponse.fName)!!)
        binding?.etNationalIDCardNumber?.setText(getVoterDetails(voterDetailsResponse.natIDRecordNo)!!)

//        if (getVoterDetails(
//                voterDetailsResponse.dob
//            )!!.isEmpty()
//        ) {
//            binding?.etDateOfBirth?.text = lastDateOfEnrolment
//        } else {
//            binding?.etDateOfBirth?.text = getDisplayDate(
//                getVoterDetails(
//                    voterDetailsResponse.dob
//                )!!
//            )
//        }
        binding?.etDateOfBirth?.text = getDisplayDate(
            getVoterDetails(
                voterDetailsResponse.dob
            )!!
        )
        binding?.cbDOBIsYear?.isChecked = getVoterDetailsBoolean(voterDetailsResponse.isYOB)
        binding?.etPlaceOfBirth?.setText(getVoterDetails(voterDetailsResponse.placeOfBirth)!!)
        if (getVoterDetails(voterDetailsResponse.provinceOfBirth)!!.isEmpty()) {
            if (voterDetailsResponse.provOfBirthFK != 0)
                getVoterProvinceOfBirthName()
        } else {
            binding?.etProvinceOfBirth?.setText(getVoterDetails(voterDetailsResponse.provinceOfBirth)!!)
        }


        if (getVoterDetails(voterDetailsResponse.gender)!! == "F")
            binding?.etGender?.text = getString(R.string.female)
        else if (getVoterDetails(voterDetailsResponse.gender)!! == "M")
            binding?.etGender?.text = getString(R.string.male)

        if (getVoterDetails(voterDetailsResponse.occupation)!!.isEmpty()) {
            if (voterDetailsResponse.occupationFK != null && voterDetailsResponse.occupationFK != 0)
                getOccupation(voterDetailsResponse.occupationFK)
        } else {
            binding?.etOccupation?.text = getVoterDetails(voterDetailsResponse.occupation)!!
        }


        if (voterDetailsResponse.isPWD)
            binding?.etPersonWithDisability?.text = getString(R.string.yes)
        else
            binding?.etPersonWithDisability?.text = getString(R.string.no)

        binding?.etReligion?.setText(getVoterDetails(voterDetailsResponse.religion)!!)

        binding?.etFathersFirstName?.setText(getVoterDetails(voterDetailsResponse.fatherFName)!!)
        binding?.etFathersLastName?.setText(getVoterDetails(voterDetailsResponse.fatherLName)!!)
        binding?.etMothersFirstName?.setText(getVoterDetails(voterDetailsResponse.motherFName)!!)
        binding?.etMothersLastName?.setText(getVoterDetails(voterDetailsResponse.motherLName)!!)


        //Place of Residence
        clanId = voterDetailsResponse.clanId
        regionId = voterDetailsResponse.regionId
        provinceId = voterDetailsResponse.provinceId
        districtId = voterDetailsResponse.districtId
        llgId = voterDetailsResponse.llgId
        wardId = voterDetailsResponse.wardId
        locationId = voterDetailsResponse.locationId

        if (voterDetailsResponse.provinceName.isNullOrEmpty()) {
            if (provinceId != 0)
                getVoterProvinceName()
        } else {
            binding?.etProvince?.text = getVoterDetails(voterDetailsResponse.provinceName)!!
        }

        if (voterDetailsResponse.districtName.isNullOrEmpty()) {
            if (districtId != 0)
                getVoterDistrictName()
        } else {
            binding?.etOpenElectorate?.text = getVoterDetails(voterDetailsResponse.districtName)!!
        }

        // Set LLG Data & manage LLG Arrow
        llgName = getVoterDetails(voterDetailsResponse.llgName)!!
        if (llgName.isEmpty()) {
            if (llgId != 0)
                getVoterLLGName()
        } else {
            binding?.etLLG?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding?.etLLG?.text = llgName
        }

        // Set WARD Data & manage WARD Arrow
        wardName = getVoterDetails(voterDetailsResponse.wardName)!!
        initialWardName = wardName
        if (wardName.isEmpty()) {
            if (wardId != 0)
                getVoterWardName()
        } else {
            binding?.etWardNoAndName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding?.etWardNoAndName?.text = wardName
        }


        // Set LOCATION Data & manage LOCATION Arrow
        if (voterDetailsResponse.locationName.isNullOrEmpty()) {
            if (locationId != 0)
                getVoterLocationName()
        } else {
            binding?.etLocationOrVillage?.text =
                getVoterDetails(voterDetailsResponse.locationName)!!
        }
        if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_LOCATION, "")
                .isNotEmpty()
        ) {
            binding?.etLocationOrVillage?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }

        // Rural

        binding?.etClan?.setText(getVoterDetails(voterDetailsResponse.clan)!!)
        binding?.etSubClan?.setText(getVoterDetails(voterDetailsResponse.subClan)!!)
        binding?.etRestHouse?.setText(getVoterDetails(voterDetailsResponse.restHouse)!!)

        //Urban

        binding?.etSectionNo?.setText(getVoterDetails(voterDetailsResponse.sectionNo)!!)
        binding?.etLotNo?.setText(getVoterDetails(voterDetailsResponse.lotNo)!!)
        binding?.etStreetName?.setText(getVoterDetails(voterDetailsResponse.streetName)!!)
        binding?.etSuburb?.setText(getVoterDetails(voterDetailsResponse.suburbTownSettlement)!!)

        //Status
        if (voterDetailsResponse.status == getString(R.string.old) || voterDetailsResponse.status.isEmpty()) {
            binding?.etStatus?.text = getString(R.string.updated)
        } else {
            binding?.etStatus?.text = voterDetailsResponse.status
        }

        binding?.etComment?.setText(getVoterDetails(voterDetailsResponse.comment)!!)


        if (voterDetailsResponse.status == getString(R.string.transferred_in)
            || voterDetailsResponse.status == getString(R.string.transferred_out)
        ) {
            binding?.etLLG?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding?.etWardNoAndName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding?.etLocationOrVillage?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        } else if (voterDetailsResponse.status == getString(R.string.new_status)) {
            binding?.etLLG?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding?.etWardNoAndName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }

    }

    private fun getOccupation(occupationFK: Int) {
        launch(Dispatchers.Main) {
            val result: OccupationList? = withContext(Dispatchers.IO) {
                dataBaseInstance.occupationListDao()?.getOccupation(occupationFK)
            }
            binding?.etOccupation?.text = result!!.occupationName
        }
    }

    private fun getVoterProvinceOfBirthName() {
        launch(Dispatchers.Main) {
            val result: ProvinceList? = withContext(Dispatchers.IO) {
                fetchVoterProvinceOfBirthNameFromDB()
            }
            binding?.etProvinceOfBirth?.setText(result!!.provinceName)
        }
    }

    private fun fetchVoterProvinceOfBirthNameFromDB(): ProvinceList? {
        return dataBaseInstance.provinceListDao()
            ?.getDPOProvince(voterDetailsResponse.provOfBirthFK)
    }


    private fun getVoterProvinceName() {
        launch(Dispatchers.Main) {
            val result: ProvinceList? = withContext(Dispatchers.IO) {
                dataBaseInstance.provinceListDao()?.getDPOProvince(provinceId)
            }
            binding?.etProvince?.text = result!!.provinceName
        }
    }

    private fun getVoterDistrictName() {
        launch(Dispatchers.Main) {
            val result: DistrictList? = withContext(Dispatchers.IO) {
                dataBaseInstance.districtListDao()?.getDPODistrict(districtId)
            }
            binding?.etOpenElectorate?.text = result!!.districtName
        }
    }

    private fun getVoterLLGName() {
        launch(Dispatchers.Main) {
            val result: LLGList? = withContext(Dispatchers.IO) {
                dataBaseInstance.llgListDao()?.getDPOLLG(llgId)
            }
            llgName = result!!.llgName
            binding?.etLLG?.text = llgName
            if (llgName.isNotEmpty())
                binding?.etLLG?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getVoterWardName() {
        launch(Dispatchers.Main) {
            val result: WardList? = withContext(Dispatchers.IO) {
                dataBaseInstance.wardListDao()?.getDPOWard(wardId)
            }
            wardName = result!!.wardName
            binding?.etWardNoAndName?.text = result.wardNumber.toString() + "&" + wardName
            initialWardName = wardName
            if (initialWardName.isNotEmpty())
                binding?.etWardNoAndName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }
    }

    private fun getVoterLocationName() {
        launch(Dispatchers.Main) {
            val result: LocationList? = withContext(Dispatchers.IO) {
                dataBaseInstance.locationListDao()?.getDPOLocation(locationId)
            }
            if (result != null)
                binding?.etLocationOrVillage?.text = result.locationName
        }
    }


    private fun showDuplicateAlertMessage() {
        bindingUpdateStatus?.etComment!!.requestFocus()
        alert(getString(R.string.can_not_update_this_voter_as_duplicate)) {
            okButton {
            }
        }.show()

    }

   private fun showAlertMessage(alertMessage: String) {
        alert(alertMessage) {
            okButton {
            }
        }.show()

    }


    // UPDATE STATUS FLOW

    private var bindingUpdateStatus: DialogUpdateStatusBinding? = null
    var mUpdateStatusDialog: AlertDialog? = null

    private fun showUpdateStatusDialog() {
        val builder = AlertDialog.Builder(this, R.style.full_screen_dialog)
        bindingUpdateStatus = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_update_status, null, false
        ) as DialogUpdateStatusBinding
        builder.setView(bindingUpdateStatus?.root)
        mUpdateStatusDialog = builder.create()
        mUpdateStatusDialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        if (voterDetailsResponse.status != "OLD")
            bindingUpdateStatus?.etStatus?.text = voterDetailsResponse.status
        bindingUpdateStatus?.etComment?.setText(voterDetailsResponse.comment)

        bindingUpdateStatus?.etStatus?.setOnClickListener {
            when (voterDetailsResponse.status) {
                "NEW" -> {
                    showAlertMessage(
                        "Can't update the status of new voter"
                    )
                }

                getString(R.string.transferred_in) -> {
                    showAlertMessage(
                        "Can't update the status of transferred in voter"
                    )
                }

                else -> {
                    showStatusOptions()
                }
            }
        }

        bindingUpdateStatus?.ivClose?.setOnClickListener {
            mUpdateStatusDialog!!.dismiss()
        }

        bindingUpdateStatus?.btnDone?.setOnClickListener {
            if (bindingUpdateStatus?.etStatus?.text!!.isEmpty()) {
                showAlertMessage(getString(R.string.please_select_status))
            } else {
                validateUpdateStatus()
            }
        }

        mUpdateStatusDialog!!.setOnDismissListener {
            finish()
        }

        mUpdateStatusDialog?.show()

    }


    private fun validateUpdateStatus() {

        when (bindingUpdateStatus?.etStatus?.text) {
            getString(R.string.transferred_out) -> {

                val region = bindingUpdateStatus?.etRegion?.text.toString()
                val regionId = if (regionListArray!!.isNotEmpty() && region.isNotEmpty()) {
                    regionListArray!![bindingUpdateStatus?.etRegion?.tag as Int].regionId
                } else {
                    0
                }

                val province = bindingUpdateStatus?.etNewProvince?.text.toString()
                val provinceId = if (provinceListArray!!.isNotEmpty() && province.isNotEmpty()) {
                    provinceListArray!![bindingUpdateStatus?.etNewProvince?.tag as Int].provinceId
                } else {
                    0
                }

                val district = bindingUpdateStatus?.etDistrict?.text.toString()
                val districtId = if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    if (districtListArray!!.isNotEmpty() && district.isNotEmpty()) {
                        districtListArray!![bindingUpdateStatus?.etDistrict?.tag as Int].districtId
                    } else {
                        0
                    }
                } else {
                    if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                        if (districtListArray!!.isNotEmpty() && district.isNotEmpty()) {
                            districtListArray!![bindingUpdateStatus?.etDistrict?.tag as Int].districtId
                        } else {
                            0
                        }
                    } else {
                        districtId
                    }
                }

                val llg = bindingUpdateStatus?.etNewLLG?.text.toString()
                val llgId = if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    if (llgListArray!!.isNotEmpty() && llg.isNotEmpty()) {
                        llgListArray!![bindingUpdateStatus?.etNewLLG?.tag as Int].llgId
                    } else {
                        0
                    }
                } else {
                    if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                        if (llgListArray!!.isNotEmpty() && llg.isNotEmpty()) {
                            llgListArray!![bindingUpdateStatus?.etNewLLG?.tag as Int].llgId
                        } else {
                            0
                        }
                    } else {
                        llgId
                    }
                }

                val ward = bindingUpdateStatus?.etWard?.text.toString()
                val wardId = if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    if (wardNoAndNameArray!!.isNotEmpty() && ward.isNotEmpty()) {
                        wardNoAndNameArray!![bindingUpdateStatus?.etWard?.tag as Int].wardId
                    } else {
                        0
                    }
                } else {
                    if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                        if (wardNoAndNameArray!!.isNotEmpty() && ward.isNotEmpty()) {
                            wardNoAndNameArray!![bindingUpdateStatus?.etWard?.tag as Int].wardId
                        } else {
                            0
                        }
                    } else {
                        wardId
                    }
                }

                val location = bindingUpdateStatus?.etNewLocation?.text.toString()
                val locationId = if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    if (locationArray!!.isNotEmpty() && location.isNotEmpty()) {
                        locationArray!![bindingUpdateStatus?.etNewLocation?.tag as Int].locationId
                    } else {
                        0
                    }
                } else {
                    if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                        if (locationArray!!.isNotEmpty() && location.isNotEmpty()) {
                            locationArray!![bindingUpdateStatus?.etNewLocation?.tag as Int].locationId
                        } else {
                            0
                        }
                    } else {
                        locationId
                    }
                }

                val status = bindingUpdateStatus?.etStatus?.text.toString()
                val comment = bindingUpdateStatus?.etComment?.text.toString()

                when {
//                    region.isEmpty() -> {
//                        showAlertMessage(
//                            "Please select region"
//                        )
//                    }
//                    province.isEmpty() -> {
//                        showAlertMessage(
//                            "Please select province"
//                        )
//                    }

                    else -> {
                        CommonMethods.hideKeypad(this, bindingUpdateStatus?.etComment!!)
                        updateTransferredOutStatus(
                            regionId,
                            province,
                            provinceId,
                            districtId,
                            district,
                            llg,
                            llgId,
                            ward,
                            wardId,
                            location,
                            locationId,
                            status,
                            comment
                        )
                    }
                }
            }

            "DUPLICATE" -> {
                when {
                    duplicateId.toString() == "0" -> {
                        showDuplicateAlertMessage()
                    }

                    else -> {
                        CommonMethods.hideKeypad(this, bindingUpdateStatus?.etComment!!)
                        updateVoterStatus()
                    }
                }
            }

            else -> {
                CommonMethods.hideKeypad(this, bindingUpdateStatus?.etComment!!)
                updateVoterStatus()
            }
        }

    }

    private fun updateVoterStatus() {
        val status = bindingUpdateStatus?.etStatus?.text.toString()
        val comment = bindingUpdateStatus?.etComment?.text.toString()

        val updatedVoterInformation = VotersList()
        updatedVoterInformation.tempElectorId = voterDetailsResponse.tempElectorId
        updatedVoterInformation.electorId = voterDetailsResponse.electorId
        updatedVoterInformation.oldElectorID = voterDetailsResponse.oldElectorID
        updatedVoterInformation.locationId = voterDetailsResponse.locationId
        updatedVoterInformation.mkaLocationId = voterDetailsResponse.mkaLocationId
        updatedVoterInformation.oldLocationId = voterDetailsResponse.oldLocationId
        updatedVoterInformation.locationName = getVoterDetails(voterDetailsResponse.locationName)
        updatedVoterInformation.mkaLocationName = getVoterDetails(voterDetailsResponse.mkaLocationName)
        updatedVoterInformation.wardId = voterDetailsResponse.wardId
        updatedVoterInformation.mkaWardId = voterDetailsResponse.mkaWardId
        updatedVoterInformation.wardName = wardName
        updatedVoterInformation.mkaWardName = voterDetailsResponse.mkaWardName
        updatedVoterInformation.llgId = voterDetailsResponse.llgId
        updatedVoterInformation.mkaLlgId = voterDetailsResponse.mkaLlgId
        updatedVoterInformation.llgName = getVoterDetails(voterDetailsResponse.llgName)
        updatedVoterInformation.mkaLlgName = getVoterDetails(voterDetailsResponse.mkaLlgName)
        updatedVoterInformation.districtId = voterDetailsResponse.districtId
        updatedVoterInformation.mkaDistrictId = voterDetailsResponse.mkaDistrictId
        updatedVoterInformation.provinceId = voterDetailsResponse.provinceId
        updatedVoterInformation.regionId = voterDetailsResponse.regionId
        updatedVoterInformation.lName = getVoterDetails(voterDetailsResponse.lName)
        updatedVoterInformation.fName = getVoterDetails(voterDetailsResponse.fName)
        updatedVoterInformation.voterName =
            getVoterDetails(voterDetailsResponse.lName) + " " + getVoterDetails(voterDetailsResponse.fName)
        updatedVoterInformation.gender = getVoterDetails(voterDetailsResponse.gender)
        updatedVoterInformation.dob = getVoterDetails(voterDetailsResponse.dob)
        updatedVoterInformation.isPWD = voterDetailsResponse.isPWD
        updatedVoterInformation.religion = getVoterDetails(voterDetailsResponse.religion)
        updatedVoterInformation.fatherFName = getVoterDetails(voterDetailsResponse.fatherFName)
        updatedVoterInformation.fatherLName = getVoterDetails(voterDetailsResponse.fatherLName)
        updatedVoterInformation.motherFName = getVoterDetails(voterDetailsResponse.motherFName)
        updatedVoterInformation.motherLName = getVoterDetails(voterDetailsResponse.motherLName)
        updatedVoterInformation.provinceOfBirth =
            getVoterDetails(voterDetailsResponse.provinceOfBirth)
        updatedVoterInformation.provOfBirthFK = voterDetailsResponse.provOfBirthFK
        updatedVoterInformation.provinceName = getVoterDetails(voterDetailsResponse.provinceName)
        updatedVoterInformation.districtName =
            getVoterDetails(voterDetailsResponse.districtName)
        updatedVoterInformation.mkaDistrictName =
            getVoterDetails(voterDetailsResponse.mkaDistrictName)
        if (getVoterDetails(voterDetailsResponse.provinceOfBirth).isNullOrEmpty())
            updatedVoterInformation.provinceOfBirth =
                binding?.etProvinceOfBirth?.text.toString().trim()
        else
            updatedVoterInformation.provinceOfBirth =
                getVoterDetails(voterDetailsResponse.provinceOfBirth)
        updatedVoterInformation.occupationFK = voterDetailsResponse.occupationFK
        if (getVoterDetails(voterDetailsResponse.occupation).isNullOrEmpty())
            updatedVoterInformation.occupation = binding?.etOccupation?.text.toString().trim()
        else
            updatedVoterInformation.occupation = getVoterDetails(voterDetailsResponse.occupation)
        updatedVoterInformation.clan = getVoterDetails(voterDetailsResponse.clan)
        updatedVoterInformation.clanId = voterDetailsResponse.clanId
        updatedVoterInformation.subClan = getVoterDetails(voterDetailsResponse.subClan)
        updatedVoterInformation.address = getVoterDetails(voterDetailsResponse.locationName)
        updatedVoterInformation.natID = ""
        updatedVoterInformation.natIDRecordNo = getVoterDetails(voterDetailsResponse.natIDRecordNo)
        updatedVoterInformation.status = status
        updatedVoterInformation.houseFK = 0
        updatedVoterInformation.houseSortOrder = 0
        updatedVoterInformation.restHouse = getVoterDetails(voterDetailsResponse.restHouse)
        updatedVoterInformation.sectionNo = getIntegerVoterDetails(voterDetailsResponse.sectionNo)
        updatedVoterInformation.lotNo = getIntegerVoterDetails(voterDetailsResponse.lotNo)
        updatedVoterInformation.streetName = getVoterDetails(voterDetailsResponse.streetName)
        updatedVoterInformation.suburbTownSettlement =
            getVoterDetails(voterDetailsResponse.suburbTownSettlement)
        updatedVoterInformation.familyFK = 0
        updatedVoterInformation.familyNumber =
            getIntegerVoterDetails(voterDetailsResponse.familyNumber)
        updatedVoterInformation.familyName = getVoterDetails(voterDetailsResponse.familyName)
        updatedVoterInformation.familySortOrder =
            getIntegerVoterDetails(voterDetailsResponse.familySortOrder)
        updatedVoterInformation.dpo =
            securedSharedPreferenceWrapper.getString(ApiConstants.DPO_NAME, "")
        updatedVoterInformation.lat = CommonMethods.getLatitudeOrLongitude(
            securedSharedPreferenceWrapper.getString(
                ApiConstants.LATITUDE,
                "0"
            )
        )
        updatedVoterInformation.longitude =
            CommonMethods.getLatitudeOrLongitude(
                securedSharedPreferenceWrapper.getString(
                    ApiConstants.LONGITUDE,
                    "0"
                )
            )
        updatedVoterInformation.posted = CommonMethods.getVoterDetailsPostedDateTime()
        updatedVoterInformation.updatedDate = CommonMethods.getVoterDetailsUpdatedDate()
        updatedVoterInformation.comment = comment
        updatedVoterInformation.electorProfileImage =
            getVoterDetails(voterDetailsResponse.electorProfileImage)
        updatedVoterInformation.electorProfileImageUrl =
            getVoterDetails(voterDetailsResponse.electorProfileImageUrl)
        updatedVoterInformation.electorFormImage =
            getVoterDetails(voterDetailsResponse.electorFormImage)
        updatedVoterInformation.electorFormImageUrl =
            getVoterDetails(voterDetailsResponse.electorFormImageUrl)
        updatedVoterInformation.electorProfileImageFile =
            getVoterDetails(voterDetailsResponse.electorProfileImageFile)
        updatedVoterInformation.electorFormImageFile =
            getVoterDetails(voterDetailsResponse.electorFormImageFile)
        updatedVoterInformation.enrolmentFormNumber =
            getVoterDetails(voterDetailsResponse.enrolmentFormNumber)
        updatedVoterInformation.createdBy = loggedInDPOUserData.userId.toInt()
        if(status == getString(R.string.old)){
            updatedVoterInformation.isSyncToServer = 1
            updatedVoterInformation.isDataUploaded = 1
        }else{
            updatedVoterInformation.isSyncToServer = 0
            updatedVoterInformation.isDataUploaded = 0
        }
        updatedVoterInformation.syncStatus = 0
        if (status == "DUPLICATE")
            updatedVoterInformation.duplicateId = duplicateId
        UpdatedVoterInformationToDB(updatedVoterInformation).execute()
    }

    private fun updateTransferredOutStatus(
        regionId: Int?,
        province: String,
        provinceId: Int?,
        districtId: Int?,
        district: String,
        llg: String,
        llgId: Int?,
        ward: String,
        wardId: Int?,
        location: String,
        locationId: Int?,
        status: String,
        comment: String
    ) {

        val updatedVoterInformation = VotersList()
        updatedVoterInformation.tempElectorId = voterDetailsResponse.tempElectorId
        updatedVoterInformation.electorId = voterDetailsResponse.electorId
        updatedVoterInformation.oldElectorID = voterDetailsResponse.oldElectorID
        updatedVoterInformation.locationId = voterDetailsResponse.locationId
        updatedVoterInformation.mkaLocationId = voterDetailsResponse.mkaLocationId
        updatedVoterInformation.oldLocationId = voterDetailsResponse.oldLocationId
        updatedVoterInformation.locationName = getVoterDetails(voterDetailsResponse.locationName)
        updatedVoterInformation.mkaLocationName =
            getVoterDetails(voterDetailsResponse.mkaLocationName)
        updatedVoterInformation.wardId = voterDetailsResponse.wardId
        updatedVoterInformation.mkaWardId = voterDetailsResponse.mkaWardId
        updatedVoterInformation.wardName = wardName
        updatedVoterInformation.mkaWardName = voterDetailsResponse.mkaWardName
        updatedVoterInformation.llgId = voterDetailsResponse.llgId
        updatedVoterInformation.mkaLlgId = voterDetailsResponse.mkaLlgId
        updatedVoterInformation.llgName = getVoterDetails(voterDetailsResponse.llgName)
        updatedVoterInformation.mkaLlgName = getVoterDetails(voterDetailsResponse.mkaLlgName)
        updatedVoterInformation.districtId = voterDetailsResponse.districtId
        updatedVoterInformation.mkaDistrictId = voterDetailsResponse.mkaDistrictId
        updatedVoterInformation.provinceId = voterDetailsResponse.provinceId
        updatedVoterInformation.regionId = voterDetailsResponse.regionId
        updatedVoterInformation.lName = getVoterDetails(voterDetailsResponse.lName)
        updatedVoterInformation.fName = getVoterDetails(voterDetailsResponse.fName)
        updatedVoterInformation.voterName =
            getVoterDetails(voterDetailsResponse.lName) + " " + getVoterDetails(voterDetailsResponse.fName)
        updatedVoterInformation.gender = getVoterDetails(voterDetailsResponse.gender)
        updatedVoterInformation.dob = getVoterDetails(voterDetailsResponse.dob)
        updatedVoterInformation.isYOB = binding?.cbDOBIsYear?.isChecked == true
        updatedVoterInformation.isPWD = voterDetailsResponse.isPWD
        updatedVoterInformation.fatherFName = getVoterDetails(voterDetailsResponse.fatherFName)
        updatedVoterInformation.fatherLName = getVoterDetails(voterDetailsResponse.fatherLName)
        updatedVoterInformation.motherFName = getVoterDetails(voterDetailsResponse.motherFName)
        updatedVoterInformation.motherLName = getVoterDetails(voterDetailsResponse.motherLName)
        if (getVoterDetails(voterDetailsResponse.provinceOfBirth).isNullOrEmpty())
            updatedVoterInformation.provinceOfBirth =
                binding?.etProvinceOfBirth?.text.toString().trim()
        else
            updatedVoterInformation.provinceOfBirth =
                getVoterDetails(voterDetailsResponse.provinceOfBirth)
        updatedVoterInformation.provOfBirthFK = voterDetailsResponse.provOfBirthFK
        updatedVoterInformation.provinceName = getVoterDetails(voterDetailsResponse.provinceName)
        updatedVoterInformation.districtName =
            getVoterDetails(voterDetailsResponse.districtName)
        updatedVoterInformation.mkaDistrictName =
            getVoterDetails(voterDetailsResponse.mkaDistrictName)
        updatedVoterInformation.placeOfBirth = getVoterDetails(voterDetailsResponse.placeOfBirth)
        updatedVoterInformation.occupationFK = voterDetailsResponse.occupationFK
//        updatedVoterInformation.occupation = getVoterDetails(voterDetailsResponse.occupation)
        if (getVoterDetails(voterDetailsResponse.occupation).isNullOrEmpty())
            updatedVoterInformation.occupation = binding?.etOccupation?.text.toString().trim()
        else
            updatedVoterInformation.occupation = getVoterDetails(voterDetailsResponse.occupation)
        updatedVoterInformation.clan = getVoterDetails(voterDetailsResponse.clan)
        updatedVoterInformation.clanId = voterDetailsResponse.clanId
        updatedVoterInformation.subClan = getVoterDetails(voterDetailsResponse.subClan)
        updatedVoterInformation.address = getVoterDetails(voterDetailsResponse.locationName)
        updatedVoterInformation.natID = ""
        updatedVoterInformation.natIDRecordNo = getVoterDetails(voterDetailsResponse.natIDRecordNo)
        updatedVoterInformation.status = status
        updatedVoterInformation.houseFK = 0
        updatedVoterInformation.houseSortOrder = 0
        updatedVoterInformation.restHouse = getVoterDetails(voterDetailsResponse.restHouse)
        updatedVoterInformation.sectionNo =
            getIntegerVoterDetails(voterDetailsResponse.sectionNo)
        updatedVoterInformation.lotNo =
            getIntegerVoterDetails(voterDetailsResponse.lotNo)
        updatedVoterInformation.streetName = getVoterDetails(voterDetailsResponse.streetName)
        updatedVoterInformation.suburbTownSettlement =
            getVoterDetails(voterDetailsResponse.suburbTownSettlement)
        updatedVoterInformation.familyFK = 0
        updatedVoterInformation.familyNumber =
            getIntegerVoterDetails(voterDetailsResponse.familyNumber)
        updatedVoterInformation.familyName = getVoterDetails(voterDetailsResponse.familyName)
        updatedVoterInformation.familySortOrder =
            getIntegerVoterDetails(voterDetailsResponse.familySortOrder)
        updatedVoterInformation.dpo =
            securedSharedPreferenceWrapper.getString(ApiConstants.DPO_NAME, "")
        updatedVoterInformation.lat = CommonMethods.getLatitudeOrLongitude(
            securedSharedPreferenceWrapper.getString(
                ApiConstants.LATITUDE,
                "0"
            )
        )
        updatedVoterInformation.longitude =
            CommonMethods.getLatitudeOrLongitude(
                securedSharedPreferenceWrapper.getString(
                    ApiConstants.LONGITUDE,
                    "0"
                )
            )
        updatedVoterInformation.posted = CommonMethods.getVoterDetailsPostedDateTime()
        updatedVoterInformation.updatedDate = CommonMethods.getVoterDetailsUpdatedDate()
        updatedVoterInformation.comment = comment
        updatedVoterInformation.electorProfileImage =
            getVoterDetails(voterDetailsResponse.electorProfileImage)
        updatedVoterInformation.electorProfileImageUrl =
            getVoterDetails(voterDetailsResponse.electorProfileImageUrl)
        updatedVoterInformation.electorFormImage =
            getVoterDetails(voterDetailsResponse.electorFormImage)
        updatedVoterInformation.electorFormImageUrl =
            getVoterDetails(voterDetailsResponse.electorFormImageUrl)
        updatedVoterInformation.electorProfileImageFile =
            getVoterDetails(voterDetailsResponse.electorProfileImageFile)
        updatedVoterInformation.electorFormImageFile =
            getVoterDetails(voterDetailsResponse.electorFormImageFile)
        updatedVoterInformation.enrolmentFormNumber =
            getVoterDetails(voterDetailsResponse.enrolmentFormNumber)
        updatedVoterInformation.createdBy = loggedInDPOUserData.userId.toInt()
        updatedVoterInformation.isSyncToServer = 0
        updatedVoterInformation.isDataUploaded = 0
        updatedVoterInformation.syncStatus = 0

        updatedVoterInformation.updatedDateTime = Math.abs(System.currentTimeMillis().toInt())
        if (securedSharedPreferenceWrapper.getString(
                ApiConstants.ROLE,
                ""
            ) == ApiConstants.ROLE_DPO
        ) {
            updatedVoterInformation.transferInRegionId = regionId
            updatedVoterInformation.transferInProvinceId = provinceId
            updatedVoterInformation.transferInDistrictId = districtId
            updatedVoterInformation.transferInLLGId = llgId
            updatedVoterInformation.transferInWardId = wardId
            updatedVoterInformation.transferInLocationId = locationId
        } else {
            if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                updatedVoterInformation.transferInRegionId = regionId
                updatedVoterInformation.transferInProvinceId = provinceId
                updatedVoterInformation.transferInDistrictId = districtId
                updatedVoterInformation.transferInLLGId = llgId
                updatedVoterInformation.transferInWardId = wardId
                updatedVoterInformation.transferInLocationId = locationId
            } else {
                updatedVoterInformation.transferInMkaDistrictId = districtId
                updatedVoterInformation.transferInMkaLlgId = llgId
                updatedVoterInformation.transferInMkaWardId = wardId
                updatedVoterInformation.transferInMkaLocationId = locationId
            }
        }

        UpdatedVoterInformationToDB(updatedVoterInformation).execute()
    }

    @SuppressLint("StaticFieldLeak")
    internal inner class UpdatedVoterInformationToDB(private val updatedVoterInformation: VotersList = VotersList()) :
        AsyncTask<Void?, Void?, Void?>() {

        override fun doInBackground(vararg p0: Void?): Void? {
            val data = dataBaseInstance.votersListDao()
            data.update(updatedVoterInformation)
            return null

        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            alert("Status updated successfully") {
                okButton {
                    mUpdateStatusDialog!!.dismiss()
                    val resultIntent = Intent()
                    resultIntent.putExtra("NEED_REFRESH", true)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }.show()

        }
    }


    private fun showStatusOptions() {

        val items = if (voterDetailsResponse.status == getString(R.string.deceased) ||
            voterDetailsResponse.status == getString(R.string.transferred_out) ||
            voterDetailsResponse.status == getString(R.string.ghost_report) ||
            voterDetailsResponse.status == getString(R.string.duplicate) ||
            voterDetailsResponse.status == getString(R.string.under_age)
        ) {
            arrayOf<CharSequence>(
                getString(R.string.deceased),
                getString(R.string.transferred_out),
                getString(R.string.ghost_report),
                getString(R.string.duplicate),
                getString(R.string.under_age),
                getString(R.string.restore),
                getString(R.string.old),
                getString(R.string.updated)
            )
        } else {
            arrayOf<CharSequence>(
                getString(R.string.deceased),
                getString(R.string.transferred_out),
                getString(R.string.ghost_report),
                getString(R.string.duplicate),
                getString(R.string.under_age),
                getString(R.string.old),
                getString(R.string.updated)
            )
        }

        val builder = AlertDialog.Builder(this)
        if (voterDetailsResponse.status == getString(R.string.deceased) ||
            voterDetailsResponse.status == getString(R.string.transferred_out) ||
            voterDetailsResponse.status == getString(R.string.ghost_report) ||
            voterDetailsResponse.status == getString(R.string.duplicate) ||
            voterDetailsResponse.status == getString(R.string.under_age)
        ){
            builder.setItems(items) { dialog, item ->
                bindingUpdateStatus?.etStatus?.text = items[item]
                when (item) {
                    1 -> {
                        showTransferredOptions()
                    }

                    3 -> {
                        bindingUpdateStatus?.etComment!!.setText("")
                        hideTransferredOptions()
                        CommonMethods.showProgress(this)
                        GetDuplicateVotersListAsync().execute()
                    }

                    5 -> {
                        hideTransferredOptions()
                        resetVoterStatus()
                    }

                    else -> {
                        bindingUpdateStatus?.etComment!!.setText("")
                        hideTransferredOptions()
                    }
                }
            }
        }else{
            builder.setItems(items) { dialog, item ->
                bindingUpdateStatus?.etStatus?.text = items[item]
                when (item) {
                    1 -> {
                        showTransferredOptions()
                    }

                    3 -> {
                        bindingUpdateStatus?.etComment!!.setText("")
                        hideTransferredOptions()
                        CommonMethods.showProgress(this)
                        GetDuplicateVotersListAsync().execute()
                    }

                    else -> {
                        bindingUpdateStatus?.etComment!!.setText("")
                        hideTransferredOptions()
                    }
                }
            }
        }

        builder.show()

    }

    private fun resetVoterStatus() {

        val updatedVoterInformation = VotersList()
        updatedVoterInformation.tempElectorId = voterDetailsResponse.tempElectorId
        updatedVoterInformation.electorId = voterDetailsResponse.electorId
        updatedVoterInformation.oldElectorID = voterDetailsResponse.oldElectorID
        updatedVoterInformation.locationId = voterDetailsResponse.locationId
        updatedVoterInformation.oldLocationId = voterDetailsResponse.oldLocationId
        updatedVoterInformation.locationName = getVoterDetails(voterDetailsResponse.locationName)
        updatedVoterInformation.wardId = voterDetailsResponse.wardId
        updatedVoterInformation.wardName = wardName
        updatedVoterInformation.llgId = voterDetailsResponse.llgId
        updatedVoterInformation.llgName = getVoterDetails(voterDetailsResponse.llgName)
        updatedVoterInformation.districtId = voterDetailsResponse.districtId
        updatedVoterInformation.provinceId = voterDetailsResponse.provinceId
        updatedVoterInformation.regionId = voterDetailsResponse.regionId
        updatedVoterInformation.lName = getVoterDetails(voterDetailsResponse.lName)
        updatedVoterInformation.fName = getVoterDetails(voterDetailsResponse.fName)
        updatedVoterInformation.voterName =
            getVoterDetails(voterDetailsResponse.lName) + " " + getVoterDetails(voterDetailsResponse.fName)
        updatedVoterInformation.gender = getVoterDetails(voterDetailsResponse.gender)
        updatedVoterInformation.dob = getVoterDetails(voterDetailsResponse.dob)
        updatedVoterInformation.isYOB = binding?.cbDOBIsYear?.isChecked == true
        updatedVoterInformation.isPWD = voterDetailsResponse.isPWD
        updatedVoterInformation.fatherFName = getVoterDetails(voterDetailsResponse.fatherFName)
        updatedVoterInformation.fatherLName = getVoterDetails(voterDetailsResponse.fatherLName)
        updatedVoterInformation.motherFName = getVoterDetails(voterDetailsResponse.motherFName)
        updatedVoterInformation.motherLName = getVoterDetails(voterDetailsResponse.motherLName)
        updatedVoterInformation.provinceOfBirth =
            getVoterDetails(voterDetailsResponse.provinceOfBirth)
        updatedVoterInformation.provOfBirthFK = voterDetailsResponse.provOfBirthFK
        updatedVoterInformation.provinceName = getVoterDetails(voterDetailsResponse.provinceName)
        updatedVoterInformation.districtName =
            getVoterDetails(voterDetailsResponse.districtName)
        updatedVoterInformation.placeOfBirth = getVoterDetails(voterDetailsResponse.placeOfBirth)
        updatedVoterInformation.occupationFK = voterDetailsResponse.occupationFK
//        updatedVoterInformation.occupation = getVoterDetails(voterDetailsResponse.occupation)
        if (getVoterDetails(voterDetailsResponse.occupation).isNullOrEmpty())
            updatedVoterInformation.occupation = binding?.etOccupation?.text.toString().trim()
        else
            updatedVoterInformation.occupation = getVoterDetails(voterDetailsResponse.occupation)
        updatedVoterInformation.clan = getVoterDetails(voterDetailsResponse.clan)
        updatedVoterInformation.clanId = voterDetailsResponse.clanId
        updatedVoterInformation.subClan = getVoterDetails(voterDetailsResponse.subClan)
        updatedVoterInformation.address = getVoterDetails(voterDetailsResponse.locationName)
        updatedVoterInformation.natID = ""
        updatedVoterInformation.natIDRecordNo = getVoterDetails(voterDetailsResponse.natIDRecordNo)
        updatedVoterInformation.status = getString(R.string.old)
        updatedVoterInformation.houseFK = 0
        updatedVoterInformation.houseSortOrder = 0
        updatedVoterInformation.restHouse = getVoterDetails(voterDetailsResponse.restHouse)
        updatedVoterInformation.sectionNo =
            getIntegerVoterDetails(voterDetailsResponse.sectionNo)
        updatedVoterInformation.lotNo =
            getIntegerVoterDetails(voterDetailsResponse.lotNo)
        updatedVoterInformation.streetName = getVoterDetails(voterDetailsResponse.streetName)
        updatedVoterInformation.suburbTownSettlement =
            getVoterDetails(voterDetailsResponse.suburbTownSettlement)
        updatedVoterInformation.familyFK = 0
        updatedVoterInformation.familyNumber =
            getIntegerVoterDetails(voterDetailsResponse.familyNumber)
        updatedVoterInformation.familyName = getVoterDetails(voterDetailsResponse.familyName)
        updatedVoterInformation.familySortOrder =
            getIntegerVoterDetails(voterDetailsResponse.familySortOrder)
        updatedVoterInformation.dpo =
            securedSharedPreferenceWrapper.getString(ApiConstants.DPO_NAME, "")
        updatedVoterInformation.lat = CommonMethods.getLatitudeOrLongitude(
            securedSharedPreferenceWrapper.getString(
                ApiConstants.LATITUDE,
                "0"
            )
        )
        updatedVoterInformation.longitude =
            CommonMethods.getLatitudeOrLongitude(
                securedSharedPreferenceWrapper.getString(
                    ApiConstants.LONGITUDE,
                    "0"
                )
            )
        updatedVoterInformation.posted = CommonMethods.getVoterDetailsPostedDateTime()
        updatedVoterInformation.updatedDate = CommonMethods.getVoterDetailsUpdatedDate()
        updatedVoterInformation.comment = ""
        updatedVoterInformation.electorProfileImage =
            getVoterDetails(voterDetailsResponse.electorProfileImage)
        updatedVoterInformation.electorProfileImageUrl =
            getVoterDetails(voterDetailsResponse.electorProfileImageUrl)
        updatedVoterInformation.electorFormImage =
            getVoterDetails(voterDetailsResponse.electorFormImage)
        updatedVoterInformation.electorFormImageUrl =
            getVoterDetails(voterDetailsResponse.electorFormImageUrl)
        updatedVoterInformation.electorProfileImageFile =
            getVoterDetails(voterDetailsResponse.electorProfileImageFile)
        updatedVoterInformation.electorFormImageFile =
            getVoterDetails(voterDetailsResponse.electorFormImageFile)
        updatedVoterInformation.enrolmentFormNumber =
            getVoterDetails(voterDetailsResponse.enrolmentFormNumber)
        updatedVoterInformation.createdBy = loggedInDPOUserData.userId.toInt()
        updatedVoterInformation.isSyncToServer = 1
        updatedVoterInformation.isDataUploaded = 1
        updatedVoterInformation.syncStatus = 0

        updatedVoterInformation.updatedDateTime = Math.abs(System.currentTimeMillis().toInt())
        updatedVoterInformation.transferInRegionId = 0
        updatedVoterInformation.transferInProvinceId = 0
        updatedVoterInformation.transferInDistrictId = 0
        updatedVoterInformation.transferInLLGId = 0
        updatedVoterInformation.transferInWardId = 0
        updatedVoterInformation.transferInLocationId = 0
        updatedVoterInformation.duplicateId = duplicateId

        UpdatedVoterInformationToDB(updatedVoterInformation).execute()

    }

    private var wardVotersList = mutableListOf<VotersList>()

    @SuppressLint("StaticFieldLeak")
    internal inner class GetDuplicateVotersListAsync :
        AsyncTask<Void?, Void?, List<VotersList>?>() {

        override fun doInBackground(vararg p0: Void?): List<VotersList>? {
            if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO) {
                return dataBaseInstance.votersListDao().getDuplicateWardFilteredVotersList(
                    getIntegerVoterDetails(voterDetailsResponse.wardId)!!
                )
            }else{
                if(filterType == ApiConstants.PNGEC_GEO_STRUCTURE){
                    return dataBaseInstance.votersListDao().getDuplicateWardFilteredVotersList(
                        getIntegerVoterDetails(voterDetailsResponse.wardId)!!
                    )
                }else{
                    return dataBaseInstance.votersListDao().getDuplicateWardFilteredVotersListForMKA(
                        getIntegerVoterDetails(voterDetailsResponse.mkaWardId)!!
                    )
                }
            }
        }

        override fun onPostExecute(result: List<VotersList>?) {
            super.onPostExecute(result)
            if (result!!.isEmpty()) {
                CommonMethods.hideProgress()
                showAlertMessage("No duplicate voter found!")
            } else {
                wardVotersList.clear()
                wardVotersList.addAll(getDisplayVotersListToMakeDuplicate(result))
                showDuplicateRecords(wardVotersList)
                CommonMethods.hideProgress()
            }
        }
    }

    private fun getDisplayVotersListToMakeDuplicate(result: List<VotersList>): List<VotersList> {

        val dList: ArrayList<VotersList> = ArrayList()
        for (i in result.indices) {
            if (CommonMethods.getLongVoterDetails(voterDetailsResponse.electorId) != CommonMethods.getLongVoterDetails(
                    result[i].electorId
                )
            ) {
                if (result[i].status != "DUPLICATE")
                    dList.add(result[i])
            }
        }
        return dList
    }


    private var bindingDuplicateVotersList: DialogDuplicateVotersListBinding? = null
    var mDuplicateVotersListDialog: AlertDialog? = null

    private var duplicateVotersAdapter: GenericAdapter<VotersList, ItemSyncVoterBinding>? = null

    private fun showDuplicateRecords(result: List<VotersList>?) {

        val builder = AlertDialog.Builder(this)
        bindingDuplicateVotersList = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_duplicate_voters_list, null, false
        ) as DialogDuplicateVotersListBinding
        builder.setView(bindingDuplicateVotersList?.root)
        mDuplicateVotersListDialog = builder.create()

        val layoutManager = LinearLayoutManager(this)
        bindingDuplicateVotersList?.rvDuplicateVoters?.layoutManager = layoutManager
        setDuplicateVotersAdapter(result)

        bindingDuplicateVotersList?.ivClose?.setOnClickListener {
            mDuplicateVotersListDialog?.dismiss()
        }

        bindingDuplicateVotersList?.etSearch?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchVotersList(s.toString())
            }
        })

        mDuplicateVotersListDialog?.show()

    }

    private fun searchVotersList(voterNameOrID: String) {

        val filteredVotersList = mutableListOf<VotersList>()

        for (i in wardVotersList.indices) {

            val electorId = wardVotersList[i].electorId

            val firstName = if (wardVotersList[i].fName.isNullOrBlank()
                || wardVotersList[i].fName.isNullOrEmpty()
            ) {
                ""
            } else {
                wardVotersList[i].fName
            }


            val lastName = if (wardVotersList[i].lName.isNullOrBlank()
                || wardVotersList[i].lName.isNullOrEmpty()
            ) {
                ""
            } else {
                wardVotersList[i].lName
            }

            if (searchWithFirstAndLastName(
                    voterNameOrID,
                    firstName,
                    lastName
                )
                || electorId.toString().contains(voterNameOrID)
            ) {
                filteredVotersList.add(wardVotersList[i])
            }
        }

        setDuplicateVotersAdapter(filteredVotersList)

    }

    private fun searchWithFirstAndLastName(
        voterNameOrID: String,
        fName: String?,
        lName: String?
    ): Boolean {

        var similarVoter = false

        if (fName!!.lowercase(Locale.getDefault()).contains(
                voterNameOrID.lowercase(
                    Locale.getDefault()
                )
            )
        ) {
            similarVoter = true
        } else if (lName!!.lowercase(Locale.getDefault()).contains(
                voterNameOrID.lowercase(
                    Locale.getDefault()
                )
            )
        ) {
            similarVoter = true
        }

        return similarVoter
    }


    private fun setDuplicateVotersAdapter(result: List<VotersList>?) {

        duplicateVotersAdapter = object :
            GenericAdapter<VotersList, ItemSyncVoterBinding>(result) {
            override fun getLayoutId(): Int {
                return R.layout.item_sync_voter
            }

            override fun onBindData(
                model: VotersList?,
                position: Int,
                dataBinding: ItemSyncVoterBinding?
            ) {

                if (model?.electorProfileImage != null
                    && model.electorProfileImage.isNotEmpty()
                ) {

                    val image =
                        File(model.electorProfileImage)

                    if (image.exists()) {
                        Glide.with(this@WorkingRollUpdateStatusActivity).load(Uri.fromFile(image))
                            .into(dataBinding?.ivVoterImage!!)
                    } else if (model.electorProfileImageUrl != null
                        && model.electorProfileImageUrl.isNotEmpty()
                    ) {

                        Glide.with(this@WorkingRollUpdateStatusActivity)
                            .load(model.electorProfileImageUrl)
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    dataBinding?.ivVoterImage?.setImageResource(R.drawable.e_user)
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    return false
                                }

                            }
                            )
                            .into(dataBinding?.ivVoterImage!!)

                    } else {
                        dataBinding?.ivVoterImage?.setImageResource(R.drawable.e_user)
                    }

                } else if (model?.status == "NEW" && (model.electorProfileImageUrl == null
                            || model.electorProfileImageUrl.isEmpty())
                ) {
                    val image =
                        File(model.electorProfileImage)
                    Glide.with(this@WorkingRollUpdateStatusActivity).load(Uri.fromFile(image))
                        .into(dataBinding?.ivVoterImage!!)

                } else {

                    if (model?.electorProfileImageUrl != null
                        && model.electorProfileImageUrl.isNotEmpty()
                    ) {

                        Glide.with(this@WorkingRollUpdateStatusActivity)
                            .load(model.electorProfileImageUrl)
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    dataBinding?.ivVoterImage?.setImageResource(R.drawable.e_user)
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    return false
                                }

                            }
                            )
                            .into(dataBinding?.ivVoterImage!!)

                    } else {
                        dataBinding?.ivVoterImage?.setImageResource(R.drawable.e_user)
                    }
                }

                dataBinding?.tvVoterName?.text = CommonMethods.getVoterName(model)
                dataBinding?.tvVoterId?.text = "ID - " + model?.electorId
                dataBinding?.tvUpdatedDate?.text = getPostedDate(model?.posted)
                dataBinding?.tvVoterLocation?.text = model?.locationName
                dataBinding?.tvVoterStatus?.text = model?.status

                dataBinding?.cbSelectItem?.setOnCheckedChangeListener { _, _ ->
                    saveDuplicateVoter(model)
                }

            }
        }

        bindingDuplicateVotersList?.rvDuplicateVoters?.adapter = duplicateVotersAdapter

    }

    @SuppressLint("SetTextI18n")
    private fun saveDuplicateVoter(model: VotersList?) {
        mDuplicateVotersListDialog?.dismiss()
        duplicateId = model?.electorId!!
        bindingUpdateStatus?.etComment!!.setText("DUP eid {$duplicateId}")
    }

    private fun getPostedDate(postedDate: String?): String? {

        return try {
            val mSimpleDateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
            val serverDate = mSimpleDateFormat.parse(postedDate!!)
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.US)
            sdf.format(serverDate!!)
        } catch (e: Exception) {
            e.printStackTrace()
            postedDate
        }

    }


    private fun hideTransferredOptions() {
        bindingUpdateStatus?.llTransferredOut?.visibility = View.GONE
    }

    private fun showTransferredOptions() {
        bindingUpdateStatus?.llTransferredOut?.visibility = View.VISIBLE
        if (securedSharedPreferenceWrapper.getString(
                ApiConstants.ROLE,
                ""
            ) == ApiConstants.ROLE_DPO
        ) {
            bindingUpdateStatus?.llRegion?.visibility = View.VISIBLE
            bindingUpdateStatus?.llProvince?.visibility = View.VISIBLE
        } else {
            if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                bindingUpdateStatus?.llRegion?.visibility = View.VISIBLE
                bindingUpdateStatus?.llProvince?.visibility = View.VISIBLE
            } else {
                bindingUpdateStatus?.llRegion?.visibility = View.GONE
                bindingUpdateStatus?.llProvince?.visibility = View.GONE
            }
        }
        if (bindingUpdateStatus?.etNewProvince?.text!!.isNotEmpty())
            bindingUpdateStatus?.etComment!!.setText("TRF OUT {" + bindingUpdateStatus?.etNewProvince?.text + "}")

        bindingUpdateStatus?.rlRegion?.setOnClickListener {
            CommonMethods.showProgress(this)
            getRegionListFromDB()
        }

        bindingUpdateStatus?.rlNewProvince?.setOnClickListener {
            if (regionListArray?.size!! > 0 && bindingUpdateStatus?.etRegion?.text!!.isNotEmpty()) {
                CommonMethods.showProgress(this)
                getProvinceListForUpdateStatusFromDB()
            } else
                showAlertMessage(
                    "Please select region"
                )
        }

        bindingUpdateStatus?.rlDistrict?.setOnClickListener {
            if (provinceListArray?.size!! > 0 && bindingUpdateStatus?.etNewProvince?.text!!.isNotEmpty()) {
                CommonMethods.showProgress(this)
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    getDistrictListForUpdateStatusFromDB()
                } else {
                    if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                        getDistrictListForUpdateStatusFromDB()
                    } else {
                        getMKADistrictListFromDB()
                    }
                }
            } else {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_MKA && filterType == ApiConstants.MKA_GEO_STRUCTURE
                ) {
                    getMKADistrictListFromDB()
                } else {
                    showAlertMessage(
                        "Please select province"
                    )
                }
            }

        }

        bindingUpdateStatus?.rlLLG?.setOnClickListener {
            if (districtListArray?.size!! > 0 && bindingUpdateStatus?.etDistrict?.text!!.isNotEmpty()) {
                CommonMethods.showProgress(this)
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    getLLGListForUpdateStatusFromDB()
                } else {
                    if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                        getLLGListForUpdateStatusFromDB()
                    } else {
                        getMKALlgListFromDB()
                    }
                }
            } else {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_MKA && filterType == ApiConstants.MKA_GEO_STRUCTURE
                ) {
                    getMKALlgListFromDB()
                } else {
                    showAlertMessage(
                        "Please select district"
                    )
                }
            }
        }

        bindingUpdateStatus?.rlWard?.setOnClickListener {
            if (llgListArray?.size!! > 0 && bindingUpdateStatus?.etNewLLG?.text!!.isNotEmpty()) {
                CommonMethods.showProgress(this)
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    getWardNoAndNameListListForUpdateStatusFromDB()
                } else {
                    if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                        getWardNoAndNameListListForUpdateStatusFromDB()
                    } else {
                        getMKAWardList()
                    }
                }
            } else {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_MKA && filterType == ApiConstants.MKA_GEO_STRUCTURE
                ) {
                    getMKAWardList()
                } else {
                    showAlertMessage(
                        "Please select llg"
                    )
                }
            }
        }

        bindingUpdateStatus?.rlLocation?.setOnClickListener {
            if (wardNoAndNameArray?.size!! > 0 && bindingUpdateStatus?.etWard?.text!!.isNotEmpty()) {
                CommonMethods.showProgress(this)
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    getLocationListListForUpdateStatusFromDB()
                } else {
                    if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                        getLocationListListForUpdateStatusFromDB()
                    } else {
                        getMKALocationList()
                    }
                }
            } else {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_MKA && filterType == ApiConstants.MKA_GEO_STRUCTURE
                ) {
                    getMKALocationList()
                } else {
                    showAlertMessage(
                        "Please select ward"
                    )
                }
            }
        }

    }

    private fun getRegionListFromDB() {
        launch(Dispatchers.Main) {
            val result: List<RegionList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.regionListDao()?.all
            }
            showRegionOptionsForUpdateStatus(result)
        }
    }

    private var regionListArray: List<RegionList>? = ArrayList()
    private fun showRegionOptionsForUpdateStatus(result: List<RegionList>?) {
        regionListArray = result
        showSingleSelectionDialogForUpdateStatus(
            CommonMethods.getRegionList(ArrayList(result!!)),
            bindingUpdateStatus?.etRegion!!,
            "Region"
        )
    }


    private fun getProvinceListForUpdateStatusFromDB() {
        launch(Dispatchers.Main) {
            val result: List<ProvinceList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.provinceListDao()
                    ?.getProvinceOfRegion(regionListArray!![bindingUpdateStatus?.etRegion?.tag as Int].regionId)
            }
            showProvinceOptionsForUpdateStatus(result)
        }
    }

    var provinceListArray: ArrayList<ProvinceList>? = ArrayList()
    private fun showProvinceOptionsForUpdateStatus(result: List<ProvinceList>?) {
        provinceListArray = ArrayList(result!!)
        showSingleSelectionDialogForUpdateStatus(
            CommonMethods.getProvinceList(provinceListArray!!),
            bindingUpdateStatus?.etNewProvince!!,
            "Province"
        )

    }


    private fun getDistrictListForUpdateStatusFromDB() {
        launch(Dispatchers.Main) {
            val result: List<DistrictList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.districtListDao()
                    ?.getDistrictOfRegionAndProvision(
                        regionListArray!![bindingUpdateStatus?.etRegion?.tag as Int].regionId,
                        provinceListArray!![bindingUpdateStatus?.etNewProvince?.tag as Int].provinceId
                    )
            }
            showDistrictOptionsForUpdateStatus(result)
        }
    }

    private fun getMKADistrictListFromDB() {
        mkaDistrictArray.clear()
        launch(Dispatchers.Main) {
            val result: List<MKAGeoListData> = withContext(Dispatchers.IO) {
                dataBaseInstance.geoListDao()?.all
            } ?: kotlin.run { arrayListOf() }
            mkaDistrictArray = ArrayList<MKAGeoListData>(result)
            showMKADistrict(mkaDistrictArray)
        }
    }

    var districtListArray: ArrayList<DistrictList>? = ArrayList()
    private fun showDistrictOptionsForUpdateStatus(result: List<DistrictList>?) {
        districtListArray = ArrayList(result!!)
        showSingleSelectionDialogForUpdateStatus(
            CommonMethods.getDistrictList(districtListArray!!),
            bindingUpdateStatus?.etDistrict!!,
            "District"
        )
    }

    private fun showMKADistrict(distListArray: ArrayList<MKAGeoListData>) {
        CommonMethods.hideProgress()
        DialogSpinner.with(this, ArrayList(distListArray)).setEnableSearch(false)
            .setMap { value: MKAGeoListData -> value.districtName ?: "" }
            .setOnValueSelectedCallback { model ->
                bindingUpdateStatus?.etDistrict?.text = model.districtName ?: ""
                districtId = model.districtId ?: 0
                llgId = 0
                mkaLlgArray.clear()
                bindingUpdateStatus?.etNewLLG?.text = ""
                wardId = 0
                mkaWardArray.clear()
                bindingUpdateStatus?.etWard?.text = ""
                locationId = 0
                mkaLocationArray.clear()
                bindingUpdateStatus?.etNewLocation?.text = ""
            }.build().show(fragmentManager = supportFragmentManager)
    }

    private fun getLLGListForUpdateStatusFromDB() {
        launch(Dispatchers.Main) {
            val result: List<LLGList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.llgListDao()
                    ?.getLLGOfRegionAndProvisionAndDistrict(
                        regionListArray!![bindingUpdateStatus?.etRegion?.tag as Int].regionId,
                        provinceListArray!![bindingUpdateStatus?.etNewProvince?.tag as Int].provinceId,
                        districtListArray!![bindingUpdateStatus?.etDistrict?.tag as Int].districtId
                    )
            }
            showLLGOptionsForUpdateStatus(result)
        }
    }

    private fun getMKALlgListFromDB() {
        mkaLlgArray.clear()
        launch(Dispatchers.Main) {
            val result: MKAGeoListData? = withContext(Dispatchers.IO) {
                dataBaseInstance.geoListDao()
                    ?.getDataForDistrict(districtId)
            }
            mkaLlgArray = Gson().fromJson<ArrayList<MKAGeoListModel.GeoListData.LlG>>(
                result?.llGs ?: "",
                object : TypeToken<ArrayList<MKAGeoListModel.GeoListData.LlG>?>() {}.type
            ) ?: kotlin.run { arrayListOf() }
            showMKALlg(mkaLlgArray)
        }
    }

    var llgListArray: ArrayList<LLGList>? = ArrayList()
    private fun showLLGOptionsForUpdateStatus(result: List<LLGList>?) {
        llgListArray = ArrayList(result!!)
        showSingleSelectionDialogForUpdateStatus(
            CommonMethods.getLLGList(llgListArray!!),
            bindingUpdateStatus?.etNewLLG!!,
            "LLG"
        )
    }

    private fun showMKALlg(llgListArray: ArrayList<MKAGeoListModel.GeoListData.LlG>) {
        CommonMethods.hideProgress()
        DialogSpinner.with(this, ArrayList(llgListArray)).setEnableSearch(false)
            .setMap { value: MKAGeoListModel.GeoListData.LlG -> value.llgName ?: "" }
            .setOnValueSelectedCallback { model ->
                bindingUpdateStatus?.etNewLLG?.text = model.llgName ?: ""
                llgId = model.llgId ?: 0
                wardId = 0
                mkaWardArray.clear()
                bindingUpdateStatus?.etWard?.text = ""
                locationId = 0
                mkaLocationArray.clear()
                bindingUpdateStatus?.etNewLocation?.text = ""
            }.build().show(fragmentManager = supportFragmentManager)
    }

    var wardNoAndNameArray: ArrayList<WardList>? = ArrayList()
    private fun getWardNoAndNameListListForUpdateStatusFromDB() {
        launch(Dispatchers.Main) {
            val result: List<WardList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.wardListDao()
                    ?.getWardListOfRegionAndProvinceAndDistrictAndLLG(
                        regionListArray!![bindingUpdateStatus?.etRegion?.tag as Int].regionId,
                        provinceListArray!![bindingUpdateStatus?.etNewProvince?.tag as Int].provinceId,
                        districtListArray!![bindingUpdateStatus?.etDistrict?.tag as Int].districtId,
                        llgListArray!![bindingUpdateStatus?.etNewLLG?.tag as Int].llgId
                    )
            }
            showWardNoAndNameOptionsForUpdateStatus(result)
        }
    }

    private fun getMKAWardList() {
        mkaWardArray.clear()
        launch(Dispatchers.Main) {
            val result = mkaLlgArray.filter { llg ->
                llg.llgId == llgId
            }
            if (result.isNotEmpty())
                mkaWardArray.addAll(result.single().wards ?: arrayListOf())
            showMKAWard(mkaWardArray)
        }
    }

    private fun showWardNoAndNameOptionsForUpdateStatus(result: List<WardList>?) {
        wardNoAndNameArray = ArrayList(result!!)
        showSingleSelectionDialogForUpdateStatus(
            CommonMethods.getWardList(wardNoAndNameArray!!),
            bindingUpdateStatus?.etWard!!,
            "Ward"
        )
    }

    private fun showMKAWard(wardListArray: ArrayList<MKAGeoListModel.GeoListData.LlG.Ward>) {
        CommonMethods.hideProgress()
        DialogSpinner.with(this, ArrayList(wardListArray)).setEnableSearch(false)
            .setMap { value: MKAGeoListModel.GeoListData.LlG.Ward -> value.wardName ?: "" }
            .setOnValueSelectedCallback { model ->
                bindingUpdateStatus?.etWard?.text = model.wardName ?: ""
                wardId = model.wardId ?: 0
                locationId = 0
                mkaLocationArray.clear()
                bindingUpdateStatus?.etNewLocation?.text = ""
            }.build().show(fragmentManager = supportFragmentManager)
    }


    private fun getLocationListListForUpdateStatusFromDB() {
        launch(Dispatchers.Main) {
            val result: List<LocationList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.locationListDao()
                    ?.getLocationListOfRegionAndProvinceAndDistrictAndLLGAndWard(
                        regionListArray!![bindingUpdateStatus?.etRegion?.tag as Int].regionId,
                        provinceListArray!![bindingUpdateStatus?.etNewProvince?.tag as Int].provinceId,
                        districtListArray!![bindingUpdateStatus?.etDistrict?.tag as Int].districtId,
                        llgListArray!![bindingUpdateStatus?.etNewLLG?.tag as Int].llgId,
                        wardNoAndNameArray!![bindingUpdateStatus?.etWard?.tag as Int].wardId
                    )
            }
            showLocationOptionsForUpdateStatus(result)
        }
    }

    private fun getMKALocationList() {
        mkaLocationArray.clear()
        launch(Dispatchers.Main) {
            val result = mkaWardArray.filter { llg ->
                llg.wardId == wardId
            }
            if (result.isNotEmpty())
                mkaLocationArray.addAll(result.single().locations ?: arrayListOf())
            showMKALocation(mkaLocationArray)
        }
    }

    private var locationArray: ArrayList<LocationList>? = ArrayList()
    private fun showLocationOptionsForUpdateStatus(result: List<LocationList>?) {
        locationArray = ArrayList(result!!)
        showSingleSelectionDialogForUpdateStatus(
            CommonMethods.getLocationList(locationArray!!),
            bindingUpdateStatus?.etNewLocation!!,
            "Location"
        )
    }

    private fun showMKALocation(locationListArray: ArrayList<MKAGeoListModel.GeoListData.LlG.Ward.Location>) {
        CommonMethods.hideProgress()
        DialogSpinner.with(this, ArrayList(locationListArray)).setEnableSearch(false)
            .setMap { value: MKAGeoListModel.GeoListData.LlG.Ward.Location ->
                value.locationName ?: ""
            }
            .setOnValueSelectedCallback { model ->
                bindingUpdateStatus?.etNewLocation?.text = model.locationName ?: ""
                locationId = model.locationId ?: 0
            }.build().show(fragmentManager = supportFragmentManager)
    }

    private fun showSingleSelectionDialogForUpdateStatus(
        singleChoiceItems: Array<String>,
        tvSelection: AppCompatTextView,
        selectedField: String
    ) {

        CommonMethods.hideProgress()
        if (singleChoiceItems.isNotEmpty()) {
            val selectionValue =
                if (tvSelection.tag == null) -1 else tvSelection.tag as Int

            val mBuilder =
                AlertDialog.Builder(this)

            mBuilder.setSingleChoiceItems(
                singleChoiceItems,
                selectionValue
            ) { dialogInterface, clickItem ->
                tvSelection.tag = clickItem
                tvSelection.text = singleChoiceItems[clickItem]

                when (selectedField) {
                    "Region" -> {

                        bindingUpdateStatus?.etNewProvince?.tag = -1
                        bindingUpdateStatus?.etNewProvince?.text = ""

                        bindingUpdateStatus?.etDistrict?.tag = -1
                        bindingUpdateStatus?.etDistrict?.text = ""

                        bindingUpdateStatus?.etNewLLG?.tag = -1
                        bindingUpdateStatus?.etNewLLG?.text = ""

                        bindingUpdateStatus?.etWard?.tag = -1
                        bindingUpdateStatus?.etWard?.text = ""

                        bindingUpdateStatus?.etNewLocation?.tag = -1
                        bindingUpdateStatus?.etNewLocation?.text = ""

                        provinceListArray?.clear()
                        districtListArray?.clear()
                        llgListArray?.clear()
                        wardNoAndNameArray?.clear()
                        locationArray?.clear()


                    }

                    "Province" -> {

                        bindingUpdateStatus?.etDistrict?.tag = -1
                        bindingUpdateStatus?.etDistrict?.text = ""

                        bindingUpdateStatus?.etNewLLG?.tag = -1
                        bindingUpdateStatus?.etNewLLG?.text = ""

                        bindingUpdateStatus?.etWard?.tag = -1
                        bindingUpdateStatus?.etWard?.text = ""

                        bindingUpdateStatus?.etNewLocation?.tag = -1
                        bindingUpdateStatus?.etNewLocation?.text = ""

                        districtListArray?.clear()
                        llgListArray?.clear()
                        wardNoAndNameArray?.clear()
                        locationArray?.clear()

                        bindingUpdateStatus?.etComment!!.setText("TRF OUT {" + singleChoiceItems[clickItem] + "}")

                    }

                    "District" -> {

                        bindingUpdateStatus?.etNewLLG?.tag = -1
                        bindingUpdateStatus?.etNewLLG?.text = ""

                        bindingUpdateStatus?.etWard?.tag = -1
                        bindingUpdateStatus?.etWard?.text = ""

                        bindingUpdateStatus?.etNewLocation?.tag = -1
                        bindingUpdateStatus?.etNewLocation?.text = ""

                        llgListArray?.clear()
                        wardNoAndNameArray?.clear()
                        locationArray?.clear()

                    }

                    "LLG" -> {

                        bindingUpdateStatus?.etWard?.tag = -1
                        bindingUpdateStatus?.etWard?.text = ""

                        bindingUpdateStatus?.etNewLocation?.tag = -1
                        bindingUpdateStatus?.etNewLocation?.text = ""

                        wardNoAndNameArray?.clear()
                        locationArray?.clear()

                    }

                    "Ward" -> {

                        bindingUpdateStatus?.etNewLocation?.tag = -1
                        bindingUpdateStatus?.etNewLocation?.text = ""

                        locationArray?.clear()
                    }
                }

                dialogInterface.dismiss()
            }
            val mDialog = mBuilder.create()
            mDialog.show()
        } else {
            showAlertMessage("No items found")
        }
    }


}
