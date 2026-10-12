package com.pngelectoral.ui.activity

import ApiConstants
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
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

class VotersSyncActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivitySyncVotersListBinding? = null
    private var syncVotersListViewModel: SyncVotersListViewModel? = null
    private var syncVotersListAdapter: GenericAdapter<VotersList, ItemSyncVoterNewBinding>? = null
    lateinit var loggedInDPOUserData: DPOUserData
    lateinit var dataBaseInstance: AppDatabase
    private var selectedVotersList: ArrayList<VotersList> = ArrayList()
    private var syncVotersList: ArrayList<VotersList> = ArrayList()
    private var selectedOnlyDataVotersList: ArrayList<VotersList> = ArrayList()
    private var selectedDataAndImageVotersList: ArrayList<VotersList> = ArrayList()

    private var currentUpdatedVotersList = -1
    private var isSyncDone = false
    private var successOrErrorMessage = ""

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

    private var statusList = mutableListOf<VoterListFilterModel>()
    private val statusFilterList = ArrayList<String>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

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
        dataBaseInstance = DatabaseClient.getInstance(this).appDatabase
        statusList = CommonMethods.getStatusFilterListForSync()
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

        setOnClickListeners()
        CommonMethods.showProgress(this)
        getVotersSyncList()
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

    private fun getVotersSyncList() {
        launch(Dispatchers.Main) {
            val result: List<VotersList> = withContext(Dispatchers.IO) {
                if (locationId == 0)
                    dataBaseInstance.votersListDao().getDPOWARDWiseUpdatedVotersListToSync(
                        districtId.toString(), llgId.toString(), wardId.toString()
                    )
                else
                    dataBaseInstance.votersListDao().getDPOLOCATIONWiseUpdatedVotersListToSync(
                        districtId.toString(),
                        llgId.toString(),
                        wardId.toString(),
                        locationId.toString()
                    )
            }
            checkAndLoadData(result)
        }
    }

    private fun checkAndLoadData(result: List<VotersList>) {
        CommonMethods.hideProgress()
//        syncVotersList = ArrayList(result)
        syncVotersList = getVotersToBeSync(result)
        if (syncVotersList.isNotEmpty()) {
            binding?.rlMainContent?.visibility = View.VISIBLE
            binding?.rlNoContent?.visibility = View.GONE
            binding?.tvTitle?.text = syncVotersList.size.toString() + " Voters To Sync"
            loadData(syncVotersList)
        } else {
            binding?.tvTitle?.text = "No Voters To Sync"
            if (syncVotersListAdapter != null)
                syncVotersListAdapter!!.clearItems()
            binding?.rlMainContent?.visibility = View.GONE
            binding?.rlNoContent?.visibility = View.VISIBLE
        }
    }

    private fun getVotersToBeSync(result: List<VotersList>): ArrayList<VotersList> {
        val tempSyncVotersList: ArrayList<VotersList> = ArrayList()
        for (voterToBeSynced in result) {
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
            if (selected) {
                selectedVotersList.addAll(syncVotersList)
            } else {
                selectedVotersList.clear()
            }
            syncVotersListAdapter?.notifyDataSetChanged()

        }

        binding?.cardSync?.setOnClickListener {
            if (selectedVotersList.size > 0) {
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
                getLocationListFromDB()
            }

        }

        bindingSelectGeoLocation?.cardDone?.setOnClickListener {
            mSelectGeoLocationAlertDialog!!.dismiss()
            filterSyncedList()

        }

//        bindingSelectGeoLocation?.cardClear?.setOnClickListener {
//            mSelectGeoLocationAlertDialog!!.dismiss()
//            CommonMethods.showProgress(this)
//            syncVotersForLLGName = ""
//            syncVotersForWardName = ""
//            syncVotersForLocationName = ""
//            syncVotersForLLGId = 0
//            syncVotersForWardId = 0
//            syncVotersForLocationId = 0
//            getVotersToSyncBasedOnGeoSelection()
//        }

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
                        if (statusFilterList.isEmpty()) {
                            dataBaseInstance.votersListDao()
                                .getDPOLOCATIONWiseUpdatedVotersListToSync(
                                    districtId.toString(),
                                    llgId.toString(),
                                    wardId.toString(),
                                    locationId.toString()
                                )
                        } else {
                            dataBaseInstance.votersListDao()
                                .getDPOLOCATIONWiseUpdatedVotersListToSyncWithStatusFilter(
                                    districtId.toString(),
                                    llgId.toString(),
                                    wardId.toString(),
                                    locationId.toString(),
                                    statusFilterList
                                )
                        }
                    wardId != 0 ->
                        if (statusFilterList.isEmpty()) {
                            dataBaseInstance.votersListDao()
                                .getDPOWARDWiseUpdatedVotersListToSync(
                                    districtId.toString(),
                                    llgId.toString(),
                                    wardId.toString(),
                                )
                        } else {
                            dataBaseInstance.votersListDao()
                                .getDPOWARDWiseUpdatedVotersListToSyncWithStatusFilter(
                                    districtId.toString(),
                                    llgId.toString(),
                                    wardId.toString(),
                                    statusFilterList
                                )
                        }
                    llgId != 0 ->
                        if (statusFilterList.isEmpty()) {
                            dataBaseInstance.votersListDao()
                                .getDPOLLGWiseUpdatedVotersListToSync(
                                    districtId.toString(),
                                    llgId.toString()
                                )
                        } else {
                            dataBaseInstance.votersListDao()
                                .getDPOLLGWiseUpdatedVotersListToSyncWithStatusFilter(
                                    districtId.toString(),
                                    llgId.toString(),
                                    statusFilterList
                                )
                        }
                    else ->
                        if (statusFilterList.isEmpty()) {
                            dataBaseInstance.votersListDao()
                                .getDPODistrictWiseUpdatedVotersListToSync(
                                    districtId.toString()
                                )
                        } else {
                            dataBaseInstance.votersListDao()
                                .getDPODistrictWiseUpdatedVotersListToSyncWithStatusFilter(
                                    districtId.toString(),
                                    statusFilterList
                                )
                        }
                }
            }
            checkAndLoadData(result)
        }

    }

    private fun startSync() {
        for (voter in selectedVotersList) {
            if (voter.isDataUploaded == 0 && voter.isImageUploaded == 0) {
                selectedDataAndImageVotersList.add(voter)
            } else {
                selectedOnlyDataVotersList.add(voter)
            }
        }

        if (selectedOnlyDataVotersList.isNotEmpty()) {
            if (CommonMethods.checkInternetConnection(this)) {
//                CommonMethods.showProgress(this@VotersSyncActivity)
                showDownloadProgressDialog()
                callSyncVotersWithOnlyDataAPI(selectedOnlyDataVotersList)
            } else {
                showAlertMessage("Internet connection is not available.Please check and try again")
            }
        } else {
            if (CommonMethods.checkInternetConnection(this)) {
//                CommonMethods.showProgress(this@VotersSyncActivity)
                showDownloadProgressDialog()
                currentUpdatedVotersList = 0
                val currentRequest: VotersList =
                    selectedDataAndImageVotersList[currentUpdatedVotersList]
                callSyncVoterWithDataAndImageAPI(currentRequest)
            } else {
                showAlertMessage("Internet connection is not available.Please check and try again")
            }
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
                        Glide.with(this@VotersSyncActivity).load(Uri.fromFile(image))
                            .into(dataBinding?.ivVoterImage!!)
                    } else if (model.electorProfileImageUrl != null
                        && model.electorProfileImageUrl.isNotEmpty()
                    ) {

                        Glide.with(this@VotersSyncActivity)
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
                        Glide.with(this@VotersSyncActivity).load(Uri.fromFile(image))
                            .into(dataBinding?.ivVoterImage!!)
                    }

                } else {

                    if (model?.electorProfileImageUrl != null
                        && model.electorProfileImageUrl.isNotEmpty()
                    ) {

                        Glide.with(this@VotersSyncActivity)
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

    private fun getFailedVoterName(model: FailedVotersList?): String {
//        deleteFailedVoterUsingCoRoutines(model?.tempElectorId!!)
        var lastNameOfVoter = ""
        var firstNameOfVoter = ""
        if (!model?.lName.isNullOrEmpty())
            lastNameOfVoter = model?.lName!!
        if (!model?.fName.isNullOrEmpty())
            firstNameOfVoter = model?.fName!!
        val voterName = "$lastNameOfVoter $firstNameOfVoter"
        return voterName.trim()
    }

//    private fun deleteFailedVoterUsingCoRoutines(tempElectorId: Int) {
//        launch(Dispatchers.Main) {
//            withContext(Dispatchers.IO) {
//                dataBaseInstance.votersListDao()
//                    .deleteNewAndTransferInVoter(tempElectorId)
//            }
//        }
//    }

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


    private fun callSyncVotersWithOnlyDataAPI(selectedVotersList: ArrayList<VotersList>) {

        val syncDataVotersListArray = JsonArray()

        for (i in selectedVotersList.indices) {

            val currentVoter = selectedVotersList[i]

            val voterDataObject = JsonObject()

            voterDataObject.addProperty(
                "tempElectorId",
                currentVoter.tempElectorId
            )

            voterDataObject.addProperty(
                ApiConstants.ELECTOR_ID,
                currentVoter.electorId
            )

            voterDataObject.addProperty(
                ApiConstants.OLD_ELECTOR_ID,
                currentVoter.oldElectorID
            )
            voterDataObject.addProperty(
                ApiConstants.LOCATION_ID,
                currentVoter.locationId
            )

            voterDataObject.addProperty(
                ApiConstants.OLD_LOCATION_ID,
                currentVoter.oldLocationId
            )

            voterDataObject.addProperty(
                ApiConstants.LOCATION_NAME,
                currentVoter.locationName
            )

            voterDataObject.addProperty(
                ApiConstants.WARDID,
                currentVoter.wardId
            )

            voterDataObject.addProperty(
                ApiConstants.WARD_NAME,
                currentVoter.wardName
            )

            voterDataObject.addProperty(
                ApiConstants.LLG_ID,
                currentVoter.llgId
            )

            voterDataObject.addProperty(
                ApiConstants.LLG_NAME,
                currentVoter.llgName
            )

            voterDataObject.addProperty(
                ApiConstants.DISTRICT_ID,
                currentVoter.districtId
            )

            voterDataObject.addProperty(
                ApiConstants.PROVINCE_ID,
                currentVoter.provinceId
            )

            voterDataObject.addProperty(
                ApiConstants.REGION_ID,
                currentVoter.regionId
            )

            voterDataObject.addProperty(
                ApiConstants.LNAME,
                currentVoter.lName
            )

            voterDataObject.addProperty(
                ApiConstants.FNAME,
                currentVoter.fName
            )

            voterDataObject.addProperty(
                ApiConstants.VOTER_NAME,
                currentVoter.voterName
            )

            voterDataObject.addProperty(
                ApiConstants.GENDER,
                currentVoter.gender
            )


            voterDataObject.addProperty(
                ApiConstants.DOB,
                currentVoter.dob
            )

            voterDataObject.addProperty(
                ApiConstants.IS_YOB,
                false
            )

            voterDataObject.addProperty(
                ApiConstants.IS_PWD,
                currentVoter.isPWD
            )

            voterDataObject.addProperty(
                "religion",
                currentVoter.religion
            )

            voterDataObject.addProperty(
                "fatherLName",
                currentVoter.fatherLName
            )

            voterDataObject.addProperty(
                "fatherFName",
                currentVoter.fatherFName
            )

            voterDataObject.addProperty(
                "motherLName",
                currentVoter.motherLName
            )

            voterDataObject.addProperty(
                "motherFName",
                currentVoter.motherFName
            )

            voterDataObject.addProperty(
                "provOfBirthFK",
                currentVoter.provOfBirthFK
            )

            voterDataObject.addProperty(
                "provinceOfBirth",
                currentVoter.provinceOfBirth
            )

            voterDataObject.addProperty(
                "provinceName",
                currentVoter.provinceName
            )

            voterDataObject.addProperty(
                "openElectorate",
                currentVoter.districtName
            )

            voterDataObject.addProperty(
                "placeOfBirth",
                currentVoter.placeOfBirth
            )

            voterDataObject.addProperty(
                "occupationFK",
                currentVoter.occupationFK
            )

            voterDataObject.addProperty(
                "occupation",
                currentVoter.occupation
            )
            voterDataObject.addProperty(
                "clanId",
                currentVoter.clanId
            )

            voterDataObject.addProperty(
                "clan",
                currentVoter.clan
            )

            voterDataObject.addProperty(
                "subClan",
                currentVoter.subClan
            )

            voterDataObject.addProperty(
                "address",
                currentVoter.address
            )

            voterDataObject.addProperty(
                "natID",
                currentVoter.natID
            )

            voterDataObject.addProperty(
                "natIDRecordNo",
                currentVoter.natIDRecordNo
            )

            voterDataObject.addProperty(
                "status",
                currentVoter.status
            )

            voterDataObject.addProperty(
                "houseFK",
                currentVoter.houseFK
            )

            voterDataObject.addProperty(
                "houseSortOrder",
                currentVoter.houseSortOrder
            )

            voterDataObject.addProperty(
                "restHouse",
                currentVoter.restHouse
            )

            voterDataObject.addProperty(
                "sectionNo",
                currentVoter.sectionNo
            )

            voterDataObject.addProperty(
                "lotNo",
                currentVoter.lotNo
            )

            voterDataObject.addProperty(
                "streetName",
                currentVoter.streetName
            )

            voterDataObject.addProperty(
                "suburbTownSettlement",
                currentVoter.suburbTownSettlement
            )

            voterDataObject.addProperty(
                "familyFK",
                currentVoter.familyFK
            )

            voterDataObject.addProperty(
                "familyNumber",
                currentVoter.familyNumber
            )

            voterDataObject.addProperty(
                "familyName",
                currentVoter.familyName
            )

            voterDataObject.addProperty(
                "familySortOrder",
                currentVoter.familySortOrder
            )

            voterDataObject.addProperty(
                "dpo",
                currentVoter.dpo
            )

            voterDataObject.addProperty(
                "lat",
                currentVoter.lat
            )

            voterDataObject.addProperty(
                "long",
                currentVoter.longitude
            )

            voterDataObject.addProperty(
                "posted",
                currentVoter.posted
            )

            voterDataObject.addProperty(
                "comment",
                currentVoter.comment
            )


            voterDataObject.addProperty(
                "electorProfileImageUrl",
                currentVoter.electorProfileImageUrl
            )


            voterDataObject.addProperty(
                "electorFormImageUrl",
                currentVoter.electorFormImageUrl
            )



            voterDataObject.addProperty(
                "createdBy",
                currentVoter.createdBy
            )

            if (currentVoter.status == "DUPLICATE")
                voterDataObject.addProperty(
                    "duplicateId",
                    currentVoter.duplicateId
                )

            if (currentVoter.status == getString(R.string.transferred_in)) {

                voterDataObject.addProperty(
                    "transferOutRegionId",
                    currentVoter.transferOutRegionId
                )

                voterDataObject.addProperty(
                    "transferOutProvinceId",
                    currentVoter.transferOutProvinceId
                )

                voterDataObject.addProperty(
                    "transferOutDistrictId",
                    currentVoter.transferOutDistrictId
                )

                voterDataObject.addProperty(
                    "transferOutLLGId",
                    currentVoter.transferOutLLGId
                )

                voterDataObject.addProperty(
                    "transferOutWardId",
                    currentVoter.transferOutWardId
                )

                voterDataObject.addProperty(
                    "transferOutLocationId",
                    currentVoter.transferOutLocationId
                )
            }

            if (currentVoter.status == getString(R.string.transferred_out)) {

                voterDataObject.addProperty(
                    "transferInRegionId",
                    currentVoter.transferInRegionId
                )

                voterDataObject.addProperty(
                    "transferInProvinceId",
                    currentVoter.transferInProvinceId
                )

                voterDataObject.addProperty(
                    "transferInDistrictId",
                    currentVoter.transferInDistrictId
                )

                voterDataObject.addProperty(
                    "transferInLLGId",
                    currentVoter.transferInLLGId
                )

                voterDataObject.addProperty(
                    "transferInWardId",
                    currentVoter.transferInWardId
                )

                voterDataObject.addProperty(
                    "transferInLocationId",
                    currentVoter.transferInLocationId
                )
            }

            if (currentVoter.status == getString(R.string.transferred_in) ||
                currentVoter.status == getString(R.string.new_status)
            ) {

                voterDataObject.addProperty(
                    "enrolmentFormNumber",
                    currentVoter.enrolmentFormNumber
                )

                val isManualForm11Number =
                    CommonMethods.isManualForm11Number(currentVoter.enrolmentFormNumber)

                voterDataObject.addProperty(
                    "IsManualFormNumber",
                    isManualForm11Number
                )

                if (currentVoter.isRejected)

                    voterDataObject.addProperty(
                        "isRejected",
                        currentVoter.isRejected
                    )
            }

            syncDataVotersListArray.add(voterDataObject)
        }

        val call =
            ApiClient.getRetrofitInterface()?.callSyncElectorsAPI(syncDataVotersListArray)

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
                    successOrErrorMessage = ""
                    UpdateVotersDataAfterSyncVotersWithOnlyData(
                        mResponse,
                        successOrErrorMessage
                    ).execute()
                }
            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    hideSyncProgress()
                    alert(message) {
                        okButton {
                            finish()
                        }
                    }.show()
                }
            })


    }

    @SuppressLint("StaticFieldLeak")
    internal inner class UpdateVotersDataAfterSyncVotersWithOnlyData(
        private val response: Response<ArrayList<FailedVotersList>>,
        private var successOrErrorMessage: String
    ) :
        AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            if (response.data != null) {
                for (responseDataCount in response.data!!.indices) {
                    successOrErrorMessage = if (successOrErrorMessage.isEmpty()) {
                        if (response.data!![responseDataCount].errorMessage.isNullOrEmpty()) {
//                            deleteFailedVoterUsingCoRoutines(response.data!![responseDataCount].tempElectorId!!)
                            ""
                        } else {
                            getFailedVoterName(response.data!![responseDataCount]) + " - " + response.data!![responseDataCount].errorMessage
                        }
                    } else {
                        if (response.data!![responseDataCount].errorMessage.isNullOrEmpty()) {
//                            deleteFailedVoterUsingCoRoutines(response.data!![responseDataCount].tempElectorId!!)
                            successOrErrorMessage
                        } else {
                            successOrErrorMessage + "\n" + getFailedVoterName(response.data!![responseDataCount]) + " - " + response.data!![responseDataCount].errorMessage
                        }
                    }
                }

                for (i in selectedOnlyDataVotersList.indices) {
                    val currentVoter = selectedOnlyDataVotersList[i]
                    if (checkCurrentVoterSyncStatus(currentVoter, response.data)) {
                        dataBaseInstance.votersListDao()
                            .updateVoterSyncStatus(currentVoter.electorId)
                        dataBaseInstance.votersListDao()
                            .updateVoterSyncStatusAfterAPI(currentVoter.electorId)
                        dataBaseInstance.votersListDao()
                            .updateVoterSyncStatusWithUpdatedDateTime(
                                currentVoter.electorId,
                                getSyncedDateTime()
                            )
                    } else {
                        dataBaseInstance.votersListDao().updateWithErrorMessage(
                            currentVoter.electorId,
                            getErrorMessage(currentVoter, response.data)
                        )
                    }
                }
            } else {
                for (i in selectedOnlyDataVotersList.indices) {
                    val currentVoter = selectedOnlyDataVotersList[i]
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
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            if (selectedDataAndImageVotersList.isEmpty()) {
                isSyncDone = true
                hideSyncProgress()
                if (successOrErrorMessage.isEmpty()) {
                    alert(response.message!!) {
                        okButton {
                            val resultIntent = Intent()
                            resultIntent.putExtra("NEED_REFRESH", true)
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
                        }
                    }.show()
                } else {
                    alert(successOrErrorMessage) {
                        okButton {
                            val resultIntent = Intent()
                            resultIntent.putExtra("NEED_REFRESH", true)
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
                        }
                    }.show()
                    successOrErrorMessage = ""
                }

            } else {
                if (CommonMethods.checkInternetConnection(this@VotersSyncActivity)) {
                    currentUpdatedVotersList = 0
                    val currentRequest: VotersList =
                        selectedDataAndImageVotersList[currentUpdatedVotersList]
                    callSyncVoterWithDataAndImageAPI(currentRequest)
                } else {
                    isSyncDone = true
                    hideSyncProgress()
                    showAlertMessage("Internet connection is not available.Please check and try again")
                }
            }

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
        val totalRecords = selectedDataAndImageVotersList.size
        percentage = (currentCount.toDouble() / totalRecords.toDouble() * 100).toInt()
        if (percentage == 100 && currentCount == 1)
            uploadSyncProgress(50)
        else
            uploadSyncProgress(percentage)

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
            currentVoter.religion
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
            currentVoter.placeOfBirth
        )

        val occupationFK = MultipartBody.Part.createFormData(
            "occupationFK",
            currentVoter.occupationFK.toString()
        )

        val occupation = MultipartBody.Part.createFormData(
            "occupation",
            currentVoter.occupation
        )

        val clanId = MultipartBody.Part.createFormData(
            "clanId",
            currentVoter.clanId.toString()
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

        val comment = MultipartBody.Part.createFormData(
            "comment",
            currentVoter.comment
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
        val enrolmentFormType = MultipartBody.Part.createFormData(
            "EnrolmentFormType",
            CommonMethods.getForm11Number(currentVoter.enrollFormType)
        )

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
                                    if (selectedDataAndImageVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedDataAndImageVotersList[currentUpdatedVotersList]
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
                                    if (selectedDataAndImageVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedDataAndImageVotersList[currentUpdatedVotersList]
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
                                    if (selectedDataAndImageVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedDataAndImageVotersList[currentUpdatedVotersList]
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
                                    if (selectedDataAndImageVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedDataAndImageVotersList[currentUpdatedVotersList]
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
                                    if (selectedDataAndImageVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedDataAndImageVotersList[currentUpdatedVotersList]
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
                                    if (selectedDataAndImageVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedDataAndImageVotersList[currentUpdatedVotersList]
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
                                    if (selectedDataAndImageVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedDataAndImageVotersList[currentUpdatedVotersList]
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
                                    if (selectedDataAndImageVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedDataAndImageVotersList[currentUpdatedVotersList]
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
                                    if (selectedDataAndImageVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedDataAndImageVotersList[currentUpdatedVotersList]
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
                                    if (selectedDataAndImageVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedDataAndImageVotersList[currentUpdatedVotersList]
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
                                    if (selectedDataAndImageVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedDataAndImageVotersList[currentUpdatedVotersList]
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
                                    if (selectedDataAndImageVotersList.size > currentUpdatedVotersList) {
                                        if (CommonMethods.checkInternetConnection(application)) {
                                            val currentRequest: VotersList =
                                                selectedDataAndImageVotersList[currentUpdatedVotersList]
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
                            if (selectedDataAndImageVotersList.size > currentUpdatedVotersList) {
                                if (CommonMethods.checkInternetConnection(application)) {
                                    val currentRequest: VotersList =
                                        selectedDataAndImageVotersList[currentUpdatedVotersList]
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
//                            deleteFailedVoterUsingCoRoutines(mResponse.data!![responseDataCount].tempElectorId!!)
                            ""
                        } else {
                            getFailedVoterName(mResponse.data!![responseDataCount]) + " - " + mResponse.data!![responseDataCount].errorMessage
                        }
                    } else {
                        if (mResponse.data!![responseDataCount].errorMessage.isNullOrEmpty()) {
//                            deleteFailedVoterUsingCoRoutines(mResponse.data!![responseDataCount].tempElectorId!!)
                            successOrErrorMessage
                        } else {
                            successOrErrorMessage + "\n" + getFailedVoterName(mResponse.data!![responseDataCount]) + " - " + mResponse.data!![responseDataCount].errorMessage
                        }
                    }
                }

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
            if (selectedDataAndImageVotersList.size > currentUpdatedVotersList) {
                if (CommonMethods.checkInternetConnection(application)) {
                    val currentRequest: VotersList =
                        selectedDataAndImageVotersList[currentUpdatedVotersList]
                    callSyncVoterWithDataAndImageAPI(currentRequest)
                } else {
                    hideSyncProgress()
                    alert(mResponse.message!!) {
                        okButton {
                            finish()
                        }
                    }.show()
                }
            } else {
                hideSyncProgress()
                if (successOrErrorMessage.isEmpty()) {
                    alert(mResponse.message!!) {
                        okButton {
                            val resultIntent = Intent()
                            resultIntent.putExtra("NEED_REFRESH", true)
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
                        }
                    }.show()
                } else {
                    alert(successOrErrorMessage) {
                        okButton {
                            val resultIntent = Intent()
                            resultIntent.putExtra("NEED_REFRESH", true)
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
                        }
                    }.show()
                    successOrErrorMessage = ""
                }
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

        if (selectedDataAndImageVotersList.size == 0) {
            percentage = 25
            bindingLayoutLoaderDownloadProgressBar?.downloadProgressBar!!.progress =
                percentage
            bindingLayoutLoaderDownloadProgressBar?.tvDownloadPercentage!!.text =
                "Uploaded: $percentage%"
        } else {
            percentage = 1
            bindingLayoutLoaderDownloadProgressBar?.downloadProgressBar!!.progress =
                percentage
            bindingLayoutLoaderDownloadProgressBar?.tvDownloadPercentage!!.text =
                "Uploaded: $percentage%"
        }


    }

    private fun uploadSyncProgress(percentage: Int) {
        if (mDownloadProgressDialog != null && mDownloadProgressDialog!!.isShowing) {
            if (percentage != 100) {
                bindingLayoutLoaderDownloadProgressBar?.downloadProgressBar!!.progress =
                    percentage
                bindingLayoutLoaderDownloadProgressBar?.tvDownloadPercentage!!.text =
                    "Uploaded: $percentage%"
            }
        }
    }

    private fun hideSyncProgress() {
        if (mDownloadProgressDialog != null && mDownloadProgressDialog!!.isShowing) {
            mDownloadProgressDialog!!.dismiss()
        }
    }

}