package com.pngelectoral.utility

import ApiConstants
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.storage.StorageVolume
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.pngelectoral.R
import com.pngelectoral.ui.activity.SettingsActivity
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.retrofit.WSClient
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


object CommonMethods {

    private var mProgressDialog: ProgressDialog? = null
    private var mDownloadProgressDialog: ProgressDialog? = null

    fun hideKeypad(mContext: Context, view: View) {
        val inputMethodManager =
            mContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    fun checkInternetConnection(mActivity: Context): Boolean {

//        val manager =
//            mActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val mNetworkInfo = manager.activeNetworkInfo
//        return mNetworkInfo?.isConnectedOrConnecting ?: false

        return isInternetAvailable(mActivity)

    }

    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result


//        val connectivityManager = context.getSystemService(android.content.Context.CONNECTIVITY_SERVICE)
//                as ConnectivityManager
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val networkCapabilities = connectivityManager.activeNetwork ?: return false
//            val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
//
//            return when {
//
//                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
//                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
//                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//                else -> false
//            }
//        }
//        else {
//            return connectivityManager.activeNetworkInfo != null &&
//                    connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting
//        }
    }

    @SuppressLint("ResourceAsColor")
    fun showProgress(mContext: Context) {
        if (mProgressDialog == null) {

            mProgressDialog = ProgressDialog(mContext, R.style.AppTheme)
            if (mProgressDialog?.window != null) mProgressDialog?.window!!
                .setBackgroundDrawable(
                    ColorDrawable(R.color.color_bg_progres_bar)
                )
            mProgressDialog?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            mProgressDialog?.isIndeterminate = true
            mProgressDialog?.setCancelable(false)
            mProgressDialog?.setCanceledOnTouchOutside(false)
            mProgressDialog?.show()
            mProgressDialog?.setContentView(R.layout.layout_loader_progress_bar)

            mProgressDialog?.setOnDismissListener {
            }
        }

        if (!mProgressDialog!!.isShowing) {
            mProgressDialog?.show()
        }

    }

