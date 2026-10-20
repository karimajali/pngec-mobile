package com.pngelectoral.ui.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Phase 1: Modular Batch Sync Request
 */
data class SyncV2BatchRequest(
    @SerializedName("userId")
    @Expose
    var userId: Int = 0,

    @SerializedName("deviceId")
    @Expose
    var deviceId: String? = null,

    @SerializedName("actionType")
    @Expose
    var actionType: String? = "NEW", // NEW, UPDATE, TRANSFER_IN, STATUS_CHANGE

    @SerializedName("voters")
    @Expose
    var voters: List<VotersList> = ArrayList()
)

/**
 * Phase 1: Individual Voter Result returned by Sync V2 API
 */
data class SyncV2RecordResult(
    @SerializedName("uploadId")
    @Expose
    var uploadId: String? = null,

    @SerializedName("electorId")
    @Expose
    var electorId: Long = 0,

    @SerializedName("recordNo")
    @Expose
    var recordNo: Long = 0,

    @SerializedName("isSuccess")
    @Expose
    var isSuccess: Boolean = false,

    @SerializedName("isDuplicateOrRetried")
    @Expose
    var isDuplicateOrRetried: Boolean = false,

    @SerializedName("status")
    @Expose
    var status: String? = null, // SUCCESS, ALREADY_PROCESSED, FAILED

    @SerializedName("errorMessage")
    @Expose
    var errorMessage: String? = null
)

/**
 * Phase 1: Batch Response from Sync V2 API
 */
data class SyncV2BatchResponse(
    @SerializedName("status")
    @Expose
    var status: Boolean = false,

    @SerializedName("message")
    @Expose
    var message: String? = null,

    @SerializedName("processedCount")
    @Expose
    var processedCount: Int = 0,

    @SerializedName("successCount")
    @Expose
    var successCount: Int = 0,

    @SerializedName("failedCount")
    @Expose
    var failedCount: Int = 0,

    @SerializedName("results")
    @Expose
    var results: List<SyncV2RecordResult> = ArrayList()
)

/**
 * Phase 2: SevisPass Verification Request Model
 */
data class SevisPassVerifyRequest(
    @SerializedName("sevisPassId")
    @Expose
    var sevisPassId: String? = null,

    @SerializedName("firstName")
    @Expose
    var firstName: String? = null,

    @SerializedName("lastName")
    @Expose
    var lastName: String? = null,

    @SerializedName("dateOfBirth")
    @Expose
    var dateOfBirth: String? = null, // yyyy-MM-dd

    @SerializedName("gender")
    @Expose
    var gender: String? = null, // M / F

    @SerializedName("electorId")
    @Expose
    var electorId: Long? = null,

    @SerializedName("userId")
    @Expose
    var userId: Int? = null
)

/**
 * Phase 2: SevisPass Verification Response Model
 */
data class SevisPassVerifyResponse(
    @SerializedName("isSuccess")
    @Expose
    var isSuccess: Boolean = false,

    @SerializedName("status")
    @Expose
    var status: String? = null, // Verified, Mismatch, NotFound, Simulated

    @SerializedName("sevisPassId")
    @Expose
    var sevisPassId: String? = null,

    @SerializedName("fullName")
    @Expose
    var fullName: String? = null,

    @SerializedName("gender")
    @Expose
    var gender: String? = null,

    @SerializedName("dateOfBirth")
    @Expose
    var dateOfBirth: String? = null,

    @SerializedName("verifiedDate")
    @Expose
    var verifiedDate: String? = null,

    @SerializedName("message")
    @Expose
    var message: String? = null,

    @SerializedName("mismatches")
    @Expose
    var mismatches: List<String> = ArrayList()
)
