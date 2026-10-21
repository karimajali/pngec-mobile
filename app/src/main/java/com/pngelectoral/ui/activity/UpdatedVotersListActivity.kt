package com.pngelectoral.ui.activity

import ApiConstants
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityUpdatedVotersListBinding
import com.pngelectoral.databinding.DialogFilterScreenBinding
import com.pngelectoral.databinding.ItemFilterBinding
import com.pngelectoral.databinding.ItemSyncFailedVoterBinding
import com.pngelectoral.ui.adapter.GenericAdapter
import com.pngelectoral.ui.model.VoterListFilterModel
import com.pngelectoral.ui.model.VotersList
import com.pngelectoral.ui.viewModel.SyncVotersListViewModel
import com.pngelectoral.utility.CommonMethods
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.okButton
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class UpdatedVotersListActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityUpdatedVotersListBinding? = null
    private var syncVotersListViewModel: SyncVotersListViewModel? = null
    private var syncVotersListAdapter: GenericAdapter<VotersList, ItemSyncFailedVoterBinding>? =
        null
    lateinit var dataBaseInstance: AppDatabase
    private var syncVotersList: ArrayList<VotersList> = ArrayList()
    private val statusFilterList = ArrayList<String>()
    private var selectedVotersList: ArrayList<VotersList> = ArrayList()
    private var statusList = mutableListOf<VoterListFilterModel>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_updated_voters_list
        )
        syncVotersListViewModel =
            ViewModelProvider(this).get(SyncVotersListViewModel::class.java)
        binding?.syncVotersListViewModel = syncVotersListViewModel
        job = Job()
        dataBaseInstance = DatabaseClient.getInstance(this).appDatabase
        statusList = CommonMethods.getStatusFilterListForSync()
        val layoutManager = LinearLayoutManager(this)
        binding?.rvSyncVotersList?.layoutManager = layoutManager
        setOnClickListeners()
        CommonMethods.showProgress(this)
        getUpdatedVotersListUsingCoRoutines()
    }

    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
    }

    private fun getUpdatedVotersListUsingCoRoutines() {

        launch(Dispatchers.Main) {
            val result: List<VotersList> = withContext(Dispatchers.IO) {
                getUpdatedVotersList()
            }
            loadData(result)
        }

    }

    private fun getUpdatedVotersList(): List<VotersList> {
        val updatedVotersList = dataBaseInstance.votersListDao()
            .syncFailedVotersList
        val syncFailedVotersList: ArrayList<VotersList> = ArrayList()
        for (voter in updatedVotersList) {
            if (!voter.errorMessage.isNullOrEmpty())
                syncFailedVotersList.add(voter)
        }
        return syncFailedVotersList
    }

    private fun loadData(result: List<VotersList>?) {
        CommonMethods.hideProgress()
        syncVotersList.clear()
        syncVotersList = ArrayList(result!!)
        if (syncVotersList.isNotEmpty()) {
            binding?.rlMainContent!!.visibility = View.VISIBLE
            binding?.rlNoVoters!!.visibility = View.GONE
            binding?.tvTitle?.text = syncVotersList.size.toString() + " Voters Rejected"
            val wardRollFilteredVotersList = mutableListOf<VotersList>()
            wardRollFilteredVotersList.addAll(syncVotersList)
            setWardRollVotersListAdapter(wardRollFilteredVotersList)
        } else {
            binding?.tvTitle?.text = "No Voters Rejected"
            if (syncVotersListAdapter != null)
                syncVotersListAdapter!!.clearItems()
            binding?.rlMainContent!!.visibility = View.GONE
            binding?.rlNoVoters!!.visibility = View.VISIBLE
        }
    }


    private fun setOnClickListeners() {

        binding?.btnFilter?.setOnClickListener {
            showStatusFilterDialog()
        }

        binding?.btnBack?.setOnClickListener {
            finish()
        }

        binding?.cbSelectAll?.setOnCheckedChangeListener { compoundButton, selected ->
            if (selected) {
                selectedVotersList.addAll(syncVotersList)
            } else {
                selectedVotersList.clear()
            }
            syncVotersListAdapter?.notifyDataSetChanged()
        }

        binding?.cardDelete?.setOnClickListener {
            if (selectedVotersList.size > 0) {
                alert("Are you sure you want to delete the voters?") {
                    okButton {
                        deleteSelectedVoters()
                    }
                    cancelButton {

                    }
                }.show()
            }
        }


    }

    private var dialogFilterScreenBinding: DialogFilterScreenBinding? = null
    private var mStatusFilterAlertDialog: AlertDialog? = null
    private var statusAdapter: GenericAdapter<VoterListFilterModel, ItemFilterBinding>? = null
    internal var statusId = java.util.ArrayList<String>()

    private fun showStatusFilterDialog() {

        val builder = AlertDialog.Builder(this, R.style.full_screen_dialog)
        dialogFilterScreenBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_filter_screen, null, false
        ) as DialogFilterScreenBinding
        builder.setView(dialogFilterScreenBinding?.root)

        mStatusFilterAlertDialog = builder.create()
        mStatusFilterAlertDialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        dialogFilterScreenBinding?.llFilterLocation!!.visibility = View.GONE

        val statusListLayoutManager = LinearLayoutManager(this)
        statusAdapter = prepareStatusAdapter()
        dialogFilterScreenBinding?.rvStatusList!!.layoutManager = statusListLayoutManager
        dialogFilterScreenBinding?.rvStatusList!!.adapter = statusAdapter

        dialogFilterScreenBinding?.cardApply?.setOnClickListener {
            mStatusFilterAlertDialog!!.dismiss()
            filterList()
        }


        dialogFilterScreenBinding?.ivClose?.setOnClickListener {
            mStatusFilterAlertDialog!!.dismiss()
        }

        mStatusFilterAlertDialog?.show()

    }

    private fun filterList() {

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

        if (statusFilterList.isEmpty()) {
            getUpdatedVotersListUsingCoRoutines()
        } else {
            getUpdatedVotersListWithStatusFilterUsingCoRoutines()
        }
    }

    private fun getUpdatedVotersListWithStatusFilterUsingCoRoutines() {
        launch(Dispatchers.Main) {
            val result: List<VotersList> = withContext(Dispatchers.IO) {
                getUpdatedVotersListWithStatusFilter()
            }
            loadData(result)
        }

    }

    private fun getUpdatedVotersListWithStatusFilter(): List<VotersList> {
        val updatedVotersList = dataBaseInstance.votersListDao()
            .getSyncFailedVotersListWithStatusFilter(statusFilterList)
        val syncFailedVotersList: ArrayList<VotersList> = ArrayList()
        for (voter in updatedVotersList) {
            if (!voter.errorMessage.isNullOrEmpty())
                syncFailedVotersList.add(voter)
        }
        return syncFailedVotersList
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

    private fun deleteSelectedVoters() {

        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                for (voter in selectedVotersList) {
                    dataBaseInstance.votersListDao().delete(voter)
                    syncVotersList.remove(voter)
                }
            }

            selectedVotersList.clear()
            syncVotersListAdapter!!.clearItems()
            syncVotersListAdapter!!.notifyDataSetChanged()
            if (syncVotersList.isNotEmpty()) {
                binding?.rlMainContent!!.visibility = View.VISIBLE
                binding?.rlNoVoters!!.visibility = View.GONE
                val wardRollFilteredVotersList = mutableListOf<VotersList>()
                wardRollFilteredVotersList.addAll(syncVotersList)
                setWardRollVotersListAdapter(wardRollFilteredVotersList)
            } else {
                binding?.rlMainContent!!.visibility = View.GONE
                binding?.rlNoVoters!!.visibility = View.VISIBLE
            }

        }
    }


    private fun setWardRollVotersListAdapter(votersList: MutableList<VotersList>) {

        syncVotersListAdapter = object :
            GenericAdapter<VotersList, ItemSyncFailedVoterBinding>(votersList) {
            override fun getLayoutId(): Int {
                return R.layout.item_sync_failed_voter
            }

            @SuppressLint("SetTextI18n")
            override fun onBindData(
                model: VotersList?,
                position: Int,
                dataBinding: ItemSyncFailedVoterBinding?
            ) {

                if (model?.electorProfileImage != null
                    && model.electorProfileImage.isNotEmpty()
                ) {
                    val image =
                        File(model.electorProfileImage)

                    if (image.exists()) {
                        Glide.with(this@UpdatedVotersListActivity).load(Uri.fromFile(image))
                            .into(dataBinding?.ivVoterImage!!)
                    } else if (model.electorProfileImageUrl != null
                        && model.electorProfileImageUrl.isNotEmpty()
                    ) {

                        Glide.with(this@UpdatedVotersListActivity)
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
                        Glide.with(this@UpdatedVotersListActivity).load(Uri.fromFile(image))
                            .into(dataBinding?.ivVoterImage!!)
                    }

                } else {

                    if (model?.electorProfileImageUrl != null
                        && model.electorProfileImageUrl.isNotEmpty()
                    ) {

                        Glide.with(this@UpdatedVotersListActivity)
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

                dataBinding?.tvVoterName?.text = getVoterName(model)
                dataBinding?.tvVoterId?.text = "ID - " + (if (model?.electorId != null && model.electorId > 0 && model.electorId < 1000000000000L) model.electorId else (if (model?.status == getString(R.string.new_status)) 0 else (model?.electorId ?: 0)))
                dataBinding?.tvUpdatedDate?.text = getPostedDate(model?.posted)
//                dataBinding?.tvVoterLocation?.text = model?.locationName
                dataBinding?.tvVoterStatus?.text = model?.status
                if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO) {
                    dataBinding?.tvVoterLocation?.text = model?.locationName
                    dataBinding?.tvVoterClan?.visibility = View.GONE
                }else{
                    dataBinding?.tvVoterLocation?.text = model?.mkaLocationName
                    if(!model?.mkaClanName.isNullOrEmpty()){
                        dataBinding?.tvVoterClan?.visibility = View.VISIBLE
                        dataBinding?.tvVoterClan?.text = "Clan :"+model?.mkaClanName
                    }else{
                        dataBinding?.tvVoterClan?.visibility = View.GONE
                    }
                }
                dataBinding?.tvRejectedReason?.text =
                    "Rejected Reason: " + CommonMethods.getVoterDetails(model?.errorMessage)!!

                dataBinding?.cbSelectItem?.isChecked = selectedVotersList.contains(model)
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

                dataBinding?.cvParent?.setOnClickListener {
                    navigateToEditVoterDetails(model)
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

    private fun navigateToEditVoterDetails(model: VotersList?) {
        val intent = Intent(this, FailedVoterEditDetailsActivity::class.java)
        intent.putExtra(ApiConstants.VOTER_DETAILS, model)
        intent.putExtra(ApiConstants.VOTER_IMAGE, "")
        startActivityForResult(intent, ApiConstants.VOTER_STATUS_UPDATE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 11 && data != null) {
                if (data.getBooleanExtra("NEED_REFRESH", false)) {
                    CommonMethods.showProgress(this)
                    getUpdatedVotersListUsingCoRoutines()
                }
            } else if (requestCode == ApiConstants.VOTER_STATUS_UPDATE && data != null) {
                if (data.getBooleanExtra("NEED_REFRESH", false)) {
                    CommonMethods.showProgress(this)
                    getUpdatedVotersListUsingCoRoutines()
                }
            }
        }

    }


}
