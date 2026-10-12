package com.pngelectoral.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DeviceOperatorPDFReportModel(

    @SerializedName("message")
    var message: String? = "",

    @SerializedName("status")
    var status: Boolean? = false,

    @SerializedName("data")
    var data: DeviceOperatorPDFReportData? = null


) : Serializable

data class DeviceOperatorPDFReportData(

    @SerializedName("reportPath")
    var reportPath: String? = ""

) : Serializable