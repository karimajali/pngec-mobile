package com.pngelectoral.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GeneralSettingsModel(

    @SerializedName("message")
    var message: String? = "",

    @SerializedName("status")
    var status: Boolean? = false,

    @SerializedName("data")
    var data: GeneralSettingsData? = null


) : Serializable

data class GeneralSettingsData(

    @SerializedName("dcPassword")
    var dcPassword: String = "",

    @SerializedName("dcVersion")
    var dcVersion: Int = 0,

    @SerializedName("geoVersion")
    var geoVersion: Int = 0,

    @SerializedName("occupationVersion")
    var occupationVersion: Int = 0,

    @SerializedName("isDCChange")
    var isDCChange: Boolean = false,

    @SerializedName("isGeoChange")
    var isGeoChange: Boolean = false,

    @SerializedName("isOccupationChange")
    var isOccupationChange: Boolean = false,

    @SerializedName("dcDateReset")
    var dcDateReset: String = "",

    @SerializedName("geoRefreshDate")
    var geoRefreshDate: String = "",

    @SerializedName("occupationRefreshDate")
    var occupationRefreshDate: String = "",

    @SerializedName("actionBy")
    var actionBy: Int = 0
) : Serializable