package com.pngelectoral.ui.activity

import ApiConstants
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.gson.JsonObject
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityRollLookUpVotersListBinding
import com.pngelectoral.databinding.ItemTransferInVoterBinding
import com.pngelectoral.ui.adapter.GenericAdapter
import com.pngelectoral.ui.model.VotersList
import com.pngelectoral.ui.viewModel.TransferInVotersListModel
import com.pngelectoral.utility.CommonMethods
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import java.util.*

class RollLookUpVotersListActivity : BaseActivity() {

    private var binding: ActivityRollLookUpVotersListBinding? = null
    private var transferInVotersListModel: TransferInVotersListModel? = null
    var wardRollVotersListAdapter: GenericAdapter<VotersList, ItemTransferInVoterBinding>? = null
    private var transferInVotersList = mutableListOf<VotersList>()
    private var transferInVotersListMain = mutableListOf<VotersList>()
    private var transferInVotersListToInitiateAdapter = mutableListOf<VotersList>()
    private var pageNumber = 1
    private var selectedPos = -1

    // input parameters
    var regionId: Int = 0
    var provinceId: Int = 0
    var districtId: Int = 0
    var wardId: Int = 0
    var llgId: Int = 0
    var regionName: String = ""
    var firstNameSearch: String = ""
    var lastNameSearch: String = ""
    var gender: String = ""
    var wardNumber: Int = 0
    var wardName: String = ""

