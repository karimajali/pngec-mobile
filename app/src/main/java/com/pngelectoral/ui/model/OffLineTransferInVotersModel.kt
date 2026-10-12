package com.pngelectoral.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OffLineTransferInVotersModel(

    @SerializedName("data")
    var data: OffLineTransferInVotersData? = null

) : Serializable

data class OffLineTransferInVotersData(

    @SerializedName("result")
    var votersList: ArrayList<DownloadedVotersList>? = ArrayList()

) : Serializable