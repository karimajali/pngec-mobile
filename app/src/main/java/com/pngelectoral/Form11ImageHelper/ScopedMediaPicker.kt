package com.pngelectoral.Form11ImageHelper

import FilePaths
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.pngelectoral.R
import compressImageFile
import getApplicationName
import getScaledImagePath
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class ScopedMediaPicker(
    val activity: AppCompatActivity?,
    val fragment: Fragment? = null,
) {


    companion object {
        const val REQ_CAPTURE = 1101
        const val RES_IMAGE = 1102
        const val IMAGE_CROP_REQUEST_CODE = 1234
        const val MEDIA_TYPE_IMAGE = 1
    }

    private var imageUri: Uri? = null
    private var imgPath: String = ""
    var mediaType: Int = MEDIA_TYPE_IMAGE
    lateinit var onMediaChoose: (pathList: ArrayList<String>, type: Int) -> Unit

    val filePaths by lazy {
        FilePaths(fragment?.requireContext() ?: activity as Context)
    }

    private val permissions = arrayOf(Manifest.permission.CAMERA)

    fun startMediaPicker(
        mediaType: Int,
        onMediaChooseMultiple: (pathList: ArrayList<String>, type: Int) -> Unit
    ) {
        this.mediaType = mediaType
        this.onMediaChoose = onMediaChooseMultiple
        if (isPermissionsAllowed(permissions)) {
            chooseImage()
        }
    }


    private fun chooseImage() {
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri())
        activity?.startActivityForResult(
            takePhotoIntent,
            RES_IMAGE
        )
    }


    private fun getPickImageIntent(): Intent? {
        var chooserIntent: Intent? = null
        var intentList: MutableList<Intent> = ArrayList()
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri())
        intentList = addIntentsToList(intentList, pickIntent)
        intentList = addIntentsToList(intentList, takePhotoIntent)

        if (intentList.size > 0) {
            chooserIntent = Intent.createChooser(
                intentList.removeAt(intentList.size - 1),
                activity?.getString(R.string.select_capture_image)
            )
            chooserIntent?.putExtra(
                Intent.EXTRA_INITIAL_INTENTS,
                intentList.toTypedArray<Parcelable>()
            )
        }

        return chooserIntent
    }

    /**
     * Add Intents to Intent list
     */
    private fun addIntentsToList(
        list: MutableList<Intent>,
        intent: Intent
    ): MutableList<Intent> {
        val resInfo = activity?.packageManager?.queryIntentActivities(intent, 0)
        for (resolveInfo in resInfo!!) {
            val packageName = resolveInfo.activityInfo.packageName
            val targetedIntent = Intent(intent)
            targetedIntent.setPackage(packageName)
            list.add(targetedIntent)
        }
        return list
    }

    /**
     * Set default URI for capture image
     */
    private fun setImageUri(): Uri? {
        val folder = File("${activity?.getExternalFilesDir(Environment.DIRECTORY_DCIM)}")
        if (!folder.exists()) {
            folder.mkdirs()
        }

        val file =
            File(folder, "${activity?.getApplicationName()}_${System.currentTimeMillis()}.png")

        if (file.exists())
            file.delete()
        file.createNewFile()
        imageUri = FileProvider.getUriForFile(
            activity!!,
            activity.applicationContext.packageName + activity.getString(R.string.file_provider_name),
            file
        )
        imgPath = file.absolutePath
        return imageUri
    }


    /**
     * Check if scoped permission is allowed or not
     */
    private fun isPermissionsAllowed(
        permissions: Array<String>
    ): Boolean {
        var isGranted = true
        if (activity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (permission in permissions) {
                    isGranted = ContextCompat.checkSelfPermission(
                        activity,
                        permission
                    ) == PackageManager.PERMISSION_GRANTED
                    if (!isGranted)
                        break
                }
            }
            if (!isGranted) {
                requestRequiredPermissions(permissions)
            }
        }
        return isGranted
    }

    private fun requestRequiredPermissions(permissions: Array<String>) {
        val pendingPermissions: ArrayList<String> = ArrayList()
        permissions.forEachIndexed { index, permission ->
            if (ContextCompat.checkSelfPermission(
                    activity!!,
                    permission
                ) == PackageManager.PERMISSION_DENIED
            )
                pendingPermissions.add(permission)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val array = arrayOfNulls<String>(pendingPermissions.size)
            pendingPermissions.toArray(array)
            activity?.requestPermissions(array, REQ_CAPTURE)
        }


    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val activity = activity ?: fragment?.requireActivity() as Activity

        when (requestCode) {
            RES_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    handleImageRequest(data)
                }
            }
            IMAGE_CROP_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val imagePath = ""
                    onMediaChoose(arrayListOf(imagePath), MEDIA_TYPE_IMAGE)
                }
            }
        }
    }


    /*
     * Handle the data URI once image is back to user
     */
    private fun handleImageRequest(data: Intent?) {
        val exceptionHandler = CoroutineExceptionHandler { _, t ->
            t.printStackTrace()
        }
        var queryImageUrl: String
        var compressedPath: String

        GlobalScope.launch(Dispatchers.Main + exceptionHandler) {
            if (data?.data != null) {     //Photo from gallery
                val imageUri = data.data
                queryImageUrl = imageUri?.path ?: ""
                compressedPath =
                    activity?.compressImageFile(queryImageUrl, false, imageUri!!) ?: ""
            } else {
                compressedPath = getScaledImagePath(activity!!, 1024, 1024, imgPath)
            }


            onMediaChoose.invoke(arrayListOf(compressedPath), MEDIA_TYPE_IMAGE)
        }
    }


    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQ_CAPTURE -> {
                if (isAllPermissionsGranted(grantResults)) {
                    chooseImage()
                } else {
                    Toast.makeText(
                        activity,
                        "Permission not granted",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun isAllPermissionsGranted(grantResults: IntArray): Boolean {
        var isGranted = true
        for (grantResult in grantResults) {
            isGranted = grantResult == PackageManager.PERMISSION_GRANTED
            if (!isGranted)
                break
        }
        return isGranted
    }
}