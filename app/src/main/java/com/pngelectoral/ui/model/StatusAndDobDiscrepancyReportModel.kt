package com.pngelectoral.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StatusAndDobDiscrepancyReportModel(

    @SerializedName("message")
    var message: String? = "",

    @SerializedName("status")
    var status: Boolean? = false,

    @SerializedName("data")
    var data: StatusAndDobDiscrepancyReportData? = null


) : Serializable

data class StatusAndDobDiscrepancyReportData(

    @SerializedName("result")
    var votersList: ArrayList<StatusAndDobDiscrepancyVotersList>? = ArrayList(),

    @SerializedName("total")
    var total: Int = 0

) : Serializable