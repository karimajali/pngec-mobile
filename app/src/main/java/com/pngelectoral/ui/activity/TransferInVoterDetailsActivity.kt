package com.pngelectoral.ui.activity

import ApiConstants
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
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
import com.pngelectoral.databinding.ActivityTransferInVoterDetailsBinding
import com.pngelectoral.databinding.DialogDatePickerBinding
import com.pngelectoral.databinding.DialogForm11ImageBinding
import com.pngelectoral.databinding.DialogYearPickerBinding
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.retrofit.*
import com.pngelectoral.ui.viewModel.TransferInVoterDetailsViewModel
import com.pngelectoral.utility.CommonMethods
import com.pngelectoral.utility.CommonMethods.getDisplayDate
import com.pngelectoral.utility.CommonMethods.getIntegerVoterDetails
import com.pngelectoral.utility.CommonMethods.getLongVoterDetails
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

class TransferInVoterDetailsActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityTransferInVoterDetailsBinding? = null
    private var transferInVoterDetailsViewModel: TransferInVoterDetailsViewModel? = null
    var voterDetailsResponse: VotersList = VotersList()
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
    private var occupationListArray: List<OccupationList>? = ArrayList()

    /**Define values to store selected mka data.*/
    var mkaDistrictId = 0
    var mkaLlgId = 0
    var mkaWardId = 0
    var mkaLocationId = 0
    var mkaClanId = 0
    var mkADistrictName = ""
    var mkALlgName = ""
    var mkAWardName = ""

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


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_transfer_in_voter_details
        )
        transferInVoterDetailsViewModel =
            ViewModelProvider(this).get(TransferInVoterDetailsViewModel::class.java)
        binding?.transferInVoterDetailsViewModel = transferInVoterDetailsViewModel

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
        form11ImagePath = intent.getStringExtra("Form11_Image_Path")!!
        voterImagePath = intent.getStringExtra("Voter_Image_Path")!!

        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )

        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase

        voterDetailsResponse =
            intent.getSerializableExtra(ApiConstants.VOTER_DETAILS) as VotersList

        if (voterImagePath.isNotEmpty()) {
            val image =
                File(voterImagePath)

            Glide.with(this).load(Uri.fromFile(image))
                .into(binding?.ivVoterImage!!)
        }

        voterDetailsResponse = intent.getSerializableExtra(ApiConstants.VOTER_DETAILS) as VotersList
        binding?.tvVoterName?.text = CommonMethods.getVoterName(voterDetailsResponse)

        when (securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "")) {
            ApiConstants.ROLE_DPO -> {
                binding?.etMKADistrict?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding?.etMKALlg?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding?.etMKAWard?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding?.etMKALocation?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding?.etMKAClan?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }

            ApiConstants.ROLE_MKA -> {
                binding?.etMKADistrict?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding?.etMKALlg?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding?.etMKAWard?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }

            else -> {
                binding?.etMKADistrict?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding?.etMKALlg?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding?.etMKAWard?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding?.etMKALocation?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding?.etMKAClan?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        }

        setFooterData()
        CommonMethods.showProgress(this)
        getOccupationListFromDB()
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


        regionId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_REGION_ID, 0)
        provinceId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_PROVINCE_ID, 0)

        if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
            districtId = loggedInDPOUserData.district.toInt()
            llgId = loggedInDPOUserData.llg.toInt()
            wardId = loggedInDPOUserData.ward.toInt()
            llgId = securedSharedPreferenceWrapper.getInt(
                "TEMP_LLGId",
                0
            )

            if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO) {
                wardId = securedSharedPreferenceWrapper.getInt(
                    "TEMP_WardId",
                    0
                )
            }

            locationId =
                securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LOCATION_ID, 0)
            //locationId = loggedInDPOUserData.location.toInt()
            binding?.etOpenElectorate?.text = loggedInDPOUserData.districtName
            binding?.etLLG?.text = loggedInDPOUserData.llgName
            binding?.etWardNoAndName?.text = loggedInDPOUserData.wardName
