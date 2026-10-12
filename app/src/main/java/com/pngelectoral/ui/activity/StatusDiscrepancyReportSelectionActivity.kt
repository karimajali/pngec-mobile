package com.pngelectoral.ui.activity

import ApiConstants
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityStatusDiscrepancyReportSelectionBinding
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.viewModel.RegistrationViewModel
import com.pngelectoral.utility.CommonMethods
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import kotlin.coroutines.CoroutineContext

class StatusDiscrepancyReportSelectionActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityStatusDiscrepancyReportSelectionBinding? = null
    private var registrationViewModel: RegistrationViewModel? = null
    lateinit var dataBaseInstance: AppDatabase

    var provinceId = 0
    var districtId = 0
    var llgId = 0
    var wardId = 0
    var locationId = 0

    var districtName = ""
    var llgName = ""
    private var wardNoAndName = ""
    var wardNumber = 0
    var wardName = ""
    var locationName = ""

    lateinit var loggedInDPOUserData: DPOUserData

    private var mkaLocationArray =
        java.util.ArrayList<MKAGeoListModel.GeoListData.LlG.Ward.Location>()


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_status_discrepancy_report_selection
        )

        registrationViewModel =
            ViewModelProvider(this).get(RegistrationViewModel::class.java)
        binding?.registrationViewModel = registrationViewModel

        job = Job()
        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )
        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase

        provinceId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_PROVINCE_ID, 0)
        binding?.etProvince?.text =
            securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_PROVINCE, "")

        if (securedSharedPreferenceWrapper.getString(
                ApiConstants.ROLE,
                ""
            ) == ApiConstants.ROLE_DPO
        ) {
            binding?.llProvince?.visibility = View.VISIBLE
            districtId = loggedInDPOUserData.district.toInt()
            llgId = loggedInDPOUserData.llg.toInt()
            wardId = loggedInDPOUserData.ward.toInt()
            binding?.etDistrict?.text = loggedInDPOUserData.districtName
            llgName = loggedInDPOUserData.llgName
            binding?.etLLG?.text = llgName
            wardName = loggedInDPOUserData.wardName
            if (wardName.isNotEmpty()) {
                getWardNumberAndNameUsingCoroutines()
            }
        } else {
            binding?.llProvince?.visibility = View.GONE
            districtId = loggedInDPOUserData.mkaDistrict.toInt()
            llgId = loggedInDPOUserData.mkallg.toInt()
            wardId = loggedInDPOUserData.mkaWard.toInt()
            binding?.etDistrict?.text = loggedInDPOUserData.mkaDistrictName
            llgName = loggedInDPOUserData.mkallgName
            binding?.etLLG?.text = llgName
            wardName = loggedInDPOUserData.mkaWardName
            if (wardName.isNotEmpty()) {
                getMKAWardNumberAndNameUsingCoroutines()
            }
            binding?.etLLG?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding?.etWardName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }


        setFooterData()
        setOnclickListeners()
    }


    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    private fun getWardNumberAndNameUsingCoroutines() {
        launch(Dispatchers.Main) {
            val result: WardList? = withContext(Dispatchers.IO) {
                dataBaseInstance.wardListDao()?.getDPOWard(wardId)
            }
            wardNumber = result!!.wardNumber
            binding?.etWardName?.text = "$wardNumber & $wardName"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getMKAWardNumberAndNameUsingCoroutines() {
        launch(Dispatchers.Main) {
            val result: MKAGeoListData? = withContext(Dispatchers.IO) {
                dataBaseInstance.geoListDao()
                    ?.getDataForDistrict(districtId)
            }
            val mkaLlgArray = Gson().fromJson<java.util.ArrayList<MKAGeoListModel.GeoListData.LlG>>(
                result?.llGs ?: "",
                object : TypeToken<java.util.ArrayList<MKAGeoListModel.GeoListData.LlG>?>() {}.type
            ) ?: kotlin.run { arrayListOf() }
            val llgResult = mkaLlgArray.filter { llg ->
                llg.llgId == llgId
            }
            var wardResult: List<MKAGeoListModel.GeoListData.LlG.Ward>? = null
            if (llgResult.isNotEmpty()) {
                wardResult = llgResult.single().wards?.filter { llg ->
                    llg.wardId == wardId
                }
            }
            wardNumber = wardResult?.single()?.wardNumber ?: 0
            wardNoAndName = "$wardNumber & $wardName"
            binding?.etWardName?.text = "$wardNumber & $wardName"
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

    private fun setOnclickListeners() {


        binding?.btnHome?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }

        binding?.btnBack?.setOnClickListener {
            finish()
        }


//        binding?.rlProvince?.setOnClickListener {
//            CommonMethods.showProgress(this)
//            getAllProvinceUsingCoRoutines()
//        }
//
//        binding?.rlDistrict?.setOnClickListener {
//            if (provinceListArray!!.isNotEmpty() && binding?.etProvince?.tag != null)
//                provinceId = provinceListArray!![binding?.etProvince?.tag as Int].provinceId
//            getDistrictUsingCoRoutines()
//
//        }

        if (securedSharedPreferenceWrapper.getString(
                ApiConstants.ROLE,
                ""
            ) == ApiConstants.ROLE_DPO
        ) {
            binding?.rlLLG?.setOnClickListener {
//            if (districtListArray?.size!! > 0 && binding?.etDistrict?.text!!.isNotEmpty()) {
//                CommonMethods.showProgress(this)
//                getLLGListUsingCoRoutines()
//            } else
//                showAlertMessage(
//                    "Please select district"
//                )

                if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_LLG, "")
                        .isEmpty()
                ) {
                    CommonMethods.showProgress(this)
                    getLLGListUsingCoRoutines()
                }

            }

            binding?.rlWardName?.setOnClickListener {
                if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_WARD, "")
                        .isEmpty()
                ) {
                    if (binding?.etLLG?.text.toString().isEmpty()) {
                        showAlertMessage(
                            "Please select llg"
                        )
                    } else {
                        if (llgListArray!!.isNotEmpty() && binding?.etLLG?.tag != null)
                            llgId = llgListArray!![binding?.etLLG?.tag as Int].llgId

                        CommonMethods.showProgress(this)
                        getWardNoAndNameListUsingCoRoutines(true)
                    }


//            if (llgListArray?.size!! > 0 && binding?.etLLG?.text!!.isNotEmpty()) {
//                CommonMethods.showProgress(this)
//                getWardNoAndNameListUsingCoRoutines(false)
//            } else if (binding?.etLLG?.text!!.isNotEmpty()) {
//                CommonMethods.showProgress(this)
//                getWardNoAndNameListUsingCoRoutines(true)
//            } else
//                showAlertMessage(
//                    "Please select llg"
//                )
                }

            }
        }

        binding?.rlLocation?.setOnClickListener {
            if (binding?.etWardName?.text.toString().isEmpty()) {
                showAlertMessage(
                    "Please select ward"
                )
            } else {
                CommonMethods.showProgress(this)
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    getLocationListFromDB()
                } else {
                    getMKALocationListFromDB()
                }
            }
        }

        binding?.btnProceed?.setOnClickListener {
            navigateToDeviceOperatorReportModule()
        }

    }

    private fun navigateToDeviceOperatorReportModule() {

        districtName = binding?.etDistrict?.text.toString()
        if (districtListArray!!.isNotEmpty() && districtName.isNotEmpty()) {
            districtId = districtListArray!![binding?.etDistrict?.tag as Int].districtId
        }

        llgName = binding?.etLLG?.text.toString()
        if (llgListArray!!.isNotEmpty() && llgName.isNotEmpty()) {
            llgId = llgListArray!![binding?.etLLG?.tag as Int].llgId
        }

        llgName = binding?.etLLG?.text.toString()
        if (llgListArray!!.isNotEmpty() && llgName.isNotEmpty()) {
            llgId = llgListArray!![binding?.etLLG?.tag as Int].llgId
        }

        wardNoAndName = binding?.etWardName?.text.toString()
        if (wardNoAndNameArray!!.isNotEmpty() && wardNoAndName.isNotEmpty()) {
            wardId = wardNoAndNameArray!![binding?.etWardName?.tag as Int].wardId
            wardNumber = wardNoAndNameArray!![binding?.etWardName?.tag as Int].wardNumber
            wardName = wardNoAndNameArray!![binding?.etWardName?.tag as Int].wardName
        }

        when {
            districtName.isEmpty() -> {
                showAlertMessage("Please select district")
            }

            llgName.isEmpty() -> {
                showAlertMessage("Please select llg")
            }

            wardName.isEmpty() -> {
                showAlertMessage("Please select ward name")
            }

            else -> {

                if (CommonMethods.checkInternetConnection(application)) {
                    navigateToStagingReportScreen()
                } else {
                    showAlertMessage("Please check internet connection and try again.")
                }

            }
        }

    }


    private fun navigateToStagingReportScreen() {

        val intent = Intent(this, StatusDiscrepancyReportActivity::class.java)
        intent.putExtra("provinceName", binding?.etProvince?.text)
        intent.putExtra("districtName", binding?.etDistrict?.text)
        intent.putExtra("llgName", llgName)
        intent.putExtra("wardName", wardName)
        intent.putExtra("locationName", locationName)
        intent.putExtra("wardNumber", wardNumber)
        intent.putExtra("provinceId", provinceId)
        intent.putExtra("districtId", districtId)
        intent.putExtra("llgId", llgId)
        intent.putExtra("wardId", wardId)
        intent.putExtra("locationId", locationId)
        startActivity(intent)

    }


    private fun getAllProvinceUsingCoRoutines() {
        launch(Dispatchers.Main) {
            val result: List<ProvinceList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.provinceListDao().all
            }
            showProvinceOptions(result)
        }
    }

    var provinceListArray: ArrayList<ProvinceList>? = ArrayList()
    private fun showProvinceOptions(result: List<ProvinceList>?) {
        provinceListArray = ArrayList(result!!)
        showSingleSelectionDialog(
            CommonMethods.getProvinceList(provinceListArray!!),
            binding?.etProvince!!,
            "Province"
        )
    }

    private fun getDistrictUsingCoRoutines() {
        launch(Dispatchers.Main) {
            val result: List<DistrictList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.districtListDao().getDistrictOfProvision(provinceId)
            }
            showDistrictOptions(result)
        }

    }


    private var districtListArray: ArrayList<DistrictList>? = ArrayList()
    private fun showDistrictOptions(result: List<DistrictList>?) {
        districtListArray = ArrayList(result!!)
        showSingleSelectionDialog(
            CommonMethods.getDistrictList(districtListArray!!),
            binding?.etDistrict!!,
            "District"
        )
    }

    private fun getLLGListUsingCoRoutines() {
        launch(Dispatchers.Main) {
            val result: List<LLGList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.llgListDao()
                    ?.getLLGOfProvisionAndDistrict(
                        provinceId, districtId
                    )
            }
            showLLGOptions(result)
        }
    }


    var llgListArray: ArrayList<LLGList>? = ArrayList()
    private fun showLLGOptions(result: List<LLGList>?) {
        llgListArray = ArrayList(result!!)
        showSingleSelectionDialog(
            CommonMethods.getLLGList(llgListArray!!),
            binding?.etLLG!!,
            "LLG"
        )
    }

    private fun getWardNoAndNameListUsingCoRoutines(llgAssigned: Boolean) {
        launch(Dispatchers.Main) {
            val result: List<WardList>? = withContext(Dispatchers.IO) {
                if (llgAssigned) {
                    dataBaseInstance.wardListDao()
                        ?.getWardListOfProvisionAndDistrictAndLLG(
                            provinceId,
                            districtId,
                            llgId
                        )
                } else {
                    dataBaseInstance.wardListDao()
                        ?.getWardListOfProvisionAndDistrictAndLLG(
                            provinceId,
                            districtId,
                            llgListArray!![binding?.etLLG?.tag as Int].llgId
                        )
                }
            }
            showWardNoAndNameOptions(result)
        }
    }

    var wardNoAndNameArray: ArrayList<WardList>? = ArrayList()
    private fun showWardNoAndNameOptions(result: List<WardList>?) {
        wardNoAndNameArray = ArrayList(result!!)
        showSingleSelectionDialog(
            CommonMethods.getWardList(wardNoAndNameArray!!),
            binding?.etWardName!!,
            "WardNoAndName"
        )
    }

    private fun getLocationListFromDB() {
        launch(Dispatchers.Main) {
            val result: List<LocationList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.locationListDao()
                    ?.getLocationListOfProvinceAndDistrictAndLLGAndWard(
                        provinceId,
                        districtId,
                        llgId,
                        wardId
                    )
            }
            showLocationOptions(result)
        }
    }

    private fun getMKALocationListFromDB() {
        launch(Dispatchers.Main) {
            val result: List<MKAGeoListModel.GeoListData.LlG.Ward.Location>? =
                withContext(Dispatchers.IO) {
                    getMKALocationListQuery()
                }
            showMKALocationOptions()
        }
    }

    private fun getMKALocationListQuery(): List<MKAGeoListModel.GeoListData.LlG.Ward.Location>? {
        val districtResult: List<MKAGeoListData>? =
            dataBaseInstance.geoListDao()?.all ?: kotlin.run { arrayListOf() }
        val mkaDistrictArray = districtResult?.let { java.util.ArrayList<MKAGeoListData>(it) }
            ?: kotlin.run { arrayListOf() }

        val llgResult = mkaDistrictArray.filter { district ->
            district.districtId == districtId
        }
        var mkaLlgArray = java.util.ArrayList<MKAGeoListModel.GeoListData.LlG>()
        if (llgResult.isNotEmpty())
            mkaLlgArray = Gson().fromJson<java.util.ArrayList<MKAGeoListModel.GeoListData.LlG>>(
                llgResult.single().llGs ?: "",
                object : TypeToken<java.util.ArrayList<MKAGeoListModel.GeoListData.LlG>?>() {}.type
            ) ?: kotlin.run { arrayListOf() }
        val wardResult = mkaLlgArray.filter { llg ->
            llg.llgId == llgId
        }
        val mkaWardArray = java.util.ArrayList<MKAGeoListModel.GeoListData.LlG.Ward>()
        if (wardResult.isNotEmpty())
            mkaWardArray.addAll(wardResult.single().wards ?: arrayListOf())
        val locationResult = mkaWardArray.filter { llg ->
            llg.wardId == wardId
        }
        mkaLocationArray.clear()
        if (locationResult.isNotEmpty())
            mkaLocationArray.addAll(locationResult.single().locations ?: arrayListOf())
        return mkaLocationArray
    }

    private fun showMKALocationOptions() {
        val locationList: java.util.ArrayList<String> = java.util.ArrayList()
//        locationList.add("All Locations")
        for (location in mkaLocationArray) {
            locationList.add(location.locationName ?: "")
        }
        showSingleSelectionDialog(
            locationList.toArray(arrayOfNulls<String>(locationList.size)),
            binding?.etLocation!!,
            "Location",
        )

    }

    private var locationArray: ArrayList<LocationList>? = ArrayList()
    private fun showLocationOptions(result: List<LocationList>?) {
        locationArray = ArrayList(result!!)
        showSingleSelectionDialog(
            CommonMethods.getLocationList(locationArray!!),
            binding?.etLocation!!,
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
                    "Province" -> {
                        provinceId = provinceListArray!![clickItem].provinceId

                        binding?.etDistrict?.tag = -1
                        binding?.etDistrict?.text = ""


                        binding?.etLLG?.tag = -1
                        binding?.etLLG?.text = ""

                        binding?.etWardName?.tag = -1
                        binding?.etWardName?.text = ""

                        binding?.etLocation?.tag = -1
                        binding?.etLocation?.text = ""

                        districtListArray?.clear()
                        llgListArray?.clear()
                        wardNoAndNameArray?.clear()
                        locationArray?.clear()

                        districtId = 0
                        llgId = 0
                        wardId = 0
                        locationId = 0

                        districtName = ""
                        llgName = ""
                        wardName = ""
                        locationName = ""

                    }

                    "District" -> {

                        districtId = districtListArray!![clickItem].districtId

                        binding?.etLLG?.tag = -1
                        binding?.etLLG?.text = ""

                        binding?.etWardName?.tag = -1
                        binding?.etWardName?.text = ""

                        binding?.etLocation?.tag = -1
                        binding?.etLocation?.text = ""

                        llgListArray?.clear()
                        wardNoAndNameArray?.clear()
                        locationArray?.clear()

                        llgId = 0
                        wardId = 0
                        locationId = 0

                        llgName = ""
                        wardName = ""
                        locationName = ""

                    }

                    "LLG" -> {

                        llgId = llgListArray!![clickItem].llgId

                        binding?.etWardName?.tag = -1
                        binding?.etWardName?.text = ""

                        binding?.etLocation?.tag = -1
                        binding?.etLocation?.text = ""

                        wardNoAndNameArray?.clear()
                        locationArray?.clear()

                        wardId = 0
                        locationId = 0

                        wardName = ""
                        locationName = ""
                    }

                    "WardNoAndName" -> {

                        wardId = wardNoAndNameArray!![clickItem].wardId

                        binding?.etLocation?.tag = -1
                        binding?.etLocation?.text = ""

                        locationArray?.clear()

                        locationId = 0

                        locationName = ""
                    }

                    "Location" -> {
                        if (securedSharedPreferenceWrapper.getString(
                                ApiConstants.ROLE,
                                ""
                            ) == ApiConstants.ROLE_DPO
                        )
                            locationId = locationArray!![clickItem].locationId
                        else
                            locationId = mkaLocationArray[clickItem].locationId ?: 0
                        locationName = singleChoiceItems[clickItem]

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


    private fun showAlertMessage(alertMessage: String) {

        alert(alertMessage) {
            okButton {
            }
        }.show()

    }


}
