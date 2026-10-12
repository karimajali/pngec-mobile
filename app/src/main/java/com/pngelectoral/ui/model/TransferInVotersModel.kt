package com.pngelectoral.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TransferInVotersModel(

    @SerializedName("message")
    var message: String? = "",

    @SerializedName("status")
    var status: Boolean? = false,

    @SerializedName("data")
    var data: TransferInVotersData? = null


) : Serializable

data class TransferInVotersData(

    @SerializedName("result")
    var votersList: ArrayList<VotersList>? = ArrayList(),

    @SerializedName("total")
    var total: Int = 0


) : Serializable