//            if (locationId != 0) {
//                binding?.etLocationOrVillage?.text =
//                    loggedInDPOUserData.locationName
//                binding?.etLocationOrVillage?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
//            }

            if (locationId != 0) {
                binding?.etLocationOrVillage?.text =
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LOCATION, "")
                binding?.etLocationOrVillage?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }

            /**Set MKA fields values*/
            mkaDistrictId = voterDetailsResponse.mkaDistrictId
            mkaLlgId = voterDetailsResponse.mkaLlgId
            mkaWardId = voterDetailsResponse.mkaWardId
            mkaLocationId = voterDetailsResponse.mkaLocationId

            binding?.etMKADistrict?.text = voterDetailsResponse.mkaDistrictName
            binding?.etMKALlg?.text = voterDetailsResponse.mkaLlgName
            binding?.etMKAWard?.text = voterDetailsResponse.mkaWardName
            if(mkaLocationId != 0) {
                binding?.etMKALocation?.text = voterDetailsResponse.mkaLocationName
                binding?.etMKALocation?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }
        }else{
            districtId = voterDetailsResponse.districtId
            llgId = voterDetailsResponse.llgId
            wardId = voterDetailsResponse.wardId
            llgId = securedSharedPreferenceWrapper.getInt(
                "TEMP_LLGId",
                0
            )

            wardId = securedSharedPreferenceWrapper.getInt(
                "TEMP_WardId",
                0
            )
            locationId =
                securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LOCATION_ID, 0)

            if (locationId == 0) {
                locationId = voterDetailsResponse.locationId
            }

            binding?.etOpenElectorate?.text = voterDetailsResponse.districtName
            binding?.etLLG?.text = voterDetailsResponse.llgName
            binding?.etWardNoAndName?.text = voterDetailsResponse.wardName

            binding?.etClan?.setText(getVoterDetails(voterDetailsResponse.clan)!!)

            if (voterDetailsResponse.locationId != 0) {
                binding?.etLocationOrVillage?.text =
                    voterDetailsResponse.locationName
                binding?.etLocationOrVillage?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }
            else if (locationId != 0) {
                binding?.etLocationOrVillage?.text =
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LOCATION, "")
                binding?.etLocationOrVillage?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }

            binding?.etLocationOrVillage?.isEnabled = false

            /**Set MKA fields values*/
            mkaDistrictId = loggedInDPOUserData.mkaDistrict.toInt()
            mkaLlgId = loggedInDPOUserData.mkallg.toInt()
            mkaWardId = loggedInDPOUserData.mkaWard.toInt()
            mkaLocationId = loggedInDPOUserData.mkaLocation.toInt()

            binding?.etMKADistrict?.text = loggedInDPOUserData.mkaDistrictName
            binding?.etMKALlg?.text = loggedInDPOUserData.mkallgName
            binding?.etMKAWard?.text = loggedInDPOUserData.mkaWardName
            if(mkaLocationId != 0) {
                binding?.etMKALocation?.text = loggedInDPOUserData.mkaLocationName
                binding?.etMKALocation?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }
        }

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

        binding?.etProvince?.text =
            securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_PROVINCE, "")

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
        }

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
        if (getVoterDetails(voterDetailsResponse.provinceOfBirth)!!.isEmpty()) {
            if (voterDetailsResponse.provOfBirthFK != 0)
                getVoterProvinceOfBirthName()
        } else {
            binding?.etProvinceOfBirth?.setText(getVoterDetails(voterDetailsResponse.provinceOfBirth)!!)
        }

        if (getVoterDetails(voterDetailsResponse.gender)!! == "F")
            binding?.etGender?.text = "Female"
        else if (getVoterDetails(voterDetailsResponse.gender)!! == "M")
            binding?.etGender?.text = "Male"
        else
            binding?.etGender?.text = ""

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
            binding?.etPersonWithDisability?.text = "Yes"
        else
            binding?.etPersonWithDisability?.text = "No"

        binding?.etReligion?.setText(getVoterDetails(voterDetailsResponse.religion)!!)

        binding?.etFathersFirstName?.setText(getVoterDetails(voterDetailsResponse.fatherFName)!!)
        binding?.etFathersLastName?.setText(getVoterDetails(voterDetailsResponse.fatherLName)!!)
        binding?.etMothersFirstName?.setText(getVoterDetails(voterDetailsResponse.motherFName)!!)
        binding?.etMothersLastName?.setText(getVoterDetails(voterDetailsResponse.motherLName)!!)

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

        binding?.etStatus?.text = getString(R.string.transferred_in)
        binding?.etComment?.setText(getVoterDetails(voterDetailsResponse.comment)!!)

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

    private fun getOccupation(occupationFK: Int) {
        launch(Dispatchers.Main) {
            val result: OccupationList? = withContext(Dispatchers.IO) {
                dataBaseInstance.occupationListDao()?.getOccupation(occupationFK)
            }
            binding?.etOccupation?.text = result!!.occupationName
        }
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

            if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LOCATION, "")
                    .isEmpty()
            ) {
                if (wardId > 0) {
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
                        "Please select location/village\""
                    )
            }
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


        binding?.btnSubmit?.setOnClickListener {
            validateVoterDetails()
        }
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

    private fun getClanList(clans: java.util.ArrayList<ClanListData>): Array<String> {
        val clanList: java.util.ArrayList<String> = java.util.ArrayList()
        //clanList.add("All")
        for (clan in clans) {
            clanList.add(clan.clanName)
        }
        return clanList.toArray(arrayOfNulls<String>(clanList.size))
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
                    filterClanId = clanArray!![clickItem ].clanId
                    filterClanName = clanArray!![clickItem ].clanName
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
//        val occupationPK = if (occupation.isNotEmpty()
//            && occupationListArray!!.isNotEmpty() && binding?.etOccupation?.tag != null
//        ) {
//            occupationListArray!![binding?.etOccupation?.tag as Int].occupationPK
//        } else {
//            voterDetailsResponse.occupationFK
//        }


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
        val locationIdValue =
            if (locationArray!!.isNotEmpty() && binding?.etLocationOrVillage?.tag != null) {
                locationArray!![binding?.etLocationOrVillage?.tag as Int].locationId
            } else if (locationId != 0) {
                locationId
            } else {
                voterDetailsResponse.locationId
            }

        val clanId = filterClanId
        val clan = binding?.etClan?.text.toString().trim()
        val subClan = binding?.etSubClan?.text.toString().trim()
        val restHouse = binding?.etRestHouse?.text.toString().trim()

        val sectionNumber = binding?.etSectionNo?.text.toString().trim()
        val lotNumber = binding?.etLotNo?.text.toString().trim()
        val streetName = binding?.etStreetName?.text.toString().trim()
        val suburb = binding?.etSuburb?.text.toString().trim()

        val status = binding?.etStatus?.text.toString()
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

          /*  binding?.etMKALocation?.text?.trim()?.isEmpty() == true -> {
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
                    val builder: android.app.AlertDialog.Builder =
                        android.app.AlertDialog.Builder(this)
                    builder.setMessage(getString(R.string.save_voter_confirmation))
                        .setCancelable(false)
                        .setPositiveButton(
                            "Yes"
                        ) { dialog, id ->
                            val updatedVoterInformation = VotersList()
                            updatedVoterInformation.tempElectorId =
                                abs(System.currentTimeMillis().toInt())
                            updatedVoterInformation.electorId =
                                getLongVoterDetails(voterDetailsResponse.electorId)
                            updatedVoterInformation.oldElectorID =
                                getLongVoterDetails(voterDetailsResponse.oldElectorID)
                            updatedVoterInformation.locationId = locationIdValue
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
                            updatedVoterInformation.dob = CommonMethods.getDOBAPIString(dateOfBirth)
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
                                CommonMethods.getIntegerVoterDetailsFromString(sectionNumber)
                            updatedVoterInformation.lotNo =
                                CommonMethods.getIntegerVoterDetailsFromString(lotNumber)
                            updatedVoterInformation.streetName = getVoterDetails(streetName)
                            updatedVoterInformation.suburbTownSettlement = getVoterDetails(suburb)
                            updatedVoterInformation.familyFK = 0
                            updatedVoterInformation.familyNumber =
                                CommonMethods.getIntegerVoterDetailsFromString(familyNumber)
                            updatedVoterInformation.familyName = familyName
                            updatedVoterInformation.familySortOrder =
                                CommonMethods.getIntegerVoterDetailsFromString(familyOrderNumber)
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
                            updatedVoterInformation.electorProfileImageUrl =
                                getVoterDetails(voterDetailsResponse.electorProfileImageUrl)
                            updatedVoterInformation.electorFormImage = form11ImagePath
                            updatedVoterInformation.electorFormImageUrl =
                                getVoterDetails(voterDetailsResponse.electorFormImageUrl)
                            updatedVoterInformation.electorProfileImageFile =
                                getVoterDetails(voterDetailsResponse.electorProfileImageFile)
                            updatedVoterInformation.electorFormImageFile =
                                getVoterDetails(voterDetailsResponse.electorFormImageFile)
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

                            updatedVoterInformation.transferOutRegionId =
                                getIntegerVoterDetails(voterDetailsResponse.regionId)
                            updatedVoterInformation.transferOutProvinceId =
                                getIntegerVoterDetails(voterDetailsResponse.provinceId)
                            updatedVoterInformation.transferOutDistrictId =
                                getIntegerVoterDetails(voterDetailsResponse.districtId)
                            updatedVoterInformation.transferOutLLGId =
                                getIntegerVoterDetails(voterDetailsResponse.llgId)
                            updatedVoterInformation.transferOutWardId =
                                getIntegerVoterDetails(voterDetailsResponse.wardId)
                            updatedVoterInformation.transferOutLocationId =
                                getIntegerVoterDetails(voterDetailsResponse.locationId)
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
                            updatedVoterInformation.transferOutMkaDistrictId =
                                getIntegerVoterDetails(voterDetailsResponse.mkaDistrictId)

                            updatedVoterInformation.transferOutMkaLlgId =
                                getIntegerVoterDetails(voterDetailsResponse.mkaLlgId)

                            updatedVoterInformation.transferOutMkaWardId =
                                getIntegerVoterDetails(voterDetailsResponse.mkaWardId)

                            updatedVoterInformation.transferOutMkaLocationId =
                                getIntegerVoterDetails(voterDetailsResponse.mkaLocationId)

                            updateTransferredInVoterInformationToDB(updatedVoterInformation)
                        }
                        .setNegativeButton(
                            "No"
                        ) { dialog, id ->
                            startActivity(Intent(this, HomeActivity::class.java))
                            finishAffinity()
                            dialog.cancel()
                        }
                    val alert: android.app.AlertDialog = builder.create()
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

    private fun getNotSpecifiedOccupationId(): Int? {
        var notSpecifiedId = 46
        for (occupation in occupationListArray!!) {
            if (occupation.occupationName == "Not Specified") {
                notSpecifiedId = occupation.occupationPK
            }
        }
        return notSpecifiedId
    }

    private fun updateTransferredInVoterInformationToDB(updatedVoterInformation: VotersList) {
        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                saveTransferredInVoterInformationToDB(updatedVoterInformation)
            }
            showTransferredInSuccessMessage()
        }
    }

    private fun saveTransferredInVoterInformationToDB(updatedVoterInformation: VotersList): Boolean {
        if (updatedVoterInformation.uploadId.isNullOrEmpty()) {
            updatedVoterInformation.uploadId = java.util.UUID.randomUUID().toString().replace("-", "")
        }
        updatedVoterInformation.isSyncToServer = 0
        updatedVoterInformation.isDataUploaded = 0
        dataBaseInstance.transferInVotersListDao().insert(updatedVoterInformation)
        return true
    }


    private fun showTransferredInSuccessMessage() {
        ApiConstants.TSV_VOTER_TRANSFERRED = true
        navigateToHomeActivity()
//        alert("Voter Transferred In successfully") {
//            okButton {
//                navigateToHomeActivity()
//            }
//        }.show().setCancelable(false)

    }

    private fun navigateToHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finishAffinity()
    }

    private fun showAlertMessage(alertMessage: String, currentEditText: AppCompatEditText?) {

        alert(alertMessage) {
            okButton {
                currentEditText!!.requestFocus()
            }
        }.show()

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

        // Setting MinDate
        val calMinDate = Calendar.getInstance()
        calMinDate.add(Calendar.YEAR, -100)
        bindingDatePicker?.datePicker!!.minDate = calMinDate.timeInMillis

        // Set lastDateOfEnrolment as MaxDate
        val year = lastDateOfEnrolment.split("-")[2].toInt()
        val month = lastDateOfEnrolment.split("-")[1].toInt() - 1
        val day = lastDateOfEnrolment.split("-")[0].toInt()
        val lastEnrolmentDate = Calendar.getInstance()
        lastEnrolmentDate[year, month] = day //Year,Month -1,Day
        bindingDatePicker?.datePicker!!.maxDate = lastEnrolmentDate.timeInMillis


        bindingDatePicker?.tvCancel?.setOnClickListener {
            mAlertDatePickerDialog!!.dismiss()
        }

        bindingDatePicker?.tvOk?.setOnClickListener {

            mSelectedCalender[Calendar.YEAR] = bindingDatePicker?.datePicker!!.year
            mSelectedCalender[Calendar.MONTH] = bindingDatePicker?.datePicker!!.month
            mSelectedCalender[Calendar.DAY_OF_MONTH] = bindingDatePicker?.datePicker!!.dayOfMonth
            binding?.etDateOfBirth?.text = mDisplayDateFormat.format(mSelectedCalender.time)
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

        val yearMaxValue = Calendar.getInstance().get(Calendar.YEAR)
        val yearMinValue = yearMaxValue - 200

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

            if (dobDate!!.after(ldeDate)) {
                showAlertMessage("Date of birth should be before or equal to the Last date of enrolment")
            } else {
                binding?.etDateOfBirth?.text = dateOfBirthTemp
            }

            mYearPickerAlertDialog!!.dismiss()
        }
        mYearPickerAlertDialog?.show()

    }

    private fun showAlertMessage(alertMessage: String) {

        alert(alertMessage) {
            okButton {
            }
        }.show()

    }


}
