package com.pngelectoral.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GeoListModel(

    @SerializedName("message")
    var message: String? = "",

    @SerializedName("status")
    var status: Boolean? = false,

    @SerializedName("data")
    var data: GeoListData? = null


) : Serializable

data class GeoListData(

    @SerializedName("regionList")
    var regionList: ArrayList<RegionList>? = ArrayList(),

    @SerializedName("provinceList")
    var provinceList: ArrayList<ProvinceList>? = ArrayList(),

    @SerializedName("districtList")
    var districtList: ArrayList<DistrictList>? = ArrayList(),

    @SerializedName("llgList")
    var llgList: ArrayList<LLGList>? = ArrayList(),

    @SerializedName("wardList")
    var wardList: ArrayList<WardList>? = ArrayList()

//    @SerializedName("locationList")
//    var locationList: ArrayList<LocationList>? = ArrayList()

) : Serializable