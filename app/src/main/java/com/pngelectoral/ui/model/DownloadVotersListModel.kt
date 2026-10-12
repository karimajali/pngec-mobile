package com.pngelectoral.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DownloadVotersListModel(

    @SerializedName("message")
    var message: String? = "",

    @SerializedName("status")
    var status: Boolean? = false,

    @SerializedName("data")
    var data: DownloadVotersData? = null


) : Serializable

data class DownloadVotersData(

    @SerializedName("result")
    var votersList: ArrayList<VotersList>? = ArrayList(),

    @SerializedName("total")
    var total: Int = 0


) : Serializable