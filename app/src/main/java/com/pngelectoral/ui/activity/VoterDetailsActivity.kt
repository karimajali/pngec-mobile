package com.pngelectoral.ui.activity

import ApiConstants
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.InputType
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.pngelectoral.BuildConfig
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityVoterDetailsBinding
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.retrofit.*
import com.pngelectoral.ui.viewModel.VoterDetailsViewModel
import com.pngelectoral.utility.CommonMethods
import com.pngelectoral.utility.CommonMethods.getDisplayDate
import com.pngelectoral.utility.CommonMethods.getDisplayDateAndTime
import com.pngelectoral.utility.CommonMethods.getIntegerVoterDetails
import com.pngelectoral.utility.CommonMethods.getVoterDetails
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import java.io.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class VoterDetailsActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityVoterDetailsBinding? = null
    private var voterDetailsViewModel: VoterDetailsViewModel? = null
    var voterDetailsResponse: VotersList = VotersList()
    lateinit var loggedInDPOUserData: DPOUserData
    private var isFromRejectedList = false
    lateinit var dataBaseInstance: AppDatabase
    var wardName = ""
    private var filterType = ""

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job
    private lateinit var storagePermissionResultLauncher: ActivityResultLauncher<Intent?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_voter_details
        )
        voterDetailsViewModel =
            ViewModelProvider(this).get(VoterDetailsViewModel::class.java)
        binding?.voterDetailsViewModel = voterDetailsViewModel

        intent?.apply {
            filterType =
                getStringExtra(ApiConstants.FILTER_TYPE) ?: ApiConstants.PNGEC_GEO_STRUCTURE
        }

        job = Job()

        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase

        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )

        setFooterData()

        voterDetailsResponse = intent.getSerializableExtra(ApiConstants.VOTER_DETAILS) as VotersList
        isFromRejectedList = intent.getBooleanExtra(ApiConstants.FROM_REJECT_LIST, false)
        binding?.tvVoterName?.text = CommonMethods.getVoterName(voterDetailsResponse)


        setOnClickListeners()
        setVoterData(voterDetailsResponse)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            storagePermissionResultLauncher = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (Environment.isExternalStorageManager()) {
                    captureImage()
                }
            }
        }


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

    private fun setVoterData(voterDetailsResponse: VotersList) {

        if (voterDetailsResponse.electorProfileImage != null
            && voterDetailsResponse.electorProfileImage.isNotEmpty()
        ) {

            val image =
                File(voterDetailsResponse.electorProfileImage)

            if (image.exists()) {
                Glide.with(this).load(Uri.fromFile(image))
                    .into(binding?.ivVoterImage!!)
            } else if (voterDetailsResponse.electorProfileImageUrl != null
                && voterDetailsResponse.electorProfileImageUrl.isNotEmpty()
            ) {

                Glide.with(this)
                    .load(voterDetailsResponse.electorProfileImageUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding?.ivVoterImage?.setImageResource(R.drawable.e_user)
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
                    .into(binding?.ivVoterImage!!)

            } else {
                binding?.ivVoterImage?.setImageResource(R.drawable.e_user)
            }

        } else if (voterDetailsResponse.status == "NEW"
            && (voterDetailsResponse.electorProfileImageUrl == null
                    || voterDetailsResponse.electorProfileImageUrl.isEmpty())
        ) {

            val image =
                File(voterDetailsResponse.electorProfileImage)

            if (image.exists()) {
                Glide.with(this).load(Uri.fromFile(image))
                    .into(binding?.ivVoterImage!!)
            } else {
                binding?.ivVoterImage?.setImageResource(R.drawable.e_user)
            }

        } else {

            if (voterDetailsResponse.electorProfileImageUrl != null
                && voterDetailsResponse.electorProfileImageUrl.isNotEmpty()
            ) {
                Glide.with(this)
                    .load(voterDetailsResponse.electorProfileImageUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding?.ivVoterImage?.setImageResource(R.drawable.e_user)
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
                    .into(binding?.ivVoterImage!!)
            } else {
                binding?.ivVoterImage?.setImageResource(R.drawable.e_user)
            }
        }

        binding?.etFamilyNumber?.text = getVoterDetails(voterDetailsResponse.familyNumber)!!
        binding?.etFamilyName?.text = getVoterDetails(voterDetailsResponse.familyName)!!
        binding?.etFamilyOrderNo?.text = getVoterDetails(voterDetailsResponse.familySortOrder)!!
        binding?.etLastName?.text = getVoterDetails(voterDetailsResponse.lName)!!
        binding?.etGivenName?.text = getVoterDetails(voterDetailsResponse.fName)!!
        binding?.etGivenName?.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        binding?.etLastName?.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        binding?.etNationalIDCardNumber?.text =
            getVoterDetails(voterDetailsResponse.natIDRecordNo)!!
        binding?.etDateOfBirth?.text = getDisplayDate(getVoterDetails(voterDetailsResponse.dob)!!)
        binding?.etPlaceOfBirth?.text = getVoterDetails(voterDetailsResponse.placeOfBirth)!!
        if (getVoterDetails(voterDetailsResponse.provinceOfBirth)!!.isEmpty()) {
            if (voterDetailsResponse.provOfBirthFK != 0)
                getVoterProvinceOfBirthName()
        } else {
            binding?.etProvinceOfBirth?.setText(getVoterDetails(voterDetailsResponse.provinceOfBirth)!!)
        }

        if (getVoterDetails(voterDetailsResponse.gender)!! == "F")
            binding?.etGender?.text = getString(R.string.female)
        else if (getVoterDetails(voterDetailsResponse.gender)!! == "M")
            binding?.etGender?.text = getString(R.string.male)

        if (getVoterDetails(voterDetailsResponse.occupation)!!.isEmpty()) {
            if (voterDetailsResponse.occupationFK != null && voterDetailsResponse.occupationFK != 0)
                getOccupation(voterDetailsResponse.occupationFK)
        } else {
            binding?.etOccupation?.text = getVoterDetails(voterDetailsResponse.occupation)!!
        }

        if (voterDetailsResponse.isPWD)
            binding?.etPersonWithDisability?.text = getString(R.string.yes)
        else
            binding?.etPersonWithDisability?.text = getString(R.string.no)

        binding?.etReligion?.text = getVoterDetails(voterDetailsResponse.religion)!!

        binding?.etFathersFirstName?.text = getVoterDetails(voterDetailsResponse.fatherFName)!!
        binding?.etFathersLastName?.text = getVoterDetails(voterDetailsResponse.fatherLName)!!
        binding?.etMothersFirstName?.text = getVoterDetails(voterDetailsResponse.motherFName)!!
        binding?.etMothersLastName?.text = getVoterDetails(voterDetailsResponse.motherLName)!!

        // Place of residence

        binding?.etWardNoAndName?.text = getVoterDetails(voterDetailsResponse.wardName)!!
        wardName = getVoterDetails(voterDetailsResponse.wardName)!!
        if (voterDetailsResponse.wardId != 0)
            getVoterWardName()


        if (voterDetailsResponse.provinceName.isNullOrEmpty()) {
            if (voterDetailsResponse.provinceId != 0)
                getVoterProvinceName()
        } else {
            binding?.etProvince?.text = getVoterDetails(voterDetailsResponse.provinceName)!!
        }

        if (voterDetailsResponse.districtName.isNullOrEmpty()) {
            if (voterDetailsResponse.districtId != 0)
                getVoterDistrictName()
        } else {
            binding?.etOpenElectorate?.text = getVoterDetails(voterDetailsResponse.districtName)!!
        }

        if (voterDetailsResponse.llgName.isNullOrEmpty()) {
            if (voterDetailsResponse.llgId != 0)
                getVoterLLGName()
        } else {
            binding?.etLLG?.text = getVoterDetails(voterDetailsResponse.llgName)!!
        }


        if (voterDetailsResponse.locationName.isNullOrEmpty()) {
            if (voterDetailsResponse.locationId != 0)
                getVoterLocationName()
        } else {
            binding?.etLocationOrVillage?.text = getVoterDetails(
                voterDetailsResponse.locationName
            )!!
        }

        // Rural

        binding?.etClan?.text = getVoterDetails(voterDetailsResponse.clanName)!!
        binding?.etSubClan?.text = getVoterDetails(voterDetailsResponse.subClan)!!
        binding?.etRestHouse?.text = getVoterDetails(voterDetailsResponse.restHouse)!!

        //Urban

        binding?.etSectionNo?.text = getVoterDetails(voterDetailsResponse.sectionNo)!!
        binding?.etLotNo?.text = getVoterDetails(voterDetailsResponse.lotNo)!!
        binding?.etStreetName?.text = getVoterDetails(voterDetailsResponse.streetName)!!
        binding?.etSuburb?.text = getVoterDetails(voterDetailsResponse.suburbTownSettlement)!!

        //Status

        binding?.etStatus?.text = getVoterDetails(voterDetailsResponse.status)!!
        binding?.etComment?.text = getVoterDetails(voterDetailsResponse.comment)!!
//        binding?.etGPSLocation?.setText(getVoterDetails(voterDetailsResponse.locationName)!!) // Need to replace with GPS Location
        binding?.etDateAndTime?.text =
            getDisplayDateAndTime(getVoterDetails(voterDetailsResponse.posted)!!)
        binding?.etSubmittedBy?.text = getVoterDetails(voterDetailsResponse.dpo)!!

    }

    // Coroutine functions to perform DB Operations

    private fun getOccupation(occupationFK: Int) {
        launch(Dispatchers.Main) {
            val result: OccupationList? = withContext(Dispatchers.IO) {
                dataBaseInstance.occupationListDao()?.getOccupation(occupationFK)
            }
            binding?.etOccupation?.text = result!!.occupationName
        }
    }

    private fun getVoterProvinceOfBirthName() {
        launch(Dispatchers.Main) {
            val result: ProvinceList? = withContext(Dispatchers.IO) {
                fetchVoterProvinceOfBirthNameFromDB()
            }
            binding?.etProvinceOfBirth?.setText(result!!.provinceName)
        }
    }

    private fun fetchVoterProvinceOfBirthNameFromDB(): ProvinceList? {
        return dataBaseInstance.provinceListDao()
            ?.getDPOProvince(voterDetailsResponse.provOfBirthFK)
    }

    private fun getVoterProvinceName() {
        launch(Dispatchers.Main) {
            val result: ProvinceList? = withContext(Dispatchers.IO) {
                fetchVoterProvinceNameFromDB()
            }
            binding?.etProvince?.text = result!!.provinceName
        }
    }

    private fun fetchVoterProvinceNameFromDB(): ProvinceList? {
        return dataBaseInstance.provinceListDao()
            ?.getDPOProvince(voterDetailsResponse.provinceId)
    }

    private fun getVoterDistrictName() {
        launch(Dispatchers.Main) {
            val result: DistrictList? = withContext(Dispatchers.IO) {
                fetchVoterDistrictNameFromDB()
            }
            binding?.etOpenElectorate?.text = result!!.districtName
        }
    }

    private fun fetchVoterDistrictNameFromDB(): DistrictList? {
        return dataBaseInstance.districtListDao()
            ?.getDPODistrict(voterDetailsResponse.districtId)
    }

    private fun getVoterLLGName() {
        launch(Dispatchers.Main) {
            val result: LLGList? = withContext(Dispatchers.IO) {
                fetchVoterLLGFromDB()
            }
            binding?.etLLG?.text = result!!.llgName
        }
    }

    private fun fetchVoterLLGFromDB(): LLGList? {
        return dataBaseInstance.llgListDao()?.getDPOLLG(voterDetailsResponse.llgId)
    }

    @SuppressLint("SetTextI18n")
    private fun getVoterWardName() {
        launch(Dispatchers.Main) {
            val result: WardList? = withContext(Dispatchers.IO) {
                fetchVoterWardFromDB()
            }
            binding?.etWardNoAndName?.text = result!!.wardNumber.toString() + "&" + result.wardName
            wardName = result.wardName
        }
    }

    private fun fetchVoterWardFromDB(): WardList? {
        return dataBaseInstance.wardListDao()?.getDPOWard(voterDetailsResponse.wardId)
    }

    private fun getVoterLocationName() {
        launch(Dispatchers.Main) {
            val result: LocationList? = withContext(Dispatchers.IO) {
                fetchVoterLocationFromDB()
            }
            if (result != null)
                binding?.etLocationOrVillage?.text = result.locationName
        }
    }

    private fun fetchVoterLocationFromDB(): LocationList? {
        return dataBaseInstance.locationListDao()
            ?.getDPOLocation(voterDetailsResponse.locationId)
    }


    private fun setOnClickListeners() {

        binding?.btnHome?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }

        binding?.btnBack?.setOnClickListener {
            finish()
        }

        binding?.rlVoterImage?.setOnClickListener {
            requestCameraPermission()
        }

        binding?.btnUpdateVoterDetails?.setOnClickListener {
            navigateToUpdateVoterDetails()
        }
    }


    private fun updateVoterStatus() {
        val updatedVoterInformation = VotersList()
        updatedVoterInformation.tempElectorId = voterDetailsResponse.tempElectorId
        updatedVoterInformation.electorId = voterDetailsResponse.electorId
        updatedVoterInformation.oldElectorID = voterDetailsResponse.oldElectorID
        updatedVoterInformation.locationId = voterDetailsResponse.locationId
        updatedVoterInformation.locationName = getVoterDetails(voterDetailsResponse.locationName)
        updatedVoterInformation.wardId = voterDetailsResponse.wardId
        updatedVoterInformation.wardName = wardName
        updatedVoterInformation.llgId = voterDetailsResponse.llgId
        updatedVoterInformation.llgName = getVoterDetails(voterDetailsResponse.llgName)
        updatedVoterInformation.districtId = voterDetailsResponse.districtId
        updatedVoterInformation.provinceId = voterDetailsResponse.provinceId
        updatedVoterInformation.regionId = voterDetailsResponse.regionId
        updatedVoterInformation.lName = getVoterDetails(voterDetailsResponse.lName)
        updatedVoterInformation.fName = getVoterDetails(voterDetailsResponse.fName)
        updatedVoterInformation.voterName =
            getVoterDetails(voterDetailsResponse.lName) + " " + getVoterDetails(voterDetailsResponse.fName)
        updatedVoterInformation.gender = getVoterDetails(voterDetailsResponse.gender)
        updatedVoterInformation.dob = getVoterDetails(voterDetailsResponse.dob)
        updatedVoterInformation.isYOB =
            CommonMethods.getVoterDetailsBoolean(voterDetailsResponse.isYOB)
        updatedVoterInformation.isPWD = voterDetailsResponse.isPWD
        updatedVoterInformation.religion = getVoterDetails(voterDetailsResponse.religion)
        updatedVoterInformation.fatherFName = getVoterDetails(voterDetailsResponse.fatherFName)
        updatedVoterInformation.fatherLName = getVoterDetails(voterDetailsResponse.fatherLName)
        updatedVoterInformation.motherFName = getVoterDetails(voterDetailsResponse.motherFName)
        updatedVoterInformation.motherLName = getVoterDetails(voterDetailsResponse.motherLName)
        updatedVoterInformation.provinceOfBirth =
            getVoterDetails(voterDetailsResponse.provinceOfBirth)
        updatedVoterInformation.provOfBirthFK = voterDetailsResponse.provOfBirthFK
        updatedVoterInformation.provinceName = getVoterDetails(voterDetailsResponse.provinceName)
        updatedVoterInformation.districtName =
            getVoterDetails(voterDetailsResponse.districtName)
        updatedVoterInformation.placeOfBirth = getVoterDetails(voterDetailsResponse.placeOfBirth)
        updatedVoterInformation.occupationFK = voterDetailsResponse.occupationFK
        if (getVoterDetails(voterDetailsResponse.occupation).isNullOrEmpty())
            updatedVoterInformation.occupation = binding?.etOccupation?.text.toString().trim()
        else
            updatedVoterInformation.occupation = getVoterDetails(voterDetailsResponse.occupation)
        updatedVoterInformation.clan = getVoterDetails(voterDetailsResponse.clan)
        updatedVoterInformation.clanId = voterDetailsResponse.clanId
        updatedVoterInformation.clanName = voterDetailsResponse.clanName
        updatedVoterInformation.subClan = getVoterDetails(voterDetailsResponse.subClan)
        updatedVoterInformation.address = getVoterDetails(voterDetailsResponse.locationName)
        updatedVoterInformation.natID = ""
        updatedVoterInformation.natIDRecordNo = getVoterDetails(voterDetailsResponse.natIDRecordNo)

        if (voterDetailsResponse.status == "NEW"  || voterDetailsResponse.status == "TRANSFERRED IN" ) {
            updatedVoterInformation.status =  getVoterDetails(voterDetailsResponse.status)
        }
        else
        {
            updatedVoterInformation.status = getString(R.string.updated)
        }
        updatedVoterInformation.houseFK = 0
        updatedVoterInformation.houseSortOrder = 0
        updatedVoterInformation.restHouse = getVoterDetails(voterDetailsResponse.restHouse)
        updatedVoterInformation.sectionNo = getIntegerVoterDetails(voterDetailsResponse.sectionNo)
        updatedVoterInformation.lotNo = getIntegerVoterDetails(voterDetailsResponse.lotNo)
        updatedVoterInformation.streetName = getVoterDetails(voterDetailsResponse.streetName)
        updatedVoterInformation.suburbTownSettlement =
            getVoterDetails(voterDetailsResponse.suburbTownSettlement)
        updatedVoterInformation.familyFK = 0
        updatedVoterInformation.familyNumber =
            getIntegerVoterDetails(voterDetailsResponse.familyNumber)
        updatedVoterInformation.familyName = getVoterDetails(voterDetailsResponse.familyName)
        updatedVoterInformation.familySortOrder =
            getIntegerVoterDetails(voterDetailsResponse.familySortOrder)
        updatedVoterInformation.dpo =
            securedSharedPreferenceWrapper.getString(ApiConstants.DPO_NAME, "")
        updatedVoterInformation.lat = CommonMethods.getLatitudeOrLongitude(
            securedSharedPreferenceWrapper.getString(
                ApiConstants.LATITUDE,
                "0"
            )
        )
        updatedVoterInformation.longitude =
            CommonMethods.getLatitudeOrLongitude(
                securedSharedPreferenceWrapper.getString(
                    ApiConstants.LONGITUDE,
                    "0"
                )
            )
        updatedVoterInformation.posted = CommonMethods.getVoterDetailsPostedDateTime()
        updatedVoterInformation.updatedDate = CommonMethods.getVoterDetailsUpdatedDate()
        updatedVoterInformation.comment = getVoterDetails(voterDetailsResponse.comment)
        updatedVoterInformation.electorProfileImage =
            getVoterDetails(voterDetailsResponse.electorProfileImage)
        updatedVoterInformation.electorProfileImageUrl =
            getVoterDetails(voterDetailsResponse.electorProfileImageUrl)
        updatedVoterInformation.electorFormImage =
            getVoterDetails(voterDetailsResponse.electorFormImage)
        updatedVoterInformation.electorFormImageUrl =
            getVoterDetails(voterDetailsResponse.electorFormImageUrl)
        updatedVoterInformation.electorProfileImageFile =
            getVoterDetails(voterDetailsResponse.electorProfileImageFile)
        updatedVoterInformation.electorFormImageFile =
            getVoterDetails(voterDetailsResponse.electorFormImageFile)
        updatedVoterInformation.enrolmentFormNumber =
            getVoterDetails(voterDetailsResponse.enrolmentFormNumber)
        updatedVoterInformation.createdBy = loggedInDPOUserData.userId.toInt()
        updatedVoterInformation.isSyncToServer = 0
        updatedVoterInformation.isDataUploaded = 0
        updatedVoterInformation.syncStatus = 0

        updatedVoterInformation.mkaDistrictId = getIntegerVoterDetails(voterDetailsResponse.mkaDistrictId)
        updatedVoterInformation.mkaDistrictName = getVoterDetails(voterDetailsResponse.mkaDistrictName)
        updatedVoterInformation.mkaLlgId = getIntegerVoterDetails(voterDetailsResponse.mkaLlgId)
        updatedVoterInformation.mkaLlgName = getVoterDetails(voterDetailsResponse.mkaLlgName)
        updatedVoterInformation.mkaWardId = getIntegerVoterDetails(voterDetailsResponse.mkaWardId)
        updatedVoterInformation.mkaWardName = getVoterDetails(voterDetailsResponse.mkaWardName)
        updatedVoterInformation.mkaLocationId = getIntegerVoterDetails(voterDetailsResponse.mkaLocationId)
        updatedVoterInformation.mkaLocationName = getVoterDetails(voterDetailsResponse.mkaLocationName)
        updatedVoterInformation.mkaClanId = getIntegerVoterDetails(voterDetailsResponse.mkaClanId)
        updatedVoterInformation.mkaClanName = getVoterDetails(voterDetailsResponse.mkaClanName)


        if (isFromRejectedList)
            updatedVoterInformation.rejectRecordUpdated = 1


        if (selectedImagePath!!.isNotEmpty()) {
            updatedVoterInformation.electorProfileImage = selectedImagePath
            updatedVoterInformation.isImageUploaded = 0
        }

        UpdatedVoterInformationToDB(updatedVoterInformation).execute()

    }

    @SuppressLint("StaticFieldLeak")
    internal inner class UpdatedVoterInformationToDB(private val updatedVoterInformation: VotersList = VotersList()) :
        AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            val data = dataBaseInstance.votersListDao()
            data.update(updatedVoterInformation)
            return null

        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            alert("Voter Image updated successfully") {
                okButton {
                    val resultIntent = Intent()
                    resultIntent.putExtra("NEED_REFRESH", true)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }.show()

        }
    }


    private fun navigateToUpdateVoterDetails() {

        val intent = Intent(this, EditVoterDetailsActivity::class.java)
        intent.putExtra(ApiConstants.VOTER_DETAILS, voterDetailsResponse)
        intent.putExtra(ApiConstants.FILTER_TYPE,filterType)
        intent.putExtra(ApiConstants.VOTER_IMAGE, selectedImagePath)
        startActivityForResult(intent, ApiConstants.EDIT_VOTER_DETAILS)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.update_crop_image_view, null) // Inflate custom layout

        val builder = AlertDialog.Builder(this, R.style.full_screen_dialog_1) // Use a custom style
            .setView(view)
            .create()

        val imageView =
            view.findViewById<androidx.appcompat.widget.AppCompatImageView>(R.id.ivCapturedImage)
        val btnSave = view.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btnSave)
        val btnReCapture =
            view.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btnReCapture)

        selectedImagePath = data?.getStringExtra("Image_path")
        val image = File(selectedImagePath!!)
        imageView.setImageURI(Uri.fromFile(image))
        builder.setTitle("")

        builder.setCancelable(false) // Prevents dismissal by tapping outside or pressing back


        btnSave?.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Save Voter Image")
            builder.setMessage("Are you sure save voter Image?")
            builder.setCancelable(false)
            // Set the positive button ("Save")
                builder.setPositiveButton("Save") { dialog, which ->

                    super.onActivityResult(requestCode, resultCode, data)
                    if (requestCode == ApiConstants.EDIT_VOTER_DETAILS
                        && resultCode == Activity.RESULT_OK
                    ) {
                        if (data?.getBooleanExtra("NEED_REFRESH", false)!!) {
                            val resultIntent = Intent()
                            resultIntent.putExtra("NEED_REFRESH", true)
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
                        } else {
                            voterDetailsResponse =
                                data.getSerializableExtra(ApiConstants.VOTER_DETAILS) as VotersList
                            binding?.tvVoterName?.text =
                                CommonMethods.getVoterName(voterDetailsResponse)
                            setVoterData(voterDetailsResponse)
                        }

                    } else if (requestCode == ApiConstants.REQ_CAMERA) {
                        if (resultCode == Activity.RESULT_OK && data != null) {
                            selectedImagePath = data.getStringExtra("Image_path")
                            if (selectedImagePath != null) {
                                val image =
                                    File(selectedImagePath!!)
                                Glide.with(this).load(Uri.fromFile(image))
                                    .into(binding?.ivVoterImage!!)

                                if (voterDetailsResponse.status == "NEW" || voterDetailsResponse.status == "TRANSFERRED IN") {
                                    binding?.etStatus?.text = voterDetailsResponse.status
                                } else {
                                    binding?.etStatus?.text = getString(R.string.updated)
                                }

//                    binding?.etStatus?.text = getString(R.string.updated)
                                updateVoterStatus()
                            }
                        }

                    }
                }

            // Set the negative button ("Cancel")
            builder.setNegativeButton("Cancel") { dialog, which ->
                // Dismiss the dialog
                dialog.dismiss()
            }

            // Create and show the AlertDialog
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }


        btnReCapture.setOnClickListener {
            // Logic for the "Retry" action goes here
            captureImage()
        }

        // Create and show the dialog

        builder.show()
    }

    private fun requestCameraPermission() {

        val permissionsList: MutableList<String> = ArrayList()
        permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissionsList.add(Manifest.permission.CAMERA)

        Dexter.withActivity(this)
            .withPermissions(permissionsList)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        checkManageAllPermission()
                    }
                    if (report.isAnyPermissionPermanentlyDenied) {
                        CommonMethods.showSettingsDialog(this@VoterDetailsActivity)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener { error: DexterError? -> }
            .onSameThread()
            .check()

    }


    private fun checkManageAllPermission() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                captureImage()
            } else {
                val intent = Intent(
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                )
                storagePermissionResultLauncher.launch(intent)
            }

        } else {
            captureImage()
        }
    }

    private var selectedImagePath: String? = ""

    private fun captureImage() {
        val voterImageCaptureIntent = Intent(this, VoterUpdateFaceDetectionActivity::class.java)
        startActivityForResult(voterImageCaptureIntent, ApiConstants.REQ_CAMERA)
    }


}
