package com.pngelectoral.ui.activity

import ApiConstants
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.pngelectoral.BuildConfig
import com.pngelectoral.Form11ImageHelper.ScopedMediaPicker
import com.pngelectoral.Form11ImageHelper.ScopedMediaPicker.Companion.RES_IMAGE
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.cropper.CropImage
import com.pngelectoral.cropper.CropImageView
import com.pngelectoral.databinding.ActivityForm11ImageCaptureBinding
import com.pngelectoral.ui.model.VotersList
import com.pngelectoral.ui.viewModel.Form11ImageCaptureViewModel
import com.pngelectoral.utility.CommonMethods
//import com.theartofdev.edmodo.cropper.CropImage
//import com.theartofdev.edmodo.cropper.CropImageView
import java.io.*
import java.util.*

class Form11ImageCaptureActivity : BaseActivity() {

    private var binding: ActivityForm11ImageCaptureBinding? = null
    private var form11ImageCaptureViewModel: Form11ImageCaptureViewModel? = null
    var localImagePath: String? = null
    var isFromTransferIn: Boolean = false
    var isFromNewEnrolment: Boolean = false
    var voterDetailsResponse: VotersList = VotersList()

    private val scopedMediaPicker by lazy {
        ScopedMediaPicker(
            activity = this,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_form11_image_capture
        )
        form11ImageCaptureViewModel =
            ViewModelProvider(this).get(Form11ImageCaptureViewModel::class.java)
        binding?.form11ImageCaptureViewModel = form11ImageCaptureViewModel
        isFromTransferIn = intent.getBooleanExtra("From_Transfer_In", false)
        if (isFromTransferIn)
            voterDetailsResponse =
                intent.getSerializableExtra(ApiConstants.VOTER_DETAILS) as VotersList
        else
            isFromNewEnrolment = intent.getBooleanExtra("From_New_Enrolment", false)

//        requestCameraPermission()
        captureForm11Image()
    }

    private fun captureForm11Image() {

        scopedMediaPicker.startMediaPicker(
            mediaType = ScopedMediaPicker.MEDIA_TYPE_IMAGE
        ) { pathList, type ->
            when (type) {
                ScopedMediaPicker.MEDIA_TYPE_IMAGE -> {
                    selectedImagePath = pathList.first()
                    beginCrop(Uri.fromFile(File(selectedImagePath!!)))
                }
            }

            Log.e("FilePath", pathList.toString())
        }
    }

