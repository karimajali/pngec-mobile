package com.pngelectoral.ui.activity

import ApiConstants
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.AssetFileDescriptor
import android.hardware.Camera
import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityNewEnrolmentBinding
import com.pngelectoral.databinding.DialogManualForm11NumberBinding
import com.pngelectoral.ui.model.VotersList
import com.pngelectoral.ui.viewModel.NewEnrolmentViewModel
import com.pngelectoral.utility.CommonMethods
import com.pngelectoral.utility.visionBarcode.BarcodeTracker
import com.pngelectoral.utility.visionBarcode.BarcodeTrackerFactory
import com.pngelectoral.utility.visionBarcode.CameraSource
import com.pngelectoral.utility.visionBarcode.CameraSourcePreview
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import java.io.IOException

class FailedEnrolmentScanActivity : BaseActivity(),
    BarcodeTracker.BarcodeGraphicTrackerCallback {

    private val TAG = "Barcode-reader"
    private val RC_HANDLE_GMS = 9001
    private val RC_HANDLE_CAMERA_PERM = 2
    private var mCameraSource: CameraSource? = null
    private var mPreview: CameraSourcePreview? = null
    private var binding: ActivityNewEnrolmentBinding? = null
    private var newEnrolmentViewModel: NewEnrolmentViewModel? = null
    var isScannedItemProcessing = false
    lateinit var dataBaseInstance: AppDatabase

    var provinceId: Int = 0
    var wardId: Int = 0
    var wardName: String = ""
    var enrolmentType: String = ""

    private var formType = "PNG"


    /**
     * Initializes the UI and creates the detector pipeline.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_enrolment)

        newEnrolmentViewModel =
            ViewModelProvider(this).get(
                NewEnrolmentViewModel::class.java
            )
        binding?.newEnrolmentViewModel = newEnrolmentViewModel

        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase

        provinceId = intent.getIntExtra("ProvinceId", 0)
        wardId = intent.getIntExtra("WardId", 0)
        wardName = intent.getStringExtra("WardName")!!
        enrolmentType = intent.getStringExtra("EnrolmentType")!!
        if (enrolmentType == getString(R.string.new_status))
            binding?.tvTitle?.text = getString(R.string.new_enrolment)
        else
            binding?.tvTitle?.text = getString(R.string.transfer_in_enrolment)
        setOnclickListeners()

        mPreview = findViewById(R.id.preview)
        val autoFocus = true
        val useFlash = false

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        val rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(autoFocus, useFlash)
        } else {
            requestCameraPermission()
        }
    }


    override fun onDetectedQrCode(barcode: Barcode?) {
        if (barcode != null && !isScannedItemProcessing) {
            isScannedItemProcessing = true
            playBeep()
            showScanAlertMessage(barcode.displayValue)
        }
    }

    private fun showScanAlertMessage(voterQRCode: String?) {
        Thread {
            this@FailedEnrolmentScanActivity.runOnUiThread {
                alert("QR Code scanned successfully") {
                    okButton {
                        checkQRCodeGeoStructureLocation(voterQRCode)
                    }
                }.show().setCancelable(false)
            }
        }.start()
    }


    private fun checkQRCodeGeoStructureLocation(voterQRCode: String?) {
        Thread {
            this.runOnUiThread {

                if (voterQRCode!!.contains("#")) {
                    val voterForm11Code = voterQRCode.split("#")
                    if (voterForm11Code.size == 7) {
                        when (securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "")) {
                            ApiConstants.ROLE_DPO -> {
                                formType = "PNG"
                                val scannedWardId = voterForm11Code[4]
                                val scannedProvinceId = voterForm11Code[1]
                                if (wardId.toString() == scannedWardId
                                    && provinceId.toString() == scannedProvinceId
                                ) {
                                    CommonMethods.showProgress(this)
                                    CheckEnrolmentFormExist(voterQRCode).execute()
                                } else {
                                    showGeoLocationErrorMessageForForm11()
                                }
                            }

                            ApiConstants.ROLE_MKA -> {
                                formType = "MKA"
                                val scannedWardId = voterForm11Code[3]
//                                val scannedProvinceId = voterForm11Code[1]
                                if (wardId.toString() == scannedWardId) {
                                    CommonMethods.showProgress(this)
                                    CheckEnrolmentFormExist(voterQRCode.replace("MKA#","")).execute()
                                } else {
                                    showGeoLocationErrorMessageForForm11()
                                }
                            }

                            else -> {
                                formType = "PNG"
                                val scannedWardId = voterForm11Code[4]
                                val scannedProvinceId = voterForm11Code[1]
                                if (wardId.toString() == scannedWardId
                                    && provinceId.toString() == scannedProvinceId
                                ) {
                                    CommonMethods.showProgress(this)
                                    CheckEnrolmentFormExist(voterQRCode).execute()
                                } else {
                                    showGeoLocationErrorMessageForForm11()
                                }
                            }
                        }

                    } else {
                        showQRCodeInvalidMessage("This electoral Id is not valid.")
                    }
                } else {
                    showQRCodeInvalidMessage("This electoral Id is not valid.")
                }
            }
        }.start()


    }

    @SuppressLint("StaticFieldLeak")
    internal inner class CheckEnrolmentFormExist(private val enrolmentFormNumber: String) :
        AsyncTask<Void?, Void?, VotersList?>() {

        override fun doInBackground(vararg p0: Void?): VotersList? {
            return dataBaseInstance.votersListDao().checkEnrolmentFormNumber(enrolmentFormNumber)
        }

        override fun onPostExecute(result: VotersList?) {
            super.onPostExecute(result)
            if (result != null) {
                CommonMethods.hideProgress()
                showQRCodeInvalidMessage(getString(R.string.form_11_already_used))
            } else {
                CheckEnrolmentFormExistInTransferIN(enrolmentFormNumber).execute()
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    internal inner class CheckEnrolmentFormExistInTransferIN(private val enrolmentFormNumber: String) :
        AsyncTask<Void?, Void?, VotersList?>() {

        override fun doInBackground(vararg p0: Void?): VotersList? {
            return dataBaseInstance.transferInVotersListDao()
                .checkEnrolmentFormNumber(enrolmentFormNumber)
        }

        override fun onPostExecute(result: VotersList?) {
            super.onPostExecute(result)
            if (result != null) {
                CommonMethods.hideProgress()
                showQRCodeInvalidMessage(getString(R.string.form_11_already_used))
            } else {
                CommonMethods.hideProgress()
                when (securedSharedPreferenceWrapper.getString(ApiConstants.ROLE, "")) {
                    ApiConstants.ROLE_DPO -> {
                        securedSharedPreferenceWrapper.putString(
                            ApiConstants.ENROLMENT_FORM_NUMBER,
                            enrolmentFormNumber
                        )
                    }

                    ApiConstants.ROLE_MKA -> {
                        securedSharedPreferenceWrapper.putString(
                            ApiConstants.ENROLMENT_FORM_NUMBER,
                            enrolmentFormNumber.replace("MKA#","")
                        )
                    }

                    else -> {
                        securedSharedPreferenceWrapper.putString(
                            ApiConstants.ENROLMENT_FORM_NUMBER,
                            enrolmentFormNumber
                        )
                    }
                }
                securedSharedPreferenceWrapper.putString(
                    ApiConstants.ENROLMENT_FORM_TYPE,
                    formType
                )
                navigateToCaptureForm11ImageScreen()

            }
        }
    }


    private fun showQRCodeInvalidMessage(message: String) {
        Thread {
            this@FailedEnrolmentScanActivity.runOnUiThread {
                alert(message) {
                    okButton {
                        Handler(mainLooper).postDelayed({
                            isScannedItemProcessing = false
                        }, 2000)
                    }
                }.show()
            }
        }.start()
    }

    private fun showGeoLocationErrorMessageForForm11() {

        val message =
            "The Form 11 is not for this ward. Please use a correct Form 11 provided by PNGEC for Ward $wardName"

        Thread {
            runOnUiThread {
                alert(message) {
                    okButton {
                        Handler(mainLooper).postDelayed({
                            isScannedItemProcessing = false
                        }, 2000)

                    }
                }.show()
            }
        }.start()
    }


    private fun playBeep() {
        try {
            val mediaPlayer = MediaPlayer()
            val descriptor: AssetFileDescriptor = assets.openFd("beep.mp3")
            mediaPlayer.setDataSource(
                descriptor.fileDescriptor,
                descriptor.startOffset,
                descriptor.length
            )
            descriptor.close()
            mediaPlayer.prepare()
            mediaPlayer.setVolume(0.5f, 0.5f)
            mediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    // Handles the requesting of the camera permission.
    private fun requestCameraPermission() {
        Log.w(
            TAG,
            "Camera permission is not granted. Requesting permission"
        )
        val permissions =
            arrayOf(Manifest.permission.CAMERA)
        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            )
        ) {
            ActivityCompat.requestPermissions(
                this,
                permissions,
                RC_HANDLE_CAMERA_PERM
            )
        }
    }

    /**
     * Creates and starts the camera.
     *
     *
     * Suppressing InlinedApi since there is a check that the minimum version is met before using
     * the constant.
     */
    @SuppressLint("InlinedApi")
    private fun createCameraSource(
        autoFocus: Boolean,
        useFlash: Boolean
    ) {
        binding?.tvCameraEnable?.visibility = View.GONE
        val context = applicationContext
        val barcodeDetector = BarcodeDetector.Builder(context)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()
        val barcodeFactory =
            BarcodeTrackerFactory(this)
        barcodeDetector.setProcessor(
            MultiProcessor.Builder(
                barcodeFactory
            ).build()
        )
        if (!barcodeDetector.isOperational) {
            Log.w(
                TAG,
                "Detector dependencies are not yet available."
            )

            val lowStorageFilter = IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW)
            val hasLowStorage = registerReceiver(null, lowStorageFilter) != null
            if (hasLowStorage) {
                Toast.makeText(
                    this, "Low storage error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        var builder =
            CameraSource.Builder(
                applicationContext,
                barcodeDetector
            )
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(metrics.widthPixels, metrics.heightPixels)
                .setRequestedFps(24.0f)

        // make sure that auto focus is an available option
        builder = builder.setFocusMode(
            if (autoFocus) Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE else null
        )
        mCameraSource = builder
            .setFlashMode(if (useFlash) Camera.Parameters.FLASH_MODE_TORCH else null)
            .build()
    }

    // Restarts the camera
    override fun onResume() {
        super.onResume()
        startCameraSource()
    }

    // Stops the camera
    override fun onPause() {
        super.onPause()
        if (mPreview != null) {
            mPreview!!.stop()
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on [.requestPermissions].
     *
     *
     * **Note:** It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     *
     *
     * @param requestCode  The request code passed in [.requestPermissions].
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     * which is either [PackageManager.PERMISSION_GRANTED]
     * or [PackageManager.PERMISSION_DENIED]. Never null.
     * @see .requestPermissions
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(
                TAG,
                "Got unexpected permission result: $requestCode"
            )
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(
                TAG,
                "Camera permission granted - initialize the camera source"
            )
            // we have permission, so create the camerasource
            val autoFocus = true
            val useFlash = false
            createCameraSource(autoFocus, useFlash)
            return
        }
        Log.e(
            TAG,
            "Permission not granted: results len = " + grantResults.size +
                    " Result code = " + if (grantResults.isNotEmpty()) grantResults[0] else "(empty)"
        )
        val listener =
            DialogInterface.OnClickListener { dialog, id -> finish() }
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.app_name)
            .setMessage("Please enable camera permission")
            .setPositiveButton("Ok", listener)
            .show()
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    @Throws(SecurityException::class)
    private fun startCameraSource() {
        // check that the device has play services available.
        val code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
            applicationContext
        )
        if (code != ConnectionResult.SUCCESS) {
            val dlg = GoogleApiAvailability.getInstance().getErrorDialog(
                this,
                code,
                RC_HANDLE_GMS
            )
            dlg.show()
        }
        if (mCameraSource != null) {
            try {
                mPreview!!.start(mCameraSource)
            } catch (e: IOException) {
                Log.e(
                    TAG,
                    "Unable to start camera source.",
                    e
                )
                mCameraSource!!.release()
                mCameraSource = null
            }
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

        binding?.btnManualForm11?.setOnClickListener {
            showForm11NumberInputDialog()
        }
    }


    private var mManualForm11NumberAlertDialog: AlertDialog? = null
    private var bindingManualForm11Number: DialogManualForm11NumberBinding? = null

    private fun showForm11NumberInputDialog() {
        val builder = AlertDialog.Builder(this)
        bindingManualForm11Number = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_manual_form11_number, null, false
        ) as DialogManualForm11NumberBinding
        builder.setView(bindingManualForm11Number?.root)
        mManualForm11NumberAlertDialog = builder.create()

        bindingManualForm11Number?.btnSubmit?.setOnClickListener {
            val form11Number = bindingManualForm11Number?.etForm11Number?.text.toString().trim()
            validateManualForm11Number(form11Number)
        }

        bindingManualForm11Number?.btnCancel?.setOnClickListener {
            mManualForm11NumberAlertDialog!!.dismiss()
        }

        mManualForm11NumberAlertDialog?.show()
    }

    private fun validateManualForm11Number(form11Number: String) {
        if (form11Number.length < 12) {
            showQRCodeInvalidMessage("Please enter valid form number.")
        } else {
            if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO) {
                formType = "PNG"
                val endOfWardNumberPos = form11Number.length - 7
                val startOfWardNumberPos = endOfWardNumberPos - 4
                val wardNumber = form11Number.substring(startOfWardNumberPos, endOfWardNumberPos)
                val provinceNumber = form11Number.substring(0, endOfWardNumberPos - 4)
                if (wardId == wardNumber.toInt() && provinceId == provinceNumber.toInt()) {
                    mManualForm11NumberAlertDialog!!.dismiss()
                    CommonMethods.showProgress(this)
                    CheckEnrolmentFormExist("$form11Number@Manual").execute()
                } else {
                    showGeoLocationErrorMessageForForm11()
                }
            }else{
                if(form11Number.contains("-")){
                    val voterForm11Code = form11Number.split("-")
                    formType = "MKA"
                    val scannedWardId = voterForm11Code[3]
//                                val scannedProvinceId = voterForm11Code[1]
                    if (wardId.toString() == scannedWardId) {
                        CommonMethods.showProgress(this)
                        val formNumber = form11Number.replace("MKA-","")
                        CheckEnrolmentFormExist(formNumber.replace('-','#')).execute()
                    } else {
                        showGeoLocationErrorMessageForForm11()
                    }
                }else{
                    showQRCodeInvalidMessage("Please enter valid form number.")
                }
            }
        }
    }

    private fun navigateToCaptureForm11ImageScreen() {
        Thread {
            this@FailedEnrolmentScanActivity.runOnUiThread {
                alert("Please upload Form11 image of voter") {
                    okButton {
                        isScannedItemProcessing = false
                        val intent =
                            Intent(
                                this@FailedEnrolmentScanActivity,
                                FailedForm11ImageCaptureActivity::class.java
                            )
                        startActivityForResult(intent, ApiConstants.FORM11_IMAGE_CAPTURE)
                    }
                }.show().setCancelable(false)
            }
        }.start()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ApiConstants.FORM11_IMAGE_CAPTURE) {
            if (resultCode == Activity.RESULT_OK) {
                val localImagePath = data!!.getStringExtra("FORM_11_IMAGE_PATH")
                val resultIntent = Intent()
                resultIntent.putExtra("FORM_11_IMAGE_PATH", localImagePath)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

}