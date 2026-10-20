package com.pngelectoral.ui.retrofit

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.pngelectoral.ui.model.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {

    @POST("UserMobile/UserLogin")
    fun callLoginAPI(@Body inputParam: JsonObject):
            Call<LoginResponseModel>

    @POST("general/forgotpassword")
    fun callForgotPasswordAPI(@Body inputParam: JsonObject):
            Call<Response<Any>>


    @GET("Voter/GetVoterList/{id}")
    fun callDownloadVotersListAPI(@Path("id") userID: String):
            Call<Response<ArrayList<VotersList>>>

    @GET("General/GetGeoList")
    fun callGetGeoListAPI():
            Call<GeoListModel>

    @GET("General/GetOccupationList")
    fun callGetOccupationListAPI():
            Call<Response<ArrayList<OccupationList>>>

    @POST("Voter/GetTransferInVoterList")
    fun callTransferInVotersListAPI(@Body inputParam: JsonObject):
            Call<TransferInVotersModel>

    @POST("General/ValidateEnrolmentNumber")
    fun callValidateEnrolmentFormNumberAPI(@Body inputParam: JsonObject):
            Call<Response<Any>>

    @POST("Voter/SyncElectors")
    fun callSyncElectorsAPI(@Body syncDataVotersListArray: JsonArray): Call<Response<ArrayList<FailedVotersList>>>

    @POST("Voter/GetVoterList")
    fun callDownloadVotersAPI(@Body downloadVotersObject: JsonObject): Call<DownloadVotersListModel>

    @POST("Voter/DownloadVoterList")
    fun callDownloadVotersFileAPI(@Body downloadVotersObject: JsonObject): Call<DownloadVotersListModel>

    @POST("Voter/GetPreliminaryVoterList")
    fun callDownloadPreliminaryVotersAPI(@Body downloadVotersObject: JsonObject): Call<DownloadVotersListModel>

    @POST("General/GetAllLocations")
    fun callAllLocationsAPI(@Body downloadLocationsObject: JsonObject): Call<AllLocationsModel>

    @POST("General/GetLocationsByIds")
    fun callGetLocationListAPI(@Body downloadLocationsObject: JsonObject): Call<Response<ArrayList<LocationList>>>

    @POST("Voter/GetRejectedVoterList")
    fun callRejectedVotersListAPI(@Body inputParam: JsonObject):
            Call<TransferInVotersModel>

    @POST("General/Logout")
    fun callLogoutAPI(@Body inputParam: JsonObject):
            Call<Response<Any>>

    @Multipart
    @POST("Voter/SyncElectorsAndImages")
    fun syncElectorsAndImages(
        @Part electorID: MultipartBody.Part,
        @Part oldElectorID: MultipartBody.Part,
        @Part locationId: MultipartBody.Part,
        @Part oldLocationId: MultipartBody.Part,
        @Part locationName: MultipartBody.Part,
        @Part wardId: MultipartBody.Part,
        @Part wardName: MultipartBody.Part,
        @Part llgId: MultipartBody.Part,
        @Part llgName: MultipartBody.Part,
        @Part districtId: MultipartBody.Part,
        @Part provinceId: MultipartBody.Part,
        @Part regionId: MultipartBody.Part,
        @Part MKADistrictId: MultipartBody.Part,
        @Part MKALLGId: MultipartBody.Part,
        @Part MKAWardId: MultipartBody.Part,
        @Part MKALocationId: MultipartBody.Part,
        @Part MKAClanId: MultipartBody.Part,
        @Part lName: MultipartBody.Part,
        @Part fName: MultipartBody.Part,
        @Part voterName: MultipartBody.Part,
        @Part gender: MultipartBody.Part,
        @Part dob: MultipartBody.Part,
        @Part("isYOB") isYOB : Boolean,
        @Part("isPWD") isPWD : Boolean,
        @Part religion: MultipartBody.Part,
        @Part fatherLName: MultipartBody.Part,
        @Part fatherFName: MultipartBody.Part,
        @Part motherLName: MultipartBody.Part,
        @Part motherFName: MultipartBody.Part,
        @Part provOfBirthFK: MultipartBody.Part,
        @Part provinceOfBirth: MultipartBody.Part,
        @Part provinceName: MultipartBody.Part,
        @Part openElectorate: MultipartBody.Part,
        @Part placeOfBirth: MultipartBody.Part,
        @Part occupationFK: MultipartBody.Part,
        @Part occupation: MultipartBody.Part,
        @Part clan: MultipartBody.Part,
        @Part subClan: MultipartBody.Part,
        @Part address: MultipartBody.Part,
        @Part natID: MultipartBody.Part,
        @Part natIDRecordNo: MultipartBody.Part,
        @Part status: MultipartBody.Part,
        @Part houseFK: MultipartBody.Part,
        @Part houseSortOrder: MultipartBody.Part,
        @Part restHouse: MultipartBody.Part,
        @Part sectionNo: MultipartBody.Part,
        @Part lotNo: MultipartBody.Part,
        @Part streetName: MultipartBody.Part,
        @Part suburbTownSettlement: MultipartBody.Part,
        @Part familyFK: MultipartBody.Part,
        @Part familyNumber: MultipartBody.Part,
        @Part familyName: MultipartBody.Part,
        @Part familySortOrder: MultipartBody.Part,
        @Part dpo: MultipartBody.Part,
        @Part("lat") lat : Double?,
        @Part("long") longitude : Double?,
        @Part posted: MultipartBody.Part,
        @Part comment: MultipartBody.Part,
        @Part electorProfileImageUrl: MultipartBody.Part,
        @Part electorFormImageUrl: MultipartBody.Part,
        @Part profileImageBody: MultipartBody.Part,
        @Part formImageBody: MultipartBody.Part,
        @Part enrolmentFormNumber: MultipartBody.Part,
        @Part EnrolmentFormType: MultipartBody.Part,
        @Part("IsManualFormNumber") IsManualFormNumber : Boolean,
        @Part createdBy: MultipartBody.Part,
        @Part tempElectorId: MultipartBody.Part,
        @Part clanId: MultipartBody.Part
    ): Call<Response<ArrayList<FailedVotersList>>>?

    @Multipart
    @POST("Voter/SyncElectorsAndImages")
    fun syncRejectedElectorsAndImages(
        @Part electorID: MultipartBody.Part,
        @Part oldElectorID: MultipartBody.Part,
        @Part locationId: MultipartBody.Part,
        @Part oldLocationId: MultipartBody.Part,
        @Part locationName: MultipartBody.Part,
        @Part wardId: MultipartBody.Part,
        @Part wardName: MultipartBody.Part,
        @Part llgId: MultipartBody.Part,
        @Part llgName: MultipartBody.Part,
        @Part districtId: MultipartBody.Part,
        @Part provinceId: MultipartBody.Part,
        @Part regionId: MultipartBody.Part,
        @Part MKADistrictId: MultipartBody.Part,
        @Part MKALLGId: MultipartBody.Part,
        @Part MKAWardId: MultipartBody.Part,
        @Part MKALocationId: MultipartBody.Part,
        @Part MKAClanId: MultipartBody.Part,
        @Part lName: MultipartBody.Part,
        @Part fName: MultipartBody.Part,
        @Part voterName: MultipartBody.Part,
        @Part gender: MultipartBody.Part,
        @Part dob: MultipartBody.Part,
        @Part("isYOB") isYOB : Boolean,
        @Part("isPWD") isPWD : Boolean,
        @Part("isRejected") isRejected : Boolean,
        @Part religion: MultipartBody.Part,
        @Part fatherLName: MultipartBody.Part,
        @Part fatherFName: MultipartBody.Part,
        @Part motherLName: MultipartBody.Part,
        @Part motherFName: MultipartBody.Part,
        @Part provOfBirthFK: MultipartBody.Part,
        @Part provinceOfBirth: MultipartBody.Part,
        @Part provinceName: MultipartBody.Part,
        @Part openElectorate: MultipartBody.Part,
        @Part placeOfBirth: MultipartBody.Part,
        @Part occupationFK: MultipartBody.Part,
        @Part occupation: MultipartBody.Part,
        @Part clan: MultipartBody.Part,
        @Part subClan: MultipartBody.Part,
        @Part address: MultipartBody.Part,
        @Part natID: MultipartBody.Part,
        @Part natIDRecordNo: MultipartBody.Part,
        @Part status: MultipartBody.Part,
        @Part houseFK: MultipartBody.Part,
        @Part houseSortOrder: MultipartBody.Part,
        @Part restHouse: MultipartBody.Part,
        @Part sectionNo: MultipartBody.Part,
        @Part lotNo: MultipartBody.Part,
        @Part streetName: MultipartBody.Part,
        @Part suburbTownSettlement: MultipartBody.Part,
        @Part familyFK: MultipartBody.Part,
        @Part familyNumber: MultipartBody.Part,
        @Part familyName: MultipartBody.Part,
        @Part familySortOrder: MultipartBody.Part,
        @Part dpo: MultipartBody.Part,
        @Part("lat") lat : Double?,
        @Part("long") longitude : Double?,
        @Part posted: MultipartBody.Part,
        @Part comment: MultipartBody.Part,
        @Part electorProfileImageUrl: MultipartBody.Part,
        @Part electorFormImageUrl: MultipartBody.Part,
        @Part profileImageBody: MultipartBody.Part,
        @Part formImageBody: MultipartBody.Part,
        @Part enrolmentFormNumber: MultipartBody.Part,
        @Part EnrolmentFormType: MultipartBody.Part,
        @Part("IsManualFormNumber") IsManualFormNumber : Boolean,
        @Part createdBy: MultipartBody.Part,
        @Part tempElectorId: MultipartBody.Part,
        @Part clanId: MultipartBody.Part
    ): Call<Response<ArrayList<FailedVotersList>>>?

    @Multipart
    @POST("Voter/SyncElectorsAndImages")
    fun syncElectorsAndImagesWithOutVoterImage(
        @Part electorID: MultipartBody.Part,
        @Part oldElectorID: MultipartBody.Part,
        @Part locationId: MultipartBody.Part,
        @Part oldLocationId: MultipartBody.Part,
        @Part locationName: MultipartBody.Part,
        @Part wardId: MultipartBody.Part,
        @Part wardName: MultipartBody.Part,
        @Part llgId: MultipartBody.Part,
        @Part llgName: MultipartBody.Part,
        @Part districtId: MultipartBody.Part,
        @Part provinceId: MultipartBody.Part,
        @Part regionId: MultipartBody.Part,
        @Part MKADistrictId: MultipartBody.Part,
        @Part MKALLGId: MultipartBody.Part,
        @Part MKAWardId: MultipartBody.Part,
        @Part MKALocationId: MultipartBody.Part,
        @Part MKAClanId: MultipartBody.Part,
        @Part lName: MultipartBody.Part,
        @Part fName: MultipartBody.Part,
        @Part voterName: MultipartBody.Part,
        @Part gender: MultipartBody.Part,
        @Part dob: MultipartBody.Part,
        @Part("isYOB") isYOB : Boolean,
        @Part("isPWD") isPWD : Boolean,
        @Part religion: MultipartBody.Part,
        @Part fatherLName: MultipartBody.Part,
        @Part fatherFName: MultipartBody.Part,
        @Part motherLName: MultipartBody.Part,
        @Part motherFName: MultipartBody.Part,
        @Part provOfBirthFK: MultipartBody.Part,
        @Part provinceOfBirth: MultipartBody.Part,
        @Part provinceName: MultipartBody.Part,
        @Part openElectorate: MultipartBody.Part,
        @Part placeOfBirth: MultipartBody.Part,
        @Part occupationFK: MultipartBody.Part,
        @Part occupation: MultipartBody.Part,
        @Part clan: MultipartBody.Part,
        @Part subClan: MultipartBody.Part,
        @Part address: MultipartBody.Part,
        @Part natID: MultipartBody.Part,
        @Part natIDRecordNo: MultipartBody.Part,
        @Part status: MultipartBody.Part,
        @Part houseFK: MultipartBody.Part,
        @Part houseSortOrder: MultipartBody.Part,
        @Part restHouse: MultipartBody.Part,
        @Part sectionNo: MultipartBody.Part,
        @Part lotNo: MultipartBody.Part,
        @Part streetName: MultipartBody.Part,
        @Part suburbTownSettlement: MultipartBody.Part,
        @Part familyFK: MultipartBody.Part,
        @Part familyNumber: MultipartBody.Part,
        @Part familyName: MultipartBody.Part,
        @Part familySortOrder: MultipartBody.Part,
        @Part dpo: MultipartBody.Part,
        @Part("lat") lat : Double?,
        @Part("long") longitude : Double?,
        @Part posted: MultipartBody.Part,
        @Part comment: MultipartBody.Part,
        @Part electorProfileImageUrl: MultipartBody.Part,
        @Part electorFormImageUrl: MultipartBody.Part,
        @Part formImageBody: MultipartBody.Part,
        @Part enrolmentFormNumber: MultipartBody.Part,
        @Part EnrolmentFormType: MultipartBody.Part,
        @Part("IsManualFormNumber") IsManualFormNumber : Boolean,
        @Part createdBy: MultipartBody.Part,
        @Part tempElectorId: MultipartBody.Part,
        @Part clanId: MultipartBody.Part
    ): Call<Response<ArrayList<FailedVotersList>>>?

    @Multipart
    @POST("Voter/SyncElectorsAndImages")
    fun syncElectorsDataOnly(
        @Part electorID: MultipartBody.Part,
        @Part oldElectorID: MultipartBody.Part,
        @Part locationId: MultipartBody.Part,
        @Part oldLocationId: MultipartBody.Part,
        @Part locationName: MultipartBody.Part,
        @Part wardId: MultipartBody.Part,
        @Part wardName: MultipartBody.Part,
        @Part llgId: MultipartBody.Part,
        @Part llgName: MultipartBody.Part,
        @Part districtId: MultipartBody.Part,
        @Part provinceId: MultipartBody.Part,
        @Part regionId: MultipartBody.Part,
        @Part MKADistrictId: MultipartBody.Part,
        @Part MKALLGId: MultipartBody.Part,
        @Part MKAWardId: MultipartBody.Part,
        @Part MKALocationId: MultipartBody.Part,
        @Part MKAClanId: MultipartBody.Part,
        @Part lName: MultipartBody.Part,
        @Part fName: MultipartBody.Part,
        @Part voterName: MultipartBody.Part,
        @Part gender: MultipartBody.Part,
        @Part dob: MultipartBody.Part,
        @Part("isYOB") isYOB : Boolean,
        @Part("isPWD") isPWD : Boolean,
        @Part religion: MultipartBody.Part,
        @Part fatherLName: MultipartBody.Part,
        @Part fatherFName: MultipartBody.Part,
        @Part motherLName: MultipartBody.Part,
        @Part motherFName: MultipartBody.Part,
        @Part provOfBirthFK: MultipartBody.Part,
        @Part provinceOfBirth: MultipartBody.Part,
        @Part provinceName: MultipartBody.Part,
        @Part openElectorate: MultipartBody.Part,
        @Part placeOfBirth: MultipartBody.Part,
        @Part occupationFK: MultipartBody.Part,
        @Part occupation: MultipartBody.Part,
        @Part clan: MultipartBody.Part,
        @Part subClan: MultipartBody.Part,
        @Part address: MultipartBody.Part,
        @Part natID: MultipartBody.Part,
        @Part natIDRecordNo: MultipartBody.Part,
        @Part status: MultipartBody.Part,
        @Part houseFK: MultipartBody.Part,
        @Part houseSortOrder: MultipartBody.Part,
        @Part restHouse: MultipartBody.Part,
        @Part sectionNo: MultipartBody.Part,
        @Part lotNo: MultipartBody.Part,
        @Part streetName: MultipartBody.Part,
        @Part suburbTownSettlement: MultipartBody.Part,
        @Part familyFK: MultipartBody.Part,
        @Part familyNumber: MultipartBody.Part,
        @Part familyName: MultipartBody.Part,
        @Part familySortOrder: MultipartBody.Part,
        @Part dpo: MultipartBody.Part,
        @Part("lat") lat : Double?,
        @Part("long") longitude : Double?,
        @Part posted: MultipartBody.Part,
        @Part comment: MultipartBody.Part,
        @Part electorProfileImageUrl: MultipartBody.Part,
        @Part electorFormImageUrl: MultipartBody.Part,
        @Part enrolmentFormNumber: MultipartBody.Part,
        @Part EnrolmentFormType: MultipartBody.Part,
        @Part("IsManualFormNumber") IsManualFormNumber : Boolean,
        @Part createdBy: MultipartBody.Part,
        @Part tempElectorId: MultipartBody.Part,
        @Part clanId: MultipartBody.Part
    ): Call<Response<ArrayList<FailedVotersList>>>?

    @Multipart
    @POST("Voter/SyncElectorsAndImages")
    fun syncRejectedElectorsAndImagesWithOutVoterImage(
        @Part electorID: MultipartBody.Part,
        @Part oldElectorID: MultipartBody.Part,
        @Part locationId: MultipartBody.Part,
        @Part oldLocationId: MultipartBody.Part,
        @Part locationName: MultipartBody.Part,
        @Part wardId: MultipartBody.Part,
        @Part wardName: MultipartBody.Part,
        @Part llgId: MultipartBody.Part,
        @Part llgName: MultipartBody.Part,
        @Part districtId: MultipartBody.Part,
        @Part provinceId: MultipartBody.Part,
        @Part regionId: MultipartBody.Part,
        @Part MKADistrictId: MultipartBody.Part,
        @Part MKALLGId: MultipartBody.Part,
        @Part MKAWardId: MultipartBody.Part,
        @Part MKALocationId: MultipartBody.Part,
        @Part MKAClanId: MultipartBody.Part,
        @Part lName: MultipartBody.Part,
        @Part fName: MultipartBody.Part,
        @Part voterName: MultipartBody.Part,
        @Part gender: MultipartBody.Part,
        @Part dob: MultipartBody.Part,
        @Part("isYOB") isYOB : Boolean,
        @Part("isPWD") isPWD : Boolean,
        @Part("isRejected") isRejected : Boolean,
        @Part religion: MultipartBody.Part,
        @Part fatherLName: MultipartBody.Part,
        @Part fatherFName: MultipartBody.Part,
        @Part motherLName: MultipartBody.Part,
        @Part motherFName: MultipartBody.Part,
        @Part provOfBirthFK: MultipartBody.Part,
        @Part provinceOfBirth: MultipartBody.Part,
        @Part provinceName: MultipartBody.Part,
        @Part openElectorate: MultipartBody.Part,
        @Part placeOfBirth: MultipartBody.Part,
        @Part occupationFK: MultipartBody.Part,
        @Part occupation: MultipartBody.Part,
        @Part clan: MultipartBody.Part,
        @Part subClan: MultipartBody.Part,
        @Part address: MultipartBody.Part,
        @Part natID: MultipartBody.Part,
        @Part natIDRecordNo: MultipartBody.Part,
        @Part status: MultipartBody.Part,
        @Part houseFK: MultipartBody.Part,
        @Part houseSortOrder: MultipartBody.Part,
        @Part restHouse: MultipartBody.Part,
        @Part sectionNo: MultipartBody.Part,
        @Part lotNo: MultipartBody.Part,
        @Part streetName: MultipartBody.Part,
        @Part suburbTownSettlement: MultipartBody.Part,
        @Part familyFK: MultipartBody.Part,
        @Part familyNumber: MultipartBody.Part,
        @Part familyName: MultipartBody.Part,
        @Part familySortOrder: MultipartBody.Part,
        @Part dpo: MultipartBody.Part,
        @Part("lat") lat : Double?,
        @Part("long") longitude : Double?,
        @Part posted: MultipartBody.Part,
        @Part comment: MultipartBody.Part,
        @Part electorProfileImageUrl: MultipartBody.Part,
        @Part electorFormImageUrl: MultipartBody.Part,
        @Part formImageBody: MultipartBody.Part,
        @Part enrolmentFormNumber: MultipartBody.Part,
        @Part EnrolmentFormType: MultipartBody.Part,
        @Part("IsManualFormNumber") IsManualFormNumber : Boolean,
        @Part createdBy: MultipartBody.Part,
        @Part tempElectorId: MultipartBody.Part,
        @Part clanId: MultipartBody.Part
    ): Call<Response<ArrayList<FailedVotersList>>>?


    @Multipart
    @POST("Voter/SyncElectorsAndImages")
    fun syncTransferInElectorsAndImages(
        @Part electorID: MultipartBody.Part,
        @Part oldElectorID: MultipartBody.Part,
        @Part locationId: MultipartBody.Part,
        @Part oldLocationId: MultipartBody.Part,
        @Part locationName: MultipartBody.Part,
        @Part wardId: MultipartBody.Part,
        @Part wardName: MultipartBody.Part,
        @Part llgId: MultipartBody.Part,
        @Part llgName: MultipartBody.Part,
        @Part districtId: MultipartBody.Part,
        @Part provinceId: MultipartBody.Part,
        @Part regionId: MultipartBody.Part,
        @Part MKADistrictId: MultipartBody.Part,
        @Part MKALLGId: MultipartBody.Part,
        @Part MKAWardId: MultipartBody.Part,
        @Part MKALocationId: MultipartBody.Part,
        @Part MKAClanId: MultipartBody.Part,
        @Part lName: MultipartBody.Part,
        @Part fName: MultipartBody.Part,
        @Part voterName: MultipartBody.Part,
        @Part gender: MultipartBody.Part,
        @Part dob: MultipartBody.Part,
        @Part("isYOB") isYOB : Boolean,
        @Part("isPWD") isPWD : Boolean,
        @Part religion: MultipartBody.Part,
        @Part fatherLName: MultipartBody.Part,
        @Part fatherFName: MultipartBody.Part,
        @Part motherLName: MultipartBody.Part,
        @Part motherFName: MultipartBody.Part,
        @Part provOfBirthFK: MultipartBody.Part,
        @Part provinceOfBirth: MultipartBody.Part,
        @Part provinceName: MultipartBody.Part,
        @Part openElectorate: MultipartBody.Part,
        @Part placeOfBirth: MultipartBody.Part,
        @Part occupationFK: MultipartBody.Part,
        @Part occupation: MultipartBody.Part,
        @Part clan: MultipartBody.Part,
        @Part subClan: MultipartBody.Part,
        @Part address: MultipartBody.Part,
        @Part natID: MultipartBody.Part,
        @Part natIDRecordNo: MultipartBody.Part,
        @Part status: MultipartBody.Part,
        @Part houseFK: MultipartBody.Part,
        @Part houseSortOrder: MultipartBody.Part,
        @Part restHouse: MultipartBody.Part,
        @Part sectionNo: MultipartBody.Part,
        @Part lotNo: MultipartBody.Part,
        @Part streetName: MultipartBody.Part,
        @Part suburbTownSettlement: MultipartBody.Part,
        @Part familyFK: MultipartBody.Part,
        @Part familyNumber: MultipartBody.Part,
        @Part familyName: MultipartBody.Part,
        @Part familySortOrder: MultipartBody.Part,
        @Part dpo: MultipartBody.Part,
        @Part("lat") lat : Double?,
        @Part("long") longitude : Double?,
        @Part posted: MultipartBody.Part,
        @Part comment: MultipartBody.Part,
        @Part electorProfileImageUrl: MultipartBody.Part,
        @Part electorFormImageUrl: MultipartBody.Part,
        @Part profileImageBody: MultipartBody.Part,
        @Part formImageBody: MultipartBody.Part,
        @Part enrolmentFormNumber: MultipartBody.Part,
        @Part EnrolmentFormType: MultipartBody.Part,
        @Part("IsManualFormNumber") IsManualFormNumber : Boolean,
        @Part createdBy: MultipartBody.Part,
        @Part tempElectorId: MultipartBody.Part,
        @Part transferOutRegionId: MultipartBody.Part,
        @Part transferOutProvinceId: MultipartBody.Part,
        @Part transferOutDistrictId: MultipartBody.Part,
        @Part transferOutLLGId: MultipartBody.Part,
        @Part transferOutWardId: MultipartBody.Part,
        @Part transferOutLocationId: MultipartBody.Part,
        @Part TransferOutMKADistrictId: MultipartBody.Part,
        @Part TransferOutMKALLGId: MultipartBody.Part,
        @Part TransferOutMKAWardId: MultipartBody.Part,
        @Part TransferOutMKALocationId: MultipartBody.Part,
        @Part clanId: MultipartBody.Part
    ): Call<Response<ArrayList<FailedVotersList>>>?

    @Multipart
    @POST("Voter/SyncElectorsAndImages")
    fun syncRejectedTransferInElectorsAndImages(
        @Part electorID: MultipartBody.Part,
        @Part oldElectorID: MultipartBody.Part,
        @Part locationId: MultipartBody.Part,
        @Part oldLocationId: MultipartBody.Part,
        @Part locationName: MultipartBody.Part,
        @Part wardId: MultipartBody.Part,
        @Part wardName: MultipartBody.Part,
        @Part llgId: MultipartBody.Part,
        @Part llgName: MultipartBody.Part,
        @Part districtId: MultipartBody.Part,
        @Part provinceId: MultipartBody.Part,
        @Part regionId: MultipartBody.Part,
        @Part MKADistrictId: MultipartBody.Part,
        @Part MKALLGId: MultipartBody.Part,
        @Part MKAWardId: MultipartBody.Part,
        @Part MKALocationId: MultipartBody.Part,
        @Part MKAClanId: MultipartBody.Part,
        @Part lName: MultipartBody.Part,
        @Part fName: MultipartBody.Part,
        @Part voterName: MultipartBody.Part,
        @Part gender: MultipartBody.Part,
        @Part dob: MultipartBody.Part,
        @Part("isYOB") isYOB : Boolean,
        @Part("isPWD") isPWD : Boolean,
        @Part("isRejected") isRejected : Boolean,
        @Part religion: MultipartBody.Part,
        @Part fatherLName: MultipartBody.Part,
        @Part fatherFName: MultipartBody.Part,
        @Part motherLName: MultipartBody.Part,
        @Part motherFName: MultipartBody.Part,
        @Part provOfBirthFK: MultipartBody.Part,
        @Part provinceOfBirth: MultipartBody.Part,
        @Part provinceName: MultipartBody.Part,
        @Part openElectorate: MultipartBody.Part,
        @Part placeOfBirth: MultipartBody.Part,
        @Part occupationFK: MultipartBody.Part,
        @Part occupation: MultipartBody.Part,
        @Part clan: MultipartBody.Part,
        @Part subClan: MultipartBody.Part,
        @Part address: MultipartBody.Part,
        @Part natID: MultipartBody.Part,
        @Part natIDRecordNo: MultipartBody.Part,
        @Part status: MultipartBody.Part,
        @Part houseFK: MultipartBody.Part,
        @Part houseSortOrder: MultipartBody.Part,
        @Part restHouse: MultipartBody.Part,
        @Part sectionNo: MultipartBody.Part,
        @Part lotNo: MultipartBody.Part,
        @Part streetName: MultipartBody.Part,
        @Part suburbTownSettlement: MultipartBody.Part,
        @Part familyFK: MultipartBody.Part,
        @Part familyNumber: MultipartBody.Part,
        @Part familyName: MultipartBody.Part,
        @Part familySortOrder: MultipartBody.Part,
        @Part dpo: MultipartBody.Part,
        @Part("lat") lat : Double?,
        @Part("long") longitude : Double?,
        @Part posted: MultipartBody.Part,
        @Part comment: MultipartBody.Part,
        @Part electorProfileImageUrl: MultipartBody.Part,
        @Part electorFormImageUrl: MultipartBody.Part,
        @Part profileImageBody: MultipartBody.Part,
        @Part formImageBody: MultipartBody.Part,
        @Part enrolmentFormNumber: MultipartBody.Part,
        @Part EnrolmentFormType: MultipartBody.Part,
        @Part("IsManualFormNumber") IsManualFormNumber : Boolean,
        @Part createdBy: MultipartBody.Part,
        @Part tempElectorId: MultipartBody.Part,
        @Part transferOutRegionId: MultipartBody.Part,
        @Part transferOutProvinceId: MultipartBody.Part,
        @Part transferOutDistrictId: MultipartBody.Part,
        @Part transferOutLLGId: MultipartBody.Part,
        @Part transferOutWardId: MultipartBody.Part,
        @Part transferOutLocationId: MultipartBody.Part,
        @Part TransferOutMKADistrictId: MultipartBody.Part,
        @Part TransferOutMKALLGId: MultipartBody.Part,
        @Part TransferOutMKAWardId: MultipartBody.Part,
        @Part TransferOutMKALocationId: MultipartBody.Part,
        @Part clanId: MultipartBody.Part,
    ): Call<Response<ArrayList<FailedVotersList>>>?

    @Multipart
    @POST("Voter/SyncElectorsAndImages")
    fun syncTransferInElectorsAndImagesWithOutProfile(
        @Part electorID: MultipartBody.Part,
        @Part oldElectorID: MultipartBody.Part,
        @Part locationId: MultipartBody.Part,
        @Part oldLocationId: MultipartBody.Part,
        @Part locationName: MultipartBody.Part,
        @Part wardId: MultipartBody.Part,
        @Part wardName: MultipartBody.Part,
        @Part llgId: MultipartBody.Part,
        @Part llgName: MultipartBody.Part,
        @Part districtId: MultipartBody.Part,
        @Part provinceId: MultipartBody.Part,
        @Part regionId: MultipartBody.Part,
        @Part MKADistrictId: MultipartBody.Part,
        @Part MKALLGId: MultipartBody.Part,
        @Part MKAWardId: MultipartBody.Part,
        @Part MKALocationId: MultipartBody.Part,
        @Part MKAClanId: MultipartBody.Part,
        @Part lName: MultipartBody.Part,
        @Part fName: MultipartBody.Part,
        @Part voterName: MultipartBody.Part,
        @Part gender: MultipartBody.Part,
        @Part dob: MultipartBody.Part,
        @Part("isYOB") isYOB : Boolean,
        @Part("isPWD") isPWD : Boolean,
        @Part religion: MultipartBody.Part,
        @Part fatherLName: MultipartBody.Part,
        @Part fatherFName: MultipartBody.Part,
        @Part motherLName: MultipartBody.Part,
        @Part motherFName: MultipartBody.Part,
        @Part provOfBirthFK: MultipartBody.Part,
        @Part provinceOfBirth: MultipartBody.Part,
        @Part provinceName: MultipartBody.Part,
        @Part openElectorate: MultipartBody.Part,
        @Part placeOfBirth: MultipartBody.Part,
        @Part occupationFK: MultipartBody.Part,
        @Part occupation: MultipartBody.Part,
        @Part clan: MultipartBody.Part,
        @Part subClan: MultipartBody.Part,
        @Part address: MultipartBody.Part,
        @Part natID: MultipartBody.Part,
        @Part natIDRecordNo: MultipartBody.Part,
        @Part status: MultipartBody.Part,
        @Part houseFK: MultipartBody.Part,
        @Part houseSortOrder: MultipartBody.Part,
        @Part restHouse: MultipartBody.Part,
        @Part sectionNo: MultipartBody.Part,
        @Part lotNo: MultipartBody.Part,
        @Part streetName: MultipartBody.Part,
        @Part suburbTownSettlement: MultipartBody.Part,
        @Part familyFK: MultipartBody.Part,
        @Part familyNumber: MultipartBody.Part,
        @Part familyName: MultipartBody.Part,
        @Part familySortOrder: MultipartBody.Part,
        @Part dpo: MultipartBody.Part,
        @Part("lat") lat : Double?,
        @Part("long") longitude : Double?,
        @Part posted: MultipartBody.Part,
        @Part comment: MultipartBody.Part,
        @Part electorProfileImageUrl: MultipartBody.Part,
        @Part electorFormImageUrl: MultipartBody.Part,
        @Part formImageBody: MultipartBody.Part,
        @Part enrolmentFormNumber: MultipartBody.Part,
        @Part EnrolmentFormType: MultipartBody.Part,
        @Part("IsManualFormNumber") IsManualFormNumber : Boolean,
        @Part createdBy: MultipartBody.Part,
        @Part tempElectorId: MultipartBody.Part,
        @Part transferOutRegionId: MultipartBody.Part,
        @Part transferOutProvinceId: MultipartBody.Part,
        @Part transferOutDistrictId: MultipartBody.Part,
        @Part transferOutLLGId: MultipartBody.Part,
        @Part transferOutWardId: MultipartBody.Part,
        @Part transferOutLocationId: MultipartBody.Part,
        @Part TransferOutMKADistrictId: MultipartBody.Part,
        @Part TransferOutMKALLGId: MultipartBody.Part,
        @Part TransferOutMKAWardId: MultipartBody.Part,
        @Part TransferOutMKALocationId: MultipartBody.Part,
        @Part clanId: MultipartBody.Part,
    ): Call<Response<ArrayList<FailedVotersList>>>?

    @Multipart
    @POST("Voter/SyncElectorsAndImages")
    fun syncRejectedTransferInElectorsAndImagesWithOutProfile(
        @Part electorID: MultipartBody.Part,
        @Part oldElectorID: MultipartBody.Part,
        @Part locationId: MultipartBody.Part,
        @Part oldLocationId: MultipartBody.Part,
        @Part locationName: MultipartBody.Part,
        @Part wardId: MultipartBody.Part,
        @Part wardName: MultipartBody.Part,
        @Part llgId: MultipartBody.Part,
        @Part llgName: MultipartBody.Part,
        @Part districtId: MultipartBody.Part,
        @Part provinceId: MultipartBody.Part,
        @Part regionId: MultipartBody.Part,
        @Part MKADistrictId: MultipartBody.Part,
        @Part MKALLGId: MultipartBody.Part,
        @Part MKAWardId: MultipartBody.Part,
        @Part MKALocationId: MultipartBody.Part,
        @Part MKAClanId: MultipartBody.Part,
        @Part lName: MultipartBody.Part,
        @Part fName: MultipartBody.Part,
        @Part voterName: MultipartBody.Part,
        @Part gender: MultipartBody.Part,
        @Part dob: MultipartBody.Part,
        @Part("isYOB") isYOB : Boolean,
        @Part("isPWD") isPWD : Boolean,
        @Part("isRejected") isRejected : Boolean,
        @Part religion: MultipartBody.Part,
        @Part fatherLName: MultipartBody.Part,
        @Part fatherFName: MultipartBody.Part,
        @Part motherLName: MultipartBody.Part,
        @Part motherFName: MultipartBody.Part,
        @Part provOfBirthFK: MultipartBody.Part,
        @Part provinceOfBirth: MultipartBody.Part,
        @Part provinceName: MultipartBody.Part,
        @Part openElectorate: MultipartBody.Part,
        @Part placeOfBirth: MultipartBody.Part,
        @Part occupationFK: MultipartBody.Part,
        @Part occupation: MultipartBody.Part,
        @Part clan: MultipartBody.Part,
        @Part subClan: MultipartBody.Part,
        @Part address: MultipartBody.Part,
        @Part natID: MultipartBody.Part,
        @Part natIDRecordNo: MultipartBody.Part,
        @Part status: MultipartBody.Part,
        @Part houseFK: MultipartBody.Part,
        @Part houseSortOrder: MultipartBody.Part,
        @Part restHouse: MultipartBody.Part,
        @Part sectionNo: MultipartBody.Part,
        @Part lotNo: MultipartBody.Part,
        @Part streetName: MultipartBody.Part,
        @Part suburbTownSettlement: MultipartBody.Part,
        @Part familyFK: MultipartBody.Part,
        @Part familyNumber: MultipartBody.Part,
        @Part familyName: MultipartBody.Part,
        @Part familySortOrder: MultipartBody.Part,
        @Part dpo: MultipartBody.Part,
        @Part("lat") lat : Double?,
        @Part("long") longitude : Double?,
        @Part posted: MultipartBody.Part,
        @Part comment: MultipartBody.Part,
        @Part electorProfileImageUrl: MultipartBody.Part,
        @Part electorFormImageUrl: MultipartBody.Part,
        @Part formImageBody: MultipartBody.Part,
        @Part enrolmentFormNumber: MultipartBody.Part,
        @Part EnrolmentFormType: MultipartBody.Part,
        @Part("IsManualFormNumber") IsManualFormNumber : Boolean,
        @Part createdBy: MultipartBody.Part,
        @Part tempElectorId: MultipartBody.Part,
        @Part transferOutRegionId: MultipartBody.Part,
        @Part transferOutProvinceId: MultipartBody.Part,
        @Part transferOutDistrictId: MultipartBody.Part,
        @Part transferOutLLGId: MultipartBody.Part,
        @Part transferOutWardId: MultipartBody.Part,
        @Part transferOutLocationId: MultipartBody.Part,
        @Part TransferOutMKADistrictId: MultipartBody.Part,
        @Part TransferOutMKALLGId: MultipartBody.Part,
        @Part TransferOutMKAWardId: MultipartBody.Part,
        @Part TransferOutMKALocationId: MultipartBody.Part,
        @Part clanId: MultipartBody.Part,
    ): Call<Response<ArrayList<FailedVotersList>>>?

    @Multipart
    @POST("Voter/SyncElectorsAndImages")
    fun syncElectorsAndVoterImage(
        @Part electorID: MultipartBody.Part,
        @Part oldElectorID: MultipartBody.Part,
        @Part locationId: MultipartBody.Part,
        @Part oldLocationId: MultipartBody.Part,
        @Part locationName: MultipartBody.Part,
        @Part wardId: MultipartBody.Part,
        @Part wardName: MultipartBody.Part,
        @Part llgId: MultipartBody.Part,
        @Part llgName: MultipartBody.Part,
        @Part districtId: MultipartBody.Part,
        @Part provinceId: MultipartBody.Part,
        @Part regionId: MultipartBody.Part,
        @Part MKADistrictId: MultipartBody.Part,
        @Part MKALLGId: MultipartBody.Part,
        @Part MKAWardId: MultipartBody.Part,
        @Part MKALocationId: MultipartBody.Part,
        @Part MKAClanId: MultipartBody.Part,
        @Part lName: MultipartBody.Part,
        @Part fName: MultipartBody.Part,
        @Part voterName: MultipartBody.Part,
        @Part gender: MultipartBody.Part,
        @Part dob: MultipartBody.Part,
        @Part("isYOB") isYOB : Boolean,
        @Part("isPWD") isPWD : Boolean,
        @Part religion: MultipartBody.Part,
        @Part fatherLName: MultipartBody.Part,
        @Part fatherFName: MultipartBody.Part,
        @Part motherLName: MultipartBody.Part,
        @Part motherFName: MultipartBody.Part,
        @Part provOfBirthFK: MultipartBody.Part,
        @Part provinceOfBirth: MultipartBody.Part,
        @Part provinceName: MultipartBody.Part,
        @Part openElectorate: MultipartBody.Part,
        @Part placeOfBirth: MultipartBody.Part,
        @Part occupationFK: MultipartBody.Part,
        @Part occupation: MultipartBody.Part,
        @Part clan: MultipartBody.Part,
        @Part subClan: MultipartBody.Part,
        @Part address: MultipartBody.Part,
        @Part natID: MultipartBody.Part,
        @Part natIDRecordNo: MultipartBody.Part,
        @Part status: MultipartBody.Part,
        @Part houseFK: MultipartBody.Part,
        @Part houseSortOrder: MultipartBody.Part,
        @Part restHouse: MultipartBody.Part,
        @Part sectionNo: MultipartBody.Part,
        @Part lotNo: MultipartBody.Part,
        @Part streetName: MultipartBody.Part,
        @Part suburbTownSettlement: MultipartBody.Part,
        @Part familyFK: MultipartBody.Part,
        @Part familyNumber: MultipartBody.Part,
        @Part familyName: MultipartBody.Part,
        @Part familySortOrder: MultipartBody.Part,
        @Part dpo: MultipartBody.Part,
        @Part("lat") lat : Double?,
        @Part("long") longitude : Double?,
        @Part posted: MultipartBody.Part,
        @Part comment: MultipartBody.Part,
        @Part electorProfileImageUrl: MultipartBody.Part,
        @Part electorFormImageUrl: MultipartBody.Part,
        @Part profileImageBody: MultipartBody.Part,
        @Part enrolmentFormNumber: MultipartBody.Part,
        @Part EnrolmentFormType: MultipartBody.Part,
        @Part("IsManualFormNumber") IsManualFormNumber : Boolean,
        @Part createdBy: MultipartBody.Part,
        @Part tempElectorId: MultipartBody.Part,
        @Part clanId: MultipartBody.Part
    ): Call<Response<ArrayList<FailedVotersList>>>?

    @GET("General/GetActiveProvinceList")
    fun callGetActiveProvinceListAPI():
            Call<Response<ArrayList<ProvinceList>>>

    @POST("General/UpdateUserGeoStructure")
    fun callUpdateGeoStructureAPI(@Body inputParam: JsonObject):
            Call<Response<Any>>

    @POST("Voter/GetUpdatedVoterList")
    fun callGetUpdatedVoterListAPI(@Body inputParam: JsonObject):
            Call<TransferInVotersModel>

    @POST("General/GetDeviceOperatorReport")
    fun callGetDeviceOperatorReportAPI(@Body inputParam: JsonObject):
            Call<DeviceOperatorReportModel>

    @POST("General/GetStatusAndDOBDiscrepancyReport")
    fun callGetVoterStatusAndDOBDiscrepancyReportAPI(@Body inputParam: JsonObject):
            Call<StatusAndDobDiscrepancyReportModel>

    @GET("General/GetSetting")
    fun callGeneralSettingsAPI():
            Call<GeneralSettingsModel>

    @POST("General/GetSyncStatus")
    fun callCheckSyncedElectorsAPI(@Body syncDataVotersListArray: JsonArray): Call<SyncedVoterStatusModel>

    @GET("General/GetMKAGeoList")
    fun callMKAGeoListAPI(): Call<MKAGeoListModel>

    @GET("General/GetMKAClanList")
    fun callMKAClanListAPI(): Call<MKAClanListModel>

    @GET("General/GetGeoClanList")
    fun callGeoClanListAPI(): Call<ClanListModel>

    // =======================================================
    // Phase 1: Modular Idempotent Sync V2 Endpoints
    // =======================================================

    @POST("v2/Sync/Batch")
    fun callSyncV2BatchAPI(@Body batchRequest: SyncV2BatchRequest): Call<SyncV2BatchResponse>

    @POST("v2/Sync/New")
    fun callSyncV2NewAPI(@Body batchRequest: SyncV2BatchRequest): Call<SyncV2BatchResponse>

    @POST("v2/Sync/Update")
    fun callSyncV2UpdateAPI(@Body batchRequest: SyncV2BatchRequest): Call<SyncV2BatchResponse>

    @POST("v2/Sync/TransferIn")
    fun callSyncV2TransferInAPI(@Body batchRequest: SyncV2BatchRequest): Call<SyncV2BatchResponse>

    @POST("v2/Sync/StatusChange")
    fun callSyncV2StatusChangeAPI(@Body batchRequest: SyncV2BatchRequest): Call<SyncV2BatchResponse>

    @Multipart
    @POST("v2/Sync/UploadImage")
    fun callSyncV2UploadImageAPI(
        @Part file: MultipartBody.Part,
        @Part imageType: MultipartBody.Part,
        @Part electorId: MultipartBody.Part,
        @Part uploadId: MultipartBody.Part
    ): Call<Response<String>>

    @GET("v2/Sync/Status/{uploadId}")
    fun callSyncV2GetStatusAPI(@Path("uploadId") uploadId: String): Call<Response<SyncV2RecordResult>>

    // =======================================================
    // Phase 2: SevisPass National Digital ID Verification
    // =======================================================

    @POST("v2/SevisPass/Verify")
    fun callVerifySevisPassAPI(@Body request: SevisPassVerifyRequest): Call<Response<SevisPassVerifyResponse>>
}





