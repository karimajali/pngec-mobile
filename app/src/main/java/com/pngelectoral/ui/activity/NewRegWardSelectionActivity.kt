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
import com.pngelectoral.databinding.ActivityNewRegWardSelectionBinding
import com.pngelectoral.ui.model.DPOUserData
import com.pngelectoral.ui.model.LLGList
import com.pngelectoral.ui.model.MKAGeoListData
import com.pngelectoral.ui.model.MKAGeoListModel
import com.pngelectoral.ui.model.WardList
import com.pngelectoral.ui.viewModel.RegistrationViewModel
import com.pngelectoral.utility.CommonMethods
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import kotlin.coroutines.CoroutineContext

class NewRegWardSelectionActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityNewRegWardSelectionBinding? = null
    private var registrationViewModel: RegistrationViewModel? = null
    lateinit var dataBaseInstance: AppDatabase
    lateinit var loggedInDPOUserData: DPOUserData

    var regionId = 0
    var provinceId = 0
    var districtId = 0
    var llgId = 0
    var wardId = 0

    var llgName = ""
    var wardNumber = 0
    var wardName = ""

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_new_reg_ward_selection
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

        regionId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_REGION_ID, 0)
        provinceId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_PROVINCE_ID, 0)
        binding?.etRegion?.text =
            securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_REGION, "")
        binding?.etProvince?.text =
            securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_PROVINCE, "")

        when (securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "")) {
            ApiConstants.ROLE_DPO -> {
                districtId = loggedInDPOUserData.district.toInt()
                llgId =
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LLG_ID, 0)
                wardId =
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_WARD_ID, 0)
                binding?.etDistrict?.text = loggedInDPOUserData.districtName
                llgName =
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LLG, "")
                if (llgName.isNotEmpty()) {
                    binding?.etLLG?.text = llgName
                    binding?.ivLLG?.visibility = View.GONE
                }

                wardName =
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_WARD, "")
                if (wardName.isNotEmpty()) {
                    getWardNumberAndNameUsingCoroutines()
                }
                binding?.llRegion?.visibility = View.VISIBLE
                binding?.llProvince?.visibility = View.VISIBLE
            }

            ApiConstants.ROLE_MKA -> {
                districtId = loggedInDPOUserData.mkaDistrict.toInt()
                llgId = securedSharedPreferenceWrapper.getInt(
                    ApiConstants.DPO_WORKING_ROLL_MKA_LLG_ID,
                    0
                )
                wardId = securedSharedPreferenceWrapper.getInt(
                    ApiConstants.DPO_WORKING_ROLL_MKA_WARD_ID,
                    0
                )
                binding?.etDistrict?.text = loggedInDPOUserData.mkaDistrictName
                llgName = securedSharedPreferenceWrapper.getString(
                    ApiConstants.DPO_WORKING_ROLL_MKA_LLG,
                    ""
                )
                if (llgName.isNotEmpty()) {
                    binding?.etLLG?.text = llgName
                    binding?.ivLLG?.visibility = View.GONE
                }

                wardName = securedSharedPreferenceWrapper.getString(
                    ApiConstants.DPO_WORKING_ROLL_MKA_WARD,
                    ""
                )
                if (wardName.isNotEmpty()) {
                    getMKAWardNumberAndNameUsingCoroutines()
                }
                binding?.llRegion?.visibility = View.GONE
                binding?.llProvince?.visibility = View.GONE
            }

            else -> {
                districtId = loggedInDPOUserData.district.toInt()
                llgId =
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LLG_ID, 0)
                wardId =
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_WARD_ID, 0)
                binding?.etDistrict?.text = loggedInDPOUserData.districtName
                llgName =
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LLG, "")
                if (llgName.isNotEmpty()) {
                    binding?.etLLG?.text = llgName
                    binding?.ivLLG?.visibility = View.GONE
                }

                wardName =
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_WARD, "")
                if (wardName.isNotEmpty()) {
                    getWardNumberAndNameUsingCoroutines()
                }
                binding?.llRegion?.visibility = View.VISIBLE
                binding?.llProvince?.visibility = View.VISIBLE
                binding?.llRegion?.visibility = View.VISIBLE
                binding?.llProvince?.visibility = View.VISIBLE
            }
        }

        setFooterData()
        setOnclickListeners()
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
    private fun getWardNumberAndNameUsingCoroutines() {
        launch(Dispatchers.Main) {
            val result: WardList? = withContext(Dispatchers.IO) {
                dataBaseInstance.wardListDao()?.getDPOWard(wardId)
            }
            wardNumber = result!!.wardNumber
            binding?.etWardName?.text = "$wardNumber & $wardName"
            binding?.ivWardName?.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getMKAWardNumberAndNameUsingCoroutines() {
        launch(Dispatchers.Main) {
            val result: MKAGeoListData? = withContext(Dispatchers.IO) {
                dataBaseInstance.geoListDao()
                    ?.getDataForDistrict(districtId)
            }
            val mkaLlgArray = Gson().fromJson<ArrayList<MKAGeoListModel.GeoListData.LlG>>(
                result?.llGs ?: "",
                object : TypeToken<ArrayList<MKAGeoListModel.GeoListData.LlG>?>() {}.type
            ) ?: kotlin.run { arrayListOf() }
            val llgResult = mkaLlgArray.filter { llg ->
                llg.llgId == llgId
            }
            var wardResult: List<MKAGeoListModel.GeoListData.LlG.Ward>? = null
            if (llgResult.isNotEmpty()){
                wardResult = llgResult.single().wards?.filter { llg ->
                    llg.wardId == wardId
                }
            }
            wardNumber = wardResult?.single()?.wardNumber ?: 0
            binding?.etWardName?.text = "$wardNumber & $wardName"
            binding?.ivWardName?.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
    }


    private fun setOnclickListeners() {

        binding?.btnSearch?.setOnClickListener {
            proceedToNewRegistration()
        }

        binding?.btnHome?.setOnClickListener {
            finish()
        }

        binding?.rlLLG?.setOnClickListener {
            if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_LLG, "").isEmpty()) {
                CommonMethods.showProgress(this)
                getLLGListUsingCoRoutines()
            }

        }

        binding?.rlWardName?.setOnClickListener {
            if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_WARD, "").isEmpty()) {
                if (llgListArray?.size!! > 0 && binding?.etLLG?.text!!.isNotEmpty()) {
                    CommonMethods.showProgress(this)
                    getWardNoAndNameListUsingCoRoutines(false)
                } else if (binding?.etLLG?.text!!.isNotEmpty()) {
                    CommonMethods.showProgress(this)
                    getWardNoAndNameListUsingCoRoutines(true)
                } else
                    showAlertMessage(
                        "Please select llg"
                    )
            }

        }

    }

    private fun getLLGListUsingCoRoutines() {
        launch(Dispatchers.Main) {
            val result: List<LLGList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.llgListDao()
                    ?.getLLGOfRegionAndProvisionAndDistrict(
                        regionId,
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
                        ?.getWardListOfRegionAndProvinceAndDistrictAndLLG(
                            regionId,
                            provinceId,
                            districtId,
                            llgId
                        )
                } else {
                    dataBaseInstance.wardListDao()
                        ?.getWardListOfRegionAndProvinceAndDistrictAndLLG(
                            regionId,
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


    private fun proceedToNewRegistration() {

        securedSharedPreferenceWrapper.putInt(
            "TEMP_LLGId",
            llgId
        )

        securedSharedPreferenceWrapper.putString(
            "TEMP_LLGName",
            llgName
        )

        securedSharedPreferenceWrapper.putInt(
            "TEMP_WardId",
            wardId
        )

        securedSharedPreferenceWrapper.putInt(
            "TEMP_WardNumber",
            wardNumber
        )

        securedSharedPreferenceWrapper.putString(
            "TEMP_WardName",
            wardName
        )

        val intent = Intent(this, NewEnrolmentScanActivity::class.java)
        intent.putExtra("ProvinceId", provinceId)
        intent.putExtra("WardId", wardId)
        intent.putExtra("WardNumber", wardNumber)
        intent.putExtra("WardName", wardName)
        startActivity(intent)

//        llgName = binding?.etLLG?.text.toString()
//        if (llgListArray!!.isNotEmpty() && llgName.isNotEmpty()) {
//            llgId = llgListArray!![binding?.etLLG?.tag as Int].llgId
//        }
//
//        wardNoAndName = binding?.etWardName?.text.toString()
//        if (wardNoAndNameArray!!.isNotEmpty() && wardNoAndName.isNotEmpty()) {
//            wardId = wardNoAndNameArray!![binding?.etWardName?.tag as Int].wardId
//            wardNumber = wardNoAndNameArray!![binding?.etWardName?.tag as Int].wardNumber
//            wardName = wardNoAndNameArray!![binding?.etWardName?.tag as Int].wardName
//        }
//
//
//
//        when {
//            llgName.isEmpty() -> {
//                showAlertMessage(
//                    "Please select llg"
//                )
//            }
//            wardNoAndName.isEmpty() -> {
//                showAlertMessage(
//                    "Please select ward"
//                )
//            }
//
//            else -> {
//
//                securedSharedPreferenceWrapper.putInt(
//                    "TEMP_LLGId",
//                    llgId
//                )
//
//                securedSharedPreferenceWrapper.putString(
//                    "TEMP_LLGName",
//                    llgName
//                )
//
//                securedSharedPreferenceWrapper.putInt(
//                    "TEMP_WardId",
//                    wardId
//                )
//
//                securedSharedPreferenceWrapper.putInt(
//                    "TEMP_WardNumber",
//                    wardNumber
//                )
//
//                securedSharedPreferenceWrapper.putString(
//                    "TEMP_WardName",
//                    wardName
//                )
//
//                val intent = Intent(this, NewEnrolmentActivity::class.java)
//                intent.putExtra("WardId", wardId)
//                intent.putExtra("WardNumber", wardNumber)
//                intent.putExtra("WardName", wardName)
//                startActivity(intent)
//            }
//        }
    }

}
