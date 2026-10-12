package com.pngelectoral.ui.model


import com.google.gson.annotations.SerializedName

/**Model class created to fetch data from api*/
data class MKAGeoListModel(
    @SerializedName("data")
    val data: ArrayList<GeoListData>? = arrayListOf(),
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("status")
    val status: Boolean? = false
) {
    data class GeoListData(
        @SerializedName("actionBy")
        val actionBy: Int? = 0,
        @SerializedName("districtId")
        val districtId: Int? = 0,
        @SerializedName("districtName")
        val districtName: String? = "",
        @SerializedName("llGs")
        val llGs: List<LlG?>? = arrayListOf()
    ) {
        data class LlG(
            @SerializedName("llgId")
            val llgId: Int? = 0,
            @SerializedName("llgName")
            val llgName: String? = "",
            @SerializedName("wards")
            val wards: ArrayList<Ward>? = arrayListOf()
        ) {
            data class Ward(
                @SerializedName("locations")
                val locations: ArrayList<Location>? = arrayListOf(),
                @SerializedName("clans")
                val clans: ArrayList<MKAClanListData>? = arrayListOf(),
                @SerializedName("wardId")
                val wardId: Int? = 0,
                @SerializedName("wardName")
                val wardName: String? = "",
                @SerializedName("wardNumber")
                val wardNumber: Int? = 0
            ) {
                data class Location(
                    @SerializedName("locationId")
                    val locationId: Int? = 0,
                    @SerializedName("locationName")
                    val locationName: String? = ""
                )
            }
        }
    }
}