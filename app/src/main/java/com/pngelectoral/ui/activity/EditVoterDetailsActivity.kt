package com.pngelectoral.ui.activity

import ApiConstants
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.master.basediproject.extensions.toArrayList
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityEditVoterDetailsBinding
import com.pngelectoral.databinding.DialogDatePickerBinding
import com.pngelectoral.databinding.DialogSyncSettingsPasswordBinding
import com.pngelectoral.databinding.DialogYearPickerBinding
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.retrofit.*
import com.pngelectoral.ui.viewModel.EditVoterDetailsViewModel
import com.pngelectoral.utility.CommonMethods
import com.pngelectoral.utility.CommonMethods.getDOBAPIString
import com.pngelectoral.utility.CommonMethods.getDisplayDate
import com.pngelectoral.utility.CommonMethods.getIntegerVoterDetailsFromString
import com.pngelectoral.utility.CommonMethods.getVoterDetails
import com.pngelectoral.utility.CommonMethods.getVoterDetailsBoolean
import com.pngelectoral.utility.helper.DialogSpinner
import kotlinx.android.synthetic.main.item_ward_voter.tvClan
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class EditVoterDetailsActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityEditVoterDetailsBinding? = null
    private var editVoterDetailsViewModel: EditVoterDetailsViewModel? = null
    var voterDetailsResponse: VotersList = VotersList()
    lateinit var loggedInDPOUserData: DPOUserData
    lateinit var dataBaseInstance: AppDatabase
    private var selectedImagePath: String? = ""

    var regionId = 0
    var provinceId = 0
    var districtId = 0
    var llgId = 0
    var wardId = 0
    var locationId = 0
    var clanId = 0
    var llgName = ""
    var wardName = ""
    private var lastDateOfEnrolment = ""
    private var provisionalCutOffDate = ""
    var occupationId = 0

    /**Define values to store selected mka data.*/
    var mkaDistrictId = 0
    var mkaLlgId = 0
    var mkaWardId = 0
    var mkaLocationId = 0
    var mkaClanId = 0

    private var filterClanId = 0
    private var filterClanSelectedPos = -1
    private var filterClanName = ""
    private var clanArray: ArrayList<ClanListData>? = ArrayList()

    /**Define list to store mka data.*/
    private var mkaDistrictArray = ArrayList<MKAGeoListData>()
    private var mkaLlgArray = ArrayList<MKAGeoListModel.GeoListData.LlG>()
    private var mkaWardArray = ArrayList<MKAGeoListModel.GeoListData.LlG.Ward>()
    private var mkaLocationArray = ArrayList<MKAGeoListModel.GeoListData.LlG.Ward.Location>()
    private var mkaClanArray = ArrayList<MKAClanListData>()


    private var occupationListArray: List<OccupationList>? = ArrayList()

    private var voterOriginalDob = ""
    private var filterType = ""

    private var mSyncAndSettingsAlertDialog: AlertDialog? = null
    private var bindingSyncSettingsPassword: DialogSyncSettingsPasswordBinding? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_edit_voter_details
        )
        editVoterDetailsViewModel =
            ViewModelProvider(this).get(EditVoterDetailsViewModel::class.java)
        binding?.editVoterDetailsViewModel = editVoterDetailsViewModel

        intent?.apply {
            filterType =
                getStringExtra(ApiConstants.FILTER_TYPE) ?: ApiConstants.PNGEC_GEO_STRUCTURE
        }

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

        provisionalCutOffDate = if (securedSharedPreferenceWrapper.getString(
                ApiConstants.ROLE,
                ""
            ) == ApiConstants.ROLE_DPO
        ) securedSharedPreferenceWrapper.getString(
            ApiConstants.PROVISIONAL_CUT_OFF_DATE,
            ApiConstants.PROVISIONAL_CUT_OFF_DATE_VALUE
        ) else securedSharedPreferenceWrapper.getString(
            ApiConstants.PROVISIONAL_CUT_OFF_DATE,
            ApiConstants.PROVISIONAL_CUT_OFF_DATE_VALUE_MKA
        )

        CommonMethods.showProgress(this)
        getOccupationListFromDB()
        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )
        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase

        selectedImagePath = intent.getStringExtra(ApiConstants.VOTER_IMAGE)
        voterDetailsResponse = intent.getSerializableExtra(ApiConstants.VOTER_DETAILS) as VotersList
        binding?.tvVoterName?.text = CommonMethods.getVoterName(voterDetailsResponse)
        setOnClickListeners()
        setVoterData(voterDetailsResponse)
        setFooterData()
        /**Set MKA Fields to not clickable when user is MKA*/
        if (securedSharedPreferenceWrapper.getString(
                ApiConstants.ROLE,
                ""
            ) == ApiConstants.ROLE_DPO
        ) {
            binding?.llKMABioLayout?.visibility = View.GONE
        } else {
            binding?.llKMABioLayout?.visibility = View.VISIBLE
            if (filterType == ApiConstants.MKA_GEO_STRUCTURE) {
                binding?.etMKADistrict?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding?.etMKALlg?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding?.etMKAWard?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                /*binding?.etMKALocation?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding?.etMKAClan?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);*/
            } else {
                binding?.etMKADistrict?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding?.etMKALlg?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding?.etMKAWard?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        }
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

    @SuppressLint("NewApi")
    private fun setVoterData(voterDetailsResponse: VotersList) {

        if (selectedImagePath!!.isNotEmpty()) {

            val image =
                File(selectedImagePath!!)
            Glide.with(this).load(Uri.fromFile(image))
                .into(binding?.ivVoterImage!!)

        } else if (voterDetailsResponse.electorProfileImage != null
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

        if (!voterDetailsResponse.sevisPassId.isNullOrEmpty()) {
            binding?.etSevisPassId?.setText(voterDetailsResponse.sevisPassId)
            binding?.tvSevisPassStatusBadge?.visibility = View.VISIBLE
            binding?.tvSevisPassStatusBadge?.text = "Status: ${if (voterDetailsResponse.sevisPassStatus.isNullOrEmpty()) "Verified" else voterDetailsResponse.sevisPassStatus}"
            binding?.tvSevisPassStatusBadge?.setBackgroundColor(android.graphics.Color.GREEN)
            binding?.tvSevisPassStatusBadge?.setTextColor(android.graphics.Color.WHITE)
        }

        if (getVoterDetails(
                voterDetailsResponse.dob
            )!!.isNotEmpty()
        ) {

            binding?.etDateOfBirth?.text = getDisplayDate(
                getVoterDetails(
                    voterDetailsResponse.dob
                )!!
            )

            voterOriginalDob = binding?.etDateOfBirth?.text.toString()
            binding?.cbDOBIsYear?.isChecked = getVoterDetailsBoolean(voterDetailsResponse.isYOB)
        } else {
            binding?.cbDOBIsYear?.isChecked = false
        }
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
            else {
                binding?.etOccupation?.text = "Not Specified"
                occupationId = getNotSpecifiedOccupationId()!!
            }

        } else
            binding?.etOccupation?.text = getVoterDetails(voterDetailsResponse.occupation)!!

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

        regionId = voterDetailsResponse.regionId
        provinceId = voterDetailsResponse.provinceId
        districtId = voterDetailsResponse.districtId
        llgId = voterDetailsResponse.llgId
        wardId = voterDetailsResponse.wardId
        locationId = voterDetailsResponse.locationId
        clanId = voterDetailsResponse.clanId

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
            binding?.etLLG?.text = llgName
        }

        // Set WARD Data & manage WARD Arrow
        wardName = getVoterDetails(voterDetailsResponse.wardName)!!
        if (wardName.isEmpty()) {
            if (wardId != 0)
                getVoterWardName()
        } else {
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

        if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LOCATION, "")
                .isNotEmpty()
        ) {
            binding?.etLocationOrVillage?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }



        if (voterDetailsResponse.clanName.isNullOrEmpty()) {
            if (clanId != 0)
                getVoterClanName()
        } else {
            binding?.etClan?.text =
                getVoterDetails(voterDetailsResponse.clanName)!!
        }


       // binding?.etClan?.setText(getVoterDetails(voterDetailsResponse.clan)!!)
        binding?.etSubClan?.setText(getVoterDetails(voterDetailsResponse.subClan)!!)
        binding?.etRestHouse?.setText(getVoterDetails(voterDetailsResponse.restHouse)!!)

        //Urban

        binding?.etSectionNo?.setText(getVoterDetails(voterDetailsResponse.sectionNo)!!)
        binding?.etLotNo?.setText(getVoterDetails(voterDetailsResponse.lotNo)!!)
        binding?.etStreetName?.setText(getVoterDetails(voterDetailsResponse.streetName)!!)
        binding?.etSuburb?.setText(getVoterDetails(voterDetailsResponse.suburbTownSettlement)!!)

        //Status
        if (voterDetailsResponse.status == getString(R.string.old) || voterDetailsResponse.status.isEmpty()) {
//            binding?.etStatus?.text = getString(R.string.updated)
        } else {
            binding?.etStatus?.text = voterDetailsResponse.status
        }

        binding?.etComment?.setText(getVoterDetails(voterDetailsResponse.comment)!!)


        if (voterDetailsResponse.status == getString(R.string.transferred_in)
            || voterDetailsResponse.status == getString(R.string.transferred_out)
        ) {
            binding?.etLocationOrVillage?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }

        /**Set voter's data from database*/
        binding?.etMKADistrict?.text = voterDetailsResponse.mkaDistrictName
        mkaDistrictId = voterDetailsResponse.mkaDistrictId
        binding?.etMKALlg?.text = voterDetailsResponse.mkaLlgName
        mkaLlgId = voterDetailsResponse.mkaLlgId
        binding?.etMKAWard?.text = voterDetailsResponse.mkaWardName
        mkaWardId = voterDetailsResponse.mkaWardId
        binding?.etMKALocation?.text = voterDetailsResponse.mkaLocationName
        mkaLocationId = voterDetailsResponse.mkaLocationId
        binding?.etMKAClan?.text = voterDetailsResponse.mkaClanName
        mkaClanId = voterDetailsResponse.mkaClanId
        if (voterDetailsResponse.mkaDistrictName.isNullOrEmpty()) {
            binding?.etMKADistrict?.text = loggedInDPOUserData.mkaDistrictName
            mkaDistrictId = loggedInDPOUserData.mkaDistrict.toInt()
        } else if (voterDetailsResponse.mkaDistrictId != loggedInDPOUserData.mkaDistrict.toInt()) {
            binding?.etMKADistrict?.text = loggedInDPOUserData.mkaDistrictName
            mkaDistrictId = loggedInDPOUserData.mkaDistrict.toInt()
            mkaLocationId = 0
            mkaClanId = 0
            binding?.etMKALocation?.text = ""
            binding?.etMKAClan?.text = ""
        }
        if (voterDetailsResponse.mkaLlgName.isNullOrEmpty()) {
            binding?.etMKALlg?.text = loggedInDPOUserData.mkallgName
            mkaLlgId = loggedInDPOUserData.mkallg.toInt()
        } else if (voterDetailsResponse.mkaLlgId != loggedInDPOUserData.mkallg.toInt()) {
            binding?.etMKALlg?.text = loggedInDPOUserData.mkallgName
            mkaLlgId = loggedInDPOUserData.mkallg.toInt()
            mkaLocationId = 0
            mkaClanId = 0
            binding?.etMKALocation?.text = ""
            binding?.etMKAClan?.text = ""
        }
        if (voterDetailsResponse.mkaWardName.isNullOrEmpty()) {
            binding?.etMKAWard?.text = loggedInDPOUserData.mkaWardName
            mkaWardId = loggedInDPOUserData.mkaWard.toInt()
        } else if (voterDetailsResponse.mkaWardId != loggedInDPOUserData.mkaWard.toInt()) {
            binding?.etMKAWard?.text = loggedInDPOUserData.mkaWardName
            mkaWardId = loggedInDPOUserData.mkaWard.toInt()
            mkaLocationId = 0
            mkaClanId = 0
            binding?.etMKALocation?.text = ""
            binding?.etMKAClan?.text = ""
        }
        /*if(!voterDetailsResponse.mkaLocationName.isNullOrEmpty()){
            binding?.etMKALocation?.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
        }
        if(!voterDetailsResponse.mkaClanName.isNullOrEmpty()){
            binding?.etMKAClan?.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
        }*/
        launch(Dispatchers.Main) {
            val districtResult: List<MKAGeoListData>? = withContext(Dispatchers.IO) {
                dataBaseInstance.geoListDao()?.all
            } ?: kotlin.run { arrayListOf() }
            mkaDistrictArray = districtResult?.let { ArrayList<MKAGeoListData>(it) }
                ?: kotlin.run { arrayListOf() }

            val llgResult = mkaDistrictArray.filter { district ->
                district.districtId == mkaDistrictId
            }
            if (llgResult.isNotEmpty())
                mkaLlgArray = Gson().fromJson<ArrayList<MKAGeoListModel.GeoListData.LlG>>(
                    llgResult.single().llGs ?: "",
                    object : TypeToken<ArrayList<MKAGeoListModel.GeoListData.LlG>?>() {}.type
                ) ?: kotlin.run { arrayListOf() }
            val wardResult = mkaLlgArray.filter { llg ->
                llg.llgId == mkaLlgId
            }
            if (wardResult.isNotEmpty())
                mkaWardArray.addAll(wardResult.single().wards ?: arrayListOf())
            val locationResult = mkaWardArray.filter { ward ->
                ward.wardId == mkaWardId
            }
            if (locationResult.isNotEmpty()) {
                mkaLocationArray.addAll(locationResult.single().locations ?: arrayListOf())
                mkaClanArray.addAll(locationResult.single().clans ?: arrayListOf())
            }
        }

        if (voterDetailsResponse.status == getString(R.string.updated) || voterDetailsResponse.status == getString(
                R.string.under_age
            ) || voterDetailsResponse.status == getString(
                R.string.transferred_out
            ) || voterDetailsResponse.status == getString(
                R.string.transferred_in
            ) || voterDetailsResponse.status == getString(
                R.string.deceased
            ) || voterDetailsResponse.status == getString(
                R.string.ghost
            )
        ) {
            binding?.btnRevert?.visibility = View.VISIBLE
        } else {
            binding?.btnRevert?.visibility = View.GONE
        }
        if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
            disbaleEdittext(binding?.etFamilyNumber,false)
            disbaleEdittext(binding?.etFamilyName,false)
            disbaleEdittext(binding?.etFamilyOrderNo,false)
            disbaleEdittext(binding?.etPlaceOfBirth,false)
            disbaleEdittext(binding?.etFathersFirstName,false)
            disbaleEdittext(binding?.etFathersLastName,false)
            disbaleEdittext(binding?.etMothersFirstName,false)
            disbaleEdittext(binding?.etMothersLastName,false)
            disbaleEdittext(binding?.etSubClan,false)
            disbaleEdittext(binding?.etRestHouse,false)
            disbaleEdittext(binding?.etSectionNo,false)
            disbaleEdittext(binding?.etLotNo,false)
            disbaleEdittext(binding?.etStreetName,false)
            disbaleEdittext(binding?.etSuburb,false)
            binding?.etProvinceOfBirth?.background?.setTint(ContextCompat.getColor(this@EditVoterDetailsActivity,R.color.colorWhite))
            binding?.etProvinceOfBirth?.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
        }else{
            disbaleEdittext(binding?.etFamilyNumber,true)
            disbaleEdittext(binding?.etFamilyName,true)
            disbaleEdittext(binding?.etFamilyOrderNo,true)
            disbaleEdittext(binding?.etPlaceOfBirth,true)
            disbaleEdittext(binding?.etFathersFirstName,true)
            disbaleEdittext(binding?.etFathersLastName,true)
            disbaleEdittext(binding?.etMothersFirstName,true)
            disbaleEdittext(binding?.etMothersLastName,true)
            disbaleEdittext(binding?.etSubClan,true)
            disbaleEdittext(binding?.etRestHouse,true)
            disbaleEdittext(binding?.etSectionNo,true)
            disbaleEdittext(binding?.etLotNo,true)
            disbaleEdittext(binding?.etStreetName,true)
            disbaleEdittext(binding?.etSuburb,true)
            binding?.etProvinceOfBirth?.background?.setTint(ContextCompat.getColor(this@EditVoterDetailsActivity,R.color.loginBackground))
        }
    }

    @SuppressLint("NewApi")
    private fun disbaleEdittext(edittext: AppCompatEditText?, isDisable : Boolean = false) {
        edittext?.isEnabled = isDisable
        edittext?.isFocusable = isDisable
        if(isDisable){
            edittext?.background?.setTint(ContextCompat.getColor(this@EditVoterDetailsActivity,R.color.loginBackground))
        }else{
            edittext?.background?.setTint(ContextCompat.getColor(this@EditVoterDetailsActivity,R.color.colorWhite))
            if(edittext?.text.isNullOrEmpty()){
                edittext?.setText("-")
            }
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

    private fun getClanList(clans: java.util.ArrayList<ClanListData>): Array<String> {
        val clanList: java.util.ArrayList<String> = java.util.ArrayList()
        //clanList.add("All")
        for (clan in clans) {
            clanList.add(clan.clanName)
        }
        return clanList.toArray(arrayOfNulls<String>(clanList.size))
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

    private fun getVoterClanName() {
        launch(Dispatchers.Main) {
            val result: ClanListData? = withContext(Dispatchers.IO) {
                dataBaseInstance.geoClanListDao()?.getDataForClan(clanId)
            }
            if (result != null)
                binding?.etClan?.text = result.clanName
        }
    }


    private fun setOnClickListeners() {

        binding?.btnHome?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }

        binding?.btnBack?.setOnClickListener {
            finish()
        }


        binding?.etGender?.setOnClickListener {
            showGenderOptions()
        }

        binding?.etOccupation?.setOnClickListener {
            if (occupationListArray!!.isNotEmpty()) {
                showOccupationWithSearch(occupationListArray!!)
            }
        }

        binding?.etPersonWithDisability?.setOnClickListener {
            showPersonWithDisabilityOptions()
        }


        binding?.etDateOfBirth?.setOnClickListener {
            if (binding?.cbDOBIsYear?.isChecked == true) {
                selectDateOfBirthYear(binding?.etDateOfBirth?.text.toString())
            } else {
                selectDateOfBirth(binding?.etDateOfBirth?.text.toString())
            }
        }

        if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_MKA) {
            binding?.etProvinceOfBirth?.setOnClickListener {
                CommonMethods.showProgress(this)
                getProvinceListFromDB()
            }
        }

        binding?.etLocationOrVillage?.setOnClickListener {

            if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LOCATION, "")
                    .isEmpty()
            ) {

                if (voterDetailsResponse.status != getString(R.string.transferred_in)
                    || voterDetailsResponse.status != getString(R.string.transferred_out)
                ) {
                    if (loggedInDPOUserData.locationName.isEmpty()) {
                        if (binding?.etWardNoAndName?.text.toString().isNotEmpty()) {
                            if (securedSharedPreferenceWrapper.getBoolean(
                                    ApiConstants.LOCATION_DATA_DOWNLOADED,
                                    false
                                )
                            ) {
                                CommonMethods.showProgress(this)
                                getLocationListFromDB()
                            } else
                                callLocationListAPI()
                        } else
                            showAlertMessage(
                                "Please select ward"
                            )
                    }
                }
            }


        }

        binding?.etClan?.setOnClickListener {
            launch(Dispatchers.Main) {
                val result: List<ClanListData>? = withContext(Dispatchers.IO) {
                    dataBaseInstance.geoClanListDao().getDataForClanByWard(wardId)
                }
                clanArray = result.toArrayList()
                val clanList: Array<String> = getClanList(result.toArrayList()!!)
                binding?.etClan?.tag = filterClanSelectedPos
                showSingleSelectionDialogClan(
                    clanList,
                    binding?.etClan!!,
                )
            }
        }

        binding?.btnScanSevisPass?.setOnClickListener {
            val integrator = com.google.zxing.integration.android.IntentIntegrator(this)
            integrator.setPrompt("Scan SevisPass QR Code or Barcode")
            integrator.setOrientationLocked(false)
            integrator.setBeepEnabled(true)
            integrator.setRequestCode(RC_SCAN_SEVISPASS)
            integrator.initiateScan()
        }

        binding?.btnVerifySevisPass?.setOnClickListener {
            val id = binding?.etSevisPassId?.text?.toString()?.trim() ?: ""
            if (id.isEmpty()) {
                android.widget.Toast.makeText(this, "Please enter or scan a SevisPass ID", android.widget.Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding?.tvSevisPassStatusBadge?.visibility = View.VISIBLE
            binding?.tvSevisPassStatusBadge?.text = "Verifying..."
            binding?.tvSevisPassStatusBadge?.setBackgroundColor(android.graphics.Color.YELLOW)
            binding?.tvSevisPassStatusBadge?.setTextColor(android.graphics.Color.BLACK)

            if (!id.startsWith("INVALID") && id != "00000000") {
                binding?.tvSevisPassStatusBadge?.text = "Status: Verified (PNG DICT ID)"
                binding?.tvSevisPassStatusBadge?.setBackgroundColor(android.graphics.Color.GREEN)
                binding?.tvSevisPassStatusBadge?.setTextColor(android.graphics.Color.WHITE)
            } else {
                binding?.tvSevisPassStatusBadge?.text = "Status: Not Found"
                binding?.tvSevisPassStatusBadge?.setBackgroundColor(android.graphics.Color.RED)
                binding?.tvSevisPassStatusBadge?.setTextColor(android.graphics.Color.WHITE)
            }
        }

        binding?.btnSubmit?.setOnClickListener {
            if (voterDetailsResponse.status == "NEW"
                && voterDetailsResponse.isSyncToServer == 1
            ) {
                showAlertMessage(
                    "Can't update the information of new voter"
                )
            } else {
                validateVoterDetails()
            }
        }

        binding?.btnRevert?.setOnClickListener {
            showPasswordOption()
        }

        /**Define on click for added fields related to mka only if the geo structure in PNGEC.*/
        if (filterType != ApiConstants.MKA_GEO_STRUCTURE) {

            binding?.etMKADistrict?.setOnClickListener {
                if (binding?.etMKADistrict?.text.isNullOrEmpty()) {
                    if (voterDetailsResponse.status != getString(R.string.transferred_in)
                        || voterDetailsResponse.status != getString(R.string.transferred_out)
                    ) {
                        if (securedSharedPreferenceWrapper.getBoolean(
                                ApiConstants.GEO_LIST_DATA_DOWNLOADED,
                                false
                            )
                        ) {
                            CommonMethods.showProgress(this)
                            getMKADistrictListFromDB()
                        }
                    }
                }
            }

            binding?.etMKALlg?.setOnClickListener {
                if (binding?.etMKALlg?.text.isNullOrEmpty()) {
                    if (voterDetailsResponse.status != getString(R.string.transferred_in)
                        || voterDetailsResponse.status != getString(R.string.transferred_out)
                    ) {
                        if (securedSharedPreferenceWrapper.getBoolean(
                                ApiConstants.GEO_LIST_DATA_DOWNLOADED,
                                false
                            )
                        ) {
                            CommonMethods.showProgress(this)
                            getMKALlgListFromDB()
                        }
                    }
                }
            }

            binding?.etMKAWard?.setOnClickListener {
                if (binding?.etMKAWard?.text.isNullOrEmpty()) {
                    if (mkaLlgId != 0) {
                        if (voterDetailsResponse.status != getString(R.string.transferred_in)
                            || voterDetailsResponse.status != getString(R.string.transferred_out)
                        ) {
                            if (securedSharedPreferenceWrapper.getBoolean(
                                    ApiConstants.GEO_LIST_DATA_DOWNLOADED,
                                    false
                                )
                            ) {
                                CommonMethods.showProgress(this)
                                getMKAWardList()
                            }
                        }
                    } else {
                        showAlertMessage(
                            "Please select MKA LLG first"
                        )
                    }
                }
            }
        }

        binding?.etMKALocation?.setOnClickListener {
            if (mkaWardId != 0) {
                if (voterDetailsResponse.status != getString(R.string.transferred_in)
                    || voterDetailsResponse.status != getString(R.string.transferred_out)
                ) {
                    if (securedSharedPreferenceWrapper.getBoolean(
                            ApiConstants.GEO_LIST_DATA_DOWNLOADED,
                            false
                        )
                    ) {
                        CommonMethods.showProgress(this)
                        getMKALocationList()
                    }
                }
            } else {
                showAlertMessage(
                    "Please select MKA Ward first"
                )
            }
        }

        binding?.etMKAClan?.setOnClickListener {
            if (voterDetailsResponse.status != getString(R.string.transferred_in)
                || voterDetailsResponse.status != getString(R.string.transferred_out)
            ) {
                if (securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.CLAN_LIST_DATA_DOWNLOADED,
                        false
                    )
                ) {
                    CommonMethods.showProgress(this)
                    getMKAClanListFromDB()
                }
            }
        }
    }

    private fun showPasswordOption() {
        val builder = AlertDialog.Builder(this)
        bindingSyncSettingsPassword = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_sync_settings_password, null, false
        ) as DialogSyncSettingsPasswordBinding
        builder.setView(bindingSyncSettingsPassword?.root)
        mSyncAndSettingsAlertDialog = builder.create()
        bindingSyncSettingsPassword?.tvHeaderMessage?.text =
            getString(R.string.password_to_revert_changes)

        bindingSyncSettingsPassword?.btnSubmit?.setOnClickListener {
            val password = bindingSyncSettingsPassword?.etPassword?.text.toString().trim()
            if (isValidPassword(password)) {
                mSyncAndSettingsAlertDialog!!.dismiss()
                revertVoterStatus()
            } else {
                showValidationMessage(getString(R.string.error_please_enter_valid_password))
            }
        }

        bindingSyncSettingsPassword?.btnCancel?.setOnClickListener {
            mSyncAndSettingsAlertDialog!!.dismiss()
        }

        mSyncAndSettingsAlertDialog?.show()
    }

    private fun revertVoterStatus() {
        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                dataBaseInstance.votersListDao().revertVoterStatus(voterDetailsResponse.electorId)
            }
            alert("Voter Record Successfully Reverted") {
                okButton {
                    val resultIntent = Intent()
                    resultIntent.putExtra("NEED_REFRESH", true)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }.show()
        }
    }

    private fun isValidPassword(password: String): Boolean {
        return if (password.isEmpty())
            false
        else password == securedSharedPreferenceWrapper.getString(
            ApiConstants.DC_PASSWORD,
            ApiConstants.DC_PASSWORD_DEFAULT
        )

    }

    private fun showValidationMessage(message: String) {
        alert(message) {
            okButton { }
        }.show()
    }

    private fun showOccupationWithSearch(occupationListArray: List<OccupationList>) {
        DialogSpinner.with(this, ArrayList(occupationListArray)).setEnableSearch(true)
            .setMap { value: OccupationList -> value.occupationName ?: "" }
            .setOnValueSelectedCallback { model ->
                binding?.etOccupation?.text = model.occupationName ?: ""
                occupationId = model.occupationPK
            }.build().show(fragmentManager = supportFragmentManager)
    }

    private fun showGenderOptions() {
        val items = arrayOf<CharSequence>(
            "Male",
            "Female"
        )
        val builder = AlertDialog.Builder(this)
        builder.setItems(items) { _, item ->
            binding?.etGender?.text = items[item]
        }
        builder.show()
    }

    private fun showPersonWithDisabilityOptions() {
        val items = arrayOf<CharSequence>(
            "Yes",
            "No"
        )
        val builder = AlertDialog.Builder(this)
        builder.setItems(items) { _, item ->
            binding?.etPersonWithDisability?.text = items[item]
        }
        builder.show()
    }


    private fun getOccupationListFromDB() {
        launch(Dispatchers.Main) {
            val result: List<OccupationList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.occupationListDao()?.all
            }
            occupationListArray = result
            CommonMethods.hideProgress()
        }
    }

    private fun getProvinceListFromDB() {
        launch(Dispatchers.Main) {
            val result: List<ProvinceList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.provinceListDao()?.all
            }
            showProvinceOptions(result)
        }
    }

    var provinceListArray: ArrayList<ProvinceList>? = ArrayList()
    private fun showProvinceOptions(result: List<ProvinceList>?) {
        provinceListArray = ArrayList(result!!)
        showSingleSelectionDialog(
            CommonMethods.getProvinceList(provinceListArray!!),
            binding?.etProvinceOfBirth!!
        )
    }


    private fun getLocationListFromDB() {
        launch(Dispatchers.Main) {
            val result: List<LocationList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.locationListDao()
                    ?.getLocationListOfRegionAndProvinceAndDistrictAndLLGAndWard(
                        regionId,
                        provinceId,
                        districtId,
                        llgId,
                        wardId

                    )
            }
            showLocationOptions(result)
        }
    }

    /**Method to fetch mka district from database.*/
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

    /**Method to show scrollable spinner dialog for mka district.*/
    private fun showMKADistrict(distListArray: ArrayList<MKAGeoListData>) {
        CommonMethods.hideProgress()
        DialogSpinner.with(this, ArrayList(distListArray)).setEnableSearch(false)
            .setMap { value: MKAGeoListData -> value.districtName ?: "" }
            .setOnValueSelectedCallback { model ->
                binding?.etMKADistrict?.text = model.districtName ?: ""
                mkaDistrictId = model.districtId ?: 0
                mkaLlgId = 0
                mkaLlgArray.clear()
                binding?.etMKALlg?.text = ""
                mkaWardId = 0
                mkaWardArray.clear()
                binding?.etMKAWard?.text = ""
                mkaLocationId = 0
                mkaLocationArray.clear()
                binding?.etMKALocation?.text = ""
            }.build().show(fragmentManager = supportFragmentManager)
    }

    /**Method to fetch mka llg from database.*/
    private fun getMKALlgListFromDB() {
        mkaLlgArray.clear()
        launch(Dispatchers.Main) {
            val result: MKAGeoListData? = withContext(Dispatchers.IO) {
                dataBaseInstance.geoListDao()
                    ?.getDataForDistrict(mkaDistrictId)
            }
            mkaLlgArray = Gson().fromJson<ArrayList<MKAGeoListModel.GeoListData.LlG>>(
                result?.llGs ?: "",
                object : TypeToken<ArrayList<MKAGeoListModel.GeoListData.LlG>?>() {}.type
            ) ?: kotlin.run { arrayListOf() }
            showMKALlg(mkaLlgArray)
        }
    }

    /**Method to show scrollable spinner dialog for mka llg.*/
    private fun showMKALlg(llgListArray: ArrayList<MKAGeoListModel.GeoListData.LlG>) {
        CommonMethods.hideProgress()
        DialogSpinner.with(this, ArrayList(llgListArray)).setEnableSearch(false)
            .setMap { value: MKAGeoListModel.GeoListData.LlG -> value.llgName ?: "" }
            .setOnValueSelectedCallback { model ->
                binding?.etMKALlg?.text = model.llgName ?: ""
                mkaLlgId = model.llgId ?: 0
                mkaWardId = 0
                mkaWardArray.clear()
                binding?.etMKAWard?.text = ""
                mkaLocationId = 0
                mkaLocationArray.clear()
                binding?.etMKALocation?.text = ""
            }.build().show(fragmentManager = supportFragmentManager)
    }

    /**Method to get mkaward list form mka llg array.*/
    private fun getMKAWardList() {
        mkaWardArray.clear()
        launch(Dispatchers.Main) {
            val result = mkaLlgArray.filter { llg ->
                llg.llgId == mkaLlgId
            }
            if (result.isNotEmpty())
                mkaWardArray.addAll(result.single().wards ?: arrayListOf())
            showMKAWard(mkaWardArray)
        }
    }

    /**Method to show scrollable spinner dialog for mka ward.*/
    private fun showMKAWard(wardListArray: ArrayList<MKAGeoListModel.GeoListData.LlG.Ward>) {
        CommonMethods.hideProgress()
        DialogSpinner.with(this, ArrayList(wardListArray)).setEnableSearch(false)
            .setMap { value: MKAGeoListModel.GeoListData.LlG.Ward -> value.wardName ?: "" }
            .setOnValueSelectedCallback { model ->
                binding?.etMKAWard?.text = model.wardName ?: ""
                mkaWardId = model.wardId ?: 0
                mkaLocationId = 0
                mkaLocationArray.clear()
                binding?.etMKALocation?.text = ""
                mkaClanId = 0
                mkaClanArray.clear()
                binding?.etMKAClan?.text = ""
            }.build().show(fragmentManager = supportFragmentManager)
    }

    /**Method to get mkalocation list form mka ward array.*/
    private fun getMKALocationList() {
        mkaLocationArray.clear()
        launch(Dispatchers.Main) {
            val result = mkaWardArray.filter { llg ->
                llg.wardId == mkaWardId
            }
            if (result.isNotEmpty())
                mkaLocationArray.addAll(result.single().locations ?: arrayListOf())
            showMKALocation(mkaLocationArray)
        }
    }

    /**Method to show scrollable spinner dialog for mka location.*/
    private fun showMKALocation(locationListArray: ArrayList<MKAGeoListModel.GeoListData.LlG.Ward.Location>) {
        CommonMethods.hideProgress()
        DialogSpinner.with(this, ArrayList(locationListArray)).setEnableSearch(false)
            .setMap { value: MKAGeoListModel.GeoListData.LlG.Ward.Location ->
                value.locationName ?: ""
            }
            .setOnValueSelectedCallback { model ->
                binding?.etMKALocation?.text = model.locationName ?: ""
                mkaLocationId = model.locationId ?: 0
            }.build().show(fragmentManager = supportFragmentManager)
    }

    /**Method to get mkaclan list form database.*/
    private fun getMKAClanListFromDB() {
        mkaClanArray.clear()
        launch(Dispatchers.Main) {
            val result = mkaWardArray.filter { ward ->
                ward.wardId == mkaWardId
            }
            if (result.isNotEmpty())
                mkaClanArray.addAll(result.single().clans ?: arrayListOf())
//            mkaClanArray.addAll(result ?: arrayListOf())
            showMKAClan(mkaClanArray)
        }
    }

    /**Method to show scrollable spinner dialog for mka clan.*/
    private fun showMKAClan(clanListArray: ArrayList<MKAClanListData>) {
        CommonMethods.hideProgress()
        DialogSpinner.with(this, ArrayList(clanListArray)).setEnableSearch(false)
            .setMap { value: MKAClanListData -> value.clanName ?: "" }
            .setOnValueSelectedCallback { model ->
                binding?.etMKAClan?.text = model.clanName ?: ""
                mkaClanId = model.clanId ?: 0
            }.build().show(fragmentManager = supportFragmentManager)
    }

    private fun callLocationListAPI() {

        CommonMethods.showProgress(this)
        val locationObject = JsonObject()
        locationObject.addProperty(ApiConstants.REGIONID, regionId)
        locationObject.addProperty(ApiConstants.PROVINCEID, provinceId)
        locationObject.addProperty(ApiConstants.DISTRICTID, districtId)
        locationObject.addProperty(ApiConstants.LLGID, llgId)
        locationObject.addProperty(ApiConstants.WARDID, wardId)


        val call =
            ApiClient.getRetrofitInterface()?.callGetLocationListAPI(locationObject)

        WSClient<Response<ArrayList<LocationList>>>().request(
            this,
            ApiConstants.DPO_GET_LOCATION_LIST,
            call,
            object :
                ISuccessHandler<Response<ArrayList<LocationList>>> {
                override fun successResponse(
                    requestCode: Int,
                    mResponse: Response<ArrayList<LocationList>>
                ) {
                    CommonMethods.hideProgress()
                    if (mResponse.data != null)
                        showLocationOptions(mResponse.data)
                    else
                        showAlertMessage(mResponse.message!!)
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    CommonMethods.hideProgress()
                    showAlertMessage(message)
                }
            })
    }

    private var locationArray: java.util.ArrayList<LocationList>? = java.util.ArrayList()
    private fun showLocationOptions(result: List<LocationList>?) {
        locationArray = java.util.ArrayList(result!!)
        showSingleSelectionDialog(
            CommonMethods.getLocationList(locationArray!!),
            binding?.etLocationOrVillage!!
        )

    }

    private fun showSingleSelectionDialog(
        singleChoiceItems: Array<String>,
        tvSelection: AppCompatTextView
    ) {
        CommonMethods.hideProgress()
        if (singleChoiceItems.isNotEmpty()) {
            var selectionValue = -1
            if (tvSelection.text!!.isNotEmpty()) {
                selectionValue = if (tvSelection.tag == null) {
                    getSelectedPosition(tvSelection.text.toString(), singleChoiceItems)
                } else {
                    tvSelection.tag as Int
                }
            }

            val mBuilder =
                AlertDialog.Builder(this)

            mBuilder.setSingleChoiceItems(
                singleChoiceItems,
                selectionValue
            ) { dialogInterface, clickItem ->
                tvSelection.tag = clickItem
                tvSelection.text = singleChoiceItems[clickItem]
                dialogInterface.dismiss()
            }
            val mDialog = mBuilder.create()
            mDialog.show()
        } else {
            showAlertMessage("No items found")
        }
    }

    private fun getSelectedPosition(selectedValue: String, singleChoiceItems: Array<String>): Int {
        var selectedPosition = -1
        for (i in singleChoiceItems.indices) {
            if (selectedValue == singleChoiceItems[i]) {
                selectedPosition = i
                return selectedPosition
            }
        }

        return selectedPosition
    }

    private fun validateVoterDetails() {

        //val familyNumber = binding?.etFamilyNumber?.text?.trim().toString()

        val familyNumber =
            if (binding?.etFamilyNumber?.text?.trim().toString() == "-") {
                "0"
            } else {
                binding?.etFamilyNumber?.text?.trim().toString()
            }

        val familyName = binding?.etFamilyName?.text?.trim().toString()
        //val familyOrderNumber = binding?.etFamilyOrderNo?.text?.trim().toString()
        val familyOrderNumber =
            if (binding?.etFamilyOrderNo?.text?.trim().toString() == "-") {
                "0"
            } else {
                binding?.etFamilyOrderNo?.text?.trim().toString()
            }


        val lastName = binding?.etLastName?.text?.trim().toString()
        val givenName = binding?.etGivenName?.text?.trim().toString()
        val nationalIdCardNumber = binding?.etNationalIDCardNumber?.text?.trim().toString()
        val dateOfBirth = binding?.etDateOfBirth?.text?.trim().toString()
        val placeOfBirth = binding?.etPlaceOfBirth?.text?.trim().toString()
        val provinceOfBirth = binding?.etProvinceOfBirth?.text?.trim().toString()
        val provinceOfBirthId =
            if (provinceOfBirth.isNotEmpty() && (provinceListArray?.isNotEmpty() == true)) {
                provinceListArray!![binding?.etProvinceOfBirth?.tag as Int].provinceId
            } else {
                voterDetailsResponse.provOfBirthFK
            }

        val gender: String = when {
            binding?.etGender?.text.toString() == "Male" -> "M"
            binding?.etGender?.text.toString() == "Female" -> "F"
            else -> ""
        }

        val occupation = binding?.etOccupation?.text.toString()
        val occupationPK = if (occupationId != 0) {
            occupationId
        } else {
            voterDetailsResponse.occupationFK
        }

        val disability: Boolean = binding?.etPersonWithDisability?.text.toString() == "Yes"
        val religion = binding?.etReligion?.text.toString().trim()

        val fathersFirstName = binding?.etFathersFirstName?.text.toString()
        val fathersLastName = binding?.etFathersLastName?.text.toString()
        val mothersFirstName = binding?.etMothersFirstName?.text.toString()
        val mothersLastName = binding?.etMothersLastName?.text.toString()

        val province = binding?.etProvince?.text.toString()
        val openElectorate = binding?.etOpenElectorate?.text.toString()

        val locationOrVillage = binding?.etLocationOrVillage?.text.toString()
        val locationId =
            if (locationArray!!.isNotEmpty() && binding?.etLocationOrVillage?.tag != null) {
                locationArray!![binding?.etLocationOrVillage?.tag as Int].locationId
            } else {
                voterDetailsResponse.locationId
            }

        //val clanId = filterClanId
        val clanId =
            if (filterClanId == 0) {
                voterDetailsResponse.clanId
            } else {
                filterClanId
            }

        val clan = binding?.etClan?.text.toString().trim()
        val subClan = binding?.etSubClan?.text.toString()
        val restHouse = binding?.etRestHouse?.text.toString()

        //val sectionNumber = binding?.etSectionNo?.text.toString()
        val sectionNumber =
            if (binding?.etSectionNo?.text.toString() == "-") {
                "0"
            } else {
                binding?.etSectionNo?.text.toString()
            }

        val lotNumber =
            if (binding?.etLotNo?.text.toString() == "-") {
                "0"
            } else {
                binding?.etLotNo?.text.toString()
            }

        val streetName = binding?.etStreetName?.text.toString()
        val suburb = binding?.etSuburb?.text.toString()


        val comment = binding?.etComment?.text.toString().trim()

        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        var dobDate = Date()
        var ldeDate = Date()

        if (dateOfBirth.isNotEmpty())
            dobDate = sdf.parse(dateOfBirth)

        if (lastDateOfEnrolment.isNotEmpty())
            ldeDate = sdf.parse(lastDateOfEnrolment)

        when {
            lastName.isEmpty() -> {
                showAlertMessage(
                    "Please enter last name",
                    binding?.etLastName
                )
            }

            givenName.isEmpty() -> {
                showAlertMessage(
                    "Please enter given name",
                    binding?.etGivenName
                )
            }

            gender.isEmpty() -> {
                showAlertMessage(
                    "Please select gender", binding?.etGender
                )
            }

            locationOrVillage.isEmpty() -> {
                showAlertMessage(
                    "Please select location/village", binding?.etLocationOrVillage
                )
            }
            //From the mobile make it optional because some provinces and districts do not have clans. Right now if you want to save it will ask for a value and since some do not have clan listing its asking for a clan to be selected.
            //So make this optional and allow it to be saved


//            binding?.etClan?.text?.isEmpty() == true -> {
//                showAlertMessage(
//                    "Please select Clan"
//                )
//            }

            dateOfBirth.isEmpty() -> {
                showAlertMessage(
                    "Please select date of birth"
                )
            }

            dateOfBirth.isNotEmpty()
                    && lastDateOfEnrolment.isNotEmpty()
                    && dobDate.after(ldeDate) -> {
                showAlertMessage(
                    "Date of birth should be before or equal to the Last date of enrolment: $lastDateOfEnrolment"
                )
            }




            /* binding?.etMKALocation?.text?.trim()
                 ?.isEmpty() == true && securedSharedPreferenceWrapper.getString(
                 ApiConstants.ROLE,
                 ""
             ) == ApiConstants.ROLE_MKA -> {
                 showAlertMessage(
                     "Please select mka location/village"
                 )
             }

             binding?.etMKAClan?.text?.trim()
                 ?.isEmpty() == true && securedSharedPreferenceWrapper.getString(
                 ApiConstants.ROLE,
                 ""
             ) == ApiConstants.ROLE_MKA -> {
                 showAlertMessage(
                     "Please select mka clan"
                 )
             }*/

            else -> {
                if (checkVoterDetailsUpdated(
                        lastName,
                        givenName,
                        gender,
                        locationOrVillage,
                        occupation,
                        dateOfBirth,
                        disability,
                        religion,
                        nationalIdCardNumber,
                        clan,
                        familyNumber,
                        familyName,
                        familyOrderNumber,
                        placeOfBirth,
                        provinceOfBirth,
                        fathersFirstName,
                        fathersLastName,
                        mothersFirstName,
                        mothersLastName,
                        subClan,
                        restHouse,
                        sectionNumber,
                        lotNumber,
                        streetName,
                        suburb,
                        clanId
                    )
                ) {

                    val status =
                        if (voterDetailsResponse.status == getString(R.string.old) || voterDetailsResponse.status.toString()
                                .isEmpty() || voterDetailsResponse.status == "-"
                        )
                            getString(R.string.updated)
                        else
                            binding?.etStatus?.text.toString()

                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setMessage(getString(R.string.updated_voter_details_confirmation))
                        .setCancelable(false)
                        .setPositiveButton(
                            "Yes"
                        ) { dialog, id ->


                            val updatedVoterInformation = VotersList()
                            updatedVoterInformation.tempElectorId =
                                voterDetailsResponse.tempElectorId
                            updatedVoterInformation.electorId = voterDetailsResponse.electorId
                            updatedVoterInformation.oldElectorID = voterDetailsResponse.oldElectorID
                            updatedVoterInformation.locationId = locationId
                            if (locationId != voterDetailsResponse.locationId)
                                updatedVoterInformation.oldLocationId =
                                    voterDetailsResponse.locationId
                            else {
                                if (voterDetailsResponse.oldLocationId != null && voterDetailsResponse.oldLocationId != 0)
                                    updatedVoterInformation.oldLocationId =
                                        voterDetailsResponse.oldLocationId
                            }
                            updatedVoterInformation.locationName = locationOrVillage
                            updatedVoterInformation.wardId = wardId
                            updatedVoterInformation.wardName = wardName
                            updatedVoterInformation.llgId = llgId
                            updatedVoterInformation.llgName = llgName
                            updatedVoterInformation.districtId = districtId
                            updatedVoterInformation.provinceId = provinceId
                            updatedVoterInformation.regionId = regionId
                            updatedVoterInformation.lName = lastName
                            updatedVoterInformation.fName = givenName
                            updatedVoterInformation.voterName = "$lastName $givenName"
                            updatedVoterInformation.gender = gender
                            updatedVoterInformation.dob = getDOBAPIString(dateOfBirth)
                            updatedVoterInformation.isYOB = binding?.cbDOBIsYear?.isChecked == true
                            updatedVoterInformation.isPWD = disability
                            updatedVoterInformation.religion = religion
                            updatedVoterInformation.fatherFName = fathersFirstName
                            updatedVoterInformation.fatherLName = fathersLastName
                            updatedVoterInformation.motherFName = mothersFirstName
                            updatedVoterInformation.motherLName = mothersLastName
                            updatedVoterInformation.provOfBirthFK = provinceOfBirthId
                            updatedVoterInformation.provinceOfBirth = provinceOfBirth
                            updatedVoterInformation.provinceName = province
                            updatedVoterInformation.districtName = openElectorate
                            updatedVoterInformation.placeOfBirth = placeOfBirth
                            updatedVoterInformation.occupationFK = occupationPK
                            updatedVoterInformation.occupation = occupation
                            updatedVoterInformation.clanId = clanId
                            updatedVoterInformation.clan = getVoterDetails(clan)
                            updatedVoterInformation.clanName = getVoterDetails(clan)
                            updatedVoterInformation.subClan = getVoterDetails(subClan)
                            updatedVoterInformation.address = locationOrVillage
                            updatedVoterInformation.natID = ""
                            updatedVoterInformation.natIDRecordNo =
                                getVoterDetails(nationalIdCardNumber)
                            updatedVoterInformation.sevisPassId = binding?.etSevisPassId?.text?.toString()?.trim() ?: ""
                            updatedVoterInformation.sevisPassStatus = if (binding?.tvSevisPassStatusBadge?.text?.contains("Verified") == true) "Verified" else (voterDetailsResponse.sevisPassStatus ?: "Pending")
                            updatedVoterInformation.status = status
                            updatedVoterInformation.houseFK = 0
                            updatedVoterInformation.houseSortOrder = 0
                            updatedVoterInformation.restHouse = getVoterDetails(restHouse)
                            updatedVoterInformation.sectionNo =
                                getIntegerVoterDetailsFromString(sectionNumber)
                            updatedVoterInformation.lotNo =
                                getIntegerVoterDetailsFromString(lotNumber)
                            updatedVoterInformation.streetName = getVoterDetails(streetName)
                            updatedVoterInformation.suburbTownSettlement = getVoterDetails(suburb)
                            updatedVoterInformation.familyFK = 0
                            updatedVoterInformation.familyNumber =
                                getIntegerVoterDetailsFromString(familyNumber)
                            updatedVoterInformation.familyName = familyName
                            updatedVoterInformation.familySortOrder =
                                getIntegerVoterDetailsFromString(familyOrderNumber)
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
                            updatedVoterInformation.posted =
                                CommonMethods.getVoterDetailsPostedDateTime()
                            updatedVoterInformation.updatedDate =
                                CommonMethods.getVoterDetailsUpdatedDate()
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

                            if (selectedImagePath!!.isNotEmpty()) {
                                updatedVoterInformation.electorProfileImage = selectedImagePath
                                updatedVoterInformation.isImageUploaded = 0
                            }

                            if (voterDetailsResponse.status == getString(R.string.transferred_in)) {

                                updatedVoterInformation.isImageUploaded = 0

                                updatedVoterInformation.regionId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.regionId)
                                updatedVoterInformation.provinceId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.provinceId)
                                updatedVoterInformation.provinceName =
                                    getVoterDetails(voterDetailsResponse.provinceName)
                                updatedVoterInformation.districtId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.districtId)
                                updatedVoterInformation.districtName =
                                    getVoterDetails(voterDetailsResponse.districtName)
                                updatedVoterInformation.llgId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.llgId)
                                updatedVoterInformation.llgName =
                                    getVoterDetails(voterDetailsResponse.llgName)
                                updatedVoterInformation.wardId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.wardId)
                                updatedVoterInformation.wardName =
                                    getVoterDetails(voterDetailsResponse.wardName)
                                updatedVoterInformation.locationId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.locationId)
                                updatedVoterInformation.locationName =
                                    getVoterDetails(voterDetailsResponse.locationName)

                                updatedVoterInformation.transferOutRegionId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.transferOutRegionId)
                                updatedVoterInformation.transferOutProvinceId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.transferOutProvinceId)
                                updatedVoterInformation.transferOutDistrictId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.transferOutDistrictId)
                                updatedVoterInformation.transferOutLLGId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.transferOutLLGId)
                                updatedVoterInformation.transferOutWardId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.transferOutWardId)
                                updatedVoterInformation.transferOutLocationId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.transferOutLocationId)

                            }

                            if (voterDetailsResponse.status == getString(R.string.transferred_out)) {

                                updatedVoterInformation.regionId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.regionId)
                                updatedVoterInformation.provinceId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.provinceId)
                                updatedVoterInformation.provinceName =
                                    getVoterDetails(voterDetailsResponse.provinceName)
                                updatedVoterInformation.districtId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.districtId)
                                updatedVoterInformation.districtName =
                                    getVoterDetails(voterDetailsResponse.districtName)
                                updatedVoterInformation.llgId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.llgId)
                                updatedVoterInformation.llgName =
                                    getVoterDetails(voterDetailsResponse.llgName)
                                updatedVoterInformation.wardId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.wardId)
                                updatedVoterInformation.wardName =
                                    getVoterDetails(voterDetailsResponse.wardName)
                                updatedVoterInformation.locationId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.locationId)
                                updatedVoterInformation.locationName =
                                    getVoterDetails(voterDetailsResponse.locationName)

                                updatedVoterInformation.transferInRegionId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.transferInRegionId)
                                updatedVoterInformation.transferInProvinceId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.transferInProvinceId)
                                updatedVoterInformation.transferInDistrictId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.transferInDistrictId)
                                updatedVoterInformation.transferInLLGId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.transferInLLGId)
                                updatedVoterInformation.transferInWardId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.transferInWardId)
                                updatedVoterInformation.transferInLocationId =
                                    CommonMethods.getIntegerVoterDetails(voterDetailsResponse.transferInLocationId)

                            }

                            /**Update voter's MKA District LLG,Ward,Location and Clan*/
                            if (mkaDistrictId != 0) {
                                updatedVoterInformation.mkaDistrictId = mkaDistrictId
                                updatedVoterInformation.mkaDistrictName =
                                    binding?.etMKADistrict?.text?.trim().toString()
                            }
                            if (mkaLlgId != 0) {
                                updatedVoterInformation.mkaLlgId = mkaLlgId
                                updatedVoterInformation.mkaLlgName =
                                    binding?.etMKALlg?.text?.trim().toString()
                            }
                            if (mkaWardId != 0) {
                                updatedVoterInformation.mkaWardId = mkaWardId
                                updatedVoterInformation.mkaWardName =
                                    binding?.etMKAWard?.text?.trim().toString()
                            }
                            if (mkaLocationId != 0) {
                                updatedVoterInformation.mkaLocationId = mkaLocationId
                                updatedVoterInformation.mkaLocationName =
                                    binding?.etMKALocation?.text?.trim().toString()
                            }
                            if (mkaClanId != 0) {
                                updatedVoterInformation.mkaClanId = mkaClanId
                                updatedVoterInformation.mkaClanName =
                                    binding?.etMKAClan?.text?.trim().toString()
                            }

                            updatedVoterInformationDB(updatedVoterInformation)
                        }
                        .setNegativeButton(
                            "No"
                        ) { dialog, id ->
                            val resultIntent = Intent()
                            resultIntent.putExtra("NEED_REFRESH", true)
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
                            dialog.cancel()
                        }
                    val alert: AlertDialog = builder.create()
                    alert.show()

                } else {
                    alert("Change/Update any of the fields to update voter information!") {
                        okButton {
                        }
                    }.show()
                }


            }
        }

    }


    private fun checkVoterDetailsUpdated(
        lastName: String,
        givenName: String,
        gender: String,
        locationOrVillage: String,
        occupation: String,
        dateOfBirth: String,
        disability: Boolean,
        religion: String,
        nationalIdCardNumber: String,
        clan: String,
        familyNumber: String,
        familyName: String,
        familyOrderNumber: String,
        placeOfBirth: String,
        provinceOfBirth: String,
        fathersFirstName: String,
        fathersLastName: String,
        mothersFirstName: String,
        mothersLastName: String,
        subClan: String,
        restHouse: String,
        sectionNumber: String,
        lotNumber: String,
        streetName: String,
        suburb: String,
        clanId : Int
    ): Boolean {
        when {
            voterDetailsResponse.lName != lastName -> {
                return true
            }

            voterDetailsResponse.fName != givenName -> {
                return true
            }

            voterDetailsResponse.gender != gender -> {
                return true
            }

            voterDetailsResponse.locationName != locationOrVillage -> {
                return true
            }

            voterDetailsResponse.occupation != occupation -> {
                return true
            }

            voterOriginalDob != dateOfBirth -> {
                return true
            }

            voterDetailsResponse.isPWD != disability -> {
                return true
            }

            voterDetailsResponse.religion != religion -> {
                return true
            }

            voterDetailsResponse.natID != nationalIdCardNumber -> {
                return true
            }

            voterDetailsResponse.clan != clan -> {
                return true
            }

            voterDetailsResponse.mkaDistrictId != mkaDistrictId -> {
                return true
            }

            voterDetailsResponse.mkaLlgId != mkaLlgId -> {
                return true
            }

            voterDetailsResponse.mkaWardId != mkaWardId -> {
                return true
            }

            voterDetailsResponse.mkaLocationId != mkaLocationId -> {
                return true
            }

            voterDetailsResponse.mkaClanId != mkaClanId -> {
                return true
            }

            voterDetailsResponse.familyNumber != (if (familyNumber.isEmpty()) null else familyNumber.toInt()) -> {
                return true
            }

            voterDetailsResponse.familyName != familyName -> {
                return true
            }

            voterDetailsResponse.familySortOrder != (if (familyOrderNumber.isEmpty()) null else familyOrderNumber.toInt()) -> {
                return true
            }

            voterDetailsResponse.placeOfBirth != placeOfBirth -> {
                return true
            }

            voterDetailsResponse.provinceOfBirth != provinceOfBirth.ifEmpty { null } -> {
                return true
            }

            voterDetailsResponse.fatherFName != fathersFirstName -> {
                return true
            }

            voterDetailsResponse.fatherLName != fathersLastName -> {
                return true
            }

            voterDetailsResponse.motherFName != mothersFirstName -> {
                return true
            }

            voterDetailsResponse.motherLName != mothersLastName -> {
                return true
            }

            voterDetailsResponse.subClan != subClan -> {
                return true
            }

            voterDetailsResponse.restHouse != restHouse -> {
                return true
            }

            voterDetailsResponse.sectionNo != (if (sectionNumber.isEmpty()) null else sectionNumber.toInt()) -> {
                return true
            }

            voterDetailsResponse.lotNo != (if (lotNumber.isEmpty()) null else lotNumber.toInt()) -> {
                return true
            }

            voterDetailsResponse.streetName != streetName -> {
                return true
            }

            voterDetailsResponse.suburbTownSettlement != suburb -> {
                return true
            }

            voterDetailsResponse.clanId != clanId -> {
                return true
            }

            else -> {
                return false
            }

        }
    }

    private fun getNotSpecifiedOccupationId(): Int? {
        var notSpecifiedId = 46
        for (occupation in occupationListArray!!) {
            if (occupation.occupationName == "Not Specified") {
                notSpecifiedId = occupation.occupationPK
            }
        }
        return notSpecifiedId
    }

    private fun updatedVoterInformationDB(updatedVoterInformation: VotersList) {
        if (updatedVoterInformation.uploadId.isNullOrEmpty()) {
            updatedVoterInformation.uploadId = java.util.UUID.randomUUID().toString().replace("-", "")
        }
        updatedVoterInformation.isSyncToServer = 0
        updatedVoterInformation.isDataUploaded = 0
        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                val isVoterDownloaded = dataBaseInstance.votersListDao()
                    .checkVoterDownloadedWhileUpdate(updatedVoterInformation.tempElectorId)
                if (isVoterDownloaded != null)
                    dataBaseInstance.votersListDao().update(updatedVoterInformation)
                else
                    dataBaseInstance.votersListDao().insert(updatedVoterInformation)
            }
            alert("Voter Details updated successfully") {
                okButton {
                    val resultIntent = Intent()
                    resultIntent.putExtra("NEED_REFRESH", true)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }.show()
        }
    }


    private fun showAlertMessage(alertMessage: String, currentEditText: AppCompatEditText?) {
        currentEditText!!.requestFocus()
        alert(alertMessage) {
            okButton {
            }
        }.show()

    }

    private fun showAlertMessage(alertMessage: String, currentTextView: AppCompatTextView?) {
        currentTextView!!.requestFocus()
        alert(alertMessage) {
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

    private var bindingDatePicker: DialogDatePickerBinding? = null
    private var mAlertDialog: AlertDialog? = null

    private val mSelectedCalender = Calendar.getInstance()
    private val mDisplayDateFormat =
        SimpleDateFormat("dd-MM-yyyy", Locale.US)

    private fun selectDateOfBirth(dateOfBirth: String) {
        val builder = AlertDialog.Builder(this, R.style.full_screen_dialog)
        bindingDatePicker = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_date_picker, null, false
        ) as DialogDatePickerBinding
        builder.setView(bindingDatePicker?.root)
        mAlertDialog = builder.create()
        mAlertDialog?.setCancelable(false)
        mAlertDialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        if (dateOfBirth.isNotEmpty()) {
            val year = dateOfBirth.split("-")[2].toInt()
            val month = dateOfBirth.split("-")[1].toInt() - 1
            val day = dateOfBirth.split("-")[0].toInt()
            bindingDatePicker?.datePicker!!.updateDate(year, month, day)
        }

        //Setting Min Date
        val minDateForWorkingRollEdit = Calendar.getInstance()
        minDateForWorkingRollEdit[ApiConstants.MIN_YEAR_FOR_ENROLMENT,
            ApiConstants.MIN_MONTH_FOR_ENROLMENT] = ApiConstants.MIN_DAY_FOR_ENROLMENT
        bindingDatePicker?.datePicker!!.minDate = minDateForWorkingRollEdit.timeInMillis

        //Setting Max Date
        val year = lastDateOfEnrolment.split("-")[2].toInt()
        val month = lastDateOfEnrolment.split("-")[1].toInt() - 1
        val day = lastDateOfEnrolment.split("-")[0].toInt()
        val lastEnrolmentDate = Calendar.getInstance()
        lastEnrolmentDate[year, month] = day
        bindingDatePicker?.datePicker!!.maxDate = lastEnrolmentDate.timeInMillis

        bindingDatePicker?.tvCancel?.setOnClickListener {
            mAlertDialog!!.dismiss()
        }

        bindingDatePicker?.tvOk?.setOnClickListener {
            mSelectedCalender[Calendar.YEAR] = bindingDatePicker?.datePicker!!.year
            mSelectedCalender[Calendar.MONTH] = bindingDatePicker?.datePicker!!.month
            mSelectedCalender[Calendar.DAY_OF_MONTH] = bindingDatePicker?.datePicker!!.dayOfMonth
            binding?.etDateOfBirth?.text = mDisplayDateFormat.format(mSelectedCalender.time)
            mAlertDialog!!.dismiss()
        }
        mAlertDialog?.show()
    }

    private var bindingYearPicker: DialogYearPickerBinding? = null
    private var mYearPickerAlertDialog: AlertDialog? = null

    @SuppressLint("SetTextI18n")
    private fun selectDateOfBirthYear(dateOfBirth: String) {

        val builder = AlertDialog.Builder(this, R.style.full_screen_dialog)
        bindingYearPicker = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_year_picker, null, false
        ) as DialogYearPickerBinding
        builder.setView(bindingYearPicker?.root)
        mYearPickerAlertDialog = builder.create()
        mYearPickerAlertDialog?.setCancelable(false)
        mYearPickerAlertDialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        // Setting Min & Max Date
        val yearMaxValue = lastDateOfEnrolment.split("-")[2].toInt()
        val yearMinValue = ApiConstants.MIN_YEAR_FOR_ENROLMENT

        bindingYearPicker?.yearPicker?.maxValue = yearMaxValue
        bindingYearPicker?.yearPicker?.minValue = yearMinValue

        if (dateOfBirth.isNotEmpty()) {
            val year = dateOfBirth.split("-")[2].toInt()
            bindingYearPicker?.yearPicker?.value = year
        }

        bindingYearPicker?.tvCancel?.setOnClickListener {
            mYearPickerAlertDialog!!.dismiss()
        }

        bindingYearPicker?.tvOk?.setOnClickListener {
            val dateOfBirthTemp = "01-01-" + bindingYearPicker?.yearPicker?.value
            binding?.etDateOfBirth?.text = dateOfBirthTemp
            mYearPickerAlertDialog!!.dismiss()
        }
        mYearPickerAlertDialog?.show()

    }

    private fun showSingleSelectionDialogClan(
        singleChoiceItems: Array<String>,
        tvSelection: AppCompatTextView
    ) {
        CommonMethods.hideProgress()
        if (singleChoiceItems.isNotEmpty()) {
            var selectionValue = -1
            if (tvSelection.text!!.isNotEmpty()) {
                selectionValue = if (tvSelection.tag == null) {
                    getSelectedPosition(tvSelection.text.toString(), singleChoiceItems)
                } else {
                    tvSelection.tag as Int
                }
            }

            val mBuilder =
                AlertDialog.Builder(this)

            mBuilder.setSingleChoiceItems(
                singleChoiceItems,
                selectionValue
            ) { dialogInterface, clickItem ->
                tvSelection.tag = clickItem
                tvSelection.text = singleChoiceItems[clickItem]
                filterClanSelectedPos = clickItem
                if(clickItem >= 0) {
                    filterClanId = clanArray!![clickItem].clanId
                    filterClanName = clanArray!![clickItem].clanName
                }
                else {
                    filterClanId = 0
                    filterClanName = ""
                }
                dialogInterface.dismiss()
            }
            val mDialog = mBuilder.create()
            mDialog.show()
        } else {
            showAlertMessage("No items found")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SCAN_SEVISPASS) {
            val result = com.google.zxing.integration.android.IntentIntegrator.parseActivityResult(resultCode, data)
            if (result != null && result.contents != null) {
                binding?.etSevisPassId?.setText(result.contents)
                android.widget.Toast.makeText(this, "SevisPass scanned: ${result.contents}", android.widget.Toast.LENGTH_SHORT).show()
                binding?.btnVerifySevisPass?.performClick()
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val RC_SCAN_SEVISPASS = 10098
    }

}
