package com.pngelectoral.ui.model


import com.google.gson.annotations.SerializedName

/**Model class created to fetch data from api.*/
data class MKAClanListModel(
    @SerializedName("data")
    val data: ArrayList<ClanData>? = arrayListOf(),
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("status")
    val status: Boolean? = false
) {
    data class ClanData(
        @SerializedName("actionBy")
        val actionBy: Int? = 0,
        @SerializedName("clanId")
        val clanId: Int? = 0,
        @SerializedName("clanName")
        val clanName: String? = "",
        @SerializedName("createdDate")
        val createdDate: String? = "",
        @SerializedName("description")
        val description: String? = "",
        @SerializedName("lastChangeDate")
        val lastChangeDate: Any? = Any(),
        @SerializedName("modifiedDate")
        val modifiedDate: String? = "",
        @SerializedName("responseCode")
        val responseCode: Int? = 0,
        @SerializedName("updateByUserName")
        val updateByUserName: Any? = Any()
    )
}