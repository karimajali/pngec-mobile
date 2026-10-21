package com.pngelectoral.datasync

import android.content.Context
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.retrofit.ApiClient
import com.pngelectoral.ui.retrofit.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.UUID

/**
 * Phase 1 & Phase 2: Sync V2 Client Manager
 * Handles:
 * 1. Batch Sync of Voter data with client-side UUID UploadIds (Idempotency)
 * 2. Independent Asynchronous Image Uploads (Decoupled sync)
 * 3. SevisPass National Digital ID Verification
 */
object SyncV2Manager {

    /**
     * Phase 1: Uploads a batch of voter records cleanly formatted as JSON with UUID UploadIds.
     */
    suspend fun syncBatchAsync(
        context: Context,
        voters: List<VotersList>,
        actionType: String = "NEW",
        userId: Int = 0,
        deviceId: String = ""
    ): SyncV2BatchResponse = withContext(Dispatchers.IO) {
        val db = DatabaseClient.getInstance(context).appDatabase.votersListDao()

        // 1. Ensure each voter has a unique UploadId (persisted locally for safe retries)
        for (voter in voters) {
            if (voter.uploadId == null || voter.uploadId.isEmpty()) {
                voter.uploadId = UUID.randomUUID().toString().replace("-", "")
                db.update(voter)
            }
        }

        // 2. Build Batch Request
        val batchRequest = SyncV2BatchRequest(
            userId = userId,
            deviceId = deviceId,
            actionType = actionType,
            voters = voters
        )

        // 3. Execute Retrofit Call synchronously in coroutine
        val response = try {
            val call = ApiClient.getRetrofitInterface()?.callSyncV2BatchAPI(batchRequest)
            val retrofitRes = call?.execute()
            retrofitRes?.body() ?: SyncV2BatchResponse(status = false, message = "Empty response from server")
        } catch (e: Exception) {
            SyncV2BatchResponse(status = false, message = "Network error: ${e.message}")
        }

        // 4. Update local database based on server results
        if (response.status && response.results.isNotEmpty()) {
            val resultMap = response.results.associateBy { it.uploadId }
            val currentSyncTime = java.text.SimpleDateFormat("dd/MM/yyyy hh:mm a", java.util.Locale.getDefault()).format(java.util.Date())
            for (voter in voters) {
                val res = resultMap[voter.uploadId]
                if (res != null && (res.isSuccess || res.status == "ALREADY_PROCESSED" || res.status == "QUEUED" || res.status == "SUCCESS")) {
                    val finalElectorId = if (res.electorId > 0) res.electorId else voter.electorId
                    db.markVoterAsSynced(finalElectorId, voter.uploadId, voter.tempElectorId, currentSyncTime)
                    voter.electorId = finalElectorId
                    voter.isDataUploaded = 1
                    voter.isSyncToServer = 1
                    voter.syncStatus = 1
                    voter.syncDateTime = currentSyncTime
                    voter.status = "OLD"
                }
            }
        }

        return@withContext response
    }

    /**
     * Phase 1: Independent, asynchronous image upload for profile and form photos.
     */
    suspend fun uploadVoterImageAsync(
        context: Context,
        electorId: Long,
        uploadId: String,
        imageType: String, // "profile" or "form"
        imagePath: String
    ): Response<String> = withContext(Dispatchers.IO) {
        val file = File(imagePath)
        if (!file.exists()) {
            return@withContext Response<String>().apply {
                status = false
                message = "File does not exist: $imagePath"
            }
        }

        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val imageTypePart = MultipartBody.Part.createFormData("imageType", imageType)
        val electorIdPart = MultipartBody.Part.createFormData("electorId", electorId.toString())
        val uploadIdPart = MultipartBody.Part.createFormData("uploadId", uploadId)

        try {
            val call = ApiClient.getRetrofitInterface()?.callSyncV2UploadImageAPI(
                file = filePart,
                imageType = imageTypePart,
                electorId = electorIdPart,
                uploadId = uploadIdPart
            )
            val retrofitRes = call?.execute()
            return@withContext retrofitRes?.body() ?: Response<String>().apply {
                status = false
                message = "Empty response from image upload endpoint"
            }
        } catch (e: Exception) {
            return@withContext Response<String>().apply {
                status = false
                message = "Upload failed: ${e.message}"
            }
        }
    }

    /**
     * Phase 2: SevisPass National Digital ID verification connector.
     */
    suspend fun verifySevisPassAsync(
        sevisPassId: String,
        firstName: String,
        lastName: String,
        dob: String,
        gender: String,
        electorId: Long? = null,
        userId: Int? = null
    ): Response<SevisPassVerifyResponse> = withContext(Dispatchers.IO) {
        val req = SevisPassVerifyRequest(
            sevisPassId = sevisPassId.trim(),
            firstName = firstName,
            lastName = lastName,
            dateOfBirth = dob,
            gender = gender,
            electorId = electorId,
            userId = userId
        )

        try {
            val call = ApiClient.getRetrofitInterface()?.callVerifySevisPassAPI(req)
            val res = call?.execute()
            return@withContext res?.body() ?: Response<SevisPassVerifyResponse>().apply {
                status = false
                message = "Empty response from verification service"
            }
        } catch (e: Exception) {
            return@withContext Response<SevisPassVerifyResponse>().apply {
                status = false
                message = "Verification failed: ${e.message}"
            }
        }
    }
}
