package com.pngelectoral.datasync

import ApiConstants
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.AsyncTask
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.gson.JsonObject
import com.pngelectoral.R
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.ui.model.AllLocationsModel
import com.pngelectoral.ui.model.DPOUserData
import com.pngelectoral.ui.model.DownloadVotersListModel
import com.pngelectoral.ui.model.LocationList
import com.pngelectoral.ui.model.VotersList
import com.pngelectoral.ui.retrofit.ApiClient
import com.pngelectoral.ui.retrofit.IFailureHandler
import com.pngelectoral.ui.retrofit.ISuccessHandler
import com.pngelectoral.ui.retrofit.WSClient
import com.pngelectoral.utility.CommonMethods.getVoterName
import com.pngelectoral.utility.sharedPreference.SecuredSharedPreferenceWrapper
import kotlin.math.abs

class DownloadPreliminaryRollSyncService : Service() {

    lateinit var dataBaseInstance: AppDatabase
    private var pageNumber = 1
    private var voterPageSize = 500
    private var downloadedPreliminaryRollCount = 0
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper
    lateinit var loggedInDPOUserData: DPOUserData

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        registerReceiver(stopServiceReceiver, IntentFilter(ApiConstants.STOP_SERVICE))

        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(this, "png_electoral")

        pageNumber = securedSharedPreferenceWrapper.getInt(ApiConstants.PRELIMINARY_PAGENO, 1)

        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase

        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )

        startNotificationWithNotification()
        callDownloadVotersAPI()
        return START_NOT_STICKY
    }

    private val stopServiceReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            securedSharedPreferenceWrapper.putInt(ApiConstants.PRELIMINARY_PAGENO, 1)
            stopNotification()
        }
    }


    private fun callDownloadVotersAPI() {

        val downloadVotersObject = JsonObject()
        downloadVotersObject.addProperty(ApiConstants.PAGENO, pageNumber)
        downloadVotersObject.addProperty(ApiConstants.PAGESIZE, voterPageSize)
        if(securedSharedPreferenceWrapper.getString(ApiConstants.ROLE,"") == ApiConstants.ROLE_DPO) {
            downloadVotersObject.addProperty(
                ApiConstants.PROVINCEID,
                securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_PROVINCE_ID, 0)
            )
            downloadVotersObject.addProperty(
                ApiConstants.DISTRICTID,
                securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_DISTRICT_ID, 0)
            )
            downloadVotersObject.addProperty(
                ApiConstants.LLGID,
                securedSharedPreferenceWrapper.getInt(ApiConstants.PRELIMINARY_ROLL_LLG_ID, 0)
            )
            downloadVotersObject.addProperty(
                ApiConstants.WARDID,
                securedSharedPreferenceWrapper.getInt(ApiConstants.PRELIMINARY_ROLL_WARD_ID, 0)
            )
            downloadVotersObject.addProperty(
                ApiConstants.LOCATIONID,
                securedSharedPreferenceWrapper.getInt(ApiConstants.PRELIMINARY_ROLL_LOCATION_ID, 0)
            )
        }else{
            downloadVotersObject.addProperty(
                ApiConstants.PROVINCEID,
                loggedInDPOUserData.province.toInt()
            )
            downloadVotersObject.addProperty(
                ApiConstants.DISTRICTID,
                loggedInDPOUserData.district.toInt()
            )
            downloadVotersObject.addProperty(
                ApiConstants.LLGID,
                loggedInDPOUserData.llg.toInt()
            )
            downloadVotersObject.addProperty(
                ApiConstants.WARDID,
                loggedInDPOUserData.ward.toInt()
            )
            downloadVotersObject.addProperty(
                ApiConstants.LOCATIONID,
                loggedInDPOUserData.location.toInt()
            )
            downloadVotersObject.addProperty(
                ApiConstants.MKADISTRICTID,
                loggedInDPOUserData.mkaDistrict.toInt()
            )
            downloadVotersObject.addProperty(
                ApiConstants.MKALLGID,
                loggedInDPOUserData.mkallg.toInt()
            )
            downloadVotersObject.addProperty(
                ApiConstants.MKAWARDID,
                loggedInDPOUserData.mkaWard.toInt()
            )
            downloadVotersObject.addProperty(
                ApiConstants.MKALOCATIONID,
                loggedInDPOUserData.mkaLocation.toInt()
            )
        }

        val call =
            ApiClient.getVotersRetrofitInterface()
                ?.callDownloadPreliminaryVotersAPI(downloadVotersObject)

        WSClient<DownloadVotersListModel>().request(
            this,
            ApiConstants.DOWNLOAD_PRELIMINARY_VOTERS_LIST,
            call,
            object :
                ISuccessHandler<DownloadVotersListModel> {
                override fun successResponse(
                    requestCode: Int,
                    mResponse: DownloadVotersListModel
                ) {

                    if (mResponse.status == true && mResponse.data != null) {
                        val downloadVotersListData = mResponse.data
                        val totalPagesRecords = downloadVotersListData!!.total

                        if (downloadVotersListData.votersList!!.size > 0) {
                            DownloadVotersListAsync(
                                downloadVotersListData.votersList!!,
                                totalPagesRecords
                            ).execute()
                        } else {


                            stopNotification()
                            securedSharedPreferenceWrapper.putInt(
                                ApiConstants.PRELIMINARY_DOWNLOADED_PERCENTAGE,
                                0
                            )
                            securedSharedPreferenceWrapper.putBoolean(
                                ApiConstants.PRELIMINARY_DOWNLOAD_IN_PROGRESS,
                                false
                            )
                            val intent =
                                Intent(ApiConstants.ALL_PRELIMINARY_DATA_COMPLETED_BROADCAST)
                            sendBroadcast(intent)


                        }


                    } else {
                        stopNotification()
                    }


                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    stopNotification()
                    val internetConnectionStatusIntent =
                        Intent(ApiConstants.INTERNET_CONNECTION_LOST)
                    sendBroadcast(internetConnectionStatusIntent)
                }
            })

    }

    @SuppressLint("StaticFieldLeak")
    internal inner class DownloadVotersListAsync(
        var downloadedVotersList: ArrayList<VotersList>,
        var totalDownloadVotersCount: Int
    ) : AsyncTask<Void?, Void?, Int?>() {

        override fun doInBackground(vararg p0: Void?): Int {
            for (i in downloadedVotersList.indices) {
                val isVoterDownloaded = dataBaseInstance.votersListDao()
                    .checkVoterDownloaded(downloadedVotersList[i].electorId)
                if (isVoterDownloaded == null) {
                    downloadedVotersList[i].tempElectorId = abs(System.currentTimeMillis().toInt())
                    if (downloadedVotersList[i].voterName == null)
                        downloadedVotersList[i].voterName = getVoterName(downloadedVotersList[i])
                    dataBaseInstance.votersListDao().insert(downloadedVotersList[i])
                } else {
                    downloadedVotersList[i].tempElectorId = isVoterDownloaded.tempElectorId
                    if (downloadedVotersList[i].voterName == null)
                        downloadedVotersList[i].voterName = getVoterName(downloadedVotersList[i])
                    dataBaseInstance.votersListDao().insert(downloadedVotersList[i])
                }
            }
            downloadedPreliminaryRollCount += downloadedVotersList.size
            return downloadedPreliminaryRollCount
        }

        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)

            val progressIntent = Intent(ApiConstants.PRELIMINARY_SINGLE_PAGE_DATA_COMPLETED)
            progressIntent.putExtra("Total_Records", totalDownloadVotersCount)
            progressIntent.putExtra("Downloaded_Records", result)
            sendBroadcast(progressIntent)

            if (result == totalDownloadVotersCount) {

                stopNotification()
                securedSharedPreferenceWrapper.putInt(
                    ApiConstants.PRELIMINARY_DOWNLOADED_PERCENTAGE,
                    0
                )
                securedSharedPreferenceWrapper.putBoolean(
                    ApiConstants.PRELIMINARY_DOWNLOAD_IN_PROGRESS,
                    false
                )
                val intent = Intent(ApiConstants.ALL_PRELIMINARY_DATA_COMPLETED_BROADCAST)
                sendBroadcast(intent)


            } else {
                pageNumber++
                securedSharedPreferenceWrapper.putInt(ApiConstants.PRELIMINARY_PAGENO, pageNumber)
                callDownloadVotersAPI()

            }
        }
    }

    private fun startNotificationWithNotification() {

        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(
                    "preliminary_voter_list_service",
                    "Preliminary Voter List Download Service"
                )
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setContentText("Downloading Preliminary Roll voter data")
            .build()
        startForeground(103, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.GREEN
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    private fun stopNotification() {
        stopForeground(true)
        stopSelf()
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(stopServiceReceiver)
    }


}