    lateinit var dataBaseInstance: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_roll_look_up_voters_list
        )
        transferInVotersListModel =
            ViewModelProvider(this).get(TransferInVotersListModel::class.java)
        binding?.transferInVotersListModel = transferInVotersListModel

        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase

        regionId = intent.getIntExtra("RegionId", 0)
        regionName = intent.getStringExtra("RegionName")!!
        provinceId = intent.getIntExtra("ProvinceId", 0)
        districtId = intent.getIntExtra("DistrictId", 0)
        wardId = intent.getIntExtra("WardId", 0)
        llgId = intent.getIntExtra("LLGId", 0)
        firstNameSearch = intent.getStringExtra("FirstName")!!
        lastNameSearch = intent.getStringExtra("LastName")!!
        gender = intent.getStringExtra("Gender")!!
        wardNumber = intent.getIntExtra("WardNumber", 0)
        wardName = intent.getStringExtra("WardName")!!

        setFooterData()
        setWardRollVotersListAdapter(transferInVotersListToInitiateAdapter)
        addRvWardRollVotersListOnScrollListener()
        setOnclickListeners()
        addObservers()
        callSearchVotersListAPI(true)
        setUpRollLookupVoterListSearch()
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

    private fun addRvWardRollVotersListOnScrollListener() {
        val layoutManager = LinearLayoutManager(this)
        binding?.rvSearchVotersList?.layoutManager = layoutManager
    }


    private fun setOnclickListeners() {

        binding?.btnBack?.setOnClickListener {
            finish()
        }

        binding?.btnHome?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }


    }


    private fun callSearchVotersListAPI(isShowLoading: Boolean) {

        if (isShowLoading)
            CommonMethods.showProgress(this)

        val transferInVoterListObj = JsonObject()

        transferInVoterListObj.addProperty(
            ApiConstants.REGIONID,
            regionId
        )

        transferInVoterListObj.addProperty(
            ApiConstants.PROVINCEID,
            provinceId
        )

        transferInVoterListObj.addProperty(
            ApiConstants.DISTRICTID,
            districtId
        )

        if (llgId != 0)
            transferInVoterListObj.addProperty(
                ApiConstants.LLGID,
                llgId
            )

        if (wardId != 0)
            transferInVoterListObj.addProperty(
                ApiConstants.WARDID,
                wardId
            )


        transferInVoterListObj.addProperty(
            ApiConstants.LNAME,
            lastNameSearch
        )

        transferInVoterListObj.addProperty(
            ApiConstants.FNAME,
            firstNameSearch
        )

        transferInVoterListObj.addProperty(
            ApiConstants.GENDER,
            gender
        )

        transferInVoterListObj.addProperty(
            ApiConstants.PAGENO,
            pageNumber
        )

        transferInVoterListObj.addProperty(
            ApiConstants.PAGESIZE,
            100
        )

        transferInVotersListModel?.callTransferInVotersListAPI(this, transferInVoterListObj)
    }

    private fun addObservers() {

        transferInVotersListModel?.transferInVotersListResponse?.observe(this, Observer {
            if (it != null && it.status!!) {
                val transferInVotersListData = it.data
                if (pageNumber == 1) {
                    transferInVotersList.addAll(transferInVotersListData?.votersList!!)
                    transferInVotersListMain.addAll(transferInVotersListData.votersList!!)
                    if (transferInVotersList.size == 0) {
                        CommonMethods.hideProgress()
                        binding?.tvTitle?.text = "Found " + transferInVotersList.size + " Voters"
                        binding?.rvSearchVotersList?.visibility = View.GONE
                        binding?.tvNoVoters?.visibility = View.VISIBLE
                    } else {
                        if (transferInVotersList.size == transferInVotersListData.total) {
                            setRollLookUpVotersList()
                        } else {
                            pageNumber += 1
                            callSearchVotersListAPI(false)
                        }

                    }
                } else {
                    transferInVotersListMain.addAll(transferInVotersListData?.votersList!!)
                    transferInVotersList.addAll(transferInVotersListData.votersList!!)
                    if (transferInVotersList.size == transferInVotersListData.total) {
                        setRollLookUpVotersList()
                    } else {
                        pageNumber += 1
                        callSearchVotersListAPI(false)
                    }
                }
            } else {
                CommonMethods.hideProgress()
                if (pageNumber == 1) {
                    if (transferInVotersList.size == 0) {
                        binding?.rvSearchVotersList?.visibility = View.GONE
                        binding?.tvNoVoters?.visibility = View.VISIBLE
                    }
                    alert(it.message ?: "") {
                        okButton { }
                    }.show()
                } else {
                    if (transferInVotersList.size == 0) {
                        binding?.rvSearchVotersList?.visibility = View.GONE
                        binding?.tvNoVoters?.visibility = View.VISIBLE
                    } else {
                        setRollLookUpVotersList()
                    }
                }

            }
        })

    }

    private fun setRollLookUpVotersList() {
        CommonMethods.hideProgress()
        if (transferInVotersList.size == 1)
            binding?.tvTitle?.text = "Found " + transferInVotersList.size + " Voter"
        else
            binding?.tvTitle?.text =
                "Found " + transferInVotersList.size + " Voters"
        wardRollVotersListAdapter?.setData(transferInVotersList)
    }

    private fun setWardRollVotersListAdapter(transferInVotersListToInitiateAdapter: MutableList<VotersList>) {

        wardRollVotersListAdapter = object :
            GenericAdapter<VotersList, ItemTransferInVoterBinding>(
                transferInVotersListToInitiateAdapter
            ) {
            override fun getLayoutId(): Int {
                return R.layout.item_transfer_in_voter
            }

            @SuppressLint("SetTextI18n")
            override fun onBindData(
                model: VotersList?,
                position: Int,
                dataBinding: ItemTransferInVoterBinding?
            ) {

                if (model?.electorProfileImageUrl != null
                    && model.electorProfileImageUrl.isNotEmpty()
                ) {

                    Glide.with(this@RollLookUpVotersListActivity)
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

                dataBinding?.tvVoterName?.text = CommonMethods.getVoterNameForTransferIn(model)
                dataBinding?.tvVoterId?.text = "ID - " + model?.electorId
                dataBinding?.tvVoterLocation?.text = CommonMethods.getVoterDetails(
                    model?.locationName
                )!!
                dataBinding?.tvVoterStatus?.text = model?.status
                dataBinding?.tvCvlanName?.text =  "Clan Name :" + model?.clanName

                when {
                    CommonMethods.getVoterDetails(model?.gender)!! == "F" -> dataBinding?.tvGender?.text =
                        "Gender: Female"
                    CommonMethods.getVoterDetails(model?.gender)!! == "M" -> dataBinding?.tvGender?.text =
                        "Gender: Male"
                    else -> dataBinding?.tvGender?.text = "Gender:"
                }

                dataBinding?.tvDob?.text = "Dob: " + CommonMethods.getDisplayDate(
                    CommonMethods.getVoterDetails(
                        model?.dob
                    )!!
                )
                dataBinding?.tvOccupation?.text = "Occupation: " + CommonMethods.getVoterDetails(
                    model?.occupation
                )!!
                dataBinding?.tvNID?.text =
                    "National ID Card Number: " + CommonMethods.getVoterDetails(
                        model?.natIDRecordNo
                    )!!


                dataBinding?.tvRegion?.text =
                    "Region: $regionName"

                dataBinding?.tvProvince?.text =
                    "Province: " + CommonMethods.getVoterDetails(
                        model?.provinceName
                    )!!

                dataBinding?.tvDistrict?.text =
                    "District: " + CommonMethods.getVoterDetails(
                        model?.districtName
                    )!!

                dataBinding?.tvLLG?.text =
                    "LLG: " + CommonMethods.getVoterDetails(
                        model?.llgName
                    )!!

                dataBinding?.tvWard?.text =
                    "Ward: " + CommonMethods.getVoterDetails(
                        model?.wardName
                    )!!

                dataBinding?.tvLocation?.text =
                    "Location: " + CommonMethods.getVoterDetails(
                        model?.locationName
                    )!!

                dataBinding?.cvParent?.setOnClickListener {
                    selectedPos = model?.electorId!!.toInt()
                    dataBinding.cvParent.setBackgroundResource(R.color.lightGrey)
                    navigateToVoterDetailsScreen(model)
                    notifyDataSetChanged()
                }

                if (selectedPos == model?.electorId!!.toInt()) {
                    dataBinding?.cvParent?.setBackgroundResource(R.color.lightGrey)
                } else {
                    dataBinding?.cvParent?.setBackgroundResource(R.color.colorWhite)
                }

            }
        }
        binding?.rvSearchVotersList?.adapter = wardRollVotersListAdapter

    }


    private fun navigateToVoterDetailsScreen(
        model: VotersList?
    ) {
        val intent =
            Intent(
                this@RollLookUpVotersListActivity,
                RollLookUpVoterDetailsActivity::class.java
            )
        intent.putExtra(ApiConstants.VOTER_DETAILS, model)
        startActivity(intent)
    }


    // Roll Lookup Voter Search

    private fun setUpRollLookupVoterListSearch() {

        binding?.etSearch?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchVotersList(s.toString())
            }
        })

    }

    private fun searchVotersList(voterName: String) {
        val filteredVotersList = mutableListOf<VotersList>()
        if (voterName.isEmpty()) {
            filteredVotersList.clear()
            filteredVotersList.addAll(transferInVotersListMain)
        } else {
            filteredVotersList.clear()
            for (i in transferInVotersListMain.indices) {
                if (searchWithFirstAndLastName(
                        voterName,
                        transferInVotersListMain[i].fName,
                        transferInVotersListMain[i].lName
                    )

                ) {
                    filteredVotersList.add(transferInVotersListMain[i])
                }
            }
        }

        if (filteredVotersList.size == 0) {
            binding?.tvTitle?.text = "Found " + filteredVotersList.size + " Voters"
            binding?.rvSearchVotersList?.visibility = View.GONE
            binding?.tvNoVoters?.visibility = View.VISIBLE
        } else {
            binding?.rvSearchVotersList?.visibility = View.VISIBLE
            binding?.tvNoVoters?.visibility = View.GONE
            if (filteredVotersList.size == 1)
                binding?.tvTitle?.text = "Found " + filteredVotersList.size + " Voter"
            else
                binding?.tvTitle?.text = "Found " + filteredVotersList.size + " Voters"

            wardRollVotersListAdapter?.clearItems()
            wardRollVotersListAdapter?.addItems(filteredVotersList)
            wardRollVotersListAdapter?.notifyDataSetChanged()
        }


    }


    private fun searchWithFirstAndLastName(
        voterName: String,
        fName: String?,
        lName: String?
    ): Boolean {
        var similarVoter = false

        if (fName!!.toLowerCase(Locale.getDefault()).contains(
                voterName.toLowerCase(
                    Locale.getDefault()
                )
            )
        ) {
            similarVoter = true
        } else if (lName!!.toLowerCase(Locale.getDefault()).contains(
                voterName.toLowerCase(
                    Locale.getDefault()
                )
            )
        ) {
            similarVoter = true
        }

        return similarVoter


    }


}
