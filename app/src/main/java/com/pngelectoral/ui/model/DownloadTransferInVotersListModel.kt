package com.pngelectoral.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DownloadTransferInVotersListModel(

    @SerializedName("message")
    var message: String? = "",

    @SerializedName("status")
    var status: Boolean? = false,

    @SerializedName("data")
    var data: DownloadTransferInVotersData? = null


) : Serializable

data class DownloadTransferInVotersData(

    @SerializedName("result")
    var votersList: ArrayList<DownloadedTransferInVotersList>? = ArrayList(),

    @SerializedName("total")
    var total: Int = 0


) : Serializable