package com.pngelectoral.ui.activity

import ApiConstants
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import android.text.InputType
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityTransferInBinding
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.retrofit.ApiClient
import com.pngelectoral.ui.retrofit.IFailureHandler
import com.pngelectoral.ui.retrofit.ISuccessHandler
import com.pngelectoral.ui.retrofit.WSClient
import com.pngelectoral.ui.viewModel.TransferInViewModel
import com.pngelectoral.utility.CommonMethods
import com.pngelectoral.utility.helper.DialogSpinner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import kotlin.coroutines.CoroutineContext

class TransferInActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityTransferInBinding? = null
    private var transferInViewModel: TransferInViewModel? = null

    var regionId = 0
    var provinceId = 0
    var districtId = 0
    var llgId = 0
    var wardId = 0
    var firstNameSearch = ""
    var lastNameSearch = ""
    var gender = ""
    var regionName = ""
    var provinceName = ""
    var districtName = ""
    var llgName = ""
    var wardNoAndName = ""
    var wardNumber = 0
    var wardName = ""

    lateinit var dataBaseInstance: AppDatabase
    lateinit var loggedInDPOUserData: DPOUserData
    private var mkaLlgArray = ArrayList<MKAGeoListModel.GeoListData.LlG>()
    private var mkaWardArray = ArrayList<MKAGeoListModel.GeoListData.LlG.Ward>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_transfer_in
        )

        transferInViewModel =
            ViewModelProvider(this).get(TransferInViewModel::class.java)
        binding?.transferInViewModel = transferInViewModel

        job = Job()

        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )
        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase

        regionId = loggedInDPOUserData.region.toInt()
        binding?.etRegion?.text = loggedInDPOUserData.regionName

        binding?.etFirstName?.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        binding?.etLastName?.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES

        when (securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "")) {
            ApiConstants.ROLE_DPO -> {
                binding?.llRegion?.visibility = View.VISIBLE
                binding?.llProvince?.visibility = View.VISIBLE
                districtId = loggedInDPOUserData.district.toInt()
                districtName = loggedInDPOUserData.districtName
                binding?.etDistrict?.text = districtName
                binding?.ivDistrict?.visibility = View.GONE
            }

            ApiConstants.ROLE_MKA -> {
                binding?.llRegion?.visibility = View.GONE
                binding?.llProvince?.visibility = View.GONE
                districtId = loggedInDPOUserData.mkaDistrict.toInt()
                districtName = loggedInDPOUserData.mkaDistrictName
                binding?.etDistrict?.text = districtName
                binding?.ivDistrict?.visibility = View.GONE
            }

            else -> {
                binding?.llRegion?.visibility = View.VISIBLE
                binding?.llProvince?.visibility = View.VISIBLE
                districtId = loggedInDPOUserData.district.toInt()
                districtName = loggedInDPOUserData.districtName
                binding?.etDistrict?.text = districtName
                binding?.ivDistrict?.visibility = View.GONE
            }
        }

        setFooterData()
        clearSelection()
        setOnclickListeners()
    }

    private fun setFooterData() {

        val dpoName = securedSharedPreferenceWrapper.getString(
            ApiConstants.DPO_NAME,
            ""
        )
        val version = packageManager.getPackageInfo(packageName, 0).versionName

        var tvBottomDataTitle: String = ""

        when (securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "")) {
            ApiConstants.ROLE_DPO -> {
                val districtName = loggedInDPOUserData.districtName
                val llgName = loggedInDPOUserData.llgName
                val wardName = loggedInDPOUserData.wardName
                tvBottomDataTitle = if (wardName.isEmpty())
                    "Version: $version | District: $districtName | DPO: $dpoName"
                else {
                    val wardNameDisplay = "$districtName/$llgName/$wardName"
                    "Version: $version | Ward: $wardNameDisplay | DPO: $dpoName"
                }
            }

            ApiConstants.ROLE_MKA -> {
                val districtName = loggedInDPOUserData.mkaDistrictName
                val llgName = loggedInDPOUserData.mkallgName
                val wardName = loggedInDPOUserData.mkaWardName
                tvBottomDataTitle = if (wardName.isEmpty())
                    "Version: $version | District: $districtName | DPO: $dpoName"
                else {
                    val wardNameDisplay = "$districtName/$llgName/$wardName"
                    "Version: $version | Ward: $wardNameDisplay | DPO: $dpoName"
                }
            }

            else -> {
                val districtName = loggedInDPOUserData.districtName
                val llgName = loggedInDPOUserData.llgName
                val wardName = loggedInDPOUserData.wardName
                tvBottomDataTitle = if (wardName.isEmpty())
                    "Version: $version | District: $districtName | DPO: $dpoName"
                else {
                    val wardNameDisplay = "$districtName/$llgName/$wardName"
                    "Version: $version | Ward: $wardNameDisplay | DPO: $dpoName"
                }
            }
        }

        binding?.tvBottomData?.text = tvBottomDataTitle
    }

    private fun clearSelection() {

        ApiConstants.TSV_REGION_ID = 0
        ApiConstants.TSV_REGION_NAME = ""

        ApiConstants.TSV_PROVINCE_ID = 0
        ApiConstants.TSV_PROVINCE_NAME = ""

        ApiConstants.TSV_DISTRICT_ID = 0
        ApiConstants.TSV_DISTRICT_NAME = ""

        ApiConstants.TSV_LLG_ID = 0
        ApiConstants.TSV_LLG_NAME = ""

        ApiConstants.TSV_WARD_ID = 0
        ApiConstants.TSV_WARD_NAME = ""
        ApiConstants.TSV_WARD_NUMBER = 0
        ApiConstants.TSV_WARD_DISPLAY_NAME = ""

        ApiConstants.TSV_VOTER_TRANSFERRED = false
    }


    private fun setOnclickListeners() {


        binding?.btnSearch?.setOnClickListener {
            searchVoters()
        }

        binding?.btnHome?.setOnClickListener {
            finish()

        }

        binding?.rlGender?.setOnClickListener {
            showGenderOptions()
        }

        binding?.rlRegion?.setOnClickListener {
            CommonMethods.showProgress(this)
            RegionListAsync().execute()
        }

        binding?.rlProvince?.setOnClickListener {
            CommonMethods.showProgress(this)
            if (regionListArray!!.isNotEmpty() && binding?.etRegion?.tag != null)
                regionId = regionListArray!![binding?.etRegion?.tag as Int].regionId
            ProvinceListAsync().execute()
        }

        binding?.rlDistrict?.setOnClickListener {
            if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
                if (provinceListArray?.size!! > 0 && binding?.etProvince?.text!!.isNotEmpty()) {
                    CommonMethods.showProgress(this)
                    DistrictListAsync().execute()
                } else
                    showAlertMessage(
                        "Please select province"
                    )
            }
        }

        binding?.rlLLG?.setOnClickListener {
            if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO) {
                if (districtListArray?.size!! > 0 && binding?.etDistrict?.text!!.isNotEmpty()) {
                    CommonMethods.showProgress(this)
                    LLGListAsync().execute()
                } else
                    showAlertMessage(
                        "Please select district"
                    )
            }else{
                getMKALlgListFromDB()
            }
        }

        binding?.rlWardName?.setOnClickListener {
            if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO) {
                if (llgListArray?.size!! > 0 && binding?.etLLG?.text!!.isNotEmpty()) {
                    CommonMethods.showProgress(this)
                    WardNoAndNameListAsync().execute()
                } else
                    showAlertMessage(
                        "Please select llg"
                    )
            }else{
                getMKAWardList()
            }
        }

    }

    /**Method to fetch mka llg from database.*/
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

    /**Method to show scrollable spinner dialog for mka llg.*/
    private fun showMKALlg(llgListArray: ArrayList<MKAGeoListModel.GeoListData.LlG>) {
        CommonMethods.hideProgress()
        DialogSpinner.with(this, ArrayList(llgListArray)).setEnableSearch(false)
            .setMap { value: MKAGeoListModel.GeoListData.LlG -> value.llgName ?: "" }
            .setOnValueSelectedCallback { model ->
                binding?.etLLG?.text = model.llgName ?: ""
                llgId = model.llgId ?: 0
                wardId = 0
                mkaWardArray.clear()
                binding?.etWardName?.text = ""
            }.build().show(fragmentManager = supportFragmentManager)
    }

    /**Method to get mkaward list form mka llg array.*/
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

    /**Method to show scrollable spinner dialog for mka ward.*/
    private fun showMKAWard(wardListArray: ArrayList<MKAGeoListModel.GeoListData.LlG.Ward>) {
        CommonMethods.hideProgress()
        DialogSpinner.with(this, ArrayList(wardListArray)).setEnableSearch(false)
            .setMap { value: MKAGeoListModel.GeoListData.LlG.Ward -> value.wardName ?: "" }
            .setOnValueSelectedCallback { model ->
                binding?.etWardName?.text = model.wardName ?: ""
                wardId = model.wardId ?: 0
            }.build().show(fragmentManager = supportFragmentManager)
    }

    private fun showGenderOptions() {

        val items = arrayOf<CharSequence>(
            "All",
            "Male",
            "Female"
        )
        val builder = AlertDialog.Builder(this)
        builder.setItems(items) { dialog, item ->
            binding?.etGender?.text = items[item]
        }
        builder.show()

    }

    @SuppressLint("StaticFieldLeak")
    internal inner class RegionListAsync : AsyncTask<Void?, Void?, List<RegionList>?>() {

        override fun doInBackground(vararg p0: Void?): List<RegionList>? {

            val dataBaseInstance =
                DatabaseClient.getInstance(this@TransferInActivity).appDatabase
            return dataBaseInstance?.regionListDao()?.all
        }

        override fun onPostExecute(result: List<RegionList>?) {
            super.onPostExecute(result)
            showRegionOptions(result)
        }
    }

    var regionListArray: List<RegionList>? = ArrayList()
    private fun showRegionOptions(result: List<RegionList>?) {

        regionListArray = result
        val regionList: Array<String> = getRegionList(ArrayList(result!!))
        showSingleSelectionDialog(
            regionList,
            binding?.etRegion!!,
            "Region"
        )

    }

    private fun getRegionList(regions: ArrayList<RegionList>): Array<String> {

        val regionList: ArrayList<String> = ArrayList()
        for (region in regions) {
            regionList.add(region.regionName)
        }
        return regionList.toArray(arrayOfNulls<String>(regionList.size))
    }

    @SuppressLint("StaticFieldLeak")
    internal inner class ProvinceListAsync : AsyncTask<Void?, Void?, List<ProvinceList>?>() {

        override fun doInBackground(vararg p0: Void?): List<ProvinceList>? {

            val dataBaseInstance =
                DatabaseClient.getInstance(this@TransferInActivity).appDatabase
            return dataBaseInstance?.provinceListDao()
                ?.getProvinceOfRegion(regionId)
        }

        override fun onPostExecute(result: List<ProvinceList>?) {
            super.onPostExecute(result)
            showProvinceOptions(result)
        }
    }

    var provinceListArray: ArrayList<ProvinceList>? = ArrayList()
    private fun showProvinceOptions(result: List<ProvinceList>?) {
        provinceListArray = ArrayList(result!!)
        val provinceList: Array<String> = getProvinceList(provinceListArray!!)
        showSingleSelectionDialog(
            provinceList,
            binding?.etProvince!!,
            "Province"
        )

    }

    private fun getProvinceList(provinces: ArrayList<ProvinceList>): Array<String> {

        val provinceList: ArrayList<String> = ArrayList()
        for (province in provinces) {
            provinceList.add(province.provinceName)
        }
        return provinceList.toArray(arrayOfNulls<String>(provinceList.size))
    }

    @SuppressLint("StaticFieldLeak")
    internal inner class DistrictListAsync : AsyncTask<Void?, Void?, List<DistrictList>?>() {

        override fun doInBackground(vararg p0: Void?): List<DistrictList>? {

            val dataBaseInstance =
                DatabaseClient.getInstance(this@TransferInActivity).appDatabase

//            return dataBaseInstance?.districtListDao()
//                ?.getDistrictOfProvision(provinceListArray!![binding?.etProvince?.tag as Int].provinceId)

            return dataBaseInstance?.districtListDao()
                ?.getDistrictOfRegionAndProvision(
                    regionId,
                    provinceListArray!![binding?.etProvince?.tag as Int].provinceId
                )
        }

        override fun onPostExecute(result: List<DistrictList>?) {
            super.onPostExecute(result)
            showDistrictOptions(result)

        }
    }

    var districtListArray: ArrayList<DistrictList>? = ArrayList()
    private fun showDistrictOptions(result: List<DistrictList>?) {
        districtListArray = ArrayList(result!!)
        val districtList: Array<String> = getDistrictList(districtListArray!!)
        showSingleSelectionDialog(
            districtList,
            binding?.etDistrict!!,
            "District"
        )

    }

    private fun getDistrictList(districts: ArrayList<DistrictList>): Array<String> {

        val districtList: ArrayList<String> = ArrayList()
        for (district in districts) {
            districtList.add(district.districtName)
        }
        return districtList.toArray(arrayOfNulls<String>(districtList.size))
    }

    @SuppressLint("StaticFieldLeak")
    internal inner class LLGListAsync : AsyncTask<Void?, Void?, List<LLGList>?>() {

        override fun doInBackground(vararg p0: Void?): List<LLGList>? {

            val dataBaseInstance =
                DatabaseClient.getInstance(this@TransferInActivity).appDatabase
            return dataBaseInstance?.llgListDao()
                ?.getLLGOfRegionAndProvisionAndDistrict(
                    regionId,
                    provinceListArray!![binding?.etProvince?.tag as Int].provinceId,
                    districtListArray!![binding?.etDistrict?.tag as Int].districtId
                )
        }

        override fun onPostExecute(result: List<LLGList>?) {
            super.onPostExecute(result)
            showLLGOptions(result)

        }
    }

    var llgListArray: ArrayList<LLGList>? = ArrayList()
    private fun showLLGOptions(result: List<LLGList>?) {
        llgListArray = ArrayList(result!!)
        val llgList: Array<String> = getLLGList(llgListArray!!)
        showSingleSelectionDialog(
            llgList,
            binding?.etLLG!!,
            "LLG"
        )

    }

    private fun getLLGList(llgs: ArrayList<LLGList>): Array<String> {

        val llgList: ArrayList<String> = ArrayList()
        for (llg in llgs) {
            llgList.add(llg.llgName)
        }
        return llgList.toArray(arrayOfNulls<String>(llgList.size))
    }

    @SuppressLint("StaticFieldLeak")
    internal inner class WardNoAndNameListAsync : AsyncTask<Void?, Void?, List<WardList>?>() {

        override fun doInBackground(vararg p0: Void?): List<WardList>? {

            val dataBaseInstance =
                DatabaseClient.getInstance(this@TransferInActivity).appDatabase
            return dataBaseInstance?.wardListDao()
                ?.getWardListOfRegionAndProvinceAndDistrictAndLLG(
                    regionId,
                    provinceListArray!![binding?.etProvince?.tag as Int].provinceId,
                    districtListArray!![binding?.etDistrict?.tag as Int].districtId,
                    llgListArray!![binding?.etLLG?.tag as Int].llgId
                )
        }

        override fun onPostExecute(result: List<WardList>?) {
            super.onPostExecute(result)
            showWardNoAndNameOptions(result)

        }
    }

    var wardNoAndNameArray: ArrayList<WardList>? = ArrayList()
    private fun showWardNoAndNameOptions(result: List<WardList>?) {
        wardNoAndNameArray = ArrayList(result!!)
        val wardNoAndNameList: Array<String> = getWardList(wardNoAndNameArray!!)
        showSingleSelectionDialog(
            wardNoAndNameList,
            binding?.etWardName!!,
            "WardNoAndName"
        )

    }

    private fun getWardList(wards: ArrayList<WardList>): Array<String> {

        val wardList: ArrayList<String> = ArrayList()
        for (ward in wards) {
            wardList.add(ward.wardNumber.toString() + " & " + ward.wardName)
        }
        return wardList.toArray(arrayOfNulls<String>(wardList.size))
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
                    "Region" -> {

                        binding?.etProvince?.tag = -1
                        binding?.etProvince?.text = ""

                        binding?.etDistrict?.tag = -1
                        binding?.etDistrict?.text = ""

                        binding?.etLLG?.tag = -1
                        binding?.etLLG?.text = ""

                        binding?.etWardName?.tag = -1
                        binding?.etWardName?.text = ""

                        provinceListArray?.clear()
                        districtListArray?.clear()
                        llgListArray?.clear()
                        wardNoAndNameArray?.clear()

                    }

                    "Province" -> {

                        binding?.etDistrict?.tag = -1
                        binding?.etDistrict?.text = ""

                        binding?.etLLG?.tag = -1
                        binding?.etLLG?.text = ""

                        binding?.etWardName?.tag = -1
                        binding?.etWardName?.text = ""

                        districtListArray?.clear()
                        llgListArray?.clear()
                        wardNoAndNameArray?.clear()

                    }

                    "District" -> {

                        binding?.etLLG?.tag = -1
                        binding?.etLLG?.text = ""

                        binding?.etWardName?.tag = -1
                        binding?.etWardName?.text = ""

                        llgListArray?.clear()
                        wardNoAndNameArray?.clear()

                    }

                    "LLG" -> {

                        binding?.etWardName?.tag = -1
                        binding?.etWardName?.text = ""

                        wardNoAndNameArray?.clear()
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


    private fun searchVoters() {

        if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
            regionName = binding?.etRegion?.text.toString()
            if (regionListArray!!.isNotEmpty() && binding?.etRegion?.tag != null)
                regionId = regionListArray!![binding?.etRegion?.tag as Int].regionId

            provinceName = binding?.etProvince?.text.toString()
            provinceId = if (provinceListArray!!.isNotEmpty() && provinceName.isNotEmpty()) {
                provinceListArray!![binding?.etProvince?.tag as Int].provinceId
            } else {
                0
            }

            districtName = binding?.etDistrict?.text.toString()
            districtId = if (districtListArray!!.isNotEmpty() && districtName.isNotEmpty()) {
                districtListArray!![binding?.etDistrict?.tag as Int].districtId
            } else {
                0
            }

            llgName = binding?.etLLG?.text.toString()
            llgId = if (llgListArray!!.isNotEmpty() && llgName.isNotEmpty()) {
                llgListArray!![binding?.etLLG?.tag as Int].llgId
            } else {
                0
            }

            wardNoAndName = binding?.etWardName?.text.toString()
            if (wardNoAndNameArray!!.isNotEmpty() && wardNoAndName.isNotEmpty()) {
                wardId = wardNoAndNameArray!![binding?.etWardName?.tag as Int].wardId
                wardNumber = wardNoAndNameArray!![binding?.etWardName?.tag as Int].wardNumber
                wardName = wardNoAndNameArray!![binding?.etWardName?.tag as Int].wardName
            }
        }else{
            llgName = binding?.etLLG?.text.toString()
            wardName = binding?.etWardName?.text.toString()
            /*if (wardNoAndNameArray!!.isNotEmpty() && wardNoAndName.isNotEmpty()) {
                wardId = wardNoAndNameArray!![binding?.etWardName?.tag as Int].wardId
                wardNumber = wardNoAndNameArray!![binding?.etWardName?.tag as Int].wardNumber
                wardName = wardNoAndNameArray!![binding?.etWardName?.tag as Int].wardName
            }*/
        }




        firstNameSearch = binding?.etFirstName?.text.toString()
        lastNameSearch = binding?.etLastName?.text.toString()

        gender = if (binding?.etGender?.text.toString() == "Male") {
            "M"
        } else if (binding?.etGender?.text.toString() == "Female") {
            "F"
        } else {
            ""
        }


        if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
            when {
                lastNameSearch.isEmpty() -> {
                    showAlertMessage(
                        "Please enter last name"
                    )
                }
                provinceName.isEmpty() -> {
                    showAlertMessage(
                        "Please select province"
                    )
                }
                districtName.isEmpty() -> {
                    showAlertMessage(
                        "Please select district"
                    )
                }
                else -> {

                    ApiConstants.TSV_REGION_ID = regionId
                    ApiConstants.TSV_REGION_NAME = binding?.etRegion?.text.toString().trim()

                    ApiConstants.TSV_PROVINCE_ID = provinceId
                    ApiConstants.TSV_PROVINCE_NAME = provinceName

                    ApiConstants.TSV_DISTRICT_ID = districtId
                    ApiConstants.TSV_DISTRICT_NAME = districtName

                    ApiConstants.TSV_LLG_ID = llgId
                    ApiConstants.TSV_LLG_NAME = llgName

                    ApiConstants.TSV_WARD_ID = wardId
                    ApiConstants.TSV_WARD_NAME = wardNoAndName
                    ApiConstants.TSV_WARD_NUMBER = wardNumber
                    ApiConstants.TSV_WARD_DISPLAY_NAME = wardName

                    ApiConstants.TSV_KEY_WORD_FIRST_NAME = firstNameSearch
                    ApiConstants.TSV_KEY_WORD_LAST_NAME = lastNameSearch
                    ApiConstants.TSV_GENDER = gender

                    if (CommonMethods.checkInternetConnection(application)) {
                        checkInterNetConnection()
                    } else {
                        navigateToOfflineSearch()
                    }
                }
            }
        }else{
            when {
                lastNameSearch.isEmpty() -> {
                    showAlertMessage(
                        "Please enter last name"
                    )
                }
                districtName.isEmpty() -> {
                    showAlertMessage(
                        "Please select district"
                    )
                }
                else -> {

                    ApiConstants.TSV_REGION_ID = regionId
                    ApiConstants.TSV_REGION_NAME = binding?.etRegion?.text.toString().trim()

                    ApiConstants.TSV_PROVINCE_ID = provinceId
                    ApiConstants.TSV_PROVINCE_NAME = provinceName

                    ApiConstants.TSV_DISTRICT_ID = districtId
                    ApiConstants.TSV_DISTRICT_NAME = districtName

                    ApiConstants.TSV_LLG_ID = llgId
                    ApiConstants.TSV_LLG_NAME = llgName

                    ApiConstants.TSV_WARD_ID = wardId
                    ApiConstants.TSV_WARD_NAME = wardNoAndName
                    ApiConstants.TSV_WARD_NUMBER = wardNumber
                    ApiConstants.TSV_WARD_DISPLAY_NAME = wardName

                    ApiConstants.TSV_KEY_WORD_FIRST_NAME = firstNameSearch
                    ApiConstants.TSV_KEY_WORD_LAST_NAME = lastNameSearch
                    ApiConstants.TSV_GENDER = gender

                    if (CommonMethods.checkInternetConnection(application)) {
                        checkInterNetConnection()
                    } else {
                        navigateToOfflineSearch()
                    }
                }
            }
        }
    }


    private fun checkInterNetConnection() {

        CommonMethods.showProgress(this)

        val loginObj = JsonObject()
        loginObj.addProperty(
            ApiConstants.EMAIL, securedSharedPreferenceWrapper.getString(
                ApiConstants.LOGGED_IN_DPO_EMAIL,
                ""
            )
        )
        loginObj.addProperty(
            ApiConstants.PASSWORD, securedSharedPreferenceWrapper.getString(
                ApiConstants.LOGGED_IN_DPO_PASSWORD,
                ""
            )
        )
        loginObj.addProperty(
            ApiConstants.DEVICENUMBER,
            Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        )

        val call =
            ApiClient.getRetrofitInterface()?.callLoginAPI(loginObj)

        WSClient<LoginResponseModel>().request(
            this,
            ApiConstants.DPO_LOGIN,
            call,
            object :
                ISuccessHandler<LoginResponseModel> {
                override fun successResponse(
                    requestCode: Int,
                    mResponse: LoginResponseModel
                ) {
                    CommonMethods.hideProgress()
                    navigateToOnlineSearch()
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    CommonMethods.hideProgress()
                    navigateToOfflineSearch()
                }
            })

    }

    private fun navigateToOnlineSearch() {
        CommonMethods.hideProgress()
        val intent = Intent(this, TransferInVotersListActivity::class.java)
        intent.putExtra("RegionId", regionId)
        intent.putExtra("RegionName", regionName)
        intent.putExtra("ProvinceId", provinceId)
        intent.putExtra("DistrictId", districtId)
        intent.putExtra("LLGId", llgId)
        intent.putExtra("WardId", wardId)
        intent.putExtra("FirstName", firstNameSearch)
        intent.putExtra("LastName", lastNameSearch)
        intent.putExtra("Gender", gender)
        intent.putExtra("WardNumber", wardNumber)
        intent.putExtra("WardName", wardName)
        startActivity(intent)

    }

    private fun navigateToOfflineSearch() {
        CommonMethods.hideProgress()
//        val intent = Intent(this, TransferInOfflineVotersListActivity::class.java)
        val intent = Intent(this, TransferInChunksOfflineVotersListActivity::class.java)
        intent.putExtra("RegionId", regionId)
        intent.putExtra("RegionName", regionName)
        intent.putExtra("ProvinceId", provinceId)
        intent.putExtra("ProvinceName", provinceName)
        intent.putExtra("DistrictId", districtId)
        intent.putExtra("DistrictName", districtName)
        intent.putExtra("LLGId", llgId)
        intent.putExtra("LLGName", llgName)
        intent.putExtra("WardId", wardId)
        intent.putExtra("WardName", wardName)
        intent.putExtra("FirstName", firstNameSearch)
        intent.putExtra("LastName", lastNameSearch)
        intent.putExtra("Gender", gender)
        intent.putExtra("WardNumber", wardNumber)
        startActivity(intent)

    }

    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
    }

}
