package com.pngelectoral.ui.activity

import ApiConstants
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.storage.StorageVolume
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.pngelectoral.BuildConfig
import com.pngelectoral.LocationHelper2
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.*
import com.pngelectoral.datasync.DownloadGeoStructureDataSyncService
import com.pngelectoral.datasync.DownloadPreliminaryRollSyncService
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.retrofit.*
import com.pngelectoral.ui.viewModel.SettingsViewModel
import com.pngelectoral.utility.CommonMethods
import com.pngelectoral.utility.helper.DialogSpinner
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import java.io.*
import java.nio.channels.FileChannel
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.coroutines.CoroutineContext


class SettingsActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivitySettingsBinding? = null
    private var settingsViewModel: SettingsViewModel? = null
    lateinit var loggedInDPOUserData: DPOUserData
    lateinit var dataBaseInstance: AppDatabase

    var regionId = 0
    var provinceId = 0
    var districtId = 0
    var llgId = 0
    var wardId = 0
    var locationId = 0

    private var downloadPreliminaryllgId = 0
    private var downloadPreliminarywardId = 0
    private var downloadPreliminarylocationId = 0

    var regionName = ""
    var provinceName = ""
    var districtName = ""
    var llgName = ""
    private var wardNoAndName = ""
    var wardNumber = 0
    var wardName = ""
    var locationName = ""

    private var mkaDistrictArray = ArrayList<MKAGeoListData>()
    private var mkaLlgArray = ArrayList<MKAGeoListModel.GeoListData.LlG>()
    private var mkaWardArray = ArrayList<MKAGeoListModel.GeoListData.LlG.Ward>()
    private var mkaLocationArray = ArrayList<MKAGeoListModel.GeoListData.LlG.Ward.Location>()

    private var locationHelper: LocationHelper2? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job
    private lateinit var storagePermissionResultLauncher: ActivityResultLauncher<Intent?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationHelper = LocationHelper2(this)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_settings
        )
        settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)
        binding?.settingsViewModel = settingsViewModel

        job = Job()

        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )
        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase

        regionId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_REGION_ID, 0)
        provinceId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_PROVINCE_ID, 0)

        regionName = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_REGION, "")
        provinceName = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_PROVINCE, "")
        when (securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "")) {
            ApiConstants.ROLE_DPO -> {
                districtId = loggedInDPOUserData.district.toInt()
                llgId = if (securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_LLG_ID,
                        0
                    ) == 0
                ) {
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_LLG_ID, 0)
                } else {
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LLG_ID, 0)
                }
                wardId = if (securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_WARD_ID,
                        0
                    ) == 0
                ) {
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_WARD_ID, 0)
                } else {
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_WARD_ID, 0)
                }
                locationId = if (securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_LOCATION_ID,
                        0
                    ) == 0
                ) {
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_LOCATION_ID, 0)
                } else {
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LOCATION_ID, 0)
                }
                districtName = loggedInDPOUserData.districtName
                llgName = if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_LLG, ""
                    ).isEmpty()
                ) {
                    loggedInDPOUserData.llgName
                } else {
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LLG, "")
                }
                wardName = if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_WARD, ""
                    ).isEmpty()
                ) {
                    loggedInDPOUserData.wardName
                } else {
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_WARD, "")
                }
                locationName = if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_LOCATION, ""
                    ).isEmpty()
                ) {
                    loggedInDPOUserData.locationName
                } else {
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LOCATION, "")
                }
            }

            ApiConstants.ROLE_MKA -> {
                districtId = loggedInDPOUserData.mkaDistrict.toInt()
                llgId = if (securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_MKA_LLG_ID,
                        0
                    ) == 0
                ) {
                    loggedInDPOUserData.mkallg.toInt()
                } else {
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_MKA_LLG_ID, 0)
                }
                wardId = if (securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_MKA_WARD_ID,
                        0
                    ) == 0
                ) {
                   loggedInDPOUserData.mkaWard.toInt()
                } else {
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_MKA_WARD_ID, 0)
                }
                locationId = if (securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_MKA_LOCATION_ID,
                        0
                    ) == 0
                ) {
                   loggedInDPOUserData.mkaLocation.toInt()
                } else {
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_MKA_LOCATION_ID, 0)
                }
                districtName = loggedInDPOUserData.mkaDistrictName
                llgName = if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_LLG, ""
                    ).isEmpty()
                ) {
                    loggedInDPOUserData.mkallgName
                } else {
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_MKA_LLG, "")
                }
                wardName = if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_WARD, ""
                    ).isEmpty()
                ) {
                    loggedInDPOUserData.mkaWardName
                } else {
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_MKA_WARD, "")
                }
                locationName = if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_LOCATION, ""
                    ).isEmpty()
                ) {
                    loggedInDPOUserData.mkaLocationName
                } else {
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_MKA_LOCATION, "")
                }
                launch(Dispatchers.Main) {
                    val districtResult: List<MKAGeoListData>? = withContext(Dispatchers.IO) {
                        dataBaseInstance.geoListDao()?.all
                    } ?: kotlin.run { arrayListOf() }
                    mkaDistrictArray = districtResult?.let { ArrayList<MKAGeoListData>(it) }
                        ?: kotlin.run { arrayListOf() }

                    val llgResult = mkaDistrictArray.filter { district ->
                        district.districtId == districtId
                    }
                    if (llgResult.isNotEmpty())
                        mkaLlgArray = Gson().fromJson<ArrayList<MKAGeoListModel.GeoListData.LlG>>(
                            llgResult.single().llGs ?: "",
                            object : TypeToken<ArrayList<MKAGeoListModel.GeoListData.LlG>?>() {}.type
                        ) ?: kotlin.run { arrayListOf() }
                    val wardResult = mkaLlgArray.filter { llg ->
                        llg.llgId == llgId
                    }
                    if (wardResult.isNotEmpty())
                        mkaWardArray.addAll(wardResult.single().wards ?: arrayListOf())
                    val locationResult = mkaWardArray.filter { ward ->
                        ward.wardId == wardId
                    }
                    if (locationResult.isNotEmpty()) {
                        mkaLocationArray.addAll(locationResult.single().locations ?: arrayListOf())
                    }
                }
            }

            else -> {
                districtId = loggedInDPOUserData.district.toInt()
                llgId = if (securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_LLG_ID,
                        0
                    ) == 0
                ) {
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_LLG_ID, 0)
                } else {
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LLG_ID, 0)
                }
                wardId = if (securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_WARD_ID,
                        0
                    ) == 0
                ) {
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_WARD_ID, 0)
                } else {
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_WARD_ID, 0)
                }
                locationId = if (securedSharedPreferenceWrapper.getInt(
                        ApiConstants.DPO_WORKING_ROLL_LOCATION_ID,
                        0
                    ) == 0
                ) {
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_LOCATION_ID, 0)
                } else {
                    securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LOCATION_ID, 0)
                }
                districtName = loggedInDPOUserData.districtName
                llgName = if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_LLG, ""
                    ).isEmpty()
                ) {
                    loggedInDPOUserData.llgName
                } else {
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LLG, "")
                }
                wardName = if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_WARD, ""
                    ).isEmpty()
                ) {
                    loggedInDPOUserData.wardName
                } else {
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_WARD, "")
                }
                locationName = if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.DPO_WORKING_ROLL_LOCATION, ""
                    ).isEmpty()
                ) {
                    loggedInDPOUserData.locationName
                } else {
                    securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LOCATION, "")
                }
            }
        }

        if (wardName.isNotEmpty()) {
            getWardNumberAndNameUsingCoroutines()
        }

        registerReceiver(
            allPageDownloadReceiver,
            IntentFilter(ApiConstants.ALL_PRELIMINARY_DATA_COMPLETED_BROADCAST)
        )

        registerReceiver(
            singlePageDownloadedReceiver,
            IntentFilter(ApiConstants.PRELIMINARY_SINGLE_PAGE_DATA_COMPLETED)
        )

        registerReceiver(
            internetConnectionStatusReceiver,
            IntentFilter(ApiConstants.INTERNET_CONNECTION_LOST)
        )

        registerReceiver(
            allPageGeoStructureDataDownloadReceiver,
            IntentFilter(ApiConstants.GEO_STRUCTURE_DATA_COMPLETED_BROADCAST)
        )

        registerReceiver(
            singlePageGeoStructureDataDownloadedReceiver,
            IntentFilter(ApiConstants.GEO_STRUCTURE_SINGLE_PAGE_DATA_COMPLETED)
        )

        setFooterData()
        setClickListeners()
        callGeoClanListApi()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            storagePermissionResultLauncher = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (Environment.isExternalStorageManager()) {
                    createImagesFilePath()
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
//            if (result != null && result.wardNumber != null) {
//                wardNumber = result.wardNumber
//                wardNoAndName = "$wardNumber & $wardName"
//            } else {
//                wardNoAndName = "$wardNumber & $wardName"
//            }
            wardNumber = result!!.wardNumber
            wardNoAndName = "$wardNumber & $wardName"
        }
    }




    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
        unregisterReceiver(allPageDownloadReceiver)
        unregisterReceiver(singlePageDownloadedReceiver)
        unregisterReceiver(internetConnectionStatusReceiver)
        unregisterReceiver(allPageGeoStructureDataDownloadReceiver)
        unregisterReceiver(singlePageGeoStructureDataDownloadedReceiver)
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

    private fun setClickListeners() {

        binding?.rlAuditSetting?.setOnClickListener {
            //startActivity(Intent(this, RejectedVoterListActivity::class.java))
            showAuditSettingDialog()
        }

        binding?.rlRejected?.setOnClickListener {
            startActivity(Intent(this, RejectedVoterListActivity::class.java))
        }

        binding?.rlUpdatedVotersData?.setOnClickListener {
            startActivity(Intent(this, UpdatedVotersListActivity::class.java))
        }

        binding?.rlSyncedVotersData?.setOnClickListener {
            startActivity(Intent(this, SyncedVotersListingActivity::class.java))
        }

        binding?.rlLastDateOfEnrolment?.setOnClickListener {
            val lastDateOfEnrolment = if (securedSharedPreferenceWrapper.getString(
                    ApiConstants.ROLE,
                    ""
                ) == ApiConstants.ROLE_DPO
            ) securedSharedPreferenceWrapper.getString(
                "LAST_DATE_OF_ENROLMENT",
                ApiConstants.LAST_DATE_OF_ENROLMENT_DEFAULT
            ) else securedSharedPreferenceWrapper.getString(
                "LAST_DATE_OF_ENROLMENT",
                ApiConstants.LAST_DATE_OF_ENROLMENT_DEFAULT_MKA
            )
            selectLastDateOfEnrolment(
                lastDateOfEnrolment,
                "LAST_DATE_OF_ENROLMENT",
                getString(R.string.last_date_of_enrolment_saved_successfully)
            )
        }

        binding?.rlProvisionalCutOffDate?.setOnClickListener {
            val provisionalCutOffDate = if (securedSharedPreferenceWrapper.getString(
                    ApiConstants.ROLE,
                    ""
                ) == ApiConstants.ROLE_DPO
            ) securedSharedPreferenceWrapper.getString(
                ApiConstants.PROVISIONAL_CUT_OFF_DATE,
                ApiConstants.PROVISIONAL_CUT_OFF_DATE_VALUE
            ) else securedSharedPreferenceWrapper.getString(
                ApiConstants.PROVISIONAL_CUT_OFF_DATE,
                ApiConstants.PROVISIONAL_CUT_OFF_DATE_VALUE_MKA
            )
            selectLastDateOfEnrolment(
                provisionalCutOffDate,
                ApiConstants.PROVISIONAL_CUT_OFF_DATE,
                getString(R.string.provisional_cutoff_date_saved_successfully)
            )
        }

        binding?.rlIssueOfWritDate?.setOnClickListener {
            selectLastDateOfEnrolment(
                securedSharedPreferenceWrapper.getString(
                    ApiConstants.ISSUE_OF_WRIT_DATE,
                    ApiConstants.ISSUE_OF_WRIT_DATE_VALUE
                ),
                ApiConstants.ISSUE_OF_WRIT_DATE,
                getString(R.string.issue_of_writ_date_saved_successfully)
            )
        }

        binding?.rlDOReport?.setOnClickListener {
            val intent = Intent(this, DeviceOperatorReportSelectionActivity::class.java)
            intent.putExtra(ApiConstants.REPORT_STAGING, false)
            startActivity(intent)
        }

        binding?.rlDOReportStaging?.setOnClickListener {
            val intent = Intent(this, DeviceOperatorReportSelectionActivity::class.java)
            intent.putExtra(ApiConstants.REPORT_STAGING, true)
            startActivity(intent)
        }

        binding?.rlFieldTallySummeryReport?.setOnClickListener {
            val intent = Intent(this, FieldTallySummarySheetReportSelectionActivity::class.java)
            startActivity(intent)
        }

        binding?.rlSelectWorkingRollFilter?.setOnClickListener {
            showSelectWardForWorkingRollFilterDialog()
        }

        binding?.rlDownloadData?.setOnClickListener {
            showDownloadPreliminaryDialog()
        }

        binding?.rlDOBAndStatusDiscrepancyReport?.setOnClickListener {
            val intent = Intent(this, StatusDiscrepancyReportSelectionActivity::class.java)
            startActivity(intent)
        }

        binding?.btnHome?.setOnClickListener {
            finish()
        }

        binding?.rlDownloadGeoStructureData?.setOnClickListener {
            downloadGeoStructureDataDilog()
        }           

        binding?.rlDownloadOccupationData?.setOnClickListener {
            if (CommonMethods.checkInternetConnection(application)) {
                downloadOccupationData()
            } else {
                showAlertMessage("Please check internet connection and try again.")
            }
        }

        binding?.rlGenderLessReport?.setOnClickListener {
            startActivity(Intent(this, GenderLessVotersListSelectionActivity::class.java))
        }

        binding?.rlCopyDatabase?.setOnClickListener {
            copyDataBase()
        }

        binding?.rlCopyImages?.setOnClickListener {
            copyImages()
        }

        binding?.rlReverseSync?.setOnClickListener {
            checkWardOrLocationAndProceed()
        }

        /*binding?.rlExport?.setOnClickListener {
            ShareUtils.extractDatabaseAndShare(application){
                val filePath =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                        .toString() + "/" + "Database" + "/"

                val reportsDirectory = File(filePath)
                if (!reportsDirectory.exists())
                    reportsDirectory.mkdirs()
                var outputChannel: FileChannel? = null
                var inputChannel: FileChannel? = null
                val newFile = File(reportsDirectory,it?.name?:"PNGECDatabase")
                try {
                    outputChannel = FileOutputStream(newFile).channel
                    inputChannel = FileInputStream(it).channel
                    inputChannel.transferTo(0, inputChannel.size(), outputChannel)
                    inputChannel.close()
                    it?.delete()
                } finally {
                    inputChannel?.close()
                    outputChannel?.close()
                }
            }
        }*/
    }

    private fun checkWardOrLocationAndProceed() {

        if (provinceName == "NATIONAL CAPITAL DISTRICT") {
            if (securedSharedPreferenceWrapper.getString(
                    ApiConstants.DPO_WORKING_ROLL_LOCATION,
                    ""
                )
                    .isNotEmpty()
            )
                startActivity(Intent(this, ReverseSyncActivity::class.java))
            else
                showAlertMessage(getString(R.string.please_set_the_location_using_working_roll_filter_in_settings_menu))
        } else {
            if (securedSharedPreferenceWrapper.getString(
                    ApiConstants.DPO_WORKING_ROLL_WARD,
                    ""
                )
                    .isNotEmpty()
            )
                startActivity(Intent(this, ReverseSyncActivity::class.java))
            else
                showAlertMessage(getString(R.string.please_set_the_ward_using_working_roll_filter_in_settings_menu))
        }

    }


    private fun showAlertMessage(alertMessage: String) {
        alert(alertMessage) {
            okButton {
            }
        }.show()

    }

    private val mSelectedCalender = Calendar.getInstance()
    private val mDisplayDateFormat =
        SimpleDateFormat("dd-MM-yyyy", Locale.US)

    private var bindingDatePicker: DialogDatePickerBinding? = null
    private var mAlertDatePickerDialog: AlertDialog? = null

    private fun selectLastDateOfEnrolment(
        selectedDate: String,
        selectedDateType: String,
        successMessage: String
    ) {

        val builder = AlertDialog.Builder(this, R.style.full_screen_dialog)
        bindingDatePicker = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_date_picker, null, false
        ) as DialogDatePickerBinding
        builder.setView(bindingDatePicker?.root)
        mAlertDatePickerDialog = builder.create()
        mAlertDatePickerDialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        if (selectedDateType == ApiConstants.ISSUE_OF_WRIT_DATE) {
            val calMinDate = Calendar.getInstance()
            calMinDate.add(Calendar.DATE, 1)
            bindingDatePicker?.datePicker!!.minDate = calMinDate.timeInMillis

            val calMaxDate = Calendar.getInstance()
            calMaxDate.add(Calendar.YEAR, 100)
            bindingDatePicker?.datePicker!!.maxDate = calMaxDate.timeInMillis
        } else {
            val calMinDate = Calendar.getInstance()
            calMinDate.add(Calendar.YEAR, -100)
            bindingDatePicker?.datePicker!!.minDate = calMinDate.timeInMillis

            val cal = Calendar.getInstance()
            bindingDatePicker?.datePicker!!.maxDate = cal.timeInMillis
        }

        if (selectedDate.isNotEmpty()) {
            val year = selectedDate.split("-")[2].toInt()
            val month = selectedDate.split("-")[1].toInt() - 1
            val day = selectedDate.split("-")[0].toInt()
            bindingDatePicker?.datePicker!!.updateDate(year, month, day)
        }


        bindingDatePicker?.tvCancel?.setOnClickListener {
            mAlertDatePickerDialog!!.dismiss()
        }

        bindingDatePicker?.tvOk?.setOnClickListener {

            mSelectedCalender[Calendar.YEAR] = bindingDatePicker?.datePicker!!.year
            mSelectedCalender[Calendar.MONTH] = bindingDatePicker?.datePicker!!.month
            mSelectedCalender[Calendar.DAY_OF_MONTH] = bindingDatePicker?.datePicker!!.dayOfMonth
            saveIssueOfWritLastDateOfEnrolmentAndProvisionalCutOffDate(mSelectedCalender.time)
            showAlertMessage(successMessage)
            mAlertDatePickerDialog!!.dismiss()

        }

        mAlertDatePickerDialog!!.setOnDismissListener {
        }

        mAlertDatePickerDialog?.show()
    }

    private fun saveIssueOfWritLastDateOfEnrolmentAndProvisionalCutOffDate(time: Date) {
        securedSharedPreferenceWrapper.putString(
            ApiConstants.ISSUE_OF_WRIT_DATE,
            mDisplayDateFormat.format(time)
        )

        val ldeCalendar = Calendar.getInstance()
        ldeCalendar.time = time
        ldeCalendar.add(Calendar.YEAR, -18)
        securedSharedPreferenceWrapper.putString(
            "LAST_DATE_OF_ENROLMENT",
            mDisplayDateFormat.format(ldeCalendar.time)
        )

        val pcdCalendar = Calendar.getInstance()
        pcdCalendar.time = ldeCalendar.time
        pcdCalendar.add(Calendar.YEAR, -1)
        securedSharedPreferenceWrapper.putString(
            ApiConstants.PROVISIONAL_CUT_OFF_DATE,
            mDisplayDateFormat.format(pcdCalendar.time)
        )
    }

    private var bindingSelectWardForWorkingRollFilter: DialogWardSelectionBinding? = null
    private var mSelectWardForWorkingRollFilterAlertDialog: AlertDialog? = null


    private fun showSelectWardForWorkingRollFilterDialog() {

        val builder = AlertDialog.Builder(this, R.style.full_screen_dialog)
        bindingSelectWardForWorkingRollFilter = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_ward_selection, null, false
        ) as DialogWardSelectionBinding
        builder.setView(bindingSelectWardForWorkingRollFilter?.root)

        mSelectWardForWorkingRollFilterAlertDialog = builder.create()
        mSelectWardForWorkingRollFilterAlertDialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        bindingSelectWardForWorkingRollFilter?.etRegion?.text = regionName
        bindingSelectWardForWorkingRollFilter?.etProvince?.text = provinceName
        bindingSelectWardForWorkingRollFilter?.etDistrict?.text = districtName

        when (securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "")) {
            ApiConstants.ROLE_DPO -> {
                bindingSelectWardForWorkingRollFilter?.llRegion?.visibility = View.VISIBLE
                bindingSelectWardForWorkingRollFilter?.llProvince?.visibility = View.VISIBLE
            }

            ApiConstants.ROLE_MKA -> {
                bindingSelectWardForWorkingRollFilter?.llRegion?.visibility = View.GONE
                bindingSelectWardForWorkingRollFilter?.llProvince?.visibility = View.GONE
            }

            else -> {
                bindingSelectWardForWorkingRollFilter?.llRegion?.visibility = View.VISIBLE
                bindingSelectWardForWorkingRollFilter?.llProvince?.visibility = View.VISIBLE
            }
        }


        if (llgName.isNotEmpty()) {
            bindingSelectWardForWorkingRollFilter?.etLLG?.text = llgName
        }

        if (wardName.isNotEmpty()) {
            bindingSelectWardForWorkingRollFilter?.etWard?.text = wardNoAndName
        }

        if (locationName.isNotEmpty()) {
            bindingSelectWardForWorkingRollFilter?.etLocation?.text = locationName
        }

        if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_LLG, "").isNotEmpty()) {
            bindingSelectWardForWorkingRollFilter?.ivLLG?.visibility = View.GONE
        }

        if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_WARD, "").isNotEmpty()) {
            bindingSelectWardForWorkingRollFilter?.ivWard?.visibility = View.GONE
        }

        if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "") == ApiConstants.ROLE_DPO) {
            if (provinceName == "NATIONAL CAPITAL DISTRICT") {
                bindingSelectWardForWorkingRollFilter?.llLocation?.visibility = View.VISIBLE
            } else {
                bindingSelectWardForWorkingRollFilter?.llLocation?.visibility = View.GONE
            }
        }else{
            bindingSelectWardForWorkingRollFilter?.llLocation?.visibility = View.GONE
        }

        bindingSelectWardForWorkingRollFilter?.rlLLG?.setOnClickListener {
            if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_LLG, "").isEmpty()) {
                CommonMethods.showProgress(this)
                getLLGListUsingCoRoutines(true)
            }
        }

        bindingSelectWardForWorkingRollFilter?.rlWard?.setOnClickListener {
            if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_WARD, "").isEmpty()) {
                if (bindingSelectWardForWorkingRollFilter?.etLLG?.text.toString().isEmpty()) {
                    showAlertMessage(
                        "Please select llg"
                    )
                } else {
                    if (llgListArray!!.isNotEmpty() && bindingSelectWardForWorkingRollFilter?.etLLG?.tag != null)
                        llgId =
                            llgListArray!![bindingSelectWardForWorkingRollFilter?.etLLG?.tag as Int].llgId

                    CommonMethods.showProgress(this)
                    getWardNoAndNameListUsingCoRoutines(true)
                }
            }
        }

        bindingSelectWardForWorkingRollFilter?.rlLocation?.setOnClickListener {
            if (bindingSelectWardForWorkingRollFilter?.etWard?.text.toString().isEmpty()) {
                showAlertMessage(
                    "Please select ward"
                )
            } else {
                CommonMethods.showProgress(this)
                getLocationListFromDB()
            }
        }

        bindingSelectWardForWorkingRollFilter?.cardDone?.setOnClickListener {
            saveWorkingRollFilterValues()
        }

        bindingSelectWardForWorkingRollFilter?.ivClose?.setOnClickListener {
            mSelectWardForWorkingRollFilterAlertDialog!!.dismiss()
        }

        mSelectWardForWorkingRollFilterAlertDialog?.show()

    }


    private fun getLLGListUsingCoRoutines(wardFilter: Boolean) {
        launch(Dispatchers.Main) {
            val result: List<LLGList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.llgListDao()
                    ?.getLLGOfRegionAndProvisionAndDistrict(
                        regionId,
                        provinceId, districtId
                    )
            }
            showLLGOptions(result, wardFilter)
        }
    }


    var llgListArray: ArrayList<LLGList>? = ArrayList()
    private fun showLLGOptions(result: List<LLGList>?, wardFilter: Boolean) {
        llgListArray = ArrayList(result!!)
        if (wardFilter)
            showSingleSelectionDialog(
                CommonMethods.getLLGList(llgListArray!!),
                bindingSelectWardForWorkingRollFilter?.etLLG!!,
                "LLG"
            ) else
            showSingleSelectionDialog(
                CommonMethods.getLLGList(llgListArray!!),
                bindingDownloadPreliminaryDialog?.etLLG!!,
                "LLG_PRELIMINARY"
            )

    }

    private fun getMKALlgListFromDB() {
        mkaLlgArray.clear()
        launch(Dispatchers.Main) {
            val result: MKAGeoListData? = withContext(Dispatchers.IO) {
                dataBaseInstance.geoListDao()
                    ?.getDataForDistrict(districtId)
            }
            mkaLlgArray = Gson().fromJson<ArrayList<MKAGeoListModel.GeoListData.LlG>>(
                result?.llGs ?: "",
                object : TypeToken<ArrayList<MKAGeoListModel.GeoListData.LlG>?>() {}.type
            ) ?: kotlin.run { arrayListOf() }
            showMKALlg(mkaLlgArray)
        }
    }

    /**Method to show scrollable spinner dialog for mka llg.*/
    private fun showMKALlg(llgListArray: ArrayList<MKAGeoListModel.GeoListData.LlG>) {
        CommonMethods.hideProgress()
        DialogSpinner.with(this, ArrayList(llgListArray)).setEnableSearch(false)
            .setMap { value: MKAGeoListModel.GeoListData.LlG -> value.llgName ?: "" }
            .setOnValueSelectedCallback { model ->
                bindingDownloadPreliminaryDialog?.etLLG?.text = model.llgName ?: ""
                llgId = model.llgId ?: 0
                wardId = 0
                mkaWardArray.clear()
                bindingDownloadPreliminaryDialog?.etWard?.text = ""
                locationId = 0
                mkaLocationArray.clear()
                bindingDownloadPreliminaryDialog?.etLocation?.text = ""
            }.build().show(fragmentManager = supportFragmentManager)
    }

    private fun getWardNoAndNameListUsingCoRoutines(llgAssigned: Boolean) {
        launch(Dispatchers.Main) {
            val result: List<WardList>? = withContext(Dispatchers.IO) {
                if (llgAssigned) {
                    dataBaseInstance.wardListDao()
                        ?.getWardListOfRegionAndProvinceAndDistrictAndLLG(
                            regionId,
                            provinceId,
                            districtId,
                            llgId
                        )
                } else {
                    dataBaseInstance.wardListDao()
                        ?.getWardListOfRegionAndProvinceAndDistrictAndLLG(
                            regionId,
                            provinceId,
                            districtId,
                            llgListArray!![bindingSelectWardForWorkingRollFilter?.etLLG?.tag as Int].llgId
                        )
                }
            }
            showWardNoAndNameOptions(result)
        }
    }

    var wardNoAndNameArray: ArrayList<WardList>? = ArrayList()
    private fun showWardNoAndNameOptions(result: List<WardList>?) {
        wardNoAndNameArray = ArrayList(result!!)
        showSingleSelectionDialog(
            CommonMethods.getWardList(wardNoAndNameArray!!),
            bindingSelectWardForWorkingRollFilter?.etWard!!,
            "WardNoAndName"
        )

    }

    private fun getLocationListFromDB() {
        launch(Dispatchers.Main) {
            val result: List<LocationList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.locationListDao()
                    ?.getLocationListOfRegionAndProvinceAndDistrictAndLLGAndWard(
                        regionId,
                        provinceId,
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
            bindingSelectWardForWorkingRollFilter?.etLocation!!,
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
                        bindingSelectWardForWorkingRollFilter?.etWard?.tag = null
                        bindingSelectWardForWorkingRollFilter?.etWard?.text = ""
                        wardNoAndNameArray?.clear()

                        bindingSelectWardForWorkingRollFilter?.etLocation?.tag = null
                        bindingSelectWardForWorkingRollFilter?.etLocation?.text = ""
                        locationArray?.clear()

                        wardId = 0
                        locationId = 0

                        wardName = ""
                        locationName = ""
                    }

                    "LLG_PRELIMINARY" -> {

                        downloadPreliminaryllgId = llgListArray!![clickItem].llgId
                        bindingDownloadPreliminaryDialog?.etWard?.tag = null
                        bindingDownloadPreliminaryDialog?.etWard?.text = ""
                        wardNoAndNameArrayForPreliminaryDownload?.clear()

                        bindingDownloadPreliminaryDialog?.etLocation?.tag = null
                        bindingDownloadPreliminaryDialog?.etLocation?.text = ""
                        locationArrayForPreliminaryDownload?.clear()

                        downloadPreliminarywardId = 0
                        downloadPreliminarylocationId = 0
                    }

                    "WardNoAndName" -> {
                        wardId = wardNoAndNameArray!![clickItem].wardId
                        bindingSelectWardForWorkingRollFilter?.etLocation?.tag = null
                        bindingSelectWardForWorkingRollFilter?.etLocation?.text = ""
                        locationArray?.clear()

                        locationId = 0
                        locationName = ""
                    }

                    "WardNoAndName_PRELIMINARY" -> {
                        downloadPreliminarywardId =
                            wardNoAndNameArrayForPreliminaryDownload!![clickItem].wardId
                        bindingDownloadPreliminaryDialog?.etLocation?.tag = null
                        bindingDownloadPreliminaryDialog?.etLocation?.text = ""
                        locationArrayForPreliminaryDownload?.clear()

                        downloadPreliminarylocationId = 0
                    }

                    "Location" -> {

                        locationId = locationArray!![clickItem].locationId
                        locationName = singleChoiceItems[clickItem]

                    }

                    "Location_PRELIMINARY" -> {
                        downloadPreliminarylocationId =
                            locationArrayForPreliminaryDownload!![clickItem].locationId
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

    private fun saveWorkingRollFilterValues() {

        llgName = bindingSelectWardForWorkingRollFilter?.etLLG?.text.toString()
        if (llgListArray!!.isNotEmpty() && llgName.isNotEmpty() && bindingSelectWardForWorkingRollFilter?.etLLG?.tag != null
        ) {
            llgId = llgListArray!![bindingSelectWardForWorkingRollFilter?.etLLG?.tag as Int].llgId
        }

        wardNoAndName = bindingSelectWardForWorkingRollFilter?.etWard?.text.toString()
        if (wardNoAndNameArray!!.isNotEmpty() && wardNoAndName.isNotEmpty() && bindingSelectWardForWorkingRollFilter?.etWard?.tag != null) {
            wardId =
                wardNoAndNameArray!![bindingSelectWardForWorkingRollFilter?.etWard?.tag as Int].wardId
            wardName =
                wardNoAndNameArray!![bindingSelectWardForWorkingRollFilter?.etWard?.tag as Int].wardName
        }

        when {
            llgName.isEmpty() -> {
                showAlertMessage(
                    "Please select llg"
                )
            }

            wardNoAndName.isEmpty() -> {
                showAlertMessage(
                    "Please select ward"
                )
            }

            securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "") == ApiConstants.ROLE_DPO && provinceName == "NATIONAL CAPITAL DISTRICT" && locationName.isEmpty() -> {
                showAlertMessage(
                    "Please select location"
                )
            }

            else -> {

                launch(Dispatchers.Main) {
                    withContext(Dispatchers.IO) {
                        dataBaseInstance.dpoListDao().updateDPOWorkingRollLLGId(
                            loggedInDPOUserData.userId.toInt(),
                            llgId
                        )

                        dataBaseInstance.dpoListDao().updateDPOWorkingRollLLGName(
                            loggedInDPOUserData.userId.toInt(),
                            llgName
                        )

                        dataBaseInstance.dpoListDao().updateDPOWorkingRollWardId(
                            loggedInDPOUserData.userId.toInt(),
                            wardId
                        )

                        dataBaseInstance.dpoListDao().updateDPOWorkingRollWardName(
                            loggedInDPOUserData.userId.toInt(),
                            wardName
                        )

                        dataBaseInstance.dpoListDao().updateDPOWorkingRollLocationId(
                            loggedInDPOUserData.userId.toInt(),
                            locationId
                        )

                        dataBaseInstance.dpoListDao().updateDPOWorkingRollLocationName(
                            loggedInDPOUserData.userId.toInt(),
                            locationName
                        )
                    }

                    securedSharedPreferenceWrapper.putInt(
                        ApiConstants.DPO_WORKING_ROLL_LLG_ID,
                        llgId
                    )

                    securedSharedPreferenceWrapper.putString(
                        ApiConstants.DPO_WORKING_ROLL_LLG,
                        llgName
                    )

                    securedSharedPreferenceWrapper.putInt(
                        ApiConstants.DPO_WORKING_ROLL_WARD_ID,
                        wardId
                    )

                    securedSharedPreferenceWrapper.putString(
                        ApiConstants.DPO_WORKING_ROLL_WARD,
                        wardName
                    )

                    securedSharedPreferenceWrapper.putInt(
                        ApiConstants.DPO_WORKING_ROLL_LOCATION_ID,
                        locationId
                    )

                    securedSharedPreferenceWrapper.putString(
                        ApiConstants.DPO_WORKING_ROLL_LOCATION,
                        locationName
                    )

                    securedSharedPreferenceWrapper.putInt(
                        ApiConstants.DPO_WORKING_ROLL_MKA_LLG_ID,
                        llgId
                    )

                    securedSharedPreferenceWrapper.putString(
                        ApiConstants.DPO_WORKING_ROLL_MKA_LLG,
                        llgName
                    )

                    securedSharedPreferenceWrapper.putInt(
                        ApiConstants.DPO_WORKING_ROLL_MKA_WARD_ID,
                        wardId
                    )

                    securedSharedPreferenceWrapper.putString(
                        ApiConstants.DPO_WORKING_ROLL_MKA_WARD,
                        wardName
                    )

                    securedSharedPreferenceWrapper.putInt(
                        ApiConstants.DPO_WORKING_ROLL_MKA_LOCATION_ID,
                        locationId
                    )

                    securedSharedPreferenceWrapper.putString(
                        ApiConstants.DPO_WORKING_ROLL_MKA_LOCATION,
                        locationName
                    )

                    ApiConstants.HOME_FILTER_LOCATION_ID = locationId
                    ApiConstants.HOME_FILTER_LOCATION = locationName
                    ApiConstants.HOME_FILTER_MKA_LOCATION_ID = locationId
                    ApiConstants.HOME_FILTER_MKA_LOCATION = locationName

                    setFooterData()
                    alert(getString(R.string.workingr_Roll_filter_settings_saved_successfully)) {
                        okButton {
                            mSelectWardForWorkingRollFilterAlertDialog!!.dismiss()
                        }
                    }.show()

                }


            }
        }
    }

    private var bindingDownloadPreliminaryDialog: DialogDownloadPreliminaryBinding? = null
    private var mSelectDownloadPreliminaryAlertDialog: AlertDialog? = null

    private fun showDownloadPreliminaryDialog() {

        val builder = AlertDialog.Builder(this, R.style.full_screen_dialog)
        bindingDownloadPreliminaryDialog = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_download_preliminary, null, false
        ) as DialogDownloadPreliminaryBinding
        builder.setView(bindingDownloadPreliminaryDialog?.root)

        mSelectDownloadPreliminaryAlertDialog = builder.create()
        mSelectDownloadPreliminaryAlertDialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        bindingDownloadPreliminaryDialog?.etDistrict?.text = districtName

        if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_LLG, "").isNotEmpty()) {
            bindingDownloadPreliminaryDialog?.ivLLG?.visibility = View.GONE
            bindingDownloadPreliminaryDialog?.etLLG?.text =
                securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_LLG, "")
            downloadPreliminaryllgId =
                securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_LLG_ID, 0)
        } else {
            bindingDownloadPreliminaryDialog?.etLLG?.text =
                securedSharedPreferenceWrapper.getString(ApiConstants.PRELIMINARY_ROLL_LLG_NAME, "")
            downloadPreliminaryllgId =
                securedSharedPreferenceWrapper.getInt(ApiConstants.PRELIMINARY_ROLL_LLG_ID, 0)
        }

        if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_WARD, "").isNotEmpty()) {
            bindingDownloadPreliminaryDialog?.ivWard?.visibility = View.GONE
            bindingDownloadPreliminaryDialog?.etWard?.text =
                securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_WARD, "")
            downloadPreliminarywardId =
                securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_WARD_ID, 0)
        } else {
            bindingDownloadPreliminaryDialog?.etWard?.text =
                securedSharedPreferenceWrapper.getString(
                    ApiConstants.PRELIMINARY_ROLL_WARD_NAME,
                    ""
                )
            downloadPreliminarywardId =
                securedSharedPreferenceWrapper.getInt(ApiConstants.PRELIMINARY_ROLL_WARD_ID, 0)
        }

        bindingDownloadPreliminaryDialog?.etLocation?.text =
            securedSharedPreferenceWrapper.getString(
                ApiConstants.PRELIMINARY_ROLL_LOCATION_NAME,
                ""
            )
        downloadPreliminarylocationId =
            securedSharedPreferenceWrapper.getInt(ApiConstants.PRELIMINARY_ROLL_LOCATION_ID, 0)

        bindingDownloadPreliminaryDialog?.rlLLG?.setOnClickListener {
            if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_LLG, "").isEmpty()) {
                CommonMethods.showProgress(this)
                if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
                    getLLGListUsingCoRoutines(false)
                }else{
                    getMKALlgListFromDB()
                }
            }
        }

        bindingDownloadPreliminaryDialog?.rlWard?.setOnClickListener {

            if (securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_WARD, "").isEmpty()) {

                if (bindingDownloadPreliminaryDialog?.etLLG?.text.toString().isEmpty()) {
                    showAlertMessage(
                        "Please select llg"
                    )
                } else {
                    if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
                        if (llgListArray!!.isNotEmpty() && bindingDownloadPreliminaryDialog?.etLLG?.tag != null)
                            downloadPreliminaryllgId =
                                llgListArray!![bindingDownloadPreliminaryDialog?.etLLG?.tag as Int].llgId

                        CommonMethods.showProgress(this)
                        getWardNoAndNameListUsingCoRoutinesForPreliminaryDownload()
                    }else{
                        CommonMethods.showProgress(this)
                        getMKAWardList()
                    }
                }
            }


        }

        bindingDownloadPreliminaryDialog?.rlLocation?.setOnClickListener {
            if (bindingDownloadPreliminaryDialog?.etWard?.text.toString().isEmpty()) {
                showAlertMessage(
                    "Please select ward"
                )
            } else {
                CommonMethods.showProgress(this)
                if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
                    getLocationListFromDBForPreliminaryDownload()
                }else{
                    getMKALocationList()
                }
            }

        }

        bindingDownloadPreliminaryDialog?.cardDone?.setOnClickListener {
            mSelectDownloadPreliminaryAlertDialog!!.dismiss()
            checkVotersPendingToSync()
        }

        bindingDownloadPreliminaryDialog?.ivClose?.setOnClickListener {
            mSelectDownloadPreliminaryAlertDialog!!.dismiss()
        }

        mSelectDownloadPreliminaryAlertDialog?.show()

    }

    private fun checkVotersPendingToSync() {
        launch(Dispatchers.Main) {
            val result: List<VotersList>? = withContext(Dispatchers.IO) {
                getVotersListToSync()
            }
            proceedToDownloadPreliminaryData(result)
        }
    }

    private fun proceedToDownloadPreliminaryData(result: List<VotersList>?) {

        if (result!!.isEmpty()) {
            securedSharedPreferenceWrapper.putInt(
                ApiConstants.PRELIMINARY_ROLL_LLG_ID,
                downloadPreliminaryllgId
            )
            securedSharedPreferenceWrapper.putInt(
                ApiConstants.PRELIMINARY_ROLL_WARD_ID,
                downloadPreliminarywardId
            )
            securedSharedPreferenceWrapper.putInt(
                ApiConstants.PRELIMINARY_ROLL_LOCATION_ID,
                downloadPreliminarylocationId
            )

            securedSharedPreferenceWrapper.putString(
                ApiConstants.PRELIMINARY_ROLL_LLG_NAME,
                bindingDownloadPreliminaryDialog?.etLLG?.text.toString()
            )
            securedSharedPreferenceWrapper.putString(
                ApiConstants.PRELIMINARY_ROLL_WARD_NAME,
                bindingDownloadPreliminaryDialog?.etWard?.text.toString()
            )
            securedSharedPreferenceWrapper.putString(
                ApiConstants.PRELIMINARY_ROLL_LOCATION_NAME,
                bindingDownloadPreliminaryDialog?.etLocation?.text.toString()
            )

            downloadPreliminaryRollData()
        } else {
            alert("The voters which are updated yet to sync with server. Please sync the voters with server before you download data!") {
                okButton {
                }
            }.show()
        }

    }

    private fun getVotersListToSync(): List<VotersList>? {
        when {
            downloadPreliminaryllgId == 0 -> {
                return dataBaseInstance.votersListDao().checkDistrictWiseUpdatedVotersListToSync(
                    districtId.toString()
                )
            }

            downloadPreliminarywardId == 0 -> {
                return dataBaseInstance.votersListDao().checkLLGWiseUpdatedVotersListToSync(
                    districtId.toString(),
                    downloadPreliminaryllgId.toString()
                )
            }

            downloadPreliminarylocationId == 0 -> {
                return dataBaseInstance.votersListDao().checkWardWiseUpdatedVotersListToSync(
                    districtId.toString(),
                    downloadPreliminaryllgId.toString(),
                    downloadPreliminarywardId.toString()
                )
            }

            else -> {
                return dataBaseInstance.votersListDao().checkLocationWiseUpdatedVotersListToSync(
                    districtId.toString(),
                    downloadPreliminaryllgId.toString(),
                    downloadPreliminarywardId.toString(),
                    downloadPreliminarylocationId.toString()
                )
            }
        }

    }

    private fun getWardNoAndNameListUsingCoRoutinesForPreliminaryDownload() {
        launch(Dispatchers.Main) {
            val result: List<WardList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.wardListDao()
                    ?.getWardListOfRegionAndProvinceAndDistrictAndLLG(
                        regionId,
                        provinceId,
                        districtId,
                        downloadPreliminaryllgId
                    )

            }
            showWardNoAndNameOptionsForPreliminaryDownload(result)
        }
    }

    var wardNoAndNameArrayForPreliminaryDownload: ArrayList<WardList>? = ArrayList()
    private fun showWardNoAndNameOptionsForPreliminaryDownload(result: List<WardList>?) {
        wardNoAndNameArrayForPreliminaryDownload = ArrayList(result!!)
        showSingleSelectionDialog(
            CommonMethods.getWardList(wardNoAndNameArrayForPreliminaryDownload!!),
            bindingDownloadPreliminaryDialog?.etWard!!,
            "WardNoAndName_PRELIMINARY"
        )

    }

    /**Method to get mkaward list form mka llg array.*/
    private fun getMKAWardList() {
        mkaWardArray.clear()
        launch(Dispatchers.Main) {
            val result = mkaLlgArray.filter { llg ->
                llg.llgId == llgId
            }
            if (result.isNotEmpty())
                mkaWardArray.addAll(result.single().wards ?: arrayListOf())
            showMKAWard(mkaWardArray)
        }
    }

    /**Method to show scrollable spinner dialog for mka ward.*/
    private fun showMKAWard(wardListArray: ArrayList<MKAGeoListModel.GeoListData.LlG.Ward>) {
        CommonMethods.hideProgress()
        DialogSpinner.with(this, ArrayList(wardListArray)).setEnableSearch(false)
            .setMap { value: MKAGeoListModel.GeoListData.LlG.Ward -> value.wardName ?: "" }
            .setOnValueSelectedCallback { model ->
                bindingDownloadPreliminaryDialog?.etWard?.text = model.wardName ?: ""
                wardId = model.wardId ?: 0
                locationId = 0
                mkaLocationArray.clear()
                bindingDownloadPreliminaryDialog?.etLocation?.text = ""
            }.build().show(fragmentManager = supportFragmentManager)
    }

    private fun getLocationListFromDBForPreliminaryDownload() {
        launch(Dispatchers.Main) {
            val result: List<LocationList>? = withContext(Dispatchers.IO) {
                dataBaseInstance.locationListDao()
                    ?.getLocationListOfRegionAndProvinceAndDistrictAndLLGAndWard(
                        regionId,
                        provinceId,
                        districtId,
                        downloadPreliminaryllgId,
                        downloadPreliminarywardId
                    )
            }
            showLocationOptionsForPreliminaryDownload(result)
        }
    }


    private var locationArrayForPreliminaryDownload: ArrayList<LocationList>? = ArrayList()
    private fun showLocationOptionsForPreliminaryDownload(result: List<LocationList>?) {
        locationArrayForPreliminaryDownload = ArrayList(result!!)
        showSingleSelectionDialog(
            CommonMethods.getLocationList(locationArrayForPreliminaryDownload!!),
            bindingDownloadPreliminaryDialog?.etLocation!!,
            "Location_PRELIMINARY"
        )

    }

    /**Method to get mkalocation list form mka ward array.*/
    private fun getMKALocationList() {
        mkaLocationArray.clear()
        launch(Dispatchers.Main) {
            val result = mkaWardArray.filter { llg ->
                llg.wardId == wardId
            }
            if (result.isNotEmpty())
                mkaLocationArray.addAll(result.single().locations ?: arrayListOf())
            showMKALocation(mkaLocationArray)
        }
    }

    /**Method to show scrollable spinner dialog for mka location.*/
    private fun showMKALocation(locationListArray: ArrayList<MKAGeoListModel.GeoListData.LlG.Ward.Location>) {
        CommonMethods.hideProgress()
        DialogSpinner.with(this, ArrayList(locationListArray)).setEnableSearch(false)
            .setMap { value: MKAGeoListModel.GeoListData.LlG.Ward.Location ->
                value.locationName ?: ""
            }
            .setOnValueSelectedCallback { model ->
                bindingDownloadPreliminaryDialog?.etLocation?.text = model.locationName ?: ""
                locationId = model.locationId ?: 0
            }.build().show(fragmentManager = supportFragmentManager)
    }

    private fun downloadPreliminaryRollData() {
        if (CommonMethods.checkInternetConnection(application)) {
            securedSharedPreferenceWrapper.putBoolean(
                ApiConstants.PRELIMINARY_DOWNLOAD_IN_PROGRESS,
                true
            )
            securedSharedPreferenceWrapper.putInt(ApiConstants.PRELIMINARY_PAGENO, 1)
            securedSharedPreferenceWrapper.putInt(
                ApiConstants.PRELIMINARY_DOWNLOADED_PERCENTAGE,
                0
            )
            showDownloadProgressDialog()
            startRequiredService(this, DownloadPreliminaryRollSyncService::class.java)
        } else {
            showAlertMessage("Please check internet connection and try again.")
        }
    }

    private var bindingLayoutLoaderDownloadProgressBar: LayoutLoaderDownloadProgressBarBinding? =
        null
    var mDownloadProgressDialog: AlertDialog? = null
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

        val percentage =
            securedSharedPreferenceWrapper.getInt(ApiConstants.PRELIMINARY_DOWNLOADED_PERCENTAGE, 1)
        bindingLayoutLoaderDownloadProgressBar?.downloadProgressBar!!.progress = percentage
        bindingLayoutLoaderDownloadProgressBar?.tvDownloadPercentage!!.text =
            "Downloaded: $percentage%"

    }

    private val singlePageDownloadedReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val totalRecords = intent.getIntExtra("Total_Records", 0)
            val downloadedRecords = intent.getIntExtra("Downloaded_Records", 0)
            val percentage = (downloadedRecords.toDouble() / totalRecords.toDouble() * 100).toInt()
            securedSharedPreferenceWrapper.putInt(
                ApiConstants.PRELIMINARY_DOWNLOADED_PERCENTAGE,
                percentage
            )
            if (mDownloadProgressDialog != null && mDownloadProgressDialog!!.isShowing) {
                if (percentage != 100) {
                    bindingLayoutLoaderDownloadProgressBar?.downloadProgressBar!!.progress =
                        percentage
                    bindingLayoutLoaderDownloadProgressBar?.tvDownloadPercentage!!.text =
                        "Downloaded: $percentage%"
                }
            }

        }
    }

    private val allPageDownloadReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (mDownloadProgressDialog != null && mDownloadProgressDialog!!.isShowing) {
                bindingLayoutLoaderDownloadProgressBar?.downloadProgressBar!!.progress = 100
                bindingLayoutLoaderDownloadProgressBar?.tvDownloadPercentage!!.text =
                    "Downloaded: 100%"
            }
            hideProgressAndNavigateToNextScreen()
        }
    }

    var showingInternetConnectionMessage = false
    private val internetConnectionStatusReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            showAlertMessage("Please check internet connection and try again.")
            if (!showingInternetConnectionMessage) {
                showingInternetConnectionMessage = true
                alert("Please check internet connection and try again.") {
                    okButton {
                        showingInternetConnectionMessage = false
                    }
                }.show()
            }
        }
    }

    private fun hideProgressAndNavigateToNextScreen() {
        mDownloadProgressDialog?.dismiss()
        alert("The data has been downloaded successfully") {
            okButton {

            }
        }.show().setCancelable(false)
    }

    private fun startRequiredService(
        activity: Activity,
        serviceClass: Class<*>?
    ) {
        if (CommonMethods.checkInternetConnection(activity)) {

            if (isMyServiceRunning(DownloadPreliminaryRollSyncService::class.java)) {
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

    // Download GeoStructure Data
    private fun downloadGeoStructureData() {
        if (CommonMethods.checkInternetConnection(application)) {
            clearData()
        } else {
            showAlertMessage("Please check internet connection and try again.")
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
                this@SettingsActivity,
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

    private fun downloadOccupationData() {
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
            CommonMethods.hideProgress()
            showAlertMessage("Occupation list downloaded successfully")

        }

    }

    private fun copyDataBase() {
        val fileName = "PNGElectoral"
        val filePath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                .toString() + "/database/"
        val reportsDirectory = File(filePath)
        if (!reportsDirectory.exists())
            reportsDirectory.mkdirs()
        val outputFile = File(filePath, fileName)
        outputFile.createNewFile()
        val data = Environment.getDataDirectory()
        val currentDBPath = "/data/com.pngelectoral/databases/PNGElectoral"
        val currentDB = File(data, currentDBPath)
        if (currentDB.exists()) {
            val src: FileChannel = FileInputStream(currentDB).getChannel()
            val dst: FileChannel = FileOutputStream(outputFile).getChannel()
            dst.transferFrom(src, 0, src.size())
            src.close()
            dst.close()
            Toast.makeText(applicationContext, "Backup is successful ", Toast.LENGTH_SHORT).show()
        }
    }

    // Copy & zip form 11 , voter images
    private fun copyImages() {
        requestStoragePermission()
    }

    private fun requestStoragePermission() {

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
                        CommonMethods.showSettingsDialog(this@SettingsActivity)
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            if (Environment.isExternalStorageManager()) {
                createImagesFilePath()
            } else {
                val intent = Intent(
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                )
                storagePermissionResultLauncher.launch(intent)

            }

        } else {
            createImagesFilePath()
        }
    }

    private lateinit var formElevenImagesFolder: File
    private lateinit var voterImagesFolder: File
    private var sdCardPath = ""
    private lateinit var fileDirectory: File
    private fun createImagesFilePath() {
        CommonMethods.showProgress(this)
        checkImageFilesToCopy()
    }

    val voterImagesList: ArrayList<VotersList> = ArrayList()
    val form11ImagesList: ArrayList<VotersList> = ArrayList()
    private fun checkImageFilesToCopy() {
        launch(Dispatchers.Main) {
            val result: List<VotersList> = withContext(Dispatchers.IO) {
                dataBaseInstance.votersListDao()
                    .getAllForm11AndVoterImages(
                        districtId.toString()
                    )
            }
            if (result.isEmpty()) {
                CommonMethods.hideProgress()
                showAlertMessage("No images to copy!")
            } else {
                CommonMethods.hideProgress()
                showCopyImageProgressDialog()
                voterImagesList.clear()
                form11ImagesList.clear()

                for (voter in result) {
                    if (voter.electorProfileImage != null
                        && voter.electorProfileImage.isNotEmpty()
                    ) {
                        voterImagesList.add(voter)
                    }

                    if (voter.electorFormImage != null
                        && voter.electorFormImage.isNotEmpty()
                    ) {
                        form11ImagesList.add(voter)
                    }
                }

                createImageFolders()

            }

        }
    }

    private fun createImageFolders() {
        sdCardPath = getExternalSdCardPath()
        val parentFileName = "PNGECImages"
//        fileDirectory = if (sdCardPath.isEmpty()) {
//            File(
//                Environment.getExternalStorageDirectory()
//                    .toString() + "/" + parentFileName + "/"
//            )
//        } else {
//            File(
//                "$sdCardPath/$parentFileName/"
//            )
//        }
        fileDirectory =
            File(
                Environment.getExternalStorageDirectory()
                    .toString() + "/" + parentFileName + "/"
            )


        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs()
        }

        formElevenImagesFolder = File(
            fileDirectory,
            "Form11Images"
        )
        if (!formElevenImagesFolder.exists()) {
            formElevenImagesFolder.mkdirs()
        }

        voterImagesFolder = File(
            fileDirectory,
            "VoterImages"
        )
        if (!voterImagesFolder.exists()) {
            voterImagesFolder.mkdirs()
        }

        copyImagesToThePNGECFolder()

    }


    private var currentVoterImage = 0
    private var currentImage = 0
    private var totalImages = 0
    private var currentForm11Image = 0

    private fun copyImagesToThePNGECFolder() {
        currentVoterImage = 0
        currentImage = 0
        currentForm11Image = 0

        totalImages = 0
        totalImages = voterImagesList.size + form11ImagesList.size
        if (voterImagesList.size > 0)
            copyVoterImageUsingCoRoutines(voterImagesList[currentVoterImage])
        else
            copyForm11ImageUsingCoRoutines(form11ImagesList[currentForm11Image])

    }

    private fun copyVoterImageUsingCoRoutines(voter: VotersList) {
        currentImage += 1
        val percentage =
            (currentImage.toDouble() / totalImages.toDouble() * 100).toInt()
        copyImageProgress(percentage)

        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                copyVImageFile(voter)
            }
            currentVoterImage += 1
            if (currentVoterImage == voterImagesList.size) {
                if (form11ImagesList.size > 0)
                    copyForm11ImageUsingCoRoutines(form11ImagesList[currentForm11Image])
                else
                    makeZipFolder()
            } else {
                copyVoterImageUsingCoRoutines(voterImagesList[currentVoterImage])
            }
        }


    }


    private fun copyVImageFile(voter: VotersList) {
        val electorProfileImageFile = File(voter.electorProfileImage.toString())
        if (electorProfileImageFile.exists()) {
            val imageFileName =
                CommonMethods.getVoterImageFileName(voter, this@SettingsActivity)
            val file =
                File(
                    voterImagesFolder,
                    imageFileName
                )

            if (file.exists())
                file.delete()
            file.createNewFile()

            val source: FileChannel? = FileInputStream(electorProfileImageFile).channel
            val destination: FileChannel? = FileOutputStream(file).channel
            if (destination != null && source != null) {
                destination.transferFrom(source, 0, source.size())
            }
            source?.close()
            destination?.close()
        }
    }

    private fun copyForm11ImageUsingCoRoutines(voter: VotersList) {
        currentImage += 1
        val percentage =
            (currentImage.toDouble() / totalImages.toDouble() * 100).toInt()
        copyImageProgress(percentage)

        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                copyF11ImageFile(voter)
            }
            currentForm11Image += 1
            if (currentForm11Image == form11ImagesList.size) {
                makeZipFolder()
            } else {
                copyForm11ImageUsingCoRoutines(form11ImagesList[currentForm11Image])
            }
        }


    }

    private fun copyF11ImageFile(voter: VotersList) {
        val electorFormImageFile = File(voter.electorFormImage.toString())
        if (electorFormImageFile.exists()) {
            val imageFileName =
                CommonMethods.getForm11ImageFileName(voter, this@SettingsActivity)
            val file =
                File(
                    formElevenImagesFolder,
                    imageFileName
                )

            if (file.exists())
                file.delete()
            file.createNewFile()

            val source: FileChannel? = FileInputStream(electorFormImageFile).channel
            val destination: FileChannel? = FileOutputStream(file).channel
            if (destination != null && source != null) {
                destination.transferFrom(source, 0, source.size())
            }
            source?.close()
            destination?.close()
        }
    }

    private fun makeZipFolder() {
        val inputFolderPath = fileDirectory.absolutePath.toString()
        val parentZipFileName = "PNGECImages.zip"
//        val zipFileDirectory = if (sdCardPath.isEmpty()) {
//            File(
//                Environment.getExternalStorageDirectory()
//                    .toString() + "/" + parentZipFileName
//            )
//        } else {
//            File(
//                "$sdCardPath/$parentZipFileName"
//            )
//        }
        val zipFileDirectory =
            File(
                Environment.getExternalStorageDirectory()
                    .toString() + "/" + parentZipFileName
            )
        if (zipFileDirectory.exists())
            zipFileDirectory.delete()
        zipFileDirectory.createNewFile()

        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                zipFileAtPath(inputFolderPath, zipFileDirectory.absolutePath.toString())
            }
            deleteRecursive(fileDirectory)
            hideCopyImageProgress()
            showAlertMessage("Images copied successfully!")
        }
    }

    private fun zipFileAtPath(sourcePath: String?, toLocation: String?) {
        val BUFFER = 2048
        val sourceFile = File(sourcePath)
        try {
            val origin: BufferedInputStream?
            val dest = FileOutputStream(toLocation)
            val out = ZipOutputStream(
                BufferedOutputStream(
                    dest
                )
            )
            if (sourceFile.isDirectory) {
                zipSubFolder(out, sourceFile, sourceFile.parent.length)
            } else {
                val data = ByteArray(BUFFER)
                val fi = FileInputStream(sourcePath)
                origin = BufferedInputStream(fi, BUFFER)
                val entry = ZipEntry(sourcePath?.let { getLastPathComponent(it) })
                entry.setTime(sourceFile.lastModified()) // to keep modification time after unzipping
                out.putNextEntry(entry)
                var count: Int
                while (origin.read(data, 0, BUFFER).also { count = it } != -1) {
                    out.write(data, 0, count)
                }
            }
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    @Throws(IOException::class)
    private fun zipSubFolder(
        out: ZipOutputStream, folder: File,
        basePathLength: Int
    ) {
        val BUFFER = 2048
        val fileList = folder.listFiles()
        var origin: BufferedInputStream? = null
        for (file in fileList) {
            if (file.isDirectory) {
                zipSubFolder(out, file, basePathLength)
            } else {
                val data = ByteArray(BUFFER)
                val unmodifiedFilePath = file.path
                val relativePath = unmodifiedFilePath
                    .substring(basePathLength)
                val fi = FileInputStream(unmodifiedFilePath)
                origin = BufferedInputStream(fi, BUFFER)
                val entry = ZipEntry(relativePath)
                entry.setTime(file.lastModified()) // to keep modification time after unzipping
                out.putNextEntry(entry)
                var count: Int
                while (origin.read(data, 0, BUFFER).also { count = it } != -1) {
                    out.write(data, 0, count)
                }
                origin.close()
            }
        }
    }

    fun getLastPathComponent(filePath: String): String? {
        val segments = filePath.split("/").toTypedArray()
        return if (segments.size == 0) "" else segments[segments.size - 1]
    }

    fun deleteRecursive(fileOrDirectory: File) {
        if (fileOrDirectory.isDirectory) for (child in fileOrDirectory.listFiles()) deleteRecursive(
            child
        )
        fileOrDirectory.delete()
    }

    private var bindingLayoutLoaderCopyImageProgressBar: LayoutLoaderCopyImageProgressBarBinding? =
        null
    var mCopyImageProgressDialog: AlertDialog? = null
    var percentage = 0
    private fun showCopyImageProgressDialog() {

        val builder = AlertDialog.Builder(this)
        bindingLayoutLoaderCopyImageProgressBar = DataBindingUtil.inflate(
            LayoutInflater.from(
                this
            ),
            R.layout.layout_loader_copy_image_progress_bar, null, false
        ) as LayoutLoaderCopyImageProgressBarBinding
        builder.setView(bindingLayoutLoaderCopyImageProgressBar?.root)
        mCopyImageProgressDialog = builder.create()
        mCopyImageProgressDialog?.setCanceledOnTouchOutside(false)
        mCopyImageProgressDialog?.setCancelable(false)
        mCopyImageProgressDialog?.show()

        percentage = 0
        bindingLayoutLoaderCopyImageProgressBar?.downloadProgressBar!!.progress =
            percentage
        bindingLayoutLoaderCopyImageProgressBar?.tvDownloadPercentage!!.text =
            "Copied: $percentage%"


    }

    private fun copyImageProgress(percentage: Int) {
        if (mCopyImageProgressDialog != null && mCopyImageProgressDialog!!.isShowing) {
            if (percentage != 100) {
                bindingLayoutLoaderCopyImageProgressBar?.downloadProgressBar!!.progress =
                    percentage
                bindingLayoutLoaderCopyImageProgressBar?.tvDownloadPercentage!!.text =
                    "Copied: $percentage%"
            }
        }
    }

    private fun hideCopyImageProgress() {
        if (mCopyImageProgressDialog != null && mCopyImageProgressDialog!!.isShowing) {
            mCopyImageProgressDialog!!.dismiss()
        }
    }

    private fun getExternalSdCardPath(): String {

        val storageManager = getSystemService(Context.STORAGE_SERVICE)
        try {
            val storageVolumeClazz = Class.forName("android.os.storage.StorageVolume")
            val getVolumeList = storageManager.javaClass.getMethod("getVolumeList")
            val getPath = storageVolumeClazz.getMethod("getPath")
            val isRemovable = storageVolumeClazz.getMethod("isRemovable")
            val result = getVolumeList.invoke(storageManager) as Array<StorageVolume>
            result.forEach {
                if (isRemovable.invoke(it) as Boolean) {
                    return File(getPath.invoke(it) as String).toString()
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return ""
    }

    /**Method to call api for geoclanlist.*/
    private fun callGeoClanListApi() {
        val call = ApiClient.getLocationsRetrofitInterface()?.callGeoClanListAPI()
        WSClient<ClanListModel>().request(
            this,
            ApiConstants.DPO_GET_GEO_CLAN_LIST,
            call,
            object :
                ISuccessHandler<ClanListModel> {
                override fun successResponse(
                    requestCode: Int,
                    mResponse: ClanListModel
                ) {
                    if (mResponse.status == true && mResponse.data != null) {
                        val allClanListData = mResponse.data
                        if (allClanListData.isNotEmpty()) {
                            downloadGeoClanList(allClanListData, allClanListData.size)
                        } else {
                            securedSharedPreferenceWrapper.putBoolean(
                                ApiConstants.CLAN_LIST_DATA_DOWNLOADED,
                                true
                            )

                            if (securedSharedPreferenceWrapper.getBoolean(
                                    ApiConstants.DPO_VOTERS_DATA_DOWNLOADED,
                                    false
                                ) && securedSharedPreferenceWrapper.getBoolean(
                                    ApiConstants.LOCATION_DATA_DOWNLOADED,
                                    false
                                ) && securedSharedPreferenceWrapper.getBoolean(
                                    ApiConstants.GEO_LIST_DOWNLOADED,
                                    false
                                )
                            ) {
                                securedSharedPreferenceWrapper.putInt(
                                    ApiConstants.DOWNLOADED_PERCENTAGE,
                                    0
                                )
                                securedSharedPreferenceWrapper.putBoolean(
                                    ApiConstants.DOWNLOAD_IN_PROGRESS,
                                    false
                                )
                                securedSharedPreferenceWrapper.putBoolean(
                                    ApiConstants.DPO_SETUP_COMPLETED,
                                    true
                                )
                                val intent = Intent(ApiConstants.ALL_DPO_DATA_COMPLETED_BROADCAST)
                                sendBroadcast(intent)
                            }

                        }
                    }
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {

                }
            })
    }

    /**Method to add data into database table clanlist*/
    private fun downloadGeoClanList(
        clanList: ArrayList<ClanListModel.ClanData>,
        totalCount: Int
    ) {
        val exceptionHandler = CoroutineExceptionHandler { _, t ->
            t.printStackTrace()
        }
        GlobalScope.launch(Dispatchers.Main + exceptionHandler) {
            var result: Int
            withContext(Dispatchers.IO) {
                clanList.forEach { clanModel ->
                    val model = ClanListData()
                    model.clanId = clanModel.clanId
                    model.clanName = clanModel.clanName
                    model.description = clanModel.description
                    model.wardId = clanModel.wardId
                    dataBaseInstance.geoClanListDao().insert(model)
                }
                result = dataBaseInstance.geoClanListDao().all.size
            }

            val progressIntent = Intent(ApiConstants.SINGLE_PAGE_DATA_COMPLETED)
            progressIntent.putExtra("Total_Records", totalCount)
            progressIntent.putExtra("Downloaded_Records", result)
            sendBroadcast(progressIntent)

            if (result == totalCount) {

                securedSharedPreferenceWrapper.putBoolean(
                    ApiConstants.CLAN_LIST_DATA_DOWNLOADED,
                    true
                )

                if (securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.DPO_VOTERS_DATA_DOWNLOADED,
                        false
                    ) && securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.LOCATION_DATA_DOWNLOADED,
                        false
                    ) && securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.GEO_LIST_DOWNLOADED,
                        false
                    )
                ) {
                    securedSharedPreferenceWrapper.putInt(ApiConstants.DOWNLOADED_PERCENTAGE, 0)
                    securedSharedPreferenceWrapper.putBoolean(
                        ApiConstants.DOWNLOAD_IN_PROGRESS,
                        false
                    )
                    securedSharedPreferenceWrapper.putBoolean(
                        ApiConstants.DPO_SETUP_COMPLETED,
                        true
                    )
                    val intent = Intent(ApiConstants.ALL_DPO_DATA_COMPLETED_BROADCAST)
                    sendBroadcast(intent)
                }


            }

        }

    }

    private var bindingAuditSettingScreen: DialogAuditSettingScreenBinding? =
        null
    var mAuditSettingDialog: AlertDialog? = null

    private fun showAuditSettingDialog() {

        val builder = AlertDialog.Builder(this)
        bindingAuditSettingScreen = DataBindingUtil.inflate(
            LayoutInflater.from(
                this
            ),
            R.layout.dialog_audit_setting_screen, null, false
        ) as DialogAuditSettingScreenBinding
        builder.setView(bindingAuditSettingScreen?.root)
        mAuditSettingDialog = builder.create()
        mAuditSettingDialog?.setCanceledOnTouchOutside(false)
        mAuditSettingDialog?.setCancelable(false)
        mAuditSettingDialog?.show()

        bindingAuditSettingScreen?.ivClose?.setOnClickListener {
            mAuditSettingDialog!!.dismiss()
        }

        bindingAuditSettingScreen?.tvLatitude?.text = securedSharedPreferenceWrapper.getString(ApiConstants.LATITUDE, "")
        bindingAuditSettingScreen?.tvLongitude?.text = securedSharedPreferenceWrapper.getString(ApiConstants.LONGITUDE, "")
        bindingAuditSettingScreen?.tvUserName?.text = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_NAME, "")

        bindingAuditSettingScreen?.getLocation?.setOnClickListener {
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

                    bindingAuditSettingScreen?.tvLatitude?.text = securedSharedPreferenceWrapper.getString(ApiConstants.LATITUDE, "")
                    bindingAuditSettingScreen?.tvLongitude?.text = securedSharedPreferenceWrapper.getString(ApiConstants.LONGITUDE, "")

                }
            }
        }

    }

    private fun downloadGeoStructureDataDilog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.updated_GeoStructureData_confirmation)
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { dialog, id ->
                downloadGeoStructureData()
            }
            .setNegativeButton(
                "No"
            ) { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }


}



