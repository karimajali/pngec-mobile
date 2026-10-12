package com.pngelectoral.ui.activity

import ApiConstants
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.pngelectoral.BuildConfig
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityHomeBinding
import com.pngelectoral.databinding.DialogSyncSettingsPasswordBinding
import com.pngelectoral.databinding.ItemHomeBinding
import com.pngelectoral.datasync.DownloadNewVotersSyncService
import com.pngelectoral.ui.adapter.GenericAdapter
import com.pngelectoral.ui.model.DPOUserData
import com.pngelectoral.ui.model.LocationList
import com.pngelectoral.ui.model.VotersList
import com.pngelectoral.ui.viewModel.HomeViewModel
import com.pngelectoral.utility.CommonMethods
import kotlinx.coroutines.*
import okhttp3.internal.wait
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.okButton
import java.util.*
import kotlin.coroutines.CoroutineContext


class HomeActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityHomeBinding? = null
    private var homeViewModel: HomeViewModel? = null
    lateinit var loggedInDPOUserData: DPOUserData
    lateinit var dataBaseInstance: AppDatabase
    private var homePageMenu = mutableListOf<String>()
    private var homeMenuAdapter: GenericAdapter<String, ItemHomeBinding>? = null
    var provinceName = ""

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_home
        )
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        binding?.homeViewModel = homeViewModel

        job = Job()

        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )
        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase
        provinceName = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_PROVINCE, "")

        setHeaderAndFooterData()
        setHomePageAdapter()
        addObservers()
    }

    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
    }

    private fun setHeaderAndFooterData() {

        val year = Calendar.getInstance().get(Calendar.YEAR)
        val districtName = securedSharedPreferenceWrapper.getString(
            ApiConstants.DPO_GEO_DISTRICT,
            ""
        )
        val dpoName = securedSharedPreferenceWrapper.getString(
            ApiConstants.DPO_NAME,
            ""
        )
        val version = packageManager.getPackageInfo(packageName, 0).versionName

        val headerTitle = "$year - $districtName District Roll Update"
        binding?.tvTitle?.text = headerTitle

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

        var environment = ""

        if (BuildConfig.FLAVOR == "training") {
            environment = "training"
        } else if (BuildConfig.FLAVOR == "production") {
            environment = "production"
        } else if (BuildConfig.FLAVOR == "dev") {
            environment = "training"
        }


        val tvBottomDataTitle: String = if (wardName.isEmpty())
            "Version: $version | District: $districtName | DPO: $dpoName | ENV: $environment"
        else {
            val wardNameDisplay = "$districtName/$llgName/$wardName"
            "Version: $version | Ward: $wardNameDisplay | DPO: $dpoName | ENV: $environment"
        }
        binding?.tvBottomData?.text = tvBottomDataTitle

//        launch(Dispatchers.Main) {
//            val result: List<VotersList>? = withContext(Dispatchers.IO) {
//                getAllVotersList()
//            }
//            binding?.tvBottomData?.text = result!!.size.toString()
//        }

    }

    private fun getAllVotersList(): List<VotersList>? {
        return dataBaseInstance.votersListDao().getAllVotersList(
            loggedInDPOUserData.region.toInt(),
            loggedInDPOUserData.province.toInt(),
            loggedInDPOUserData.district.toInt()
        )
    }

    private fun setHomePageAdapter() {
        when (securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "")){
            ApiConstants.ROLE_DPO -> {
                homePageMenu.add(getString(R.string.working_roll))
                homePageMenu.add(getString(R.string.roll_look_up))
                homePageMenu.add(getString(R.string.new_enrolment))
                homePageMenu.add(getString(R.string.transfer_in_enrolment))
                homePageMenu.add(getString(R.string.provisional_enrolment))
                homePageMenu.add(getString(R.string.sync))
                homePageMenu.add(getString(R.string.settings))
                homePageMenu.add(getString(R.string.sign_out))
            }
            ApiConstants.ROLE_MKA -> {
                homePageMenu.add(getString(R.string.working_roll_pngec))
                homePageMenu.add(getString(R.string.working_roll_mka))
                homePageMenu.add(getString(R.string.roll_look_up))
                homePageMenu.add(getString(R.string.new_enrolment))
                homePageMenu.add(getString(R.string.transfer_in_enrolment))
                homePageMenu.add(getString(R.string.provisional_enrolment))
//                homePageMenu.add(getString(R.string.sync_pngec))
                homePageMenu.add(getString(R.string.sync_mka))
                homePageMenu.add(getString(R.string.settings))
                homePageMenu.add(getString(R.string.sign_out))
            }
            else -> {
                homePageMenu.add(getString(R.string.working_roll))
                homePageMenu.add(getString(R.string.roll_look_up))
                homePageMenu.add(getString(R.string.new_enrolment))
                homePageMenu.add(getString(R.string.transfer_in_enrolment))
                homePageMenu.add(getString(R.string.provisional_enrolment))
                homePageMenu.add(getString(R.string.sync))
                homePageMenu.add(getString(R.string.settings))
                homePageMenu.add(getString(R.string.sign_out))
            }
        }

        homeMenuAdapter = object :
            GenericAdapter<String, ItemHomeBinding>(homePageMenu) {
            override fun getLayoutId(): Int {
                return R.layout.item_home
            }

            override fun onBindData(
                model: String?,
                position: Int,
                dataBinding: ItemHomeBinding?
            ) {
                dataBinding?.tvMenuTitle?.text = model
                dataBinding?.cvParent?.setOnClickListener {
                    navigateToClickedModule(model)
                }

            }
        }
        binding?.rvHomeMenu?.adapter = homeMenuAdapter
    }

    private fun navigateToClickedModule(menuItem: String?) {

        when (menuItem) {
            getString(R.string.working_roll) -> {
                var objHomeActivity = this
                launch(Dispatchers.Main) {
                    val result: List<VotersList>? = withContext(Dispatchers.IO) {
                        validateRollAndSync()
                    }

                    if (!result.isNullOrEmpty()) {
                        alert(getString(R.string.gender_clan_null_message)) {
                            okButton {
                                if (provinceName == "NATIONAL CAPITAL DISTRICT") {
                                    if (securedSharedPreferenceWrapper.getString(
                                            ApiConstants.DPO_WORKING_ROLL_LOCATION,
                                            ""
                                        ).isNotEmpty()
                                    ) {
                                        startActivity(
                                            Intent(
                                                objHomeActivity,
                                                WardRollVoterListActivity::class.java
                                            )
                                        )
                                    } else {
                                        showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_location_using_working_roll_filter_in_settings_menu))
                                    }
                                } else {
                                    if (securedSharedPreferenceWrapper.getString(
                                            ApiConstants.DPO_WORKING_ROLL_WARD,
                                            ""
                                        ).isNotEmpty()
                                    ) {
                                        startActivity(
                                            Intent(
                                                objHomeActivity,
                                                WardRollVoterListActivity::class.java
                                            )
                                        )
                                    } else {
                                        showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
                                    }
                                }
                            }
                        }.show()
                    }
                    else {

                        if (provinceName == "NATIONAL CAPITAL DISTRICT") {
                            if (securedSharedPreferenceWrapper.getString(
                                    ApiConstants.DPO_WORKING_ROLL_LOCATION,
                                    ""
                                ).isNotEmpty()
                            ) {
                                startActivity(
                                    Intent(
                                        objHomeActivity,
                                        WardRollVoterListActivity::class.java
                                    )
                                )
                            } else {
                                showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_location_using_working_roll_filter_in_settings_menu))
                            }
                        } else {
                            if (securedSharedPreferenceWrapper.getString(
                                    ApiConstants.DPO_WORKING_ROLL_WARD,
                                    ""
                                ).isNotEmpty()
                            ) {
                                startActivity(
                                    Intent(
                                        objHomeActivity,
                                        WardRollVoterListActivity::class.java
                                    )
                                )
                            } else {
                                showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
                            }
                        }
                    }
                }
            }

            getString(R.string.working_roll_pngec) -> {
                val intent = Intent(this, WardRollVoterListActivity::class.java)
                /*if (provinceName == "NATIONAL CAPITAL DISTRICT") {
                    intent.putExtra(ApiConstants.FILTER_TYPE,ApiConstants.PNGEC_GEO_STRUCTURE)
                    startActivity(intent)
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_LOCATION,
                            ""
                        ).isNotEmpty()
                    ){
                        intent.putExtra(ApiConstants.FILTER_TYPE,ApiConstants.PNGEC_GEO_STRUCTURE)
                        startActivity(intent)
                    }
                    else
                        showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_location_using_working_roll_filter_in_settings_menu))
                } else {
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_WARD,
                            ""
                        ).isNotEmpty()
                    ){
                        intent.putExtra(ApiConstants.FILTER_TYPE,ApiConstants.PNGEC_GEO_STRUCTURE)
                        startActivity(intent)
                    }
                    else
                        showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
                }*/
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_WARD,
                        ""
                    ).isNotEmpty()
                ){
                    intent.putExtra(ApiConstants.FILTER_TYPE,ApiConstants.PNGEC_GEO_STRUCTURE)
                    intent.putExtra("CanEdit","NO")
                    startActivity(intent)
                }
                else
                    showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
            }

            getString(R.string.working_roll_mka) -> {
                val intent = Intent(this, WardRollVoterListActivity::class.java)
                /*if (provinceName == "NATIONAL CAPITAL DISTRICT") {
                    intent.putExtra(ApiConstants.FILTER_TYPE,ApiConstants.PNGEC_GEO_STRUCTURE)
                    startActivity(intent)
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_MKA_LOCATION,
                            ""
                        ).isNotEmpty()
                    ){
                        intent.putExtra(ApiConstants.FILTER_TYPE,ApiConstants.MKA_GEO_STRUCTURE)
                        startActivity(intent)
                    }
                    else
                        showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_location_using_working_roll_filter_in_settings_menu))
                } else {
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_WARD,
                            ""
                        ).isNotEmpty()
                    ){
                        intent.putExtra(ApiConstants.FILTER_TYPE,ApiConstants.MKA_GEO_STRUCTURE)
                        startActivity(intent)
                    }
                    else
                        showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
                }*/
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_WARD,
                        ""
                    ).isNotEmpty()
                ){
                    intent.putExtra(ApiConstants.FILTER_TYPE,ApiConstants.MKA_GEO_STRUCTURE)
                    startActivity(intent)
                }
                else
                    showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
            }

            getString(R.string.roll_look_up) -> {
                startActivity(Intent(this, RollLookUpActivity::class.java))
            }

            getString(R.string.new_enrolment) -> {
                if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
                    if (provinceName == "NATIONAL CAPITAL DISTRICT") {
                        if (securedSharedPreferenceWrapper.getString(
                                ApiConstants.DPO_WORKING_ROLL_LOCATION,
                                ""
                            )
                                .isNotEmpty()
                        )
                            startActivity(Intent(this, NewRegWardSelectionActivity::class.java))
                        else
                            showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_location_using_working_roll_filter_in_settings_menu))
                    } else {
                        if (securedSharedPreferenceWrapper.getString(
                                ApiConstants.DPO_WORKING_ROLL_WARD,
                                ""
                            )
                                .isNotEmpty()
                        )
                            startActivity(Intent(this, NewRegWardSelectionActivity::class.java))
                        else
                            showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
                    }
                }else{
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_WARD,
                            ""
                        )
                            .isNotEmpty()
                    )
                        startActivity(Intent(this, NewRegWardSelectionActivity::class.java))
                    else
                        showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
                }

            }

            getString(R.string.transfer_in_enrolment) -> {
                if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
                    if (provinceName == "NATIONAL CAPITAL DISTRICT") {
                        if (securedSharedPreferenceWrapper.getString(
                                ApiConstants.DPO_WORKING_ROLL_LOCATION,
                                ""
                            )
                                .isNotEmpty()
                        )
                            startActivity(Intent(this, TransferInActivity::class.java))
                        else
                            showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_location_using_working_roll_filter_in_settings_menu))
                    } else {
                        if (securedSharedPreferenceWrapper.getString(
                                ApiConstants.DPO_WORKING_ROLL_WARD,
                                ""
                            )
                                .isNotEmpty()
                        )
                            startActivity(Intent(this, TransferInActivity::class.java))
                        else
                            showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
                    }
                }else{
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_WARD,
                            ""
                        )
                            .isNotEmpty()
                    )
                        startActivity(Intent(this, TransferInActivity::class.java))
                    else
                        showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
                }
            }

            getString(R.string.provisional_enrolment) -> {
                if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
                    if (provinceName == "NATIONAL CAPITAL DISTRICT") {
                        if (securedSharedPreferenceWrapper.getString(
                                ApiConstants.DPO_WORKING_ROLL_LOCATION,
                                ""
                            )
                                .isNotEmpty()
                        )
                            startActivity(Intent(this, ProvisionalRegWardSelectionActivity::class.java))
                        else
                            showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_location_using_working_roll_filter_in_settings_menu))
                    } else {
                        if (securedSharedPreferenceWrapper.getString(
                                ApiConstants.DPO_WORKING_ROLL_WARD,
                                ""
                            )
                                .isNotEmpty()
                        )
                            startActivity(Intent(this, ProvisionalRegWardSelectionActivity::class.java))
                        else
                            showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
                    }
                }else{
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_WARD,
                            ""
                        )
                            .isNotEmpty()
                    )
                        startActivity(Intent(this, ProvisionalRegWardSelectionActivity::class.java))
                    else
                        showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
                }

            }

            getString(R.string.sync) -> {

                launch(Dispatchers.Main) {
                    val result: List<VotersList>? = withContext(Dispatchers.IO) {
                        validateRollAndSync()
                    }

                    if (!result.isNullOrEmpty()) {
                        alert(getString(R.string.gender_clan_null_message_sync)) {
                            okButton {
                            }
                        }.show()
                    } else {
                        if (provinceName == "NATIONAL CAPITAL DISTRICT") {
                            if (securedSharedPreferenceWrapper.getString(
                                    ApiConstants.DPO_WORKING_ROLL_LOCATION,
                                    ""
                                )
                                    .isNotEmpty()
                            )
                                showSyncAndSettingsPasswordOption(
                                    true,
                                    ApiConstants.PNGEC_GEO_STRUCTURE
                                )
                            else
                                showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_location_using_working_roll_filter_in_settings_menu))
                        } else {
                            if (securedSharedPreferenceWrapper.getString(
                                    ApiConstants.DPO_WORKING_ROLL_WARD,
                                    ""
                                )
                                    .isNotEmpty()
                            )
                                showSyncAndSettingsPasswordOption(
                                    true,
                                    ApiConstants.PNGEC_GEO_STRUCTURE
                                )
                            else
                                showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
                        }
                    }
                }
            }

            getString(R.string.sync_pngec) -> {
                /*if (provinceName == "NATIONAL CAPITAL DISTRICT") {
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_LOCATION,
                            ""
                        )
                            .isNotEmpty()
                    )
                        showSyncAndSettingsPasswordOption(true, ApiConstants.PNGEC_GEO_STRUCTURE)
                    else
                        showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_location_using_working_roll_filter_in_settings_menu))
                } else {
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_WARD,
                            ""
                        )
                            .isNotEmpty()
                    )
                        showSyncAndSettingsPasswordOption(true, ApiConstants.PNGEC_GEO_STRUCTURE)
                    else
                        showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
                }*/
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_WARD,
                        ""
                    )
                        .isNotEmpty()
                )
                    showSyncAndSettingsPasswordOption(true, ApiConstants.PNGEC_GEO_STRUCTURE)
                else
                    showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
            }

            getString(R.string.sync_mka) -> {
                /*if (provinceName == "NATIONAL CAPITAL DISTRICT") {
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_MKA_LOCATION,
                            ""
                        )
                            .isNotEmpty()
                    )
                        showSyncAndSettingsPasswordOption(true, ApiConstants.MKA_GEO_STRUCTURE)
                    else
                        showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_location_using_working_roll_filter_in_settings_menu))
                } else {
                    if (securedSharedPreferenceWrapper.getString(
                            ApiConstants.DPO_WORKING_ROLL_MKA_WARD,
                            ""
                        )
                            .isNotEmpty()
                    )
                        showSyncAndSettingsPasswordOption(true, ApiConstants.MKA_GEO_STRUCTURE)
                    else
                        showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
                }*/
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_MKA_WARD,
                        ""
                    )
                        .isNotEmpty()
                )
                    showSyncAndSettingsPasswordOption(true, ApiConstants.MKA_GEO_STRUCTURE)
                else
                    showSelectWorkingRollFilterAlert(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
            }

            getString(R.string.settings) -> {
                showSyncAndSettingsPasswordOption(false, ApiConstants.PNGEC_GEO_STRUCTURE)
            }

            getString(R.string.sign_out) -> {
//                checkVotersToSync()
                alert("Are you sure you want to logout or stay connected offline?") {
                    okButton {
                        checkNetWorkAndCallLogoutAPI()
                    }
                    cancelButton {

                    }
                }.show()
            }
        }

    }

    private fun showSelectWorkingRollFilterAlert(message: String) {
        alert(message) {
            okButton {
            }
        }.show()
    }


    private var mSyncAndSettingsAlertDialog: AlertDialog? = null
    private var bindingSyncSettingsPassword: DialogSyncSettingsPasswordBinding? = null

    private fun showSyncAndSettingsPasswordOption(isSync: Boolean, filterType: String) {

        val builder = AlertDialog.Builder(this)
        bindingSyncSettingsPassword = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_sync_settings_password, null, false
        ) as DialogSyncSettingsPasswordBinding
        builder.setView(bindingSyncSettingsPassword?.root)
        mSyncAndSettingsAlertDialog = builder.create()

        if (isSync) {
            bindingSyncSettingsPassword?.tvHeaderMessage?.text =
                getString(R.string.password_to_access_sync)
        } else {
            bindingSyncSettingsPassword?.tvHeaderMessage?.text =
                getString(R.string.password_to_access_settings)
        }

        bindingSyncSettingsPassword?.btnSubmit?.setOnClickListener {
            val password = bindingSyncSettingsPassword?.etPassword?.text.toString().trim()
            if (isValidPassword(password, isSync)) {
                mSyncAndSettingsAlertDialog!!.dismiss()
                if (isSync)
                    navigateToSyncScreen(filterType)
                else
                    navigateToSettingsScreen()
            } else {
                showValidationMessage(getString(R.string.error_please_enter_valid_password))
            }
        }

        bindingSyncSettingsPassword?.btnCancel?.setOnClickListener {
            mSyncAndSettingsAlertDialog!!.dismiss()
        }

        mSyncAndSettingsAlertDialog?.show()
    }

    private fun isValidPassword(password: String, isSync: Boolean): Boolean {
        return if (password.isEmpty())
            false
        else if (isSync && password == securedSharedPreferenceWrapper.getString(
                ApiConstants.DC_PASSWORD,
                ApiConstants.DC_PASSWORD_DEFAULT
            )
        )
            true
        else !(isSync || password != securedSharedPreferenceWrapper.getString(
            ApiConstants.DC_PASSWORD,
            ApiConstants.DC_PASSWORD_DEFAULT
        ))

    }



    private fun navigateToSettingsScreen() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSyncScreen(filterType: String) {
        showSyncSelectionOptions(filterType)
    }

    private fun showSyncSelectionOptions(filterType: String) {
        val syncOptions: ArrayList<String> = ArrayList()
        syncOptions.add("Data Only")
        //syncOptions.add("Data with Images")
        syncOptions.add("Data and Images")
        val syncOptionsList = syncOptions.toArray(arrayOfNulls<String>(syncOptions.size))

        val mBuilder =
            AlertDialog.Builder(this)
        val selectionValue = -1
        mBuilder.setTitle("Choose Sync Option")

        mBuilder.setSingleChoiceItems(
            syncOptionsList,
            selectionValue
        ) { dialogInterface, clickItem ->
            when (clickItem) {
                0 -> {
                    val intent = Intent(this, VotersDataOnlySyncActivity::class.java)
                    intent.putExtra(ApiConstants.SYNC_DATA_OPTION, "")
                    intent.putExtra(ApiConstants.FILTER_TYPE,filterType)
                    startActivity(intent)
                }
//                1 -> {
//                    val intent = Intent(this, VotersSyncWithDataAndImageActivity::class.java)
//                    intent.putExtra(ApiConstants.SYNC_DATA_OPTION, "")
//                    intent.putExtra(ApiConstants.FILTER_TYPE,filterType)
//                    startActivity(intent)
//                }
                1 -> {
                    val intent = Intent(this, VotersSyncWithDataAndImageActivity::class.java)
                    intent.putExtra(ApiConstants.SYNC_DATA_OPTION, "dataImages")
                    intent.putExtra(ApiConstants.FILTER_TYPE,filterType)
                    startActivity(intent)
                }
            }
            dialogInterface.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun showValidationMessage(message: String) {
        alert(message) {
            okButton { }
        }.show()
    }
    private fun checkVotersToSync() {
        launch(Dispatchers.Main) {
            val result: List<VotersList>? = withContext(Dispatchers.IO) {
                getVotersListToSync()
            }
            proceedToLogoutFlow(result)
        }
    }

    private fun getVotersListToSync(): List<VotersList>? {
        return dataBaseInstance.votersListDao().getDPOWiseUpdatedVotersListToSync(
            securedSharedPreferenceWrapper.getString(
                ApiConstants.DPO_NAME,
                ""
            )
        )
    }

    private fun proceedToLogoutFlow(result: List<VotersList>?) {

        if (result!!.isEmpty()) {

            alert("Are you sure you want to logout or stay connected offline?") {
                okButton {
                    checkNetWorkAndCallLogoutAPI()
                }
                cancelButton {

                }
            }.show()

        } else {
            alert("The voters which are updated yet to sync with server. Please sync the data with server before you logout!") {
                okButton {
                }
            }.show()
        }

    }

    private fun checkNetWorkAndCallLogoutAPI() {
        if (CommonMethods.checkInternetConnection(this)) {
            CommonMethods.showProgress(this)

            val logoutObject = JsonObject()
            logoutObject.addProperty(
                "UserId", securedSharedPreferenceWrapper.getString(ApiConstants.UserId, "")
            )
            homeViewModel?.callLogoutAPI(
                this,
                logoutObject
            )
        } else {
            stopAnyRunningService()
        }
    }

    private fun addObservers() {

        homeViewModel?.logoutResponse?.observe(this) {
            if (it != null && it.status!!) {
                stopAnyRunningService()
            } else {
                CommonMethods.hideProgress()
                alert(it.message ?: "") {
                    okButton { }
                }.show()
            }
        }


    }

    private fun stopAnyRunningService() {
        if (isMyServiceRunning(DownloadNewVotersSyncService::class.java)) {
            securedSharedPreferenceWrapper.putInt(ApiConstants.LOCATION_PAGE_NO, 1)
            val intent = Intent(ApiConstants.STOP_SERVICE)
            sendBroadcast(intent)
        }
        Handler(mainLooper).postDelayed({
            navigateToLogin()
        }, 1000)
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    private fun navigateToLogin() {
        securedSharedPreferenceWrapper.putInt(
            ApiConstants.PRELIMINARY_ROLL_LLG_ID,
            0
        )
        securedSharedPreferenceWrapper.putInt(
            ApiConstants.PRELIMINARY_ROLL_WARD_ID,
            0
        )
        securedSharedPreferenceWrapper.putInt(
            ApiConstants.PRELIMINARY_ROLL_LOCATION_ID,
            0
        )

        securedSharedPreferenceWrapper.putString(
            ApiConstants.PRELIMINARY_ROLL_LLG_NAME,
            ""
        )
        securedSharedPreferenceWrapper.putString(
            ApiConstants.PRELIMINARY_ROLL_WARD_NAME,
            ""
        )
        securedSharedPreferenceWrapper.putString(
            ApiConstants.PRELIMINARY_ROLL_LOCATION_NAME,
            ""
        )
        CommonMethods.hideProgress()
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }

    private fun validateRollAndSync(): List<VotersList>? {
        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )

        if (securedSharedPreferenceWrapper.getString(
                ApiConstants.ROLE,
                ""
            ) == ApiConstants.ROLE_MKA
        ) {
            return dataBaseInstance.votersListDao()
                .getMkaWardRollWardFilteredVotersListIsClanGenderNull(
                    loggedInDPOUserData.region.toInt(),
                    loggedInDPOUserData.province.toInt(),
                    loggedInDPOUserData.district.toInt(),
                    securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_LLG_ID,
                        0
                    ),
                    securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_MKA_WARD_ID,
                        0
                    )
                )
        } else {
            var provinceName =
                securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_PROVINCE, "")

            if (provinceName == "NATIONAL CAPITAL DISTRICT") {
                return dataBaseInstance.votersListDao()
                    .getLocationRollLocationFilteredVotersListIsClanGenderNull(
                        loggedInDPOUserData.region.toInt(),
                        loggedInDPOUserData.province.toInt(),
                        loggedInDPOUserData.district.toInt(),
                        securedSharedPreferenceWrapper.getInt(
                            ApiConstants.DPO_WORKING_ROLL_LLG_ID,
                            0
                        ),
                        securedSharedPreferenceWrapper.getInt(
                            ApiConstants.DPO_WORKING_ROLL_WARD_ID,
                            0
                        ),
                        securedSharedPreferenceWrapper.getInt(
                            ApiConstants.DPO_WORKING_ROLL_LOCATION_ID,
                            0
                        )
                    )
            } else {
                return dataBaseInstance.votersListDao()
                    .getWardRollWardFilteredVotersListIsClanGenderNull(
                        loggedInDPOUserData.region.toInt(),
                        loggedInDPOUserData.province.toInt(),
                        loggedInDPOUserData.district.toInt(),
                        securedSharedPreferenceWrapper.getInt(
                            ApiConstants.DPO_WORKING_ROLL_LLG_ID,
                            0
                        ),
                        securedSharedPreferenceWrapper.getInt(
                            ApiConstants.DPO_WORKING_ROLL_WARD_ID,
                            0
                        )
                    )
            }
        }
    }
}
