package com.pngelectoral.ui.activity

import ApiConstants
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.Api
import com.google.gson.JsonObject
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
import com.pngelectoral.databinding.ActivityDeviceOperatorReportBinding
import com.pngelectoral.databinding.ItemWardVoterBinding
import com.pngelectoral.ui.adapter.GenericAdapter
import com.pngelectoral.ui.model.DeviceOperatorReportModel
import com.pngelectoral.ui.model.VotersList
import com.pngelectoral.ui.retrofit.ApiClient
import com.pngelectoral.ui.retrofit.IFailureHandler
import com.pngelectoral.ui.retrofit.ISuccessHandler
import com.pngelectoral.ui.retrofit.WSClient
import com.pngelectoral.ui.viewModel.RegistrationViewModel
import com.pngelectoral.utility.CommonMethods
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext


class StagingDeviceOperatorReportActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityDeviceOperatorReportBinding? = null
    private var registrationViewModel: RegistrationViewModel? = null

    private var wardVotersListAdapter: GenericAdapter<VotersList, ItemWardVoterBinding>? = null


    private var startDateUTC = ""
    private var endDateUTC = ""

    var provinceId = 0
    var districtId = 0
    var llgId = 0
    var wardId = 0
    var locationId = 0

    var pageNumber = 1
    private var pageSize = 1000

    private var deviceRun = ""
    private var deviceRunInMillis = ""
    private var deviceId = ""
    private var dpo = ""
    private var wardName = ""
    private var locationName = ""

    private var updatedVotersList = mutableListOf<VotersList>()
    private var newVotersList = mutableListOf<VotersList>()
    private var provisionalVotersList = mutableListOf<VotersList>()
    private var deceasedVotersList = mutableListOf<VotersList>()
    private var ghostVotersList = mutableListOf<VotersList>()
    private var duplicateVotersList = mutableListOf<VotersList>()
    private var transferredOutVotersList = mutableListOf<VotersList>()
    private var transferInVotersList = mutableListOf<VotersList>()
    private var underAgeVotersList = mutableListOf<VotersList>()
    private var dpoWardStatusWiseVotersList = mutableListOf<VotersList>()
    private var dpoWardUpdatedRollVotersList = mutableListOf<VotersList>()
    private var dpoWardUpdatedRollVotersListWithSortByGenderAndLname = mutableListOf<VotersList>()
    private var dpoWardNewEnrollmentVotersList = mutableListOf<VotersList>()
    private var wardVotersList = mutableListOf<VotersList>()

    private var dopReportFilesPath = "PNG_Device_Operator_Report"

    lateinit var dataBaseInstance: AppDatabase

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    private lateinit var storagePermissionResultLauncher: ActivityResultLauncher<Intent?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_device_operator_report
        )
        registrationViewModel =
            ViewModelProvider(this).get(RegistrationViewModel::class.java)
        binding?.registrationViewModel = registrationViewModel
        dataBaseInstance = DatabaseClient.getInstance(this).appDatabase
        job = Job()


        if (intent.getStringExtra(ApiConstants.START_DATE)!!.isNotEmpty())
            startDateUTC = getDateInUTC(intent.getStringExtra(ApiConstants.START_DATE)!!)

        if (intent.getStringExtra(ApiConstants.END_DATE)!!.isNotEmpty())
            endDateUTC = getDateInUTC(intent.getStringExtra(ApiConstants.END_DATE)!!)

        provinceId = intent.getIntExtra("provinceId", 0)
        districtId = intent.getIntExtra("districtId", 0)
        llgId = intent.getIntExtra("llgId", 0)
        wardId = intent.getIntExtra("wardId", 0)
        locationId = intent.getIntExtra("locationId", 0)

        locationName = intent.getStringExtra("locationName")!!

        setFooterData()
        setReportHeaderData()
        setOnclickListeners()
        callGetDeviceOperatorApi(true)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            storagePermissionResultLauncher = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (Environment.isExternalStorageManager()) {
                    exportReportPDF()
                }
            }
        }

    }

    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n", "HardwareIds")
    private fun setReportHeaderData() {

        val mSimpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        deviceRun = mSimpleDateFormat.format(Date())
        deviceRunInMillis = System.currentTimeMillis().toString()
        binding?.tvDeviceReportRunTime?.text = deviceRun

        deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        binding?.tvDeviceId?.text = deviceId

        dpo = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_NAME, "")
        binding?.tvDPO?.text = dpo


        val wardNumberAndName = intent.getIntExtra("wardNumber", 0)
            .toString() + " - " + intent.getStringExtra("wardName")

        wardName =
            intent.getStringExtra("provinceName") + "/" + intent.getStringExtra("districtName") + "/" + intent.getStringExtra(
                "llgName"
            ) + "/" + wardNumberAndName

        binding?.tvWardName?.text = "Ward Name: $wardName"


        if (locationName.isNotEmpty()) {
            binding?.tvLocationName?.text = "Location Name: $locationName"
            binding?.tvLocationName?.visibility = View.VISIBLE
        } else {
            binding?.tvLocationName?.visibility = View.GONE
        }
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

    private fun setOnclickListeners() {

        binding?.btnHome?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }

        binding?.btnBack?.setOnClickListener {
            finish()
        }

        binding?.cardPrint?.setOnClickListener {
            requestStoragePermission()
        }
    }

    private fun callGetDeviceOperatorApi(isShowLoading: Boolean) {

        if (CommonMethods.checkInternetConnection(application)) {

            if (isShowLoading)
                CommonMethods.showProgress(this)

            val deviceOperatorReportObject = JsonObject()
            if (startDateUTC.isNotEmpty() && endDateUTC.isNotEmpty()) {
                deviceOperatorReportObject.addProperty(ApiConstants.START_DATE, startDateUTC)
                deviceOperatorReportObject.addProperty(ApiConstants.END_DATE, endDateUTC)
            }
            if (securedSharedPreferenceWrapper.getString(
                    ApiConstants.ROLE,
                    ""
                ) == ApiConstants.ROLE_DPO
            ) {
                deviceOperatorReportObject.addProperty(ApiConstants.REQUESTTYPE, "PNG")
                deviceOperatorReportObject.addProperty(ApiConstants.PROVINCEID, provinceId)
                deviceOperatorReportObject.addProperty(ApiConstants.DISTRICTID, districtId)
                deviceOperatorReportObject.addProperty(ApiConstants.LLGID, llgId)
                deviceOperatorReportObject.addProperty(ApiConstants.WARDID, wardId)
                if (locationId != 0)
                    deviceOperatorReportObject.addProperty(ApiConstants.LOCATION_ID, locationId)
            } else {
                deviceOperatorReportObject.addProperty(ApiConstants.REQUESTTYPE, "MKA")
                deviceOperatorReportObject.addProperty(ApiConstants.MKADISTRICTID, districtId)
                deviceOperatorReportObject.addProperty(ApiConstants.MKALLGID, llgId)
                deviceOperatorReportObject.addProperty(ApiConstants.MKAWARDID, wardId)
                if (locationId != 0)
                    deviceOperatorReportObject.addProperty(ApiConstants.MKALOCATIONID, locationId)
            }
            deviceOperatorReportObject.addProperty(ApiConstants.PAGENO, pageNumber)
            deviceOperatorReportObject.addProperty(ApiConstants.PAGESIZE, pageSize)

            val call =
                ApiClient.getRetrofitInterface()?.callGetDeviceOperatorReportAPI(
                    deviceOperatorReportObject
                )

            WSClient<DeviceOperatorReportModel>().request(
                this,
                ApiConstants.DPO_DEVICE_OPERATOR_REPORT,
                call,
                object :
                    ISuccessHandler<DeviceOperatorReportModel> {
                    override fun successResponse(
                        requestCode: Int,
                        mResponse: DeviceOperatorReportModel
                    ) {

                        if (mResponse.status!! && mResponse.data!!.votersList!!.size > 0) {
                            val transferInVotersListData = mResponse.data

                            if (pageNumber == 1) {
                                wardVotersList =
                                    (transferInVotersListData!!.votersList as MutableList<VotersList>?)!!
                                if (wardVotersList.size == 0) {
                                    CommonMethods.hideProgress()
                                    alert("No data found!") {
                                        okButton { }
                                    }.show()
                                } else {
                                    if (wardVotersList.size == transferInVotersListData.total) {
                                        filterWardData(wardVotersList)
                                    } else {
                                        pageNumber += 1
                                        callGetDeviceOperatorApi(false)
                                    }
                                }
                            } else {
                                wardVotersList.addAll((transferInVotersListData!!.votersList as MutableList<VotersList>?)!!)
                                if (wardVotersList.size == transferInVotersListData.total) {
                                    filterWardData(wardVotersList)
                                } else {
                                    pageNumber += 1
                                    callGetDeviceOperatorApi(false)
                                }

                            }

                        } else {
                            CommonMethods.hideProgress()
                            showAlertMessage("No data found!")
                        }
                    }

                },
                object : IFailureHandler {
                    override fun failureResponse(requestCode: Int, message: String) {
                        CommonMethods.hideProgress()
                        showAlertMessage("Internet connection is not available.Please check and try again")
                    }
                })

        } else {
            showAlertMessage("Internet connection is not available.Please check and try again")
        }

    }

    private fun getDateInUTC(dateInApiFormat: String): String {
        val mSimpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
        val serverDate = mSimpleDateFormat.parse(dateInApiFormat)

        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone("UTC")
        calendar.time = serverDate!!
        val time = calendar.time
//        val outputFmt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
        return mSimpleDateFormat.format(time)
    }


    private fun filterWardData(list: MutableList<VotersList>) {
        wardVotersList = list
        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                filterWardDataAndDisplayStatusCount(list)
            }
            loadData()
        }
    }

    private fun filterWardDataAndDisplayStatusCount(wardVotersList: MutableList<VotersList>) {

        for (wardVoter in wardVotersList) {

            when (wardVoter.status) {
                getString(R.string.updated) -> {
                    updatedVotersList.add(wardVoter)
                }

                getString(R.string.new_status) -> {
                    if (CommonMethods.isProvisionalVoter(
                            wardVoter,
                            securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "")
                        )
                    ) {
                        wardVoter.status = getString(R.string.provisional)
                        provisionalVotersList.add(wardVoter)
                    } else {
                        newVotersList.add(wardVoter)
                    }
                }

                "DECEASED" -> {
                    deceasedVotersList.add(wardVoter)
                }

                "GHOST" -> {
                    ghostVotersList.add(wardVoter)
                }

                "DUPLICATE" -> {
                    duplicateVotersList.add(wardVoter)
                }

                "TRANSFERRED OUT" -> {
                    transferredOutVotersList.add(wardVoter)
                }

                "TRANSFERRED IN" -> {
                    if (CommonMethods.isProvisionalVoter(
                            wardVoter,
                            securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "")
                        )
                    ) {
                        wardVoter.status = getString(R.string.provisional)
                        provisionalVotersList.add(wardVoter)
                    } else {
                        transferInVotersList.add(wardVoter)
                    }
                }

                "UNDER AGE" -> {
                    underAgeVotersList.add(wardVoter)
                }
            }

        }

        // WardUpdatedRollVotersList
        if (updatedVotersList.size > 0) {
            dpoWardUpdatedRollVotersList.addAll(updatedVotersList)
        }

        if (transferredOutVotersList.size > 0) {
            dpoWardUpdatedRollVotersList.addAll(transferredOutVotersList)
        }

        if (deceasedVotersList.size > 0) {
            dpoWardUpdatedRollVotersList.addAll(deceasedVotersList)
        }

        if (ghostVotersList.size > 0) {
            dpoWardUpdatedRollVotersList.addAll(ghostVotersList)
        }

        if (duplicateVotersList.size > 0) {
            dpoWardUpdatedRollVotersList.addAll(duplicateVotersList)
        }

        if (underAgeVotersList.size > 0) {
            dpoWardUpdatedRollVotersList.addAll(underAgeVotersList)
        }

        sortByGender(dpoWardUpdatedRollVotersList)
