package com.pngelectoral.ui.activity

import ApiConstants
import android.app.Activity
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.*
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.ClipboardManager
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.pngelectoral.LocationHelper2
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityLoginBinding
import com.pngelectoral.databinding.ForgotPasswordBinding
import com.pngelectoral.databinding.GetDeviceIdBinding
import com.pngelectoral.databinding.LayoutLoaderDownloadProgressBarBinding
import com.pngelectoral.datasync.DownloadGeoStructureDataSyncService
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.retrofit.*
import com.pngelectoral.ui.viewModel.LoginViewModel
import com.pngelectoral.utility.CommonMethods
import com.pngelectoral.utility.extension.isEmailValid
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import kotlin.coroutines.CoroutineContext

class LoginActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityLoginBinding? = null
    private var loginViewModel: LoginViewModel? = null
    private var passwordState = true
    private var email = ""
    private var password = ""

    lateinit var dataBaseInstance: AppDatabase
    private var locationHelper: LocationHelper2? = null
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_login
        )
        loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)
        binding?.loginViewModel = loginViewModel
        locationHelper = LocationHelper2(this)
        job = Job()

        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase

        val version = packageManager.getPackageInfo(packageName, 0).versionName
        binding?.tvVersionName?.text = "Version: $version"

        registerReceiver(
            allPageGeoStructureDataDownloadReceiver,
            IntentFilter(ApiConstants.GEO_STRUCTURE_DATA_COMPLETED_BROADCAST)
        )

        registerReceiver(
            singlePageGeoStructureDataDownloadedReceiver,
            IntentFilter(ApiConstants.GEO_STRUCTURE_SINGLE_PAGE_DATA_COMPLETED)
        )

        setClickListeners()
        addObservers()
    }

    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
        unregisterReceiver(allPageGeoStructureDataDownloadReceiver)
        unregisterReceiver(singlePageGeoStructureDataDownloadedReceiver)
    }


    private fun setClickListeners() {

        ivPasswordToggle.setOnClickListener {

            if (passwordState) {
                etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                passwordState = !passwordState
                ivPasswordToggle.setImageResource(R.drawable.view)
            } else {
                etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                passwordState = !passwordState
                ivPasswordToggle.setImageResource(R.drawable.unview)
            }

        }

        tvForgotPassword.setOnClickListener {
            showForgotPasswordDialog()
        }

        btnLogin.setOnClickListener {

            val emailAddress = binding?.etEmail?.text.toString().trim()
            val passwordValue = binding?.etPassword?.text.toString().trim()

            if (validateLogin(emailAddress, passwordValue)) {
                CommonMethods.hideKeypad(this, it)
                email = emailAddress
                password = passwordValue
                if (CommonMethods.checkInternetConnection(application)) {
                    callGeneralSettingsApi()
                } else {
                    callLoginApi(true)
                }
            }

        }

        tvGetDeviceId.setOnClickListener {
            showDeviceIdDialog()
        }

    }

    private var bindingGetDeviceId: GetDeviceIdBinding? = null

    private fun showDeviceIdDialog() {

        val builder = AlertDialog.Builder(this)
        bindingGetDeviceId = DataBindingUtil.inflate(
            LayoutInflater.from(this@LoginActivity),
            R.layout.get_device_id, null, false
        ) as GetDeviceIdBinding
        builder.setView(bindingGetDeviceId?.root)
        mAlertDialog = builder.create()

        bindingGetDeviceId?.tvDeviceId?.text =
            Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        bindingGetDeviceId?.tvCopy?.setOnClickListener {

            val sdk = Build.VERSION.SDK_INT
            if (sdk < Build.VERSION_CODES.HONEYCOMB) {
                val clipboard =
                    getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.text = bindingGetDeviceId?.tvDeviceId?.text
            } else {
                val clipboard =
                    getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip =
                    ClipData.newPlainText("text label", bindingGetDeviceId?.tvDeviceId?.text)
                clipboard.setPrimaryClip(clip)
            }

            mAlertDialog!!.dismiss()

        }

        bindingGetDeviceId?.tvOk?.setOnClickListener {
            mAlertDialog!!.dismiss()
        }


        mAlertDialog?.show()

    }

    private var mAlertDialog: AlertDialog? = null
    private var bindingForgotPassword: ForgotPasswordBinding? = null

    private fun showForgotPasswordDialog() {

        val builder = AlertDialog.Builder(this)
        bindingForgotPassword = DataBindingUtil.inflate(
            LayoutInflater.from(this@LoginActivity),
            R.layout.forgot_password, null, false
        ) as ForgotPasswordBinding
        builder.setView(bindingForgotPassword?.root)
        mAlertDialog = builder.create()

        bindingForgotPassword?.btnSubmit?.setOnClickListener {

            val email = bindingForgotPassword?.etEmail?.text.toString().trim()
            if (email.isEmpty()) {
                showValidationMessage(getString(R.string.error_please_enter_email_address))
            } else if (!email.isEmailValid()) {
                showValidationMessage(getString(R.string.error_please_enter_valid_email_address))
            } else {
                mAlertDialog!!.dismiss()
                callForgotPasswordApi(email)
            }
        }

        bindingForgotPassword?.btnCancel?.setOnClickListener {
            mAlertDialog!!.dismiss()
        }


        mAlertDialog?.show()

    }

    private fun validateLogin(email: String, password: String): Boolean {

        var validCount = 0

        if (email.isEmpty()) {
            showValidationMessage(getString(R.string.error_please_enter_email_address))
            validCount = +1
        } else if (!email.isEmailValid()) {
            showValidationMessage(getString(R.string.error_please_enter_valid_email_address))
            validCount = +1
        } else if (password.isEmpty()) {
            showValidationMessage(getString(R.string.error_please_enter_password))
            validCount = +1
        }

        return validCount == 0

    }

    private fun showValidationMessage(message: String) {
        alert(message) {
            okButton { }
        }.show()

    }

    private fun callForgotPasswordApi(email: String) {

        CommonMethods.showProgress(this)
        val loginObj = JsonObject()
        loginObj.addProperty(ApiConstants.EMAIL, email)
        loginViewModel?.callForgotPasswordAPI(this, loginObj)
    }

    private fun callLoginApi(showProgress: Boolean) {
        if (CommonMethods.checkInternetConnection(application)) {
            if (showProgress)
                CommonMethods.showProgress(this)
            val loginObj = JsonObject()
            loginObj.addProperty(ApiConstants.EMAIL, email)
            loginObj.addProperty(ApiConstants.PASSWORD, password)
            loginObj.addProperty(
                ApiConstants.DEVICENUMBER,
                Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
            )


            val call =
                ApiClient.getRetrofitInterface()?.callLoginAPI(loginObj)

            WSClient<LoginResponseModel>().request(
                this,
                ApiConstants.DPO_LOGIN,
                call,
                object :
                    ISuccessHandler<LoginResponseModel> {
                    override fun successResponse(
                        requestCode: Int,
                        mResponse: LoginResponseModel
                    ) {
                        if (mResponse.status!!) {
                            checkDPODistrictChange(mResponse)
                        } else {
                            CommonMethods.hideProgress()
                            alert(mResponse.message ?: "") {
                                okButton {
                                }
                            }.show()
                        }
                    }
                },
                object : IFailureHandler {
                    override fun failureResponse(requestCode: Int, message: String) {
                        CommonMethods.hideProgress()
                        handleOfflineOrFailureResponse()
                    }
                })

        } else {
            handleOfflineOrFailureResponse()
        }
    }

    /**
     * Checking if district is change or not
     * If change then check voters to sync
     * If not change then handle the login response*/
    private fun checkDPODistrictChange(mResponse: LoginResponseModel) {
        val dpoDistrictList: ArrayList<DpoList> = ArrayList()
        launch(Dispatchers.Main) {
            val result: List<DpoList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.dpoListDao()?.all
            }
            for (dpo in result!!) {
                when (mResponse.data?.role) {
                    /**Condition for DPO*/
                    "1" -> {
                        if (dpo.district == mResponse.data?.district?.toInt()) {
                            dpoDistrictList.add(dpo)
                        }
                    }
                    /**Condition for MKA*/
                    "9" -> {
                        if (dpo.mkaDistrict == mResponse.data?.mkaDistrict?.toInt()) {
                            dpoDistrictList.add(dpo)
                        }
                    }

                    else -> {
                        if (dpo.district == mResponse.data?.district?.toInt()) {
                            dpoDistrictList.add(dpo)
                        }
                    }
                }
            }
            if (dpoDistrictList.isEmpty()) {
                checkVotersToSync(mResponse)
            } else {
                handleLoginResponse(mResponse)
            }
        }
    }

    /**
     * Checking if any voters is left for sync.
     * if not then clear all voters data of other district.
     * if left out then show alert dialog.*/
    private fun checkVotersToSync(mResponse: LoginResponseModel) {
        launch(Dispatchers.Main) {
            val result: List<VotersList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.votersListDao().updatedVotersListToSync
            }
            if (result!!.isEmpty()) {
                clearAllVotersDataForOtherDistrict(mResponse)
            } else {
                CommonMethods.hideProgress()
                alert("The voters which are updated yet to sync with server. Please sync the data with server before you login with another district!") {
                    okButton {
                    }
                }.show()
            }
        }

    }

    /**Clear all voters data and handle the login response */
    private fun clearAllVotersDataForOtherDistrict(mResponse: LoginResponseModel) {

        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                dataBaseInstance.votersListDao().deleteAll()
                dataBaseInstance.transferInVotersListDao().deleteAll()
                dataBaseInstance.regionListDao().deleteAll()
                dataBaseInstance.provinceListDao().deleteAll()
                dataBaseInstance.districtListDao().deleteAll()
                dataBaseInstance.llgListDao().deleteAll()
                dataBaseInstance.wardListDao().deleteAll()
                dataBaseInstance.locationListDao().deleteAll()
                dataBaseInstance.occupationListDao().deleteAll()
                dataBaseInstance.dpoListDao().deleteAll()
                val latitude = securedSharedPreferenceWrapper.getString(ApiConstants.LATITUDE, "")
                val longitude = securedSharedPreferenceWrapper.getString(ApiConstants.LONGITUDE, "")
                val dcPassword =
                    securedSharedPreferenceWrapper.getString(
                        ApiConstants.DC_PASSWORD,
                        ApiConstants.DC_PASSWORD_DEFAULT
                    )
                securedSharedPreferenceWrapper.clear()
                securedSharedPreferenceWrapper.putString(ApiConstants.LATITUDE, latitude)
                securedSharedPreferenceWrapper.putString(ApiConstants.LONGITUDE, longitude)
                securedSharedPreferenceWrapper.putString(ApiConstants.DC_PASSWORD, dcPassword)
            }
            handleLoginResponse(mResponse)
        }

    }

    /**Handling the login response.
     * Verifying for the dpo data if found in the database.
     * If not then save it to database else fetch the data from database.*/
    private fun handleLoginResponse(it: LoginResponseModel) {

        val dpoUserId = it.data?.userId!!.toInt()
        launch(Dispatchers.Main) {
            val result: DpoList? = withContext(Dispatchers.IO) {
                dataBaseInstance.dpoListDao()?.getDPO(dpoUserId)
            }
            saveRoleTypeOfTheUser(it.data?.role)
            if (result == null)
                saveDpoLoginResponse(it)
            else {
                val loggedInDpoData = it.data
                securedSharedPreferenceWrapper.putInt(
                    ApiConstants.DPO_WORKING_ROLL_LLG_ID,
                    result.workingRollLLGId
                )

                securedSharedPreferenceWrapper.putString(
                    ApiConstants.DPO_WORKING_ROLL_LLG,
                    result.workingRollLLGName
                )

                securedSharedPreferenceWrapper.putInt(
                    ApiConstants.DPO_WORKING_ROLL_WARD_ID,
                    result.workingRollWardId
                )


                securedSharedPreferenceWrapper.putString(
                    ApiConstants.DPO_WORKING_ROLL_WARD,
                    result.workingRollWardName
                )

                securedSharedPreferenceWrapper.putInt(
                    ApiConstants.DPO_WORKING_ROLL_LOCATION_ID,
                    result.workingRollLocationId
                )


                securedSharedPreferenceWrapper.putString(
                    ApiConstants.DPO_WORKING_ROLL_LOCATION,
                    result.workingRollLocationName
                )

                saveCurrentDPOAndShowSuccessMessage(loggedInDpoData, it.message ?: "")
            }
        }
    }

    /**Save role type of the user either DPO or MKA*/
    private fun saveRoleTypeOfTheUser(role: String?) {
        when (role) {
            "1" -> {
                securedSharedPreferenceWrapper.putString(ApiConstants.ROLE, ApiConstants.ROLE_DPO)
                securedSharedPreferenceWrapper.putString(ApiConstants.ROLE_ID, role)
            }

            "9" -> {
                securedSharedPreferenceWrapper.putString(ApiConstants.ROLE, ApiConstants.ROLE_MKA)
                securedSharedPreferenceWrapper.putString(ApiConstants.ROLE_ID, role)
            }

            else -> {
                securedSharedPreferenceWrapper.putString(ApiConstants.ROLE, "")
                securedSharedPreferenceWrapper.putString(ApiConstants.ROLE_ID, role)
            }
        }
    }

    /**Saving DPO/MKA data in the database.*/
    private fun saveDpoLoginResponse(it: LoginResponseModel) {

        val loggedInDpoData = it.data

        val dpo = DpoList()
        /**Data for PNGEC geo structure*/
        dpo.userId = loggedInDpoData?.userId!!.toInt()
        dpo.firstName = loggedInDpoData.firstName
        dpo.lastName = loggedInDpoData.lastName
        dpo.emailAddress = loggedInDpoData.emailAddress
        dpo.region = loggedInDpoData.region.toInt()
        dpo.regionName = loggedInDpoData.regionName
        dpo.province = loggedInDpoData.province.toInt()
        dpo.provinceName = loggedInDpoData.provinceName
        dpo.district = loggedInDpoData.district.toInt()
        dpo.districtName = loggedInDpoData.districtName
        dpo.llg = loggedInDpoData.llg.toInt()
        dpo.llgName = loggedInDpoData.llgName
        dpo.ward = loggedInDpoData.ward.toInt()
        dpo.wardName = loggedInDpoData.wardName
        dpo.location = loggedInDpoData.location.toInt()
        dpo.locationName = loggedInDpoData.locationName
        /**Data for MKA geo structure*/
        dpo.mkaDistrict = loggedInDpoData.mkaDistrict.toInt()
        dpo.mkaDistrictName = loggedInDpoData.mkaDistrictName
        dpo.mkallg = loggedInDpoData.mkallg.toInt()
        dpo.mkallgName = loggedInDpoData.mkallgName
        dpo.mkaWard = loggedInDpoData.mkaWard.toInt()
        dpo.mkaWardName = loggedInDpoData.mkaWardName
        dpo.mkaLocation = loggedInDpoData.mkaLocation.toInt()
        dpo.mkaLocationName = loggedInDpoData.mkaLocationName

        /**Store data for PNGEC geo structure*/
        if (loggedInDpoData.llgName != null && loggedInDpoData.llgName.isNotEmpty()) {
            dpo.workingRollLLGName = loggedInDpoData.llgName
            dpo.workingRollLLGId = loggedInDpoData.llg.toInt()
            securedSharedPreferenceWrapper.putInt(
                ApiConstants.DPO_WORKING_ROLL_LLG_ID,
                loggedInDpoData.llg.toInt()
            )
            securedSharedPreferenceWrapper.putString(
                ApiConstants.DPO_WORKING_ROLL_LLG,
                loggedInDpoData.llgName
            )
        } else {
            securedSharedPreferenceWrapper.putInt(ApiConstants.DPO_WORKING_ROLL_LLG_ID, 0)
            securedSharedPreferenceWrapper.putString(ApiConstants.DPO_WORKING_ROLL_LLG, "")
        }

        if (loggedInDpoData.wardName != null && loggedInDpoData.wardName.isNotEmpty()) {
            dpo.workingRollWardName = loggedInDpoData.wardName
            dpo.workingRollWardId = loggedInDpoData.ward.toInt()
            securedSharedPreferenceWrapper.putInt(
                ApiConstants.DPO_WORKING_ROLL_WARD_ID,
                loggedInDpoData.ward.toInt()
            )
            securedSharedPreferenceWrapper.putString(
                ApiConstants.DPO_WORKING_ROLL_WARD,
                loggedInDpoData.wardName
            )
        } else {
            securedSharedPreferenceWrapper.putInt(ApiConstants.DPO_WORKING_ROLL_WARD_ID, 0)
            securedSharedPreferenceWrapper.putString(ApiConstants.DPO_WORKING_ROLL_WARD, "")
        }

        securedSharedPreferenceWrapper.putInt(ApiConstants.DPO_WORKING_ROLL_LOCATION_ID, 0)
        securedSharedPreferenceWrapper.putString(ApiConstants.DPO_WORKING_ROLL_LOCATION, "")

        /**Store data for MKA geo structure*/
        if (loggedInDpoData.mkallgName != null && loggedInDpoData.mkallgName.isNotEmpty()) {
            dpo.workingRollMKALLGName = loggedInDpoData.mkallgName
            dpo.workingRollMKALLGId = loggedInDpoData.mkallg.toInt()
            securedSharedPreferenceWrapper.putInt(
                ApiConstants.DPO_WORKING_ROLL_MKA_LLG_ID,
                loggedInDpoData.mkallg.toInt()
            )
            securedSharedPreferenceWrapper.putString(
                ApiConstants.DPO_WORKING_ROLL_MKA_LLG,
                loggedInDpoData.mkallgName
            )
        } else {
            securedSharedPreferenceWrapper.putInt(ApiConstants.DPO_WORKING_ROLL_MKA_LLG_ID, 0)
            securedSharedPreferenceWrapper.putString(ApiConstants.DPO_WORKING_ROLL_MKA_LLG, "")
        }

        if (loggedInDpoData.mkaWardName != null && loggedInDpoData.mkaWardName.isNotEmpty()) {
            dpo.workingRollMKAWardName = loggedInDpoData.mkaWardName
            dpo.workingRollMKAWardId = loggedInDpoData.mkaWard.toInt()
            securedSharedPreferenceWrapper.putInt(
                ApiConstants.DPO_WORKING_ROLL_MKA_WARD_ID,
                loggedInDpoData.mkaWard.toInt()
            )
            securedSharedPreferenceWrapper.putString(
                ApiConstants.DPO_WORKING_ROLL_MKA_WARD,
                loggedInDpoData.mkaWardName
            )
        } else {
            securedSharedPreferenceWrapper.putInt(ApiConstants.DPO_WORKING_ROLL_MKA_WARD_ID, 0)
            securedSharedPreferenceWrapper.putString(ApiConstants.DPO_WORKING_ROLL_MKA_WARD, "")
        }

        securedSharedPreferenceWrapper.putInt(ApiConstants.DPO_WORKING_ROLL_MKA_LOCATION_ID, 0)
        securedSharedPreferenceWrapper.putString(ApiConstants.DPO_WORKING_ROLL_MKA_LOCATION, "")

        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                dataBaseInstance.dpoListDao().insert(dpo)
            }

            saveCurrentDPOAndShowSuccessMessage(loggedInDpoData, it.message ?: "")

        }
    }

    /**Saving the data in shared preference and show success alert message. */
    private fun saveCurrentDPOAndShowSuccessMessage(
        loggedInDpoData: DPOUserData?,
        message: String
    ) {
        CommonMethods.hideProgress()
        securedSharedPreferenceWrapper.putInt(
            ApiConstants.DPO_GEO_REGION_ID,
            loggedInDpoData?.region!!.toInt()
        )
        securedSharedPreferenceWrapper.putInt(
            ApiConstants.DPO_GEO_PROVINCE_ID,
            loggedInDpoData.province.toInt()
        )


        securedSharedPreferenceWrapper.putString(
            ApiConstants.DPO_GEO_REGION,
            loggedInDpoData.regionName
        )
        securedSharedPreferenceWrapper.putString(
            ApiConstants.DPO_GEO_PROVINCE,
            loggedInDpoData.provinceName
        )


        securedSharedPreferenceWrapper.saveObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            loggedInDpoData
        )

        securedSharedPreferenceWrapper.putString(
            ApiConstants.UserId,
            loggedInDpoData.userId
        )


        securedSharedPreferenceWrapper.putString(
            ApiConstants.DPO_NAME,
            loggedInDpoData.firstName + " " + loggedInDpoData.lastName
        )

        securedSharedPreferenceWrapper.putString(
            ApiConstants.LOGGED_IN_DPO_EMAIL,
            email
        )

        securedSharedPreferenceWrapper.putString(
            ApiConstants.LOGGED_IN_DPO_PASSWORD,
            password
        )

        when (loggedInDpoData.role) {
            /**Condition for DPO user*/
            "1" -> {
                securedSharedPreferenceWrapper.putInt(
                    ApiConstants.DPO_GEO_DISTRICT_ID,
                    loggedInDpoData.district.toInt()
                )
                securedSharedPreferenceWrapper.putInt(
                    ApiConstants.DPO_GEO_LLG_ID,
                    loggedInDpoData.llg.toInt()
                )
                securedSharedPreferenceWrapper.putInt(
                    ApiConstants.DPO_GEO_WARD_ID,
                    loggedInDpoData.ward.toInt()
                )
                securedSharedPreferenceWrapper.putInt(
                    ApiConstants.DPO_GEO_LOCATION_ID,
                    loggedInDpoData.location.toInt()
                )
                securedSharedPreferenceWrapper.putString(
                    ApiConstants.DPO_GEO_DISTRICT,
                    loggedInDpoData.districtName
                )
                securedSharedPreferenceWrapper.putString(
                    ApiConstants.DPO_GEO_LLG,
                    loggedInDpoData.llgName
                )
                securedSharedPreferenceWrapper.putString(
                    ApiConstants.DPO_GEO_WARD,
                    loggedInDpoData.wardName
                )
                securedSharedPreferenceWrapper.putString(
                    ApiConstants.DPO_GEO_LOCATION,
                    loggedInDpoData.locationName
                )
            }
            /**Condition for MKA user*/
            "9" -> {
                securedSharedPreferenceWrapper.putInt(
                    ApiConstants.DPO_GEO_DISTRICT_ID,
                    loggedInDpoData.mkaDistrict.toInt()
                )
                securedSharedPreferenceWrapper.putInt(
                    ApiConstants.DPO_GEO_LLG_ID,
                    loggedInDpoData.mkallg.toInt()
                )
                securedSharedPreferenceWrapper.putInt(
                    ApiConstants.DPO_GEO_WARD_ID,
                    loggedInDpoData.mkaWard.toInt()
                )
                securedSharedPreferenceWrapper.putInt(
                    ApiConstants.DPO_GEO_LOCATION_ID,
                    loggedInDpoData.mkaLocation.toInt()
                )
                securedSharedPreferenceWrapper.putString(
                    ApiConstants.DPO_GEO_DISTRICT,
                    loggedInDpoData.mkaDistrictName
                )
                securedSharedPreferenceWrapper.putString(
                    ApiConstants.DPO_GEO_LLG,
                    loggedInDpoData.mkallgName
                )
                securedSharedPreferenceWrapper.putString(
                    ApiConstants.DPO_GEO_WARD,
                    loggedInDpoData.mkaWardName
                )
                securedSharedPreferenceWrapper.putString(
                    ApiConstants.DPO_GEO_LOCATION,
                    loggedInDpoData.mkaLocationName
                )
            }
        }

        alert(message) {
            okButton {
                navigateToNextModule()
            }
        }.show().setCancelable(false)
    }

    /**Handle the response in case of api failure or no internet connection.
     * Check if the dpo related to same email is in database else show alert message to the user.*/
    private fun handleOfflineOrFailureResponse() {

        launch(Dispatchers.Main) {
            val result: DpoList? = withContext(Dispatchers.IO) {
                dataBaseInstance.dpoListDao()?.getDPOUsingEmailAddress(email)
            }
            if (result == null)
                alert("Please check internet connection and try again.") {
                    okButton { }
                }.show()
            else {
                saveCurrentDPOAndShowSuccessMessageOffline(result)
            }
        }
    }

    /**Get DPO data from database if found in case of Api Failure.
     * Show success message alert to the user.*/
    private fun saveCurrentDPOAndShowSuccessMessageOffline(result: DpoList) {


        securedSharedPreferenceWrapper.putInt(
            ApiConstants.DPO_GEO_REGION_ID,
            result.region
        )
        securedSharedPreferenceWrapper.putInt(
            ApiConstants.DPO_GEO_PROVINCE_ID,
            result.province
        )
        securedSharedPreferenceWrapper.putInt(
            ApiConstants.DPO_GEO_DISTRICT_ID,
            result.district
        )
        securedSharedPreferenceWrapper.putInt(
            ApiConstants.DPO_GEO_LLG_ID,
            result.llg
        )
        securedSharedPreferenceWrapper.putInt(
            ApiConstants.DPO_GEO_WARD_ID,
            result.ward
        )
        securedSharedPreferenceWrapper.putInt(
            ApiConstants.DPO_GEO_LOCATION_ID,
            result.location
        )

        securedSharedPreferenceWrapper.putString(
            ApiConstants.DPO_GEO_REGION,
            result.regionName
        )
        securedSharedPreferenceWrapper.putString(
            ApiConstants.DPO_GEO_PROVINCE,
            result.provinceName
        )
        securedSharedPreferenceWrapper.putString(
            ApiConstants.DPO_GEO_DISTRICT,
            result.districtName
        )
        securedSharedPreferenceWrapper.putString(
            ApiConstants.DPO_GEO_LLG,
            result.llgName
        )
        securedSharedPreferenceWrapper.putString(
            ApiConstants.DPO_GEO_WARD,
            result.wardName
        )
        securedSharedPreferenceWrapper.putString(
            ApiConstants.DPO_GEO_LOCATION,
            result.locationName
        )

        val loggedInDpoData = DPOUserData()
        loggedInDpoData.userId = result.userId.toString()
        loggedInDpoData.firstName = result.firstName
        loggedInDpoData.lastName = result.lastName
        loggedInDpoData.emailAddress = result.emailAddress
        loggedInDpoData.region = result.region.toString()
        loggedInDpoData.regionName = result.regionName
        loggedInDpoData.province = result.province.toString()
        loggedInDpoData.provinceName = result.provinceName
        loggedInDpoData.district = result.district.toString()
        loggedInDpoData.districtName = result.districtName
        loggedInDpoData.llg = result.llg.toString()
        loggedInDpoData.llgName = result.llgName ?: ""
        loggedInDpoData.ward = result.ward.toString()
        loggedInDpoData.wardName = result.wardName ?: ""
        loggedInDpoData.location = result.location.toString()
        loggedInDpoData.locationName = result.locationName ?: ""
        /**Save MKA geo structure field from database when MKA is offline */
        loggedInDpoData.mkaDistrict = result.mkaDistrict?.toString() ?: ""
        loggedInDpoData.mkaDistrictName = result.mkaDistrictName?.toString() ?: ""
        loggedInDpoData.mkallg = result.mkallg?.toString() ?: ""
        loggedInDpoData.mkallgName = result.mkallgName?.toString() ?: ""
        loggedInDpoData.mkaWard = result.mkaWard?.toString() ?: ""
        loggedInDpoData.mkaWardName = result.mkaWardName?.toString() ?: ""
        loggedInDpoData.mkaLocation = result.mkaLocation?.toString() ?: ""
        loggedInDpoData.mkaLocationName = result.mkaLocationName?.toString() ?: ""

        securedSharedPreferenceWrapper.saveObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            loggedInDpoData
        )

        securedSharedPreferenceWrapper.putString(
            ApiConstants.UserId,
            result.userId.toString()
        )


        securedSharedPreferenceWrapper.putString(
            ApiConstants.DPO_NAME,
            result.firstName + " " + result.lastName
        )

        securedSharedPreferenceWrapper.putString(
            ApiConstants.LOGGED_IN_DPO_EMAIL,
            email
        )

        securedSharedPreferenceWrapper.putString(
            ApiConstants.LOGGED_IN_DPO_PASSWORD,
            password
        )

        securedSharedPreferenceWrapper.putInt(
            ApiConstants.DPO_WORKING_ROLL_LLG_ID,
            result.workingRollLLGId
        )

        securedSharedPreferenceWrapper.putString(
            ApiConstants.DPO_WORKING_ROLL_LLG,
            result.workingRollLLGName
        )

        securedSharedPreferenceWrapper.putInt(
            ApiConstants.DPO_WORKING_ROLL_WARD_ID,
            result.workingRollWardId
        )


        securedSharedPreferenceWrapper.putString(
            ApiConstants.DPO_WORKING_ROLL_WARD,
            result.workingRollWardName
        )

        alert("Successfully login") {
            okButton { navigateToNextModule() }
        }.show()
    }

    private fun addObservers() {

        loginViewModel?.forgotPasswordResponse?.observe(this) {
            CommonMethods.hideProgress()
            if (it != null && it.status!!) {
                alert(it.message ?: "") {
                    okButton { }
                }.show()
            } else {
                alert(it.message ?: "") {
                    okButton { }
                }.show()
            }
        }
    }


    private fun navigateToNextModule() {
        securedSharedPreferenceWrapper.putString(
            ApiConstants.FILTER_WARD_ROLL_LAST_NAME,
            ""
        )
        securedSharedPreferenceWrapper.putString(
            ApiConstants.FILTER_WARD_ROLL_FIRST_NAME,
            ""
        )
        securedSharedPreferenceWrapper.putString(
            ApiConstants.FILTER_WARD_ROLL_ELECTOR_ID,
            ""
        )
        securedSharedPreferenceWrapper.putString(
            ApiConstants.FILTER_WARD_ROLL_GENDER,
            ""
        )

        if (securedSharedPreferenceWrapper.getBoolean(ApiConstants.DPO_SETUP_COMPLETED, false)) {
            if (CommonMethods.checkInternetConnection(this))
                checkSettingsForDataRefresh()
            else {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }

        } else {
            startActivity(Intent(this, NewDownloadActivity::class.java))
            finish()
        }
    }


    var generalSettingsData: GeneralSettingsData = GeneralSettingsData()
    private fun callGeneralSettingsApi() {

        if (CommonMethods.checkInternetConnection(application)) {
            if (locationHelper == null) {
                locationHelper = LocationHelper2(this)
            }
            locationHelper!!.fetchSingleLocation { location ->

                securedSharedPreferenceWrapper.putString(
                    ApiConstants.LATITUDE,
                    location.latitude.toString()
                )

                securedSharedPreferenceWrapper.putString(
                    ApiConstants.LONGITUDE,
                    location.longitude.toString()
                )

            }
        }


        CommonMethods.showProgress(this)
        val call =
            ApiClient.getRetrofitInterface()?.callGeneralSettingsAPI()

        WSClient<GeneralSettingsModel>().request(
            this,
            ApiConstants.GENERAL_SETTINGS,
            call,
            object :
                ISuccessHandler<GeneralSettingsModel> {
                override fun successResponse(
                    requestCode: Int,
                    mResponse: GeneralSettingsModel
                ) {
                    if (mResponse.status!!) {
                        generalSettingsData = mResponse.data!!
                        securedSharedPreferenceWrapper.putString(
                            ApiConstants.DC_PASSWORD,
                            CommonMethods.decryptDcPassWord(
                                generalSettingsData.dcPassword,
                                ApiConstants.DECRYPT_KEY
                            )
                        )
                        securedSharedPreferenceWrapper.putInt(
                            ApiConstants.GEO_VERSION,
                            generalSettingsData.geoVersion
                        )

                        securedSharedPreferenceWrapper.putInt(
                            ApiConstants.OCCUPATION_VERSION,
                            generalSettingsData.occupationVersion
                        )
                        callLoginApi(false)
                    } else {
                        callLoginApi(false)
                    }
                }
            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    callLoginApi(false)
                }
            })

    }

    private fun checkSettingsForDataRefresh() {
        val occupationVersion =
            securedSharedPreferenceWrapper.getInt(ApiConstants.OCCUPATION_VERSION, 1)
        val geoVersion = securedSharedPreferenceWrapper.getInt(ApiConstants.GEO_VERSION, 1)
        when {
            occupationVersion != generalSettingsData.occupationVersion && generalSettingsData.occupationVersion != 0 -> {
                callGetOccupationListAPI()
            }

            geoVersion != generalSettingsData.geoVersion && generalSettingsData.geoVersion != 0 -> {
                downloadGeoStructureData()
            }

            else -> {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        if (generalSettingsData.geoVersion != 0)
            securedSharedPreferenceWrapper.putInt(
                ApiConstants.GEO_VERSION,
                generalSettingsData.geoVersion
            )

        if (generalSettingsData.occupationVersion != 0)
            securedSharedPreferenceWrapper.putInt(
                ApiConstants.OCCUPATION_VERSION,
                generalSettingsData.occupationVersion
            )
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun callGetOccupationListAPI() {
        CommonMethods.showProgress(this)
        val call =
            ApiClient.getRetrofitInterface()?.callGetOccupationListAPI()
        WSClient<Response<ArrayList<OccupationList>>>().request(
            this,
            ApiConstants.DPO_GET_OCCUPATION_LIST,
            call,
            object :
                ISuccessHandler<Response<ArrayList<OccupationList>>> {
                override fun successResponse(
                    requestCode: Int,
                    mResponse: Response<ArrayList<OccupationList>>
                ) {
                    CommonMethods.hideProgress()
                    saveOccupationList(mResponse.data!!)
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    CommonMethods.hideProgress()
                }
            })

    }

    private fun saveOccupationList(occupationList: ArrayList<OccupationList>) {

        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                dataBaseInstance.occupationListDao().deleteAll()
                dataBaseInstance.occupationListDao().insertAll(occupationList)
            }
            checkGeoDataForRefresh()

        }

    }

    private fun checkGeoDataForRefresh() {
        val geoVersion = securedSharedPreferenceWrapper.getInt(ApiConstants.GEO_VERSION, 1)
        if (geoVersion != generalSettingsData.geoVersion && generalSettingsData.geoVersion != 0) {
            downloadGeoStructureData()
        } else {
            navigateToHome()
        }
    }


    // Download GeoStructure Data
    private fun downloadGeoStructureData() {
        if (CommonMethods.checkInternetConnection(application)) {
            clearData()
        } else {
            navigateToHome()
        }
    }

    private fun clearData() {

        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                dataBaseInstance.regionListDao().deleteAll()
                dataBaseInstance.provinceListDao().deleteAll()
                dataBaseInstance.districtListDao().deleteAll()
                dataBaseInstance.llgListDao().deleteAll()
                dataBaseInstance.wardListDao().deleteAll()
                dataBaseInstance.locationListDao().deleteAll()
            }
            callGetGeoListAPI()
            securedSharedPreferenceWrapper.putBoolean(
                ApiConstants.GEO_STRUCTURE_DOWNLOAD_IN_PROGRESS,
                true
            )
            securedSharedPreferenceWrapper.putInt(ApiConstants.GEO_STRUCTURE_PAGENO, 1)
            securedSharedPreferenceWrapper.putInt(
                ApiConstants.GEO_STRUCTURE_DOWNLOADED_PERCENTAGE,
                0
            )
            showGeoStructureDownloadProgressDialog()
            startRequiredService(
                this@LoginActivity,
                DownloadGeoStructureDataSyncService::class.java
            )

        }

    }


    private var bindingGeoStructureDownloadProgressBar: LayoutLoaderDownloadProgressBarBinding? =
        null
    var mGeoStructureDownloadProgressDialog: AlertDialog? = null
    private fun showGeoStructureDownloadProgressDialog() {

        val builder = AlertDialog.Builder(this)
        bindingGeoStructureDownloadProgressBar = DataBindingUtil.inflate(
            LayoutInflater.from(
                this
            ),
            R.layout.layout_loader_download_progress_bar, null, false
        ) as LayoutLoaderDownloadProgressBarBinding
        builder.setView(bindingGeoStructureDownloadProgressBar?.root)
        mGeoStructureDownloadProgressDialog = builder.create()
        mGeoStructureDownloadProgressDialog?.setCanceledOnTouchOutside(false)
        mGeoStructureDownloadProgressDialog?.setCancelable(false)
        mGeoStructureDownloadProgressDialog?.show()

        bindingGeoStructureDownloadProgressBar?.tvDownloadMessage!!.text =
            getString(R.string.please_wait_until_the_geo_structure_download_is_complete)

        val percentage =
            securedSharedPreferenceWrapper.getInt(
                ApiConstants.GEO_STRUCTURE_DOWNLOADED_PERCENTAGE,
                1
            )
        bindingGeoStructureDownloadProgressBar?.downloadProgressBar!!.progress = percentage
        bindingGeoStructureDownloadProgressBar?.tvDownloadPercentage!!.text =
            "Downloaded: $percentage%"

    }

    private val singlePageGeoStructureDataDownloadedReceiver: BroadcastReceiver =
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val totalRecords = intent.getIntExtra("Total_Records", 0)
                val downloadedRecords = intent.getIntExtra("Downloaded_Records", 0)
                val percentage =
                    (downloadedRecords.toDouble() / totalRecords.toDouble() * 100).toInt()
                securedSharedPreferenceWrapper.putInt(
                    ApiConstants.GEO_STRUCTURE_DOWNLOADED_PERCENTAGE,
                    percentage
                )
                if (mGeoStructureDownloadProgressDialog != null && mGeoStructureDownloadProgressDialog!!.isShowing) {
                    if (percentage != 100) {
                        bindingGeoStructureDownloadProgressBar?.downloadProgressBar!!.progress =
                            percentage
                        bindingGeoStructureDownloadProgressBar?.tvDownloadPercentage!!.text =
                            "Downloaded: $percentage%"
                    }
                }

            }
        }

    private val allPageGeoStructureDataDownloadReceiver: BroadcastReceiver =
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (mGeoStructureDownloadProgressDialog != null && mGeoStructureDownloadProgressDialog!!.isShowing) {
                    bindingGeoStructureDownloadProgressBar?.downloadProgressBar!!.progress = 100
                    bindingGeoStructureDownloadProgressBar?.tvDownloadPercentage!!.text =
                        "Downloaded: 100%"
                    mGeoStructureDownloadProgressDialog?.dismiss()
                }

                alert("The Geo Structure data has been downloaded successfully") {
                    okButton {
                        securedSharedPreferenceWrapper.putInt(
                            ApiConstants.GEO_VERSION,
                            generalSettingsData.geoVersion
                        )
                        securedSharedPreferenceWrapper.putInt(
                            ApiConstants.OCCUPATION_VERSION,
                            generalSettingsData.occupationVersion
                        )
                        navigateToHome()
                    }
                }.show().setCancelable(false)
            }
        }

    lateinit var geoListData: GeoListData
    private fun callGetGeoListAPI() {

        val call =
            ApiClient.getRetrofitInterface()?.callGetGeoListAPI()

        WSClient<GeoListModel>().request(this, ApiConstants.DPO_GET_GEO_LIST, call, object :
            ISuccessHandler<GeoListModel> {
            override fun successResponse(
                requestCode: Int,
                mResponse: GeoListModel
            ) {
                geoListData = mResponse.data!!
                saveGeoDataList()
            }

        }, object : IFailureHandler {
            override fun failureResponse(requestCode: Int, message: String) {
            }
        })

    }

    private fun saveGeoDataList() {

        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                val mRegionListDAO = dataBaseInstance.regionListDao()
                mRegionListDAO.deleteAll()
                mRegionListDAO.insertAll(geoListData.regionList!!)

                val mProvinceListDAO = dataBaseInstance.provinceListDao()
                mProvinceListDAO.deleteAll()
                mProvinceListDAO.insertAll(geoListData.provinceList!!)

                val mDistrictListDAO = dataBaseInstance.districtListDao()
                mDistrictListDAO.deleteAll()
                mDistrictListDAO.insertAll(geoListData.districtList!!)

                val mLLGListDAO = dataBaseInstance.llgListDao()
                mLLGListDAO.deleteAll()
                mLLGListDAO.insertAll(geoListData.llgList!!)

                val mWardListDAO = dataBaseInstance.wardListDao()
                mWardListDAO.deleteAll()
                mWardListDAO.insertAll(geoListData.wardList!!)
            }
        }

    }


    private fun startRequiredService(
        activity: Activity,
        serviceClass: Class<*>?
    ) {
        if (CommonMethods.checkInternetConnection(activity)) {

            if (isMyServiceRunning(DownloadGeoStructureDataSyncService::class.java)) {
                val intent = Intent(ApiConstants.STOP_SERVICE)
                sendBroadcast(intent)
            }

            val serviceIntent = Intent(activity, serviceClass)

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (!isMyServiceRunning(serviceClass!!)) activity.startForegroundService(
                        serviceIntent
                    )
                } else {
                    if (!isMyServiceRunning(serviceClass!!)) activity.startService(
                        serviceIntent
                    )
                }
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
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

}
