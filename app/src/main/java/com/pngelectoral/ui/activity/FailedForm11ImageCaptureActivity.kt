package com.pngelectoral.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.pngelectoral.Form11ImageHelper.ScopedMediaPicker
import com.pngelectoral.Form11ImageHelper.ScopedMediaPicker.Companion.RES_IMAGE
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.cropper.CropImage
import com.pngelectoral.cropper.CropImageView
import com.pngelectoral.databinding.ActivityForm11ImageCaptureBinding
import com.pngelectoral.ui.viewModel.Form11ImageCaptureViewModel
//import com.theartofdev.edmodo.cropper.CropImage
//import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File

class FailedForm11ImageCaptureActivity : BaseActivity() {

    private var binding: ActivityForm11ImageCaptureBinding? = null
    private var form11ImageCaptureViewModel: Form11ImageCaptureViewModel? = null
    private var localImagePath: String? = null

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


        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        scopedMediaPicker.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private var selectedImagePath: String? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK && result != null) {
                localImagePath = result.uri.path
                if (localImagePath != null) {
                    val resultIntent = Intent()
                    resultIntent.putExtra("FORM_11_IMAGE_PATH", localImagePath)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            } else {
                finish()
            }
        } else if (requestCode == RES_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                scopedMediaPicker.onActivityResult(requestCode, resultCode, data)
            } else {
                finish()
            }
        }
    }


    private fun beginCrop(source: Uri) {
        CropImage.activity(source).setGuidelines(CropImageView.Guidelines.ON)
            .setAllowFlipping(false)
            .setAllowRotation(false)
            .start(this)
    }

}
