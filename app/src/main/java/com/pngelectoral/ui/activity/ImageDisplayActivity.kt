package com.pngelectoral.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
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
import com.pngelectoral.databinding.ActivityImageDisplayBinding
import com.pngelectoral.ui.model.VotersList
import com.pngelectoral.ui.viewModel.ImageDisplayViewModel
import com.pngelectoral.utility.CommonMethods
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import java.io.File
import java.util.ArrayList

class ImageDisplayActivity : BaseActivity() {

    private var binding: ActivityImageDisplayBinding? = null
    private var imageDisplayViewModel: ImageDisplayViewModel? = null
    private var isFromForm11 = true
    private var voterImagePath = ""
    private var form11ImagePath = ""
    var isFromTransferIn: Boolean = false
    var isFromNewEnrolment: Boolean = false
    var voterDetailsResponse: VotersList = VotersList()
    lateinit var dataBaseInstance: AppDatabase
    private lateinit var storagePermissionResultLauncher: ActivityResultLauncher<Intent?>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_image_display
        )
        imageDisplayViewModel =
            ViewModelProvider(this).get(ImageDisplayViewModel::class.java)
        binding?.imageDisplayViewModel = imageDisplayViewModel

        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase
        setFooterData()

        isFromForm11 = intent.getBooleanExtra("From_Form11", true)
        isFromTransferIn = intent.getBooleanExtra("From_Transfer_In", false)

        if (isFromTransferIn)
            voterDetailsResponse =
                intent.getSerializableExtra(ApiConstants.VOTER_DETAILS) as VotersList
        else
            isFromNewEnrolment = intent.getBooleanExtra("From_New_Enrolment", false)

        if (isFromForm11) {
            binding?.btnSave?.text = "Save"
            binding?.tvTitle?.text = getString(R.string.form_11_photo)
            form11ImagePath = intent.getStringExtra("Form11_Image_Path")!!

            val image =
                File(form11ImagePath)

            Glide.with(this).load(Uri.fromFile(image))
                .into(binding?.ivCapturedImage!!)

        } else {
            binding?.btnSave?.text = "Next"
            binding?.tvTitle?.text = getString(R.string.voter_image)
            form11ImagePath = intent.getStringExtra("Form11_Image_Path")!!
            voterImagePath = intent.getStringExtra("Voter_Image_Path")!!

            val image =
                File(voterImagePath)

            Glide.with(this).load(Uri.fromFile(image))
                .into(binding?.ivCapturedImage!!)
        }



        binding?.btnSave?.setOnClickListener {

            if (isFromForm11) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Do you want to upload image of voter?")
                builder.setPositiveButton("Yes") { dialog, which ->
//                    navigateToVoterImageCaptureScreen()
                    requestCameraPermission()
                }

                builder.setNegativeButton("No") { dialog, which ->
                    navigateToDigitalFormScreen()
                }

                builder.show()
//                alert("Please upload image of voter") {
//                    okButton {
//                        navigateToVoterImageCaptureScreen()
//                    }
//                }.show()
            } else {

                alert("Please update all information of voter") {
                    okButton {
                        navigateToDigitalFormScreen()
                    }
                }.show()

            }
        }

        binding?.btnReCapture?.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding?.btnHome?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }

        binding?.btnBack?.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            storagePermissionResultLauncher = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (Environment.isExternalStorageManager()) {
                    navigateToVoterImageCaptureScreen()
                }
            }
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

    private fun navigateToDigitalFormScreen() {
        if (isFromTransferIn) {
            val transferInVoterDetailsIntent =
                Intent(this, TransferInVoterDetailsActivity::class.java)
            transferInVoterDetailsIntent.putExtra("Form11_Image_Path", form11ImagePath)
            transferInVoterDetailsIntent.putExtra("Voter_Image_Path", voterImagePath)
            transferInVoterDetailsIntent.putExtra(
                ApiConstants.VOTER_DETAILS,
                voterDetailsResponse
            )
            startActivity(transferInVoterDetailsIntent)
        } else {
            if (isFromNewEnrolment) {
                val newEnrolmentFormIntent = Intent(this, NewEnrolmentFormActivity::class.java)
                newEnrolmentFormIntent.putExtra("Form11_Image_Path", form11ImagePath)
                newEnrolmentFormIntent.putExtra("Voter_Image_Path", voterImagePath)
                startActivity(newEnrolmentFormIntent)
            } else {
                val digitalFormIntent = Intent(this, ProvisionalEnrolmentFormActivity::class.java)
                digitalFormIntent.putExtra("Form11_Image_Path", form11ImagePath)
                digitalFormIntent.putExtra("Voter_Image_Path", voterImagePath)
                startActivity(digitalFormIntent)
            }

        }

    }

    private fun navigateToVoterImageCaptureScreen() {

        val voterImageCaptureIntent = Intent(this, FaceDetectionActivity::class.java)
        voterImageCaptureIntent.putExtra("Form11_Image_Path", form11ImagePath)
        voterImageCaptureIntent.putExtra("From_Transfer_In", isFromTransferIn)
        voterImageCaptureIntent.putExtra("From_New_Enrolment", isFromNewEnrolment)
        if (isFromTransferIn)
            voterImageCaptureIntent.putExtra(
                ApiConstants.VOTER_DETAILS,
                voterDetailsResponse
            )
        startActivity(voterImageCaptureIntent)
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
                        CommonMethods.showSettingsDialog(this@ImageDisplayActivity)
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
                navigateToVoterImageCaptureScreen()
            } else {
                val intent = Intent(
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                )
                storagePermissionResultLauncher.launch(intent)
            }

        } else {
            navigateToVoterImageCaptureScreen()
        }
    }

    override fun onBackPressed() {
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }


}
