package com.pngelectoral.ui.activity

import ApiConstants
import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityDownloadBinding
import com.pngelectoral.databinding.DownloadFileProgressBinding
import com.pngelectoral.databinding.LayoutLoaderDownloadProgressBarBinding
import com.pngelectoral.datasync.DownloadNewVotersSyncService
import com.pngelectoral.ui.model.DPOUserData
import com.pngelectoral.ui.model.DownloadVotersListModel
import com.pngelectoral.ui.retrofit.ApiClient
import com.pngelectoral.ui.retrofit.IFailureHandler
import com.pngelectoral.ui.retrofit.ISuccessHandler
import com.pngelectoral.ui.retrofit.WSClient
import com.pngelectoral.ui.viewModel.DownloadViewModel
import com.pngelectoral.utility.CommonMethods
import com.tonyodev.fetch2.AbstractFetchListener
import com.tonyodev.fetch2.Download
import com.tonyodev.fetch2.Error
import com.tonyodev.fetch2.Fetch
import com.tonyodev.fetch2.FetchConfiguration
import com.tonyodev.fetch2.NetworkType
import com.tonyodev.fetch2.Priority
import com.tonyodev.fetch2.Request
import com.tonyodev.fetch2core.Downloader
import com.tonyodev.fetch2okhttp.OkHttpDownloader
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.okButton
import java.io.*
import java.net.URL
import java.net.URLConnection
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import kotlin.coroutines.CoroutineContext

class NewDownloadActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityDownloadBinding? = null
    private var downloadViewModel: DownloadViewModel? = null
    private lateinit var loggedInDPOUserData: DPOUserData
    lateinit var dataBaseInstance: AppDatabase
    private var clearDataDone = false

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_download
        )
        downloadViewModel =
            ViewModelProvider(this).get(DownloadViewModel::class.java)
        binding?.downloadViewModel = downloadViewModel
        job = Job()
        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )
        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase
        registerReceiver(
            allPageDownloadReceiver,
            IntentFilter(ApiConstants.ALL_DPO_DATA_COMPLETED_BROADCAST)
        )

        registerReceiver(
            singlePageDownloadedReceiver,
            IntentFilter(ApiConstants.SINGLE_PAGE_DATA_COMPLETED)
        )

        registerReceiver(
            internetConnectionStatusReceiver,
            IntentFilter(ApiConstants.INTERNET_CONNECTION_LOST)
        )

        registerReceiver(
            fileDownloadedReceiver,
            IntentFilter(ApiConstants.FILE_DOWNLOADED)
        )

        registerReceiver(
            fileDownloadedProgressReceiver,
            IntentFilter(ApiConstants.FILE_DOWNLOAD_PROGRESS)
        )
        clearData()
        setClickListeners()
    }

    private fun clearData() {
        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                if (!securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.OCCUPATION_DOWNLOADED,
                        false
                    )
                )
                    dataBaseInstance.occupationListDao().deleteAll()

                if (!securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.GEO_LIST_DOWNLOADED,
                        false
                    )
                ) {
                    dataBaseInstance.regionListDao().deleteAll()
                    dataBaseInstance.provinceListDao().deleteAll()
                    dataBaseInstance.districtListDao().deleteAll()
                    dataBaseInstance.llgListDao().deleteAll()
                    dataBaseInstance.wardListDao().deleteAll()
                }
                clearDataDone = true

            }
            if (securedSharedPreferenceWrapper.getBoolean(
                    ApiConstants.DOWNLOAD_IN_PROGRESS,
                    false
                )
            ) {
                showDownloadProgressDialog()
            }


        }
    }

    private fun setClickListeners() {

        binding?.btnDownload?.setOnClickListener {
            if (clearDataDone)
                alert("Downloading data will make changes or data lost. Are you sure to download?") {
                    okButton {
                        requestPermission()
                    }
                    cancelButton {

                    }
                }.show()
        }

    }

    private fun requestPermission() {
        val permissionsList: MutableList<String> = java.util.ArrayList()
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
                        CommonMethods.showSettingsDialog(this@NewDownloadActivity)
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
        if (CommonMethods.checkInternetConnection(application)) {
//            showDownloadProgressDialog()
            CommonMethods.showProgress(this)
            callDownloadVotersListFile()
        } else {
            showAlertMessage("Please check internet connection and try again.")
        }
    }

    private fun callDownloadVotersListFile() {
        val downloadVotersObject = JsonObject()
        downloadVotersObject.addProperty(ApiConstants.UserId, loggedInDPOUserData.userId)

        val call =
            ApiClient.getVotersRetrofitInterface()
                ?.callDownloadVotersFileAPI(downloadVotersObject)

        WSClient<DownloadVotersListModel>().request(
            this,
            ApiConstants.DPO_DOWNLOAD_VOTERS_LIST,
            call,
            object :
                ISuccessHandler<DownloadVotersListModel> {
                override fun successResponse(
                    requestCode: Int,
                    mResponse: DownloadVotersListModel
                ) {
                    if (mResponse.status == true) {
                        val url = mResponse.message
                        downloadFileUsingFetch(url)
                    } else {
                        mResponse.message?.let { showAlertMessage(it) }
                        CommonMethods.hideProgress()
                    }
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    showAlertMessage(message)
                    CommonMethods.hideProgress()
//                    mDownloadProgressDialog?.dismiss()
                }
            })

    }

    var zipFilePath = ""
    private fun downloadFile(url: String?) {
        CommonMethods.hideProgress()
        showFileDownloadProgressDialog()
        val fileName: String = url!!.substring(url.lastIndexOf('/') + 1)
        val folder = File("${getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)}")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val file =
            File(
                folder,
                fileName
            )

        if (file.exists())
            file.delete()
        file.createNewFile()

        launch(Dispatchers.IO) {
            /**Added file downloader in try catch block to the catch the exception*/
            var count: Int
            val u = URL(url)
            val conn: URLConnection = u.openConnection()
            /**Used to close the current connection request.*/
            conn.setRequestProperty("connection", "close")
            val contentLength: Int = conn.contentLength
            val input: InputStream = BufferedInputStream(
                u.openStream(),
                8192
            )
            val output: OutputStream = FileOutputStream(
                file
            )
            val data = ByteArray(1024)
            var total: Long = 0
            while (input.read(data).also { count = it } != -1) {
                total += count.toLong()
                val progressIntent = Intent(ApiConstants.FILE_DOWNLOAD_PROGRESS)
                progressIntent.putExtra(
                    "Downloaded_Progress",
                    (total * 100 / contentLength).toInt()
                )
                sendBroadcast(progressIntent)
                output.write(data, 0, count)
            }
            // flushing output
            output.flush()
            // closing streams
            output.close()
            input.close()
            /*try {

            }catch (e:Exception){
                Log.e("FILE_DOWNLOADER", "downloadFileException==>"+e.localizedMessage)
            }*/


            //UnZipping the downloaded file
            try {
                val unZipFileFolder =
                    File(
                        folder,
                        fileName.split(".")[0]
                    )

                if (!unZipFileFolder.exists()) {
                    unZipFileFolder.mkdirs()
                } else {
                    unZipFileFolder.delete()
                    unZipFileFolder.mkdirs()
                }
                val location = unZipFileFolder.absolutePath
                zipFilePath = location
                try {
                    val inputStream = FileInputStream(file)
                    val zipStream = ZipInputStream(inputStream)
                    var zEntry: ZipEntry? = null
                    while (zipStream.nextEntry.also { zEntry = it } != null) {
                        if (zEntry!!.isDirectory) {
                            handleDirectory(zEntry!!.name, location)
                        } else {
                            val fout = FileOutputStream(
                                location + "/" + zEntry!!.name
                            )
                            val bufout = BufferedOutputStream(fout)
                            val buffer = ByteArray(1024)
                            var read = 0
                            while (zipStream.read(buffer).also { read = it } != -1) {
                                bufout.write(buffer, 0, read)
                            }
                            zipStream.closeEntry()
                            bufout.close()
                            fout.close()
                        }
                    }
                    zipStream.close()
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

            val intent = Intent(ApiConstants.FILE_DOWNLOADED)
            sendBroadcast(intent)
        }
    }

    private var fetch: Fetch? = null
    private fun downloadFileUsingFetch(url: String?){
        CommonMethods.hideProgress()
        showFileDownloadProgressDialog()
        val fileName: String = url!!.substring(url.lastIndexOf('/') + 1)
        val folder = File("${getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)}")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val file =
            File(
                folder,
                fileName
            )

        if (file.exists())
            file.delete()
        file.createNewFile()

        val fetchConfiguration: FetchConfiguration = FetchConfiguration.Builder(this@NewDownloadActivity)
            .setDownloadConcurrentLimit(4)
            .setHttpDownloader(OkHttpDownloader(Downloader.FileDownloaderType.PARALLEL))
            .setNamespace("download_pdfs")
            .build()
        fetch = Fetch.Impl.getInstance(fetchConfiguration)
        fetch?.addListener(object : AbstractFetchListener(){
            override fun onAdded(download: Download) {
                Log.e(ContentValues.TAG, "onAdded: ${download.id} ==> ${download.progress}")
                /*if(!isBroadCastSent){
                    isBroadCastSent = true
                    val intent = Intent(BROADCAST_PDF)
                    intent.putExtra("isDownloading",true)
                    context.sendBroadcast(intent)
                }*/
            }

            override fun onCompleted(download: Download) {
                Log.e(ContentValues.TAG, "onCompleted: ${download.id} ==> ${download.progress}")
                unZipTheDownloadedFile(folder,fileName,file)
            }

            override fun onError(download: Download, error: Error, throwable: Throwable?) {
                super.onError(download, error, throwable)
                Log.e(ContentValues.TAG, "onError: $error")
                fetch?.retry(download.id)
            }

            override fun onProgress(
                download: Download,
                etaInMilliSeconds: Long,
                downloadedBytesPerSecond: Long
            ) {
                Log.e(ContentValues.TAG, "onProgress: ${download.id} ==> ${download.progress}")
                val progressIntent = Intent(ApiConstants.FILE_DOWNLOAD_PROGRESS)
                progressIntent.putExtra(
                    "Downloaded_Progress",
                    download.progress
                )
                sendBroadcast(progressIntent)
            }
        })
        val request = Request(url, "$folder/$fileName")
        request.priority = Priority.HIGH
        request.networkType = NetworkType.ALL
        fetch?.enqueue(request)
    }

    private fun unZipTheDownloadedFile(folder: File, fileName: String, file: File) {
        //UnZipping the downloaded file
        try {
            val unZipFileFolder =
                File(
                    folder,
                    fileName.split(".")[0]
                )

            if (!unZipFileFolder.exists()) {
                unZipFileFolder.mkdirs()
            } else {
                unZipFileFolder.delete()
                unZipFileFolder.mkdirs()
            }
            val location = unZipFileFolder.absolutePath
            zipFilePath = location
            val inputStream = FileInputStream(file)
            val zipStream = ZipInputStream(inputStream)
            var zEntry: ZipEntry? = null
            while (zipStream.nextEntry.also { zEntry = it } != null) {
                if (zEntry!!.isDirectory) {
                    handleDirectory(zEntry!!.name, location)
                } else {
                    val fout = FileOutputStream(
                        location + "/" + zEntry!!.name
                    )
                    val bufout = BufferedOutputStream(fout)
                    val buffer = ByteArray(1024)
                    var read = 0
                    while (zipStream.read(buffer).also { read = it } != -1) {
                        bufout.write(buffer, 0, read)
                    }
                    zipStream.closeEntry()
                    bufout.close()
                    fout.close()
                }
            }
            zipStream.close()
            fetch?.close()
            val intent = Intent(ApiConstants.FILE_DOWNLOADED)
            sendBroadcast(intent)
        } catch (e: Exception) {
            Log.e("TAG", "unZipTheDownloadedFileError==>"+e.localizedMessage)
        }
    }

    private fun handleDirectory(dir: String, location: String) {
        val f = File(location + dir)
        if (!f.isDirectory) {
            f.mkdirs()
        }
    }

    private var downloadFileProgressBinding: DownloadFileProgressBinding? =
        null
    var mFileDownloadProgressDialog: AlertDialog? = null
    private fun showFileDownloadProgressDialog() {

        val builder = AlertDialog.Builder(this)
        downloadFileProgressBinding = DataBindingUtil.inflate(
            LayoutInflater.from(
                this
            ),
            R.layout.download_file_progress, null, false
        ) as DownloadFileProgressBinding
        builder.setView(downloadFileProgressBinding?.root)
        mFileDownloadProgressDialog = builder.create()
        mFileDownloadProgressDialog?.setCanceledOnTouchOutside(false)
        mFileDownloadProgressDialog?.setCancelable(false)
        mFileDownloadProgressDialog?.show()


    }


    private val fileDownloadedProgressReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val downloadProgress = intent.getIntExtra("Downloaded_Progress", 0)
            downloadFileProgressBinding?.downloadProgressBar!!.progress = downloadProgress
            downloadFileProgressBinding?.tvDownloadPercentage!!.text =
                "Downloaded: $downloadProgress%"
        }
    }

    private val fileDownloadedReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            CommonMethods.hideProgress()
            mFileDownloadProgressDialog!!.dismiss()
            showDownloadProgressDialog()
            securedSharedPreferenceWrapper.putBoolean(ApiConstants.DOWNLOAD_IN_PROGRESS, true)
            startRequiredService(this@NewDownloadActivity, DownloadNewVotersSyncService::class.java)
        }
    }


    private val allPageDownloadReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (securedSharedPreferenceWrapper.getBoolean(
                    ApiConstants.GEO_LIST_DOWNLOADED,
                    false
                )
            ) {
                if (mDownloadProgressDialog!!.isShowing) {
                    bindingLayoutLoaderDownloadProgressBar?.downloadProgressBar!!.progress = 100
                    bindingLayoutLoaderDownloadProgressBar?.tvDownloadPercentage!!.text =
                        "Downloaded: 100%"
                }
                hideProgressAndNavigateToNextScreen()
            }

        }
    }

    private val singlePageDownloadedReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val totalRecords = intent.getIntExtra("Total_Records", 0)
            val downloadedRecords = intent.getIntExtra("Downloaded_Records", 0)
            val percentage = (downloadedRecords.toDouble() / totalRecords.toDouble() * 100).toInt()
            securedSharedPreferenceWrapper.putInt(ApiConstants.DOWNLOADED_PERCENTAGE, percentage)
            if (mDownloadProgressDialog!!.isShowing) {
                if (percentage != 100) {
                    bindingLayoutLoaderDownloadProgressBar?.downloadProgressBar!!.progress =
                        percentage
                    bindingLayoutLoaderDownloadProgressBar?.tvDownloadPercentage!!.text =
                        "Downloaded: $percentage%"
                }
            }

        }
    }

    var showingInternetConnectionMessage = false
    private val internetConnectionStatusReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            showAlertMessage("Please check internet connection and try again.")

            if (!showingInternetConnectionMessage) {
                showingInternetConnectionMessage = true

                alert("Please check internet connection and try again.") {
                    okButton {

                        showingInternetConnectionMessage = false

                    }
                }.show()
            }

        }
    }

    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
        unregisterReceiver(allPageDownloadReceiver)
        unregisterReceiver(singlePageDownloadedReceiver)
        unregisterReceiver(internetConnectionStatusReceiver)
        unregisterReceiver(fileDownloadedReceiver)
        unregisterReceiver(fileDownloadedProgressReceiver)
    }


    private var bindingLayoutLoaderDownloadProgressBar: LayoutLoaderDownloadProgressBarBinding? =
        null
    var mDownloadProgressDialog: AlertDialog? = null
    private fun showDownloadProgressDialog() {

        val builder = AlertDialog.Builder(this)
        bindingLayoutLoaderDownloadProgressBar = DataBindingUtil.inflate(
            LayoutInflater.from(
                this
            ),
            R.layout.layout_loader_download_progress_bar, null, false
        ) as LayoutLoaderDownloadProgressBarBinding
        builder.setView(bindingLayoutLoaderDownloadProgressBar?.root)
        mDownloadProgressDialog = builder.create()
        mDownloadProgressDialog?.setCanceledOnTouchOutside(false)
        mDownloadProgressDialog?.setCancelable(false)
        mDownloadProgressDialog?.show()
        bindingLayoutLoaderDownloadProgressBar?.tvDownloadMessage!!.text = "Please wait until the download is complete (2/2)"
        val percentage =
            securedSharedPreferenceWrapper.getInt(ApiConstants.DOWNLOADED_PERCENTAGE, 1)
        bindingLayoutLoaderDownloadProgressBar?.downloadProgressBar!!.progress = percentage
        bindingLayoutLoaderDownloadProgressBar?.tvDownloadPercentage!!.text =
            "Downloaded: $percentage%"

    }

    private fun hideProgressAndNavigateToNextScreen() {
        mDownloadProgressDialog?.dismiss()
        alert("The data has been downloaded successfully") {
            okButton {
                navigateToWardRollScreen()
            }
        }.show().setCancelable(false)
    }

    private fun navigateToWardRollScreen() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }


    private fun showAlertMessage(alertMessage: String) {

        alert(alertMessage) {
            okButton {
            }
        }.show()

    }

    private fun startRequiredService(
        activity: Activity,
        serviceClass: Class<*>?
    ) {
        if (CommonMethods.checkInternetConnection(activity)) {

            if (isMyServiceRunning(DownloadNewVotersSyncService::class.java)) {
                val intent = Intent(ApiConstants.STOP_SERVICE)
                sendBroadcast(intent)
            }

            val serviceIntent = Intent(activity, serviceClass)
            serviceIntent.putExtra(ApiConstants.ZipFilePath, zipFilePath)
            securedSharedPreferenceWrapper.putString(ApiConstants.ZipFilePath, zipFilePath)

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (!isMyServiceRunning(serviceClass!!)) activity.startForegroundService(
                        serviceIntent
                    )
                } else {
                    if (!isMyServiceRunning(serviceClass!!)) activity.startService(
                        serviceIntent
                    )
                }
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }


}



