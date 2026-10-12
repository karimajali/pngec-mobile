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
import com.pngelectoral.databinding.ActivityFieldTallySummarySheetReportBinding
import com.pngelectoral.ui.model.VotersList
import com.pngelectoral.ui.viewModel.RegistrationViewModel
import com.pngelectoral.utility.CommonMethods
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext


class FieldTallySummarySheetReportActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityFieldTallySummarySheetReportBinding? = null
    private var registrationViewModel: RegistrationViewModel? = null

    private var startDate = ""
    private var endDate = ""
    var provinceId = 0
    var districtId = 0
    var llgId = 0
    var wardId = 0
    var locationId = 0

    private var deviceRun = ""
    private var deviceRunInMillis = ""
    private var deviceId = ""
    private var provinceName = ""
    private var districtName = ""
    private var llgName = ""
    private var wardNumberAndName = ""
    private var locationName = ""

    private var dopReportFilesPath = "PNG_Field_Tally_Summary_Sheet_Report"

    lateinit var dataBaseInstance: AppDatabase

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job
    private lateinit var storagePermissionResultLauncher: ActivityResultLauncher<Intent?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_field_tally_summary_sheet_report
        )
        registrationViewModel =
            ViewModelProvider(this).get(RegistrationViewModel::class.java)
        binding?.registrationViewModel = registrationViewModel
        dataBaseInstance = DatabaseClient.getInstance(this).appDatabase
        job = Job()

        if (intent.getStringExtra(ApiConstants.START_DATE)!!.isNotEmpty())
            startDate = intent.getStringExtra(ApiConstants.START_DATE)!!

        if (intent.getStringExtra(ApiConstants.END_DATE)!!.isNotEmpty())
            endDate = intent.getStringExtra(ApiConstants.END_DATE)!!

        if (securedSharedPreferenceWrapper.getString(
                ApiConstants.ROLE,
                ""
            ) == ApiConstants.ROLE_DPO
        ) {
            binding?.llProvince?.visibility = View.VISIBLE
        } else {
            binding?.llProvince?.visibility = View.GONE
        }

        provinceId = intent.getIntExtra("provinceId", 0)
        districtId = intent.getIntExtra("districtId", 0)
        llgId = intent.getIntExtra("llgId", 0)
        wardId = intent.getIntExtra("wardId", 0)
        locationId = intent.getIntExtra("locationId", 0)

        provinceName = intent.getStringExtra("provinceName")!!
        districtName = intent.getStringExtra("districtName")!!
        llgName = intent.getStringExtra("llgName")!!
        locationName = intent.getStringExtra("locationName")!!

        setFooterData()
        setReportHeaderData()
        setOnclickListeners()
        getUpdatedVotersListUsingCoRoutines()
        getPhotoVotersListUsingCoRoutines()

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
        binding?.tvProvince?.text = provinceName
        binding?.tvDistrict?.text = districtName
        binding?.tvLLG?.text = llgName
        wardNumberAndName = intent.getIntExtra("wardNumber", 0)
            .toString() + " - " + intent.getStringExtra("wardName")
        binding?.tvWard?.text = wardNumberAndName
        if (locationName.isNotEmpty()) {
            binding?.tvLocation?.text = locationName
            binding?.llLocation?.visibility = View.VISIBLE
        } else {
            binding?.llLocation?.visibility = View.GONE
        }

        val mSimpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        deviceRun = mSimpleDateFormat.format(Date())
        deviceRunInMillis = System.currentTimeMillis().toString()
        binding?.tvDeviceReportRunTime?.text = deviceRun
        deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        binding?.tvDeviceId?.text = deviceId
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

    private fun getUpdatedVotersListUsingCoRoutines() {
        CommonMethods.showProgress(this)
        launch(Dispatchers.Main) {
            val result: List<VotersList>? = withContext(Dispatchers.IO) {
                getUpdatedVotersList()
            }
            filterWardData(result as MutableList<VotersList>)

        }

    }

    private fun getPhotoVotersListUsingCoRoutines() {
        CommonMethods.showProgress(this)
        launch(Dispatchers.Main) {

            val result1: List<VotersList>? = withContext(Dispatchers.IO) {
                getWardVotersList()
            }

            filterWardPhotData(result1 as MutableList<VotersList>)
        }

    }

    private fun getWardVotersList(): List<VotersList>? {
        val statusList: ArrayList<String> = ArrayList()
        statusList.add(getString(R.string.old))
        if (locationId == 0) {
            return if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    dataBaseInstance.votersListDao()
                        .getFieldTallySummaryReportVotersListBetweenDatesPhoto(
                            provinceId,
                            districtId,
                            llgId,
                            wardId,
                            startDate,
                            endDate
                        )
                } else {
                    dataBaseInstance.votersListDao()
                        .getFieldTallySummaryReportVotersListBetweenDatesForMKAPhoto(
                            districtId,
                            llgId,
                            wardId,
                            startDate,
                            endDate
                        )
                }
            } else {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    dataBaseInstance.votersListDao()
                        .getFieldTallySummaryReportVotersListPhoto(
                            provinceId,
                            districtId,
                            llgId,
                            wardId
                        )
                } else {
                    dataBaseInstance.votersListDao()
                        .getFieldTallySummaryReportVotersListForMKAPhoto(
                            districtId,
                            llgId,
                            wardId
                        )
                }
            }

        } else {

            return if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    dataBaseInstance.votersListDao()
                        .getFieldTallySummaryReportVotersListBetweenDatesPhoto(
                            provinceId,
                            districtId,
                            llgId,
                            wardId,
                            locationId,
                            startDate,
                            endDate
                        )
                } else {
                    dataBaseInstance.votersListDao()
                        .getFieldTallySummaryReportVotersListBetweenDatesForMKAPhoto(
                            districtId,
                            llgId,
                            wardId,
                            locationId,
                            startDate,
                            endDate
                        )
                }
            } else {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    dataBaseInstance.votersListDao()
                        .getFieldTallySummaryReportVotersListPhoto(
                            provinceId,
                            districtId,
                            llgId,
                            wardId,
                            locationId
                        )
                } else {
                    dataBaseInstance.votersListDao()
                        .getFieldTallySummaryReportVotersListForMKAPhoto(
                            districtId,
                            llgId,
                            wardId,
                            locationId

                        )
                }
            }

        }

    }

    private fun getUpdatedVotersList(): List<VotersList>? {
        val statusList: ArrayList<String> = ArrayList()
        statusList.add(getString(R.string.old))
        if (locationId == 0) {
            return if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    dataBaseInstance.votersListDao()
                        .getFieldTallySummaryReportVotersListBetweenDates(
                            provinceId,
                            districtId,
                            llgId,
                            wardId,
                            startDate,
                            endDate,
                            statusList
                        )
                } else {
                    dataBaseInstance.votersListDao()
                        .getFieldTallySummaryReportVotersListBetweenDatesForMKA(
                            districtId,
                            llgId,
                            wardId,
                            startDate,
                            endDate,
                            statusList
                        )
                }
            } else {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    dataBaseInstance.votersListDao()
                        .getFieldTallySummaryReportVotersList(
                            provinceId,
                            districtId,
                            llgId,
                            wardId,
                            statusList
                        )
                } else {
                    dataBaseInstance.votersListDao()
                        .getFieldTallySummaryReportVotersListForMKA(
                            districtId,
                            llgId,
                            wardId,
                            statusList
                        )
                }
            }

        } else {

            return if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    dataBaseInstance.votersListDao()
                        .getFieldTallySummaryReportVotersListBetweenDates(
                            provinceId,
                            districtId,
                            llgId,
                            wardId,
                            locationId,
                            startDate,
                            endDate,
                            statusList
                        )
                } else {
                    dataBaseInstance.votersListDao()
                        .getFieldTallySummaryReportVotersListBetweenDatesForMKA(
                            districtId,
                            llgId,
                            wardId,
                            locationId,
                            startDate,
                            endDate,
                            statusList
                        )
                }
            } else {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    dataBaseInstance.votersListDao()
                        .getFieldTallySummaryReportVotersList(
                            provinceId,
                            districtId,
                            llgId,
                            wardId,
                            locationId,
                            statusList
                        )
                } else {
                    dataBaseInstance.votersListDao()
                        .getFieldTallySummaryReportVotersListForMKA(
                            districtId,
                            llgId,
                            wardId,
                            locationId,
                            statusList
                        )
                }
            }

        }


    }


    private fun filterWardData(list: MutableList<VotersList>) {
        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                filterWardDataAndDisplayStatusCount(list)
            }
            loadData()
        }
    }

    private fun filterWardPhotData(list: MutableList<VotersList>) {
        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                filterWardPhotoDataAndDisplayStatusCount(list)
            }
            loadData()
        }
    }





    var updatedCount = 0
    var newCount = 0
    var provisionalCount = 0
    var deceasedCount = 0
    var ghostCount = 0
    var duplicateCount = 0
    var transferredOutCount = 0
    var transferInCount = 0
    var transferInWithInProvinceCount = 0
    var transferInDifferentProvinceCount = 0
    var underAgeCount = 0

    var editTotalCount = 0
    var newEnrolmentTotalCount = 0
    var grandTotalCount = 0

    var withPreviousPhotoCount = 0   // photo with old status

    var newPhoto = 0  // photo with new/update status

    var totalWithPhotoCount = 0 // photo count with old/update/new

    var totalWithNoPhotoCount = 0 // No photo count old/update/new

    var totalPhotoCount = 0 // total Records count

    private fun   filterWardPhotoDataAndDisplayStatusCount(wardVotersList: MutableList<VotersList>)
    {
        for (wardVoter in wardVotersList) {
            if(!wardVoter.electorProfileImageUrl.isNullOrEmpty() &&  wardVoter.status == getString(R.string.old))
            {
                withPreviousPhotoCount += 1
            }

            if((!wardVoter.electorProfileImageUrl.isNullOrEmpty() || !wardVoter.electorProfileImage.isNullOrEmpty() )
                &&  ( wardVoter.status == getString(R.string.updated) || wardVoter.status == getString(R.string.new_status)))
            {
                newPhoto += 1
            }

            if((wardVoter.electorProfileImageUrl.isNullOrEmpty() || wardVoter.electorProfileImage.isNullOrEmpty() )
                &&  ( wardVoter.status == getString(R.string.updated)  || wardVoter.status  == getString(R.string.new_status) || wardVoter.status == getString(R.string.old)))
            {
                totalWithNoPhotoCount += 1
            }

            totalPhotoCount  += 1
        }

        totalWithPhotoCount = withPreviousPhotoCount + newPhoto;

    }

    private fun filterWardDataAndDisplayStatusCount(wardVotersList: MutableList<VotersList>) {

        for (wardVoter in wardVotersList) {
            when (wardVoter.status) {
                getString(R.string.updated) -> {
                    updatedCount += 1
                }

                getString(R.string.new_status) -> {
                    if (CommonMethods.isProvisionalVoter(
                            wardVoter,
                            securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "")
                        )
                    ) {
                        provisionalCount += 1
                    } else {
                        newCount += 1
                    }
                }

                getString(R.string.deceased) -> {
                    deceasedCount += 1
                }

                getString(R.string.ghost_report) -> {
                    ghostCount += 1
                }

                getString(R.string.duplicate) -> {
                    duplicateCount += 1
                }

                getString(R.string.transferred_out) -> {
                    transferredOutCount += 1
                }

                getString(R.string.transferred_in) -> {
                    if (CommonMethods.isProvisionalVoter(
                            wardVoter,
                            securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "")
                        )
                    ) {
                        provisionalCount += 1
                    } else {
                        if (wardVoter.provinceId == wardVoter.transferOutProvinceId) {
                            transferInWithInProvinceCount += 1
                        } else {
                            transferInDifferentProvinceCount += 1
                        }
                        transferInCount += 1
                    }
                }

                getString(R.string.under_age) -> {
                    underAgeCount += 1
                }
            }
        }
    }



    private fun loadData() {
        // EDIT Data & Counts
        binding?.tvUpdatedCount?.text = updatedCount.toString()
        binding?.tvGhostCount?.text = ghostCount.toString()
        binding?.tvDuplicateCount?.text = duplicateCount.toString()
        binding?.tvTransferOutCount?.text = transferredOutCount.toString()
        binding?.tvDeceasedCount?.text = deceasedCount.toString()
        binding?.tvUnderAgeCount?.text = underAgeCount.toString()

        editTotalCount =
            updatedCount + ghostCount + duplicateCount + transferredOutCount + deceasedCount + underAgeCount
        binding?.tvEditTotalCount?.text = editTotalCount.toString()

        // NEW Enrolments Data & Counts
        binding?.tvNewCount?.text = newCount.toString()
        binding?.tvWithInProvinceCount?.text = transferInWithInProvinceCount.toString()
        binding?.tvFromDifferentProvinceCount?.text = transferInDifferentProvinceCount.toString()
        binding?.tvProvisionalCount?.text = provisionalCount.toString()

        newEnrolmentTotalCount = newCount + transferInCount + provisionalCount
        binding?.tvNewEnrolmentTotalCount?.text = newEnrolmentTotalCount.toString()

        // Grand Total
        grandTotalCount = editTotalCount + newEnrolmentTotalCount
        binding?.tvGrandTotalCount?.text = grandTotalCount.toString()
        if (grandTotalCount == 0) {
            binding?.cardPrint?.visibility = View.GONE
        } else {
            binding?.cardPrint?.visibility = View.VISIBLE
        }


        //Photo Binding

        binding?.tvWithPreviousPhotoCount?.text = withPreviousPhotoCount.toString()
        binding?.tvNewPhotoCount?.text = newPhoto.toString()
        binding?.tvTotalWithPhotoCount?.text = totalWithPhotoCount.toString()
        binding?.tvTotalWithNoPhotoCount?.text = totalWithNoPhotoCount.toString()
        binding?.tvTotalRecordsCount?.text = totalPhotoCount.toString()

        CommonMethods.hideProgress()
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
                        CommonMethods.showSettingsDialog(this@FieldTallySummarySheetReportActivity)
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
                wardName + "_" + deviceRunInMillis + "_Field Tally Summary Sheet Report.pdf"
            else
                wardName + "_" + locationName + "_" + deviceRunInMillis + "_Field Tally Summary Sheet Report.pdf"
        else
            "MKA_" + if (locationName.isEmpty())
                wardName + "_" + deviceRunInMillis + "_Field Tally Summary Sheet Report.pdf"
            else
                wardName + "_" + locationName + "_" + deviceRunInMillis + "_Field Tally Summary Sheet Report.pdf"

        val outputFile = File(filePath, fileName)
        outputFile.createNewFile()

        val pdfDocument = PdfDocument(PdfWriter(outputFile))
        pdfDocument.defaultPageSize = PageSize.A4
        val document = Document(pdfDocument)

        val wardSummary = Paragraph(getString(R.string.quality_check_tally_sheet))
        wardSummary.setMarginTop(10f)
        wardSummary.setFontSize(10.0f)
        wardSummary.setBold()
        wardSummary.setTextAlignment(TextAlignment.LEFT)
        document.add(wardSummary)

        if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"")==ApiConstants.ROLE_DPO) {
            val table = Table(
                UnitValue.createPercentArray(
                    floatArrayOf(
                        15f,
                        85f
                    )
                )
            ).useAllAvailableWidth()
            table.addCell(getBoldTextCell("Province:"))
            table.addCell(getCell(provinceName))
            document.add(table)
        }

        val table1 = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    15f,
                    85f
                )
            )
        ).useAllAvailableWidth()
        table1.addCell(getBoldTextCell("District:"))
        table1.addCell(getCell(districtName))
        document.add(table1)

        val table2 = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    15f,
                    85f
                )
            )
        ).useAllAvailableWidth()
        table2.addCell(getBoldTextCell("LLG:"))
        table2.addCell(getCell(llgName))
        document.add(table2)

        val table3 = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    15f,
                    85f
                )
            )
        ).useAllAvailableWidth()
        table3.addCell(getBoldTextCell("Ward:"))
        table3.addCell(getCell(wardNumberAndName))
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
            table4.addCell(getBoldTextCell("Location:"))
            table4.addCell(getCell(locationName))
            document.add(table4)
        }

        val table5 = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    15f,
                    85f
                )
            )
        ).useAllAvailableWidth()
        table5.addCell(getBoldTextCell("Device:"))
        table5.addCell(getCell(deviceId))
        document.add(table5)

        val table6 = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    15f,
                    85f
                )
            )
        ).useAllAvailableWidth()
        table6.addCell(getBoldTextCell("Date:"))
        table6.addCell(getCell(deviceRun))
        document.add(table6)

        setEditDataCount(document)
        setNewEnrolmentDataCount(document)
        setGrandTotalCount(document)
        setPhotoDataCount(document)

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

    private fun setEditDataCount(document: Document) {
        val editStatusAndCountTable = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    70f,
                    30f
                )
            )
        ).useAllAvailableWidth()
        editStatusAndCountTable.setMarginTop(10f)

        editStatusAndCountTable.addCell(getCountHeaderCell("EDIT", TextAlignment.LEFT))
        editStatusAndCountTable.addCell(getCountHeaderCell("COUNT", TextAlignment.CENTER))


        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                "UPDATED", TextAlignment.LEFT
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                updatedCount.toString(), TextAlignment.CENTER
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                "GHOST", TextAlignment.LEFT
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                ghostCount.toString(), TextAlignment.CENTER
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                "DUPLICATES", TextAlignment.LEFT
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                duplicateCount.toString(), TextAlignment.CENTER
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                "TRANSFER OUT", TextAlignment.LEFT
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                transferredOutCount.toString(), TextAlignment.CENTER
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                "DECEASED", TextAlignment.LEFT
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                deceasedCount.toString(), TextAlignment.CENTER
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                "UNDER-AGED", TextAlignment.LEFT
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                underAgeCount.toString(), TextAlignment.CENTER
            )
        )

        editStatusAndCountTable.addCell(
            getCountHeaderCell(
                "TOTAL", TextAlignment.LEFT
            )
        )

        editStatusAndCountTable.addCell(
            getCountHeaderCell(
                editTotalCount.toString(), TextAlignment.CENTER
            )
        )
        document.add(editStatusAndCountTable)

    }

    private fun setNewEnrolmentDataCount(document: Document) {
        val newEnrolmentStatusAndCountTable = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    70f,
                    30f
                )
            )
        ).useAllAvailableWidth()
        newEnrolmentStatusAndCountTable.setMarginTop(10f)

        newEnrolmentStatusAndCountTable.addCell(
            getCountHeaderCell(
                "NEW ENROLMENTS",
                TextAlignment.LEFT
            )
        )
        newEnrolmentStatusAndCountTable.addCell(getCountHeaderCell("COUNT", TextAlignment.CENTER))

        newEnrolmentStatusAndCountTable.addCell(
            getStatusAndCountCell(
                "NEW", TextAlignment.LEFT
            )
        )

        newEnrolmentStatusAndCountTable.addCell(
            getStatusAndCountCell(
                newCount.toString(), TextAlignment.CENTER
            )
        )

        newEnrolmentStatusAndCountTable.addCell(
            getStatusAndCountCell(
                "TRANSFER IN", TextAlignment.LEFT
            )
        )

        newEnrolmentStatusAndCountTable.addCell(
            getStatusAndCountCell(
                "", TextAlignment.CENTER
            )
        )

        newEnrolmentStatusAndCountTable.addCell(
            getStatusAndCountSubCell(
                "With In Province", TextAlignment.LEFT
            )
        )

        newEnrolmentStatusAndCountTable.addCell(
            getStatusAndCountCell(
                transferInWithInProvinceCount.toString(), TextAlignment.CENTER
            )
        )

        newEnrolmentStatusAndCountTable.addCell(
            getStatusAndCountSubCell(
                "From Different Province", TextAlignment.LEFT
            )
        )

        newEnrolmentStatusAndCountTable.addCell(
            getStatusAndCountCell(
                transferInDifferentProvinceCount.toString(), TextAlignment.CENTER
            )
        )

        newEnrolmentStatusAndCountTable.addCell(
            getStatusAndCountCell(
                "PROVISIONAL", TextAlignment.LEFT
            )
        )

        newEnrolmentStatusAndCountTable.addCell(
            getStatusAndCountCell(
                provisionalCount.toString(), TextAlignment.CENTER
            )
        )

        newEnrolmentStatusAndCountTable.addCell(
            getCountHeaderCell(
                "TOTAL", TextAlignment.LEFT
            )
        )

        newEnrolmentStatusAndCountTable.addCell(
            getCountHeaderCell(
                newEnrolmentTotalCount.toString(), TextAlignment.CENTER
            )
        )
        document.add(newEnrolmentStatusAndCountTable)
    }


    private fun setGrandTotalCount(document: Document) {
        val grandTotalCountTable = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    70f,
                    30f
                )
            )
        ).useAllAvailableWidth()
        grandTotalCountTable.setMarginTop(10f)
        grandTotalCountTable.addCell(getCountHeaderCell("GRAND TOTAL", TextAlignment.LEFT))
        grandTotalCountTable.addCell(
            getCountHeaderCell(
                grandTotalCount.toString(),
                TextAlignment.CENTER
            )
        )
        document.add(grandTotalCountTable)
    }


    private fun setPhotoDataCount(document: Document) {
        val editStatusAndCountTable = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    70f,
                    30f
                )
            )
        ).useAllAvailableWidth()
        editStatusAndCountTable.setMarginTop(10f)

        editStatusAndCountTable.addCell(getCountHeaderCell("PHOTO", TextAlignment.LEFT))
        editStatusAndCountTable.addCell(getCountHeaderCell("COUNT", TextAlignment.CENTER))


        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                "WITH PREVIOUS PHOTO", TextAlignment.LEFT
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                withPreviousPhotoCount.toString(), TextAlignment.CENTER
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                "NEW PHOTO", TextAlignment.LEFT
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                newPhoto.toString(), TextAlignment.CENTER
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                "TOTAL WITH PHOTO", TextAlignment.LEFT
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                totalWithPhotoCount.toString(), TextAlignment.CENTER
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                "TOTAL WITH NO PHOTO", TextAlignment.LEFT
            )
        )

        editStatusAndCountTable.addCell(
            getStatusAndCountCell(
                totalWithNoPhotoCount.toString(), TextAlignment.CENTER
            )
        )


        editStatusAndCountTable.addCell(
            getCountHeaderCell(
                "TOTAL RECORDS", TextAlignment.LEFT
            )
        )

        editStatusAndCountTable.addCell(
            getCountHeaderCell(
                totalPhotoCount.toString(), TextAlignment.CENTER
            )
        )
        document.add(editStatusAndCountTable)

    }



    private fun getCountHeaderCell(text: String?, alignment: TextAlignment?): Cell? {
        val sizedText = Paragraph(text)
        sizedText.setFontSize(7.0f)
        sizedText.setBold()
        val cell = Cell().add(sizedText)
        cell.setPadding(2.0f)
        cell.setTextAlignment(alignment)
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE)
        return cell
    }

    private fun getStatusAndCountCell(text: String?, alignment: TextAlignment?): Cell? {
        val sizedText = Paragraph(text)
        sizedText.setFontSize(7.0f)
        val cell = Cell().add(sizedText)
        cell.setPadding(2.0f)
        cell.setTextAlignment(alignment)
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE)
        return cell
    }

    private fun getStatusAndCountSubCell(text: String?, alignment: TextAlignment?): Cell? {
        val sizedText = Paragraph(text)
        sizedText.setMarginLeft(20.0f)
        sizedText.setFontSize(7.0f)
        val cell = Cell().add(sizedText)
        cell.setPadding(2.0f)
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
