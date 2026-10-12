package com.pngelectoral.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VoterListFilterModel(

    @SerializedName("id")
    var id: String? = "0",

    @SerializedName("name")
    var name: String? = "" ,

    @SerializedName("selected")
    var selected: Boolean? = false


) : Serializable