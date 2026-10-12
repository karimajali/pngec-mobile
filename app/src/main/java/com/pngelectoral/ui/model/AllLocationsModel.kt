package com.pngelectoral.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AllLocationsModel(

    @SerializedName("message")
    var message: String? = "",

    @SerializedName("status")
    var status: Boolean? = false,

    @SerializedName("data")
    var data: LocationsData? = null


) : Serializable

data class LocationsData(

    @SerializedName("result")
    var locationsList: ArrayList<LocationList>? = ArrayList(),

    @SerializedName("total")
    var total: Int = 0


) : Serializable