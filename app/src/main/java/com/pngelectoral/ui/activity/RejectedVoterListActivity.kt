package com.pngelectoral.ui.activity

import ApiConstants
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.gson.JsonObject
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityRejectedVoterListBinding
import com.pngelectoral.databinding.ItemWardRollVoterBinding
import com.pngelectoral.ui.adapter.GenericAdapter
import com.pngelectoral.ui.model.DPOUserData
import com.pngelectoral.ui.model.VotersList
import com.pngelectoral.ui.viewModel.RejectedVotersListViewModel
import com.pngelectoral.utility.CommonMethods
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RejectedVoterListActivity : BaseActivity() {

    private var binding: ActivityRejectedVoterListBinding? = null
    private var rejectedVotersListViewModel: RejectedVotersListViewModel? = null
    private var rejectedVotersListAdapter: GenericAdapter<VotersList, ItemWardRollVoterBinding>? =
        null
    lateinit var loggedInDPOUserData: DPOUserData
    lateinit var dataBaseInstance: AppDatabase
    private var rejectedVotersList = mutableListOf<VotersList>()
    private var pageNumber = 1
    private var hasNext = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_rejected_voter_list
        )
        rejectedVotersListViewModel =
            ViewModelProvider(this).get(RejectedVotersListViewModel::class.java)
        binding?.rejectedVotersListViewModel = rejectedVotersListViewModel

        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )
        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase

        setFooterData()
        setOnClickListeners()
        addRvRejectedVotersListOnScrollListener()
        setRejectedVotersListAdapter()
        addObservers()
        callRejectedVotersListApi(true)
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

    private fun setOnClickListeners() {

        binding?.btnHome?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }

        binding?.btnBack?.setOnClickListener {
            finish()
        }

    }

    private fun addRvRejectedVotersListOnScrollListener() {

        val layoutManager = LinearLayoutManager(this)
        binding?.rvRejectedVotersList?.layoutManager = layoutManager
        binding?.rvRejectedVotersList?.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1) {
                    if (hasNext) {
                        hasNext = false
                        binding?.rvProgressLayout?.visibility = View.VISIBLE
                        pageNumber += 1
                        callRejectedVotersListApi(false)
                    }
                }
            }

        })

    }


    private fun addObservers() {

        rejectedVotersListViewModel?.rejectedVotersListResponse?.observe(this, Observer {
            if (it != null && it.status!!) {
                val transferInVotersListData = it.data
                val totalPages = transferInVotersListData?.total!! / 10
                hasNext = pageNumber < totalPages
                GetFilterListAsync(transferInVotersListData.votersList).execute()
            } else {
                CommonMethods.hideProgress()
                alert(it.message ?: "") {
                    okButton { }
                }.show()
            }
        })

    }

    @SuppressLint("StaticFieldLeak")
    internal inner class GetFilterListAsync(private val votersList: ArrayList<VotersList>? = ArrayList()) :
        AsyncTask<Void?, Void?, List<VotersList>?>() {

        override fun doInBackground(vararg p0: Void?): List<VotersList>? {

            val filteredRejectedVotersList: ArrayList<VotersList> = ArrayList()

            for (rejectedVoter in votersList!!) {

                val isRejectVoterUpdated = dataBaseInstance.votersListDao()
                    .checkRejectVoterUpdated(rejectedVoter.electorId)

                if (isRejectVoterUpdated != null) {
                    if (isRejectVoterUpdated.rejectRecordUpdated == 0)
                        filteredRejectedVotersList.add(rejectedVoter)
                } else {
                    filteredRejectedVotersList.add(rejectedVoter)
                }
            }

            return filteredRejectedVotersList

        }

        override fun onPostExecute(result: List<VotersList>?) {
            super.onPostExecute(result)

            CommonMethods.hideProgress()
            binding?.rvProgressLayout?.visibility = View.GONE

            if (pageNumber == 1) {
                rejectedVotersList = ArrayList(result!!)
                if (rejectedVotersList.size == 0) {
                    binding?.rvRejectedVotersList?.visibility = View.GONE
                    alert("Rejected Voters List Not Found!") {
                        okButton {
                            finish()
                        }
                    }.show()
                } else {
                    binding?.rvRejectedVotersList?.visibility = View.VISIBLE
                    rejectedVotersListAdapter?.setData(rejectedVotersList)
                }
            } else {
                binding?.rvRejectedVotersList?.visibility = View.VISIBLE
                rejectedVotersList.addAll(result!!)
                rejectedVotersListAdapter?.addItems(result)
            }


        }
    }


    private fun callRejectedVotersListApi(isShowLoading: Boolean) {

        if (isShowLoading)
            CommonMethods.showProgress(this)

        val rejectedVotersListObject = JsonObject()
        rejectedVotersListObject.addProperty(
            ApiConstants.UserId,
            loggedInDPOUserData.userId.toInt()
        )
        rejectedVotersListObject.addProperty(ApiConstants.PAGENO, pageNumber)
        rejectedVotersListObject.addProperty(ApiConstants.PAGESIZE, 10)

        rejectedVotersListViewModel?.callRejectedVotersListAPI(this, rejectedVotersListObject)
    }

    private fun setRejectedVotersListAdapter() {

        rejectedVotersListAdapter = object :
            GenericAdapter<VotersList, ItemWardRollVoterBinding>(rejectedVotersList) {
            override fun getLayoutId(): Int {
                return R.layout.item_ward_roll_voter
            }

            override fun onBindData(
                model: VotersList?,
                position: Int,
                dataBinding: ItemWardRollVoterBinding?
            ) {


                if (model?.electorProfileImageUrl != null
                    && model.electorProfileImageUrl.isNotEmpty()
                ) {

                    Glide.with(this@RejectedVoterListActivity)
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

                dataBinding?.tvVoterName?.text = getVoterName(model)
                dataBinding?.tvVoterId?.text = "ID - " + model?.electorId
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

                dataBinding?.cvParent?.setOnClickListener {
                    navigateToVoterDetailsScreen(model)
                }

            }
        }
        binding?.rvRejectedVotersList?.adapter = rejectedVotersListAdapter


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

    private fun navigateToVoterDetailsScreen(model: VotersList?) {
        val intent = Intent(this, RejectedVoterEditDetailsActivity::class.java)
        intent.putExtra(ApiConstants.VOTER_DETAILS, model)
        startActivityForResult(intent, 101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101
            && resultCode == Activity.RESULT_OK
        ) {

            pageNumber = 1
            callRejectedVotersListApi(true)

        }
    }


}