    private fun requestCameraPermission() {

        val permissionsList: MutableList<String> =
            ArrayList()
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
                        CommonMethods.showSettingsDialog(this@Form11ImageCaptureActivity)
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

                val storagePermissionResultLauncher = registerForActivityResult(
                    ActivityResultContracts.StartActivityForResult()
                ) {
                    if (Environment.isExternalStorageManager()) {
                        captureImage()
                    }
                }
                storagePermissionResultLauncher.launch(intent)
            }

        } else {
            captureImage()
        }
    }

    private fun captureImage() {

        val cameraIntent = Intent(
            MediaStore.ACTION_IMAGE_CAPTURE
        )
        startActivityForResult(cameraIntent, ApiConstants.REQ_CAMERA)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        scopedMediaPicker.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        scopedMediaPicker.onActivityResult(requestCode, resultCode, data)
//    }


    private var selectedImagePath: String? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK && result != null) {
                localImagePath = result.uri.path
                if (localImagePath != null) {
                    val imageDisplayIntent = Intent(this, ImageDisplayActivity::class.java)
                    imageDisplayIntent.putExtra("Form11_Image_Path", localImagePath)
                    imageDisplayIntent.putExtra("From_Form11", true)
                    imageDisplayIntent.putExtra("From_Transfer_In", isFromTransferIn)
                    imageDisplayIntent.putExtra("From_New_Enrolment", isFromNewEnrolment)
                    if (isFromTransferIn)
                        imageDisplayIntent.putExtra(
                            ApiConstants.VOTER_DETAILS,
                            voterDetailsResponse
                        )
                    startActivityForResult(imageDisplayIntent, ApiConstants.FORM11_IMAGE_CAPTURE)
                }
            } else {
                finish()
            }
        } else if(requestCode == RES_IMAGE){
            if (resultCode == Activity.RESULT_OK) {
                scopedMediaPicker.onActivityResult(requestCode, resultCode, data)
            }
            else{
                finish()
            }
        }else if (requestCode == ApiConstants.REQ_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {

//                val thumbnail = data!!.extras!!["data"] as Bitmap?
//                val destination = File(
//                    Environment.getExternalStorageDirectory(),
//                    System.currentTimeMillis().toString() + ".jpg"
//                )
//
//                saveImage(thumbnail,destination.absolutePath)

                try {
                    val thumbnail = data!!.extras!!["data"] as Bitmap?
                    val bytes = ByteArrayOutputStream()
                    thumbnail!!.compress(Bitmap.CompressFormat.PNG, 100, bytes)
                    val destination = File(
                        Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis().toString() + ".jpg"
                    )
                    val fo: FileOutputStream
                    try {
                        destination.createNewFile()
                        fo = FileOutputStream(destination)
                        fo.write(bytes.toByteArray())
                        fo.close()
                        selectedImagePath = destination.absolutePath
                        beginCrop(Uri.fromFile(File(selectedImagePath!!)))
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                finish()
            }

        } else if (requestCode == ApiConstants.FORM11_IMAGE_CAPTURE
            && resultCode == Activity.RESULT_OK
        ) {
//            finish()
//            requestCameraPermission()
            captureForm11Image()
        }
    }

//    private val BMP_WIDTH_OF_TIMES = 4
//    private val BYTE_PER_PIXEL = 3
//
//    private fun saveImage(orgBitmap: Bitmap?, filePath: String) {
//
//        //image size
//        val width = orgBitmap?.width
//        val height = orgBitmap?.height
//
//        //image dummy data size
//        //reason : bmp file's width equals 4's multiple
//        var dummySize = 0
//        var dummyBytesPerRow: ByteArray? = null
//        var hasDummy = false
//        if (isBmpWidth4Times(width!!)) {
//            hasDummy = true
//            dummySize = BMP_WIDTH_OF_TIMES - width % BMP_WIDTH_OF_TIMES
//            dummyBytesPerRow = ByteArray(dummySize * BYTE_PER_PIXEL)
//            for (i in dummyBytesPerRow.indices) {
//                dummyBytesPerRow[i] = 0xFF.toByte()
//            }
//        }
//
//        val pixels = IntArray(width * height!!)
//        val imageSize = pixels.size * BYTE_PER_PIXEL + height * dummySize * BYTE_PER_PIXEL
//        val imageDataOffset = 0x36
//        val fileSize = imageSize + imageDataOffset
//
//        //Android Bitmap Image Data
//        orgBitmap.getPixels(pixels, 0, width, 0, 0, width, height)
//
//        val buffer: ByteBuffer = ByteBuffer.allocate(fileSize)
//
//        try {
//            /**
//             * BITMAP FILE HEADER Write Start
//             */
//            buffer.put(0x42.toByte())
//            buffer.put(0x4D.toByte())
//
//            //size
//            buffer.put(writeInt(fileSize))
//
//            //reserved
//            buffer.put(writeShort(0.toShort()))
//            buffer.put(writeShort(0.toShort()))
//
//            //image data start offset
//            buffer.put(writeInt(imageDataOffset))
//            /** BITMAP FILE HEADER Write End  */
//
//            //*******************************************
//            /** BITMAP INFO HEADER Write Start  */
//            //size
//            buffer.put(writeInt(0x28))
//
//            //width, height
//            buffer.put(writeInt(width))
//            buffer.put(writeInt(height))
//
//            //planes
//            buffer.put(writeShort(1.toShort()))
//
//            //bit count
//            buffer.put(writeShort(24.toShort()))
//
//            //bit compression
//            buffer.put(writeInt(0))
//
//            //image data size
//            buffer.put(writeInt(imageSize))
//
//            //horizontal resolution in pixels per meter
//            buffer.put(writeInt(0))
//
//            //vertical resolution in pixels per meter (unreliable)
//            buffer.put(writeInt(0))
//
//            //컬러 사용 유무
//            buffer.put(writeInt(0))
//
//            //중요하게 사용하는 색
//            buffer.put(writeInt(0))
//            /** BITMAP INFO HEADER Write End  */
//            var row = height
//            var startPosition = 0
//            var endPosition = 0
//            while (row > 0) {
//                startPosition = (row - 1) * width
//                endPosition = row * width
//                for (i in startPosition until endPosition) {
//                    buffer.put(write24BitForPixcel(pixels[i]))
//                    if (hasDummy) {
//                        if (isBitmapWidthLastPixcel(width, i)) {
//                            buffer.put(dummyBytesPerRow)
//                        }
//                    }
//                }
//                row--
//            }
//            val fos = FileOutputStream(filePath)
//            fos.write(buffer.array())
//            fos.close()
//        } catch (e1: IOException) {
//            e1.printStackTrace()
//        } finally {
//        }
//
//    }
//
//    /**
//     * Is last pixel in Android Bitmap width
//     * @param width
//     * @param i
//     * @return
//     */
//    private fun isBitmapWidthLastPixcel(width: Int, i: Int): Boolean {
//        return i > 0 && i % (width - 1) == 0
//    }
//
//    /**
//     * BMP file is a multiples of 4?
//     * @param width
//     * @return
//     */
//    private fun isBmpWidth4Times(width: Int): Boolean {
//        return width % BMP_WIDTH_OF_TIMES > 0
//    }
//
//    /**
//     * Write integer to little-endian
//     * @param value
//     * @return
//     * @throws IOException
//     */
//    @Throws(IOException::class)
//    private fun writeInt(value: Int): ByteArray? {
//        val b = ByteArray(4)
//        b[0] = (value and 0x000000FF).toByte()
//        b[1] = (value and 0x0000FF00 shr 8).toByte()
//        b[2] = (value and 0x00FF0000 shr 16).toByte()
//        b[3] = (value and -0x1000000 shr 24).toByte()
//        return b
//    }
//
//    /**
//     * Write integer pixel to little-endian byte array
//     * @param value
//     * @return
//     * @throws IOException
//     */
//    @Throws(IOException::class)
//    private fun write24BitForPixcel(value: Int): ByteArray? {
//        val b = ByteArray(3)
//        b[0] = (value and 0x000000FF).toByte()
//        b[1] = (value and 0x0000FF00 shr 8).toByte()
//        b[2] = (value and 0x00FF0000 shr 16).toByte()
//        return b
//    }
//
//    /**
//     * Write short to little-endian byte array
//     * @param value
//     * @return
//     * @throws IOException
//     */
//    @Throws(IOException::class)
//    private fun writeShort(value: Short): ByteArray? {
//        val b = ByteArray(2)
//        b[0] = (value and 0x00FF) as Byte
//        b[1] = (value and 0xFF00 shr 8) as Byte
//        return b
//    }

    private fun beginCrop(source: Uri) {
        CropImage.activity(source).setGuidelines(CropImageView.Guidelines.ON)
            .setAllowFlipping(false)
            .setAllowRotation(false)
            .start(this)
    }

}
