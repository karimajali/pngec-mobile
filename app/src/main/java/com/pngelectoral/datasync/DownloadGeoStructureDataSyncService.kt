package com.pngelectoral.datasync

import ApiConstants
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.gson.JsonObject
import com.pngelectoral.R
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.ui.model.AllLocationsModel
import com.pngelectoral.ui.model.LocationList
import com.pngelectoral.ui.retrofit.ApiClient
import com.pngelectoral.ui.retrofit.IFailureHandler
import com.pngelectoral.ui.retrofit.ISuccessHandler
import com.pngelectoral.ui.retrofit.WSClient
import com.pngelectoral.utility.sharedPreference.SecuredSharedPreferenceWrapper
import kotlinx.coroutines.*

class DownloadGeoStructureDataSyncService : Service() {

    lateinit var dataBaseInstance: AppDatabase
    private var pageNumber = 1
    private var locationPageSize = 1000
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        registerReceiver(stopServiceReceiver, IntentFilter(ApiConstants.STOP_SERVICE))
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(this, "png_electoral")
        pageNumber = securedSharedPreferenceWrapper.getInt(ApiConstants.GEO_STRUCTURE_PAGENO, 1)
        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase
        startNotificationWithNotification()
        callLocationsAPI()
        return START_NOT_STICKY
    }

    private fun callLocationsAPI() {

        val downloadLocationsObject = JsonObject()
        downloadLocationsObject.addProperty(ApiConstants.PAGENO, pageNumber)
        downloadLocationsObject.addProperty(ApiConstants.PAGESIZE, locationPageSize)

        val call =
            ApiClient.getLocationsRetrofitInterface()?.callAllLocationsAPI(downloadLocationsObject)

        WSClient<AllLocationsModel>().request(
            this,
            ApiConstants.DPO_DOWNLOAD_LOCATIONS_LIST,
            call,
            object :
                ISuccessHandler<AllLocationsModel> {
                override fun successResponse(
                    requestCode: Int,
                    mResponse: AllLocationsModel
                ) {
                    if (mResponse.status == true && mResponse.data != null) {
                        val allLocationsData = mResponse.data
                        val totalLocationsCount = allLocationsData?.total!!
                        if (allLocationsData.locationsList!!.size > 0) {
                            downloadLocationsList(
                                allLocationsData.locationsList!!,
                                totalLocationsCount
                            )
                        } else {
                            stopNotification()
                            securedSharedPreferenceWrapper.putInt(
                                ApiConstants.GEO_STRUCTURE_DOWNLOADED_PERCENTAGE,
                                0
                            )
                            securedSharedPreferenceWrapper.putBoolean(
                                ApiConstants.GEO_STRUCTURE_DOWNLOAD_IN_PROGRESS,
                                false
                            )
                            securedSharedPreferenceWrapper.putInt(
                                ApiConstants.GEO_STRUCTURE_PAGENO,
                                1
                            )
                            val intent =
                                Intent(ApiConstants.GEO_STRUCTURE_DATA_COMPLETED_BROADCAST)
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

    private fun downloadLocationsList(
        locationsList: java.util.ArrayList<LocationList>,
        totalLocationsCount: Int
    ) {

        val exceptionHandler = CoroutineExceptionHandler { _, t ->
            t.printStackTrace()
        }
        GlobalScope.launch(Dispatchers.Main + exceptionHandler) {
            var result: Int
            withContext(Dispatchers.IO) {
                dataBaseInstance.locationListDao().insertAll(locationsList)
                result = dataBaseInstance.locationListDao().all.size
            }

            val progressIntent = Intent(ApiConstants.GEO_STRUCTURE_SINGLE_PAGE_DATA_COMPLETED)
            progressIntent.putExtra("Total_Records", totalLocationsCount)
            progressIntent.putExtra("Downloaded_Records", result)
            sendBroadcast(progressIntent)

            if (result == totalLocationsCount) {

                stopNotification()
                securedSharedPreferenceWrapper.putInt(
                    ApiConstants.GEO_STRUCTURE_DOWNLOADED_PERCENTAGE,
                    0
                )
                securedSharedPreferenceWrapper.putBoolean(
                    ApiConstants.GEO_STRUCTURE_DOWNLOAD_IN_PROGRESS,
                    false
                )
                securedSharedPreferenceWrapper.putInt(ApiConstants.GEO_STRUCTURE_PAGENO, 1)
                val intent = Intent(ApiConstants.GEO_STRUCTURE_DATA_COMPLETED_BROADCAST)
                sendBroadcast(intent)


            } else {

                pageNumber++
                securedSharedPreferenceWrapper.putInt(
                    ApiConstants.GEO_STRUCTURE_PAGENO,
                    pageNumber
                )
                callLocationsAPI()


            }

        }
    }


    private val stopServiceReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            securedSharedPreferenceWrapper.putInt(ApiConstants.GEO_STRUCTURE_PAGENO, 1)
            stopNotification()
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