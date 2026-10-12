package com.pngelectoral.ui.activity

import ApiConstants
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.gson.JsonObject
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityRejectedVoterEditDetailsBinding
import com.pngelectoral.databinding.DialogDatePickerBinding
import com.pngelectoral.databinding.DialogYearPickerBinding
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.retrofit.*
import com.pngelectoral.ui.viewModel.EditVoterDetailsViewModel
import com.pngelectoral.utility.CommonMethods
import com.pngelectoral.utility.CommonMethods.getDOBAPIString
import com.pngelectoral.utility.CommonMethods.getDisplayDate
import com.pngelectoral.utility.CommonMethods.getIntegerVoterDetailsFromString
import com.pngelectoral.utility.CommonMethods.getVoterDetails
import com.pngelectoral.utility.helper.DialogSpinner
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class RejectedVoterEditDetailsActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityRejectedVoterEditDetailsBinding? = null
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
    private var lastDateOfEnrolment = ""
    private var occupationListArray: List<OccupationList>? = ArrayList()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_rejected_voter_edit_details
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
        CommonMethods.showProgress(this)
        getOccupationListFromDB()

        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )

        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase

        voterDetailsResponse = intent.getSerializableExtra(ApiConstants.VOTER_DETAILS) as VotersList
        binding?.tvVoterName?.text = CommonMethods.getVoterName(voterDetailsResponse)


        setFooterData()
        setOnClickListeners()
        setVoterData(voterDetailsResponse)
    }

    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
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

    @SuppressLint("SetTextI18n")
    private fun setVoterData(voterDetailsResponse: VotersList) {


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


        binding?.tvRejectedReason?.text =
            "Rejected Reason: " + getVoterDetails(voterDetailsResponse.rejectedReason)!!

        binding?.etFamilyNumber?.setText(getVoterDetails(voterDetailsResponse.familyNumber)!!)
        binding?.etFamilyName?.setText(getVoterDetails(voterDetailsResponse.familyName)!!)
        binding?.etFamilyOrderNo?.setText(getVoterDetails(voterDetailsResponse.familySortOrder)!!)
        binding?.etLastName?.setText(getVoterDetails(voterDetailsResponse.lName)!!)
        binding?.etGivenName?.setText(getVoterDetails(voterDetailsResponse.fName)!!)
        binding?.etNationalIDCardNumber?.setText(getVoterDetails(voterDetailsResponse.natIDRecordNo)!!)
        if (getVoterDetails(
                voterDetailsResponse.dob
            )!!.isNotEmpty()
        ) {

            binding?.etDateOfBirth?.text = getDisplayDate(
                getVoterDetails(
                    voterDetailsResponse.dob
                )!!
            )

            binding?.cbDOBIsYear?.isChecked =
                CommonMethods.getVoterDetailsBoolean(voterDetailsResponse.isYOB)
        } else {
            binding?.cbDOBIsYear?.isChecked = false
        }
        binding?.etPlaceOfBirth?.setText(getVoterDetails(voterDetailsResponse.placeOfBirth)!!)
        binding?.etProvinceOfBirth?.text = getVoterDetails(voterDetailsResponse.provinceOfBirth)!!


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
            binding?.etWardNoAndName?.text = result!!.wardNumber.toString() + "&" + result.wardName
            wardName = result.wardName
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

        binding?.etProvinceOfBirth?.setOnClickListener {
            CommonMethods.showProgress(this)
            getProvinceListFromDB()
        }


        binding?.etLocationOrVillage?.setOnClickListener {
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

        binding?.btnSubmit?.setOnClickListener {
            validateVoterDetails()
        }


    }

    var occupationId = 0
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
                getOccupationListQuery()
            }
            occupationListArray = result
            CommonMethods.hideProgress()
        }
    }

    private fun getOccupationListQuery(): List<OccupationList>? {
        return dataBaseInstance.occupationListDao()?.all
    }


    private fun getProvinceListFromDB() {
        launch(Dispatchers.Main) {
            val result: List<ProvinceList>? = withContext(Dispatchers.IO) {
                getProvinceListQuery()
            }
            showProvinceOptions(result)
        }
    }

    private fun getProvinceListQuery(): List<ProvinceList>? {
        return dataBaseInstance.provinceListDao()?.all
    }

    private var provinceListArray: ArrayList<ProvinceList>? = ArrayList()
    private fun showProvinceOptions(result: List<ProvinceList>?) {
        provinceListArray = ArrayList(result!!)
        showSingleSelectionDialog(
            CommonMethods.getProvinceList(provinceListArray!!),
            binding?.etProvinceOfBirth!!,
            "ProvinceOfBirth"
        )
    }


    var llgListArray: ArrayList<LLGList>? = ArrayList()
    var wardNoAndNameArray: ArrayList<WardList>? = ArrayList()


    private fun getLocationListFromDB() {
        launch(Dispatchers.Main) {
            val result: List<LocationList>? = withContext(Dispatchers.IO) {
                getLocationListQuery()
            }
            showLocationOptions(result)
        }
    }

    private fun getLocationListQuery(): List<LocationList>? {
        return dataBaseInstance.locationListDao()
            ?.getLocationListOfRegionAndProvinceAndDistrictAndLLGAndWard(
                regionId,
                provinceId,
                districtId,
                llgId,
                wardId

            )
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
            binding?.etLocationOrVillage!!,
            "Location"
        )
    }


    private fun showSingleSelectionDialog(
        singleChoiceItems: Array<String>,
        tvSelection: AppCompatTextView,
        selectedField: String
    ) {
        CommonMethods.hideProgress()
        if (singleChoiceItems.isNotEmpty()) {
            var selectionValue = -1
            if (tvSelection.text.isNotEmpty()) {
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

                when (selectedField) {
                    "LLG" -> {

                        binding?.etWardNoAndName?.tag = -1
                        binding?.etWardNoAndName?.text = ""
                        wardName = ""

                        binding?.etLocationOrVillage?.tag = -1
                        binding?.etLocationOrVillage?.text = ""

                        wardNoAndNameArray?.clear()
                        locationArray?.clear()

                    }
                    "Ward" -> {

                        binding?.etLocationOrVillage?.tag = -1
                        binding?.etLocationOrVillage?.text = ""

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

        val familyNumber = binding?.etFamilyNumber?.text.toString()
        val familyName = binding?.etFamilyName?.text.toString()
        val familyOrderNumber = binding?.etFamilyOrderNo?.text.toString()
        val lastName = binding?.etLastName?.text.toString()
        val givenName = binding?.etGivenName?.text.toString()
        val nationalIdCardNumber = binding?.etNationalIDCardNumber?.text.toString()
        val dateOfBirth = binding?.etDateOfBirth?.text.toString()
        val placeOfBirth = binding?.etPlaceOfBirth?.text.toString()
        val provinceOfBirth = binding?.etProvinceOfBirth?.text.toString()
        val provinceOfBirthId =
            if (provinceOfBirth.isNotEmpty() && provinceListArray!!.isNotEmpty()) {
                provinceListArray!![binding?.etProvinceOfBirth?.tag as Int].provinceId
            } else {
                voterDetailsResponse.provOfBirthFK
            }

        val gender: String = when {
            binding?.etGender?.text.toString() == "Male" -> "M"
            binding?.etGender?.text.toString() == "Female" -> "F"
            else -> ""
        }

        var occupation = binding?.etOccupation?.text.toString()
        var occupationPK = if (occupationId != 0) {
            occupationId
        } else {
            voterDetailsResponse.occupationFK
        }
//        val occupationPK = if (occupation.isNotEmpty()
//            && occupationListArray!!.isNotEmpty() && binding?.etOccupation?.tag != null
//        ) {
//            occupationListArray!![binding?.etOccupation?.tag as Int].occupationPK
//        } else {
//            voterDetailsResponse.occupationFK
//        }


        val disability: Boolean = binding?.etPersonWithDisability?.text.toString() == "Yes"
        val religion = binding?.etReligion?.text.toString()

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

        val clan = binding?.etClan?.text.toString()
        val subClan = binding?.etSubClan?.text.toString()
        val restHouse = binding?.etRestHouse?.text.toString()

        val sectionNumber = binding?.etSectionNo?.text.toString()
        val lotNumber = binding?.etLotNo?.text.toString()
        val streetName = binding?.etStreetName?.text.toString()
        val suburb = binding?.etSuburb?.text.toString()

        val status = binding?.etStatus?.text.toString()
        val comment = binding?.etComment?.text.toString()

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
            dateOfBirth.isEmpty() -> {
                showAlertMessage(
                    "Please select date of birth"
                )
            }
            else -> {

                if (occupation.isEmpty()) {
                    occupation = "Not Specified"
                    occupationPK = getNotSpecifiedOccupationId()
                }

                val updatedVoterInformation = VotersList()
                updatedVoterInformation.tempElectorId = voterDetailsResponse.tempElectorId
                updatedVoterInformation.electorId = voterDetailsResponse.electorId
                updatedVoterInformation.oldElectorID = voterDetailsResponse.oldElectorID
                updatedVoterInformation.locationId = locationId
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
                updatedVoterInformation.clan = getVoterDetails(clan)
                updatedVoterInformation.subClan = getVoterDetails(subClan)
                updatedVoterInformation.address = locationOrVillage
                updatedVoterInformation.natID = ""
                updatedVoterInformation.natIDRecordNo = getVoterDetails(nationalIdCardNumber)
                updatedVoterInformation.status = status
                updatedVoterInformation.houseFK = 0
                updatedVoterInformation.houseSortOrder = 0
                updatedVoterInformation.restHouse = getVoterDetails(restHouse)
                updatedVoterInformation.sectionNo = getIntegerVoterDetailsFromString(sectionNumber)
                updatedVoterInformation.lotNo = getIntegerVoterDetailsFromString(lotNumber)
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
                updatedVoterInformation.rejectRecordUpdated = 1
                updatedVoterInformation.isRejected = true

                if (voterDetailsResponse.status == getString(R.string.transferred_in)) {

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
                updatedVoterInformationDB(updatedVoterInformation)
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
                    resultIntent.putExtra("NEED_REFRESH", false)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }.show()
        }
    }


    private fun showAlertMessage(alertMessage: String, currentEditText: AppCompatEditText?) {

        alert(alertMessage) {
            okButton {
                currentEditText!!.requestFocus()
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

    private val mSelectedCalender = Calendar.getInstance()
    private val mDisplayDateFormat =
        SimpleDateFormat("dd-MM-yyyy", Locale.US)

    private var bindingDatePicker: DialogDatePickerBinding? = null
    private var mAlertDialog: AlertDialog? = null

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

        mAlertDialog!!.setOnDismissListener {
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
}
