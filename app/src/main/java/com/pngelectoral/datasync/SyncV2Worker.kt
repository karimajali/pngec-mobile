package com.pngelectoral.datasync

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.pngelectoral.R
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.ui.model.DPOUserData
import ApiConstants
import android.provider.Settings
import com.pngelectoral.utility.sharedPreference.SecuredSharedPreferenceWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

/**
 * Phase 1: Kotlin WorkManager Background Sync Worker
 * Operates offline-first (like WhatsApp media upload):
 * 1. Data first: Uploads voter demographic batches via SyncV2Manager
 * 2. Images second: Asynchronously uploads profile & form photos
 * 3. Chained and retried with exponential backoff on network failures
 */
class SyncV2Worker(
    private val appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val CHANNEL_ID = "PNGEC_SYNC_CHANNEL"
        const val NOTIFICATION_ID = 1001
        const val TAG = "PNGEC_SYNC_V2_WORKER"
        const val KEY_WARD_ID = "KEY_WARD_ID"

        /**
         * Enqueues background sync job when a Ward is marked complete or user taps "Sync Now".
         */
        fun enqueueWardSync(context: Context, wardId: Int = 0, isImmediate: Boolean = false) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()

            val data = workDataOf(KEY_WARD_ID to wardId)

            val syncRequest = OneTimeWorkRequestBuilder<SyncV2Worker>()
                .setConstraints(constraints)
                .setInputData(data)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 15, TimeUnit.SECONDS)
                .addTag(TAG)
                .build()

            val workManager = WorkManager.getInstance(context)
            if (isImmediate) {
                workManager.enqueueUniqueWork(
                    "PNGEC_IMMEDIATE_SYNC",
                    ExistingWorkPolicy.REPLACE,
                    syncRequest
                )
            } else {
                workManager.enqueueUniqueWork(
                    "PNGEC_WARD_SYNC_${wardId}",
                    ExistingWorkPolicy.KEEP,
                    syncRequest
                )
            }
        }
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        showProgressNotification("Voter Sync in Progress", "Preparing records...")

        val db = DatabaseClient.getInstance(appContext).appDatabase.votersListDao()
        val pref = SecuredSharedPreferenceWrapper(appContext, "png_electoral")
        val userData = pref.getObject(ApiConstants.DPO_LOGGED_IN_USER_DATA, DPOUserData::class.java)
        val userId = userData?.userId?.toIntOrNull() ?: 1
        val deviceId = Settings.Secure.getString(appContext.contentResolver, Settings.Secure.ANDROID_ID) ?: "MOBILE_DEVICE"

        try {
            // STEP 1: DATA FIRST - Fetch all unsynced voters
            val unsyncedVoters = db.updatedDataOnlyVotersList
            if (unsyncedVoters != null && unsyncedVoters.isNotEmpty()) {
                val batchSize = 50
                val chunks = unsyncedVoters.chunked(batchSize)

                for ((index, batch) in chunks.withIndex()) {
                    showProgressNotification(
                        "Syncing Voter Data",
                        "Uploading batch ${index + 1} of ${chunks.size} (${batch.size} records)..."
                    )

                    val batchRes = SyncV2Manager.syncBatchAsync(
                        context = appContext,
                        voters = batch,
                        actionType = "BATCH",
                        userId = userId,
                        deviceId = deviceId
                    )

                    if (!batchRes.status && batchRes.results.isEmpty()) {
                        // Network failure, trigger retry with backoff
                        return@withContext Result.retry()
                    }
                }
            }

            // STEP 2: IMAGES SECOND - Resumable independent image uploads
            val votersWithImages = db.updatedImageOnlyVotersList
            if (votersWithImages != null && votersWithImages.isNotEmpty()) {
                var imgCount = 0
                for (voter in votersWithImages) {
                    imgCount++
                    showProgressNotification(
                        "Syncing Voter Photos",
                        "Uploading image $imgCount of ${votersWithImages.size}..."
                    )

                    // Profile Image
                    if (!voter.electorProfileImage.isNullOrEmpty()) {
                        SyncV2Manager.uploadVoterImageAsync(
                            context = appContext,
                            electorId = voter.electorId,
                            uploadId = voter.uploadId ?: "",
                            imageType = "profile",
                            imagePath = voter.electorProfileImage
                        )
                    }

                    // Form Image
                    if (!voter.electorFormImage.isNullOrEmpty()) {
                        SyncV2Manager.uploadVoterImageAsync(
                            context = appContext,
                            electorId = voter.electorId,
                            uploadId = voter.uploadId ?: "",
                            imageType = "form",
                            imagePath = voter.electorFormImage
                        )
                    }

                    // Mark image as uploaded
                    voter.isImageUploaded = 1
                    db.update(voter)
                }
            }

            showProgressNotification("Sync Completed", "All voter records and images synced successfully.")
            return@withContext Result.success()

        } catch (e: Exception) {
            return@withContext Result.retry()
        }
    }

    private fun showProgressNotification(title: String, content: String) {
        val notificationManager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "PNGEC Sync Service",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(appContext, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setOngoing(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}
