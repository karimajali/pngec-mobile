package com.pngelectoral.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SyncedVoterStatusModel(

    @SerializedName("message")
    var message: String? = "",

    @SerializedName("status")
    var status: Boolean? = false,

    @SerializedName("data")
    var votersList: ArrayList<SyncedVoterStatusData>? = ArrayList(),


    ) : Serializable

data class SyncedVoterStatusData(

    @SerializedName("electorId")
    var electorId: Long? = null,

    @SerializedName("tempElectorId")
    var tempElectorId: Int? = null,

    @SerializedName("locationId")
    var locationId: Int? = null,

    @SerializedName("wardId")
    var wardId: Int? = null,

    @SerializedName("llgId")
    var llgId: Int? = null,

    @SerializedName("districtId")
    var districtId: Int? = null,

    @SerializedName("provinceId")
    var provinceId: Int? = null,

    @SerializedName("regionId")
    var regionId: Int? = null,

    @SerializedName("status")
    var status: String? = "",

    @SerializedName("isSynced")
    var isSynced: Boolean? = null,

    ) : Serializable

