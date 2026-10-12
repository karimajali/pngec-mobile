package com.pngelectoral.ui.activity

import ApiConstants
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.storage.StorageVolume
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.json.JsonReadFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
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
import com.pngelectoral.databinding.ActivityOfflineTransferInVotersListBinding
import com.pngelectoral.databinding.ItemTransferInVoterBinding
import com.pngelectoral.ui.adapter.GenericAdapter
import com.pngelectoral.ui.model.DownloadedVotersList
import com.pngelectoral.ui.model.NewVoterDownloadModel
import com.pngelectoral.ui.model.NewVotersList
import com.pngelectoral.ui.model.OccupationList
import com.pngelectoral.ui.model.OfflineTVLM
import com.pngelectoral.ui.model.VotersList
import com.pngelectoral.ui.viewModel.TransferInVotersListModel
import com.pngelectoral.utility.CommonMethods
import com.pngelectoral.utility.CommonMethods.getVoterDetails
import com.pngelectoral.utility.CommonMethods.getVoterName
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.okButton
import java.io.File
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class TransferInChunksOfflineVotersListActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityOfflineTransferInVotersListBinding? = null
    private var transferInVotersListModel: TransferInVotersListModel? = null
    var transferInVotersListAdapter: GenericAdapter<NewVotersList, ItemTransferInVoterBinding>? =
        null
    private var selectedPos = -1
    private var transferInVotersList = mutableListOf<NewVotersList>()

    // input parameters
    var regionId: Int = 0
    var provinceId: Int = 0
    var districtId: Int = 0
    var wardId: Int = 0
    var llgId: Int = 0
    var regionName: String = ""
    var districtName: String = ""
    var llgName: String = ""
    var firstNameSearch: String = ""
    var lastNameSearch: String = ""
    var gender: String = ""
    var provinceName = ""
    lateinit var dataBaseInstance: AppDatabase
    var provinceFilesPath = "PNG_Province_Files"
    var wardNumber: Int = 0
    var wardName: String = ""

    var provinceFileCount: Int = 0
    private lateinit var fileDirectory: File
    private var zipFilePath = ""

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_offline_transfer_in_voters_list
        )
        transferInVotersListModel =
            ViewModelProvider(this).get(TransferInVotersListModel::class.java)
        binding?.transferInVotersListModel = transferInVotersListModel

        job = Job()

        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase

        regionId = intent.getIntExtra("RegionId", 0)
        provinceId = intent.getIntExtra("ProvinceId", 0)
        districtId = intent.getIntExtra("DistrictId", 0)
        districtName = intent.getStringExtra("DistrictName") ?: ""
        wardId = intent.getIntExtra("WardId", 0)
        llgId = intent.getIntExtra("LLGId", 0)
        llgName = intent.getStringExtra("LLGName") ?: ""
        regionName = intent.getStringExtra("RegionName")!!
        firstNameSearch = intent.getStringExtra("FirstName")!!
        lastNameSearch = intent.getStringExtra("LastName")!!
        gender = intent.getStringExtra("Gender")!!
        provinceName = intent.getStringExtra("ProvinceName")!!
        wardNumber = intent.getIntExtra("WardNumber", 0)
        wardName = intent.getStringExtra("WardName")!!
        zipFilePath = securedSharedPreferenceWrapper.getString(ApiConstants.ZipFilePath, "")

        val layoutManager = LinearLayoutManager(this)
        binding?.rvSearchVotersList?.layoutManager = layoutManager

        setFooterData()
        setUpRollLookupVoterListSearch()
        setOnclickListeners()
        requestStoragePermission()
    }

    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
    }

    private fun setFooterData() {

        val dpoName = securedSharedPreferenceWrapper.getString(
            ApiConstants.DPO_NAME,
            ""
        )
        val version = packageManager.getPackageInfo(packageName, 0).versionName

        /*val districtName = securedSharedPreferenceWrapper.getString(
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
        )*/
        val tvBottomDataTitle: String = if (wardName.isEmpty())
            "Version: $version | District: $districtName | DPO: $dpoName"
        else {
            val wardNameDisplay = "$districtName/$llgName/$wardName"
            "Version: $version | Ward: $wardNameDisplay | DPO: $dpoName"
        }
        binding?.tvBottomData?.text = tvBottomDataTitle
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
                        CommonMethods.showSettingsDialog(this@TransferInChunksOfflineVotersListActivity)
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
                getProvinceFilesPath()
            } else {
                val intent = Intent(
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                )

                val storagePermissionResultLauncher = registerForActivityResult(
                    ActivityResultContracts.StartActivityForResult()
                ) {
                    if (Environment.isExternalStorageManager()) {
                        getProvinceFilesPath()
                    }
                }
                storagePermissionResultLauncher.launch(intent)
            }

        } else {
            getProvinceFilesPath()
        }
    }

    private fun getProvinceFilesPath() {

        CommonMethods.showProgress(this)
        val sdCardPath = getExternalSdCardPath()
        fileDirectory = if (sdCardPath!!.isEmpty()) {
            File(
                Environment.getExternalStorageDirectory()
                    .toString() + "/" + provinceFilesPath + "/"
            )
        } else {
            File(
                "$sdCardPath/$provinceFilesPath/"
            )
        }

        readProvinceFileData()

    }

    private fun getExternalSdCardPath(): String? {

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

    private fun readProvinceFileData() {
        val files = File(zipFilePath).listFiles()
        if (provinceFileCount <= ((files?.size ?: 0) - 1)) {
            if (files?.get(provinceFileCount)?.exists() == true) {
                launch(Dispatchers.Main) {
                    val result: List<NewVotersList> = withContext(Dispatchers.IO) {
                        getDownloadedTransferInVotersList(files[provinceFileCount])
                    }
                    storeDataAndFetchFromAnotherFile(result)
                }
            } else {
                setData()
            }
        } else {.0
            setData()
        }
    }

    private fun getDownloadedTransferInVotersList(file: File): ArrayList<NewVotersList> {
        val fileInputStream = FileInputStream(file)
        val filteredVotersList: ArrayList<NewVotersList> = ArrayList()

        try {
            val objectMapper: ObjectMapper = JsonMapper.builder()
                .enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS)
                .enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES)
                .build()

            val offLineTransferInVotersResult: NewVoterDownloadModel =
                objectMapper.readValue(
                    fileInputStream,
                    NewVoterDownloadModel::class.java
                )

            val resultArray: ArrayList<NewVotersList>? =
                offLineTransferInVotersResult.downloadedVotersList

            if (securedSharedPreferenceWrapper.getString(
                    ApiConstants.ROLE,
                    ""
                ) == ApiConstants.ROLE_DPO
            ) {
                for (voter in resultArray!!) {
                    if (voter.districtId == districtId) {
                        if (llgId == 0 && checkGenderAndAddResult(voter)) {
                            filteredVotersList.add(voter)
                        } else if (voter.llgId == llgId) {
                            if (wardId == 0 && checkGenderAndAddResult(voter)) {
                                filteredVotersList.add(voter)
                            } else if (voter.wardId == wardId && checkGenderAndAddResult(voter)) {
                                filteredVotersList.add(voter)
                            }
                        }
                    }
                }
            } else {
                for (voter in resultArray!!) {
                    if (voter.mkaDistrictId == districtId) {
                        if (llgId == 0 && checkGenderAndAddResult(voter)) {
                            filteredVotersList.add(voter)
                        } else if (voter.mkallgId == llgId) {
                            if (wardId == 0 && checkGenderAndAddResult(voter)) {
                                filteredVotersList.add(voter)
                            } else if (voter.mkaWardId == wardId && checkGenderAndAddResult(voter)) {
                                filteredVotersList.add(voter)
                            }
                        }
                    }
                }
            }
            return filteredVotersList
        } catch (e: Exception) {
            Log.e("FilReadError", "Error==>"+e.localizedMessage)
            return filteredVotersList
        }
    }

    private fun setData() {
        CommonMethods.hideProgress()
        if (transferInVotersList.isEmpty()) {
            binding?.tvTitle?.text = "Found " + transferInVotersList.size + " Voters"
            binding?.rvSearchVotersList?.visibility = View.GONE
            binding?.tvNoVoters?.visibility = View.VISIBLE
        } else {
            if (transferInVotersList.size == 1)
                binding?.tvTitle?.text = "Found " + transferInVotersList.size + " Voter"
            else
                binding?.tvTitle?.text = "Found " + transferInVotersList.size + " Voters"
            setWardRollVotersListAdapter(transferInVotersList)
        }
    }

    private fun storeDataAndFetchFromAnotherFile(result: List<NewVotersList>?) {
        transferInVotersList.addAll(result!!)
        provinceFileCount++
        readProvinceFileData()
    }


    private fun checkGenderAndAddResult(downloadedVotersList: NewVotersList): Boolean {

        val sameGender = if (gender.isEmpty())
            true
        else gender == downloadedVotersList.gender

        if (sameGender) {

            return if (firstNameSearch.isEmpty()) {
                if (downloadedVotersList.lName != null && downloadedVotersList.lName!!.length >= lastNameSearch.length) {
                    lastNameSearch.toLowerCase(Locale.ENGLISH) == downloadedVotersList.lName.take(
                        lastNameSearch.length
                    ).toLowerCase(Locale.ENGLISH)
                } else {
                    false
                }
            } else {

                downloadedVotersList.lName != null && downloadedVotersList.lName!!.length >= lastNameSearch.length &&
                        lastNameSearch.toLowerCase(Locale.ENGLISH) == downloadedVotersList.lName.take(
                    lastNameSearch.length
                ).toLowerCase(Locale.ENGLISH) &&
                        downloadedVotersList.fName != null && downloadedVotersList.fName!!.length >= firstNameSearch.length &&
                        firstNameSearch.toLowerCase(Locale.ENGLISH) == downloadedVotersList.fName.take(
                    firstNameSearch.length
                ).toLowerCase(Locale.ENGLISH)

            }

        } else {
            return false
        }


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


    private fun setWardRollVotersListAdapter(transferInVotersList: MutableList<NewVotersList>) {

        transferInVotersListAdapter = object :
            GenericAdapter<NewVotersList, ItemTransferInVoterBinding>(
                transferInVotersList
            ) {
            override fun getLayoutId(): Int {
                return R.layout.item_transfer_in_voter
            }

            @SuppressLint("SetTextI18n")
            override fun onBindData(
                model: NewVotersList?,
                position: Int,
                dataBinding: ItemTransferInVoterBinding?
            ) {

                if (model?.electorProfileImageUrl != null
                    && model.electorProfileImageUrl.isNotEmpty()
                ) {

                    Glide.with(this@TransferInChunksOfflineVotersListActivity)
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
                dataBinding?.tvVoterLocation?.text = getVoterDetails(
                    model?.locationName
                )!!
                dataBinding?.tvVoterStatus?.text = model?.status

                when {
                    getVoterDetails(model?.gender)!! == "F" -> dataBinding?.tvGender?.text =
                        "Gender: Female"

                    getVoterDetails(model?.gen)!! == "M" -> dataBinding?.tvGender?.text =
                        "Gender: Male"

                    else -> dataBinding?.tvGender?.text = "Gender:"
                }

                dataBinding?.tvDob?.text = "Dob: " + CommonMethods.getDisplayDate(
                    getVoterDetails(
                        model?.dob
                    )!!
                )
//                dataBinding?.tvOccupation?.text = "Occupation: " + getVoterDetails(
//                    model?.Occupation
//                )!!
                setOccupation(model, dataBinding?.tvOccupation)
                dataBinding?.tvNID?.text =
                    "National ID Card Number: " + getVoterDetails(
                        model?.natIDRecordNo
                    )!!

                dataBinding?.tvRegion?.text =
                    "Region: $regionName"

                dataBinding?.tvProvince?.text =
                    "Province: " + getVoterDetails(
                        model?.provinceName
                    )!!

                dataBinding?.tvDistrict?.text =
                    "District: " + getVoterDetails(
                        model?.districtName
                    )!!

                dataBinding?.tvLLG?.text =
                    "LLG: " + getVoterDetails(
                        model?.llgName
                    )!!

                dataBinding?.tvWard?.text =
                    "Ward: " + getVoterDetails(
                        model?.wardName
                    )!!

                dataBinding?.tvLocation?.text =
                    "Location: " + getVoterDetails(
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
        binding?.rvSearchVotersList?.adapter = transferInVotersListAdapter

    }

    private fun setOccupation(model: NewVotersList?, tvOccupation: AppCompatTextView?) {

        if (getVoterDetails(model!!.occupation)!!.isEmpty()) {
            if (model.occupationFK != null) {

                launch(Dispatchers.Main) {
                    val result: OccupationList? = withContext(Dispatchers.IO) {
                        dataBaseInstance.occupationListDao()?.getOccupation(model.occupationFK)
                    }
                    tvOccupation!!.text = "Occupation: " + result!!.occupationName
                }

            } else {
                tvOccupation!!.text = "Occupation: Not Specified"
            }

        } else
            tvOccupation!!.text = "Occupation: " + getVoterDetails(model.occupation)!!
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

    private fun navigateToVoterDetailsScreen(
        model: NewVotersList?
    ) {

        alert("Are you sure you want to transfer this voter?") {
            okButton {
                val selectedTransferInVoter = getVoterData(model)
                val intent =
                    Intent(
                        this@TransferInChunksOfflineVotersListActivity,
                        TransferInWardSelectionActivity::class.java
                    )
                intent.putExtra(ApiConstants.VOTER_DETAILS, selectedTransferInVoter)
                startActivity(intent)
            }
            cancelButton { }
        }.show()
    }

    private fun getVoterData(model: NewVotersList?): VotersList {

        val voterDetailsResponse = VotersList()


        voterDetailsResponse.electorId = model?.electorId
        voterDetailsResponse.oldElectorID = model?.oldElectorID
        voterDetailsResponse.locationId = model?.locationId
        voterDetailsResponse.locationName = model?.locationName
        voterDetailsResponse.wardId = model?.wardId
        voterDetailsResponse.wardName = model?.wardName
        voterDetailsResponse.llgId = model?.llgId
        voterDetailsResponse.llgName = model?.llgName
        voterDetailsResponse.districtId = model?.districtId
        voterDetailsResponse.provinceId = model?.provinceId
        voterDetailsResponse.regionId = model?.regionId
        voterDetailsResponse.lName = model?.lName
        voterDetailsResponse.fName = model?.fName
        voterDetailsResponse.voterName = model?.voterName
        voterDetailsResponse.gender = model?.gender
        voterDetailsResponse.dob = model?.dob
        voterDetailsResponse.isYOB = model?.isYOB
        voterDetailsResponse.isPWD = model?.isPWD
        voterDetailsResponse.religion = model?.religion
        voterDetailsResponse.fatherLName = model?.fatherLName
        voterDetailsResponse.fatherFName = model?.fatherFName
        voterDetailsResponse.motherLName = model?.motherLName
        voterDetailsResponse.motherFName = model?.motherFName
        voterDetailsResponse.provOfBirthFK = model?.provOfBirthFK
        voterDetailsResponse.provinceOfBirth = model?.provinceOfBirth
        voterDetailsResponse.provinceName = model?.provinceName
        voterDetailsResponse.districtName = model?.districtName
        voterDetailsResponse.placeOfBirth = model?.placeOfBirth
        voterDetailsResponse.occupationFK = model?.occupationFK
        voterDetailsResponse.occupation = model?.occupation
        voterDetailsResponse.clanFK = 0
        voterDetailsResponse.clan = model?.clan
        voterDetailsResponse.subClan = model?.subClan
        voterDetailsResponse.address = model?.address
        voterDetailsResponse.natID = model?.natID
        voterDetailsResponse.natIDRecordNo = model?.natIDRecordNo
        voterDetailsResponse.status = model?.status
        voterDetailsResponse.houseFK = model?.houseFK
        voterDetailsResponse.houseSortOrder = model?.houseSortOrder
        voterDetailsResponse.restHouse = model?.restHouse
        voterDetailsResponse.sectionNo = model?.sectionNo
        voterDetailsResponse.lotNo = model?.lotNo
        voterDetailsResponse.streetName = model?.streetName
        voterDetailsResponse.suburbTownSettlement = model?.suburbTownSettlement
        voterDetailsResponse.familyFK = model?.familyFK
        voterDetailsResponse.familySortOrder = model?.familySortOrder
        voterDetailsResponse.familyNumber = model?.familyNumber
        voterDetailsResponse.familyName = model?.familyName
        voterDetailsResponse.dpo = model?.dpo
        voterDetailsResponse.lat = model?.lat
        voterDetailsResponse.longitude = model?.Long
        voterDetailsResponse.posted = model?.posted
        voterDetailsResponse.comment = model?.comment
        voterDetailsResponse.electorProfileImage = model?.electorProfileImage
        voterDetailsResponse.electorProfileImageUrl = model?.electorProfileImageUrl
        voterDetailsResponse.electorProfileImageFile = model?.electorProfileImageFile
        voterDetailsResponse.electorFormImage = model?.electorFormImage
        voterDetailsResponse.electorFormImageUrl = model?.electorFormImageUrl
        voterDetailsResponse.electorFormImageFile = model?.electorFormImageFile
        voterDetailsResponse.enrolmentFormNumber = model?.enrolmentFormNumber
        voterDetailsResponse.createdBy = model?.createdBy

        return voterDetailsResponse
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


        val filteredVotersList = mutableListOf<NewVotersList>()

        for (i in transferInVotersList.indices) {

            if (searchWithFirstAndLastName(
                    voterName,
                    transferInVotersList[i].fName,
                    transferInVotersList[i].lName
                )

            ) {
                filteredVotersList.add(transferInVotersList[i])
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
            setWardRollVotersListAdapter(filteredVotersList)
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
