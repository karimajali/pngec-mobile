package com.pngelectoral.ui.activity

import ApiConstants
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.*
import com.pngelectoral.ui.adapter.GenericAdapter
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.retrofit.ApiClient
import com.pngelectoral.ui.retrofit.IFailureHandler
import com.pngelectoral.ui.retrofit.ISuccessHandler
import com.pngelectoral.ui.retrofit.WSClient
import com.pngelectoral.ui.viewModel.SyncVotersListViewModel
import com.pngelectoral.utility.CommonMethods
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class SyncedVotersListingActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivitySyncedVotersListBinding? = null
    private var syncVotersListViewModel: SyncVotersListViewModel? = null
    private var syncVotersListAdapter: GenericAdapter<VotersList, ItemSyncVoterNewBinding>? = null
    lateinit var loggedInDPOUserData: DPOUserData
    lateinit var dataBaseInstance: AppDatabase
    private var selectedVotersList: ArrayList<VotersList> = ArrayList()
    private var syncedVotersList: ArrayList<VotersList> = ArrayList()
    private var statusList = mutableListOf<VoterListFilterModel>()
    private val statusFilterList = ArrayList<String>()

    var districtId = 0
    var llgId = 0
    var wardId = 0
    var locationId = 0

    var districtName = ""
    var llgName = ""
    var wardName = ""
    private var wardNoAndName = ""
    var wardNumber = 0
    var locationName = ""
    var currentPosition = 0

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_synced_voters_list
        )
        syncVotersListViewModel =
            ViewModelProvider(this).get(SyncVotersListViewModel::class.java)
        binding?.syncVotersListViewModel = syncVotersListViewModel

        job = Job()

        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )
        dataBaseInstance = DatabaseClient.getInstance(this).appDatabase
        statusList = CommonMethods.getStatusFilterList()
        districtId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_DISTRICT_ID, 0)
        llgId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LLG_ID, 0)
        wardId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_WARD_ID, 0)
        locationId =
            securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LOCATION_ID, 0)

        districtName = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_DISTRICT, "")
        llgName = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LLG, "")
        wardName = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_WARD, "")
        if (wardName.isNotEmpty()) {
            getWardNumberAndNameUsingCoroutines()
        }
        locationName =
            securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LOCATION, "")

        val layoutManager = LinearLayoutManager(this)
        binding?.rvSyncVotersList?.layoutManager = layoutManager

        registerReceiver(
            unSyncProgressReceiver,
            IntentFilter(ApiConstants.UN_SYNC_PROGRESS)
        )

        setOnClickListeners()
        CommonMethods.showProgress(this)
        getVotersToSyncBasedOnGeoSelection()
    }

    @SuppressLint("SetTextI18n")
    private fun getWardNumberAndNameUsingCoroutines() {
        launch(Dispatchers.Main) {
            val result: WardList? = withContext(Dispatchers.IO) {
                dataBaseInstance.wardListDao()?.getDPOWard(wardId)
            }
            wardNumber = result!!.wardNumber
            wardNoAndName = "$wardNumber & $wardName"
        }
    }


    private fun checkAndLoadData(result: List<VotersList>) {
        CommonMethods.hideProgress()
        syncedVotersList = ArrayList(result)
        if (syncedVotersList.isNotEmpty()) {
            binding?.rlMainContent?.visibility = View.VISIBLE
            binding?.rlNoContent?.visibility = View.GONE
            binding?.tvTitle?.text = syncedVotersList.size.toString() + " Synced Voters"
            loadData(syncedVotersList)
        } else {
            binding?.tvTitle?.text = "No Synced Voters"
            if (syncVotersListAdapter != null)
                syncVotersListAdapter!!.clearItems()
            binding?.rlMainContent?.visibility = View.GONE
            binding?.rlNoContent?.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
        unregisterReceiver(unSyncProgressReceiver)
    }

    private fun setOnClickListeners() {

        binding?.btnBack?.setOnClickListener {
            finish()
        }

        binding?.btnFilter?.setOnClickListener {
            showSelectGeoLocationToSync()
        }

        binding?.cbSelectAll?.setOnCheckedChangeListener { compoundButton, selected ->
            if (compoundButton.isPressed) {
                if (selected) {
                    selectedVotersList.addAll(syncedVotersList)
                } else {
                    selectedVotersList.clear()
                }
                syncVotersListAdapter?.notifyDataSetChanged()
            }

        }

        binding?.cardSync?.setOnClickListener {
            if (selectedVotersList.size > 0) {
//                startUnSync()
                startUnSyncProgressRecordWise()
            } else {
                alert("Select voters to un sync") {
                    okButton { }
                }.show()
            }
        }

    }

    private fun startUnSyncProgressRecordWise() {
        if (CommonMethods.checkInternetConnection(this)) {
            showUnSyncProgressDialog()
            unSyncEachRecord()
        } else {
            showAlertMessage("Internet connection is not available.Please check and try again")
        }
    }

    var currentSyncingVoter: VotersList = VotersList()
    private fun unSyncEachRecord() {
        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {

                val votersListSize = selectedVotersList.size
                val voterCount = currentPosition + 1
                val percentage1 = (voterCount.toDouble() / votersListSize.toDouble() * 100).toInt()
                val progressIntent = Intent(ApiConstants.UN_SYNC_PROGRESS)
                progressIntent.putExtra(
                    "UnSync_Progress",
                    percentage1
                )
                sendBroadcast(progressIntent)

                val syncedVotersListArray = JsonArray()
                currentSyncingVoter = selectedVotersList[currentPosition]
                val voterDataObject = JsonObject()

                val electorId = if (currentSyncingVoter.status == getString(R.string.new_status))
                    0
                else
                    currentSyncingVoter.electorId.toInt()

                voterDataObject.addProperty(
                    "ElectorId",
                    electorId
                )

                if (currentSyncingVoter.status == getString(R.string.new_status)
                    || currentSyncingVoter.status == getString(R.string.transferred_in)
                ) {
                    voterDataObject.addProperty(
                        "tempElectorId",
                        currentSyncingVoter.tempElectorId
                    )
                }

                voterDataObject.addProperty(
                    "LocationId",
                    currentSyncingVoter.locationId
                )
                voterDataObject.addProperty(
                    "WardId",
                    currentSyncingVoter.wardId
                )
                voterDataObject.addProperty(
                    "LLGId",
                    currentSyncingVoter.llgId
                )
                voterDataObject.addProperty(
                    "DistrictId",
                    currentSyncingVoter.districtId
                )
                voterDataObject.addProperty(
                    "ProvinceId",
                    currentSyncingVoter.provinceId
                )
                voterDataObject.addProperty(
                    "RegionId",
                    currentSyncingVoter.regionId
                )
                voterDataObject.addProperty(
                    "Status",
                    currentSyncingVoter.status
                )

                syncedVotersListArray.add(voterDataObject)

                val call =
                    ApiClient.getRetrofitInterface()
                        ?.callCheckSyncedElectorsAPI(syncedVotersListArray)

                WSClient<SyncedVoterStatusModel>().request(
                    this@SyncedVotersListingActivity,
                    ApiConstants.DPO_CHECK_SYNC_VOTERS_LIST,
                    call,
                    object :
                        ISuccessHandler<SyncedVoterStatusModel> {
                        override fun successResponse(
                            requestCode: Int,
                            mResponse: SyncedVoterStatusModel
                        ) {
                            checkAndDeleteUnSyncedDataEachRecord(mResponse.votersList)
                        }
                    },
                    object : IFailureHandler {
                        override fun failureResponse(requestCode: Int, message: String) {
                            currentPosition++
                            if (currentPosition == selectedVotersList.size) {
                                currentPosition = 0
                                unSyncProgressDialog!!.dismiss()
                            } else {
                                unSyncEachRecord()
                            }

                        }
                    })
            }
        }
    }

    private var unSyncProgressBinding: UnSyncProgressBinding? =
        null
    var unSyncProgressDialog: AlertDialog? = null
    private fun showUnSyncProgressDialog() {
        val builder = AlertDialog.Builder(this)
        unSyncProgressBinding = DataBindingUtil.inflate(
            LayoutInflater.from(
                this
            ),
            R.layout.un_sync_progress, null, false
        ) as UnSyncProgressBinding
        builder.setView(unSyncProgressBinding?.root)
        unSyncProgressDialog = builder.create()
        unSyncProgressDialog?.setCanceledOnTouchOutside(false)
        unSyncProgressDialog?.setCancelable(false)
        unSyncProgressDialog?.show()

    }

    private val unSyncProgressReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val downloadProgress = intent.getIntExtra("UnSync_Progress", 0)
            if (downloadProgress != 100) {
                unSyncProgressBinding?.downloadProgressBar!!.progress = downloadProgress
                unSyncProgressBinding?.tvDownloadPercentage!!.text =
                    "UnSync Progress: $downloadProgress%"
                Log.i("UnSync Progress: ", downloadProgress.toString())
            }
        }
    }

    private fun checkAndDeleteUnSyncedDataEachRecord(data: ArrayList<SyncedVoterStatusData>?) {


        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {

                val unSyncedVotersList: ArrayList<VotersList> = ArrayList()

                if (data != null && data.isNotEmpty()) {

                    if (currentSyncingVoter.status == getString(R.string.new_status)) {
                        for (voterData in data) {
                            if (voterData != null
                                && voterData.status != null
                                && voterData.status == getString(R.string.new_status)
                                && currentSyncingVoter.tempElectorId != null
                                && voterData.tempElectorId != null
                                && voterData.isSynced != null
                            ) {
                                if (currentSyncingVoter.tempElectorId == voterData.tempElectorId && voterData.isSynced == false) {
                                    unSyncedVotersList.add(currentSyncingVoter)
                                }
                            } else {
                                unSyncedVotersList.add(currentSyncingVoter)
                            }

                        }
                    } else {
                        for (voterData in data) {
                            if (voterData != null
                                && currentSyncingVoter.electorId != null
                                && voterData.electorId != null
                                && voterData.isSynced != null
                            ) {
                                if (currentSyncingVoter.electorId == voterData.electorId && voterData.isSynced == false) {
                                    unSyncedVotersList.add(currentSyncingVoter)
                                }
                            } else {
                                unSyncedVotersList.add(currentSyncingVoter)
                            }

                        }
                    }
                } else {
                    unSyncedVotersList.add(currentSyncingVoter)
                }




                for (i in unSyncedVotersList.indices) {
                    val voter = unSyncedVotersList[i]
                    if (voter.status == getString(R.string.old)) {
                        dataBaseInstance.votersListDao()
                            .updateOLDVoterSyncStatusToUnSync(voter.electorId)
                        dataBaseInstance.votersListDao()
                            .updateVoterSyncStatusAfterAPIToUnSync(voter.electorId)
                        dataBaseInstance.votersListDao()
                            .updateVoterSyncStatusWithUpdatedDateTimeUnSync(
                                voter.electorId,
                                ""
                            )
                    } else {
                        if (voter.status == getString(R.string.new_status)) {
                            dataBaseInstance.votersListDao()
                                .updateNewVoterSyncStatusToUnSync(voter.tempElectorId)
                            dataBaseInstance.votersListDao()
                                .updateNewVoterSyncStatusToUnSyncDataUpload(voter.tempElectorId)
                            dataBaseInstance.votersListDao()
                                .updateNewVoterSyncStatusToUnSyncImageUpload(voter.tempElectorId)
                            dataBaseInstance.votersListDao()
                                .updateNewVoterSyncStatusAfterAPIToUnSync(voter.tempElectorId)
                            dataBaseInstance.votersListDao()
                                .updateNewVoterSyncStatusWithUpdatedDateTimeUnSync(
                                    voter.tempElectorId,
                                    ""
                                )
                        } else {
                            dataBaseInstance.votersListDao()
                                .updateVoterSyncStatusToUnSync(voter.electorId)
                            dataBaseInstance.votersListDao()
                                .updateVoterSyncStatusAfterAPIToUnSync(voter.electorId)
                            dataBaseInstance.votersListDao()
                                .updateVoterSyncStatusWithUpdatedDateTimeUnSync(
                                    voter.electorId,
                                    ""
                                )
                            dataBaseInstance.votersListDao()
                                .updateVoterSyncStatusToUnSyncDataUpload(voter.electorId)
                            if (voter.status == getString(R.string.transferred_in)) {
                                dataBaseInstance.votersListDao()
                                    .updateVoterSyncStatusToUnSyncImageUpload(voter.electorId)
                            }
                        }

                    }
                }


            }

            currentPosition++
            if (currentPosition == selectedVotersList.size) {
                currentPosition = 0
                unSyncProgressDialog!!.dismiss()
                showAlertMessage("UnSync process completed successfully!")
                binding?.cbSelectAll?.isChecked = false
                selectedVotersList.clear()
                CommonMethods.showProgress(this@SyncedVotersListingActivity)
                getVotersToSyncBasedOnGeoSelection()
            } else {
                unSyncEachRecord()
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
                getLocationListFromDB()
            }

        }

        bindingSelectGeoLocation?.cardDone?.setOnClickListener {
            mSelectGeoLocationAlertDialog!!.dismiss()
            filterUnSyncedList()
        }


        bindingSelectGeoLocation?.ivClose?.setOnClickListener {
            mSelectGeoLocationAlertDialog!!.dismiss()
        }

        mSelectGeoLocationAlertDialog?.show()
    }

    private fun filterUnSyncedList() {

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
                        locationId = locationArray!![clickItem].locationId
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

    private fun getVotersToSyncBasedOnGeoSelection() {
        val statusList: ArrayList<String> = ArrayList()
        launch(Dispatchers.Main) {
            val result: List<VotersList> = withContext(Dispatchers.IO) {
                when {
                    locationId != 0 ->
                        if (statusFilterList.isEmpty()) {
                            dataBaseInstance.votersListDao()
                                .getDPOLOCATIONWiseSyncedVotersList(
                                    districtId.toString(),
                                    llgId.toString(),
                                    wardId.toString(),
                                    locationId.toString(),
                                    statusList
                                )
                        } else {
                            dataBaseInstance.votersListDao()
                                .getDPOLOCATIONWiseSyncedVotersListWithFilter(
                                    districtId.toString(),
                                    llgId.toString(),
                                    wardId.toString(),
                                    locationId.toString(),
                                    statusList,
                                    statusFilterList
                                )
                        }

                    wardId != 0 ->
                        if (statusFilterList.isEmpty()) {
                            dataBaseInstance.votersListDao()
                                .getDPOWARDWiseSyncedVotersList(
                                    districtId.toString(),
                                    llgId.toString(),
                                    wardId.toString(),
                                    statusList
                                )
                        } else {
                            dataBaseInstance.votersListDao()
                                .getDPOWARDWiseSyncedVotersListWithFilter(
                                    districtId.toString(),
                                    llgId.toString(),
                                    wardId.toString(),
                                    statusList,
                                    statusFilterList
                                )
                        }
                    llgId != 0 ->
                        if (statusFilterList.isEmpty()) {
                            dataBaseInstance.votersListDao()
                                .getDPOLLGWiseSyncedVotersList(
                                    districtId.toString(),
                                    llgId.toString(),
                                    statusList
                                )
                        } else {
                            dataBaseInstance.votersListDao()
                                .getDPOLLGWiseSyncedVotersListWithFilter(
                                    districtId.toString(),
                                    llgId.toString(),
                                    statusList,
                                    statusFilterList
                                )
                        }
                    else ->
                        if (statusFilterList.isEmpty()) {
                            dataBaseInstance.votersListDao()
                                .getDPODistrictWiseSyncedVotersList(
                                    districtId.toString(),
                                    statusList
                                )
                        } else {
                            dataBaseInstance.votersListDao()
                                .getDPODistrictWiseSyncedVotersListWithFilter(
                                    districtId.toString(),
                                    statusList,
                                    statusFilterList
                                )
                        }
                }
            }
            checkAndLoadData(result)
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
                        Glide.with(this@SyncedVotersListingActivity).load(Uri.fromFile(image))
                            .into(dataBinding?.ivVoterImage!!)
                    } else if (model.electorProfileImageUrl != null
                        && model.electorProfileImageUrl.isNotEmpty()
                    ) {

                        Glide.with(this@SyncedVotersListingActivity)
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
                        Glide.with(this@SyncedVotersListingActivity).load(Uri.fromFile(image))
                            .into(dataBinding?.ivVoterImage!!)
                    }

                } else {

                    if (model?.electorProfileImageUrl != null
                        && model.electorProfileImageUrl.isNotEmpty()
                    ) {

                        Glide.with(this@SyncedVotersListingActivity)
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
                dataBinding?.tvVoterId?.text = "ID - " + (if (model?.electorId != null && model.electorId > 0 && model.electorId < 1000000000000L) model.electorId else (if (model?.status == getString(R.string.new_status)) 0 else (model?.electorId ?: 0)))
                dataBinding?.tvUpdatedDate?.text = getPostedDate(model?.posted)
                dataBinding?.tvSyncStatus?.visibility = View.VISIBLE
                dataBinding?.tvSyncStatus?.text =
                    "Synced on: " + (if (!model?.syncDateTime.isNullOrEmpty()) model?.syncDateTime else getPostedDate(model?.posted))
                dataBinding?.tvVoterLocation?.text = model?.locationName
                dataBinding?.tvVoterStatus?.text = model?.status
                dataBinding?.tvDob?.text = "DOB: " + CommonMethods.getDisplayDate(
                    CommonMethods.getVoterDetails(
                        model?.dob
                    )!!
                )
                when {
                    CommonMethods.getVoterDetails(model?.gender)!! == "F" -> dataBinding?.tvGender?.text =
                        "Gender: Female"
                    CommonMethods.getVoterDetails(model?.gender)!! == "M" -> dataBinding?.tvGender?.text =
                        "Gender: Male"
                    else -> dataBinding?.tvGender?.text = "Gender:"
                }

                dataBinding?.tvLLG?.text = "LLG: " + CommonMethods.getVoterDetails(
                    model?.llgName
                )!!
                dataBinding?.tvWard?.text = "Ward: " + CommonMethods.getVoterDetails(
                    model?.wardName
                )!!

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


    private fun showAlertMessage(message: String) {
        alert(message) {
            okButton {
            }
        }.show()
    }


}