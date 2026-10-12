package com.pngelectoral.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponseModel(

    @SerializedName("message")
    var message: String? = "",

    @SerializedName("status")
    var status: Boolean? = false,

    @SerializedName("data")
    var data: DPOUserData? = null


) : Serializable

data class DPOUserData(

    @SerializedName("userId")
    var userId: String = "",

    @SerializedName("firstName")
    var firstName: String = "",

    @SerializedName("lastName")
    var lastName: String = "",

    @SerializedName("profileImage")
    var profileImage: String = "",

    @SerializedName("profileImageName")
    var profileImageName: String = "",

    @SerializedName("profileImageUrl")
    var profileImageUrl: String = "",

    @SerializedName("fullName")
    var fullName: String = "",

    @SerializedName("emailAddress")
    var emailAddress: String = "",

    @SerializedName("contactNumber")
    var contactNumber: String = "",

    @SerializedName("password")
    var password: String = "",

    @SerializedName("status")
    var status: String = "",

    @SerializedName("statusName")
    var statusName: String = "",

    @SerializedName("role")
    var role: String = "",

    @SerializedName("roleName")
    var roleName: String = "",

    @SerializedName("region")
    var region: String = "",

    @SerializedName("regionName")
    var regionName: String = "",

    @SerializedName("province")
    var province: String = "",

    @SerializedName("provinceName")
    var provinceName: String = "",

    @SerializedName("district")
    var district: String = "",

    @SerializedName("districtName")
    var districtName: String = "",

    @SerializedName("llg")
    var llg: String = "",

    @SerializedName("llgName")
    var llgName: String = "",

    @SerializedName("ward")
    var ward: String = "",

    @SerializedName("wardName")
    var wardName: String = "",

    @SerializedName("location")
    var location: String = "",

    @SerializedName("locationName")
    var locationName: String = "",

    @SerializedName("permission")
    var permission: String = "",

    @SerializedName("isDBWrite")
    var isDBWrite: String = "",

    @SerializedName("responseCode")
    var responseCode: String = "",

    @SerializedName("actionBy")
    var actionBy: String = "",

    @SerializedName("mkaDistrict")
    var mkaDistrict: String = "",

    @SerializedName("mkaDistrictName")
    var mkaDistrictName: String = "",

    @SerializedName("mkallg")
    var mkallg: String = "",

    @SerializedName("mkallgName")
    var mkallgName: String = "",

    @SerializedName("mkaWard")
    var mkaWard: String = "",

    @SerializedName("mkaWardName")
    var mkaWardName: String = "",

    @SerializedName("mkaLocation")
    var mkaLocation: String = "",

    @SerializedName("mkaLocationName")
    var mkaLocationName: String = ""
) : Serializable