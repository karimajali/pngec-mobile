package com.pngelectoral.ui.activity

import ApiConstants
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.layout.property.UnitValue
import com.itextpdf.layout.property.VerticalAlignment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.pngelectoral.BuildConfig
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.*
import com.pngelectoral.ui.adapter.GenericAdapter
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.retrofit.*
import com.pngelectoral.ui.viewModel.SyncVotersListViewModel
import com.pngelectoral.utility.CommonMethods
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class VotersSyncWithDataAndImageActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivitySyncVotersListBinding? = null
    private var syncVotersListViewModel: SyncVotersListViewModel? = null
    private var syncVotersListAdapter: GenericAdapter<VotersList, ItemSyncVoterNewBinding>? = null
    lateinit var loggedInDPOUserData: DPOUserData
    lateinit var dataBaseInstance: AppDatabase
    private var selectedVotersList: ArrayList<VotersList> = ArrayList()
    private var votersListToBeSynced: ArrayList<VotersList> = ArrayList()
    private var syncedVotersList: ArrayList<VotersList> = ArrayList()
    private var rejectedVotersList: ArrayList<VotersList> = ArrayList()

    private var currentUpdatedVotersList = -1
    private var isSyncDone = false
    private var successOrErrorMessage = ""

    var districtId = 0
    var llgId = 0
    var wardId = 0
    var locationId = 0

    var provinceName = ""
    var districtName = ""
    var llgName = ""
    var wardName = ""
    private var wardNoAndName = ""
    private var syncWardNoAndName = ""
    var wardNumber = 0
    var locationName = ""

    private var statusList = mutableListOf<VoterListFilterModel>()
    private val statusFilterList = ArrayList<String>()

    private var filterType = ""
    private var syncDataOptions = ""

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job
    private lateinit var storagePermissionResultLauncher: ActivityResultLauncher<Intent?>
    private var syncSummaryReportFilesPath = "PNG_Sync_Summary_Report"

    private var mkaLocationArray =
        java.util.ArrayList<MKAGeoListModel.GeoListData.LlG.Ward.Location>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_sync_voters_list
        )
        syncVotersListViewModel =
            ViewModelProvider(this).get(SyncVotersListViewModel::class.java)
        binding?.syncVotersListViewModel = syncVotersListViewModel

        job = Job()

        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )
        intent?.apply {
            filterType =
                getStringExtra(ApiConstants.FILTER_TYPE) ?: ApiConstants.PNGEC_GEO_STRUCTURE
        }

        intent?.apply {
            syncDataOptions =
                getStringExtra(ApiConstants.SYNC_DATA_OPTION) ?: ""
        }

        dataBaseInstance = DatabaseClient.getInstance(this).appDatabase
        statusList = CommonMethods.getStatusFilterListForSync()

        provinceName = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_PROVINCE, "")
        when (securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "")) {
            ApiConstants.ROLE_DPO -> {
                districtId = loggedInDPOUserData.district.toInt()
                llgId =
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LLG_ID, 0)
                wardId =
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_WARD_ID, 0)
                locationId =
                    securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_LOCATION_ID,
                        0
                    )
                districtName = loggedInDPOUserData.districtName
                llgName =
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LLG, "")
                wardName =
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_WARD, "")
                if (wardName.isNotEmpty()) {
                    getWardNumberAndNameUsingCoroutines()
                }
                locationName =
                    securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_LOCATION,
                        ""
                    )
            }

            ApiConstants.ROLE_MKA -> {
                if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                    districtId = loggedInDPOUserData.district.toInt()
                    llgId = securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_LLG_ID,
                        0
                    )
                    wardId = securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_WARD_ID,
                        0
                    )
                    locationId =
                        securedSharedPreferenceWrapper.getInt(
                            ApiConstants.DPO_WORKING_ROLL_LOCATION_ID,
                            0
                        )
                    districtName = loggedInDPOUserData.districtName
                    llgName = securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_LLG,
                        ""
                    )
                    wardName = securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_WARD,
                        ""
                    )
                    if (wardName.isNotEmpty()) {
                        getWardNumberAndNameUsingCoroutines()
                    }
                    locationName =
                        securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_LOCATION,
                            ""
                        )
                } else {
                    districtId = loggedInDPOUserData.mkaDistrict.toInt()
                    llgId = securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_MKA_LLG_ID,
                        0
                    )
                    wardId = securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_MKA_WARD_ID,
                        0
                    )
                    locationId =
                        securedSharedPreferenceWrapper.getInt(
                            ApiConstants.DPO_WORKING_ROLL_MKA_LOCATION_ID,
                            0
                        )
                    districtName = loggedInDPOUserData.mkaDistrictName
                    llgName = securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_MKA_LLG,
                        ""
                    )
                    wardName = securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_MKA_WARD,
                        ""
                    )
                    if (wardName.isNotEmpty()) {
                        getMKAWardNumberAndNameUsingCoroutines()
                    }
                    locationName =
                        securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_MKA_LOCATION,
                            ""
                        )
                }
            }

            else -> {
                districtId = loggedInDPOUserData.district.toInt()
                llgId =
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LLG_ID, 0)
                wardId =
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_WARD_ID, 0)
                locationId =
                    securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_LOCATION_ID,
                        0
                    )
                districtName = loggedInDPOUserData.districtName
                llgName =
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LLG, "")
                wardName =
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_WARD, "")
                if (wardName.isNotEmpty()) {
                    getWardNumberAndNameUsingCoroutines()
                }
                locationName =
                    securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_LOCATION,
                        ""
                    )
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding?.rvSyncVotersList?.layoutManager = layoutManager

        setOnClickListeners()
        CommonMethods.showProgress(this)
        getVotersSyncList()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            storagePermissionResultLauncher = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (Environment.isExternalStorageManager()) {
                    exportSyncSummarySheetReport()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getWardNumberAndNameUsingCoroutines() {
        launch(Dispatchers.Main) {
            val result: WardList? = withContext(Dispatchers.IO) {
                dataBaseInstance.wardListDao()?.getDPOWard(wardId)
            }
            wardNumber = result!!.wardNumber
            wardNoAndName = "$wardNumber & $wardName"
            if (securedSharedPreferenceWrapper.getString(
                    ApiConstants.ROLE,
                    ""
                ) == ApiConstants.ROLE_MKA && filterType == ApiConstants.PNGEC_GEO_STRUCTURE
            ) {
                val mkaResult: MKAGeoListData? = withContext(Dispatchers.IO) {
                    dataBaseInstance.geoListDao()
                        ?.getDataForDistrict(districtId)
                }
                val mkaLlgArray = Gson().fromJson<ArrayList<MKAGeoListModel.GeoListData.LlG>>(
                    mkaResult?.llGs ?: "",
                    object : TypeToken<ArrayList<MKAGeoListModel.GeoListData.LlG>?>() {}.type
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
                val wardNo =
                    if (wardResult?.isNotEmpty() == true) wardResult.single().wardNumber ?: 0 else 0
                syncWardNoAndName = "$wardNo & ${loggedInDPOUserData.mkaWardName}"
            }
        }
    }

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
            if (llgResult.isNotEmpty()) {
                wardResult = llgResult.single().wards?.filter { llg ->
                    llg.wardId == wardId
                }
            }
            wardNumber =
                if (wardResult?.isNotEmpty() == true) wardResult.single().wardNumber ?: 0 else 0
            wardNoAndName = "$wardNumber & $wardName"
            syncWardNoAndName = "$wardNumber & $wardName"
        }
    }

    private fun getVotersSyncList() {
        launch(Dispatchers.Main) {
            val result: List<VotersList> = withContext(Dispatchers.IO) {
                if (locationId == 0)
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.ROLE,
                            ""
                        ) == ApiConstants.ROLE_DPO
                    ) {
                        if(syncDataOptions.isBlank()) {
                            dataBaseInstance.votersListDao()
                                .getDPOWARDWiseUpdatedVotersListToSyncWithImage(
                                    districtId.toString(), llgId.toString(), wardId.toString()
                                )
                        }
                        else
                        {
                            dataBaseInstance.votersListDao()
                                .getDPOWARDWiseUpdatedVotersListToSync(
                                    districtId.toString(), llgId.toString(), wardId.toString()
                                )
                        }
                    } else {
                        if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                            dataBaseInstance.votersListDao()
                                .getDPOWARDWiseUpdatedVotersListToSyncWithImage(
                                    districtId.toString(), llgId.toString(), wardId.toString()
                                )
                        } else {
                            dataBaseInstance.votersListDao()
                                .getDPOWARDWiseUpdatedVotersListToSyncWithImageForMKA(
                                    districtId.toString(), llgId.toString(), wardId.toString()
                                )
                        }
                    }
                else
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.ROLE,
                            ""
                        ) == ApiConstants.ROLE_DPO
                    ) {
                        if(syncDataOptions.isBlank()) {
                            dataBaseInstance.votersListDao()
                                .getDPOLOCATIONWiseUpdatedVotersListToSyncWithImage(
                                    districtId.toString(),
                                    llgId.toString(),
                                    wardId.toString(),
                                    locationId.toString()
                                )
                        }
                        else{
                            dataBaseInstance.votersListDao()
                                .getDPOLOCATIONWiseUpdatedVotersListToSyncWithImageSync(
                                    districtId.toString(),
                                    llgId.toString(),
                                    wardId.toString(),
                                    locationId.toString()
                                )
                        }
                    } else {
                        if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                            dataBaseInstance.votersListDao()
                                .getDPOLOCATIONWiseUpdatedVotersListToSyncWithImage(
                                    districtId.toString(),
                                    llgId.toString(),
                                    wardId.toString(),
                                    locationId.toString()
                                )
                        } else {
                            dataBaseInstance.votersListDao()
                                .getDPOLOCATIONWiseUpdatedVotersListToSyncWithImageForMKA(
                                    districtId.toString(),
                                    llgId.toString(),
                                    wardId.toString(),
                                    locationId.toString()
                                )
                        }
                    }
            }
            checkAndLoadData(result)
        }
    }

    private fun checkAndLoadData(result: List<VotersList>) {
        CommonMethods.hideProgress()
        votersListToBeSynced = getVotersToBeSync(result)
        if (votersListToBeSynced.isNotEmpty()) {
            if (votersListToBeSynced.size > 25) {
                binding?.tvOr?.visibility = View.VISIBLE
                binding?.cbSelectTwentyFive?.visibility = View.VISIBLE
            } else {
                binding?.tvOr?.visibility = View.GONE
                binding?.cbSelectTwentyFive?.visibility = View.GONE
            }
            binding?.rlMainContent?.visibility = View.VISIBLE
            binding?.rlNoContent?.visibility = View.GONE
            binding?.tvTitle?.text = votersListToBeSynced.size.toString() + " Voters To Sync"
            loadData(votersListToBeSynced)
        } else {
            binding?.tvTitle?.text = "No Voters To Sync"
            if (syncVotersListAdapter != null)
                syncVotersListAdapter!!.clearItems()
            binding?.rlMainContent?.visibility = View.GONE
            binding?.rlNoContent?.visibility = View.VISIBLE
        }
    }

    private fun getVotersToBeSync(result: List<VotersList>): ArrayList<VotersList> {

        val resList = result.partition {
            it.status == getString(R.string.updated)
        }.toList().flatten()

        val tempSyncVotersList: ArrayList<VotersList> = ArrayList()
        for (voterToBeSynced in resList) {
            if (voterToBeSynced.errorMessage == null) {
                tempSyncVotersList.add(voterToBeSynced)
            } else if (voterToBeSynced.errorMessage.isEmpty())
                tempSyncVotersList.add(voterToBeSynced)
        }
        return tempSyncVotersList
    }

    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
    }

    private fun setOnClickListeners() {

        binding?.btnHome?.setOnClickListener {
            if (isSyncDone) {
                val resultIntent = Intent()
                resultIntent.putExtra("NEED_REFRESH", true)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                finish()
            }
        }

        binding?.btnFilter?.setOnClickListener {
            showSelectGeoLocationToSync()
        }

        binding?.cbSelectAll?.setOnCheckedChangeListener { compoundButton, selected ->
            if (compoundButton.isPressed) {
                selectedVotersList.clear()
                binding?.cbSelectTwentyFive?.isChecked = false
                if (selected) {
                    selectedVotersList.addAll(votersListToBeSynced)
                } else {
                    selectedVotersList.clear()
                }
                syncVotersListAdapter?.notifyDataSetChanged()
            }
        }

        binding?.cbSelectTwentyFive?.setOnCheckedChangeListener { compoundButton, selected ->
            if (compoundButton.isPressed) {
                selectedVotersList.clear()
                binding?.cbSelectAll?.isChecked = false
                if (selected) {
                    if (votersListToBeSynced.size > 25) {
                        selectedVotersList.addAll(votersListToBeSynced.subList(0, 25))
                    } else {
                        selectedVotersList.addAll(votersListToBeSynced)
                    }

                } else {
                    selectedVotersList.clear()
                }
                syncVotersListAdapter?.notifyDataSetChanged()
            }
        }

        binding?.cardSync?.setOnClickListener {
            if (selectedVotersList.size > 0) {
                binding?.cardSync?.isEnabled = false
                startSync()
            } else {
                alert("Select voters to sync") {
                    okButton { }
                }.show()
            }
        }

    }

    private var bindingSelectGeoLocation: DialogGeoAndFilterSelectionBinding? = null
    private var mSelectGeoLocationAlertDialog: AlertDialog? = null
    private var statusAdapter: GenericAdapter<VoterListFilterModel, ItemFilterBinding>? = null
    internal var statusId = ArrayList<String>()

    private fun showSelectGeoLocationToSync() {

        val builder = AlertDialog.Builder(this, R.style.full_screen_dialog)
        bindingSelectGeoLocation = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_geo_and_filter_selection, null, false
        ) as DialogGeoAndFilterSelectionBinding
        builder.setView(bindingSelectGeoLocation?.root)

        mSelectGeoLocationAlertDialog = builder.create()
        mSelectGeoLocationAlertDialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        bindingSelectGeoLocation?.etDistrict?.text = districtName

        if (llgName.isNotEmpty()) {
            bindingSelectGeoLocation?.etLLG?.text = llgName
        }

        if (wardName.isNotEmpty()) {
            bindingSelectGeoLocation?.etWard?.text = wardNoAndName
        }

        if (locationName.isNotEmpty()) {
            bindingSelectGeoLocation?.etLocation?.text = locationName
        }


        if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_LLG, "").isNotEmpty()) {
            bindingSelectGeoLocation?.ivLLG?.visibility = View.GONE
        }

        if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_WARD, "").isNotEmpty()) {
            bindingSelectGeoLocation?.ivWard?.visibility = View.GONE
        }

        val statusListLayoutManager = LinearLayoutManager(this)
        statusAdapter = prepareStatusAdapter()
        bindingSelectGeoLocation?.rvStatusList!!.layoutManager = statusListLayoutManager
        bindingSelectGeoLocation?.rvStatusList!!.adapter = statusAdapter

        bindingSelectGeoLocation?.rlLLG?.setOnClickListener {
            if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_LLG, "").isEmpty()) {
                CommonMethods.showProgress(this)
                getLLGListUsingCoRoutines()
            }

        }

        bindingSelectGeoLocation?.rlWard?.setOnClickListener {
            if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_WARD, "").isEmpty()) {
                if (bindingSelectGeoLocation?.etLLG?.text.toString().isEmpty()) {
                    showAlertMessage(
                        "Please select llg"
                    )
                } else {
                    CommonMethods.showProgress(this)
                    getWardNoAndNameListUsingCoRoutines()
                }
            }

        }

        bindingSelectGeoLocation?.rlLocation?.setOnClickListener {
            if (bindingSelectGeoLocation?.etWard?.text.toString().isEmpty()) {
                showAlertMessage(
                    "Please select ward"
                )
            } else {
                CommonMethods.showProgress(this)
                if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                    getLocationListFromDB()
                } else {
                    getMKALocationListFromDB()
                }
            }

        }

        bindingSelectGeoLocation?.cardDone?.setOnClickListener {
            mSelectGeoLocationAlertDialog!!.dismiss()
            filterSyncedList()

        }


        bindingSelectGeoLocation?.ivClose?.setOnClickListener {
            mSelectGeoLocationAlertDialog!!.dismiss()
        }

        mSelectGeoLocationAlertDialog?.show()
    }


    private fun prepareStatusAdapter(): GenericAdapter<VoterListFilterModel, ItemFilterBinding> {
        return object : GenericAdapter<VoterListFilterModel, ItemFilterBinding>(statusList) {
            override fun getLayoutId(): Int {
                return R.layout.item_filter
            }

            override fun onBindData(
                model: VoterListFilterModel,
                position: Int,
                dataBinding: ItemFilterBinding
            ) {

                dataBinding.tvFilterItem.text = model.name

                if (statusList[position].selected!!) {
                    dataBinding.ivSelectFilter.setImageResource(R.drawable.check_box_tick1)
                } else {
                    dataBinding.ivSelectFilter.setImageResource(R.drawable.check_box)
                }

                dataBinding.root.setOnClickListener {

                    if (statusId.contains(statusList[position].id)) {
                        statusId.remove(statusList[position].id)
                        dataBinding.ivSelectFilter.setImageResource(R.drawable.check_box)
                    } else {
                        statusId.add(statusList[position].id!!)
                        dataBinding.ivSelectFilter.setImageResource(R.drawable.check_box_tick1)
                    }
                }
            }

        }
    }

    private fun getLLGListUsingCoRoutines() {
        launch(Dispatchers.Main) {
            val result: List<LLGList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.llgListDao()
                    ?.getLLGOfDistrict(
                        districtId
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
            bindingSelectGeoLocation?.etLLG!!,
            "LLG"
        )

    }

    private fun getWardNoAndNameListUsingCoRoutines() {
        launch(Dispatchers.Main) {
            val result: List<WardList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.wardListDao()
                    ?.getWardListOfDistrictAndLLG(
                        districtId,
                        llgId
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
            bindingSelectGeoLocation?.etWard!!,
            "WardNoAndName"
        )

    }

    private fun getLocationListFromDB() {
        launch(Dispatchers.Main) {
            val result: List<LocationList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.locationListDao()
                    ?.getLocationListOfDistrictAndLLGAndWard(
                        districtId,
                        llgId,
                        wardId
                    )
            }
            showLocationOptions(result)
        }
    }


    private var locationArray: ArrayList<LocationList>? = ArrayList()
    private fun showLocationOptions(result: List<LocationList>?) {
        locationArray = ArrayList(result!!)
        showSingleSelectionDialog(
            CommonMethods.getLocationList(locationArray!!),
            bindingSelectGeoLocation?.etLocation!!,
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
                    "LLG" -> {
                        llgId = llgListArray!![clickItem].llgId
                        llgName = singleChoiceItems[clickItem]
                        bindingSelectGeoLocation?.etWard?.tag = null
                        bindingSelectGeoLocation?.etWard?.text = ""
                        wardNoAndNameArray?.clear()

                        bindingSelectGeoLocation?.etLocation?.tag = null
                        bindingSelectGeoLocation?.etLocation?.text = ""
                        locationArray?.clear()

                        wardId = 0
                        wardName = ""
                        wardNoAndName = ""
                        locationId = 0
                        locationName = ""

                    }

                    "WardNoAndName" -> {
                        wardId = wardNoAndNameArray!![clickItem].wardId
                        wardName = singleChoiceItems[clickItem]
                        wardNoAndName = singleChoiceItems[clickItem]
                        bindingSelectGeoLocation?.etLocation?.tag = null
                        bindingSelectGeoLocation?.etLocation?.text = ""
                        locationArray?.clear()

                        locationId = 0
                        locationName = ""
                    }

                    "Location" -> {
                        if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE)
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
            bindingSelectGeoLocation?.etLocation!!,
            "Location",
        )

    }

    private fun filterSyncedList() {

        CommonMethods.showProgress(this)

        statusFilterList.clear()
        for (i in statusList.indices) {
            statusList[i].selected = statusId.contains(statusList[i].id)
        }

        for (i in statusList.indices) {
            if (statusList[i].selected!!) {
                statusFilterList.add(statusList[i].name!!)
            }
        }

        getVotersToSyncBasedOnGeoSelection()

    }

    private fun getVotersToSyncBasedOnGeoSelection() {

        launch(Dispatchers.Main) {
            val result: List<VotersList> = withContext(Dispatchers.IO) {
                when {
                    locationId != 0 ->
                        if (securedSharedPreferenceWrapper.getString(
                                ApiConstants.ROLE,
                                ""
                            ) == ApiConstants.ROLE_DPO
                        ) {
                            if (statusFilterList.isEmpty()) {
                                dataBaseInstance.votersListDao()
                                    .getDPOLOCATIONWiseUpdatedVotersListToSyncWithImage(
                                        districtId.toString(),
                                        llgId.toString(),
                                        wardId.toString(),
                                        locationId.toString()
                                    )
                            } else {
                                dataBaseInstance.votersListDao()
                                    .getDPOLOCATIONWiseUpdatedVotersListToSyncWithStatusFilterAndImage(
                                        districtId.toString(),
                                        llgId.toString(),
                                        wardId.toString(),
                                        locationId.toString(),
                                        statusFilterList
                                    )
                            }
                        } else {
                            if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                                if (statusFilterList.isEmpty()) {
                                    dataBaseInstance.votersListDao()
                                        .getDPOLOCATIONWiseUpdatedVotersListToSyncWithImage(
                                            districtId.toString(),
                                            llgId.toString(),
                                            wardId.toString(),
                                            locationId.toString()
                                        )
                                } else {
                                    dataBaseInstance.votersListDao()
                                        .getDPOLOCATIONWiseUpdatedVotersListToSyncWithStatusFilterAndImage(
                                            districtId.toString(),
                                            llgId.toString(),
                                            wardId.toString(),
                                            locationId.toString(),
                                            statusFilterList
                                        )
                                }
                            } else {
                                if (statusFilterList.isEmpty()) {
                                    dataBaseInstance.votersListDao()
                                        .getDPOLOCATIONWiseUpdatedVotersListToSyncWithImageForMKA(
                                            districtId.toString(),
                                            llgId.toString(),
                                            wardId.toString(),
                                            locationId.toString()
                                        )
                                } else {
                                    dataBaseInstance.votersListDao()
                                        .getDPOLOCATIONWiseUpdatedVotersListToSyncWithStatusFilterAndImageForMKA(
                                            districtId.toString(),
                                            llgId.toString(),
                                            wardId.toString(),
                                            locationId.toString(),
                                            statusFilterList
                                        )
                                }
                            }
                        }


                    wardId != 0 ->
                        if (securedSharedPreferenceWrapper.getString(
                                ApiConstants.ROLE,
                                ""
                            ) == ApiConstants.ROLE_DPO
                        ) {
                            if (statusFilterList.isEmpty()) {
                                dataBaseInstance.votersListDao()
                                    .getDPOWARDWiseUpdatedVotersListToSyncWithImage(
                                        districtId.toString(),
                                        llgId.toString(),
                                        wardId.toString(),
                                    )
                            } else {
                                dataBaseInstance.votersListDao()
                                    .getDPOWARDWiseUpdatedVotersListToSyncWithStatusFilterAndImage(
                                        districtId.toString(),
                                        llgId.toString(),
                                        wardId.toString(),
                                        statusFilterList
                                    )
                            }
                        } else {
                            if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                                if (statusFilterList.isEmpty()) {
                                    dataBaseInstance.votersListDao()
                                        .getDPOWARDWiseUpdatedVotersListToSyncWithImage(
                                            districtId.toString(),
                                            llgId.toString(),
                                            wardId.toString(),
                                        )
                                } else {
                                    dataBaseInstance.votersListDao()
                                        .getDPOWARDWiseUpdatedVotersListToSyncWithStatusFilterAndImage(
                                            districtId.toString(),
                                            llgId.toString(),
                                            wardId.toString(),
                                            statusFilterList
                                        )
                                }
                            } else {
                                if (statusFilterList.isEmpty()) {
                                    dataBaseInstance.votersListDao()
                                        .getDPOWARDWiseUpdatedVotersListToSyncWithImageForMKA(
                                            districtId.toString(),
                                            llgId.toString(),
                                            wardId.toString(),
                                        )
                                } else {
                                    dataBaseInstance.votersListDao()
                                        .getDPOWARDWiseUpdatedVotersListToSyncWithStatusFilterAndImageForMKA(
                                            districtId.toString(),
                                            llgId.toString(),
                                            wardId.toString(),
                                            statusFilterList
                                        )
                                }
                            }
                        }


                    llgId != 0 ->
                        if (securedSharedPreferenceWrapper.getString(
                                ApiConstants.ROLE,
                                ""
                            ) == ApiConstants.ROLE_DPO
                        ) {
                            if (statusFilterList.isEmpty()) {
                                dataBaseInstance.votersListDao()
                                    .getDPOLLGWiseUpdatedVotersListToSyncWithImage(
                                        districtId.toString(),
                                        llgId.toString()
                                    )
                            } else {
                                dataBaseInstance.votersListDao()
                                    .getDPOLLGWiseUpdatedVotersListToSyncWithStatusFilterAndImage(
                                        districtId.toString(),
                                        llgId.toString(),
                                        statusFilterList
                                    )
                            }
                        } else {
                            if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                                if (statusFilterList.isEmpty()) {
                                    dataBaseInstance.votersListDao()
                                        .getDPOLLGWiseUpdatedVotersListToSyncWithImage(
                                            districtId.toString(),
                                            llgId.toString()
                                        )
                                } else {
                                    dataBaseInstance.votersListDao()
                                        .getDPOLLGWiseUpdatedVotersListToSyncWithStatusFilterAndImage(
                                            districtId.toString(),
                                            llgId.toString(),
                                            statusFilterList
                                        )
                                }
                            } else {
                                if (statusFilterList.isEmpty()) {
                                    dataBaseInstance.votersListDao()
                                        .getDPOLLGWiseUpdatedVotersListToSyncWithImageForMKA(
                                            districtId.toString(),
                                            llgId.toString()
                                        )
                                } else {
                                    dataBaseInstance.votersListDao()
                                        .getDPOLLGWiseUpdatedVotersListToSyncWithStatusFilterAndImageForMKA(
                                            districtId.toString(),
                                            llgId.toString(),
                                            statusFilterList
                                        )
                                }
                            }
                        }

                    else ->
                        if (securedSharedPreferenceWrapper.getString(
                                ApiConstants.ROLE,
                                ""
                            ) == ApiConstants.ROLE_DPO
                        ) {
                            if (statusFilterList.isEmpty()) {
                                dataBaseInstance.votersListDao()
                                    .getDPODistrictWiseUpdatedVotersListToSyncWithImage(
                                        districtId.toString()
                                    )
                            } else {
                                dataBaseInstance.votersListDao()
                                    .getDPODistrictWiseUpdatedVotersListToSyncWithStatusFilterAndImage(
                                        districtId.toString(),
                                        statusFilterList
                                    )
                            }
                        } else {
                            if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                                if (statusFilterList.isEmpty()) {
                                    dataBaseInstance.votersListDao()
                                        .getDPODistrictWiseUpdatedVotersListToSyncWithImage(
                                            districtId.toString()
                                        )
                                } else {
                                    dataBaseInstance.votersListDao()
                                        .getDPODistrictWiseUpdatedVotersListToSyncWithStatusFilterAndImage(
                                            districtId.toString(),
                                            statusFilterList
                                        )
                                }
                            } else {
                                if (statusFilterList.isEmpty()) {
                                    dataBaseInstance.votersListDao()
                                        .getDPODistrictWiseUpdatedVotersListToSyncWithImageForMKA(
                                            districtId.toString()
                                        )
                                } else {
                                    dataBaseInstance.votersListDao()
                                        .getDPODistrictWiseUpdatedVotersListToSyncWithStatusFilterAndImageForMKA(
                                            districtId.toString(),
                                            statusFilterList
                                        )
                                }
                            }
                        }

                }
            }
            checkAndLoadData(result)
        }

    }

    private fun startSync() {
        if (CommonMethods.checkInternetConnection(this)) {
            showDownloadProgressDialog()
            currentUpdatedVotersList = 0
            val currentRequest: VotersList =
                selectedVotersList[currentUpdatedVotersList]
            callSyncVoterWithDataAndImageAPI(currentRequest)
        } else {
            binding?.cardSync?.isEnabled = true
            showAlertMessage("Internet connection is not available.Please check and try again")
        }
    }


    private fun loadData(result: List<VotersList>?) {
        val wardRollFilteredVotersList = mutableListOf<VotersList>()
        wardRollFilteredVotersList.addAll(result!!)
        setWardRollVotersListAdapter(wardRollFilteredVotersList)
    }

    private fun setWardRollVotersListAdapter(votersList: MutableList<VotersList>) {

        syncVotersListAdapter = object :
            GenericAdapter<VotersList, ItemSyncVoterNewBinding>(votersList) {
            override fun getLayoutId(): Int {
                return R.layout.item_sync_voter_new
            }

            @SuppressLint("SetTextI18n")
            override fun onBindData(
                model: VotersList?,
                position: Int,
                dataBinding: ItemSyncVoterNewBinding?
            ) {

                if (model?.electorProfileImage != null
                    && model.electorProfileImage.isNotEmpty()
                ) {
                    val image =
                        File(model.electorProfileImage)

                    if (image.exists()) {
                        Glide.with(this@VotersSyncWithDataAndImageActivity)
                            .load(Uri.fromFile(image))
                            .into(dataBinding?.ivVoterImage!!)
                    } else if (model.electorProfileImageUrl != null
                        && model.electorProfileImageUrl.isNotEmpty()
                    ) {

                        Glide.with(this@VotersSyncWithDataAndImageActivity)
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
                    if (model.electorProfileImage.isNotEmpty()) {
                        val image =
                            File(model.electorProfileImage)
                        Glide.with(this@VotersSyncWithDataAndImageActivity)
                            .load(Uri.fromFile(image))
                            .into(dataBinding?.ivVoterImage!!)
                    }

                } else {

                    if (model?.electorProfileImageUrl != null
                        && model.electorProfileImageUrl.isNotEmpty()
                    ) {

                        Glide.with(this@VotersSyncWithDataAndImageActivity)
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

                dataBinding?.cbSelectItem?.isChecked = selectedVotersList.contains(model)
                dataBinding?.tvVoterName?.text = getVoterName(model)
                if (model?.status == getString(R.string.new_status))
                    dataBinding?.tvVoterId?.text = "ID - 0"
                else
                    dataBinding?.tvVoterId?.text = "ID - " + model?.electorId
                dataBinding?.tvUpdatedDate?.text = getPostedDate(model?.posted)
//                dataBinding?.tvVoterLocation?.text = model?.locationName
                dataBinding?.tvVoterStatus?.text = model?.status
                dataBinding?.tvDob?.text = "DOB: " + CommonMethods.getDisplayDate(
                    CommonMethods.getVoterDetails(
                        model?.dob
                    )
                )
                when {
                    CommonMethods.getVoterDetails(model?.gender) == "F" -> dataBinding?.tvGender?.text =
                        "Gender: Female"

                    CommonMethods.getVoterDetails(model?.gender) == "M" -> dataBinding?.tvGender?.text =
                        "Gender: Male"

                    else -> dataBinding?.tvGender?.text = "Gender:"
                }

                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    dataBinding?.tvVoterLocation?.text = model?.locationName
                    dataBinding?.tvLLG?.text = "LLG: " + CommonMethods.getVoterDetails(
                        model?.llgName
                    )
                    dataBinding?.tvWard?.text = "Ward: " + CommonMethods.getVoterDetails(
                        model?.wardName
                    )
                    dataBinding?.tvVoterClan?.visibility = View.GONE
                } else {
                    dataBinding?.tvVoterLocation?.text = model?.mkaLocationName
                    dataBinding?.tvLLG?.text = "LLG: " + CommonMethods.getVoterDetails(
                        model?.mkaLlgName
                    )
                    dataBinding?.tvWard?.text = "Ward: " + CommonMethods.getVoterDetails(
                        model?.mkaWardName
                    )
                    if (!model?.mkaClanName.isNullOrEmpty()) {
                        dataBinding?.tvVoterClan?.visibility = View.VISIBLE
                        dataBinding?.tvVoterClan?.text = "Clan: " + model?.mkaClanName
                    } else {
                        dataBinding?.tvVoterClan?.visibility = View.GONE
                    }
                }

                dataBinding?.cbSelectItem?.setOnCheckedChangeListener { compoundButton, selected ->
                    if (compoundButton.isPressed) {
                        if (selected) {
                            if (!selectedVotersList.contains(model))
                                selectedVotersList.add(model!!)
                        } else {
                            selectedVotersList.remove(model)
                        }
                    }
                }

            }
        }
        binding?.rvSyncVotersList?.adapter = syncVotersListAdapter


    }

    private fun getVoterName(model: VotersList?): String {

        var lastNameOfVoter = ""
        var firstNameOfVoter = ""

        if (!model?.lName.isNullOrEmpty())
            lastNameOfVoter = model?.lName!!

        if (!model?.fName.isNullOrEmpty())
            firstNameOfVoter = model?.fName!!

        val voterName = "$lastNameOfVoter $firstNameOfVoter"
        return voterName.trim()

    }

    private fun getFailedVoterName(model: FailedVotersList?): String {
        var lastNameOfVoter = ""
        var firstNameOfVoter = ""
        if (!model?.lName.isNullOrEmpty())
            lastNameOfVoter = model?.lName!!
        if (!model?.fName.isNullOrEmpty())
            firstNameOfVoter = model?.fName!!
        val voterName = "$lastNameOfVoter $firstNameOfVoter"
        return voterName.trim()
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


    private fun checkCurrentVoterSyncStatus(
        currentVoter: VotersList,
        data: ArrayList<FailedVotersList>?
    ): Boolean {

        for (voter in data!!) {
            if (currentVoter.tempElectorId == voter.tempElectorId)
                return false
        }

        return true
    }

    private fun getErrorMessage(
        currentVoter: VotersList,
        data: ArrayList<FailedVotersList>?
    ): String {

        for (voter in data!!) {
            if (currentVoter.tempElectorId == voter.tempElectorId)
                return voter.errorMessage
        }

        return ""
    }


    private fun callSyncVoterWithDataAndImageAPI(currentVoter: VotersList) {

        val currentCount = currentUpdatedVotersList + 1
        val totalRecords = selectedVotersList.size
        percentage = (currentCount.toDouble() / totalRecords.toDouble() * 100).toInt()
        if (percentage == 100 && currentCount == 1)
            uploadSyncProgress(50,1)
        else
            uploadSyncProgress(percentage, currentCount)

        val electorId = if (currentVoter.status == getString(R.string.new_status))
            0
        else
            currentVoter.electorId.toInt()

        val electorID = MultipartBody.Part.createFormData(
            ApiConstants.ELECTOR_ID,
            electorId.toString()
        )

        val oldElectorID = MultipartBody.Part.createFormData(
            ApiConstants.OLD_ELECTOR_ID,
            currentVoter.oldElectorID.toString()
        )

        val locationId = MultipartBody.Part.createFormData(
            ApiConstants.LOCATION_ID,
            currentVoter.locationId.toString()
        )

        val oldLocationId = MultipartBody.Part.createFormData(
            ApiConstants.OLD_LOCATION_ID,
            currentVoter.oldLocationId.toString()
        )

        val locationName = MultipartBody.Part.createFormData(
            ApiConstants.LOCATION_NAME,
            currentVoter.locationName
        )

        val wardId = MultipartBody.Part.createFormData(
            ApiConstants.WARDID,
            currentVoter.wardId.toString()
        )

        val wardName = MultipartBody.Part.createFormData(
            ApiConstants.WARD_NAME,
            currentVoter.wardName
        )

        val llgId = MultipartBody.Part.createFormData(
            ApiConstants.LLG_ID,
            currentVoter.llgId.toString()
        )

        val llgName = MultipartBody.Part.createFormData(
            ApiConstants.LLG_NAME,
            currentVoter.llgName
        )

        val districtId = MultipartBody.Part.createFormData(
            ApiConstants.DISTRICT_ID,
            currentVoter.districtId.toString()
        )

        val provinceId = MultipartBody.Part.createFormData(
            ApiConstants.PROVINCE_ID,
            currentVoter.provinceId.toString()
        )

        val regionId = MultipartBody.Part.createFormData(
            ApiConstants.REGION_ID,
            currentVoter.regionId.toString()
        )

        /**Voter's MKA LLG,Ward,Location and Clan*/
        val mkaDistrictId = MultipartBody.Part.createFormData(
            "MKADistrictId",
            currentVoter.mkaDistrictId.toString()
        )

        val mkaLlgId = MultipartBody.Part.createFormData(
            "MKALLGId",
            currentVoter.mkaLlgId.toString()
        )

        val mkaWardId = MultipartBody.Part.createFormData(
            "MKAWardId",
            currentVoter.mkaWardId.toString()
        )

        val mkaLocationId = MultipartBody.Part.createFormData(
            "MKALocationId",
            currentVoter.mkaLocationId.toString()
        )

        val mkaClanId = MultipartBody.Part.createFormData(
            "MKAClanId",
            currentVoter.mkaClanId.toString()
        )

        val clanId = MultipartBody.Part.createFormData(
            "clanId",
            currentVoter.clanId.toString()
        )

        val lName = MultipartBody.Part.createFormData(
            ApiConstants.LNAME,
            currentVoter.lName
        )

        val fName = MultipartBody.Part.createFormData(
            ApiConstants.FNAME,
            currentVoter.fName
        )

        val voterName = MultipartBody.Part.createFormData(
            ApiConstants.VOTER_NAME,
            currentVoter.voterName
        )

        val gender = MultipartBody.Part.createFormData(
            ApiConstants.GENDER,
            currentVoter.gender
        )

        val dob = MultipartBody.Part.createFormData(
            ApiConstants.DOB,
            currentVoter.dob
        )

        val religion = MultipartBody.Part.createFormData(
            "religion",
            currentVoter.religion ?: ""
        )

        val fatherLName = MultipartBody.Part.createFormData(
            "fatherLName",
            currentVoter.fatherLName
        )

        val fatherFName = MultipartBody.Part.createFormData(
            "fatherFName",
            currentVoter.fatherFName
        )

        val motherLName = MultipartBody.Part.createFormData(
            "motherLName",
            currentVoter.motherLName
        )

        val motherFName = MultipartBody.Part.createFormData(
            "motherFName",
            currentVoter.motherFName
        )

        val provOfBirthFK = MultipartBody.Part.createFormData(
            "provOfBirthFK",
            currentVoter.provOfBirthFK.toString()
        )

        val provinceOfBirth = MultipartBody.Part.createFormData(
            "provinceOfBirth",
            currentVoter.provinceOfBirth
        )

        val provinceName = MultipartBody.Part.createFormData(
            "provinceName",
            currentVoter.provinceName
        )

        val openElectorate = MultipartBody.Part.createFormData(
            "openElectorate",
            currentVoter.districtName
        )

        val placeOfBirth = MultipartBody.Part.createFormData(
            "placeOfBirth",
            currentVoter.placeOfBirth ?: ""
        )

        val occupationFK = MultipartBody.Part.createFormData(
            "occupationFK",
            currentVoter.occupationFK.toString()
        )

        val occupation = MultipartBody.Part.createFormData(
            "occupation",
            currentVoter.occupation
        )

        val clan = MultipartBody.Part.createFormData(
            "clan",
            currentVoter.clan
        )

        val subClan = MultipartBody.Part.createFormData(
            "subClan",
            currentVoter.subClan
        )

        val address = MultipartBody.Part.createFormData(
            "address",
            currentVoter.address
        )

        val natID = MultipartBody.Part.createFormData(
            "natID",
            currentVoter.natID
        )

        val natIDRecordNo = MultipartBody.Part.createFormData(
            "natIDRecordNo",
            currentVoter.natIDRecordNo
        )

        val status = MultipartBody.Part.createFormData(
            "status",
            currentVoter.status
        )

        val houseFK = MultipartBody.Part.createFormData(
            "houseFK",
            currentVoter.houseFK.toString()
        )

        val houseSortOrder = MultipartBody.Part.createFormData(
            "houseSortOrder",
            currentVoter.houseSortOrder.toString()
        )

        val restHouse = MultipartBody.Part.createFormData(
            "restHouse",
            currentVoter.restHouse
        )

        val sectionNo = MultipartBody.Part.createFormData(
            "sectionNo",
            currentVoter.sectionNo.toString()
        )

        val lotNo = MultipartBody.Part.createFormData(
            "lotNo",
            currentVoter.lotNo.toString()
        )

        val streetName = MultipartBody.Part.createFormData(
            "streetName",
            currentVoter.streetName
        )

        val suburbTownSettlement = MultipartBody.Part.createFormData(
            "suburbTownSettlement",
            currentVoter.suburbTownSettlement
        )

        val familyFK = MultipartBody.Part.createFormData(
            "familyFK",
            currentVoter.familyFK.toString()
        )

        val familyNumber = MultipartBody.Part.createFormData(
            "familyNumber",
            currentVoter.familyNumber.toString()
        )

        val familyName = MultipartBody.Part.createFormData(
            "familyName",
            currentVoter.familyName
        )

        val familySortOrder = MultipartBody.Part.createFormData(
            "familySortOrder",
            currentVoter.familySortOrder.toString()
        )

        val dpo = MultipartBody.Part.createFormData(
            "dpo",
            currentVoter.dpo
        )

        val posted = MultipartBody.Part.createFormData(
            "posted",
            currentVoter.posted
        )

//        val comment = MultipartBody.Part.createFormData(
//            "comment",
//            currentVoter.comment
//        )

        val comment = MultipartBody.Part.createFormData(
            "comment",
            "Data Posted From { " +  Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID) + " }"
        )

        val electorProfileImageUrl = MultipartBody.Part.createFormData(
            "electorProfileImageUrl",
            currentVoter.electorProfileImageUrl
        )


        val electorFormImageUrl = MultipartBody.Part.createFormData(
            "electorFormImageUrl",
            currentVoter.electorFormImageUrl
        )

        val createdBy = MultipartBody.Part.createFormData(
            "createdBy",
            currentVoter.createdBy.toString()
        )

        val isManualForm11Number =
            CommonMethods.isManualForm11Number(currentVoter.enrolmentFormNumber)

        val enrolmentFormNumber = MultipartBody.Part.createFormData(
            "enrolmentFormNumber",
            CommonMethods.getForm11Number(currentVoter.enrolmentFormNumber)
        )

        /**Add Enrollment Form Type*/
        val enrolmentFormType = if (currentVoter.enrollFormType.isNotEmpty()) {
            MultipartBody.Part.createFormData(
                "EnrolmentFormType",
                CommonMethods.getForm11Number(currentVoter.enrollFormType)
            )
        } else {
            if (securedSharedPreferenceWrapper.getString(
                    ApiConstants.ROLE,
                    ""
                ) == ApiConstants.ROLE_DPO
            ) {
                MultipartBody.Part.createFormData(
                    "EnrolmentFormType",
                    "PNG"
                )
            } else {
                MultipartBody.Part.createFormData(
                    "EnrolmentFormType",
                    "MKA"
                )
            }
        }

        val tempElectorId = MultipartBody.Part.createFormData(
            "TempElectorId",
            currentVoter.tempElectorId.toString()
        )

        when (currentVoter.status) {
            getString(R.string.transferred_in) -> {


                val electorFormImageFile = File(currentVoter.electorFormImage.toString())
                val requestElectorFormImageFile =
                    electorFormImageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val formImageBody = MultipartBody.Part.createFormData(
                    "electorFormImageFile",
                    electorFormImageFile.name, requestElectorFormImageFile
                )

                val transferOutRegionId = MultipartBody.Part.createFormData(
                    "transferOutRegionId",
                    currentVoter.transferOutRegionId.toString()
                )

                val transferOutProvinceId = MultipartBody.Part.createFormData(
                    "transferOutProvinceId",
                    currentVoter.transferOutProvinceId.toString()
                )

                val transferOutDistrictId = MultipartBody.Part.createFormData(
                    "transferOutDistrictId",
                    currentVoter.transferOutDistrictId.toString()
                )

                val transferOutLLGId = MultipartBody.Part.createFormData(
                    "transferOutLLGId",
                    currentVoter.transferOutLLGId.toString()
                )

                val transferOutWardId = MultipartBody.Part.createFormData(
                    "transferOutWardId",
                    currentVoter.transferOutWardId.toString()
                )

                val transferOutLocationId = MultipartBody.Part.createFormData(
                    "transferOutLocationId",
                    currentVoter.transferOutLocationId.toString()
                )

                val transferOutMkaDistrictId = MultipartBody.Part.createFormData(
                    "TransferOutMKADistrictId",
                    currentVoter.transferOutMkaDistrictId.toString()
                )

                val transferOutMkaLlgId = MultipartBody.Part.createFormData(
                    "TransferOutMKALLGId",
                    currentVoter.transferOutMkaLlgId.toString()
                )

                val transferOutMkaWardId = MultipartBody.Part.createFormData(
                    "TransferOutMKAWardId",
                    currentVoter.transferOutMkaWardId.toString()
                )

                val transferOutMkaLocationId = MultipartBody.Part.createFormData(
                    "TransferOutMKALocationId",
                    currentVoter.transferOutMkaLocationId.toString()
                )

                if (currentVoter.electorProfileImage != null
                    && currentVoter.electorProfileImage.isNotEmpty()
                    && currentVoter.electorProfileImage.contains("/")
                ) {

                    val electorProfileImageFile = File(currentVoter.electorProfileImage.toString())
                    val requestElectorProfileImageFile =
                        electorProfileImageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val profileImageBody = MultipartBody.Part.createFormData(
                        "electorProfileImageFile",
                        electorProfileImageFile.name, requestElectorProfileImageFile
                    )

                    if (currentVoter.isRejected) {

                        val call = ApiClient.getRetrofitInterface()
                            ?.syncRejectedTransferInElectorsAndImages(
                                electorID,
                                oldElectorID,
                                locationId,
                                oldLocationId,
                                locationName,
                                wardId,
                                wardName,
                                llgId,
                                llgName,
                                districtId,
                                provinceId,
                                regionId,
                                mkaDistrictId,
                                mkaLlgId,
                                mkaWardId,
                                mkaLocationId,
                                mkaClanId,
                                lName,
                                fName,
                                voterName,
                                gender,
                                dob,
                                false,
                                currentVoter.isPWD,
                                currentVoter.isRejected,
                                religion,
                                fatherLName,
                                fatherFName,
                                motherLName,
                                motherFName,
                                provOfBirthFK,
                                provinceOfBirth,
                                provinceName,
                                openElectorate,
                                placeOfBirth,
                                occupationFK,
                                occupation,
                                clan,
                                subClan,
                                address,
                                natID,
                                natIDRecordNo,
                                status,
                                houseFK,
                                houseSortOrder,
                                restHouse,
                                sectionNo,
                                lotNo,
                                streetName,
                                suburbTownSettlement,
                                familyFK,
                                familyNumber,
                                familyName,
                                familySortOrder,
                                dpo,
                                currentVoter.lat,
                                currentVoter.longitude,
                                posted,
                                comment,
                                electorProfileImageUrl,
                                electorFormImageUrl,
                                profileImageBody,
                                formImageBody,
                                enrolmentFormNumber,
                                enrolmentFormType,
                                isManualForm11Number,
                                createdBy,
                                tempElectorId,
                                transferOutRegionId,
                                transferOutProvinceId,
                                transferOutDistrictId,
                                transferOutLLGId,
                                transferOutWardId,
                                transferOutLocationId,
                                transferOutMkaDistrictId,
                                transferOutMkaLlgId,
                                transferOutMkaWardId,
                                transferOutMkaLocationId,
                                clanId
                            )

                        WSClient<Response<ArrayList<FailedVotersList>>>().request(
                            this,
                            ApiConstants.DPO_SYNC_VOTERS_LIST,
                            call,
                            object :
                                ISuccessHandler<Response<ArrayList<FailedVotersList>>> {
                                override fun successResponse(
                                    requestCode: Int,
                                    mResponse: Response<ArrayList<FailedVotersList>>
                                ) {
                                    isSyncDone = true
                                    UpdateVotersDataAfterSyncVoterWithDataAndImage(
                                        currentVoter,
                                        mResponse
                                    ).execute()
                                }

                            },
                            object : IFailureHandler {
                                override fun failureResponse(requestCode: Int, message: String) {

                                    currentUpdatedVotersList++
                                    if (selectedVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedVotersList[currentUpdatedVotersList]
                                            callSyncVoterWithDataAndImageAPI(currentRequest)
                                        } else {
                                            hideSyncProgress()
                                            alert(message) {
                                                okButton {
                                                    finish()
                                                }
                                            }.show()
                                        }
                                    } else {
                                        hideSyncProgress()
                                        alert(message) {
                                            okButton {
                                                finish()
                                            }
                                        }.show()
                                    }

                                }
                            })

                    } else {

                        val call =
                            ApiClient.getRetrofitInterface()?.syncTransferInElectorsAndImages(
                                electorID,
                                oldElectorID,
                                locationId,
                                oldLocationId,
                                locationName,
                                wardId,
                                wardName,
                                llgId,
                                llgName,
                                districtId,
                                provinceId,
                                regionId,
                                mkaDistrictId,
                                mkaLlgId,
                                mkaWardId,
                                mkaLocationId,
                                mkaClanId,
                                lName,
                                fName,
                                voterName,
                                gender,
                                dob,
                                false,
                                currentVoter.isPWD,
                                religion,
                                fatherLName,
                                fatherFName,
                                motherLName,
                                motherFName,
                                provOfBirthFK,
                                provinceOfBirth,
                                provinceName,
                                openElectorate,
                                placeOfBirth,
                                occupationFK,
                                occupation,
                                clan,
                                subClan,
                                address,
                                natID,
                                natIDRecordNo,
                                status,
                                houseFK,
                                houseSortOrder,
                                restHouse,
                                sectionNo,
                                lotNo,
                                streetName,
                                suburbTownSettlement,
                                familyFK,
                                familyNumber,
                                familyName,
                                familySortOrder,
                                dpo,
                                currentVoter.lat,
                                currentVoter.longitude,
                                posted,
                                comment,
                                electorProfileImageUrl,
                                electorFormImageUrl,
                                profileImageBody,
                                formImageBody,
                                enrolmentFormNumber,
                                enrolmentFormType,
                                isManualForm11Number,
                                createdBy,
                                tempElectorId,
                                transferOutRegionId,
                                transferOutProvinceId,
                                transferOutDistrictId,
                                transferOutLLGId,
                                transferOutWardId,
                                transferOutLocationId,
                                transferOutMkaDistrictId,
                                transferOutMkaLlgId,
                                transferOutMkaWardId,
                                transferOutMkaLocationId,
                                clanId
                            )

                        WSClient<Response<ArrayList<FailedVotersList>>>().request(
                            this,
                            ApiConstants.DPO_SYNC_VOTERS_LIST,
                            call,
                            object :
                                ISuccessHandler<Response<ArrayList<FailedVotersList>>> {
                                override fun successResponse(
                                    requestCode: Int,
                                    mResponse: Response<ArrayList<FailedVotersList>>
                                ) {
                                    isSyncDone = true
                                    UpdateVotersDataAfterSyncVoterWithDataAndImage(
                                        currentVoter,
                                        mResponse
                                    ).execute()
                                }

                            },
                            object : IFailureHandler {
                                override fun failureResponse(requestCode: Int, message: String) {

                                    currentUpdatedVotersList++
                                    if (selectedVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedVotersList[currentUpdatedVotersList]
                                            callSyncVoterWithDataAndImageAPI(currentRequest)
                                        } else {
                                            hideSyncProgress()
                                            alert(message) {
                                                okButton {
                                                    finish()
                                                }
                                            }.show()
                                        }
                                    } else {
                                        hideSyncProgress()
                                        alert(message) {
                                            okButton {
                                                finish()
                                            }
                                        }.show()
                                    }

                                }
                            })

                    }


                } else {

                    if (currentVoter.isRejected) {
                        val call = ApiClient.getRetrofitInterface()
                            ?.syncRejectedTransferInElectorsAndImagesWithOutProfile(
                                electorID,
                                oldElectorID,
                                locationId,
                                oldLocationId,
                                locationName,
                                wardId,
                                wardName,
                                llgId,
                                llgName,
                                districtId,
                                provinceId,
                                regionId,
                                mkaDistrictId,
                                mkaLlgId,
                                mkaWardId,
                                mkaLocationId,
                                mkaClanId,
                                lName,
                                fName,
                                voterName,
                                gender,
                                dob,
                                false,
                                currentVoter.isPWD,
                                currentVoter.isRejected,
                                religion,
                                fatherLName,
                                fatherFName,
                                motherLName,
                                motherFName,
                                provOfBirthFK,
                                provinceOfBirth,
                                provinceName,
                                openElectorate,
                                placeOfBirth,
                                occupationFK,
                                occupation,
                                clan,
                                subClan,
                                address,
                                natID,
                                natIDRecordNo,
                                status,
                                houseFK,
                                houseSortOrder,
                                restHouse,
                                sectionNo,
                                lotNo,
                                streetName,
                                suburbTownSettlement,
                                familyFK,
                                familyNumber,
                                familyName,
                                familySortOrder,
                                dpo,
                                currentVoter.lat,
                                currentVoter.longitude,
                                posted,
                                comment,
                                electorProfileImageUrl,
                                electorFormImageUrl,
                                formImageBody,
                                enrolmentFormNumber,
                                enrolmentFormType,
                                isManualForm11Number,
                                createdBy,
                                tempElectorId,
                                transferOutRegionId,
                                transferOutProvinceId,
                                transferOutDistrictId,
                                transferOutLLGId,
                                transferOutWardId,
                                transferOutLocationId,
                                transferOutMkaDistrictId,
                                transferOutMkaLlgId,
                                transferOutMkaWardId,
                                transferOutMkaLocationId,
                                clanId
                            )

                        WSClient<Response<ArrayList<FailedVotersList>>>().request(
                            this,
                            ApiConstants.DPO_SYNC_VOTERS_LIST,
                            call,
                            object :
                                ISuccessHandler<Response<ArrayList<FailedVotersList>>> {
                                override fun successResponse(
                                    requestCode: Int,
                                    mResponse: Response<ArrayList<FailedVotersList>>
                                ) {
                                    isSyncDone = true
                                    UpdateVotersDataAfterSyncVoterWithDataAndImage(
                                        currentVoter,
                                        mResponse
                                    ).execute()
                                }

                            },
                            object : IFailureHandler {
                                override fun failureResponse(requestCode: Int, message: String) {

                                    currentUpdatedVotersList++
                                    if (selectedVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedVotersList[currentUpdatedVotersList]
                                            callSyncVoterWithDataAndImageAPI(currentRequest)
                                        } else {
                                            hideSyncProgress()
                                            alert(message) {
                                                okButton {
                                                    finish()
                                                }
                                            }.show()
                                        }
                                    } else {
                                        hideSyncProgress()
                                        alert(message) {
                                            okButton {
                                                finish()
                                            }
                                        }.show()
                                    }

                                }
                            })
                    } else {
                        val call = ApiClient.getRetrofitInterface()
                            ?.syncTransferInElectorsAndImagesWithOutProfile(
                                electorID,
                                oldElectorID,
                                locationId,
                                oldLocationId,
                                locationName,
                                wardId,
                                wardName,
                                llgId,
                                llgName,
                                districtId,
                                provinceId,
                                regionId,
                                mkaDistrictId,
                                mkaLlgId,
                                mkaWardId,
                                mkaLocationId,
                                mkaClanId,
                                lName,
                                fName,
                                voterName,
                                gender,
                                dob,
                                false,
                                currentVoter.isPWD,
                                religion,
                                fatherLName,
                                fatherFName,
                                motherLName,
                                motherFName,
                                provOfBirthFK,
                                provinceOfBirth,
                                provinceName,
                                openElectorate,
                                placeOfBirth,
                                occupationFK,
                                occupation,
                                clan,
                                subClan,
                                address,
                                natID,
                                natIDRecordNo,
                                status,
                                houseFK,
                                houseSortOrder,
                                restHouse,
                                sectionNo,
                                lotNo,
                                streetName,
                                suburbTownSettlement,
                                familyFK,
                                familyNumber,
                                familyName,
                                familySortOrder,
                                dpo,
                                currentVoter.lat,
                                currentVoter.longitude,
                                posted,
                                comment,
                                electorProfileImageUrl,
                                electorFormImageUrl,
                                formImageBody,
                                enrolmentFormNumber,
                                enrolmentFormType,
                                isManualForm11Number,
                                createdBy,
                                tempElectorId,
                                transferOutRegionId,
                                transferOutProvinceId,
                                transferOutDistrictId,
                                transferOutLLGId,
                                transferOutWardId,
                                transferOutLocationId,
                                transferOutMkaDistrictId,
                                transferOutMkaLlgId,
                                transferOutMkaWardId,
                                transferOutMkaLocationId,
                                clanId
                            )

                        WSClient<Response<ArrayList<FailedVotersList>>>().request(
                            this,
                            ApiConstants.DPO_SYNC_VOTERS_LIST,
                            call,
                            object :
                                ISuccessHandler<Response<ArrayList<FailedVotersList>>> {
                                override fun successResponse(
                                    requestCode: Int,
                                    mResponse: Response<ArrayList<FailedVotersList>>
                                ) {
                                    isSyncDone = true
                                    UpdateVotersDataAfterSyncVoterWithDataAndImage(
                                        currentVoter,
                                        mResponse
                                    ).execute()
                                }

                            },
                            object : IFailureHandler {
                                override fun failureResponse(requestCode: Int, message: String) {

                                    currentUpdatedVotersList++
                                    if (selectedVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedVotersList[currentUpdatedVotersList]
                                            callSyncVoterWithDataAndImageAPI(currentRequest)
                                        } else {
                                            hideSyncProgress()
                                            alert(message) {
                                                okButton {
                                                    finish()
                                                }
                                            }.show()
                                        }
                                    } else {
                                        hideSyncProgress()
                                        alert(message) {
                                            okButton {
                                                finish()
                                            }
                                        }.show()
                                    }

                                }
                            })
                    }


                }


            }

            getString(R.string.transferred_out) -> {


                val electorFormImageFile = File(currentVoter.electorFormImage.toString())
                val requestElectorFormImageFile =
                    electorFormImageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val formImageBody = MultipartBody.Part.createFormData(
                    "electorFormImageFile",
                    electorFormImageFile.name, requestElectorFormImageFile
                )

                val transferOutRegionId = MultipartBody.Part.createFormData(
                    "transferInRegionId",
                    currentVoter.transferInRegionId.toString()
                )

                val transferOutProvinceId = MultipartBody.Part.createFormData(
                    "transferInProvinceId",
                    currentVoter.transferInProvinceId.toString()
                )

                val transferOutDistrictId = MultipartBody.Part.createFormData(
                    "transferInDistrictId",
                    currentVoter.transferInDistrictId.toString()
                )

                val transferOutLLGId = MultipartBody.Part.createFormData(
                    "transferInLLGId",
                    currentVoter.transferInLLGId.toString()
                )

                val transferOutWardId = MultipartBody.Part.createFormData(
                    "transferInWardId",
                    currentVoter.transferInWardId.toString()
                )

                val transferOutLocationId = MultipartBody.Part.createFormData(
                    "transferInLocationId",
                    currentVoter.transferInLocationId.toString()
                )

                val transferOutMkaDistrictId = MultipartBody.Part.createFormData(
                    "TransferOutMKADistrictId",
                    currentVoter.transferOutMkaDistrictId.toString()
                )

                val transferOutMkaLlgId = MultipartBody.Part.createFormData(
                    "TransferOutMKALLGId",
                    currentVoter.transferOutMkaLlgId.toString()
                )

                val transferOutMkaWardId = MultipartBody.Part.createFormData(
                    "TransferOutMKAWardId",
                    currentVoter.transferOutMkaWardId.toString()
                )

                val transferOutMkaLocationId = MultipartBody.Part.createFormData(
                    "TransferOutMKALocationId",
                    currentVoter.transferOutMkaLocationId.toString()
                )

                if (currentVoter.electorProfileImage != null
                    && currentVoter.electorProfileImage.isNotEmpty()
                    && currentVoter.electorProfileImage.contains("/")
                ) {

                    val electorProfileImageFile = File(currentVoter.electorProfileImage.toString())
                    val requestElectorProfileImageFile =
                        electorProfileImageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val profileImageBody = MultipartBody.Part.createFormData(
                        "electorProfileImageFile",
                        electorProfileImageFile.name, requestElectorProfileImageFile
                    )

                    if (currentVoter.isRejected) {

                        val call = ApiClient.getRetrofitInterface()
                            ?.syncRejectedTransferInElectorsAndImages(
                                electorID,
                                oldElectorID,
                                locationId,
                                oldLocationId,
                                locationName,
                                wardId,
                                wardName,
                                llgId,
                                llgName,
                                districtId,
                                provinceId,
                                regionId,
                                mkaDistrictId,
                                mkaLlgId,
                                mkaWardId,
                                mkaLocationId,
                                mkaClanId,
                                lName,
                                fName,
                                voterName,
                                gender,
                                dob,
                                false,
                                currentVoter.isPWD,
                                currentVoter.isRejected,
                                religion,
                                fatherLName,
                                fatherFName,
                                motherLName,
                                motherFName,
                                provOfBirthFK,
                                provinceOfBirth,
                                provinceName,
                                openElectorate,
                                placeOfBirth,
                                occupationFK,
                                occupation,
                                clan,
                                subClan,
                                address,
                                natID,
                                natIDRecordNo,
                                status,
                                houseFK,
                                houseSortOrder,
                                restHouse,
                                sectionNo,
                                lotNo,
                                streetName,
                                suburbTownSettlement,
                                familyFK,
                                familyNumber,
                                familyName,
                                familySortOrder,
                                dpo,
                                currentVoter.lat,
                                currentVoter.longitude,
                                posted,
                                comment,
                                electorProfileImageUrl,
                                electorFormImageUrl,
                                profileImageBody,
                                formImageBody,
                                enrolmentFormNumber,
                                enrolmentFormType,
                                isManualForm11Number,
                                createdBy,
                                tempElectorId,
                                transferOutRegionId,
                                transferOutProvinceId,
                                transferOutDistrictId,
                                transferOutLLGId,
                                transferOutWardId,
                                transferOutLocationId,
                                transferOutMkaDistrictId,
                                transferOutMkaLlgId,
                                transferOutMkaWardId,
                                transferOutMkaLocationId,
                                clanId
                            )

                        WSClient<Response<ArrayList<FailedVotersList>>>().request(
                            this,
                            ApiConstants.DPO_SYNC_VOTERS_LIST,
                            call,
                            object :
                                ISuccessHandler<Response<ArrayList<FailedVotersList>>> {
                                override fun successResponse(
                                    requestCode: Int,
                                    mResponse: Response<ArrayList<FailedVotersList>>
                                ) {
                                    isSyncDone = true
                                    UpdateVotersDataAfterSyncVoterWithDataAndImage(
                                        currentVoter,
                                        mResponse
                                    ).execute()
                                }

                            },
                            object : IFailureHandler {
                                override fun failureResponse(requestCode: Int, message: String) {

                                    currentUpdatedVotersList++
                                    if (selectedVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedVotersList[currentUpdatedVotersList]
                                            callSyncVoterWithDataAndImageAPI(currentRequest)
                                        } else {
                                            hideSyncProgress()
                                            alert(message) {
                                                okButton {
                                                    finish()
                                                }
                                            }.show()
                                        }
                                    } else {
                                        hideSyncProgress()
                                        alert(message) {
                                            okButton {
                                                finish()
                                            }
                                        }.show()
                                    }

                                }
                            })

                    } else {

                        val call =
                            ApiClient.getRetrofitInterface()?.syncTransferInElectorsAndImages(
                                electorID,
                                oldElectorID,
                                locationId,
                                oldLocationId,
                                locationName,
                                wardId,
                                wardName,
                                llgId,
                                llgName,
                                districtId,
                                provinceId,
                                regionId,
                                mkaDistrictId,
                                mkaLlgId,
                                mkaWardId,
                                mkaLocationId,
                                mkaClanId,
                                lName,
                                fName,
                                voterName,
                                gender,
                                dob,
                                false,
                                currentVoter.isPWD,
                                religion,
                                fatherLName,
                                fatherFName,
                                motherLName,
                                motherFName,
                                provOfBirthFK,
                                provinceOfBirth,
                                provinceName,
                                openElectorate,
                                placeOfBirth,
                                occupationFK,
                                occupation,
                                clan,
                                subClan,
                                address,
                                natID,
                                natIDRecordNo,
                                status,
                                houseFK,
                                houseSortOrder,
                                restHouse,
                                sectionNo,
                                lotNo,
                                streetName,
                                suburbTownSettlement,
                                familyFK,
                                familyNumber,
                                familyName,
                                familySortOrder,
                                dpo,
                                currentVoter.lat,
                                currentVoter.longitude,
                                posted,
                                comment,
                                electorProfileImageUrl,
                                electorFormImageUrl,
                                profileImageBody,
                                formImageBody,
                                enrolmentFormNumber,
                                enrolmentFormType,
                                isManualForm11Number,
                                createdBy,
                                tempElectorId,
                                transferOutRegionId,
                                transferOutProvinceId,
                                transferOutDistrictId,
                                transferOutLLGId,
                                transferOutWardId,
                                transferOutLocationId,
                                transferOutMkaDistrictId,
                                transferOutMkaLlgId,
                                transferOutMkaWardId,
                                transferOutMkaLocationId,
                                clanId
                            )

                        WSClient<Response<ArrayList<FailedVotersList>>>().request(
                            this,
                            ApiConstants.DPO_SYNC_VOTERS_LIST,
                            call,
                            object :
                                ISuccessHandler<Response<ArrayList<FailedVotersList>>> {
                                override fun successResponse(
                                    requestCode: Int,
                                    mResponse: Response<ArrayList<FailedVotersList>>
                                ) {
                                    isSyncDone = true
                                    UpdateVotersDataAfterSyncVoterWithDataAndImage(
                                        currentVoter,
                                        mResponse
                                    ).execute()
                                }

                            },
                            object : IFailureHandler {
                                override fun failureResponse(requestCode: Int, message: String) {

                                    currentUpdatedVotersList++
                                    if (selectedVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedVotersList[currentUpdatedVotersList]
                                            callSyncVoterWithDataAndImageAPI(currentRequest)
                                        } else {
                                            hideSyncProgress()
                                            alert(message) {
                                                okButton {
                                                    finish()
                                                }
                                            }.show()
                                        }
                                    } else {
                                        hideSyncProgress()
                                        alert(message) {
                                            okButton {
                                                finish()
                                            }
                                        }.show()
                                    }

                                }
                            })

                    }


                } else {

                    if (currentVoter.isRejected) {
                        val call = ApiClient.getRetrofitInterface()
                            ?.syncRejectedTransferInElectorsAndImagesWithOutProfile(
                                electorID,
                                oldElectorID,
                                locationId,
                                oldLocationId,
                                locationName,
                                wardId,
                                wardName,
                                llgId,
                                llgName,
                                districtId,
                                provinceId,
                                regionId,
                                mkaDistrictId,
                                mkaLlgId,
                                mkaWardId,
                                mkaLocationId,
                                mkaClanId,
                                lName,
                                fName,
                                voterName,
                                gender,
                                dob,
                                false,
                                currentVoter.isPWD,
                                currentVoter.isRejected,
                                religion,
                                fatherLName,
                                fatherFName,
                                motherLName,
                                motherFName,
                                provOfBirthFK,
                                provinceOfBirth,
                                provinceName,
                                openElectorate,
                                placeOfBirth,
                                occupationFK,
                                occupation,
                                clan,
                                subClan,
                                address,
                                natID,
                                natIDRecordNo,
                                status,
                                houseFK,
                                houseSortOrder,
                                restHouse,
                                sectionNo,
                                lotNo,
                                streetName,
                                suburbTownSettlement,
                                familyFK,
                                familyNumber,
                                familyName,
                                familySortOrder,
                                dpo,
                                currentVoter.lat,
                                currentVoter.longitude,
                                posted,
                                comment,
                                electorProfileImageUrl,
                                electorFormImageUrl,
                                formImageBody,
                                enrolmentFormNumber,
                                enrolmentFormType,
                                isManualForm11Number,
                                createdBy,
                                tempElectorId,
                                transferOutRegionId,
                                transferOutProvinceId,
                                transferOutDistrictId,
                                transferOutLLGId,
                                transferOutWardId,
                                transferOutLocationId,
                                transferOutMkaDistrictId,
                                transferOutMkaLlgId,
                                transferOutMkaWardId,
                                transferOutMkaLocationId,
                                clanId
                            )

                        WSClient<Response<ArrayList<FailedVotersList>>>().request(
                            this,
                            ApiConstants.DPO_SYNC_VOTERS_LIST,
                            call,
                            object :
                                ISuccessHandler<Response<ArrayList<FailedVotersList>>> {
                                override fun successResponse(
                                    requestCode: Int,
                                    mResponse: Response<ArrayList<FailedVotersList>>
                                ) {
                                    isSyncDone = true
                                    UpdateVotersDataAfterSyncVoterWithDataAndImage(
                                        currentVoter,
                                        mResponse
                                    ).execute()
                                }

                            },
                            object : IFailureHandler {
                                override fun failureResponse(requestCode: Int, message: String) {

                                    currentUpdatedVotersList++
                                    if (selectedVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedVotersList[currentUpdatedVotersList]
                                            callSyncVoterWithDataAndImageAPI(currentRequest)
                                        } else {
                                            hideSyncProgress()
                                            alert(message) {
                                                okButton {
                                                    finish()
                                                }
                                            }.show()
                                        }
                                    } else {
                                        hideSyncProgress()
                                        alert(message) {
                                            okButton {
                                                finish()
                                            }
                                        }.show()
                                    }

                                }
                            })
                    } else {

                        if (currentVoter.electorFormImage == null
                            || currentVoter.electorFormImage.isEmpty()
                        ) {
                            val call =
                                ApiClient.getRetrofitInterface()
                                    ?.syncElectorsDataOnly(
                                        electorID,
                                        oldElectorID,
                                        locationId,
                                        oldLocationId,
                                        locationName,
                                        wardId,
                                        wardName,
                                        llgId,
                                        llgName,
                                        districtId,
                                        provinceId,
                                        regionId,
                                        mkaDistrictId,
                                        mkaLlgId,
                                        mkaWardId,
                                        mkaLocationId,
                                        mkaClanId,
                                        lName,
                                        fName,
                                        voterName,
                                        gender,
                                        dob,
                                        false,
                                        currentVoter.isPWD,
                                        religion,
                                        fatherLName,
                                        fatherFName,
                                        motherLName,
                                        motherFName,
                                        provOfBirthFK,
                                        provinceOfBirth,
                                        provinceName,
                                        openElectorate,
                                        placeOfBirth,
                                        occupationFK,
                                        occupation,
                                        clan,
                                        subClan,
                                        address,
                                        natID,
                                        natIDRecordNo,
                                        status,
                                        houseFK,
                                        houseSortOrder,
                                        restHouse,
                                        sectionNo,
                                        lotNo,
                                        streetName,
                                        suburbTownSettlement,
                                        familyFK,
                                        familyNumber,
                                        familyName,
                                        familySortOrder,
                                        dpo,
                                        currentVoter.lat,
                                        currentVoter.longitude,
                                        posted,
                                        comment,
                                        electorProfileImageUrl,
                                        electorFormImageUrl,
                                        enrolmentFormNumber,
                                        enrolmentFormType,
                                        isManualForm11Number,
                                        createdBy,
                                        tempElectorId,
                                        clanId
                                    )

                            WSClient<Response<ArrayList<FailedVotersList>>>().request(
                                this,
                                ApiConstants.DPO_SYNC_VOTERS_LIST,
                                call,
                                object :
                                    ISuccessHandler<Response<ArrayList<FailedVotersList>>> {
                                    override fun successResponse(
                                        requestCode: Int,
                                        mResponse: Response<ArrayList<FailedVotersList>>
                                    ) {
                                        isSyncDone = true
                                        UpdateVotersDataAfterSyncVoterWithDataAndImage(
                                            currentVoter,
                                            mResponse
                                        ).execute()
                                    }

                                },
                                object : IFailureHandler {
                                    override fun failureResponse(
                                        requestCode: Int,
                                        message: String
                                    ) {

                                        currentUpdatedVotersList++
                                        if (selectedVotersList.size > currentUpdatedVotersList) {
                                            if (CommonMethods.checkInternetConnection(application)) {
                                                val currentRequest: VotersList =
                                                    selectedVotersList[currentUpdatedVotersList]
                                                callSyncVoterWithDataAndImageAPI(currentRequest)
                                            } else {
                                                hideSyncProgress()
                                                alert(message) {
                                                    okButton {
                                                        finish()
                                                    }
                                                }.show()
                                            }
                                        } else {
                                            hideSyncProgress()
                                            alert(message) {
                                                okButton {
                                                    finish()
                                                }
                                            }.show()
                                        }

                                    }
                                })
                        } else {
                            val call = ApiClient.getRetrofitInterface()
                                ?.syncTransferInElectorsAndImagesWithOutProfile(
                                    electorID,
                                    oldElectorID,
                                    locationId,
                                    oldLocationId,
                                    locationName,
                                    wardId,
                                    wardName,
                                    llgId,
                                    llgName,
                                    districtId,
                                    provinceId,
                                    regionId,
                                    mkaDistrictId,
                                    mkaLlgId,
                                    mkaWardId,
                                    mkaLocationId,
                                    mkaClanId,
                                    lName,
                                    fName,
                                    voterName,
                                    gender,
                                    dob,
                                    false,
                                    currentVoter.isPWD,
                                    religion,
                                    fatherLName,
                                    fatherFName,
                                    motherLName,
                                    motherFName,
                                    provOfBirthFK,
                                    provinceOfBirth,
                                    provinceName,
                                    openElectorate,
                                    placeOfBirth,
                                    occupationFK,
                                    occupation,
                                    clan,
                                    subClan,
                                    address,
                                    natID,
                                    natIDRecordNo,
                                    status,
                                    houseFK,
                                    houseSortOrder,
                                    restHouse,
                                    sectionNo,
                                    lotNo,
                                    streetName,
                                    suburbTownSettlement,
                                    familyFK,
                                    familyNumber,
                                    familyName,
                                    familySortOrder,
                                    dpo,
                                    currentVoter.lat,
                                    currentVoter.longitude,
                                    posted,
                                    comment,
                                    electorProfileImageUrl,
                                    electorFormImageUrl,
                                    formImageBody,
                                    enrolmentFormNumber,
                                    enrolmentFormType,
                                    isManualForm11Number,
                                    createdBy,
                                    tempElectorId,
                                    transferOutRegionId,
                                    transferOutProvinceId,
                                    transferOutDistrictId,
                                    transferOutLLGId,
                                    transferOutWardId,
                                    transferOutLocationId,
                                    transferOutMkaDistrictId,
                                    transferOutMkaLlgId,
                                    transferOutMkaWardId,
                                    transferOutMkaLocationId,
                                    clanId
                                )

                            WSClient<Response<ArrayList<FailedVotersList>>>().request(
                                this,
                                ApiConstants.DPO_SYNC_VOTERS_LIST,
                                call,
                                object :
                                    ISuccessHandler<Response<ArrayList<FailedVotersList>>> {
                                    override fun successResponse(
                                        requestCode: Int,
                                        mResponse: Response<ArrayList<FailedVotersList>>
                                    ) {
                                        isSyncDone = true
                                        UpdateVotersDataAfterSyncVoterWithDataAndImage(
                                            currentVoter,
                                            mResponse
                                        ).execute()
                                    }

                                },
                                object : IFailureHandler {
                                    override fun failureResponse(
                                        requestCode: Int,
                                        message: String
                                    ) {

                                        currentUpdatedVotersList++
                                        if (selectedVotersList.size > currentUpdatedVotersList) {
                                            if (CommonMethods.checkInternetConnection(application)) {
                                                val currentRequest: VotersList =
                                                    selectedVotersList[currentUpdatedVotersList]
                                                callSyncVoterWithDataAndImageAPI(currentRequest)
                                            } else {
                                                hideSyncProgress()
                                                alert(message) {
                                                    okButton {
                                                        finish()
                                                    }
                                                }.show()
                                            }
                                        } else {
                                            hideSyncProgress()
                                            alert(message) {
                                                okButton {
                                                    finish()
                                                }
                                            }.show()
                                        }

                                    }
                                })
                        }

                    }
                }


            }

            getString(R.string.new_status) -> {

                val electorFormImageFile = File(currentVoter.electorFormImage.toString())
                val requestElectorFormImageFile =
                    electorFormImageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val formImageBody = MultipartBody.Part.createFormData(
                    "electorFormImageFile",
                    electorFormImageFile.name, requestElectorFormImageFile
                )

                if (currentVoter.electorProfileImage != null
                    && currentVoter.electorProfileImage.isNotEmpty()
                    && currentVoter.electorProfileImage.contains("/")
                ) {

                    val electorProfileImageFile = File(currentVoter.electorProfileImage.toString())
                    val requestElectorProfileImageFile =
                        electorProfileImageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val profileImageBody = MultipartBody.Part.createFormData(
                        "electorProfileImageFile",
                        electorProfileImageFile.name, requestElectorProfileImageFile
                    )

                    if (currentVoter.isRejected) {
                        val call = ApiClient.getRetrofitInterface()?.syncRejectedElectorsAndImages(
                            electorID,
                            oldElectorID,
                            locationId,
                            oldLocationId,
                            locationName,
                            wardId,
                            wardName,
                            llgId,
                            llgName,
                            districtId,
                            provinceId,
                            regionId,
                            mkaDistrictId,
                            mkaLlgId,
                            mkaWardId,
                            mkaLocationId,
                            mkaClanId,
                            lName,
                            fName,
                            voterName,
                            gender,
                            dob,
                            false,
                            currentVoter.isPWD,
                            currentVoter.isRejected,
                            religion,
                            fatherLName,
                            fatherFName,
                            motherLName,
                            motherFName,
                            provOfBirthFK,
                            provinceOfBirth,
                            provinceName,
                            openElectorate,
                            placeOfBirth,
                            occupationFK,
                            occupation,
                            clan,
                            subClan,
                            address,
                            natID,
                            natIDRecordNo,
                            status,
                            houseFK,
                            houseSortOrder,
                            restHouse,
                            sectionNo,
                            lotNo,
                            streetName,
                            suburbTownSettlement,
                            familyFK,
                            familyNumber,
                            familyName,
                            familySortOrder,
                            dpo,
                            currentVoter.lat,
                            currentVoter.longitude,
                            posted,
                            comment,
                            electorProfileImageUrl,
                            electorFormImageUrl,
                            profileImageBody,
                            formImageBody,
                            enrolmentFormNumber,
                            enrolmentFormType,
                            isManualForm11Number,
                            createdBy,
                            tempElectorId,
                            clanId
                        )

                        WSClient<Response<ArrayList<FailedVotersList>>>().request(
                            this,
                            ApiConstants.DPO_SYNC_VOTERS_LIST,
                            call,
                            object :
                                ISuccessHandler<Response<ArrayList<FailedVotersList>>> {
                                override fun successResponse(
                                    requestCode: Int,
                                    mResponse: Response<ArrayList<FailedVotersList>>
                                ) {
                                    isSyncDone = true
                                    UpdateVotersDataAfterSyncVoterWithDataAndImage(
                                        currentVoter,
                                        mResponse
                                    ).execute()
                                }

                            },
                            object : IFailureHandler {
                                override fun failureResponse(requestCode: Int, message: String) {
                                    currentUpdatedVotersList++
                                    if (selectedVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedVotersList[currentUpdatedVotersList]
                                            callSyncVoterWithDataAndImageAPI(currentRequest)
                                        } else {
                                            hideSyncProgress()
                                            alert(message) {
                                                okButton {
                                                    finish()
                                                }
                                            }.show()
                                        }
                                    } else {
                                        hideSyncProgress()
                                        alert(message) {
                                            okButton {
                                                finish()
                                            }
                                        }.show()
                                    }

                                }
                            })
                    } else {
                        val call = ApiClient.getRetrofitInterface()?.syncElectorsAndImages(
                            electorID,
                            oldElectorID,
                            locationId,
                            oldLocationId,
                            locationName,
                            wardId,
                            wardName,
                            llgId,
                            llgName,
                            districtId,
                            provinceId,
                            regionId,
                            mkaDistrictId,
                            mkaLlgId,
                            mkaWardId,
                            mkaLocationId,
                            mkaClanId,
                            lName,
                            fName,
                            voterName,
                            gender,
                            dob,
                            false,
                            currentVoter.isPWD,
                            religion,
                            fatherLName,
                            fatherFName,
                            motherLName,
                            motherFName,
                            provOfBirthFK,
                            provinceOfBirth,
                            provinceName,
                            openElectorate,
                            placeOfBirth,
                            occupationFK,
                            occupation,
                            clan,
                            subClan,
                            address,
                            natID,
                            natIDRecordNo,
                            status,
                            houseFK,
                            houseSortOrder,
                            restHouse,
                            sectionNo,
                            lotNo,
                            streetName,
                            suburbTownSettlement,
                            familyFK,
                            familyNumber,
                            familyName,
                            familySortOrder,
                            dpo,
                            currentVoter.lat,
                            currentVoter.longitude,
                            posted,
                            comment,
                            electorProfileImageUrl,
                            electorFormImageUrl,
                            profileImageBody,
                            formImageBody,
                            enrolmentFormNumber,
                            enrolmentFormType,
                            isManualForm11Number,
                            createdBy,
                            tempElectorId,
                            clanId
                        )

                        WSClient<Response<ArrayList<FailedVotersList>>>().request(
                            this,
                            ApiConstants.DPO_SYNC_VOTERS_LIST,
                            call,
                            object :
                                ISuccessHandler<Response<ArrayList<FailedVotersList>>> {
                                override fun successResponse(
                                    requestCode: Int,
                                    mResponse: Response<ArrayList<FailedVotersList>>
                                ) {
                                    isSyncDone = true
                                    UpdateVotersDataAfterSyncVoterWithDataAndImage(
                                        currentVoter,
                                        mResponse
                                    ).execute()
                                }

                            },
                            object : IFailureHandler {
                                override fun failureResponse(requestCode: Int, message: String) {

                                    currentUpdatedVotersList++
                                    if (selectedVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedVotersList[currentUpdatedVotersList]
                                            callSyncVoterWithDataAndImageAPI(currentRequest)
                                        } else {
                                            hideSyncProgress()
                                            alert(message) {
                                                okButton {
                                                    finish()
                                                }
                                            }.show()
                                        }
                                    } else {
                                        hideSyncProgress()
                                        alert(message) {
                                            okButton {
                                                finish()
                                            }
                                        }.show()
                                    }

                                }
                            })
                    }


                } else {

                    if (currentVoter.isRejected) {
                        val call =
                            ApiClient.getRetrofitInterface()
                                ?.syncRejectedElectorsAndImagesWithOutVoterImage(
                                    electorID,
                                    oldElectorID,
                                    locationId,
                                    oldLocationId,
                                    locationName,
                                    wardId,
                                    wardName,
                                    llgId,
                                    llgName,
                                    districtId,
                                    provinceId,
                                    regionId,
                                    mkaDistrictId,
                                    mkaLlgId,
                                    mkaWardId,
                                    mkaLocationId,
                                    mkaClanId,
                                    lName,
                                    fName,
                                    voterName,
                                    gender,
                                    dob,
                                    false,
                                    currentVoter.isPWD,
                                    currentVoter.isRejected,
                                    religion,
                                    fatherLName,
                                    fatherFName,
                                    motherLName,
                                    motherFName,
                                    provOfBirthFK,
                                    provinceOfBirth,
                                    provinceName,
                                    openElectorate,
                                    placeOfBirth,
                                    occupationFK,
                                    occupation,
                                    clan,
                                    subClan,
                                    address,
                                    natID,
                                    natIDRecordNo,
                                    status,
                                    houseFK,
                                    houseSortOrder,
                                    restHouse,
                                    sectionNo,
                                    lotNo,
                                    streetName,
                                    suburbTownSettlement,
                                    familyFK,
                                    familyNumber,
                                    familyName,
                                    familySortOrder,
                                    dpo,
                                    currentVoter.lat,
                                    currentVoter.longitude,
                                    posted,
                                    comment,
                                    electorProfileImageUrl,
                                    electorFormImageUrl,
                                    formImageBody,
                                    enrolmentFormNumber,
                                    enrolmentFormType,
                                    isManualForm11Number,
                                    createdBy,
                                    tempElectorId,
                                    clanId
                                )

                        WSClient<Response<ArrayList<FailedVotersList>>>().request(
                            this,
                            ApiConstants.DPO_SYNC_VOTERS_LIST,
                            call,
                            object :
                                ISuccessHandler<Response<ArrayList<FailedVotersList>>> {
                                override fun successResponse(
                                    requestCode: Int,
                                    mResponse: Response<ArrayList<FailedVotersList>>
                                ) {
                                    isSyncDone = true
                                    UpdateVotersDataAfterSyncVoterWithDataAndImage(
                                        currentVoter,
                                        mResponse
                                    ).execute()
                                }

                            },
                            object : IFailureHandler {
                                override fun failureResponse(requestCode: Int, message: String) {

                                    currentUpdatedVotersList++
                                    if (selectedVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedVotersList[currentUpdatedVotersList]
                                            callSyncVoterWithDataAndImageAPI(currentRequest)
                                        } else {
                                            hideSyncProgress()
                                            alert(message) {
                                                okButton {
                                                    finish()
                                                }
                                            }.show()
                                        }
                                    } else {
                                        hideSyncProgress()
                                        alert(message) {
                                            okButton {
                                                finish()
                                            }
                                        }.show()
                                    }

                                }
                            })
                    } else {
                        val call =
                            ApiClient.getRetrofitInterface()
                                ?.syncElectorsAndImagesWithOutVoterImage(
                                    electorID,
                                    oldElectorID,
                                    locationId,
                                    oldLocationId,
                                    locationName,
                                    wardId,
                                    wardName,
                                    llgId,
                                    llgName,
                                    districtId,
                                    provinceId,
                                    regionId,
                                    mkaDistrictId,
                                    mkaLlgId,
                                    mkaWardId,
                                    mkaLocationId,
                                    mkaClanId,
                                    lName,
                                    fName,
                                    voterName,
                                    gender,
                                    dob,
                                    false,
                                    currentVoter.isPWD,
                                    religion,
                                    fatherLName,
                                    fatherFName,
                                    motherLName,
                                    motherFName,
                                    provOfBirthFK,
                                    provinceOfBirth,
                                    provinceName,
                                    openElectorate,
                                    placeOfBirth,
                                    occupationFK,
                                    occupation,
                                    clan,
                                    subClan,
                                    address,
                                    natID,
                                    natIDRecordNo,
                                    status,
                                    houseFK,
                                    houseSortOrder,
                                    restHouse,
                                    sectionNo,
                                    lotNo,
                                    streetName,
                                    suburbTownSettlement,
                                    familyFK,
                                    familyNumber,
                                    familyName,
                                    familySortOrder,
                                    dpo,
                                    currentVoter.lat,
                                    currentVoter.longitude,
                                    posted,
                                    comment,
                                    electorProfileImageUrl,
                                    electorFormImageUrl,
                                    formImageBody,
                                    enrolmentFormNumber,
                                    enrolmentFormType,
                                    isManualForm11Number,
                                    createdBy,
                                    tempElectorId,
                                    clanId
                                )

                        WSClient<Response<ArrayList<FailedVotersList>>>().request(
                            this,
                            ApiConstants.DPO_SYNC_VOTERS_LIST,
                            call,
                            object :
                                ISuccessHandler<Response<ArrayList<FailedVotersList>>> {
                                override fun successResponse(
                                    requestCode: Int,
                                    mResponse: Response<ArrayList<FailedVotersList>>
                                ) {
                                    isSyncDone = true
                                    UpdateVotersDataAfterSyncVoterWithDataAndImage(
                                        currentVoter,
                                        mResponse
                                    ).execute()
                                }

                            },
                            object : IFailureHandler {
                                override fun failureResponse(requestCode: Int, message: String) {

                                    currentUpdatedVotersList++
                                    if (selectedVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedVotersList[currentUpdatedVotersList]
                                            callSyncVoterWithDataAndImageAPI(currentRequest)
                                        } else {
                                            hideSyncProgress()
                                            alert(message) {
                                                okButton {
                                                    finish()
                                                }
                                            }.show()
                                        }
                                    } else {
                                        hideSyncProgress()
                                        alert(message) {
                                            okButton {
                                                finish()
                                            }
                                        }.show()
                                    }

                                }
                            })
                    }


                }


            }

            else -> {

                if (currentVoter.electorProfileImage == null
                    || currentVoter.electorProfileImage.isEmpty()
                    || !currentVoter.electorProfileImage.contains("/")
                ) {
                    val call =
                        ApiClient.getRetrofitInterface()
                            ?.syncElectorsDataOnly(
                                electorID,
                                oldElectorID,
                                locationId,
                                oldLocationId,
                                locationName,
                                wardId,
                                wardName,
                                llgId,
                                llgName,
                                districtId,
                                provinceId,
                                regionId,
                                mkaDistrictId,
                                mkaLlgId,
                                mkaWardId,
                                mkaLocationId,
                                mkaClanId,
                                lName,
                                fName,
                                voterName,
                                gender,
                                dob,
                                false,
                                currentVoter.isPWD,
                                religion,
                                fatherLName,
                                fatherFName,
                                motherLName,
                                motherFName,
                                provOfBirthFK,
                                provinceOfBirth,
                                provinceName,
                                openElectorate,
                                placeOfBirth,
                                occupationFK,
                                occupation,
                                clan,
                                subClan,
                                address,
                                natID,
                                natIDRecordNo,
                                status,
                                houseFK,
                                houseSortOrder,
                                restHouse,
                                sectionNo,
                                lotNo,
                                streetName,
                                suburbTownSettlement,
                                familyFK,
                                familyNumber,
                                familyName,
                                familySortOrder,
                                dpo,
                                currentVoter.lat,
                                currentVoter.longitude,
                                posted,
                                comment,
                                electorProfileImageUrl,
                                electorFormImageUrl,
                                enrolmentFormNumber,
                                enrolmentFormType,
                                isManualForm11Number,
                                createdBy,
                                tempElectorId,
                                clanId
                            )

                    WSClient<Response<ArrayList<FailedVotersList>>>().request(
                        this,
                        ApiConstants.DPO_SYNC_VOTERS_LIST,
                        call,
                        object :
                            ISuccessHandler<Response<ArrayList<FailedVotersList>>> {
                            override fun successResponse(
                                requestCode: Int,
                                mResponse: Response<ArrayList<FailedVotersList>>
                            ) {
                                isSyncDone = true
                                UpdateVotersDataAfterSyncVoterWithDataAndImage(
                                    currentVoter,
                                    mResponse
                                ).execute()
                            }

                        },
                        object : IFailureHandler {
                            override fun failureResponse(requestCode: Int, message: String) {

                                currentUpdatedVotersList++
                                if (selectedVotersList.size > currentUpdatedVotersList) {
                                    if (CommonMethods.checkInternetConnection(application)) {
                                        val currentRequest: VotersList =
                                            selectedVotersList[currentUpdatedVotersList]
                                        callSyncVoterWithDataAndImageAPI(currentRequest)
                                    } else {
                                        hideSyncProgress()
                                        alert(message) {
                                            okButton {
                                                finish()
                                            }
                                        }.show()
                                    }
                                } else {
                                    hideSyncProgress()
                                    alert(message) {
                                        okButton {
                                            finish()
                                        }
                                    }.show()
                                }

                            }
                        })
                }
                else {
                    val electorProfileImageFile = File(currentVoter.electorProfileImage.toString())
                    val requestElectorProfileImageFile =
                        electorProfileImageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val profileImageBody = MultipartBody.Part.createFormData(
                        "electorProfileImageFile",
                        electorProfileImageFile.name, requestElectorProfileImageFile
                    )

                    val call = ApiClient.getRetrofitInterface()?.syncElectorsAndVoterImage(
                        electorID,
                        oldElectorID,
                        locationId,
                        oldLocationId,
                        locationName,
                        wardId,
                        wardName,
                        llgId,
                        llgName,
                        districtId,
                        provinceId,
                        regionId,
                        mkaDistrictId,
                        mkaLlgId,
                        mkaWardId,
                        mkaLocationId,
                        mkaClanId,
                        lName,
                        fName,
                        voterName,
                        gender,
                        dob,
                        false,
                        currentVoter.isPWD,
                        religion,
                        fatherLName,
                        fatherFName,
                        motherLName,
                        motherFName,
                        provOfBirthFK,
                        provinceOfBirth,
                        provinceName,
                        openElectorate,
                        placeOfBirth,
                        occupationFK,
                        occupation,
                        clan,
                        subClan,
                        address,
                        natID,
                        natIDRecordNo,
                        status,
                        houseFK,
                        houseSortOrder,
                        restHouse,
                        sectionNo,
                        lotNo,
                        streetName,
                        suburbTownSettlement,
                        familyFK,
                        familyNumber,
                        familyName,
                        familySortOrder,
                        dpo,
                        currentVoter.lat,
                        currentVoter.longitude,
                        posted,
                        comment,
                        electorProfileImageUrl,
                        electorFormImageUrl,
                        profileImageBody,
                        enrolmentFormNumber,
                        enrolmentFormType,
                        isManualForm11Number,
                        createdBy,
                        tempElectorId,
                        clanId
                    )

                    WSClient<Response<ArrayList<FailedVotersList>>>().request(
                        this,
                        ApiConstants.DPO_SYNC_VOTERS_LIST,
                        call,
                        object :
                            ISuccessHandler<Response<ArrayList<FailedVotersList>>> {
                            override fun successResponse(
                                requestCode: Int,
                                mResponse: Response<ArrayList<FailedVotersList>>
                            ) {
                                isSyncDone = true
                                UpdateVotersDataAfterSyncVoterWithDataAndImage(
                                    currentVoter,
                                    mResponse
                                ).execute()
                            }

                        },
                        object : IFailureHandler {
                            override fun failureResponse(requestCode: Int, message: String) {

                                currentUpdatedVotersList++
                                if (selectedVotersList.size > currentUpdatedVotersList) {
                                    if (CommonMethods.checkInternetConnection(application)) {
                                        val currentRequest: VotersList =
                                            selectedVotersList[currentUpdatedVotersList]
                                        callSyncVoterWithDataAndImageAPI(currentRequest)
                                    } else {
                                        hideSyncProgress()
                                        alert(message) {
                                            okButton {
                                                finish()
                                            }
                                        }.show()
                                    }
                                } else {
                                    hideSyncProgress()
                                    alert(message) {
                                        okButton {
                                            finish()
                                        }
                                    }.show()
                                }

                            }
                        })
                }
            }
        }


    }

    @SuppressLint("StaticFieldLeak")
    internal inner class UpdateVotersDataAfterSyncVoterWithDataAndImage(
        private val currentVoter: VotersList,
        private val mResponse: Response<ArrayList<FailedVotersList>>
    ) :
        AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg p0: Void?): Void? {

            if (mResponse.data != null) {
                for (responseDataCount in mResponse.data!!.indices) {
                    successOrErrorMessage = if (successOrErrorMessage.isEmpty()) {
                        if (mResponse.data!![responseDataCount].errorMessage.isNullOrEmpty()) {
                            ""
                        } else {
                            getFailedVoterName(mResponse.data!![responseDataCount]) + " - " + mResponse.data!![responseDataCount].errorMessage
                        }
                    } else {
                        if (mResponse.data!![responseDataCount].errorMessage.isNullOrEmpty()) {
                            successOrErrorMessage
                        } else {
                            successOrErrorMessage + "\n" + getFailedVoterName(mResponse.data!![responseDataCount]) + " - " + mResponse.data!![responseDataCount].errorMessage
                        }
                    }
                }
                currentVoter.errorMessage = getErrorMessage(currentVoter, mResponse.data)
                rejectedVotersList.add(currentVoter)
                when (currentVoter.status) {
                    "TRANSFERRED IN" -> {
                        dataBaseInstance.transferInVotersListDao()
                            .updateWithErrorMessage(
                                currentVoter.electorId,
                                mResponse.data!![0].errorMessage
                            )
                    }

                    "NEW" -> {
                        dataBaseInstance.votersListDao()
                            .updateWithErrorMessageNewVoter(
                                currentVoter.tempElectorId,
                                mResponse.data!![0].errorMessage
                            )
                    }

                    else -> {
                        dataBaseInstance.votersListDao()
                            .updateWithErrorMessage(
                                currentVoter.electorId,
                                mResponse.data!![0].errorMessage
                            )
                    }
                }
            } else {
                syncedVotersList.add(currentVoter)
                when (currentVoter.status) {
                    "TRANSFERRED IN" -> {
                        dataBaseInstance.transferInVotersListDao()
                            .updateVoterSyncStatus(currentVoter.electorId)
                        dataBaseInstance.transferInVotersListDao()
                            .updateVoterSyncStatusAfterAPI(currentVoter.electorId)
                        dataBaseInstance.transferInVotersListDao()
                            .updateVoterSyncStatusWithUpdatedDateTime(
                                currentVoter.electorId,
                                getSyncedDateTime()
                            )
                    }

                    "NEW" -> {
                        dataBaseInstance.votersListDao()
                            .updateNewVoterSyncStatus(currentVoter.tempElectorId)
                        dataBaseInstance.votersListDao()
                            .updateNewVoterSyncStatusAfterAPI(currentVoter.tempElectorId)
                        dataBaseInstance.votersListDao()
                            .updateNewVoterSyncStatusWithUpdatedDateTime(
                                currentVoter.tempElectorId,
                                getSyncedDateTime()
                            )
                    }

                    else -> {
                        dataBaseInstance.votersListDao()
                            .updateVoterSyncStatus(currentVoter.electorId)
                        dataBaseInstance.votersListDao()
                            .updateVoterSyncStatusAfterAPI(currentVoter.electorId)
                        dataBaseInstance.votersListDao()
                            .updateVoterSyncStatusWithUpdatedDateTime(
                                currentVoter.electorId,
                                getSyncedDateTime()
                            )
                    }
                }
            }


            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            currentUpdatedVotersList++
            if (selectedVotersList.size > currentUpdatedVotersList) {
                if (CommonMethods.checkInternetConnection(application)) {
                    val currentRequest: VotersList =
                        selectedVotersList[currentUpdatedVotersList]
                    callSyncVoterWithDataAndImageAPI(currentRequest)
                } else {
                    val syncFinishMessage = successOrErrorMessage.ifEmpty { mResponse.message!! }
                    showSyncSuccessMessage(syncFinishMessage)
                }
            } else {
                val syncFinishMessage = successOrErrorMessage.ifEmpty { mResponse.message!! }
                showSyncSuccessMessage(syncFinishMessage)
            }
        }

    }

    private fun getSyncedDateTime(): String? {
        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.US)
        return sdf.format(Date())
    }

    private fun showAlertMessage(message: String) {
        alert(message) {
            okButton {
            }
        }.show()
    }

    private var bindingLayoutLoaderDownloadProgressBar: LayoutLoaderDownloadProgressBarBinding? =
        null
    var mDownloadProgressDialog: AlertDialog? = null
    var percentage = 0
    private fun showDownloadProgressDialog() {

        val builder = AlertDialog.Builder(this)
        bindingLayoutLoaderDownloadProgressBar = DataBindingUtil.inflate(
            LayoutInflater.from(
                this
            ),
            R.layout.layout_loader_download_progress_bar, null, false
        ) as LayoutLoaderDownloadProgressBarBinding
        builder.setView(bindingLayoutLoaderDownloadProgressBar?.root)
        mDownloadProgressDialog = builder.create()
        mDownloadProgressDialog?.setCanceledOnTouchOutside(false)
        mDownloadProgressDialog?.setCancelable(false)
        mDownloadProgressDialog?.show()

        bindingLayoutLoaderDownloadProgressBar?.tvDownloadMessage!!.text =
            getString(R.string.please_wait_until_the_upload_is_complete)


        percentage = 0
        bindingLayoutLoaderDownloadProgressBar?.downloadProgressBar!!.progress =
            percentage
//        bindingLayoutLoaderDownloadProgressBar?.tvDownloadPercentage!!.text =
//            "Uploaded: $percentage%"

        bindingLayoutLoaderDownloadProgressBar?.tvDownloadPercentage!!.text =
            "Preparing to Sync"
    }

    private fun uploadSyncProgress(percentage: Int,numberOfDataSyncs: Int) {
        if (mDownloadProgressDialog != null && mDownloadProgressDialog!!.isShowing) {
            if (percentage != 100) {
                bindingLayoutLoaderDownloadProgressBar?.downloadProgressBar!!.progress =
                    percentage
//                bindingLayoutLoaderDownloadProgressBar?.tvDownloadPercentage!!.text =
//                    "Uploaded: $percentage%"

                if (selectedVotersList.size > 1) {
                    bindingLayoutLoaderDownloadProgressBar?.tvDownloadPercentage!!.text =
                        numberOfDataSyncs.toString() + " of " + selectedVotersList.size.toString() + " Records Sync."
                } else {
                    bindingLayoutLoaderDownloadProgressBar?.tvDownloadPercentage!!.text =
                        numberOfDataSyncs.toString() + " of " + selectedVotersList.size.toString() + " Records Sync."
                }
            }
        }
    }

    private fun hideSyncProgress() {
        if (mDownloadProgressDialog != null && mDownloadProgressDialog!!.isShowing) {
            mDownloadProgressDialog!!.dismiss()
        }
    }

    var syncMessage = ""
    private fun showSyncSuccessMessage(syncFinishMessage: String) {
        isSyncDone = true
        hideSyncProgress()
        successOrErrorMessage = ""
        syncMessage = syncFinishMessage
        generateSyncSummarySheetReport()
    }

    // Sync summary sheet report
    private fun generateSyncSummarySheetReport() {

        val permissionsList: MutableList<String> =
            ArrayList()
        permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        Dexter.withActivity(this)
            .withPermissions(permissionsList)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        checkManageAllPermission()
                    }
                    if (report.isAnyPermissionPermanentlyDenied) {
                        CommonMethods.showSettingsDialog(this@VotersSyncWithDataAndImageActivity)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener { }
            .onSameThread()
            .check()

    }

    private fun checkManageAllPermission() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {

            if (Environment.isExternalStorageManager()) {
                exportSyncSummarySheetReport()
            } else {
                val intent = Intent(
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                )
                storagePermissionResultLauncher.launch(intent)
            }

        } else {
            exportSyncSummarySheetReport()
        }
    }

    private fun exportSyncSummarySheetReport() {

        CommonMethods.showProgress(this)
        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                writeSyncSummarySheet()
            }
            showSyncSummarySheetSuccessMessage()
        }
    }

    private fun writeSyncSummarySheet() {

        val filePath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                .toString() + "/" + syncSummaryReportFilesPath + "/"

        val reportsDirectory = File(filePath)
        if (!reportsDirectory.exists())
            reportsDirectory.mkdirs()

        val regex = Regex("[^A-Za-z0-9]")
        val currentWardName = wardName?.let { regex.replace(it, "") }
        var currentLocationName = ""
        if (locationName.isNotEmpty())
            currentLocationName = locationName.let { regex.replace(it, "") }

        val fileName = if (currentLocationName.isEmpty())
            currentWardName + "_" + System.currentTimeMillis().toString() + ".pdf"
        else
            currentWardName + "_" + currentLocationName + "_" + System.currentTimeMillis()
                .toString() + ".pdf"

        val outputFile = File(filePath, fileName)
        outputFile.createNewFile()

        val pdfDocument = PdfDocument(PdfWriter(outputFile))
        pdfDocument.defaultPageSize = PageSize.A4
        val document = Document(pdfDocument)

        val wardSummary = Paragraph("Sync Summary Sheet Report")
        wardSummary.setMarginTop(10f)
        wardSummary.setFontSize(10.0f)
        wardSummary.setBold()
        wardSummary.setTextAlignment(TextAlignment.LEFT)
        document.add(wardSummary)

        if (securedSharedPreferenceWrapper.getString(
                ApiConstants.ROLE,
                ""
            ) == ApiConstants.ROLE_DPO
        ) {
            val table = Table(
                UnitValue.createPercentArray(
                    floatArrayOf(
                        15f,
                        85f
                    )
                )
            ).useAllAvailableWidth()
            table.addCell(getBoldTextCell("Province:"))
            table.addCell(getCell(provinceName))
            document.add(table)
        }

        val table1 = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    15f,
                    85f
                )
            )
        ).useAllAvailableWidth()
        if (securedSharedPreferenceWrapper.getString(
                ApiConstants.ROLE,
                ""
            ) == ApiConstants.ROLE_DPO
        ) {
            table1.addCell(getBoldTextCell("District:"))
            table1.addCell(getCell(districtName))
        } else {
            table1.addCell(getBoldTextCell("MKA District:"))
            table1.addCell(getCell(loggedInDPOUserData.mkaDistrictName))
        }
        document.add(table1)

        val table2 = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    15f,
                    85f
                )
            )
        ).useAllAvailableWidth()
        if (securedSharedPreferenceWrapper.getString(
                ApiConstants.ROLE,
                ""
            ) == ApiConstants.ROLE_DPO
        ) {
            table2.addCell(getBoldTextCell("LLG:"))
            table2.addCell(getCell(llgName))
        } else {
            table2.addCell(getBoldTextCell("MKA LLG:"))
            table2.addCell(getCell(loggedInDPOUserData.mkallgName))
        }
        document.add(table2)

        val table3 = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    15f,
                    85f
                )
            )
        ).useAllAvailableWidth()
        if (securedSharedPreferenceWrapper.getString(
                ApiConstants.ROLE,
                ""
            ) == ApiConstants.ROLE_DPO
        ) {
            table3.addCell(getBoldTextCell("Ward:"))
            table3.addCell(getCell(wardNoAndName))
        } else {
            table3.addCell(getBoldTextCell("MKA Ward:"))
            table3.addCell(getCell(syncWardNoAndName))
        }
        document.add(table3)

        if (locationName.isNotEmpty()) {
            val table4 = Table(
                UnitValue.createPercentArray(
                    floatArrayOf(
                        15f,
                        85f
                    )
                )
            ).useAllAvailableWidth()
            if (securedSharedPreferenceWrapper.getString(
                    ApiConstants.ROLE,
                    ""
                ) == ApiConstants.ROLE_DPO
            ) {
                table4.addCell(getBoldTextCell("Location:"))
                table4.addCell(getCell(locationName))
            } else {
                table4.addCell(getBoldTextCell("MKA Location:"))
                table4.addCell(getCell(loggedInDPOUserData.mkaLocationName))
            }
            document.add(table4)
        }

        val table5 = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    15f,
                    85f
                )
            )
        ).useAllAvailableWidth()
        table5.addCell(getBoldTextCell("Device:"))
        table5.addCell(
            getCell(
                Settings.Secure.getString(
                    contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            )
        )
        document.add(table5)

        val mSimpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val table6 = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    15f,
                    85f
                )
            )
        ).useAllAvailableWidth()
        table6.addCell(getBoldTextCell("Date:"))
        table6.addCell(getCell(mSimpleDateFormat.format(Date())))
        document.add(table6)

        if (syncedVotersList.size > 0) {
            val syncedVoterListTitle = Paragraph("Synced Voters List")
            syncedVoterListTitle.setMarginTop(10f)
            syncedVoterListTitle.setFontSize(10.0f)
            syncedVoterListTitle.setBold()
            syncedVoterListTitle.setTextAlignment(TextAlignment.LEFT)
            document.add(syncedVoterListTitle)

            val newVoterList: ArrayList<VotersList> =
                getVoterListStatusWise(syncedVotersList, getString(R.string.new_status))
            if (newVoterList.isNotEmpty()) {
                val newVotersListTitle = Paragraph(getString(R.string.new_status))
                newVotersListTitle.setMarginTop(5f)
                newVotersListTitle.setFontSize(10.0f)
                newVotersListTitle.setBold()
                newVotersListTitle.setTextAlignment(TextAlignment.LEFT)
                document.add(newVotersListTitle)

                setSyncedVotersListInfoTableData(document, newVoterList)

            }

            val transferredInVoterList: ArrayList<VotersList> =
                getVoterListStatusWise(syncedVotersList, getString(R.string.transferred_in))
            if (transferredInVoterList.isNotEmpty()) {
                val transferredInVotersListTitle = Paragraph(getString(R.string.transferred_in))
                transferredInVotersListTitle.setMarginTop(5f)
                transferredInVotersListTitle.setFontSize(10.0f)
                transferredInVotersListTitle.setBold()
                transferredInVotersListTitle.setTextAlignment(TextAlignment.LEFT)
                document.add(transferredInVotersListTitle)

                setSyncedVotersListInfoTableData(document, transferredInVoterList)

            }

            val updatedVoterList: ArrayList<VotersList> =
                getVoterListStatusWise(syncedVotersList, getString(R.string.updated))
            if (updatedVoterList.isNotEmpty()) {
                val updatedVotersListTitle = Paragraph(getString(R.string.updated))
                updatedVotersListTitle.setMarginTop(5f)
                updatedVotersListTitle.setFontSize(10.0f)
                updatedVotersListTitle.setBold()
                updatedVotersListTitle.setTextAlignment(TextAlignment.LEFT)
                document.add(updatedVotersListTitle)

                setSyncedVotersListInfoTableData(document, updatedVoterList)

            }

            val deceasedVoterList: ArrayList<VotersList> =
                getVoterListStatusWise(syncedVotersList, getString(R.string.deceased))
            if (deceasedVoterList.isNotEmpty()) {
                val deceasedVotersListTitle = Paragraph(getString(R.string.deceased))
                deceasedVotersListTitle.setMarginTop(5f)
                deceasedVotersListTitle.setFontSize(10.0f)
                deceasedVotersListTitle.setBold()
                deceasedVotersListTitle.setTextAlignment(TextAlignment.LEFT)
                document.add(deceasedVotersListTitle)

                setSyncedVotersListInfoTableData(document, deceasedVoterList)

            }

            val transferredOutVoterList: ArrayList<VotersList> =
                getVoterListStatusWise(syncedVotersList, getString(R.string.transferred_out))
            if (transferredOutVoterList.isNotEmpty()) {
                val transferredOutVotersListTitle = Paragraph(getString(R.string.transferred_out))
                transferredOutVotersListTitle.setMarginTop(5f)
                transferredOutVotersListTitle.setFontSize(10.0f)
                transferredOutVotersListTitle.setBold()
                transferredOutVotersListTitle.setTextAlignment(TextAlignment.LEFT)
                document.add(transferredOutVotersListTitle)

                setSyncedVotersListInfoTableData(document, transferredOutVoterList)

            }

            val ghostVoterList: ArrayList<VotersList> =
                getVoterListStatusWise(syncedVotersList, getString(R.string.ghost_report))
            if (ghostVoterList.isNotEmpty()) {
                val ghostVotersListTitle = Paragraph(getString(R.string.ghost_report))
                ghostVotersListTitle.setMarginTop(5f)
                ghostVotersListTitle.setFontSize(10.0f)
                ghostVotersListTitle.setBold()
                ghostVotersListTitle.setTextAlignment(TextAlignment.LEFT)
                document.add(ghostVotersListTitle)

                setSyncedVotersListInfoTableData(document, ghostVoterList)

            }

            val duplicateVoterList: ArrayList<VotersList> =
                getVoterListStatusWise(syncedVotersList, getString(R.string.duplicate))
            if (duplicateVoterList.isNotEmpty()) {
                val duplicateVotersListTitle = Paragraph(getString(R.string.duplicate))
                duplicateVotersListTitle.setMarginTop(5f)
                duplicateVotersListTitle.setFontSize(10.0f)
                duplicateVotersListTitle.setBold()
                duplicateVotersListTitle.setTextAlignment(TextAlignment.LEFT)
                document.add(duplicateVotersListTitle)

                setSyncedVotersListInfoTableData(document, duplicateVoterList)

            }

            val underAgeVoterList: ArrayList<VotersList> =
                getVoterListStatusWise(syncedVotersList, getString(R.string.under_age))
            if (underAgeVoterList.isNotEmpty()) {
                val underAgeVotersListTitle = Paragraph(getString(R.string.under_age))
                underAgeVotersListTitle.setMarginTop(5f)
                underAgeVotersListTitle.setFontSize(10.0f)
                underAgeVotersListTitle.setBold()
                underAgeVotersListTitle.setTextAlignment(TextAlignment.LEFT)
                document.add(underAgeVotersListTitle)

                setSyncedVotersListInfoTableData(document, underAgeVoterList)

            }

        }


        if (rejectedVotersList.size > 0) {

            val rejectedVotersListSummary = Paragraph("Rejected Voters List")
            rejectedVotersListSummary.setMarginTop(10f)
            rejectedVotersListSummary.setFontSize(10.0f)
            rejectedVotersListSummary.setBold()
            rejectedVotersListSummary.setTextAlignment(TextAlignment.LEFT)
            document.add(rejectedVotersListSummary)

            val newVoterList: ArrayList<VotersList> =
                getVoterListStatusWise(rejectedVotersList, getString(R.string.new_status))
            if (newVoterList.isNotEmpty()) {
                val newVotersListTitle = Paragraph(getString(R.string.new_status))
                newVotersListTitle.setMarginTop(5f)
                newVotersListTitle.setFontSize(10.0f)
                newVotersListTitle.setBold()
                newVotersListTitle.setTextAlignment(TextAlignment.LEFT)
                document.add(newVotersListTitle)

                setRejectedVotersListInfoTableData(document, newVoterList)

            }

            val transferredInVoterList: ArrayList<VotersList> =
                getVoterListStatusWise(rejectedVotersList, getString(R.string.transferred_in))
            if (transferredInVoterList.isNotEmpty()) {
                val transferredInVotersListTitle = Paragraph(getString(R.string.transferred_in))
                transferredInVotersListTitle.setMarginTop(5f)
                transferredInVotersListTitle.setFontSize(10.0f)
                transferredInVotersListTitle.setBold()
                transferredInVotersListTitle.setTextAlignment(TextAlignment.LEFT)
                document.add(transferredInVotersListTitle)

                setRejectedVotersListInfoTableData(document, transferredInVoterList)

            }

            val updatedVoterList: ArrayList<VotersList> =
                getVoterListStatusWise(rejectedVotersList, getString(R.string.updated))
            if (updatedVoterList.isNotEmpty()) {
                val updatedVotersListTitle = Paragraph(getString(R.string.updated))
                updatedVotersListTitle.setMarginTop(5f)
                updatedVotersListTitle.setFontSize(10.0f)
                updatedVotersListTitle.setBold()
                updatedVotersListTitle.setTextAlignment(TextAlignment.LEFT)
                document.add(updatedVotersListTitle)

                setRejectedVotersListInfoTableData(document, updatedVoterList)

            }

            val deceasedVoterList: ArrayList<VotersList> =
                getVoterListStatusWise(rejectedVotersList, getString(R.string.deceased))
            if (deceasedVoterList.isNotEmpty()) {
                val deceasedVotersListTitle = Paragraph(getString(R.string.deceased))
                deceasedVotersListTitle.setMarginTop(5f)
                deceasedVotersListTitle.setFontSize(10.0f)
                deceasedVotersListTitle.setBold()
                deceasedVotersListTitle.setTextAlignment(TextAlignment.LEFT)
                document.add(deceasedVotersListTitle)

                setRejectedVotersListInfoTableData(document, deceasedVoterList)

            }

            val transferredOutVoterList: ArrayList<VotersList> =
                getVoterListStatusWise(rejectedVotersList, getString(R.string.transferred_out))
            if (transferredOutVoterList.isNotEmpty()) {
                val transferredOutVotersListTitle = Paragraph(getString(R.string.transferred_out))
                transferredOutVotersListTitle.setMarginTop(5f)
                transferredOutVotersListTitle.setFontSize(10.0f)
                transferredOutVotersListTitle.setBold()
                transferredOutVotersListTitle.setTextAlignment(TextAlignment.LEFT)
                document.add(transferredOutVotersListTitle)

                setRejectedVotersListInfoTableData(document, transferredOutVoterList)

            }

            val ghostVoterList: ArrayList<VotersList> =
                getVoterListStatusWise(rejectedVotersList, getString(R.string.ghost_report))
            if (ghostVoterList.isNotEmpty()) {
                val ghostVotersListTitle = Paragraph(getString(R.string.ghost_report))
                ghostVotersListTitle.setMarginTop(5f)
                ghostVotersListTitle.setFontSize(10.0f)
                ghostVotersListTitle.setBold()
                ghostVotersListTitle.setTextAlignment(TextAlignment.LEFT)
                document.add(ghostVotersListTitle)

                setRejectedVotersListInfoTableData(document, ghostVoterList)

            }

            val duplicateVoterList: ArrayList<VotersList> =
                getVoterListStatusWise(rejectedVotersList, getString(R.string.duplicate))
            if (duplicateVoterList.isNotEmpty()) {
                val duplicateVotersListTitle = Paragraph(getString(R.string.duplicate))
                duplicateVotersListTitle.setMarginTop(5f)
                duplicateVotersListTitle.setFontSize(10.0f)
                duplicateVotersListTitle.setBold()
                duplicateVotersListTitle.setTextAlignment(TextAlignment.LEFT)
                document.add(duplicateVotersListTitle)

                setRejectedVotersListInfoTableData(document, duplicateVoterList)

            }

            val underAgeVoterList: ArrayList<VotersList> =
                getVoterListStatusWise(rejectedVotersList, getString(R.string.under_age))
            if (underAgeVoterList.isNotEmpty()) {
                val underAgeVotersListTitle = Paragraph(getString(R.string.under_age))
                underAgeVotersListTitle.setMarginTop(5f)
                underAgeVotersListTitle.setFontSize(10.0f)
                underAgeVotersListTitle.setBold()
                underAgeVotersListTitle.setTextAlignment(TextAlignment.LEFT)
                document.add(underAgeVotersListTitle)

                setRejectedVotersListInfoTableData(document, underAgeVoterList)

            }

        }

        setGrandTotalCount(document)

        document.close()
    }

    private fun getVoterListStatusWise(
        syncedVotersList: ArrayList<VotersList>,
        status: String
    ): ArrayList<VotersList> {
        val tempList: ArrayList<VotersList> = ArrayList()
        for (voter in syncedVotersList) {
            if (voter.status == status)
                tempList.add(voter)
        }
        return tempList
    }

    private fun setSyncedVotersListInfoTableData(
        document: Document,
        votersList: ArrayList<VotersList>
    ) {


        val votersInfoHeaderTable = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    5f,
                    10f,
                    10f,
                    10f,
                    35f,
                    15f,
                    15f
                )
            )
        ).useAllAvailableWidth()

        votersInfoHeaderTable.setMarginTop(5f)

        votersInfoHeaderTable.addCell(getVotersListHeaderCell("No", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("LName", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("FName", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Status", TextAlignment.CENTER))

        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Transmission", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Form11", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Photo", TextAlignment.CENTER))

        var count = 0
        for (voter in votersList) {
            count += 1
            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    count.toString(), TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.lName
                    ), TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.fName
                    ), TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.status
                    ), TextAlignment.CENTER
                )
            )
            if(voter?.electorId > 0)
            {
                votersInfoHeaderTable.addCell(
                    getVotersListCell(
                        "SUCCESS", TextAlignment.CENTER
                    )
                )
            }
            else
            {
                votersInfoHeaderTable.addCell(
                    getVotersListCell(
                        CommonMethods.getVoterDetails(
                            voter.errorMessage
                        ), TextAlignment.CENTER
                    )
                )
            }

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getForm11Number(
                        CommonMethods.getVoterDetails(voter.enrolmentFormNumber)
                    ), TextAlignment.CENTER
                )
            )
            var isImage = "YES"
            if( CommonMethods.getVoterDetails(voter.electorProfileImage).isBlank() )
            {
                isImage = "NO"
            }
            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    isImage , TextAlignment.CENTER
                )
            )
        }

        document.add(votersInfoHeaderTable)

        val countTable = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    70f,
                    30f
                )
            )
        ).useAllAvailableWidth()
        countTable.addCell(getVotersListHeaderCell("TOTAL", TextAlignment.LEFT))
        countTable.addCell(
            getVotersListHeaderCell(
                votersList.size.toString(),
                TextAlignment.CENTER
            )
        )

        document.add(countTable)
    }

    private fun setRejectedVotersListInfoTableData(
        document: Document,
        votersList: ArrayList<VotersList>
    ) {


        val votersInfoHeaderTable = if (securedSharedPreferenceWrapper.getString(
                ApiConstants.ROLE,
                ""
            ) == ApiConstants.ROLE_MKA
        ) Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    5f,
                    10f,
                    10f,
                    5f,
                    10f,
                    10f,
                    20f,
                    30f
                )
            )
        ).useAllAvailableWidth()
        else
            Table(
                UnitValue.createPercentArray(
                    floatArrayOf(
                        5f,
                        10f,
                        10f,
                        10f,
                        10f,
                        25f,
                        30f
                    )
                )
            ).useAllAvailableWidth()

        votersInfoHeaderTable.setMarginTop(5f)

        votersInfoHeaderTable.addCell(getVotersListHeaderCell("No", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("LName", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("FName", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Gender", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Location", TextAlignment.CENTER))
        if (securedSharedPreferenceWrapper.getString(
                ApiConstants.ROLE,
                ""
            ) == ApiConstants.ROLE_MKA
        ) {
            votersInfoHeaderTable.addCell(getVotersListHeaderCell("Clan", TextAlignment.CENTER))
        }
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Reason", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Status", TextAlignment.CENTER))

        var count = 0
        for (voter in votersList) {
            count += 1
            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    count.toString(), TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.lName
                    ), TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.fName
                    ), TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.gender
                    ), TextAlignment.CENTER
                )
            )

            if (securedSharedPreferenceWrapper.getString(
                    ApiConstants.ROLE,
                    ""
                ) == ApiConstants.ROLE_DPO
            ) {
                votersInfoHeaderTable.addCell(
                    getVotersListCell(
                        CommonMethods.getVoterDetails(
                            voter.locationName
                        ), TextAlignment.CENTER
                    )
                )
            } else {
                votersInfoHeaderTable.addCell(
                    getVotersListCell(
                        CommonMethods.getVoterDetails(
                            voter.mkaLocationName
                        ), TextAlignment.CENTER
                    )
                )
                votersInfoHeaderTable.addCell(
                    getVotersListCell(
                        CommonMethods.getVoterDetails(
                            voter.mkaClanName
                        ), TextAlignment.CENTER
                    )
                )
            }


            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.errorMessage
                    ), TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.status
                    ), TextAlignment.CENTER
                )
            )
        }

        document.add(votersInfoHeaderTable)

        val countTable = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    70f,
                    30f
                )
            )
        ).useAllAvailableWidth()
        countTable.addCell(getVotersListHeaderCell("TOTAL", TextAlignment.LEFT))
        countTable.addCell(
            getVotersListHeaderCell(
                votersList.size.toString(),
                TextAlignment.CENTER
            )
        )

        document.add(countTable)

    }

    private fun setGrandTotalCount(document: Document) {
        val grandTotalCount = syncedVotersList.size + rejectedVotersList.size
        val grandTotalCountTable = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    70f,
                    30f
                )
            )
        ).useAllAvailableWidth()
        grandTotalCountTable.setMarginTop(10f)
        grandTotalCountTable.addCell(getVotersListHeaderCell("GRAND TOTAL", TextAlignment.LEFT))
        grandTotalCountTable.addCell(
            getVotersListHeaderCell(
                grandTotalCount.toString(),
                TextAlignment.CENTER
            )
        )
        document.add(grandTotalCountTable)
    }

    private fun getBoldTextCell(text: String?): Cell? {
        val sizedText = Paragraph(text)
        sizedText.setBold()
        sizedText.setFontSize(8.0f)
        val cell = Cell().add(sizedText)
        cell.setPadding(3f)
        cell.setTextAlignment(TextAlignment.LEFT)
        cell.setBorder(Border.NO_BORDER)
        return cell
    }

    private fun getCell(text: String?): Cell? {
        val sizedText = Paragraph(text)
        sizedText.setFontSize(8.0f)
        val cell = Cell().add(sizedText)
        cell.setPadding(3f)
        cell.setTextAlignment(TextAlignment.LEFT)
        cell.setBorder(Border.NO_BORDER)
        return cell
    }

    private fun getVotersListHeaderCell(text: String?, alignment: TextAlignment?): Cell? {
        val sizedText = Paragraph(text)
        sizedText.setFontSize(7.0f)
        sizedText.setBold()
        val cell = Cell().add(sizedText)
        cell.setPadding(2f)
        cell.setTextAlignment(alignment)
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE)
        return cell
    }

    private fun getVotersListCell(text: String?, alignment: TextAlignment?): Cell? {
        val sizedText = Paragraph(text)
        sizedText.setFontSize(7.0f)
        val cell = Cell().add(sizedText)
        cell.setPadding(2f)
        cell.setTextAlignment(alignment)
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE)
        return cell
    }

    private fun showSyncSummarySheetSuccessMessage() {
        Handler(mainLooper).postDelayed({
            CommonMethods.hideProgress()
            alert("Sync summary sheet Report has been generated successfully and placed in the documents folder") {
                okButton {
                    showSyncCompletedMessage()
                }
            }.show().setCancelable(false)
        }, 1000)

    }

    private fun showSyncCompletedMessage() {
        alert(syncMessage) {
            okButton {
                val resultIntent = Intent()
                resultIntent.putExtra("NEED_REFRESH", true)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }.show().setCancelable(false)
    }

}
