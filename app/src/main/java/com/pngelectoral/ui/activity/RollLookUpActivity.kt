package com.pngelectoral.ui.activity

import ApiConstants
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.InputType
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityRollLookUpBinding
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.retrofit.ApiClient
import com.pngelectoral.ui.retrofit.IFailureHandler
import com.pngelectoral.ui.retrofit.ISuccessHandler
import com.pngelectoral.ui.retrofit.WSClient
import com.pngelectoral.ui.viewModel.TransferInViewModel
import com.pngelectoral.utility.CommonMethods
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import kotlin.coroutines.CoroutineContext

class RollLookUpActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityRollLookUpBinding? = null
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

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_roll_look_up
        )

        transferInViewModel =
            ViewModelProvider(this).get(TransferInViewModel::class.java)
        binding?.transferInViewModel = transferInViewModel

        job = Job()

        val loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
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

        setFooterData()
        setOnclickListeners()
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
            getRegionListFromDB()
        }

        binding?.rlProvince?.setOnClickListener {
            CommonMethods.showProgress(this)
            if (regionListArray!!.isNotEmpty() && binding?.etRegion?.tag != null)
                regionId = regionListArray!![binding?.etRegion?.tag as Int].regionId
            getProvinceListFromDB()
        }

        binding?.rlDistrict?.setOnClickListener {
            if (provinceListArray?.size!! > 0 && binding?.etProvince?.text!!.isNotEmpty()) {
                CommonMethods.showProgress(this)
                getDistrictListFromDB()
            } else
                showAlertMessage(
                    "Please select province"
                )
        }

        binding?.rlLLG?.setOnClickListener {
            if (districtListArray?.size!! > 0 && binding?.etDistrict?.text!!.isNotEmpty()) {
                CommonMethods.showProgress(this)
                getLLGListFromDB()
            } else
                showAlertMessage(
                    "Please select district"
                )
        }

        binding?.rlWardName?.setOnClickListener {
            if (llgListArray?.size!! > 0 && binding?.etLLG?.text!!.isNotEmpty()) {
                CommonMethods.showProgress(this)
                getWardListFromDB()
            } else
                showAlertMessage(
                    "Please select llg"
                )
        }

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

    private fun getRegionListFromDB() {
        launch(Dispatchers.Main) {
            val result: List<RegionList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.regionListDao()?.all
            }
            showRegionOptions(result)
        }
    }

    var regionListArray: List<RegionList>? = ArrayList()
    private fun showRegionOptions(result: List<RegionList>?) {
        regionListArray = result
        showSingleSelectionDialog(
            CommonMethods.getRegionList(ArrayList(result!!)),
            binding?.etRegion!!,
            "Region"
        )

    }

    private fun getProvinceListFromDB() {
        launch(Dispatchers.Main) {
            val result: List<ProvinceList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.provinceListDao()
                    ?.getProvinceOfRegion(regionId)
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

    private fun getDistrictListFromDB() {
        launch(Dispatchers.Main) {
            val result: List<DistrictList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.districtListDao()
                    ?.getDistrictOfRegionAndProvision(
                        regionId,
                        provinceListArray!![binding?.etProvince?.tag as Int].provinceId
                    )
            }
            showDistrictOptions(result)
        }
    }

    var districtListArray: ArrayList<DistrictList>? = ArrayList()
    private fun showDistrictOptions(result: List<DistrictList>?) {
        districtListArray = ArrayList(result!!)
        showSingleSelectionDialog(
            CommonMethods.getDistrictList(districtListArray!!),
            binding?.etDistrict!!,
            "District"
        )
    }


    private fun getLLGListFromDB() {
        launch(Dispatchers.Main) {
            val result: List<LLGList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.llgListDao()
                    ?.getLLGOfRegionAndProvisionAndDistrict(
                        regionId,
                        provinceListArray!![binding?.etProvince?.tag as Int].provinceId,
                        districtListArray!![binding?.etDistrict?.tag as Int].districtId
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

    private fun getWardListFromDB() {
        launch(Dispatchers.Main) {
            val result: List<WardList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.wardListDao()
                    ?.getWardListOfRegionAndProvinceAndDistrictAndLLG(
                        regionId,
                        provinceListArray!![binding?.etProvince?.tag as Int].provinceId,
                        districtListArray!![binding?.etDistrict?.tag as Int].districtId,
                        llgListArray!![binding?.etLLG?.tag as Int].llgId
                    )
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


        firstNameSearch = binding?.etFirstName?.text.toString()
        lastNameSearch = binding?.etLastName?.text.toString()

        gender = if (binding?.etGender?.text.toString() == "Male") {
            "M"
        } else if (binding?.etGender?.text.toString() == "Female") {
            "F"
        } else {
            ""
        }


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
                if (CommonMethods.checkInternetConnection(application)) {
                    checkInterNetConnection()
                } else {
                    navigateToOfflineSearch()
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
        val intent = Intent(this, RollLookUpVotersListActivity::class.java)
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
        val intent = Intent(this, RollLookUpChunksOfflineVotersListActivity::class.java)
        intent.putExtra("RegionId", regionId)
        intent.putExtra("RegionName", regionName)
        intent.putExtra("ProvinceId", provinceId)
        intent.putExtra("ProvinceName", provinceName)
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


}
