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
import com.pngelectoral.databinding.ActivityStatusDiscrepancyReportBinding
import com.pngelectoral.databinding.ItemStatusDiscrepancyVoterBinding
import com.pngelectoral.ui.adapter.GenericAdapter
import com.pngelectoral.ui.model.StatusAndDobDiscrepancyReportModel
import com.pngelectoral.ui.model.StatusAndDobDiscrepancyVotersList
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


class StatusDiscrepancyReportActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityStatusDiscrepancyReportBinding? = null
    private var registrationViewModel: RegistrationViewModel? = null
    private var votersListAdapter: GenericAdapter<StatusAndDobDiscrepancyVotersList, ItemStatusDiscrepancyVoterBinding>? =
        null

    var provinceId = 0
    var districtId = 0
    var llgId = 0
    var wardId = 0
    var locationId = 0

    private var provinceName = ""
    private var districtName = ""
    private var llgName = ""
    private var wardNumberAndName = ""
    private var locationName = ""

    var pageNumber = 1
    private var pageSize = 1000

    private var votersList = mutableListOf<StatusAndDobDiscrepancyVotersList>()
    private var reportFilesPath = "PNG_Voter_Status_and_DOB_Discrepancy_Report"
    lateinit var dataBaseInstance: AppDatabase

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    private lateinit var storagePermissionResultLauncher: ActivityResultLauncher<Intent?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_status_discrepancy_report
        )
        registrationViewModel =
            ViewModelProvider(this).get(RegistrationViewModel::class.java)
        binding?.registrationViewModel = registrationViewModel
        dataBaseInstance = DatabaseClient.getInstance(this).appDatabase
        job = Job()

        provinceId = intent.getIntExtra("provinceId", 0)
        districtId = intent.getIntExtra("districtId", 0)
        llgId = intent.getIntExtra("llgId", 0)
        wardId = intent.getIntExtra("wardId", 0)
        locationId = intent.getIntExtra("locationId", 0)

        provinceName = intent.getStringExtra("provinceName")!!
        districtName = intent.getStringExtra("districtName")!!
        llgName = intent.getStringExtra("llgName")!!
        locationName = intent.getStringExtra("locationName")!!

        if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
            binding?.llProvince?.visibility = View.VISIBLE
        }else{
            binding?.llProvince?.visibility = View.GONE
        }

        setFooterData()
        setReportHeaderData()
        setOnclickListeners()
        callGetVoterStatusAndDOBDiscrepancyReportAPI(true)

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

    private fun callGetVoterStatusAndDOBDiscrepancyReportAPI(isShowLoading: Boolean) {

        if (CommonMethods.checkInternetConnection(application)) {

            if (isShowLoading)
                CommonMethods.showProgress(this)

            val voterStatusAndDobDiscrepancyReportObject = JsonObject()

            if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO){
                voterStatusAndDobDiscrepancyReportObject.addProperty(ApiConstants.REQUESTTYPE, "PNG")
                voterStatusAndDobDiscrepancyReportObject.addProperty(
                    ApiConstants.PROVINCEID,
                    provinceId
                )
                voterStatusAndDobDiscrepancyReportObject.addProperty(
                    ApiConstants.DISTRICTID,
                    districtId
                )
                voterStatusAndDobDiscrepancyReportObject.addProperty(ApiConstants.LLGID, llgId)
                voterStatusAndDobDiscrepancyReportObject.addProperty(ApiConstants.WARDID, wardId)
                if (locationId != 0)
                    voterStatusAndDobDiscrepancyReportObject.addProperty(
                        ApiConstants.LOCATION_ID,
                        locationId
                    )
            }else{
                voterStatusAndDobDiscrepancyReportObject.addProperty(ApiConstants.REQUESTTYPE, "MKA")
                voterStatusAndDobDiscrepancyReportObject.addProperty(
                    ApiConstants.MKADISTRICTID,
                    districtId
                )
                voterStatusAndDobDiscrepancyReportObject.addProperty(ApiConstants.MKALLGID, llgId)
                voterStatusAndDobDiscrepancyReportObject.addProperty(ApiConstants.MKAWARDID, wardId)
                if (locationId != 0)
                    voterStatusAndDobDiscrepancyReportObject.addProperty(
                        ApiConstants.MKALOCATIONID,
                        locationId
                    )
            }

            voterStatusAndDobDiscrepancyReportObject.addProperty(ApiConstants.PAGENO, pageNumber)
            voterStatusAndDobDiscrepancyReportObject.addProperty(ApiConstants.PAGESIZE, pageSize)

            val call =
                ApiClient.getRetrofitInterface()?.callGetVoterStatusAndDOBDiscrepancyReportAPI(
                    voterStatusAndDobDiscrepancyReportObject
                )

            WSClient<StatusAndDobDiscrepancyReportModel>().request(
                this,
                ApiConstants.VOTER_STATUS_DOB_DISCREPANCY_REPORT,
                call,
                object :
                    ISuccessHandler<StatusAndDobDiscrepancyReportModel> {
                    override fun successResponse(
                        requestCode: Int,
                        mResponse: StatusAndDobDiscrepancyReportModel
                    ) {

                        if (mResponse.status!! && mResponse.data!!.votersList!!.size > 0) {
                            val statusAndDobDiscrepancyVotersListData = mResponse.data

                            if (pageNumber == 1) {
                                votersList =
                                    (statusAndDobDiscrepancyVotersListData!!.votersList as MutableList<StatusAndDobDiscrepancyVotersList>?)!!
                                if (votersList.size == 0) {
                                    CommonMethods.hideProgress()
                                    alert("No data found!") {
                                        okButton { }
                                    }.show()
                                } else {
                                    if (votersList.size == statusAndDobDiscrepancyVotersListData.total) {
                                        setVotersData(votersList)
                                    } else {
                                        pageNumber += 1
                                        callGetVoterStatusAndDOBDiscrepancyReportAPI(false)
                                    }
                                }
                            } else {
                                votersList.addAll((statusAndDobDiscrepancyVotersListData!!.votersList as MutableList<StatusAndDobDiscrepancyVotersList>?)!!)
                                if (votersList.size == statusAndDobDiscrepancyVotersListData.total) {
                                    setVotersData(votersList)
                                } else {
                                    pageNumber += 1
                                    callGetVoterStatusAndDOBDiscrepancyReportAPI(false)
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


    private fun setVotersData(list: MutableList<StatusAndDobDiscrepancyVotersList>) {
        votersList = getDiscrepancyVotersListBasedOnStatusAndDob(list)
        if (votersList.size == 0) {
            showAlertMessage("No data found!")
            binding?.cardPrint?.visibility = View.GONE
        } else {
            binding?.cardPrint?.visibility = View.VISIBLE
        }
        binding?.rvVotersList?.layoutManager =
            object : LinearLayoutManager(this, VERTICAL, false) {
                override fun onLayoutCompleted(state: RecyclerView.State?) {
                    super.onLayoutCompleted(state)
                    CommonMethods.hideProgress()
                }
            }
        setWardVotersListAdapter(votersList)
    }

    private fun getDiscrepancyVotersListBasedOnStatusAndDob(list: MutableList<StatusAndDobDiscrepancyVotersList>)
            : MutableList<StatusAndDobDiscrepancyVotersList> {
        val discrepancyList: ArrayList<StatusAndDobDiscrepancyVotersList> = ArrayList()
        for (voter in list) {
            if (voter.dateOfBirth != null && voter.dateOfBirth.isNotEmpty()) {
                when {
                    voter.dateOfBirth.split("-")[2].toInt() < ApiConstants.MIN_YEAR_FOR_ENROLMENT -> {
                        voter.reason =
                            "Over Age (DOB before " + ApiConstants.MIN_YEAR_FOR_ENROLMENT + ")"
                        discrepancyList.add(voter)
                    }
                    checkUnderAge(voter.dateOfBirth) -> {
                        voter.reason = "Under Age (DOB below last date of enrolment)"
                        discrepancyList.add(voter)
                    }
                    checkOutOfRange(voter.dateOfBirth) -> {
                        voter.reason = "Mismatched (DOB out of range)"
                        discrepancyList.add(voter)
                    }
                }

            } else {
                if (voter.dateOfBirth == null) {
                    voter.reason = "DOB is Null"
                    discrepancyList.add(voter)
                }
            }

        }
        return discrepancyList
    }

//    private fun checkUnderAge(dateOfBirth: String): Boolean {
//        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
//        val dobDate = sdf.parse(dateOfBirth)
//        val pcoDate = sdf.parse(
//            securedSharedPreferenceWrapper.getString(
//                ApiConstants.PROVISIONAL_CUT_OFF_DATE,
//                ApiConstants.PROVISIONAL_CUT_OFF_DATE_VALUE
//            )
//        )
//        val ldeDate = sdf.parse(
//            securedSharedPreferenceWrapper.getString(
//                "LAST_DATE_OF_ENROLMENT",
//                ApiConstants.LAST_DATE_OF_ENROLMENT_DEFAULT
//            )
//        )
//        return dobDate!!.after(pcoDate) && dobDate.before(ldeDate)
//    }

    private fun checkUnderAge(dateOfBirth: String): Boolean {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        val currentDateString = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val currentDate = sdf.parse(currentDateString)
        val dobDate = sdf.parse(dateOfBirth)
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
        val ldeDate = sdf.parse(
            lastDateOfEnrolment
        )
        return dobDate!!.after(ldeDate) && dobDate.before(currentDate)
    }

    private fun checkOutOfRange(dateOfBirth: String): Boolean {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        val dobDate = sdf.parse(dateOfBirth)
        val currentDateString = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val currentDate = sdf.parse(currentDateString)
        return dobDate!!.after(currentDate)
    }


    private fun setWardVotersListAdapter(dpoWardStatusWiseVotersList: MutableList<StatusAndDobDiscrepancyVotersList>) {

        votersListAdapter = object :
            GenericAdapter<StatusAndDobDiscrepancyVotersList, ItemStatusDiscrepancyVoterBinding>(
                dpoWardStatusWiseVotersList
            ) {
            override fun getLayoutId(): Int {
                return R.layout.item_status_discrepancy_voter
            }

            @SuppressLint("SetTextI18n")
            override fun onBindData(
                model: StatusAndDobDiscrepancyVotersList?,
                position: Int,
                dataBinding: ItemStatusDiscrepancyVoterBinding?
            ) {
                dataBinding?.tvFName?.text = "First Name: " + CommonMethods.getVoterDetails(
                    model!!.fName
                )!!
                dataBinding?.tvLName?.text = "Last Name: " + CommonMethods.getVoterDetails(
                    model.lName
                )!!
                when {
                    CommonMethods.getVoterDetails(model.gender)!! == "F" -> dataBinding?.tvGender?.text =
                        "Gender: Female"
                    CommonMethods.getVoterDetails(model.gender)!! == "M" -> dataBinding?.tvGender?.text =
                        "Gender: Male"
                    else -> dataBinding?.tvGender?.text = "Gender:"
                }

                dataBinding?.tvDob?.text = "Dob: " + CommonMethods.getDisplayDate(
                    CommonMethods.getVoterDetails(
                        model.dateOfBirth
                    )!!
                )

                dataBinding?.tvLocation?.text =
                    "Location: " + CommonMethods.getVoterDetails(
                        model.locationName
                    )!!
                dataBinding?.tvElectorId?.text = "Elector ID - " + model.electorId
                dataBinding?.tvStatus?.text = "Status: " + model.status
                dataBinding?.tvReason?.text = "Reason: " + model.reason


            }
        }
        binding?.rvVotersList?.adapter = votersListAdapter

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
                        CommonMethods.showSettingsDialog(this@StatusDiscrepancyReportActivity)
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
                .toString() + "/" + reportFilesPath + "/"

        val reportsDirectory = File(filePath)
        if (!reportsDirectory.exists())
            reportsDirectory.mkdirs()


        val regex = Regex("[^A-Za-z0-9]")
        var wardName = intent.getStringExtra("wardName")
        wardName = wardName?.let { regex.replace(it, "") }
        if (locationName.isNotEmpty())
            locationName = locationName.let { regex.replace(it, "") }
        val fileName = if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO)
            "PNG_" + if (locationName.isEmpty())
                wardName + "_" + "STAGING" + "_" + System.currentTimeMillis()
                    .toString() + ".pdf"
            else
                wardName + "_" + locationName + "_" + "STAGING" + "_" + System.currentTimeMillis()
                    .toString() + ".pdf"
        else
            "MKA_" + if (locationName.isEmpty())
                wardName + "_" + "STAGING" + "_" + System.currentTimeMillis()
                    .toString() + ".pdf"
            else
                wardName + "_" + locationName + "_" + "STAGING" + "_" + System.currentTimeMillis()
                    .toString() + ".pdf"

        val outputFile = File(filePath, fileName)
        outputFile.createNewFile()

        val pdfDocument = PdfDocument(PdfWriter(outputFile))
        pdfDocument.defaultPageSize = PageSize.A4
        val document = Document(pdfDocument)


        val wardSummary = Paragraph(getString(R.string.voter_status_and_dob_discrepancy_report))
        wardSummary.setMarginTop(10f)
        wardSummary.setFontSize(10.0f)
        wardSummary.setBold()
        wardSummary.setTextAlignment(TextAlignment.LEFT)
        document.add(wardSummary)

        if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO) {
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

        if (votersList.size > 0)
            setStatusAndDobDiscrepancyVotersInfoTableData(document)

        document.close()
    }

    private fun setStatusAndDobDiscrepancyVotersInfoTableData(document: Document) {

        val statusAndDobDiscrepancyVotersInfoTable = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    4f,
                    10f,
                    12f,
                    12f,
                    8f,
                    12f,
                    12f,
                    12f,
                    18f
                )
            )
        ).useAllAvailableWidth()

        statusAndDobDiscrepancyVotersInfoTable.setMarginTop(15f)

        statusAndDobDiscrepancyVotersInfoTable.addCell(
            getVotersListHeaderCell(
                "No",
                TextAlignment.CENTER
            )
        )
        statusAndDobDiscrepancyVotersInfoTable.addCell(
            getVotersListHeaderCell(
                "Elector Id",
                TextAlignment.CENTER
            )
        )
        statusAndDobDiscrepancyVotersInfoTable.addCell(
            getVotersListHeaderCell(
                "LName",
                TextAlignment.CENTER
            )
        )
        statusAndDobDiscrepancyVotersInfoTable.addCell(
            getVotersListHeaderCell(
                "FName",
                TextAlignment.CENTER
            )
        )
        statusAndDobDiscrepancyVotersInfoTable.addCell(
            getVotersListHeaderCell(
                "Gender",
                TextAlignment.CENTER
            )
        )
        statusAndDobDiscrepancyVotersInfoTable.addCell(
            getVotersListHeaderCell(
                "DOB",
                TextAlignment.CENTER
            )
        )
        statusAndDobDiscrepancyVotersInfoTable.addCell(
            getVotersListHeaderCell(
                "Location",
                TextAlignment.CENTER
            )
        )
        statusAndDobDiscrepancyVotersInfoTable.addCell(
            getVotersListHeaderCell(
                "Status",
                TextAlignment.CENTER
            )
        )
        statusAndDobDiscrepancyVotersInfoTable.addCell(
            getVotersListHeaderCell(
                "Reason",
                TextAlignment.CENTER
            )
        )

        var count = 0
        for (voter in votersList) {

            count += 1

            statusAndDobDiscrepancyVotersInfoTable.addCell(
                getVotersListCell(
                    count.toString(), TextAlignment.CENTER
                )
            )

            statusAndDobDiscrepancyVotersInfoTable.addCell(
                getVotersListCell(
                    voter.electorId.toString(), TextAlignment.CENTER
                )
            )

            statusAndDobDiscrepancyVotersInfoTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.lName
                    )!!, TextAlignment.CENTER
                )
            )

            statusAndDobDiscrepancyVotersInfoTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.fName
                    )!!, TextAlignment.CENTER
                )
            )

            statusAndDobDiscrepancyVotersInfoTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.gender
                    )!!, TextAlignment.CENTER
                )
            )


            statusAndDobDiscrepancyVotersInfoTable.addCell(
                getVotersListCell(
                    CommonMethods.getDisplayDate(
                        CommonMethods.getVoterDetails(
                            voter.dateOfBirth
                        )!!
                    ), TextAlignment.CENTER
                )
            )

            statusAndDobDiscrepancyVotersInfoTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.locationName
                    )!!, TextAlignment.CENTER
                )
            )

            statusAndDobDiscrepancyVotersInfoTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.status
                    )!!, TextAlignment.CENTER
                )
            )



            statusAndDobDiscrepancyVotersInfoTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.reason
                    )!!, TextAlignment.CENTER
                )
            )


        }

        document.add(statusAndDobDiscrepancyVotersInfoTable)

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


    private fun showDownloadSuccessMessage() {
        Handler(mainLooper).postDelayed({
            binding?.rlProgressBar?.visibility = View.GONE
            val reportDestination = if (locationName.isEmpty())
                intent.getStringExtra("wardName") + " - " + intent.getIntExtra("wardNumber", 0)
                    .toString()
            else
                locationName
            showAlertMessage("Voter Status and DOB Discrepancy Report for $reportDestination has been generated successfully and placed in the documents folder")
        }, 1000)
    }

}
