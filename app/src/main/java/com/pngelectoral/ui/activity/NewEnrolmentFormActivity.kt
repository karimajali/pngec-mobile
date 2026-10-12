package com.pngelectoral.ui.activity

import ApiConstants
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.master.basediproject.extensions.toArrayList
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityNewEnrolmentFormBinding
import com.pngelectoral.databinding.DialogDatePickerBinding
import com.pngelectoral.databinding.DialogForm11ImageBinding
import com.pngelectoral.databinding.DialogYearPickerBinding
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.retrofit.*
import com.pngelectoral.ui.viewModel.NewEnrolmentFormViewModel
import com.pngelectoral.utility.CommonMethods
import com.pngelectoral.utility.CommonMethods.getDOBAPIString
import com.pngelectoral.utility.CommonMethods.getIntegerVoterDetailsFromString
import com.pngelectoral.utility.CommonMethods.getVoterDetails
import com.pngelectoral.utility.helper.DialogSpinner
import kotlinx.android.synthetic.main.item_ward_voter.tvClan
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.math.abs

class NewEnrolmentFormActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityNewEnrolmentFormBinding? = null
    private var newEnrolmentFormViewModel: NewEnrolmentFormViewModel? = null
    private var form11ImagePath = ""
    private var voterImagePath = ""
    lateinit var loggedInDPOUserData: DPOUserData
    lateinit var dataBaseInstance: AppDatabase

    var regionId = 0
    var provinceId = 0
    var districtId = 0
    var llgId = 0
    var wardId = 0
    var wardName = ""
    var locationId = 0
    private var lastDateOfEnrolment = ""
    private var provisionalCutOffDate = ""
    private var occupationListArray: List<OccupationList>? = ArrayList()
    private var isProvisional = false

    /**Define values to store selected mka data.*/
    var mkaDistrictId = 0
    var mkaLlgId = 0
    var mkaWardId = 0
    var mkaLocationId = 0
    var mkaClanId = 0

    /**Define list to store mka data.*/
    private var mkaDistrictArray = ArrayList<MKAGeoListData>()
    private var mkaLlgArray = ArrayList<MKAGeoListModel.GeoListData.LlG>()
    private var mkaWardArray = ArrayList<MKAGeoListModel.GeoListData.LlG.Ward>()
    private var mkaLocationArray = ArrayList<MKAGeoListModel.GeoListData.LlG.Ward.Location>()
    private var mkaClanArray = ArrayList<MKAClanListData>()

    private var filterClanId = 0
    private var filterClanSelectedPos = -1
    private var filterClanName = ""
    private var clanArray: ArrayList<ClanListData>? = ArrayList()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_new_enrolment_form
        )
        newEnrolmentFormViewModel =
            ViewModelProvider(this).get(NewEnrolmentFormViewModel::class.java)
        binding?.newEnrolmentFormViewModel = newEnrolmentFormViewModel

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

        binding?.etDateOfBirth?.text = lastDateOfEnrolment

        CommonMethods.showProgress(this)
        getOccupationListFromDB()
        form11ImagePath = intent.getStringExtra("Form11_Image_Path")!!
        voterImagePath = intent.getStringExtra("Voter_Image_Path")!!
        if (voterImagePath.isNotEmpty()) {
            val image =
                File(voterImagePath)

            Glide.with(this).load(Uri.fromFile(image))
                .into(binding?.ivVoterImage!!)
        }
        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )
        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase
        regionId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_REGION_ID, 0)
        provinceId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_PROVINCE_ID, 0)
        districtId = loggedInDPOUserData.district.toInt()
        llgId = loggedInDPOUserData.llg.toInt()


        llgId = securedSharedPreferenceWrapper.getInt(
            "TEMP_LLGId",
            0
        )

        wardId = loggedInDPOUserData.ward.toInt()

        if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO) {
            wardId = securedSharedPreferenceWrapper.getInt(
                "TEMP_WardId",
                0
            )
        }

        locationId =
            securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LOCATION_ID, 0)

        binding?.etProvince?.text =
            securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_PROVINCE, "")
        binding?.etOpenElectorate?.text = loggedInDPOUserData.districtName
        binding?.etLLG?.text = loggedInDPOUserData.llgName
        wardName = loggedInDPOUserData.wardName
        getMKAWardNumberAndNameUsingCoroutines()
        /*if (securedSharedPreferenceWrapper.getString("TEMP_LLGName", "").isNotEmpty()) {
            binding?.etLLG?.text =
                securedSharedPreferenceWrapper.getString("TEMP_LLGName", "")
        }
        if (securedSharedPreferenceWrapper.getString("TEMP_WardName", "").isNotEmpty()) {
            val wardNumber = securedSharedPreferenceWrapper.getInt(
                "TEMP_WardNumber",
                0
            )
            val wardNumberString = if (wardNumber == 0)
                ""
            else
                wardNumber.toString()
            wardName = securedSharedPreferenceWrapper.getString(
                "TEMP_WardName",
                ""
            )
            if (wardNumberString.isEmpty()) {
                binding?.etWardNoAndName?.text = wardName
            } else {
                binding?.etWardNoAndName?.text = "$wardNumberString & $wardName"
            }
        }*/

        if (locationId != 0) {
            binding?.etLocationOrVillage?.text =
                securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LOCATION, "")
            binding?.etLocationOrVillage?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }


        /*when (securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "")) {
            ApiConstants.ROLE_DPO -> {
                binding?.llProvince?.visibility = View.VISIBLE
            }

            ApiConstants.ROLE_MKA -> {
                binding?.llProvince?.visibility = View.GONE
            }

            else -> {
                binding?.llProvince?.visibility = View.VISIBLE
            }
        }*/

        /**Set voter's data from database*/
        binding?.etMKADistrict?.text = loggedInDPOUserData.mkaDistrictName
        mkaDistrictId = loggedInDPOUserData.mkaDistrict.toInt()
        binding?.etMKALlg?.text = loggedInDPOUserData.mkallgName
        mkaLlgId = loggedInDPOUserData.mkallg.toInt()
        binding?.etMKAWard?.text = loggedInDPOUserData.mkaWardName
        mkaWardId = loggedInDPOUserData.mkaWard.toInt()
        /*binding?.etMKALocation?.text = loggedInDPOUserData.mkaLocationName
        mkaLocationId = loggedInDPOUserData.mkaLocation.toInt()
        binding?.etMKAClan?.text = loggedInDPOUserData.mkaClanName
        mkaClanId = loggedInDPOUserData.mkaClanId*/
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
            val locationResult = mkaWardArray.filter { llg ->
                llg.wardId == mkaWardId
            }
            if (locationResult.isNotEmpty()) {
                mkaLocationArray.addAll(locationResult.single().locations ?: arrayListOf())
                mkaClanArray.addAll(locationResult.single().clans ?: arrayListOf())
            }
        }
        if(mkaDistrictId != 0){
            binding?.etMKADistrict?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if(mkaLlgId != 0){
            binding?.etMKALlg?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if(mkaWardId != 0){
            binding?.etMKAWard?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        setFooterData()
        setOnClickListeners()
    }

    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    private fun getMKAWardNumberAndNameUsingCoroutines() {
        launch(Dispatchers.Main) {
            val result: WardList? = withContext(Dispatchers.IO) {
                dataBaseInstance.wardListDao()?.getDPOWard(wardId)
            }
            var wardNumber = result?.wardNumber ?: 0
            var wardNoAndName = "$wardNumber & $wardName"
            binding?.etWardNoAndName?.text = wardNoAndName
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

    private fun setOnClickListeners() {

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

        binding?.btnHome?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }

        binding?.btnBack?.setOnClickListener {
            finish()
        }

        binding?.ivViewForm11?.setOnClickListener {
            showForm11Image()
        }

        binding?.etDateOfBirth?.setOnClickListener {
            if (binding?.cbDOBIsYear?.isChecked == true) {
                selectDateOfBirthYear(binding?.etDateOfBirth?.text.toString())
            } else {
                selectDateOfBirth(binding?.etDateOfBirth?.text.toString())
            }
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

        binding?.etProvinceOfBirth?.setOnClickListener {
            CommonMethods.showProgress(this)
            getProvinceListFromDB()
        }

        binding?.etLocationOrVillage?.setOnClickListener {

            if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LOCATION, "")
                    .isEmpty()
            ) {
                if (securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.LOCATION_DATA_DOWNLOADED,
                        false
                    )
                ) {
                    CommonMethods.showProgress(this)
                    getLocationListFromDB()
                } else
                    callLocationListAPI()
            }
        }

        binding?.btnSubmit?.setOnClickListener {
            validateVoterDetails()
        }

        /**Define on click for added fields related to mka only if the user is DPO.*/
        binding?.etMKALocation?.setOnClickListener {
            if (mkaWardId != 0) {
                if (securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.GEO_LIST_DATA_DOWNLOADED,
                        false
                    )
                ) {
                    CommonMethods.showProgress(this)
                    getMKALocationList()
                }
            } else {
                showAlertMessage(
                    "Please select MKA Ward first"
                )
            }
        }

        binding?.etMKAClan?.setOnClickListener {
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
                android.app.AlertDialog.Builder(this)

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

    private fun getClanList(clans: java.util.ArrayList<ClanListData>): Array<String> {
        val clanList: java.util.ArrayList<String> = java.util.ArrayList()
        //clanList.add("All")
        for (clan in clans) {
            clanList.add(clan.clanName)
        }
        return clanList.toArray(arrayOfNulls<String>(clanList.size))
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

    private fun showGenderOptions() {
        val items = arrayOf<CharSequence>(
            "Male",
            "Female"
        )
        val builder = android.app.AlertDialog.Builder(this)
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
        val builder = android.app.AlertDialog.Builder(this)
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

    var occupationId = 0
    private fun showOccupationWithSearch(occupationListArray: List<OccupationList>) {
        DialogSpinner.with(this, ArrayList(occupationListArray)).setEnableSearch(true)
            .setMap { value: OccupationList -> value.occupationName ?: "" }
            .setOnValueSelectedCallback { model ->
                binding?.etOccupation?.text = model.occupationName ?: ""
                occupationId = model.occupationPK
            }.build().show(fragmentManager = supportFragmentManager)
    }


    private fun getProvinceListFromDB() {
        launch(Dispatchers.Main) {
            val result: List<ProvinceList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.provinceListDao()?.all
            }
            showProvinceOptions(result)
        }
    }

    private var provinceListArray: ArrayList<ProvinceList>? = ArrayList()
    private fun showProvinceOptions(result: List<ProvinceList>?) {
        provinceListArray = ArrayList(result!!)
        showSingleSelectionDialog(
            CommonMethods.getProvinceList(provinceListArray!!),
            binding?.etProvinceOfBirth!!
        )
    }

    private fun getLocationListFromDB() {
        launch(Dispatchers.Main) {
            if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO) {
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
            else
            {
                val result: List<LocationList>? = withContext(Dispatchers.IO) {
                    dataBaseInstance.locationListDao()
                        ?.getLocationListOfWard(wardId)
                }
                showLocationOptions(result)

            }
        }
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

    private var locationArray: ArrayList<LocationList>? = ArrayList()
    private fun showLocationOptions(result: List<LocationList>?) {
        locationArray = ArrayList(result!!)
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
                android.app.AlertDialog.Builder(this)

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

        val familyNumber = binding?.etFamilyNumber?.text.toString().trim()
        val familyName = binding?.etFamilyName?.text.toString().trim()
        val familyOrderNumber = binding?.etFamilyOrderNo?.text.toString().trim()
        val lastName = binding?.etLastName?.text.toString().trim()
        val givenName = binding?.etGivenName?.text.toString().trim()
        val nationalIdCardNumber = binding?.etNationalIDCardNumber?.text.toString().trim()
        val dateOfBirth = binding?.etDateOfBirth?.text.toString()
        val placeOfBirth = binding?.etPlaceOfBirth?.text.toString().trim()
        val provinceOfBirth = binding?.etProvinceOfBirth?.text.toString()
        val provinceOfBirthId =
            if (provinceOfBirth.isNotEmpty() && provinceListArray!!.isNotEmpty()) {
                provinceListArray!![binding?.etProvinceOfBirth?.tag as Int].provinceId
            } else {
                0
            }

        val gender: String = when {
            binding?.etGender?.text.toString() == "Male" -> "M"
            binding?.etGender?.text.toString() == "Female" -> "F"
            else -> ""
        }

        val occupation = binding?.etOccupation?.text.toString()
        val occupationPK = occupationId


        val disability: Boolean = binding?.etPersonWithDisability?.text.toString() == "Yes"
        val religion = binding?.etReligion?.text.toString().trim()

        val fathersFirstName = binding?.etFathersFirstName?.text.toString().trim()
        val fathersLastName = binding?.etFathersLastName?.text.toString().trim()
        val mothersFirstName = binding?.etMothersFirstName?.text.toString().trim()
        val mothersLastName = binding?.etMothersLastName?.text.toString().trim()

        val province = binding?.etProvince?.text.toString()
        val openElectorate = binding?.etOpenElectorate?.text.toString()

        val llg = binding?.etLLG?.text.toString()
        val locationOrVillage = binding?.etLocationOrVillage?.text.toString()
        val locationId =
            if (locationArray!!.isNotEmpty() && binding?.etLocationOrVillage?.tag != null) {
                locationArray!![binding?.etLocationOrVillage?.tag as Int].locationId
            } else {
                locationId
            }

        val clanId = filterClanId
        val ClanName = filterClanName
        val clan = binding?.etClan?.text.toString().trim()
        val subClan = binding?.etSubClan?.text.toString().trim()
        val restHouse = binding?.etRestHouse?.text.toString().trim()

        val sectionNumber = binding?.etSectionNo?.text.toString().trim()
        val lotNumber = binding?.etLotNo?.text.toString().trim()
        val streetName = binding?.etStreetName?.text.toString().trim()
        val suburb = binding?.etSuburb?.text.toString().trim()

        val status = binding?.etStatus?.text.toString()
        val comment = binding?.etComment?.text.toString().trim()

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

            dateOfBirth.isEmpty() -> {
                showAlertMessage(
                    "Please select date of birth"
                )
            }
//            placeOfBirth.isEmpty() -> {
//                showAlertMessage(
//                    "Please enter place of birth", binding?.etPlaceOfBirth
//                )
//            }
//            provinceOfBirth.isEmpty() -> {
//                showAlertMessage(
//                    "Please select province of birth"
//                )
//            }
            gender.isEmpty() -> {
                showAlertMessage(
                    "Please select gender"
                )
            }
//            occupation.isEmpty() -> {
//                showAlertMessage(
//                    "Please select occupation"
//                )
//            }
            locationOrVillage.isEmpty() -> {
                showAlertMessage(
                    "Please select location/village"
                )
            }

            //From the mobile make it optional because some provinces and districts do not have clans. Right now if you want to save it will ask for a value and since some do not have clan listing its asking for a clan to be selected.
            //So make this optional and allow it to be saved

//            binding?.etClan?.text?.isEmpty() == true -> {
//                showAlertMessage(
//                    "Please select Clan"
//                )
//            }



           /* binding?.etMKALocation?.text?.trim()?.isEmpty() == true -> {
                showAlertMessage(
                    "Please select mka location/village"
                )
            }

            binding?.etMKAClan?.text?.trim()?.isEmpty() == true -> {
                showAlertMessage(
                    "Please select mka clan"
                )
            }*/

            else -> {
                if (checkMkaDataValid()) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setMessage(getString(R.string.save_voter_confirmation))
                        .setCancelable(false)
                        .setPositiveButton(
                            "Yes"
                        ) { dialog, id ->
                            val updatedVoterInformation = VotersList()

                            updatedVoterInformation.tempElectorId =
                                abs(System.currentTimeMillis().toInt())
                            updatedVoterInformation.electorId = abs(System.currentTimeMillis())
                            updatedVoterInformation.oldElectorID = 0
                            updatedVoterInformation.locationId = locationId
                            updatedVoterInformation.locationName = locationOrVillage
                            updatedVoterInformation.wardId = wardId
                            updatedVoterInformation.wardName = wardName
                            updatedVoterInformation.llgId = llgId
                            updatedVoterInformation.llgName = llg
                            updatedVoterInformation.districtId = districtId
                            updatedVoterInformation.provinceId = provinceId
                            updatedVoterInformation.regionId = regionId
                            updatedVoterInformation.lName = lastName
                            updatedVoterInformation.fName = givenName
                            updatedVoterInformation.voterName = "$lastName $givenName"
                            updatedVoterInformation.gender = gender
                            updatedVoterInformation.dob = getDOBAPIString(dateOfBirth)
                            updatedVoterInformation.isYOB = binding?.cbDOBIsYear?.isChecked == true
                            updatedVoterInformation.isProvisional = isProvisional
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
                            updatedVoterInformation.clanName = getVoterDetails(clan)
                            updatedVoterInformation.clan = getVoterDetails(clan)
                            updatedVoterInformation.subClan = getVoterDetails(subClan)
                            updatedVoterInformation.address = locationOrVillage
                            updatedVoterInformation.natID = ""
                            updatedVoterInformation.natIDRecordNo =
                                getVoterDetails(nationalIdCardNumber)
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
                            updatedVoterInformation.electorProfileImage = voterImagePath
                            updatedVoterInformation.electorProfileImageUrl = ""
                            updatedVoterInformation.electorFormImage = form11ImagePath
                            updatedVoterInformation.electorFormImageUrl = ""
                            updatedVoterInformation.electorProfileImageFile = ""
                            updatedVoterInformation.electorFormImageFile = ""
                            updatedVoterInformation.enrolmentFormNumber =
                                securedSharedPreferenceWrapper.getString(
                                    ApiConstants.ENROLMENT_FORM_NUMBER,
                                    ""
                                )
                            updatedVoterInformation.enrollFormType =
                                securedSharedPreferenceWrapper.getString(
                                    ApiConstants.ENROLMENT_FORM_TYPE,
                                    ""
                                )
                            updatedVoterInformation.createdBy = loggedInDPOUserData.userId.toInt()
                            updatedVoterInformation.isSyncToServer = 0
                            updatedVoterInformation.isDataUploaded = 0
                            updatedVoterInformation.isImageUploaded = 0
                            updatedVoterInformation.syncStatus = 0
                            /**Update MKA Fields*/
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
                            addNewVoterToDB(updatedVoterInformation)
                        }
                        .setNegativeButton(
                            "No"
                        ) { dialog, id ->
                            startActivity(Intent(this, HomeActivity::class.java))
                            finishAffinity()
                            dialog.cancel()
                        }
                    val alert: AlertDialog = builder.create()
                    alert.show()

                }
            }
        }
    }
    fun checkMkaDataValid(): Boolean {
        if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_MKA) {

            if (binding?.etMKALocation?.text?.trim()?.isEmpty() == true) {
                showAlertMessage("Please select mka location/village")
                return false
            } else if (binding?.etMKAClan?.text?.trim()?.isEmpty() == true) {
                showAlertMessage("Please select mka clan")
                return false
            }
        }
        return true
    }
    private fun addNewVoterToDB(updatedVoterInformation: VotersList) {
        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                dataBaseInstance.votersListDao().insert(updatedVoterInformation)
            }
//            alert("New Voter added successfully") {
//                okButton {
//                    startActivity(
//                        Intent(
//                            this@NewEnrolmentFormActivity,
//                            WardRollVoterListActivity::class.java
//                        )
//                    )
//                    finishAffinity()
//                }
//            }.show()


            if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_MKA) {
                startActivity(
                    Intent(
                        this@NewEnrolmentFormActivity,
                        WardRollVoterListActivity::class.java
                    ).putExtra("CanEdit","NO")
                )
            }
            else
            {
                startActivity(
                    Intent(
                        this@NewEnrolmentFormActivity,
                        WardRollVoterListActivity::class.java
                    )
                )
            }

            finishAffinity()
        }
    }


    private fun showAlertMessage(alertMessage: String, currentEditText: AppCompatEditText?) {

        alert(alertMessage) {
            okButton {
                currentEditText!!.requestFocus()
            }
        }.show()

    }

    private fun showAlertMessage(alertMessage: String) {

        alert(alertMessage) {
            okButton {
            }
        }.show()

    }

    private var bindingForm11Image: DialogForm11ImageBinding? = null
    private var mAlertDialog: AlertDialog? = null

    private fun showForm11Image() {

        val builder = AlertDialog.Builder(this, R.style.full_screen_dialog)
        bindingForm11Image = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_form11_image, null, false
        ) as DialogForm11ImageBinding
        builder.setView(bindingForm11Image?.root)
        mAlertDialog = builder.create()
        mAlertDialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        val image =
            File(form11ImagePath)

        Glide.with(this).load(Uri.fromFile(image))
            .into(bindingForm11Image?.ivForm11Image!!)


        bindingForm11Image?.ivClose?.setOnClickListener {
            mAlertDialog!!.dismiss()
        }

        mAlertDialog?.show()

    }

    private val mSelectedCalender = Calendar.getInstance()
    private val mDisplayDateFormat =
        SimpleDateFormat("dd-MM-yyyy", Locale.US)
    private var bindingDatePicker: DialogDatePickerBinding? = null
    private var mAlertDatePickerDialog: android.app.AlertDialog? = null

    private fun selectDateOfBirth(dateOfBirth: String) {
        val builder = android.app.AlertDialog.Builder(this, R.style.full_screen_dialog)
        bindingDatePicker = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_date_picker, null, false
        ) as DialogDatePickerBinding
        builder.setView(bindingDatePicker?.root)
        mAlertDatePickerDialog = builder.create()
        mAlertDatePickerDialog?.setCancelable(false)
        mAlertDatePickerDialog?.window?.setLayout(
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
            mAlertDatePickerDialog!!.dismiss()
        }

        bindingDatePicker?.tvOk?.setOnClickListener {
            mSelectedCalender[Calendar.YEAR] = bindingDatePicker?.datePicker!!.year
            mSelectedCalender[Calendar.MONTH] = bindingDatePicker?.datePicker!!.month
            mSelectedCalender[Calendar.DAY_OF_MONTH] = bindingDatePicker?.datePicker!!.dayOfMonth
            val dobDate: Date? =
                mDisplayDateFormat.parse(mDisplayDateFormat.format(mSelectedCalender.time))
            val ldeDate: Date? = mDisplayDateFormat.parse(lastDateOfEnrolment)
            val pcoDate: Date? = mDisplayDateFormat.parse(provisionalCutOffDate)
            binding?.etDateOfBirth?.text = mDisplayDateFormat.format(mSelectedCalender.time)
            isProvisional = ldeDate!!.compareTo(dobDate) * dobDate!!.compareTo(pcoDate) > 0
            mAlertDatePickerDialog!!.dismiss()
        }
        mAlertDatePickerDialog?.show()
    }

    private var bindingYearPicker: DialogYearPickerBinding? = null
    private var mYearPickerAlertDialog: android.app.AlertDialog? = null

    @SuppressLint("SetTextI18n")
    private fun selectDateOfBirthYear(dateOfBirth: String) {

        val builder = android.app.AlertDialog.Builder(this, R.style.full_screen_dialog)
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
            val dobDate: Date? = mDisplayDateFormat.parse(dateOfBirthTemp)
            val ldeDate: Date? = mDisplayDateFormat.parse(lastDateOfEnrolment)
            val pcoDate: Date? = mDisplayDateFormat.parse(provisionalCutOffDate)
            binding?.etDateOfBirth?.text = dateOfBirthTemp
            isProvisional = ldeDate!!.compareTo(dobDate) * dobDate!!.compareTo(pcoDate) > 0
            mYearPickerAlertDialog!!.dismiss()
        }
        mYearPickerAlertDialog?.show()

    }

}