    fun hideProgress() {

        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog?.dismiss()
            WSClient.requestQue?.clear()
        }
        mProgressDialog = null
    }

    @SuppressLint("ResourceAsColor")
    fun showDownloadProgress(mContext: Context) {
        if (mDownloadProgressDialog == null) {

            mDownloadProgressDialog = ProgressDialog(mContext, R.style.AppTheme)
            if (mDownloadProgressDialog?.window != null) mDownloadProgressDialog?.window!!
                .setBackgroundDrawable(
                    ColorDrawable(R.color.color_bg_progres_bar)
                )
            mDownloadProgressDialog?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            mDownloadProgressDialog?.isIndeterminate = true
            mDownloadProgressDialog?.setCancelable(false)
            mDownloadProgressDialog?.setCanceledOnTouchOutside(false)
            mDownloadProgressDialog?.show()

//            val inflater =
//                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//            val layout: View = inflater.inflate(
//                R.layout.layout_loader_download_progress_bar,
//                findViewById(R.id.rootRL) as ViewGroup?
//            )

            mDownloadProgressDialog?.setContentView(R.layout.layout_loader_download_progress_bar)



            mDownloadProgressDialog?.setOnDismissListener {
            }
        }


        if (!mDownloadProgressDialog!!.isShowing) {
            mDownloadProgressDialog?.show()
        }

    }

    fun updateDownloadProgress(percentage: Int) {


//        val downloadProgressBar = mDownloadProgressDialog?.findViewById(android.R.id.progress)

    }

    fun hideDownloadProgress() {

        if (mDownloadProgressDialog != null && mDownloadProgressDialog!!.isShowing) {
            mDownloadProgressDialog?.dismiss()
            WSClient.requestQue?.clear()
        }
        mDownloadProgressDialog = null
    }


    fun showSettingsDialog(mContext: Context?) {
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton(
            "GOTO SETTINGS"
        ) { dialog: DialogInterface, which: Int ->
            dialog.cancel()
            openSettings(mContext!!)
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog: DialogInterface, which: Int -> dialog.cancel() }
        builder.show()
    }

    fun openSettings(mContext: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri =
            Uri.fromParts("package", mContext.packageName, null)
        intent.data = uri
        (mContext as Activity).startActivityForResult(intent, 101)
    }

    fun getVoterName(model: VotersList?): String? {
//        return if (model?.voterName == null || model.voterName.isEmpty()) {
//            getVoterDetails(model?.lName) + " " + getVoterDetails(model?.fName)
//        } else {
//            getVoterDetails(model.voterName)
//        }
//        return getVoterDetails(model?.lName) + " " + getVoterDetails(model?.fName)

        var lastNameOfVoter = ""
        var firstNameOfVoter = ""

        if (!model?.lName.isNullOrEmpty())
            lastNameOfVoter = model?.lName!!

        if (!model?.fName.isNullOrEmpty())
            firstNameOfVoter = model?.fName!!

        val voterName = "$lastNameOfVoter $firstNameOfVoter"
        return voterName.trim()
    }

    fun getVoterName(model: NewVotersList?): String? {
//        return if (model?.voterName == null || model.voterName.isEmpty()) {
//            getVoterDetails(model?.lName) + " " + getVoterDetails(model?.fName)
//        } else {
//            getVoterDetails(model.voterName)
//        }
//        return getVoterDetails(model?.lName) + " " + getVoterDetails(model?.fName)

        var lastNameOfVoter = ""
        var firstNameOfVoter = ""

        if (!model?.lName.isNullOrEmpty())
            lastNameOfVoter = model?.lName!!

        if (!model?.fName.isNullOrEmpty())
            firstNameOfVoter = model?.fName!!

        val voterName = "$lastNameOfVoter $firstNameOfVoter"
        return voterName.trim()
    }

    fun getVoterNameForTransferIn(model: VotersList?): String? {
//        return getVoterDetails(model?.lName) + " " + getVoterDetails(model?.fName)
        var lastNameOfVoter = ""
        var firstNameOfVoter = ""

        if (!model?.lName.isNullOrEmpty())
            lastNameOfVoter = model?.lName!!

        if (!model?.fName.isNullOrEmpty())
            firstNameOfVoter = model?.fName!!

        val voterName = "$lastNameOfVoter $firstNameOfVoter"
        return voterName.trim()
    }

    fun getVoterName(model: DownloadedVotersList?): String? {
//        return if (model?.VoterName == null || model.VoterName.isEmpty()) {
//            getVoterDetails(model?.LName) + " " + getVoterDetails(model?.FName)
//        } else {
//            getVoterDetails(model.VoterName)
//        }
//        return getVoterDetails(model?.LName) + " " + getVoterDetails(model?.FName)
        var lastNameOfVoter = ""
        var firstNameOfVoter = ""

        if (!model?.LName.isNullOrEmpty())
            lastNameOfVoter = model?.LName!!

        if (!model?.FName.isNullOrEmpty())
            firstNameOfVoter = model?.FName!!

        val voterName = "$lastNameOfVoter $firstNameOfVoter"
        return voterName.trim()
    }

    fun getVoterDetails(model: String?): String {
        return if (!model.isNullOrEmpty()) {
            model
        } else {
            ""
        }
    }

    fun getForm11NumberManualAndScanForReport(form11Number: String?, filterType: String): String {
        return if (form11Number!!.contains("@Manual")) {
            form11Number.removeSuffix("@Manual")
            form11Number.split("@")[0]
        } else {
            val voterForm11Code = form11Number.split("#")
            var scannedForm11Number = ""
            if (filterType == ApiConstants.ROLE_DPO) {
                if (voterForm11Code.size == 7) {
                    val wardId = if (voterForm11Code[4].length == 3)
                        "0" + voterForm11Code[4]
                    else
                        voterForm11Code[4]
                    scannedForm11Number =
                        voterForm11Code[1] + wardId + voterForm11Code[6]
                    scannedForm11Number
                } else {
                    ""
                }
            } else {
                scannedForm11Number = form11Number.replace("#", "")
                scannedForm11Number
            }
        }

    }

    fun getForm11NumberForStagingReport(form11Number: String?, filterType: String): String {
        if (form11Number!!.contains("#")) {
            val voterForm11Code = form11Number.split("#")
            if (filterType == ApiConstants.ROLE_DPO) {
                return if (voterForm11Code.size == 7) {
                    val wardId = if (voterForm11Code[4].length == 3)
                        "0" + voterForm11Code[4]
                    else
                        voterForm11Code[4]
                    val scannedForm11Number =
                        voterForm11Code[1] + wardId + voterForm11Code[6]
                    scannedForm11Number
                } else {
                    ""
                }
            } else {
                return form11Number.replace("#", "")
            }

        } else {
            return form11Number
        }


    }

    fun getForm11Number(form11Number: String?): String {
        var resultForm11Number = ""
        resultForm11Number = if (form11Number!!.contains("@Manual")) {
            form11Number.removeSuffix("@Manual")
            form11Number.split("@")[0]
        } else {
            form11Number
        }
        return resultForm11Number
    }

    fun isManualForm11Number(form11Number: String?): Boolean {
        return form11Number!!.contains("@Manual")
    }

    fun getVoterDetailsBoolean(model: Boolean?): Boolean {
        return model != null && model == true
    }

    fun getVoterDetails(model: Int?): String? {
        return model?.toString() ?: ""
    }

    fun getIntegerVoterDetails(model: Int?): Int? {
        return model ?: 0
    }

    fun getLongVoterDetails(model: Long?): Long? {
        return model ?: 0
    }

    fun getIntegerVoterDetailsFromString(model: String?): Int? {
        return if (model.isNullOrEmpty())
            0
        else
            model.toInt()
    }

    fun getDisplayDate(postedDate: String?): String? {

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

    fun getDisplayDateAndTime(postedDate: String?): String? {

        return try {
            val mSimpleDateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
            val serverDate = mSimpleDateFormat.parse(postedDate!!)
            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.US)
            sdf.format(serverDate!!)
        } catch (e: Exception) {
            e.printStackTrace()
            postedDate
        }

    }

    fun getVoterDetailsPostedDateTime(): String? {
        val mSimpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
        return mSimpleDateFormat.format(Date())

    }

    fun getVoterDetailsUpdatedDate(): String? {
//        val mSimpleDateFormat =
//            SimpleDateFormat("dd-MM-yyyy", Locale.US)
//        return mSimpleDateFormat.format(Date())
        val mSimpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return mSimpleDateFormat.format(Date())

    }

    fun getDOBAPIString(dateOfBirth: String): String? {
        return if (dateOfBirth.isNotEmpty()) {
            try {
                val mSimpleDateFormat =
                    SimpleDateFormat("dd-MM-yyyy", Locale.US)
                val serverDate = mSimpleDateFormat.parse(dateOfBirth)
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
                sdf.format(serverDate!!)
            } catch (e: Exception) {
                e.printStackTrace()
                dateOfBirth
            }
        } else {
            dateOfBirth
        }

    }

    fun getExternalSdCardPath(activity: Activity): String {

        val storageManager = activity.getSystemService(Context.STORAGE_SERVICE)
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

    fun getRegionList(regions: ArrayList<RegionList>): Array<String> {
        val regionList: ArrayList<String> = ArrayList()
        for (region in regions) {
            regionList.add(region.regionName)
        }
        return regionList.toArray(arrayOfNulls<String>(regionList.size))
    }

    fun getProvinceList(provinces: ArrayList<ProvinceList>): Array<String> {
        val provinceList: ArrayList<String> = ArrayList()
        for (province in provinces) {
            provinceList.add(province.provinceName)
        }
        return provinceList.toArray(arrayOfNulls<String>(provinceList.size))
    }

    fun getPDPOList(dpos: ArrayList<DpoList>): Array<String> {
        val dpoList: ArrayList<String> = ArrayList()
        for (dpo in dpos) {
            dpoList.add(getVoterName(dpo))
        }
        return dpoList.toArray(arrayOfNulls<String>(dpoList.size))
    }

    fun getVoterName(model: DpoList?): String {
        var lastNameOfVoter = ""
        var firstNameOfVoter = ""

        if (!model?.lastName.isNullOrEmpty())
            lastNameOfVoter = model?.lastName!!

        if (!model?.firstName.isNullOrEmpty())
            firstNameOfVoter = model?.firstName!!

        val voterName = "$lastNameOfVoter $firstNameOfVoter"
        return voterName.trim()
    }

    fun getDistrictList(districts: ArrayList<DistrictList>): Array<String> {
        val districtList: ArrayList<String> = ArrayList()
        for (district in districts) {
            districtList.add(district.districtName)
        }
        return districtList.toArray(arrayOfNulls<String>(districtList.size))
    }

    fun getLLGList(llgs: ArrayList<LLGList>): Array<String> {
        val llgList: ArrayList<String> = ArrayList()
        for (llg in llgs) {
            llgList.add(llg.llgName)
        }
        return llgList.toArray(arrayOfNulls<String>(llgList.size))
    }

    fun getWardList(wards: ArrayList<WardList>): Array<String> {
        val wardList: ArrayList<String> = ArrayList()
        for (ward in wards) {
            wardList.add(ward.wardNumber.toString() + " & " + ward.wardName)
        }
        return wardList.toArray(arrayOfNulls<String>(wardList.size))
    }

    fun getLocationList(locations: ArrayList<LocationList>): Array<String> {
        val locationList: ArrayList<String> = ArrayList()
        for (location in locations) {
            locationList.add(location.locationName)
        }
        return locationList.toArray(arrayOfNulls<String>(locationList.size))
    }

    fun getGenderLessVotersReportPostedDateTime(): String? {
        val mSimpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        return mSimpleDateFormat.format(Date())

    }

    fun decryptDcPassWord(text: String, key: String): String {
        val cipher: Cipher =
            Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keyBytes = ByteArray(16)
        val b = key.toByteArray(charset("UTF-8"))
        var len = b.size
        if (len > keyBytes.size) len = keyBytes.size
        System.arraycopy(b, 0, keyBytes, 0, len)
        val keySpec = SecretKeySpec(keyBytes, "AES")
        val ivSpec = IvParameterSpec(keyBytes)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        val results = cipher.doFinal(android.util.Base64.decode(text, android.util.Base64.DEFAULT))
        return String(results)
    }

    fun getLatitudeOrLongitude(latitudeOrLongitude: String): Double {
        return if (latitudeOrLongitude == null || latitudeOrLongitude.isEmpty())
            0.0
        else
            latitudeOrLongitude.toDouble()
    }

    fun getStatusFilterList(): MutableList<VoterListFilterModel> {
        val statusList = mutableListOf<VoterListFilterModel>()

        val status1 = VoterListFilterModel()
        status1.id = "1"
        status1.name = "NEW"

        val status2 = VoterListFilterModel()
        status2.id = "2"
        status2.name = "OLD"

        val status3 = VoterListFilterModel()
        status3.id = "3"
        status3.name = "DECEASED"

        val status4 = VoterListFilterModel()
        status4.id = "4"
        status4.name = "TRANSFERRED OUT"

        val status9 = VoterListFilterModel()
        status9.id = "9"
        status9.name = "TRANSFERRED IN"

        val status5 = VoterListFilterModel()
        status5.id = "5"
        status5.name = "GHOST"

        val status6 = VoterListFilterModel()
        status6.id = "6"
        status6.name = "DUPLICATE"

        val status7 = VoterListFilterModel()
        status7.id = "7"
        status7.name = "UNDER AGE"

        val status8 = VoterListFilterModel()
        status8.id = "8"
        status8.name = "UPDATED"

        val status11 = VoterListFilterModel()
        status11.id = "11"
        status11.name = "With Photo"

        val status10 = VoterListFilterModel()
        status10.id = "10"
        status10.name = "Without Photo"

        statusList.add(status1)
        statusList.add(status2)
        statusList.add(status3)
        statusList.add(status4)
        statusList.add(status9)
        statusList.add(status5)
        statusList.add(status6)
        statusList.add(status7)
        statusList.add(status8)
        statusList.add(status10)
        statusList.add(status11)

        return statusList

    }

    fun getStatusFilterListForSync(): MutableList<VoterListFilterModel> {
        val statusList = mutableListOf<VoterListFilterModel>()

        val status1 = VoterListFilterModel()
        status1.id = "1"
        status1.name = "NEW"

        val status3 = VoterListFilterModel()
        status3.id = "3"
        status3.name = "DECEASED"

        val status4 = VoterListFilterModel()
        status4.id = "4"
        status4.name = "TRANSFERRED OUT"

        val status9 = VoterListFilterModel()
        status9.id = "9"
        status9.name = "TRANSFERRED IN"

        val status5 = VoterListFilterModel()
        status5.id = "5"
        status5.name = "GHOST"

        val status6 = VoterListFilterModel()
        status6.id = "6"
        status6.name = "DUPLICATE"

        val status7 = VoterListFilterModel()
        status7.id = "7"
        status7.name = "UNDER AGE"

        val status8 = VoterListFilterModel()
        status8.id = "8"
        status8.name = "UPDATED"

        statusList.add(status1)
        statusList.add(status3)
        statusList.add(status4)
        statusList.add(status9)
        statusList.add(status5)
        statusList.add(status6)
        statusList.add(status7)
        statusList.add(status8)

        return statusList

    }

    fun isProvisionalVoter(wardVoter: VotersList, role: String): Boolean {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        val mSimpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd", Locale.US)
        var isProvisional = false
        if (wardVoter.dob.isNotEmpty()) {
            val serverDate = mSimpleDateFormat.parse(wardVoter.dob!!)
            val minDate =
                if (role == ApiConstants.ROLE_DPO) sdf.parse(ApiConstants.PROVISIONAL_CUT_OFF_DATE_VALUE) else sdf.parse(
                    ApiConstants.PROVISIONAL_CUT_OFF_DATE_VALUE_MKA
                )
            val maxDate =
                if (role == ApiConstants.ROLE_DPO)
                    sdf.parse(ApiConstants.LAST_DATE_OF_ENROLMENT_DEFAULT)
                else
                    sdf.parse(ApiConstants.LAST_DATE_OF_ENROLMENT_DEFAULT_MKA)
            if (serverDate != null) {
                isProvisional = serverDate.after(minDate) && serverDate.before(maxDate)
            }
        }
        return isProvisional
    }

    fun getVoterImageFileName(voter: VotersList, mContext: SettingsActivity): String {

        return if (voter.status == mContext.getString(R.string.new_status)
            || voter.status == mContext.getString(R.string.transferred_in)
        ) {
            voter.regionId.toString() + "#@#" + voter.provinceId.toString() + "#@#" +
                    voter.districtId.toString() + "#@#" + voter.llgId.toString() + "#@#" +
                    voter.locationId.toString() + "#@#" + voter.wardId.toString() + "#@#voter_image#@#" + voter.tempElectorId.toString() + "#@#" + voter.enrolmentFormNumber + ".jpg"
        } else {
            voter.regionId.toString() + "#@#" + voter.provinceId.toString() + "#@#" +
                    voter.districtId.toString() + "#@#" + voter.llgId.toString() + "#@#" +
                    voter.locationId.toString() + "#@#" + voter.wardId.toString() + "#@#voter_image#@#" + voter.tempElectorId.toString() + "#@#" + voter.electorId + ".jpg"
        }

    }

    fun getForm11ImageFileName(voter: VotersList, mContext: SettingsActivity): String {

        return if (voter.status == mContext.getString(R.string.new_status)
            || voter.status == mContext.getString(R.string.transferred_in)
        ) {
            voter.regionId.toString() + "#@#" + voter.provinceId.toString() + "#@#" +
                    voter.districtId.toString() + "#@#" + voter.llgId.toString() + "#@#" +
                    voter.locationId.toString() + "#@#" + voter.wardId.toString() + "#@#form11_image#@#" + voter.tempElectorId.toString() + "#@#" + voter.enrolmentFormNumber + ".jpg"
        } else {
            voter.regionId.toString() + "#@#" + voter.provinceId.toString() + "#@#" +
                    voter.districtId.toString() + "#@#" + voter.llgId.toString() + "#@#" +
                    voter.locationId.toString() + "#@#" + voter.wardId.toString() + "#@#form11_image#@#" + voter.tempElectorId.toString() + "#@#" + voter.electorId + ".jpg"
        }

    }


}