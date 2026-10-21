package com.pngelectoral.ui.activity

import ApiConstants
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
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
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.master.basediproject.extensions.toArrayList
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.*
import com.pngelectoral.ui.adapter.GenericAdapter
import com.pngelectoral.ui.model.ClanListData
import com.pngelectoral.ui.model.ClanListModel
import com.pngelectoral.ui.model.DPOUserData
import com.pngelectoral.ui.model.DistrictList
import com.pngelectoral.ui.model.LocationList
import com.pngelectoral.ui.model.MKAGeoListData
import com.pngelectoral.ui.model.MKAGeoListModel
import com.pngelectoral.ui.model.VoterListFilterModel
import com.pngelectoral.ui.model.VotersList
import com.pngelectoral.ui.retrofit.*
import com.pngelectoral.ui.viewModel.WardRollViewModel
import com.pngelectoral.utility.CommonMethods
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext


class WardRollVoterListActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityWardRollVoterListBinding? = null
    private var wardRollViewModel: WardRollViewModel? = null
    private var wardRollVotersListAdapter: GenericAdapter<VotersList, ItemWardRollVoterBinding>? =
        null
    private var statusList = mutableListOf<VoterListFilterModel>()
    lateinit var loggedInDPOUserData: DPOUserData
    lateinit var dataBaseInstance: AppDatabase

    var filterRegionId = 0
    var filterProvinceId = 0
    var filterDistrictId = 0
    var filterMkaDistrictId = 0
    var filterLLGId = 0
    var filterMkaLLGId = 0
    var filterWardId = 0
    var filterMkaWardId = 0
    private var filterWardName = ""

    private var filterLocationId = 0
    private var filterLocationSelectedPos = -1
    private var filterLocationName = ""

    private val statusFilterList = ArrayList<String>()
    private var clickedVoterPosition = 0
    private var maleVotersCount = 0
    private var femaleVotersCount = 0
    private var otherVotersCount = 0
    private var totalVotersCount = 0
    private var workingRollTitle = ""

    private var voterWithPhoto = 0

    private var voterWithoutPhoto = 0

    private var filterType = ""
    private var CanEdit = ""
    private var filterClanId = 0
    private var filterClanSelectedPos = -1
    private var filterClanName = ""

    private var mkaLocationArray = ArrayList<MKAGeoListModel.GeoListData.LlG.Ward.Location>()
    val allVoterList = mutableListOf<VotersList>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    private var clanArray: ArrayList<ClanListData>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_ward_roll_voter_list
        )
        wardRollViewModel =
            ViewModelProvider(this).get(WardRollViewModel::class.java)
        binding?.wardRollViewModel = wardRollViewModel

        job = Job()

        intent?.apply {
            filterType =
                getStringExtra(ApiConstants.FILTER_TYPE) ?: ApiConstants.PNGEC_GEO_STRUCTURE
            CanEdit =  getStringExtra("CanEdit")?:""
        }



        initDetailsAndFetchVoterData()
        setFooterData()
        setOnClickListeners()
        statusList = CommonMethods.getStatusFilterList()
        addRvWardRollVotersListOnScrollListener()
        setUpVoterListSearch()
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


    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    private fun initDetailsAndFetchVoterData() {

        CommonMethods.showProgress(this)

        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )
        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase

        // GEO LOCATION ID'S
        filterRegionId = loggedInDPOUserData.region.toIntOrNull() ?: 4
        filterProvinceId = loggedInDPOUserData.province.toIntOrNull() ?: 13
        if (securedSharedPreferenceWrapper.getString(
                ApiConstants.ROLE,
                ""
            ) == ApiConstants.ROLE_MKA
        ) {
            if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                filterDistrictId = loggedInDPOUserData.district.toIntOrNull()?.takeIf { it != 0 } ?: 58
                filterMkaDistrictId = loggedInDPOUserData.mkaDistrict.toIntOrNull()?.takeIf { it != 0 } ?: 1
                val prefLLG = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LLG_ID, 0)
                filterLLGId = if (prefLLG != 0) prefLLG else (loggedInDPOUserData.llg.toIntOrNull()?.takeIf { it != 0 } ?: 248)
                filterMkaLLGId = loggedInDPOUserData.mkallg.toIntOrNull()?.takeIf { it != 0 } ?: 1
                val prefWard = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_WARD_ID, 0)
                filterWardId = if (prefWard != 0) prefWard else (loggedInDPOUserData.ward.toIntOrNull()?.takeIf { it != 0 } ?: 5810)
                filterMkaWardId = loggedInDPOUserData.mkaWard.toIntOrNull()?.takeIf { it != 0 } ?: 1
                filterLocationId =
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_LOCATION,
                            ""
                        )
                            .isNotEmpty()
                    )
                        securedSharedPreferenceWrapper.getInt(
                            ApiConstants.DPO_WORKING_ROLL_LOCATION_ID,
                            0
                        )
                    else
                        ApiConstants.HOME_FILTER_LOCATION_ID
                val savedWardName = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_WARD, "")
                filterWardName = if (savedWardName.isNotEmpty()) savedWardName else (loggedInDPOUserData.wardName ?: "Ward 1")
                filterLocationName =
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_LOCATION,
                            ""
                        )
                            .isNotEmpty()
                    )
                        securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_LOCATION,
                            ""
                        )
                    else
                        ApiConstants.HOME_FILTER_LOCATION
            } else {
                val mkaDist = loggedInDPOUserData.mkaDistrict.toIntOrNull()?.takeIf { it != 0 } ?: 1
                filterDistrictId = mkaDist
                val prefMkaLLG = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_MKA_LLG_ID, 0)
                val prefLLG = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LLG_ID, 0)
                filterLLGId = if (prefMkaLLG != 0) prefMkaLLG else if (prefLLG != 0) prefLLG else (loggedInDPOUserData.mkallg.toIntOrNull()?.takeIf { it != 0 } ?: 1)
                val prefMkaWard = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_MKA_WARD_ID, 0)
                val prefWard = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_WARD_ID, 0)
                filterWardId = if (prefMkaWard != 0) prefMkaWard else if (prefWard != 0) prefWard else (loggedInDPOUserData.mkaWard.toIntOrNull()?.takeIf { it != 0 } ?: 1)
                filterLocationId =
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_MKA_LOCATION,
                            ""
                        )
                            .isNotEmpty()
                    )
                        securedSharedPreferenceWrapper.getInt(
                            ApiConstants.DPO_WORKING_ROLL_MKA_LOCATION_ID,
                            0
                        )
                    else
                        ApiConstants.HOME_FILTER_MKA_LOCATION_ID
                val savedMkaWardName = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_MKA_WARD, "")
                val savedWardName = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_WARD, "")
                filterWardName = if (savedMkaWardName.isNotEmpty()) savedMkaWardName else if (savedWardName.isNotEmpty()) savedWardName else (loggedInDPOUserData.mkaWardName ?: "Araira")
                filterLocationName =
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_MKA_LOCATION,
                            ""
                        )
                            .isNotEmpty()
                    )
                        securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_MKA_LOCATION,
                            ""
                        )
                    else
                        ApiConstants.HOME_FILTER_MKA_LOCATION
            }
        } else {
            filterDistrictId = loggedInDPOUserData.district.toIntOrNull()?.takeIf { it != 0 } ?: 58
            val prefLLG = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LLG_ID, 0)
            filterLLGId = if (prefLLG != 0) prefLLG else (loggedInDPOUserData.llg.toIntOrNull()?.takeIf { it != 0 } ?: 248)
            val prefWard = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_WARD_ID, 0)
            filterWardId = if (prefWard != 0) prefWard else (loggedInDPOUserData.ward.toIntOrNull()?.takeIf { it != 0 } ?: 5810)
            filterLocationId =
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_LOCATION,
                        ""
                    )
                        .isNotEmpty()
                )
                    securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_LOCATION_ID,
                        0
                    )
                else
                    ApiConstants.HOME_FILTER_LOCATION_ID
            val savedWard = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_WARD, "")
            filterWardName = if (savedWard.isNotEmpty()) savedWard else (loggedInDPOUserData.wardName ?: "Ward 1")
            filterLocationName =
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_LOCATION,
                        ""
                    )
                        .isNotEmpty()
                )
                    securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_LOCATION,
                        ""
                    )
                else
                    ApiConstants.HOME_FILTER_LOCATION
        }

        // GEO LOCATION NAMES

        when {
            filterLocationId != 0 -> workingRollTitle =
                "$filterLocationName Location"

            filterWardId != 0 ->
                workingRollTitle = "$filterWardName Ward"

            else ->
                workingRollTitle = if (filterWardName.isNotEmpty()) "$filterWardName Ward" else "Ward Roll"
        }
        binding?.tvTitle?.text = workingRollTitle
        getWardRollVotersListUsingCoRoutines()
    }

    private fun getWardRollVotersListUsingCoRoutines() {
        launch(Dispatchers.Main) {
            val result: List<VotersList>? = withContext(Dispatchers.IO) {
                var list = getFilteredVotersList()
                if (list.isNullOrEmpty()) {
                    list = dataBaseInstance.votersListDao().all
                }
                list
            }
            allVoterList.clear()
            if (result != null) {
                allVoterList.addAll(result)
            }
            searchVotersListUsingPersistedFilterValues(allVoterList)
        }
    }

    private fun setOnClickListeners() {
        binding?.btnHome?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }

        binding?.btnFilter?.setOnClickListener {
            showFilterDialog()
        }

        binding?.rlGender?.setOnClickListener {
            showGenderOptions()
        }

        binding?.btnGenderSplitReport?.setOnClickListener {
            showGenderWiseVotersCount()
        }

        binding?.btnPhotoSplitReport?.setOnClickListener {
            showPhotoWiseVotersCount()
        }


    }
    private var bindingDialogPhotoVoterCount: DialogPhotoVoterCountBinding? = null
    private var mAlertDialogPhotoWiseCount: AlertDialog? = null


    private fun showPhotoWiseVotersCount() {

        val builder = AlertDialog.Builder(this, R.style.full_screen_dialog)
        bindingDialogPhotoVoterCount = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_photo_voter_count, null, false
        ) as DialogPhotoVoterCountBinding
        builder.setView(bindingDialogPhotoVoterCount?.root)

        mAlertDialogPhotoWiseCount = builder.create()
        mAlertDialogPhotoWiseCount?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        bindingDialogPhotoVoterCount?.tvPhotoCount?.text = "Voters with Photo: $voterWithPhoto"
        bindingDialogPhotoVoterCount?.tvWithoutPhotoCount?.text =  "Voters without Photo: $voterWithoutPhoto"
        bindingDialogPhotoVoterCount?.tvTotalCount?.text = "No.of Total voters: $totalVotersCount"

        bindingDialogPhotoVoterCount?.ivClose?.setOnClickListener {
            mAlertDialogPhotoWiseCount!!.dismiss()
        }

        mAlertDialogPhotoWiseCount?.show()
    }



    private var bindingDialogGenderVoterCount: DialogGenderVoterCountBinding? = null
    private var mAlertDialogGenderWiseCount: AlertDialog? = null

    private fun showGenderWiseVotersCount() {

        val builder = AlertDialog.Builder(this, R.style.full_screen_dialog)
        bindingDialogGenderVoterCount = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_gender_voter_count, null, false
        ) as DialogGenderVoterCountBinding
        builder.setView(bindingDialogGenderVoterCount?.root)

        mAlertDialogGenderWiseCount = builder.create()
        mAlertDialogGenderWiseCount?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        bindingDialogGenderVoterCount?.tvMaleCount?.text = "No.of Male voters: $maleVotersCount"
        bindingDialogGenderVoterCount?.tvFemaleCount?.text =
            "No.of Female voters: $femaleVotersCount"
        bindingDialogGenderVoterCount?.tvOthersCount?.text = "No.of Other voters: $otherVotersCount"
        bindingDialogGenderVoterCount?.tvTotalCount?.text = "No.of Total voters: $totalVotersCount"

        bindingDialogGenderVoterCount?.ivClose?.setOnClickListener {
            mAlertDialogGenderWiseCount!!.dismiss()
        }

        mAlertDialogGenderWiseCount?.show()
    }

    private fun showGenderOptions() {

        val items = arrayOf<CharSequence>(
            "All",
            "Male",
            "Female",
            "Other"

        )
        val builder = AlertDialog.Builder(this)
        builder.setItems(items) { _, item ->
            if (item == 0) {
                binding?.etGender?.text = ""
            } else {
                binding?.etGender?.text = items[item]
            }
            searchVotersList()
        }
        builder.show()

    }


    private fun addRvWardRollVotersListOnScrollListener() {
        val layoutManager = LinearLayoutManager(this)
        binding?.rvWardRollVotersList?.layoutManager = layoutManager
    }

    private var textChangedJob: Job? = null
    private fun setUpVoterListSearch() {
        binding?.etFirstName?.addTextChangedListener(object : TextWatcher {
            private var searchFor = ""
            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().trim()
                if(searchText.isNotEmpty()){
                    if (searchText != searchFor) {
                        searchFor = searchText
                        textChangedJob?.cancel()
                        textChangedJob = CoroutineScope(Dispatchers.IO).launch {
                            delay(1000L)
                            if (searchText == searchFor) {
                                searchVotersList()
                            }
                        }
                    }
                }else{
                    setWardRollVotersListAdapter(allVoterList)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding?.etLastName?.addTextChangedListener(object : TextWatcher {
            private var searchFor = ""
            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().trim()
                if(searchText.isNotEmpty()) {
                    if (searchText != searchFor) {
                        searchFor = searchText
                        textChangedJob?.cancel()
                        textChangedJob = CoroutineScope(Dispatchers.IO).launch {
                            delay(1000L)
                            if (searchText == searchFor) {
                                searchVotersList()
                            }
                        }
                    }
                }else{
                    setWardRollVotersListAdapter(allVoterList)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        binding?.etElectorId?.addTextChangedListener(object : TextWatcher {
            private var searchFor = ""
            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().trim()
                if(searchText.isNotEmpty()) {
                    if (searchText != searchFor) {
                        searchFor = searchText
                        textChangedJob?.cancel()
                        textChangedJob = CoroutineScope(Dispatchers.IO).launch {
                            delay(1000L)
                            if (searchText == searchFor) {
                                searchVotersList()
                            }
                        }
                    }
                }else{
                    setWardRollVotersListAdapter(allVoterList)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private var searchedClanId = 0
    private var searchedLastName = ""
    private var searchedFirstName = ""
    private var searchedElectorId = ""
    private var selectedGender = ""

    private fun searchVotersList() {

        searchedLastName = binding?.etLastName?.text.toString().trim()
        searchedFirstName = binding?.etFirstName?.text.toString().trim()
        searchedElectorId = binding?.etElectorId?.text.toString().trim()
        selectedGender = when {
            binding?.etGender?.text.toString() == "Male" -> {
                "M"
            }

            binding?.etGender?.text.toString() == "Female" -> {
                "F"
            }

            binding?.etGender?.text.toString() == "Other" -> {
                "Other"
            }

            else -> {
                ""
            }
        }

        securedSharedPreferenceWrapper.putString(
            ApiConstants.FILTER_WARD_ROLL_LAST_NAME,
            searchedLastName
        )
        securedSharedPreferenceWrapper.putString(
            ApiConstants.FILTER_WARD_ROLL_FIRST_NAME,
            searchedFirstName
        )
        securedSharedPreferenceWrapper.putString(
            ApiConstants.FILTER_WARD_ROLL_ELECTOR_ID,
            searchedElectorId
        )
        securedSharedPreferenceWrapper.putString(
            ApiConstants.FILTER_WARD_ROLL_GENDER,
            selectedGender
        )

        getSearchWiseVotersList()


    }

    private fun getSearchWiseVotersList() {

        launch(Dispatchers.Main) {
            val result: List<VotersList>? = withContext(Dispatchers.IO) {
                var list = getFilteredVotersList()
                if (list.isNullOrEmpty() && (searchedFirstName.isEmpty() && searchedLastName.isEmpty() && searchedElectorId.isEmpty() && selectedGender.isEmpty())) {
                    list = dataBaseInstance.votersListDao().all
                }
                list
            }
            val wardRollVotersList = mutableListOf<VotersList>()
            if (result != null) {
                wardRollVotersList.addAll(result)
            }
            if (wardRollVotersList.isEmpty() && (searchedFirstName.isNotEmpty() || searchedLastName.isNotEmpty() || searchedElectorId.isNotEmpty() || selectedGender.isNotEmpty())) {
                val allCached = withContext(Dispatchers.IO) { dataBaseInstance.votersListDao().all }
                if (!allCached.isNullOrEmpty()) {
                    for (voter in allCached) {
                        if (checkGenderAndAddResult(voter)) {
                            wardRollVotersList.add(voter)
                        }
                    }
                }
            }
            setWardRollVotersListAdapter(wardRollVotersList)
        }

    }

    private fun checkGenderAndAddResult(downloadedVotersList: VotersList): Boolean {
        val sameGender = if (selectedGender.isEmpty())
            true
        else {
            if (selectedGender == "Other" && downloadedVotersList.gender.isNullOrEmpty())
                true
            else if (selectedGender == "Other" && (downloadedVotersList.gender != "M" && downloadedVotersList.gender != "F")) {
                true
            } else selectedGender == downloadedVotersList.gender
        }


        if (sameGender) {
            if (searchedLastName.isEmpty() && searchedFirstName.isEmpty() && searchedElectorId.isEmpty()) {
                return true
            } else {
                var addVoter = false
                if (searchedLastName.isNotEmpty()) {
                    if (downloadedVotersList.lName != null
                        && downloadedVotersList.lName!!.length >= searchedLastName.length
                    ) {
                        if (searchedLastName.equals(
                                downloadedVotersList.lName.take(
                                    searchedLastName.length
                                ), ignoreCase = true
                            )
                        )
                            addVoter = true
                    }
                }

                if (searchedFirstName.isNotEmpty()) {
                    if (downloadedVotersList.fName != null
                        && downloadedVotersList.fName!!.length >= searchedFirstName.length
                    ) {
                        if (searchedFirstName.equals(
                                downloadedVotersList.fName.take(
                                    searchedFirstName.length
                                ), ignoreCase = true
                            )
                        )
                            addVoter = true
                    }
                }

                if (searchedElectorId.isNotEmpty()) {
                    if (downloadedVotersList.electorId != null
                        && downloadedVotersList.electorId!!.toString().length >= searchedElectorId.length
                    ) {
                        if (searchedElectorId.equals(
                                downloadedVotersList.electorId.toString().take(
                                    searchedElectorId.length
                                ), ignoreCase = true
                            )
                        )
                            addVoter = true
                    }
                }
                return addVoter
            }
        } else {
            return false
        }
    }


    private var bindingFilterScreen: DialogFilterScreenBinding? = null
    private var mAlertDialog: AlertDialog? = null
    private var statusAdapter: GenericAdapter<VoterListFilterModel, ItemFilterBinding>? = null
    internal var statusId = ArrayList<String>()

    private fun showFilterDialog() {

        val builder = AlertDialog.Builder(this, R.style.full_screen_dialog)
        bindingFilterScreen = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_filter_screen, null, false
        ) as DialogFilterScreenBinding
        builder.setView(bindingFilterScreen?.root)

        mAlertDialog = builder.create()
        mAlertDialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        when (filterType) {
            ApiConstants.PNGEC_GEO_STRUCTURE -> {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_LOCATION,
                        ""
                    )
                        .isEmpty()
                ) {
                    bindingFilterScreen?.ivLocation?.visibility = View.VISIBLE
                } else {
                    bindingFilterScreen?.ivLocation?.visibility = View.GONE
                }
            }

            ApiConstants.MKA_GEO_STRUCTURE -> {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_MKA_LOCATION,
                        ""
                    )
                        .isEmpty()
                ) {
                    bindingFilterScreen?.ivLocation?.visibility = View.VISIBLE
                } else {
                    bindingFilterScreen?.ivLocation?.visibility = View.GONE
                }
            }

            else -> {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_LOCATION,
                        ""
                    )
                        .isEmpty()
                ) {
                    bindingFilterScreen?.ivLocation?.visibility = View.VISIBLE
                } else {
                    bindingFilterScreen?.ivLocation?.visibility = View.GONE
                }
            }
        }

        if (filterLocationSelectedPos == 0) {
            bindingFilterScreen?.etLocation?.text = "All Locations"
        } else if (filterLocationName.isNotEmpty()) {
            bindingFilterScreen?.etLocation?.text = filterLocationName
        }

        if (filterClanSelectedPos == 0) {
            bindingFilterScreen?.etClan?.text = "All"
        } else if (filterClanName.isNotEmpty()) {
            bindingFilterScreen?.etClan?.text = filterClanName
        }

        val statusListLayoutManager = LinearLayoutManager(this)
        statusAdapter = prepareStatusAdapter()
        bindingFilterScreen?.rvStatusList!!.layoutManager = statusListLayoutManager
        bindingFilterScreen?.rvStatusList!!.adapter = statusAdapter

        bindingFilterScreen?.rlLocation?.setOnClickListener {
            when (filterType) {
                ApiConstants.PNGEC_GEO_STRUCTURE -> {
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_LOCATION,
                            ""
                        )
                            .isEmpty()
                    ) {
                        if (securedSharedPreferenceWrapper.getBoolean(
                                ApiConstants.LOCATION_DATA_DOWNLOADED,
                                false
                            )
                        ) {
                            CommonMethods.showProgress(this)
                            getLocationListFromDB()
                        } else
                            callLocationListAPI()
                    }
                }

                ApiConstants.MKA_GEO_STRUCTURE -> {
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_MKA_LOCATION,
                            ""
                        )
                            .isEmpty()
                    ) {
                        if (securedSharedPreferenceWrapper.getBoolean(
                                ApiConstants.LOCATION_DATA_DOWNLOADED,
                                false
                            )
                        ) {
                            CommonMethods.showProgress(this)
                            getMKALocationListFromDB()
                        }
                    }
                }

                else -> {
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_LOCATION,
                            ""
                        )
                            .isEmpty()
                    ) {
                        if (securedSharedPreferenceWrapper.getBoolean(
                                ApiConstants.LOCATION_DATA_DOWNLOADED,
                                false
                            )
                        ) {
                            CommonMethods.showProgress(this)
                            getLocationListFromDB()
                        } else
                            callLocationListAPI()
                    }
                }
            }
        }


        bindingFilterScreen?.rlClan?.setOnClickListener {
            launch(Dispatchers.Main) {
                val result: List<ClanListData>? = withContext(Dispatchers.IO) {
                    dataBaseInstance.geoClanListDao().getDataForClanByWard(filterWardId)
                }
                clanArray = result.toArrayList()
                val clanList: Array<String> = getClanList(clanArray!!)
                bindingFilterScreen?.etClan?.tag = filterClanSelectedPos
                showSingleSelectionDialogClan(
                    clanList,
                    bindingFilterScreen?.etClan!!,
                )
            }
        }

        mAlertDialog?.setCancelable(false)

        bindingFilterScreen?.cardApply?.setOnClickListener {
            filterVotersList()
        }

        bindingFilterScreen?.ivClose?.setOnClickListener {
            mAlertDialog!!.dismiss()
        }

        mAlertDialog?.show()

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


    private fun callLocationListAPI() {

        CommonMethods.showProgress(this)
        val locationObject = JsonObject()
        locationObject.addProperty(ApiConstants.REGIONID, filterRegionId)
        locationObject.addProperty(ApiConstants.PROVINCEID, filterProvinceId)
        locationObject.addProperty(ApiConstants.DISTRICTID, filterDistrictId)
        locationObject.addProperty(ApiConstants.LLGID, filterLLGId)
        locationObject.addProperty(ApiConstants.WARDID, filterWardId)

        val call =
            ApiClient.getRetrofitInterface()?.callGetLocationListAPI(locationObject)

        WSClient<Response<ArrayList<LocationList>>>().request(
            this,
            ApiConstants.DPO_GET_LOCATION_LIST,
            call,
            object :
                ISuccessHandler<Response<ArrayList<LocationList>>> {
                override fun successResponse(
                    requestCode: Int,
                    mResponse: Response<ArrayList<LocationList>>
                ) {
                    CommonMethods.hideProgress()
                    if (mResponse.data != null)
                        showLocationOptions(mResponse.data)
                    else
                        showAlertMessage(mResponse.message!!)
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    CommonMethods.hideProgress()
                    showAlertMessage(message)
                }
            })
    }


    private fun showSingleSelectionDialogClan(
        singleChoiceItems: Array<String>,
        tvSelection: AppCompatTextView
    ) {
        CommonMethods.hideProgress()
        if (singleChoiceItems.isNotEmpty()) {
            var selectionValue = -1
            if (tvSelection.text!!.isNotEmpty()) {
                selectionValue = if (tvSelection.tag == null) {
                    getSelectedPosition(tvSelection.text.toString(), singleChoiceItems)
                } else {
                    tvSelection.tag as Int
                }
            }

            val mBuilder =
                AlertDialog.Builder(this)

            mBuilder.setSingleChoiceItems(
                singleChoiceItems,
                selectionValue
            ) { dialogInterface, clickItem ->
                tvSelection.tag = clickItem
                tvSelection.text = singleChoiceItems[clickItem]
                filterClanSelectedPos = clickItem
                if(clickItem > 1) {
                    filterClanId = clanArray!![clickItem - 2].clanId
                    filterClanName = clanArray!![clickItem - 2].clanName
                }
                else if(clickItem == 1) {
                    filterClanId = -1
                    filterClanName =  "Null"
                }
                else {
                    filterClanId = 0
                    filterClanName = ""
                }
                dialogInterface.dismiss()
            }
            val mDialog = mBuilder.create()
            mDialog.show()
        } else {
            showAlertMessage("No items found")
        }
    }

    private fun getSelectedPosition(selectedValue: String, singleChoiceItems: Array<String>): Int {
        var selectedPosition = -1
        for (i in singleChoiceItems.indices) {
            if (selectedValue == singleChoiceItems[i]) {
                selectedPosition = i
                return selectedPosition
            }
        }

        return selectedPosition
    }

    private fun showSingleSelectionDialog(
        singleChoiceItems: Array<String>,
        tvSelection: AppCompatTextView
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
                if (clickItem == 0) {
                    tvSelection.text = singleChoiceItems[clickItem]
                    filterLocationSelectedPos = clickItem
                    filterLocationId = 0
                    filterLocationName = ""
                } else {
                    tvSelection.tag = clickItem
                    tvSelection.text = singleChoiceItems[clickItem]
                    filterLocationSelectedPos = clickItem
                    if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE)
                        filterLocationId = locationArray!![clickItem - 1].locationId
                    else
                        filterLocationId = mkaLocationArray[clickItem - 1].locationId ?: 0
                    filterLocationName = singleChoiceItems[clickItem]
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


    @SuppressLint("SetTextI18n")
    private fun filterVotersList() {

        mAlertDialog!!.dismiss()
        if (securedSharedPreferenceWrapper.getString( ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO)
         {
            ApiConstants.HOME_FILTER_LOCATION_ID = filterLocationId
            ApiConstants.HOME_FILTER_LOCATION = filterLocationName
             ApiConstants.HOME_FILTER_CLAN_ID = filterClanId
             ApiConstants.HOME_FILTER_CLAN = filterClanName

        } else {
            if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                ApiConstants.HOME_FILTER_LOCATION_ID = filterLocationId
                ApiConstants.HOME_FILTER_LOCATION = filterLocationName
            } else {
                ApiConstants.HOME_FILTER_MKA_LOCATION_ID = filterLocationId
                ApiConstants.HOME_FILTER_MKA_LOCATION = filterLocationName
            }
        }
        when {
//            filterLocationId != 0 -> binding?.tvTitle?.text = "$filterLocationName Location"
//            filterLocationSelectedPos == 0 -> binding?.tvTitle?.text =
//                "$filterWardName Ward (All Locations)"
//            else -> binding?.tvTitle?.text = "$filterWardName Ward"

            filterLocationId != 0 -> workingRollTitle = "$filterLocationName Location"
            filterLocationSelectedPos == 0 -> workingRollTitle =
                "$filterWardName Ward (All Locations)"

            else -> workingRollTitle = "$filterWardName Ward"
        }
        binding?.tvTitle?.text = workingRollTitle

        statusFilterList.clear()
        for (i in statusList.indices) {
            statusList[i].selected = statusId.contains(statusList[i].id)
        }

        for (i in statusList.indices) {
            if (statusList[i].selected!!) {
                if(statusList[i].id != "10" && statusList[i].id != "11"){
                     statusFilterList.add(statusList[i].name!!)
                }
            }
        }

        CommonMethods.showProgress(this)
        getFilteredVotersListUsingCoRoutines()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 11 && data != null) {
                if (data.getBooleanExtra("NEED_REFRESH", false)) {
                    CommonMethods.showProgress(this)
                    getFilteredVotersListUsingCoRoutines()
                }
            } else if (requestCode == ApiConstants.VOTER_STATUS_UPDATE && data != null) {
                if (data.getBooleanExtra("NEED_REFRESH", false)) {
                    CommonMethods.showProgress(this)
                    getFilteredVotersListUsingCoRoutines()
                }
            }
        }

    }


    private fun getFilteredVotersListUsingCoRoutines() {
        launch(Dispatchers.Main) {
            searchedLastName = securedSharedPreferenceWrapper.getString(
                ApiConstants.FILTER_WARD_ROLL_LAST_NAME,
                ""
            )
            searchedFirstName = securedSharedPreferenceWrapper.getString(
                ApiConstants.FILTER_WARD_ROLL_FIRST_NAME,
                ""
            )
            searchedElectorId = securedSharedPreferenceWrapper.getString(
                ApiConstants.FILTER_WARD_ROLL_ELECTOR_ID,
                ""
            )
            selectedGender = securedSharedPreferenceWrapper.getString(
                ApiConstants.FILTER_WARD_ROLL_GENDER,
                ""
            )

            searchedClanId = ApiConstants.HOME_FILTER_CLAN_ID


            val result: List<VotersList>? = withContext(Dispatchers.IO) {
                getFilteredVotersList()
            }
            /*val wardRollFilteredVotersList = mutableListOf<VotersList>()
            val wardRollVotersList = mutableListOf<VotersList>()
            wardRollVotersList.addAll(result!!)
            for (i in wardRollVotersList.indices) {
                if (checkGenderAndAddResult(wardRollVotersList[i])) {
                    wardRollFilteredVotersList.add(wardRollVotersList[i])
                }
            }*/

            statusFilterList.clear()
            for (i in statusList.indices) {
                statusList[i].selected = statusId.contains(statusList[i].id)
            }

            for (i in statusList.indices) {
                if (statusList[i].selected!!) {
                        statusFilterList.add(statusList[i].name!!)
                }
            }

            val finalFilterResult = mutableListOf<VotersList>()


           for(e in result!!) {
               if (statusFilterList.contains("Without Photo") || statusFilterList.contains("With Photo")) {
                   if (statusFilterList.contains("Without Photo")) {
                       if (e.electorProfileImageUrl.isNullOrEmpty() && e.electorProfileImage.isNullOrEmpty()) {
                           finalFilterResult.add(e);
                       }
                   }

                   if (statusFilterList.contains("With Photo")) {
                       if (!e.electorProfileImageUrl.isNullOrEmpty() || !e.electorProfileImage.isNullOrEmpty()) {
                           finalFilterResult.add(e);
                       }
                   }
               }
               else{

                   finalFilterResult.add(e);
               }
           }

            val wardRollVotersList = mutableListOf<VotersList>()
            wardRollVotersList.addAll(finalFilterResult!!)
            searchVotersListUsingPersistedFilterValues(wardRollVotersList)
        }
    }

    private fun searchVotersListUsingPersistedFilterValues(votersList: MutableList<VotersList>) {
        if (votersList.size > 0) {
            setWardRollVotersListAdapter(votersList)
            if (clickedVoterPosition == 0 || clickedVoterPosition == votersList.size - 1) {
                binding?.rvWardRollVotersList?.smoothScrollToPosition(clickedVoterPosition)
            } else {
                binding?.rvWardRollVotersList?.smoothScrollToPosition(clickedVoterPosition + 1)
            }
            Handler(mainLooper).postDelayed({
                CommonMethods.hideProgress()
            }, 1000)
        } else {
            binding?.tvTitle?.text = workingRollTitle + " (" + votersList.size + ")"
            CommonMethods.hideProgress()
            binding?.tvNoVoters?.visibility = View.VISIBLE
            binding?.rvWardRollVotersList?.visibility = View.GONE

        }
    }


    private fun getFilteredVotersList(): List<VotersList>? {
        if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "") == ApiConstants.ROLE_DPO)
        {
            if (statusFilterList.isEmpty())
            {
                if(selectedGender.isNotEmpty() || searchedFirstName.isNotEmpty() || searchedLastName.isNotEmpty() || searchedElectorId.isNotEmpty()){

                    when (filterLocationId) {
                        0 -> {
                            if(searchedClanId == 0) {
                                if(selectedGender == "Other") {
                                    selectedGender = ""
                                    return dataBaseInstance.votersListDao()
                                        .getSearchWiseWardRollWardFilteredVotersListLike(
                                            filterRegionId,
                                            filterProvinceId,
                                            filterDistrictId,
                                            filterLLGId,
                                            filterWardId,
                                            selectedGender,
                                            searchedFirstName,
                                            searchedLastName,
                                            searchedElectorId
                                        )
                                }
                                else {
                                    return dataBaseInstance.votersListDao()
                                        .getSearchWiseWardRollWardFilteredVotersList(
                                            filterRegionId,
                                            filterProvinceId,
                                            filterDistrictId,
                                            filterLLGId,
                                            filterWardId,
                                            selectedGender,
                                            searchedFirstName,
                                            searchedLastName,
                                            searchedElectorId
                                        )
                                }
                            }
                            else
                            {
                                if(searchedClanId == -1) {
                                    if(selectedGender == "Other") {
                                        selectedGender = ""
                                        return dataBaseInstance.votersListDao()
                                            .getSearchWiseWardRollWardFilteredVotersListClanNullLike(
                                                filterRegionId,
                                                filterProvinceId,
                                                filterDistrictId,
                                                filterLLGId,
                                                filterWardId,
                                                selectedGender,
                                                searchedFirstName,
                                                searchedLastName,
                                                searchedElectorId
                                            )
                                    }
                                    else
                                    {
                                        return dataBaseInstance.votersListDao()
                                            .getSearchWiseWardRollWardFilteredVotersListClanNull(
                                                filterRegionId,
                                                filterProvinceId,
                                                filterDistrictId,
                                                filterLLGId,
                                                filterWardId,
                                                selectedGender,
                                                searchedFirstName,
                                                searchedLastName,
                                                searchedElectorId
                                            )
                                    }
                                }
                                else
                                {
                                    if(selectedGender == "Other") {
                                        selectedGender = ""
                                        return dataBaseInstance.votersListDao()
                                            .getSearchWiseWardRollWardFilteredVotersListClanLike(
                                                filterRegionId,
                                                filterProvinceId,
                                                filterDistrictId,
                                                filterLLGId,
                                                filterWardId,
                                                selectedGender,
                                                searchedFirstName,
                                                searchedLastName,
                                                searchedElectorId,
                                                searchedClanId
                                            )
                                    }
                                    else
                                    {
                                        return dataBaseInstance.votersListDao()
                                            .getSearchWiseWardRollWardFilteredVotersListClan(
                                                filterRegionId,
                                                filterProvinceId,
                                                filterDistrictId,
                                                filterLLGId,
                                                filterWardId,
                                                selectedGender,
                                                searchedFirstName,
                                                searchedLastName,
                                                searchedElectorId,
                                                searchedClanId
                                            )
                                    }
                                }
                            }
                        }

                        else -> {
                            if(searchedClanId == 0) {
                                if(selectedGender == "Other") {
                                    selectedGender = ""
                                    return dataBaseInstance.votersListDao()
                                        .getSearchWiseWardRollFilteredVotersListLike(
                                            filterRegionId,
                                            filterProvinceId,
                                            filterDistrictId,
                                            filterLLGId,
                                            filterWardId,
                                            filterLocationId,
                                            selectedGender,
                                            searchedFirstName,
                                            searchedLastName,
                                            searchedElectorId
                                        )
                                }
                                else
                                {
                                    return dataBaseInstance.votersListDao()
                                        .getSearchWiseWardRollFilteredVotersList(
                                            filterRegionId,
                                            filterProvinceId,
                                            filterDistrictId,
                                            filterLLGId,
                                            filterWardId,
                                            filterLocationId,
                                            selectedGender,
                                            searchedFirstName,
                                            searchedLastName,
                                            searchedElectorId
                                        )
                                }
                            }
                            else {
                                if (searchedClanId == -1) {
                                    if(selectedGender == "Other") {
                                        selectedGender = ""
                                        return dataBaseInstance.votersListDao()
                                            .getSearchWiseWardRollFilteredVotersListClanNullLike(
                                                filterRegionId,
                                                filterProvinceId,
                                                filterDistrictId,
                                                filterLLGId,
                                                filterWardId,
                                                filterLocationId,
                                                selectedGender,
                                                searchedFirstName,
                                                searchedLastName,
                                                searchedElectorId
                                            )
                                    }
                                    else
                                    {
                                        return dataBaseInstance.votersListDao()
                                            .getSearchWiseWardRollFilteredVotersListClanNull(
                                                filterRegionId,
                                                filterProvinceId,
                                                filterDistrictId,
                                                filterLLGId,
                                                filterWardId,
                                                filterLocationId,
                                                selectedGender,
                                                searchedFirstName,
                                                searchedLastName,
                                                searchedElectorId
                                            )
                                    }
                                }
                                else
                                {
                                    if(selectedGender == "Other") {
                                        selectedGender = ""
                                        return dataBaseInstance.votersListDao()
                                            .getSearchWiseWardRollFilteredVotersListClanLike(
                                                filterRegionId,
                                                filterProvinceId,
                                                filterDistrictId,
                                                filterLLGId,
                                                filterWardId,
                                                filterLocationId,
                                                selectedGender,
                                                searchedFirstName,
                                                searchedLastName,
                                                searchedElectorId,
                                                searchedClanId
                                            )
                                    }
                                    else
                                    {
                                        return dataBaseInstance.votersListDao()
                                            .getSearchWiseWardRollFilteredVotersListClan(
                                                filterRegionId,
                                                filterProvinceId,
                                                filterDistrictId,
                                                filterLLGId,
                                                filterWardId,
                                                filterLocationId,
                                                selectedGender,
                                                searchedFirstName,
                                                searchedLastName,
                                                searchedElectorId,
                                                searchedClanId
                                            )
                                    }
                                }
                            }
                        }
                    }
                }else{
                    when (filterLocationId) {
                        0 -> {
                            if(searchedClanId == 0) {
                                return dataBaseInstance.votersListDao()
                                    .getWardRollWardFilteredVotersList(
                                        filterRegionId,
                                        filterProvinceId,
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId
                                    )
                            }
                            else {
                                if (searchedClanId == -1) {
                                    return dataBaseInstance.votersListDao()
                                        .getWardRollWardFilteredVotersListClanNull(
                                            filterRegionId,
                                            filterProvinceId,
                                            filterDistrictId,
                                            filterLLGId,
                                            filterWardId
                                        )
                                }
                                else
                                {
                                    return dataBaseInstance.votersListDao()
                                        .getWardRollWardFilteredVotersListClan(
                                            filterRegionId,
                                            filterProvinceId,
                                            filterDistrictId,
                                            filterLLGId,
                                            filterWardId,
                                            searchedClanId
                                        )
                                }
                            }
                        }

                        else -> {
                            if(searchedClanId == 0) {
                                return dataBaseInstance.votersListDao()
                                    .getWardRollFilteredVotersList(
                                        filterRegionId,
                                        filterProvinceId,
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId,
                                        filterLocationId
                                    )
                            }
                            else {
                                if (searchedClanId == -1) {
                                    return dataBaseInstance.votersListDao()
                                        .getWardRollFilteredVotersListClanNull(
                                            filterRegionId,
                                            filterProvinceId,
                                            filterDistrictId,
                                            filterLLGId,
                                            filterWardId,
                                            filterLocationId
                                        )
                                }
                                else
                                {
                                    return dataBaseInstance.votersListDao()
                                        .getWardRollFilteredVotersListClan(
                                            filterRegionId,
                                            filterProvinceId,
                                            filterDistrictId,
                                            filterLLGId,
                                            filterWardId,
                                            filterLocationId,
                                            searchedClanId
                                        )
                                }
                            }

                        }
                    }
                }

            } else {
                if(selectedGender.isNotEmpty() || searchedFirstName.isNotEmpty() || searchedLastName.isNotEmpty() || searchedElectorId.isNotEmpty()){
                    when (filterLocationId) {
                        0 -> {
                            if(searchedClanId == 0) {
                                if(selectedGender == "Other") {
                                    selectedGender = ""
                                    return dataBaseInstance.votersListDao()
                                        .getSearchWiseWardRollWardFilteredVotersListLike(
                                            filterRegionId,
                                            filterProvinceId,
                                            filterDistrictId,
                                            filterLLGId,
                                            filterWardId,
                                            statusFilterList,
                                            selectedGender,
                                            searchedFirstName,
                                            searchedLastName,
                                            searchedElectorId
                                        )
                                }
                                else
                                {
                                    return dataBaseInstance.votersListDao()
                                        .getSearchWiseWardRollWardFilteredVotersList(
                                            filterRegionId,
                                            filterProvinceId,
                                            filterDistrictId,
                                            filterLLGId,
                                            filterWardId,
                                            statusFilterList,
                                            selectedGender,
                                            searchedFirstName,
                                            searchedLastName,
                                            searchedElectorId
                                        )
                                }
                            }
                            else {
                                if (searchedClanId == -1) {
                                    if(selectedGender == "Other") {
                                        selectedGender = ""
                                        return dataBaseInstance.votersListDao()
                                            .getSearchWiseWardRollWardFilteredVotersListClanNullLike(
                                                filterRegionId,
                                                filterProvinceId,
                                                filterDistrictId,
                                                filterLLGId,
                                                filterWardId,
                                                statusFilterList,
                                                selectedGender,
                                                searchedFirstName,
                                                searchedLastName,
                                                searchedElectorId
                                            )
                                    }
                                    else
                                    {
                                        if(selectedGender == "Other") {
                                            selectedGender = ""
                                            return dataBaseInstance.votersListDao()
                                                .getSearchWiseWardRollWardFilteredVotersListClanNullLike(
                                                    filterRegionId,
                                                    filterProvinceId,
                                                    filterDistrictId,
                                                    filterLLGId,
                                                    filterWardId,
                                                    statusFilterList,
                                                    selectedGender,
                                                    searchedFirstName,
                                                    searchedLastName,
                                                    searchedElectorId
                                                )
                                        }
                                        else
                                        {
                                            return dataBaseInstance.votersListDao()
                                                .getSearchWiseWardRollWardFilteredVotersListClanNull(
                                                    filterRegionId,
                                                    filterProvinceId,
                                                    filterDistrictId,
                                                    filterLLGId,
                                                    filterWardId,
                                                    statusFilterList,
                                                    selectedGender,
                                                    searchedFirstName,
                                                    searchedLastName,
                                                    searchedElectorId
                                                )
                                        }
                                    }
                                }
                                else
                                {
                                    if(selectedGender == "Other") {
                                        selectedGender = ""
                                        return dataBaseInstance.votersListDao()
                                            .getSearchWiseWardRollWardFilteredVotersListClanLike(
                                                filterRegionId,
                                                filterProvinceId,
                                                filterDistrictId,
                                                filterLLGId,
                                                filterWardId,
                                                statusFilterList,
                                                selectedGender,
                                                searchedFirstName,
                                                searchedLastName,
                                                searchedElectorId,
                                                searchedClanId
                                            )
                                    }
                                    else
                                    {
                                        return dataBaseInstance.votersListDao()
                                            .getSearchWiseWardRollWardFilteredVotersListClan(
                                                filterRegionId,
                                                filterProvinceId,
                                                filterDistrictId,
                                                filterLLGId,
                                                filterWardId,
                                                statusFilterList,
                                                selectedGender,
                                                searchedFirstName,
                                                searchedLastName,
                                                searchedElectorId,
                                                searchedClanId
                                            )
                                    }
                                }
                            }
                        }
                        else -> {
                            if(searchedClanId == 0) {
                                if(selectedGender == "Other") {
                                    selectedGender = ""
                                    return dataBaseInstance.votersListDao()
                                        .getSearchWiseWardRollFilteredVotersListLike(
                                            filterRegionId,
                                            filterProvinceId,
                                            filterDistrictId,
                                            filterLLGId,
                                            filterWardId,
                                            filterLocationId,
                                            statusFilterList,
                                            selectedGender,
                                            searchedFirstName,
                                            searchedLastName,
                                            searchedElectorId
                                        )
                                }
                                else
                                {
                                    return dataBaseInstance.votersListDao()
                                        .getSearchWiseWardRollFilteredVotersList(
                                            filterRegionId,
                                            filterProvinceId,
                                            filterDistrictId,
                                            filterLLGId,
                                            filterWardId,
                                            filterLocationId,
                                            statusFilterList,
                                            selectedGender,
                                            searchedFirstName,
                                            searchedLastName,
                                            searchedElectorId
                                        )
                                }
                            }
                            else {
                                if (searchedClanId == -1) {
                                    if(selectedGender == "Other") {
                                        selectedGender = ""
                                        return dataBaseInstance.votersListDao()
                                            .getSearchWiseWardRollFilteredVotersListClanNullLike(
                                                filterRegionId,
                                                filterProvinceId,
                                                filterDistrictId,
                                                filterLLGId,
                                                filterWardId,
                                                filterLocationId,
                                                statusFilterList,
                                                selectedGender,
                                                searchedFirstName,
                                                searchedLastName,
                                                searchedElectorId
                                            )
                                    }
                                    else
                                    {
                                        return dataBaseInstance.votersListDao()
                                            .getSearchWiseWardRollFilteredVotersListClanNull(
                                                filterRegionId,
                                                filterProvinceId,
                                                filterDistrictId,
                                                filterLLGId,
                                                filterWardId,
                                                filterLocationId,
                                                statusFilterList,
                                                selectedGender,
                                                searchedFirstName,
                                                searchedLastName,
                                                searchedElectorId
                                            )
                                    }
                                }
                                else
                                {
                                    if(selectedGender == "Other") {
                                        selectedGender = ""
                                        return dataBaseInstance.votersListDao()
                                            .getSearchWiseWardRollFilteredVotersListClanLike(
                                                filterRegionId,
                                                filterProvinceId,
                                                filterDistrictId,
                                                filterLLGId,
                                                filterWardId,
                                                filterLocationId,
                                                statusFilterList,
                                                selectedGender,
                                                searchedFirstName,
                                                searchedLastName,
                                                searchedElectorId,
                                                searchedClanId
                                            )
                                    }
                                    else
                                    {
                                        return dataBaseInstance.votersListDao()
                                            .getSearchWiseWardRollFilteredVotersListClan(
                                                filterRegionId,
                                                filterProvinceId,
                                                filterDistrictId,
                                                filterLLGId,
                                                filterWardId,
                                                filterLocationId,
                                                statusFilterList,
                                                selectedGender,
                                                searchedFirstName,
                                                searchedLastName,
                                                searchedElectorId,
                                                searchedClanId
                                            )
                                    }
                                }
                            }
                        }
                    }
                }else{
                    when (filterLocationId) {
                        0 -> {
                            if(searchedClanId == 0) {
                                return dataBaseInstance.votersListDao()
                                    .getWardRollWardFilteredVotersList(
                                        filterRegionId,
                                        filterProvinceId,
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId,
                                        statusFilterList
                                    )
                            }
                            else {
                                if (searchedClanId == -1) {
                                    return dataBaseInstance.votersListDao()
                                        .getWardRollWardFilteredVotersListClanNull(
                                            filterRegionId,
                                            filterProvinceId,
                                            filterDistrictId,
                                            filterLLGId,
                                            filterWardId,
                                            statusFilterList
                                        )
                                }
                                else
                                {
                                    return dataBaseInstance.votersListDao()
                                        .getWardRollWardFilteredVotersListClan(
                                            filterRegionId,
                                            filterProvinceId,
                                            filterDistrictId,
                                            filterLLGId,
                                            filterWardId,
                                            statusFilterList,
                                            searchedClanId
                                        )
                                }
                            }

                        }

                        else -> {
                            if(searchedClanId == 0) {
                                return dataBaseInstance.votersListDao()
                                    .getWardRollFilteredVotersList(
                                        filterRegionId,
                                        filterProvinceId,
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId,
                                        filterLocationId,
                                        statusFilterList
                                    )
                            }
                            else
                            {
                                if (searchedClanId == -1) {
                                    return dataBaseInstance.votersListDao()
                                        .getWardRollFilteredVotersListClanNull(
                                            filterRegionId,
                                            filterProvinceId,
                                            filterDistrictId,
                                            filterLLGId,
                                            filterWardId,
                                            filterLocationId,
                                            statusFilterList
                                        )
                                }
                                else
                                {
                                    return dataBaseInstance.votersListDao()
                                        .getWardRollFilteredVotersListClan(
                                            filterRegionId,
                                            filterProvinceId,
                                            filterDistrictId,
                                            filterLLGId,
                                            filterWardId,
                                            filterLocationId,
                                            statusFilterList,
                                            searchedClanId
                                        )
                                }
                            }
                        }
                    }
                }
            }
        }else{
            if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                if (statusFilterList.isEmpty()) {
                    if(selectedGender.isNotEmpty() || searchedFirstName.isNotEmpty() || searchedLastName.isNotEmpty() || searchedElectorId.isNotEmpty()){
                        when (filterLocationId) {
                            0 -> {
                                return dataBaseInstance.votersListDao()
                                    .getSearchWiseWardRollWardFilteredVotersListForPNGEC(
                                        filterRegionId,
                                        filterProvinceId,
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId,
                                        selectedGender,
                                        searchedFirstName,
                                        searchedLastName,
                                        searchedElectorId
                                    )
                            }

                            else -> {
                                return dataBaseInstance.votersListDao()
                                    .getSearchWiseWardRollFilteredVotersListForPNGEC(
                                        filterRegionId,
                                        filterProvinceId,
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId,
                                        filterLocationId,
                                        selectedGender,
                                        searchedFirstName,
                                        searchedLastName,
                                        searchedElectorId
                                    )
                            }
                        }
                    }else{
                        when (filterLocationId) {
                            0 -> {
                                return dataBaseInstance.votersListDao()
                                    .getWardRollWardFilteredVotersListForPNGEC(
                                        filterRegionId,
                                        filterProvinceId,
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId
                                    )
                            }

                            else -> {
                                return dataBaseInstance.votersListDao()
                                    .getWardRollFilteredVotersListForPNGEC(
                                        filterRegionId,
                                        filterProvinceId,
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId,
                                        filterLocationId
                                    )
                            }
                        }
                    }
                } else {
                    if(selectedGender.isNotEmpty() || searchedFirstName.isNotEmpty() || searchedLastName.isNotEmpty() || searchedElectorId.isNotEmpty()){
                        when (filterLocationId) {
                            0 -> {
                                return dataBaseInstance.votersListDao()
                                    .getSearchWiseWardRollWardFilteredVotersListForPNGEC(
                                        filterRegionId,
                                        filterProvinceId,
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId,
                                        statusFilterList,
                                        selectedGender,
                                        searchedFirstName,
                                        searchedLastName,
                                        searchedElectorId
                                    )
                            }

                            else -> {
                                return dataBaseInstance.votersListDao()
                                    .getSearchWiseWardRollFilteredVotersListForPNGEC(
                                        filterRegionId,
                                        filterProvinceId,
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId,
                                        filterLocationId,
                                        statusFilterList,
                                        selectedGender,
                                        searchedFirstName,
                                        searchedLastName,
                                        searchedElectorId
                                    )
                            }
                        }
                    }else{
                        when (filterLocationId) {
                            0 -> {
                                return dataBaseInstance.votersListDao()
                                    .getWardRollWardFilteredVotersListForPNGEC(
                                        filterRegionId,
                                        filterProvinceId,
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId,
                                        statusFilterList
                                    )
                            }

                            else -> {
                                return dataBaseInstance.votersListDao()
                                    .getWardRollFilteredVotersListForPNGEC(
                                        filterRegionId,
                                        filterProvinceId,
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId,
                                        filterLocationId,
                                        statusFilterList
                                    )
                            }
                        }
                    }
                }
            } else {
                if (statusFilterList.isEmpty()) {
                    if(selectedGender.isNotEmpty() || searchedFirstName.isNotEmpty() || searchedLastName.isNotEmpty() || searchedElectorId.isNotEmpty()){
                        when (filterLocationId) {
                            0 -> {
                                return dataBaseInstance.votersListDao()
                                    .getSearchWiseFilteredVotersListForMKA(
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId,
                                        selectedGender,
                                        searchedFirstName,
                                        searchedLastName,
                                        searchedElectorId
                                    )
                            }

                            else -> {
                                return dataBaseInstance.votersListDao()
                                    .getSearchWiseFilteredVotersListForMKAWithLocation(
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId,
                                        filterLocationId,
                                        selectedGender,
                                        searchedFirstName,
                                        searchedLastName,
                                        searchedElectorId
                                    )
                            }
                        }
                    }else{
                        when (filterLocationId) {
                            0 -> {
                                return dataBaseInstance.votersListDao()
                                    .getFilteredVotersListForMKA(
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId
                                    )
                            }

                            else -> {
                                return dataBaseInstance.votersListDao()
                                    .getFilteredVotersListForMKAWithLocation(
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId,
                                        filterLocationId
                                    )
                            }
                        }
                    }
                } else {
                    if(selectedGender.isNotEmpty() || searchedFirstName.isNotEmpty() || searchedLastName.isNotEmpty() || searchedElectorId.isNotEmpty()){
                        when (filterLocationId) {
                            0 -> {
                                return dataBaseInstance.votersListDao()
                                    .getSearchWiseFilteredVotersListForMKA(
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId,
                                        statusFilterList,
                                        selectedGender,
                                        searchedFirstName,
                                        searchedLastName,
                                        searchedElectorId
                                    )
                            }

                            else -> {
                                return dataBaseInstance.votersListDao()
                                    .getSearchWiseFilteredVotersListForMKAWithLocation(
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId,
                                        filterLocationId,
                                        statusFilterList,
                                        selectedGender,
                                        searchedFirstName,
                                        searchedLastName,
                                        searchedElectorId
                                    )
                            }
                        }
                    }else{
                        when (filterLocationId) {
                            0 -> {
                                return dataBaseInstance.votersListDao()
                                    .getFilteredVotersListForMKA(
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId,
                                        statusFilterList
                                    )
                            }

                            else -> {
                                return dataBaseInstance.votersListDao()
                                    .getFilteredVotersListForMKAWithLocation(
                                        filterDistrictId,
                                        filterLLGId,
                                        filterWardId,
                                        filterLocationId,
                                        statusFilterList
                                    )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getLocationListFromDB() {
        launch(Dispatchers.Main) {
            val result: List<LocationList>? = withContext(Dispatchers.IO) {
                getLocationListQuery()
            }
            showLocationOptions(result)
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

    private fun getLocationListQuery(): List<LocationList>? {
        return dataBaseInstance.locationListDao()
            ?.getLocationListOfRegionAndProvinceAndDistrictAndLLGAndWard(
                filterRegionId,
                filterProvinceId,
                filterDistrictId,
                filterLLGId,
                filterWardId
            )
    }

    private fun getMKALocationListQuery(): List<MKAGeoListModel.GeoListData.LlG.Ward.Location>? {
        val districtResult: List<MKAGeoListData>? =
            dataBaseInstance.geoListDao()?.all ?: kotlin.run { arrayListOf() }
        val mkaDistrictArray = districtResult?.let { ArrayList<MKAGeoListData>(it) }
            ?: kotlin.run { arrayListOf() }

        val llgResult = mkaDistrictArray.filter { district ->
            district.districtId == filterDistrictId
        }
        var mkaLlgArray = ArrayList<MKAGeoListModel.GeoListData.LlG>()
        if (llgResult.isNotEmpty())
            mkaLlgArray = Gson().fromJson<ArrayList<MKAGeoListModel.GeoListData.LlG>>(
                llgResult.single().llGs ?: "",
                object : TypeToken<ArrayList<MKAGeoListModel.GeoListData.LlG>?>() {}.type
            ) ?: kotlin.run { arrayListOf() }
        val wardResult = mkaLlgArray.filter { llg ->
            llg.llgId == filterLLGId
        }
        val mkaWardArray = ArrayList<MKAGeoListModel.GeoListData.LlG.Ward>()
        if (wardResult.isNotEmpty())
            mkaWardArray.addAll(wardResult.single().wards ?: arrayListOf())
        val locationResult = mkaWardArray.filter { llg ->
            llg.wardId == filterWardId
        }
        mkaLocationArray.clear()
        if (locationResult.isNotEmpty())
            mkaLocationArray.addAll(locationResult.single().locations ?: arrayListOf())
        return mkaLocationArray
    }


    private var locationArray: ArrayList<LocationList>? = ArrayList()
    private fun showLocationOptions(result: List<LocationList>?) {

        locationArray = ArrayList(result!!)
        val locationList: Array<String> = getLocationList(locationArray!!)
        bindingFilterScreen?.etLocation?.tag = filterLocationSelectedPos
        showSingleSelectionDialog(
            locationList,
            bindingFilterScreen?.etLocation!!,
        )

    }

    private fun showMKALocationOptions() {
        val locationList: ArrayList<String> = ArrayList()
        locationList.add("All Locations")
        for (location in mkaLocationArray) {
            locationList.add(location.locationName ?: "")
        }
        bindingFilterScreen?.etLocation?.tag = filterLocationSelectedPos
        showSingleSelectionDialog(
            locationList.toArray(arrayOfNulls<String>(locationList.size)),
            bindingFilterScreen?.etLocation!!,
        )

    }

    //DJ
    private fun getClanList(clans: ArrayList<ClanListData>): Array<String> {
        val clanList: ArrayList<String> = ArrayList()
        clanList.add("All")
        clanList.add("Null")
        for (clan in clans) {
            clanList.add(clan.clanName)
        }
        return clanList.toArray(arrayOfNulls<String>(clanList.size))
    }

    private fun getLocationList(locations: ArrayList<LocationList>): Array<String> {

        val locationList: ArrayList<String> = ArrayList()
        locationList.add("All Locations")
        for (location in locations) {
            locationList.add(location.locationName)
        }
        return locationList.toArray(arrayOfNulls<String>(locationList.size))
    }

    private fun setWardRollVotersListAdapter(votersList: MutableList<VotersList>) {
        binding?.tvTitle?.text = workingRollTitle + " (" + votersList.size + ")"
        if (votersList.size > 0) {
            calculateVotersCount(votersList)
            binding?.tvNoVoters?.visibility = View.GONE
            binding?.rvWardRollVotersList?.visibility = View.VISIBLE

            wardRollVotersListAdapter = object :
                GenericAdapter<VotersList, ItemWardRollVoterBinding>(votersList) {
                override fun getLayoutId(): Int {
                    return R.layout.item_ward_roll_voter
                }

                @SuppressLint("SetTextI18n")
                override fun onBindData(
                    model: VotersList?,
                    position: Int,
                    dataBinding: ItemWardRollVoterBinding?
                ) {

                    if (model?.electorProfileImage != null
                        && model.electorProfileImage.isNotEmpty()
                    ) {
                        val image =
                            File(model.electorProfileImage)

                        if (image.exists()) {
                            Glide.with(this@WardRollVoterListActivity).load(Uri.fromFile(image))
                                .into(dataBinding?.ivVoterImage!!)
                        } else if (model.electorProfileImageUrl != null
                            && model.electorProfileImageUrl.isNotEmpty()
                        ) {

                            Glide.with(this@WardRollVoterListActivity)
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


                    } else

                        if (model?.status == "NEW" && (model.electorProfileImageUrl == null
                                    || model.electorProfileImageUrl.isEmpty())
                        ) {
                            if (model.electorProfileImage.isNotEmpty()) {
                                val image =
                                    File(model.electorProfileImage)
                                Glide.with(this@WardRollVoterListActivity).load(Uri.fromFile(image))
                                    .into(dataBinding?.ivVoterImage!!)
                            }
                        } else {

                            if (model?.electorProfileImageUrl != null
                                && model.electorProfileImageUrl.isNotEmpty()
                            ) {

                                Glide.with(this@WardRollVoterListActivity)
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

                    if (model?.syncStatus == 1) {
                        dataBinding?.rlSyncStatus?.visibility = View.VISIBLE
                        dataBinding?.tvSyncStatus?.text =
                            "Synced on: " + (if (!model.syncDateTime.isNullOrEmpty()) model.syncDateTime else getPostedDate(model.posted))
                    } else {
                        dataBinding?.rlSyncStatus?.visibility = View.GONE
                    }

                    dataBinding?.tvVoterName?.text = getVoterName(model)
                    if (model?.status == getString(R.string.new_status) && (model?.electorId == null || model?.electorId == 0L || model?.electorId > 1000000000000L))
                        dataBinding?.tvVoterId?.text = "ID - 0"
                    else
                        dataBinding?.tvVoterId?.text = "ID - " + (model?.electorId ?: 0)
                    dataBinding?.tvUpdatedDate?.text = getPostedDate(model?.posted)
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.ROLE,
                            ""
                        ) == ApiConstants.ROLE_DPO
                    ) {
                        dataBinding?.tvVoterLocation?.text = model?.locationName
                        dataBinding?.tvMkaDistrict?.visibility = View.GONE
                        dataBinding?.tvMkaLlg?.visibility = View.GONE
                        dataBinding?.tvMkaWard?.visibility = View.GONE
                        dataBinding?.tvVoterClan?.visibility = View.GONE
                        dataBinding?.tvMkaLocation?.visibility = View.GONE
                    } else {
                        if (filterType == ApiConstants.PNGEC_GEO_STRUCTURE) {
                            dataBinding?.tvVoterLocation?.text = model?.locationName
                            if (!model?.mkaDistrictName.isNullOrEmpty()) {
                                dataBinding?.tvMkaDistrict?.visibility = View.VISIBLE
                                dataBinding?.tvMkaDistrict?.text =
                                    "MKA District :" + model?.mkaDistrictName
                            } else {
                                dataBinding?.tvMkaDistrict?.visibility = View.GONE
                            }
                            if (!model?.mkaLlgName.isNullOrEmpty()) {
                                dataBinding?.tvMkaLlg?.visibility = View.VISIBLE
                                dataBinding?.tvMkaLlg?.text = "MKA Llg :" + model?.mkaLlgName
                            } else {
                                dataBinding?.tvMkaLlg?.visibility = View.GONE
                            }
                            if (!model?.mkaWardName.isNullOrEmpty()) {
                                dataBinding?.tvMkaWard?.visibility = View.VISIBLE
                                dataBinding?.tvMkaWard?.text = "MKA Ward :" + model?.mkaWardName
                            } else {
                                dataBinding?.tvMkaWard?.visibility = View.GONE
                            }
                            if (!model?.mkaLocationName.isNullOrEmpty()) {
                                dataBinding?.tvMkaLocation?.visibility = View.VISIBLE
                                dataBinding?.tvMkaLocation?.text =
                                    "MKA Location :" + model?.mkaLocationName
                            } else {
                                dataBinding?.tvMkaLocation?.visibility = View.GONE
                            }
                            if (!model?.mkaClanName.isNullOrEmpty()) {
                                dataBinding?.tvVoterClan?.visibility = View.VISIBLE
                                dataBinding?.tvVoterClan?.text = "MKA Clan :" + model?.mkaClanName
                            } else {
                                dataBinding?.tvVoterClan?.visibility = View.GONE
                            }
                        } else {
                            dataBinding?.tvVoterLocation?.text = model?.mkaLocationName
                            dataBinding?.tvMkaDistrict?.visibility = View.GONE
                            dataBinding?.tvMkaLlg?.visibility = View.GONE
                            dataBinding?.tvMkaWard?.visibility = View.GONE
                            dataBinding?.tvMkaLocation?.visibility = View.GONE
                            if (!model?.mkaClanName.isNullOrEmpty()) {
                                dataBinding?.tvVoterClan?.visibility = View.VISIBLE
                                dataBinding?.tvVoterClan?.text = "Clan :" + model?.mkaClanName
                            } else {
                                dataBinding?.tvVoterClan?.visibility = View.GONE
                            }
                        }
                    }
                    dataBinding?.tvVoterStatus?.text = model?.status

                    dataBinding?.ivVoterImage?.setOnClickListener {
                        if (model?.status == "OLD" ||  model?.status == "UPDATED"
                            ||  model?.status == "NEW" ||  model?.status == "TRANSFERRED IN"
                            ||  model?.status == "UNDER AGE") {
                            clickedVoterPosition = position
                            navigateToVoterDetailsScreen(model)
                        }
                    }

                    dataBinding?.tvVoterStatus?.setOnClickListener {
                        clickedVoterPosition = position
                        navigateToWorkingRollVoterUpdateStatus(model)
                    }

                    dataBinding?.ivEditVoter?.setOnClickListener {
                        clickedVoterPosition = position
                        navigateToEditVoterDetails(model)
                    }

                }
            }
            binding?.rvWardRollVotersList?.adapter = wardRollVotersListAdapter

        } else {
            binding?.tvNoVoters?.visibility = View.VISIBLE
            binding?.rvWardRollVotersList?.visibility = View.GONE
            maleVotersCount = 0
            femaleVotersCount = 0
            otherVotersCount = 0
            totalVotersCount = 0
        }


    }

    private fun calculateVotersCount(votersList: MutableList<VotersList>) {

        maleVotersCount = 0
        femaleVotersCount = 0
        otherVotersCount = 0
        totalVotersCount = 0

        voterWithoutPhoto = 0
        voterWithPhoto = 0

        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                for (voter in votersList) {
                    when (voter.gender) {
                        "M" -> maleVotersCount += 1
                        "F" -> femaleVotersCount += 1
                        else -> otherVotersCount += 1
                    }

                    if(!voter.electorProfileImageUrl.isNullOrEmpty() || !voter.electorProfileImage.isNullOrEmpty())
                    {
                      voterWithPhoto += 1
                    } else if(voter.electorProfileImageUrl.isNullOrEmpty() && voter.electorProfileImage.isNullOrEmpty()){
                        voterWithoutPhoto += 1
                    }
                }

            }

            totalVotersCount = maleVotersCount + femaleVotersCount + otherVotersCount

        }


    }


    private fun getVoterName(model: VotersList?): String {
        var lastNameOfVoter = ""
        var firstNameOfVoter = ""
        if (!model?.lName.isNullOrEmpty())
            lastNameOfVoter = model?.lName!!
        if (!model?.fName.isNullOrEmpty())
            firstNameOfVoter = model?.fName!!
        val voterName = "$lastNameOfVoter $firstNameOfVoter"
        val voterNameWithGender =
            voterName + " (" + CommonMethods.getVoterDetails(model?.gender) + ")"
        return voterNameWithGender.trim()
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
        //jds MKA
        if(CanEdit.isEmpty()) {
            val intent = Intent(this, VoterDetailsActivity::class.java)
            intent.putExtra(ApiConstants.VOTER_DETAILS, model)
            intent.putExtra(ApiConstants.FILTER_TYPE, filterType)
            intent.putExtra(ApiConstants.FROM_REJECT_LIST, false)
            startActivityForResult(intent, ApiConstants.VOTER_STATUS_UPDATE)
        }
    }

    private fun navigateToEditVoterDetails(model: VotersList?) {
        val intent = Intent(this, EditVoterDetailsActivity::class.java)
        intent.putExtra(ApiConstants.VOTER_DETAILS, model)
        intent.putExtra(ApiConstants.VOTER_IMAGE, "")
        startActivityForResult(intent, ApiConstants.VOTER_STATUS_UPDATE)
    }

    private fun navigateToWorkingRollVoterUpdateStatus(model: VotersList?) {
        if(CanEdit.isEmpty()) {
            val intent = Intent(this, WorkingRollUpdateStatusActivity::class.java)
            intent.putExtra(ApiConstants.VOTER_DETAILS, model)
            intent.putExtra(ApiConstants.FILTER_TYPE, filterType)
            startActivityForResult(intent, ApiConstants.VOTER_STATUS_UPDATE)
        }
    }

}
