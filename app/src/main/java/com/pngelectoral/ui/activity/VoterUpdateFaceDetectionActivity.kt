package com.pngelectoral.ui.activity

import ApiConstants
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.face.FaceDetector
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.pngelectoral.R
import com.pngelectoral.cropper.CropImage
import com.pngelectoral.cropper.CropImageView
import com.pngelectoral.facedetectionhelper.*
import com.pngelectoral.utility.CommonMethods
//import com.theartofdev.edmodo.cropper.CropImage
//import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class VoterUpdateFaceDetectionActivity : AppCompatActivity(),
    ActivityCompat.OnRequestPermissionsResultCallback,
    CompoundButton.OnCheckedChangeListener {

    private var cameraSource: CameraSource? = null
    private var preview: CameraSourcePreview? = null
    private var graphicOverlay: GraphicOverlay? = null
    private lateinit var btnContinue: AppCompatButton

    private var localImagePath: String? = null
    private var faceBox: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_detection)
        preview = findViewById(R.id.preview_view)
        graphicOverlay = findViewById(R.id.graphic_overlay)
        btnContinue = findViewById(R.id.btnContinue)


        if (allPermissionsGranted()) {
            createCameraSource()
        } else {
            runtimePermissions
        }

        val facingSwitch = findViewById<ToggleButton>(R.id.facing_switch)
        facingSwitch.setOnCheckedChangeListener(this)

        btnContinue.setOnClickListener {
            CommonMethods.showProgress(this)
            cameraSource!!.takeImage()
        }

    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        Log.d(TAG, "Set facing")
        if (cameraSource != null) {
            if (isChecked) {
                cameraSource?.setFacing(CameraSource.CAMERA_FACING_FRONT)
            } else {
                cameraSource?.setFacing(CameraSource.CAMERA_FACING_BACK)
            }
        }
        preview?.stop()
        startCameraSource()
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in requiredPermissions) {
            if (!isPermissionGranted(this, permission)) {
                return false
            }
        }
        return true
    }

    private val requiredPermissions: Array<String?>
        get() =
            try {
                val info =
                    this.packageManager.getPackageInfo(
                        this.packageName,
                        PackageManager.GET_PERMISSIONS
                    )
                val ps = info.requestedPermissions
                if (ps != null && ps.isNotEmpty()) {
                    ps
                } else {
                    arrayOfNulls(0)
                }
            } catch (e: Exception) {
                arrayOfNulls(0)
            }


    private val runtimePermissions: Unit
        get() {
            val allNeededPermissions: MutableList<String?> = ArrayList()
            for (permission in requiredPermissions) {
                if (!isPermissionGranted(this, permission)) {
                    allNeededPermissions.add(permission)
                }
            }
            if (allNeededPermissions.isNotEmpty()) {
                ActivityCompat.requestPermissions(
                    this,
                    allNeededPermissions.toTypedArray(),
                    PERMISSION_REQUESTS
                )
            }
        }


    private fun startCameraSource() {
        if (cameraSource != null) {
            try {
                if (preview == null) {
                    Log.d(TAG, "resume: Preview is null")
                }
                if (graphicOverlay == null) {
                    Log.d(TAG, "resume: graphOverlay is null")
                }
                preview!!.start(cameraSource, graphicOverlay)
            } catch (e: IOException) {
                Log.e(TAG, "Unable to start camera source.", e)
                cameraSource!!.release()
                cameraSource = null
            }
        }
    }

    public override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        createCameraSource()
        startCameraSource()

        registerReceiver(
            faceDetectedReceiver,
            IntentFilter(ApiConstants.FACE_DETECTED_CALL_BACK)
        )

        registerReceiver(
            imageCapturedReceiver,
            IntentFilter(ApiConstants.IMAGE_CAPTURED)
        )
    }

    /** Stops the camera. */
    override fun onPause() {
        super.onPause()
        preview?.stop()
    }

    public override fun onDestroy() {
        super.onDestroy()
        if (cameraSource != null) {
            cameraSource?.release()
        }
        unregisterReceiver(faceDetectedReceiver)
        unregisterReceiver(imageCapturedReceiver)
    }


    private fun createCameraSource() {
        // If there's no existing cameraSource, create one.
        if (cameraSource == null) {
            cameraSource = CameraSource(this, graphicOverlay)
        }
        try {
            val faceDetectorOptions = PreferenceUtils.getFaceDetectorOptions(this)
            cameraSource!!.setMachineLearningFrameProcessor(
                FaceDetectorProcessor(this, faceDetectorOptions)
            )
        } catch (e: Exception) {
            Toast.makeText(
                applicationContext,
                "Can not create image processor: " + e.message,
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "Permission granted!")
        if (allPermissionsGranted()) {
            createCameraSource()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private val faceDetectedReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val faceDetected = intent.getBooleanExtra(ApiConstants.FACE_DETECTED, false)
            faceBox = intent.getStringExtra(ApiConstants.FACE_DETECTED_OBJECT)
            if (faceDetected)
                btnContinue.visibility = View.VISIBLE
            else
                btnContinue.visibility = View.GONE
        }
    }


    private val imageCapturedReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val selectedImagePath = intent.getStringExtra(ApiConstants.IMAGE_PATH)

            var bitmap = BitmapFactory.decodeFile(selectedImagePath)

            val exif: ExifInterface

            exif = ExifInterface(selectedImagePath!!)
            val orientation: Int = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION, 0
            )
            Log.d("EXIF", "Exif: $orientation")
            val matrix = Matrix()
            if (orientation == 6) {
                matrix.postRotate(90F)
                Log.d("EXIF", "Exif: $orientation")
            } else if (orientation == 3) {
                matrix.postRotate(180F)
                Log.d("EXIF", "Exif: $orientation")
            } else if (orientation == 8) {
                matrix.postRotate(270F)
                Log.d("EXIF", "Exif: $orientation")
            }
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)


            try {
                analysePhoto(bitmap, selectedImagePath!!)
            } catch (e: Exception) {
                Log.e("CROP FACE", "")
                beginCrop(Uri.fromFile(File(selectedImagePath!!)))
            }
        }
    }

    private fun analysePhoto(bitmap: Bitmap, imageUri: String) {
        val realTimeFDO = FaceDetectorOptions.Builder()
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .build()

        val detector = FaceDetection.getClient(realTimeFDO)

        val inputImage = InputImage.fromBitmap(bitmap, 0)

        detector.process(inputImage)
            .addOnSuccessListener { faces ->
                for (face in faces) {
                    val rect = face.boundingBox
                    rect.set(
                        rect.left,
                        rect.top,
                        rect.right,
                        rect.bottom
                    )
                }
                if (faces.count() > 0) {
                    try {
                        cropDectedFace(bitmap, faces, imageUri)
                    } catch (x: Exception) {
                        CommonMethods.hideProgress()
                        beginCrop(Uri.fromFile(File(imageUri!!)))
                    }
                } else {
                    CommonMethods.hideProgress()
                    beginCrop(Uri.fromFile(File(imageUri!!)))
                }
            }
            .addOnFailureListener {
                beginCrop(Uri.fromFile(File(imageUri!!)))
            }
    }

    private fun cropDectedFace(
        bitmap: Bitmap,
        faces: MutableList<com.google.mlkit.vision.face.Face>,
        imageUri: String
    ): String? {
        val rect = faces[0].boundingBox
        var x = Math.max(rect.left, 0)
        x = x - (rect.width() / 4)

        var y = Math.max(rect.top, 0)
        y = y - rect.height() / 4


        val width = rect.width() + (rect.width() / 2)
        val height = rect.height() + (rect.height() / 2)

        val cropBitmap = Bitmap.createBitmap(
            bitmap,
            if (x > 0) x else 0,
            if (y > 0) y else 0,
            if (x + width > bitmap.width) bitmap.width - x else width,
            if (y + height > bitmap.height) bitmap.height - y else height
        )

        var out: FileOutputStream? = null
        out = FileOutputStream(imageUri)

        cropBitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, out)

        beginCrop(Uri.fromFile(File(imageUri!!)))
        return imageUri

    }


    fun getFace(context: Context, imageUri: String): String? {
        try {

            var bitmap = BitmapFactory.decodeFile(imageUri)

            val exif: ExifInterface

            exif = ExifInterface(imageUri!!)
            val orientation: Int = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION, 0
            )
            Log.d("EXIF", "Exif: $orientation")
            val matrix = Matrix()
            if (orientation == 6) {
                matrix.postRotate(90F)
                Log.d("EXIF", "Exif: $orientation")
            } else if (orientation == 3) {
                matrix.postRotate(180F)
                Log.d("EXIF", "Exif: $orientation")
            } else if (orientation == 8) {
                matrix.postRotate(270F)
                Log.d("EXIF", "Exif: $orientation")
            }
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)


            val faceDetector =
                FaceDetector.Builder(context).setProminentFaceOnly(true).setTrackingEnabled(false)
                    .build()
            val frame = Frame.Builder().setBitmap(bitmap).build()
            val faces = faceDetector.detect(frame)
            var results: Bitmap? = null

            val thisFace = faces.valueAt(0)


            var x = thisFace.position.x - (thisFace.width / 6)
            var y = thisFace.position.y - (thisFace.height / 6)
            val x2 = x / 4 + thisFace.width
            val y2 = y / 4 + thisFace.height

            results = createBitmapCustom(bitmap, x.toInt(), y.toInt(), x2.toInt(), y2.toInt())

            var out: FileOutputStream? = null
            out = FileOutputStream(imageUri)

            results!!.compress(Bitmap.CompressFormat.JPEG, 100, out)

            return imageUri
        } catch (e: Exception) {
            Log.e("GET_FACE", "")
        }
        return null
    }

    fun createBitmapCustom(bitmap: Bitmap, x: Int, y: Int, x2: Int, y2: Int): Bitmap? {
        var results: Bitmap? = null
        var xlocal: Int = x
        var ylocal: Int = y
        var x2local: Int = x2
        var y2local: Int = y2

        try {
            results = Bitmap.createBitmap(bitmap, xlocal, ylocal, x2local, y2local)
        } catch (e: Exception) {
        }
        if (results == null) {
            Bitmap.createBitmap(bitmap, xlocal++, ylocal++, x2local--, y2local--)
        }

        return results;
    }

    private fun beginCrop(source: Uri) {
        CropImage.activity(source).setGuidelines(CropImageView.Guidelines.ON)
            .setAllowFlipping(false)
            .setAllowRotation(false)
            .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CommonMethods.hideProgress()
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK && result != null) {
                localImagePath = result.uri.path
                if (localImagePath != null) {
                    val resultIntent = Intent()
                    resultIntent.putExtra("Image_path", localImagePath)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }
        }


    }


    companion object {

        private const val TAG = "FaceDetectionActivity"
        private const val PERMISSION_REQUESTS = 1
        private fun isPermissionGranted(context: Context, permission: String?): Boolean {
            if (ContextCompat.checkSelfPermission(context, permission!!) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                Log.i(TAG, "Permission granted: $permission")
                return true
            }
            Log.i(TAG, "Permission NOT granted: $permission")
            return false
        }
    }
}