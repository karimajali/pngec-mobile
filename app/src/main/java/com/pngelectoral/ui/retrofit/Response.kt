package com.pngelectoral.ui.retrofit

import com.google.gson.annotations.SerializedName

class Response<T> {

    @SerializedName("data")
    var data: T? = null

    @SerializedName("message")
    var message: String? = ""

    @SerializedName("status")
    var status: Boolean? = false

//    @SerializedName("settings")
//    var settings: Settings? = null
//
//    class Settings {
//        @SerializedName("message")
//        var message: String? = null
//        @SerializedName("success")
//        var success: String? = "0"
//
//
//        @SerializedName("count")
//        @Expose
//        var count: Int = 0
//        @SerializedName("per_page")
//        @Expose
//        var perPage: Int = 0
//        @SerializedName("curr_page")
//        @Expose
//        var currPage: Int = 0
//        @SerializedName("prev_page")
//        @Expose
//        var prevPage: Int = 0
//        @SerializedName("next_page")
//        @Expose
//        var nextPage: Int = 0
//        @SerializedName("fields")
//        @Expose
//        var fields: List<String>? = null
//
//
//    }
//
//    var error=""


}
