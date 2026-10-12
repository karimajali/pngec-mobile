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
import com.pngelectoral.databinding.ActivityGenderLessVotersListReportBinding
import com.pngelectoral.databinding.ItemGenderLessVoterBinding
import com.pngelectoral.ui.adapter.GenericAdapter
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

class GenderLessVoterListReportActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityGenderLessVotersListReportBinding? = null
    private var registrationViewModel: RegistrationViewModel? = null

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
    private var wardName = ""
    private var locationName = ""

    private var wardNumber = 0
    private var geoLocationName = ""

    private var dpoReportFilesPath = "PNG_District_Genderless_Voterslist_Report"
    lateinit var dataBaseInstance: AppDatabase
    private var districtGenderLessVotersList = mutableListOf<VotersList>()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private lateinit var job: Job
    private lateinit var storagePermissionResultLauncher: ActivityResultLauncher<Intent?>
    private var genderLessVotersListAdapter: GenericAdapter<VotersList, ItemGenderLessVoterBinding>? =
        null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_gender_less_voters_list_report
        )
        registrationViewModel =
            ViewModelProvider(this).get(RegistrationViewModel::class.java)
        binding?.registrationViewModel = registrationViewModel
        dataBaseInstance = DatabaseClient.getInstance(this).appDatabase
        job = Job()

        districtId = intent.getIntExtra("districtId", 0)
        llgId = intent.getIntExtra("llgId", 0)
        wardId = intent.getIntExtra("wardId", 0)
        locationId = intent.getIntExtra("locationId", 0)

        provinceName = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_PROVINCE, "")
        districtName = intent.getStringExtra("districtName")!!
        llgName = intent.getStringExtra("llgName")!!
        wardName = intent.getStringExtra("wardName")!!
        locationName = intent.getStringExtra("locationName")!!

        wardNumber = intent.getIntExtra("wardNumber", 0)

        geoLocationName = when {
            locationName.isNotEmpty() -> locationName
            wardName.isNotEmpty() -> wardName
            llgName.isNotEmpty() -> llgName
            else -> districtName
        }

        setReportHeaderData()
        setOnclickListeners()

        getGenderLessVotersListUsingCoRoutines()

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
        val mSimpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        deviceRun = mSimpleDateFormat.format(Date())
        deviceRunInMillis = System.currentTimeMillis().toString()
        binding?.tvDeviceReportRunTime?.text = deviceRun
        deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        binding?.tvDeviceId?.text = deviceId

        if (llgName.isNotEmpty()) {
            binding?.llLLG?.visibility = View.VISIBLE
            binding?.tvLLG?.text = llgName
        }
        if (wardName.isNotEmpty()) {
            binding?.llWard?.visibility = View.VISIBLE
            binding?.tvWard?.text = "$wardNumber & $wardName"
        }
        if (locationName.isNotEmpty()) {
            binding?.llLocation?.visibility = View.VISIBLE
            binding?.tvLocation?.text = locationName
        }
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

    private fun getGenderLessVotersListUsingCoRoutines() {
        CommonMethods.showProgress(this)
        launch(Dispatchers.Main) {
            val result: List<VotersList> = withContext(Dispatchers.IO) {
                getGenderLessVotersList()
            }
            districtGenderLessVotersList = result as MutableList<VotersList>
            loadData()
        }

    }

    private fun loadData() {
        if (districtGenderLessVotersList.size == 0) {
            binding?.llBottom?.visibility = View.GONE
            CommonMethods.hideProgress()
            showAlertMessage("No voters found with empty gender!")
        } else {
            binding?.tvTotalCount?.text = districtGenderLessVotersList.size.toString()
            binding?.llBottom?.visibility = View.VISIBLE
            binding?.rvDistrictGenderLessVotersList?.layoutManager =
                object : LinearLayoutManager(this, VERTICAL, false) {
                    override fun onLayoutCompleted(state: RecyclerView.State?) {
                        super.onLayoutCompleted(state)
                        CommonMethods.hideProgress()
                    }
                }
            setDistrictGenderLessListAdapter(districtGenderLessVotersList)
        }

    }

    private fun setDistrictGenderLessListAdapter(districtGenderLessVotersList: MutableList<VotersList>) {

        genderLessVotersListAdapter = object :
            GenericAdapter<VotersList, ItemGenderLessVoterBinding>(districtGenderLessVotersList) {
            override fun getLayoutId(): Int {
                return R.layout.item_gender_less_voter
            }

            @SuppressLint("SetTextI18n")
            override fun onBindData(
                model: VotersList?,
                position: Int,
                dataBinding: ItemGenderLessVoterBinding?
            ) {


                dataBinding?.cvParent?.visibility = View.VISIBLE

                dataBinding?.tvFName?.text = "First Name: " + CommonMethods.getVoterDetails(
                    model!!.fName
                )!!

                dataBinding?.tvLName?.text = "Last Name: " + CommonMethods.getVoterDetails(
                    model.lName
                )!!



                dataBinding?.tvElectorId?.text = "Elector ID - " + model.electorId

                dataBinding?.tvDOB?.text = "DOB: " + CommonMethods.getDisplayDate(
                    CommonMethods.getVoterDetails(
                        model.dob
                    )!!
                )


                dataBinding?.tvLLG?.text = "LLG: " + CommonMethods.getVoterDetails(
                    model.llgName
                )!!

                dataBinding?.tvWard?.text = "Ward: " + CommonMethods.getVoterDetails(
                    model.wardName
                )!!




                dataBinding?.tvOccupation?.text =
                    "Occupation: " + CommonMethods.getVoterDetails(
                        model.occupation
                    )!!


            }

        }
        binding?.rvDistrictGenderLessVotersList?.adapter = genderLessVotersListAdapter

    }

    private fun getGenderLessVotersList(): List<VotersList> {
        val list = when {
            locationId != 0 -> {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    dataBaseInstance.votersListDao()
                        .getLocationVotersList(locationId)
                } else {
                    dataBaseInstance.votersListDao()
                        .getLocationVotersListForMKA(locationId)
                }
            }

            wardId != 0 -> {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    dataBaseInstance.votersListDao()
                        .getWardVotersList(wardId)
                } else {
                    dataBaseInstance.votersListDao()
                        .getWardVotersListForMKA(wardId)
                }
            }

            llgId != 0 -> {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    dataBaseInstance.votersListDao()
                        .getLLGVotersList(llgId)
                } else {
                    dataBaseInstance.votersListDao()
                        .getLLGVotersListForMKA(llgId)
                }

            }

            else -> {
                if (securedSharedPreferenceWrapper.getString(
                        ApiConstants.ROLE,
                        ""
                    ) == ApiConstants.ROLE_DPO
                ) {
                    dataBaseInstance.votersListDao()
                        .getDistrictVotersList(districtId)
                } else {
                    dataBaseInstance.votersListDao()
                        .getDistrictVotersListForMKA(districtId)
                }
            }
        }


        val filterList = mutableListOf<VotersList>()

        for (voter in list) {
            val voterGender = CommonMethods.getVoterDetails(voter.gender)!!
            if (voterGender.isEmpty()) {
                filterList.add(voter)
            } else if (voterGender != "M" && voterGender != "F") {
                filterList.add(voter)
            }
        }

        return filterList
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
                        CommonMethods.showSettingsDialog(this@GenderLessVoterListReportActivity)
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
        CommonMethods.showProgress(this)
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
                .toString() + "/" + dpoReportFilesPath + "/"

        val reportsDirectory = File(filePath)
        if (!reportsDirectory.exists())
            reportsDirectory.mkdirs()

        val regex = Regex("[^A-Za-z0-9]")
        if (geoLocationName.isNotEmpty())
            geoLocationName = geoLocationName.let { regex.replace(it, "") }

        val fileName = geoLocationName + "_" + System.currentTimeMillis().toString() + ".pdf"


        val outputFile = File(filePath, fileName)
        outputFile.createNewFile()

        val pdfDocument = PdfDocument(PdfWriter(outputFile))
        pdfDocument.defaultPageSize = PageSize.A4
        val document = Document(pdfDocument)

        val reportTitle = Paragraph(getString(R.string.district_gender_less_voter_list))
        reportTitle.setMarginTop(10f)
        reportTitle.setFontSize(10.0f)
        reportTitle.setBold()
        reportTitle.setTextAlignment(TextAlignment.LEFT)
        document.add(reportTitle)

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

        if (llgName.isNotEmpty()) {
            val llgTable = Table(
                UnitValue.createPercentArray(
                    floatArrayOf(
                        15f,
                        85f
                    )
                )
            ).useAllAvailableWidth()
            llgTable.addCell(getBoldTextCell("LLG:"))
            llgTable.addCell(getCell(llgName))
            document.add(llgTable)
        }
        if (wardName.isNotEmpty()) {
            val wardTable = Table(
                UnitValue.createPercentArray(
                    floatArrayOf(
                        15f,
                        85f
                    )
                )
            ).useAllAvailableWidth()
            wardTable.addCell(getBoldTextCell("Ward:"))
            wardTable.addCell(getCell(wardNumber.toString() + " & " + wardName))
            document.add(wardTable)
        }
        if (locationName.isNotEmpty()) {
            val locationTable = Table(
                UnitValue.createPercentArray(
                    floatArrayOf(
                        15f,
                        85f
                    )
                )
            ).useAllAvailableWidth()
            locationTable.addCell(getBoldTextCell("Location:"))
            locationTable.addCell(getCell(locationName))
            document.add(locationTable)
        }

        table2.addCell(getBoldTextCell("Date Time:"))
        table2.addCell(getCell(CommonMethods.getGenderLessVotersReportPostedDateTime()))
        document.add(table2)

        val table3 = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    15f,
                    85f
                )
            )
        ).useAllAvailableWidth()
        table3.addCell(getBoldTextCell("Device:"))
        table3.addCell(
            getCell(
                Settings.Secure.getString(
                    contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            )
        )
        document.add(table3)
        setGenderLessVoters(document)
        setGrandTotalCount(document)
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

    private fun setGenderLessVoters(document: Document) {

        val votersInfoHeaderTable = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    4f,
                    14f,
                    14f,
                    14f,
                    14f,
                    14f,
                    12f,
                    14f
                )
            )
        ).useAllAvailableWidth()

        votersInfoHeaderTable.setMarginTop(10f)

        votersInfoHeaderTable.addCell(getVotersListHeaderCell("No", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("LLG", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Ward", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Lname", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Fname", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Elector ID", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("DOB", TextAlignment.CENTER))
        votersInfoHeaderTable.addCell(getVotersListHeaderCell("Occupation", TextAlignment.CENTER))

        var count = 0
        for (voter in districtGenderLessVotersList) {

            count += 1

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    count.toString(), TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.llgName
                    )!!, TextAlignment.CENTER
                )
            )

            votersInfoHeaderTable.addCell(
                getVotersListCell(
                    CommonMethods.getVoterDetails(
                        voter.wardName
                    )!!, TextAlignment.CENTER
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
                    voter.electorId.toString(), TextAlignment.CENTER
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

    private fun setGrandTotalCount(document: Document) {
        val grandTotalCountTable = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    74f,
                    26f
                )
            )
        ).useAllAvailableWidth()
        grandTotalCountTable.setMarginTop(10f)
        grandTotalCountTable.addCell(getCountHeaderCell("TOTAL", TextAlignment.LEFT))
        grandTotalCountTable.addCell(
            getCountHeaderCell(
                districtGenderLessVotersList.size.toString(),
                TextAlignment.CENTER
            )
        )
        document.add(grandTotalCountTable)
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

    private fun showDownloadSuccessMessage() {
        Handler(mainLooper).postDelayed({
            CommonMethods.hideProgress()
            showAlertMessage("District Gender Less Voters List Report for $geoLocationName has been generated successfully and placed in the documents folder")
        }, 1000)

    }

}