//        dpoWardUpdatedRollVotersList.sortBy { it.gender }
//        dpoWardUpdatedRollVotersList.reverse()
//        dpoWardUpdatedRollVotersList.sortBy { it.lName }
//        dpoWardUpdatedRollVotersList.sortBy { it.fName }


        // WardNewEnrollmentVotersList
        if (newVotersList.size > 0) {
            dpoWardNewEnrollmentVotersList.addAll(newVotersList)
        }

        if (transferInVotersList.size > 0) {
            dpoWardNewEnrollmentVotersList.addAll(transferInVotersList)
        }

        if (provisionalVotersList.size > 0) {
            dpoWardNewEnrollmentVotersList.addAll(provisionalVotersList)
        }

        dpoWardNewEnrollmentVotersList.sortBy { it.enrolmentFormNumber }

//        if (dpoWardUpdatedRollVotersList.size > 0) {
//            val wardUpdatedRollHeaderItem = VotersList()
//            wardUpdatedRollHeaderItem.isHeader = true
//            wardUpdatedRollHeaderItem.voterName = getString(R.string.ward_updated_roll_list)
//            dpoWardStatusWiseVotersList.add(wardUpdatedRollHeaderItem)
//            dpoWardStatusWiseVotersList.addAll(dpoWardUpdatedRollVotersList)
//        }

        if (dpoWardNewEnrollmentVotersList.size > 0) {
            val wardNewEnrollmentHeaderItem = VotersList()
            wardNewEnrollmentHeaderItem.isHeader = true
            wardNewEnrollmentHeaderItem.voterName = getString(R.string.ward_new_enrollment_list)
            dpoWardStatusWiseVotersList.add(wardNewEnrollmentHeaderItem)
            dpoWardStatusWiseVotersList.addAll(dpoWardNewEnrollmentVotersList)
        }


    }

    private fun sortByGender(dpoWardUpdatedRollVotersList: MutableList<VotersList>) {
        val maleVotersList: ArrayList<VotersList> = ArrayList()
        val femaleVotersList: ArrayList<VotersList> = ArrayList()

        for (voter in dpoWardUpdatedRollVotersList) {
            if (voter.gender == "M")
                maleVotersList.add(voter)
            else
                femaleVotersList.add(voter)
        }

        maleVotersList.sortBy { it.lName }
        femaleVotersList.sortBy { it.lName }

        dpoWardUpdatedRollVotersListWithSortByGenderAndLname.addAll(maleVotersList)
        dpoWardUpdatedRollVotersListWithSortByGenderAndLname.addAll(femaleVotersList)

        if (dpoWardUpdatedRollVotersListWithSortByGenderAndLname.size > 0) {
            val wardUpdatedRollHeaderItem = VotersList()
            wardUpdatedRollHeaderItem.isHeader = true
            wardUpdatedRollHeaderItem.voterName = getString(R.string.ward_updated_roll_list)
            dpoWardStatusWiseVotersList.add(wardUpdatedRollHeaderItem)
            dpoWardStatusWiseVotersList.addAll(dpoWardUpdatedRollVotersListWithSortByGenderAndLname)
        }

    }

    private fun loadData() {
        val updatedCount = updatedVotersList.size
        val newCount = newVotersList.size
        val deceasedCount = deceasedVotersList.size
        val ghostCount = ghostVotersList.size
        val duplicateCount = duplicateVotersList.size
        val transferredOutCount = transferredOutVotersList.size
        val transferInCount = transferInVotersList.size
        val underAgeCount = underAgeVotersList.size
        val provisionalCount = provisionalVotersList.size

        binding?.tvUpdated?.text = updatedCount.toString()
        binding?.tvNew?.text = newCount.toString()
        binding?.tvDeceased?.text = deceasedCount.toString()
        binding?.tvGhost?.text = ghostCount.toString()
        binding?.tvDuplicate?.text = duplicateCount.toString()
        binding?.tvTransferredOut?.text = transferredOutCount.toString()
        binding?.tvTransferredIn?.text = transferInCount.toString()
        binding?.tvUnderAge?.text = underAgeCount.toString()
        binding?.tvProvisional?.text = provisionalCount.toString()

        val totalCount =
            updatedCount + newCount + deceasedCount + ghostCount + duplicateCount + transferredOutCount + transferInCount + underAgeCount + provisionalCount

        binding?.tvTotal?.text = totalCount.toString()
        if (totalCount == 0) {
            showAlertMessage("No data found!")
            binding?.cardPrint?.visibility = View.GONE
        } else {
            binding?.cardPrint?.visibility = View.VISIBLE
        }
        binding?.rvWardVotersList?.layoutManager =
            object : LinearLayoutManager(this, VERTICAL, false) {
                override fun onLayoutCompleted(state: RecyclerView.State?) {
                    super.onLayoutCompleted(state)
                    CommonMethods.hideProgress()
                }
            }
        setWardVotersListAdapter(dpoWardStatusWiseVotersList)
    }


    private fun setWardVotersListAdapter(dpoWardStatusWiseVotersList: MutableList<VotersList>) {

        wardVotersListAdapter = object :
            GenericAdapter<VotersList, ItemWardVoterBinding>(dpoWardStatusWiseVotersList) {
            override fun getLayoutId(): Int {
                return R.layout.item_ward_voter
            }

            @SuppressLint("SetTextI18n")
            override fun onBindData(
                model: VotersList?,
                position: Int,
                dataBinding: ItemWardVoterBinding?
            ) {

                if (model?.isHeader!!) {

                    dataBinding?.rlHeader?.visibility = View.VISIBLE
                    dataBinding?.cvParent?.visibility = View.GONE
                    dataBinding?.tvTitle?.text = model.voterName

                } else {

                    dataBinding?.rlHeader?.visibility = View.GONE
                    dataBinding?.cvParent?.visibility = View.VISIBLE

                    dataBinding?.tvFName?.text = "First Name: " + CommonMethods.getVoterDetails(
                        model.fName
                    )!!

                    dataBinding?.tvLName?.text = "Last Name: " + CommonMethods.getVoterDetails(
                        model.lName
                    )!!

                    if (CommonMethods.getVoterDetails(model.gender)!! == "F")
                        dataBinding?.tvGender?.text = "Gender: Female"
                    else if (CommonMethods.getVoterDetails(model.gender)!! == "M")
                        dataBinding?.tvGender?.text = "Gender: Male"
                    else
                        dataBinding?.tvGender?.text = "Gender:"

                    dataBinding?.tvDob?.text = "Dob: " + CommonMethods.getDisplayDate(
                        CommonMethods.getVoterDetails(
                            model.dob
                        )!!
                    )

                    if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
                        dataBinding?.tvLocation?.text =
                            "Location: " + CommonMethods.getVoterDetails(
                                model.locationName
                            )
                        dataBinding?.tvClan?.visibility = View.GONE
                    }else{
                        dataBinding?.tvLocation?.text =
                            "Location: " + CommonMethods.getVoterDetails(
                                model.mkaLocationName
                            )
                        dataBinding?.tvClan?.visibility = View.VISIBLE
                        dataBinding?.tvClan?.text = "Clan: " + CommonMethods.getVoterDetails(
                            model.mkaClanName
                        )
                    }

                    dataBinding?.tvProvinceOfBirth?.text =
                        "Province Of Birth: " + CommonMethods.getVoterDetails(
                            model.provinceOfBirth
                        )!!

                    dataBinding?.tvElectorId?.text = "Elector ID - " + model.electorId

                    dataBinding?.tvStatus?.text = "Status: " + model.status

                    dataBinding?.tvOccupation?.text =
                        "Occupation: " + CommonMethods.getVoterDetails(
                            model.occupation
                        )!!

                    dataBinding?.tvNID?.text =
                        "NID: " + CommonMethods.getVoterDetails(
                            model.natIDRecordNo
                        )!!

                    if (model.status == getString(R.string.new_status)
                        || model.status == getString(R.string.transferred_in)
                    ) {
                        dataBinding?.tvForm11Number?.visibility = View.VISIBLE
                        dataBinding?.tvForm11Number?.text =
                            "Form 11 Number: " + CommonMethods.getForm11NumberForStagingReport(
                                CommonMethods.getVoterDetails(model.enrolmentFormNumber),
                                securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "")
                            )
                    } else {

                        dataBinding?.tvForm11Number?.visibility = View.GONE
                    }
                }
            }
        }
        binding?.rvWardVotersList?.adapter = wardVotersListAdapter

    }


    private fun showAlertMessage(alertMessage: String) {

        alert(alertMessage) {
            okButton {
            }
        }.show()

    }


    // Download Report flow

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
                        CommonMethods.showSettingsDialog(this@StagingDeviceOperatorReportActivity)
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
                exportReportPDF()
            } else {
                val intent = Intent(
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                )
                storagePermissionResultLauncher.launch(intent)
            }

        } else {
            exportReportPDF()
        }
    }

    private fun exportReportPDF() {
        binding?.rlProgressBar?.visibility = View.VISIBLE
        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                writePDFFile()
            }
            showDownloadSuccessMessage()
        }
    }

    private fun writePDFFile() {

        val filePath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                .toString() + "/" + dopReportFilesPath + "/"

        val reportsDirectory = File(filePath)
        if (!reportsDirectory.exists())
            reportsDirectory.mkdirs()

        val regex = Regex("[^A-Za-z0-9]")
        var wardName = intent.getStringExtra("wardName")
        wardName = wardName?.let { regex.replace(it, "") }
        if (locationName.isNotEmpty())
            locationName = locationName.let { regex.replace(it, "") }

        val fileName = if (securedSharedPreferenceWrapper.getString(
                ApiConstants.ROLE,
                ""
            ) == ApiConstants.ROLE_DPO
        )
            "PNG_" + if (locationName.isEmpty())
                wardName + "_" + "STAGING" + "_" + deviceRunInMillis + ".pdf"
            else
                wardName + "_" + locationName + "_" + "STAGING" + "_" + deviceRunInMillis + ".pdf"
        else
            "MKA_" + if (locationName.isEmpty())
                wardName + "_" + "STAGING" + "_" + deviceRunInMillis + ".pdf"
            else
                wardName + "_" + locationName + "_" + "STAGING" + "_" + deviceRunInMillis + ".pdf"

        val outputFile = File(filePath, fileName)
        outputFile.createNewFile()

        val pdfDocument = PdfDocument(PdfWriter(outputFile))
        pdfDocument.defaultPageSize = PageSize.A4
        val document = Document(pdfDocument)

        val table = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    15f,
                    85f
                )
            )
        ).useAllAvailableWidth()
        table.addCell(getBoldTextCell("Device Run:"))
        table.addCell(getCell(deviceRun))
        document.add(table)

        val table1 = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    15f,
                    85f
                )
            )
        ).useAllAvailableWidth()
        table1.addCell(getBoldTextCell("Device ID:"))
        table1.addCell(getCell(deviceId))
        document.add(table1)

        val table2 = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    15f,
                    85f
                )
            )
        ).useAllAvailableWidth()
        table2.addCell(getBoldTextCell("DPO:"))
        table2.addCell(getCell(dpo))
        document.add(table2)

        val table3 = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    15f,
                    85f
                )
            )
        ).useAllAvailableWidth()
        table3.addCell(getBoldTextCell("Ward Name:"))
        table3.addCell(getCell(wardName))
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
            table4.addCell(getBoldTextCell("Location Name:"))
            table4.addCell(getCell(locationName))
            document.add(table4)
        }

        if (dpoWardUpdatedRollVotersListWithSortByGenderAndLname.size > 0)
            setWardUpdatedRollVotersInfoTableData(document)

        if (dpoWardNewEnrollmentVotersList.size > 0)
            setWardNewEnrollmentVotersInfoTableData(document)

        val wardSummary = Paragraph(getString(R.string.ward_summary))
        wardSummary.setMarginTop(10f)
        wardSummary.setFontSize(12.0f)
        wardSummary.setBold()
        wardSummary.setTextAlignment(TextAlignment.CENTER)
        document.add(wardSummary)

        setWardSummaryData(document)

        document.close()
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

    private fun setWardUpdatedRollVotersInfoTableData(document: Document) {

        val wardSummary = Paragraph(getString(R.string.ward_updated_roll_list))
        wardSummary.setMarginTop(10f)
        wardSummary.setFontSize(10.0f)
        wardSummary.setBold()
        wardSummary.setTextAlignment(TextAlignment.LEFT)
        document.add(wardSummary)

        val votersInfoHeaderTable = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    2f,
                    9f,
                    9f,
                    5f,
                    10f,
                    10f,
                    10f,
                    5f,
                    10f,
                    10f,
                    10f,
                    10f
                )
            )
        ).useAllAvailableWidth()

        votersInfoHeaderTable.setMarginTop(10f)

        votersInfoHeaderTable.addCell(getVotersListHeaderCell("No", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("LName", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("FName", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Gender", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("DOB", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(
            getVotersListHeaderCell(
                "Occupation",
                TextAlignment.CENTER
            )
        )
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("NID", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(
            getVotersListHeaderCell(
                "With Disability",
                TextAlignment.CENTER
            )
        )
        if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
            votersInfoHeaderTable.addCell(
                getVotersListHeaderCell(
                    "Location",
                    TextAlignment.CENTER
                )
            )
            votersInfoHeaderTable.addCell(
                getVotersListHeaderCell(
                    "Place Of Birth",
                    TextAlignment.CENTER
                )
            )
        }else{
            votersInfoHeaderTable.addCell(
                getVotersListHeaderCell(
                    "Section",
                    TextAlignment.CENTER
                )
            )
            votersInfoHeaderTable.addCell(
                getVotersListHeaderCell(
                    "Clan",
                    TextAlignment.CENTER
                )
            )
        }
        votersInfoHeaderTable.addCell(
            getVotersListHeaderCell(
                "Prov Of Birth",
                TextAlignment.CENTER
            )
        )
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Status", TextAlignment.CENTER))

        var count = 0
        for (voter in dpoWardUpdatedRollVotersListWithSortByGenderAndLname) {

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
                    )!!, TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.fName
                    )!!, TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.gender
                    )!!, TextAlignment.CENTER
                )
            )


            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getDisplayDate(
                        CommonMethods.getVoterDetails(
                            voter.dob
                        )!!
                    ), TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.occupation
                    )!!, TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.natIDRecordNo
                    )!!, TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    getDisability(voter.isPWD), TextAlignment.CENTER
                )
            )

            if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
                votersInfoHeaderTable.addCell(
                    getVotersListCell(
                        CommonMethods.getVoterDetails(
                            voter.locationName
                        )!!, TextAlignment.CENTER
                    )
                )

                votersInfoHeaderTable.addCell(
                    getVotersListCell(
                        CommonMethods.getVoterDetails(
                            voter.placeOfBirth
                        )!!, TextAlignment.CENTER
                    )
                )
            }else{
                votersInfoHeaderTable.addCell(
                    getVotersListCell(
                        CommonMethods.getVoterDetails(
                            voter.mkaLocationName
                        )!!, TextAlignment.CENTER
                    )
                )

                votersInfoHeaderTable.addCell(
                    getVotersListCell(
                        CommonMethods.getVoterDetails(
                            voter.mkaClanName
                        )!!, TextAlignment.CENTER
                    )
                )
            }


            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.provinceOfBirth
                    )!!, TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.status
                    )!!, TextAlignment.CENTER
                )
            )


        }

        document.add(votersInfoHeaderTable)

    }

    private fun setWardNewEnrollmentVotersInfoTableData(document: Document) {

        val wardSummary = Paragraph(getString(R.string.ward_new_enrollment_list))
        wardSummary.setMarginTop(10f)
        wardSummary.setFontSize(10.0f)
        wardSummary.setBold()
        wardSummary.setTextAlignment(TextAlignment.LEFT)
        document.add(wardSummary)

        val votersInfoHeaderTable = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    2f,
                    1f,
                    9f,
                    9f,
                    5f,
                    10f,
                    10f,
                    10f,
                    5f,
                    10f,
                    10f,
                    10f,
                    9f
                )
            )
        ).useAllAvailableWidth()

        votersInfoHeaderTable.setMarginTop(10f)

        votersInfoHeaderTable.addCell(getVotersListHeaderCell("No", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Form 11 No", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("LName", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("FName", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Gender", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("DOB", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(
            getVotersListHeaderCell(
                "Occupation",
                TextAlignment.CENTER
            )
        )
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("NID", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(
            getVotersListHeaderCell(
                "With Disability",
                TextAlignment.CENTER
            )
        )
        if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
            votersInfoHeaderTable.addCell(
                getVotersListHeaderCell(
                    "Location",
                    TextAlignment.CENTER
                )
            )
            votersInfoHeaderTable.addCell(
                getVotersListHeaderCell(
                    "Place Of Birth",
                    TextAlignment.CENTER
                )
            )
        }else{
            votersInfoHeaderTable.addCell(
                getVotersListHeaderCell(
                    "Section",
                    TextAlignment.CENTER
                )
            )
            votersInfoHeaderTable.addCell(
                getVotersListHeaderCell(
                    "Clan",
                    TextAlignment.CENTER
                )
            )
        }
        votersInfoHeaderTable.addCell(
            getVotersListHeaderCell(
                "Prov Of Birth",
                TextAlignment.CENTER
            )
        )
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Status", TextAlignment.CENTER))

        var count = 0
        for (voter in dpoWardNewEnrollmentVotersList) {

            count += 1

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    count.toString(), TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getForm11NumberForStagingReport(
                        CommonMethods.getVoterDetails(voter.enrolmentFormNumber),
                        securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "")
                    ), TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.lName
                    )!!, TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.fName
                    )!!, TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.gender
                    )!!, TextAlignment.CENTER
                )
            )


            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getDisplayDate(
                        CommonMethods.getVoterDetails(
                            voter.dob
                        )!!
                    ), TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.occupation
                    )!!, TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.natIDRecordNo
                    )!!, TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    getDisability(voter.isPWD), TextAlignment.CENTER
                )
            )

            if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
                votersInfoHeaderTable.addCell(
                    getVotersListCell(
                        CommonMethods.getVoterDetails(
                            voter.locationName
                        )!!, TextAlignment.CENTER
                    )
                )

                votersInfoHeaderTable.addCell(
                    getVotersListCell(
                        CommonMethods.getVoterDetails(
                            voter.placeOfBirth
                        )!!, TextAlignment.CENTER
                    )
                )
            }else{
                votersInfoHeaderTable.addCell(
                    getVotersListCell(
                        CommonMethods.getVoterDetails(
                            voter.mkaLocationName
                        )!!, TextAlignment.CENTER
                    )
                )

                votersInfoHeaderTable.addCell(
                    getVotersListCell(
                        CommonMethods.getVoterDetails(
                            voter.mkaClanName
                        )!!, TextAlignment.CENTER
                    )
                )
            }

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.provinceOfBirth
                    )!!, TextAlignment.CENTER
                )
            )


            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.status
                    )!!, TextAlignment.CENTER
                )
            )


        }

        document.add(votersInfoHeaderTable)

    }


    private fun getVotersListHeaderCell(text: String?, alignment: TextAlignment?): Cell? {
        val sizedText = Paragraph(text)
        sizedText.setFontSize(7.0f)
        sizedText.setBold()
        val cell = Cell().add(sizedText)
        cell.setPadding(2f)
        cell.setTextAlignment(alignment)
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE)
//        cell.setBorder(Border.NO_BORDER)
        return cell
    }

    private fun getVotersListCell(text: String?, alignment: TextAlignment?): Cell? {
        val sizedText = Paragraph(text)
        sizedText.setFontSize(7.0f)
        val cell = Cell().add(sizedText)
        cell.setPadding(2f)
        cell.setTextAlignment(alignment)
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE)
//        cell.setBorder(Border.NO_BORDER)
        return cell
    }

    private fun getDisability(pwd: Boolean?): String {

        return if (pwd!!) {
            "Yes"
        } else {
            "No"
        }

    }

    private fun setWardSummaryData(document: Document) {

//        val wardSummaryTable = Table(
//            UnitValue.createPercentArray(
//                floatArrayOf(
//                    7f,
//                    7f,
//                    11f,
//                    13f,
//                    9f,
//                    12f,
//                    12f,
//                    12f,
//                    13f,
//                    11f
//                )
//            )
//        ).useAllAvailableWidth()

        val wardSummaryTable = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    7f,
                    10f,
                    10f,
                    10f,
                    10f,
                    10f,
                    10f,
                    10f,
                    13f,
                    10f
                )
            )
        ).useAllAvailableWidth()


        wardSummaryTable.setMarginTop(10f)

        wardSummaryTable.addHeaderCell(
            getWardSummaryHeaderCell(
                getString(R.string.new_status),
                TextAlignment.CENTER
            )
        )

        wardSummaryTable.addHeaderCell(
            getWardSummaryHeaderCell(
                getString(R.string.provisional),
                TextAlignment.CENTER
            )
        )

        wardSummaryTable.addHeaderCell(
            getWardSummaryHeaderCell(
                getString(R.string.updated),
                TextAlignment.CENTER
            )
        )

        wardSummaryTable.addHeaderCell(
            getWardSummaryHeaderCell(
                getString(R.string.deceased),
                TextAlignment.CENTER
            )
        )
        wardSummaryTable.addHeaderCell(
            getWardSummaryHeaderCell(
                getString(R.string.ghost_report),
                TextAlignment.CENTER
            )
        )
        wardSummaryTable.addHeaderCell(
            getWardSummaryHeaderCell(
                getString(R.string.duplicate),
                TextAlignment.CENTER
            )
        )

        wardSummaryTable.addHeaderCell(
            getWardSummaryHeaderCell(
                getString(R.string.tran_out),
                TextAlignment.CENTER
            )
        )

        wardSummaryTable.addHeaderCell(
            getWardSummaryHeaderCell(
                getString(R.string.tran_in),
                TextAlignment.CENTER
            )
        )

        wardSummaryTable.addHeaderCell(
            getWardSummaryHeaderCell(
                getString(R.string.under_age),
                TextAlignment.CENTER
            )
        )

        wardSummaryTable.addHeaderCell(
            getWardSummaryHeaderCell(
                getString(R.string.total),
                TextAlignment.CENTER
            )
        )


        wardSummaryTable.addCell(
            getWardSummaryCell(
                binding?.tvNew?.text.toString(),
                TextAlignment.CENTER
            )
        )

        wardSummaryTable.addCell(
            getWardSummaryCell(
                binding?.tvProvisional?.text.toString(),
                TextAlignment.CENTER
            )
        )

        wardSummaryTable.addCell(
            getWardSummaryCell(
                binding?.tvUpdated?.text.toString(),
                TextAlignment.CENTER
            )
        )


        wardSummaryTable.addCell(
            getWardSummaryCell(
                binding?.tvDeceased?.text.toString(),
                TextAlignment.CENTER
            )
        )

        wardSummaryTable.addCell(
            getWardSummaryCell(
                binding?.tvGhost?.text.toString(),
                TextAlignment.CENTER
            )
        )

        wardSummaryTable.addCell(
            getWardSummaryCell(
                binding?.tvDuplicate?.text.toString(),
                TextAlignment.CENTER
            )
        )

        wardSummaryTable.addCell(
            getWardSummaryCell(
                binding?.tvTransferredOut?.text.toString(),
                TextAlignment.CENTER
            )
        )

        wardSummaryTable.addCell(
            getWardSummaryCell(
                binding?.tvTransferredIn?.text.toString(),
                TextAlignment.CENTER
            )
        )

        wardSummaryTable.addCell(
            getWardSummaryCell(
                binding?.tvUnderAge?.text.toString(),
                TextAlignment.CENTER
            )
        )

        wardSummaryTable.addCell(
            getWardSummaryCell(
                binding?.tvTotal?.text.toString(),
                TextAlignment.CENTER
            )
        )

        document.add(wardSummaryTable)

    }


    private fun getWardSummaryHeaderCell(text: String?, alignment: TextAlignment?): Cell? {
        val sizedText = Paragraph(text)
        sizedText.setFontSize(8.0f)
        sizedText.setBold()
        val cell = Cell().add(sizedText)
        cell.setPadding(3f)
        cell.setTextAlignment(alignment)
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE)
        return cell
    }

    private fun getWardSummaryCell(text: String?, alignment: TextAlignment?): Cell? {
        val sizedText = Paragraph(text)
        sizedText.setFontSize(10.0f)
        val cell = Cell().add(sizedText)
        cell.setPadding(3f)
        cell.setTextAlignment(alignment)
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE)
        return cell
    }


    private fun showDownloadSuccessMessage() {
        Handler(mainLooper).postDelayed({
            binding?.rlProgressBar?.visibility = View.GONE
            val reportDestination = if (locationName.isEmpty())
                intent.getStringExtra("wardName") + " - " + intent.getIntExtra("wardNumber", 0)
                    .toString()
            else
                locationName
            showAlertMessage("Quality Check Report for $reportDestination has been generated successfully and placed in the documents folder")
        }, 1000)
    }

}
