package com.pngelectoral.ui.activity

import ApiConstants
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityProvisionalRegWardSelectionBinding
import com.pngelectoral.ui.model.DPOUserData
import com.pngelectoral.ui.model.MKAGeoListData
import com.pngelectoral.ui.model.MKAGeoListModel
import com.pngelectoral.ui.model.WardList
import com.pngelectoral.ui.viewModel.RegistrationViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ProvisionalRegWardSelectionActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityProvisionalRegWardSelectionBinding? = null
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
            R.layout.activity_provisional_reg_ward_selection
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
        districtId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_DISTRICT_ID, 0)
        llgId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LLG_ID, 0)
        wardId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_WARD_ID, 0)

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

        /*binding?.etDistrict?.text =
            securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_DISTRICT, "")

        llgName = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LLG, "")
        if (llgName.isNotEmpty()) {
            binding?.etLLG?.text = llgName
            binding?.ivLLG?.visibility = View.GONE
        }

        wardName = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_WARD, "")
        if (wardName.isNotEmpty()) {
            getWardNumberAndNameUsingCoroutines()
        }*/

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
            proceedToProvisionalRegistration()
        }

        binding?.btnHome?.setOnClickListener {
            finish()
        }


    }


    private fun proceedToProvisionalRegistration() {

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

        val intent = Intent(this, ProvisionalEnrolmentScanActivity::class.java)
        intent.putExtra("ProvinceId", provinceId)
        intent.putExtra("WardId", wardId)
        intent.putExtra("WardNumber", wardNumber)
        intent.putExtra("WardName", wardName)
        startActivity(intent)


    }

}
