package com.pngelectoral.datasync

import ApiConstants
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.json.JsonReadFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.pngelectoral.R
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.retrofit.*
import com.pngelectoral.utility.CommonMethods
import com.pngelectoral.utility.sharedPreference.SecuredSharedPreferenceWrapper
import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream
import kotlin.math.abs

class DownloadNewVotersSyncService : Service() {

    lateinit var dataBaseInstance: AppDatabase
    private var locationPageNumber = 1
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper
    private var provinceFileCount: Int = 0
    private var zipFilePath = ""
    private var voterDataFiles: ArrayList<File> = ArrayList()


    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        registerReceiver(stopServiceReceiver, IntentFilter(ApiConstants.STOP_SERVICE))

        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(this, "png_electoral")

        locationPageNumber = securedSharedPreferenceWrapper.getInt(ApiConstants.LOCATION_PAGE_NO, 1)
        zipFilePath = intent?.getStringExtra(ApiConstants.ZipFilePath)!!
        val files = File(zipFilePath).listFiles()
        for (tempFile in files!!)
            voterDataFiles.add(tempFile)

        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase

        startNotificationWithNotification()
        if (!securedSharedPreferenceWrapper.getBoolean(
                ApiConstants.OCCUPATION_DOWNLOADED,
                false
            )
        ) {
            callGetOccupationListAPI()
        } else {
            if (!securedSharedPreferenceWrapper.getBoolean(
                    ApiConstants.GEO_LIST_DOWNLOADED,
                    false
                )
            )
                callGetGeoListAPI()
        }
        copyVotersData()
        callLocationsAPI()
        /**Call api's for mkageolist and mkaclanlist when download service started.*/
        callMKAGeoListApi()
        callMKAClanListApi()
        callGeoClanListApi()
        return START_NOT_STICKY
    }

    /**Method to call api for mkageolist.*/
    private fun callMKAGeoListApi() {
        val call = ApiClient.getLocationsRetrofitInterface()?.callMKAGeoListAPI()
        WSClient<MKAGeoListModel>().request(
            this,
            ApiConstants.DPO_GET_MKA_GEO_LIST,
            call,
            object :
                ISuccessHandler<MKAGeoListModel> {
                override fun successResponse(
                    requestCode: Int,
                    mResponse: MKAGeoListModel
                ) {
                    if (mResponse.status == true && mResponse.data != null) {
                        val allGeoListData = mResponse.data
                        if (allGeoListData.isNotEmpty()) {
                            downloadMKAGeoList(allGeoListData, allGeoListData.size)
                        } else {
                            securedSharedPreferenceWrapper.putBoolean(
                                ApiConstants.GEO_LIST_DATA_DOWNLOADED,
                                true
                            )

                            if (securedSharedPreferenceWrapper.getBoolean(
                                    ApiConstants.DPO_VOTERS_DATA_DOWNLOADED,
                                    false
                                ) && securedSharedPreferenceWrapper.getBoolean(
                                    ApiConstants.LOCATION_DATA_DOWNLOADED,
                                    false
                                ) && securedSharedPreferenceWrapper.getBoolean(
                                    ApiConstants.CLAN_LIST_DATA_DOWNLOADED,
                                    false
                                )
                            ) {
                                stopNotification()
                                securedSharedPreferenceWrapper.putInt(
                                    ApiConstants.DOWNLOADED_PERCENTAGE,
                                    0
                                )
                                securedSharedPreferenceWrapper.putBoolean(
                                    ApiConstants.DOWNLOAD_IN_PROGRESS,
                                    false
                                )
                                securedSharedPreferenceWrapper.putBoolean(
                                    ApiConstants.DPO_SETUP_COMPLETED,
                                    true
                                )
                                val intent = Intent(ApiConstants.ALL_DPO_DATA_COMPLETED_BROADCAST)
                                sendBroadcast(intent)
                            }

                        }
                    }
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {

                }
            })
    }

    /**Method to add data into database table mkageolist.*/
    private fun downloadMKAGeoList(
        geoList: ArrayList<MKAGeoListModel.GeoListData>,
        totalCount: Int
    ) {

        val exceptionHandler = CoroutineExceptionHandler { _, t ->
            t.printStackTrace()
        }
        GlobalScope.launch(Dispatchers.Main + exceptionHandler) {
            var result: Int
            withContext(Dispatchers.IO) {
                geoList.forEach { geoModel ->
                    val model = MKAGeoListData()
                    model.actionBy = geoModel.actionBy
                    model.districtId = geoModel.districtId
                    model.districtName = geoModel.districtName
                    model.llGs = Gson().toJson(geoModel.llGs)
                    dataBaseInstance.geoListDao().insert(model)
                }
                result = dataBaseInstance.geoListDao().all.size
            }

            val progressIntent = Intent(ApiConstants.SINGLE_PAGE_DATA_COMPLETED)
            progressIntent.putExtra("Total_Records", totalCount)
            progressIntent.putExtra("Downloaded_Records", result)
            sendBroadcast(progressIntent)

            if (result == totalCount) {

                securedSharedPreferenceWrapper.putBoolean(
                    ApiConstants.GEO_LIST_DATA_DOWNLOADED,
                    true
                )

                if (securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.DPO_VOTERS_DATA_DOWNLOADED,
                        false
                    ) && securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.LOCATION_DATA_DOWNLOADED,
                        false
                    ) && securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.CLAN_LIST_DATA_DOWNLOADED,
                        false
                    )
                ) {
                    stopNotification()
                    securedSharedPreferenceWrapper.putInt(ApiConstants.DOWNLOADED_PERCENTAGE, 0)
                    securedSharedPreferenceWrapper.putBoolean(
                        ApiConstants.DOWNLOAD_IN_PROGRESS,
                        false
                    )
                    securedSharedPreferenceWrapper.putBoolean(
                        ApiConstants.DPO_SETUP_COMPLETED,
                        true
                    )
                    val intent = Intent(ApiConstants.ALL_DPO_DATA_COMPLETED_BROADCAST)
                    sendBroadcast(intent)
                }


            }

        }

    }

    /**Method to call api for mkaclanlist.*/
    private fun callMKAClanListApi() {
        val call = ApiClient.getLocationsRetrofitInterface()?.callMKAClanListAPI()
        WSClient<MKAClanListModel>().request(
            this,
            ApiConstants.DPO_GET_MKA_CLAN_LIST,
            call,
            object :
                ISuccessHandler<MKAClanListModel> {
                override fun successResponse(
                    requestCode: Int,
                    mResponse: MKAClanListModel
                ) {
                    if (mResponse.status == true && mResponse.data != null) {
                        val allClanListData = mResponse.data
                        if (allClanListData.isNotEmpty()) {
                            downloadMKAClanList(allClanListData, allClanListData.size)
                        } else {
                            securedSharedPreferenceWrapper.putBoolean(
                                ApiConstants.CLAN_LIST_DATA_DOWNLOADED,
                                true
                            )

                            if (securedSharedPreferenceWrapper.getBoolean(
                                    ApiConstants.DPO_VOTERS_DATA_DOWNLOADED,
                                    false
                                ) && securedSharedPreferenceWrapper.getBoolean(
                                    ApiConstants.LOCATION_DATA_DOWNLOADED,
                                    false
                                ) && securedSharedPreferenceWrapper.getBoolean(
                                    ApiConstants.GEO_LIST_DOWNLOADED,
                                    false
                                )
                            ) {
                                stopNotification()
                                securedSharedPreferenceWrapper.putInt(
                                    ApiConstants.DOWNLOADED_PERCENTAGE,
                                    0
                                )
                                securedSharedPreferenceWrapper.putBoolean(
                                    ApiConstants.DOWNLOAD_IN_PROGRESS,
                                    false
                                )
                                securedSharedPreferenceWrapper.putBoolean(
                                    ApiConstants.DPO_SETUP_COMPLETED,
                                    true
                                )
                                val intent = Intent(ApiConstants.ALL_DPO_DATA_COMPLETED_BROADCAST)
                                sendBroadcast(intent)
                            }

                        }
                    }
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {

                }
            })
    }

    /**Method to add data into database table mkaclanlist*/
    private fun downloadMKAClanList(
        clanList: ArrayList<MKAClanListModel.ClanData>,
        totalCount: Int
    ) {
        val exceptionHandler = CoroutineExceptionHandler { _, t ->
            t.printStackTrace()
        }
        GlobalScope.launch(Dispatchers.Main + exceptionHandler) {
            var result: Int
            withContext(Dispatchers.IO) {
                clanList.forEach { clanModel ->
                    val model = MKAClanListData()
                    model.clanId = clanModel.clanId
                    model.clanName = clanModel.clanName
                    model.description = clanModel.description
                    model.responseCode = clanModel.responseCode
                    dataBaseInstance.clanListDao().insert(model)
                }
                result = dataBaseInstance.clanListDao().all.size
            }

            val progressIntent = Intent(ApiConstants.SINGLE_PAGE_DATA_COMPLETED)
            progressIntent.putExtra("Total_Records", totalCount)
            progressIntent.putExtra("Downloaded_Records", result)
            sendBroadcast(progressIntent)

            if (result == totalCount) {

                securedSharedPreferenceWrapper.putBoolean(
                    ApiConstants.CLAN_LIST_DATA_DOWNLOADED,
                    true
                )

                if (securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.DPO_VOTERS_DATA_DOWNLOADED,
                        false
                    ) && securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.LOCATION_DATA_DOWNLOADED,
                        false
                    ) && securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.GEO_LIST_DOWNLOADED,
                        false
                    )
                ) {
                    stopNotification()
                    securedSharedPreferenceWrapper.putInt(ApiConstants.DOWNLOADED_PERCENTAGE, 0)
                    securedSharedPreferenceWrapper.putBoolean(
                        ApiConstants.DOWNLOAD_IN_PROGRESS,
                        false
                    )
                    securedSharedPreferenceWrapper.putBoolean(
                        ApiConstants.DPO_SETUP_COMPLETED,
                        true
                    )
                    val intent = Intent(ApiConstants.ALL_DPO_DATA_COMPLETED_BROADCAST)
                    sendBroadcast(intent)
                }


            }

        }

    }

    /**Method to call api for geoclanlist.*/
    private fun callGeoClanListApi() {
        val call = ApiClient.getLocationsRetrofitInterface()?.callGeoClanListAPI()
        WSClient<ClanListModel>().request(
            this,
            ApiConstants.DPO_GET_GEO_CLAN_LIST,
            call,
            object :
                ISuccessHandler<ClanListModel> {
                override fun successResponse(
                    requestCode: Int,
                    mResponse: ClanListModel
                ) {
                    if (mResponse.status == true && mResponse.data != null) {
                        val allClanListData = mResponse.data
                        if (allClanListData.isNotEmpty()) {
                            downloadGeoClanList(allClanListData, allClanListData.size)
                        } else {
                            securedSharedPreferenceWrapper.putBoolean(
                                ApiConstants.CLAN_LIST_DATA_DOWNLOADED,
                                true
                            )

                            if (securedSharedPreferenceWrapper.getBoolean(
                                    ApiConstants.DPO_VOTERS_DATA_DOWNLOADED,
                                    false
                                ) && securedSharedPreferenceWrapper.getBoolean(
                                    ApiConstants.LOCATION_DATA_DOWNLOADED,
                                    false
                                ) && securedSharedPreferenceWrapper.getBoolean(
                                    ApiConstants.GEO_LIST_DOWNLOADED,
                                    false
                                )
                            ) {
                                stopNotification()
                                securedSharedPreferenceWrapper.putInt(
                                    ApiConstants.DOWNLOADED_PERCENTAGE,
                                    0
                                )
                                securedSharedPreferenceWrapper.putBoolean(
                                    ApiConstants.DOWNLOAD_IN_PROGRESS,
                                    false
                                )
                                securedSharedPreferenceWrapper.putBoolean(
                                    ApiConstants.DPO_SETUP_COMPLETED,
                                    true
                                )
                                val intent = Intent(ApiConstants.ALL_DPO_DATA_COMPLETED_BROADCAST)
                                sendBroadcast(intent)
                            }

                        }
                    }
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {

                }
            })
    }

    /**Method to add data into database table clanlist*/
    private fun downloadGeoClanList(
        clanList: ArrayList<ClanListModel.ClanData>,
        totalCount: Int
    ) {
        val exceptionHandler = CoroutineExceptionHandler { _, t ->
            t.printStackTrace()
        }
        GlobalScope.launch(Dispatchers.Main + exceptionHandler) {
            var result: Int
            withContext(Dispatchers.IO) {
                clanList.forEach { clanModel ->
                    val model = ClanListData()
                    model.clanId = clanModel.clanId
                    model.clanName = clanModel.clanName
                    model.description = clanModel.description
                    model.wardId = clanModel.wardId
                    dataBaseInstance.geoClanListDao().insert(model)
                }
                result = dataBaseInstance.geoClanListDao().all.size
            }

            val progressIntent = Intent(ApiConstants.SINGLE_PAGE_DATA_COMPLETED)
            progressIntent.putExtra("Total_Records", totalCount)
            progressIntent.putExtra("Downloaded_Records", result)
            sendBroadcast(progressIntent)

            if (result == totalCount) {

                securedSharedPreferenceWrapper.putBoolean(
                    ApiConstants.CLAN_LIST_DATA_DOWNLOADED,
                    true
                )

                if (securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.DPO_VOTERS_DATA_DOWNLOADED,
                        false
                    ) && securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.LOCATION_DATA_DOWNLOADED,
                        false
                    ) && securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.GEO_LIST_DOWNLOADED,
                        false
                    )
                ) {
                    stopNotification()
                    securedSharedPreferenceWrapper.putInt(ApiConstants.DOWNLOADED_PERCENTAGE, 0)
                    securedSharedPreferenceWrapper.putBoolean(
                        ApiConstants.DOWNLOAD_IN_PROGRESS,
                        false
                    )
                    securedSharedPreferenceWrapper.putBoolean(
                        ApiConstants.DPO_SETUP_COMPLETED,
                        true
                    )
                    val intent = Intent(ApiConstants.ALL_DPO_DATA_COMPLETED_BROADCAST)
                    sendBroadcast(intent)
                }


            }

        }

    }

    private fun copyVotersData() {

        if (provinceFileCount == voterDataFiles.size) {
            allVotersDownloaded()
        } else {
            val voterDataFile = voterDataFiles[provinceFileCount]
            if (voterDataFile.exists()) {
                val exceptionHandler = CoroutineExceptionHandler { _, t ->
                    t.printStackTrace()
                }
                GlobalScope.launch(Dispatchers.Main + exceptionHandler) {
                    val result: List<NewVotersList> = withContext(Dispatchers.IO) {
                        getDownloadedTransferInVotersList(voterDataFile)
                    }
                    storeDataAndFetchFromAnotherFile(result)
                }
            } else {
                allVotersDownloaded()
            }
        }


    }

    private fun allVotersDownloaded() {
        securedSharedPreferenceWrapper.putBoolean(
            ApiConstants.DPO_VOTERS_DATA_DOWNLOADED,
            true
        )

        if (securedSharedPreferenceWrapper.getBoolean(
                ApiConstants.LOCATION_DATA_DOWNLOADED,
                false
            ) && securedSharedPreferenceWrapper.getBoolean(
                ApiConstants.GEO_LIST_DATA_DOWNLOADED,
                false
            ) && securedSharedPreferenceWrapper.getBoolean(
                ApiConstants.CLAN_LIST_DATA_DOWNLOADED,
                false
            )
        ) {
            stopNotification()
            securedSharedPreferenceWrapper.putInt(ApiConstants.DOWNLOADED_PERCENTAGE, 0)
            securedSharedPreferenceWrapper.putBoolean(
                ApiConstants.DOWNLOAD_IN_PROGRESS,
                false
            )
            securedSharedPreferenceWrapper.putBoolean(ApiConstants.DPO_SETUP_COMPLETED, true)
            val intent = Intent(ApiConstants.ALL_DPO_DATA_COMPLETED_BROADCAST)
            sendBroadcast(intent)
        }
    }

    private fun storeDataAndFetchFromAnotherFile(result: List<NewVotersList>) {
        val exceptionHandler = CoroutineExceptionHandler { _, t ->
            t.printStackTrace()
        }
        GlobalScope.launch(Dispatchers.Main + exceptionHandler) {
            withContext(Dispatchers.IO) {
                val downloadedVotersList: ArrayList<VotersList> = ArrayList()
                for (i in result.indices) {
                    val voterToInsert = getVoterToInsertFromFile(result[i])
                    downloadedVotersList.add(voterToInsert)
                }
                dataBaseInstance.votersListDao().insertAll(downloadedVotersList)
            }
            provinceFileCount++
//            securedSharedPreferenceWrapper.putInt(
//                ApiConstants.PROVINCE_FILE_NUMBER,
//                provinceFileCount
//            )
            copyVotersData()
        }


    }

    private fun getVoterToInsertFromFile(newVoter: NewVotersList): VotersList {
        val voter = VotersList()
        voter.tempElectorId = abs(System.currentTimeMillis().toInt())
        if (newVoter.voterName == null)
            voter.voterName =
                CommonMethods.getVoterName(newVoter)
        voter.electorId = newVoter.electorId
        voter.oldElectorID = newVoter.oldElectorID
        voter.locationId = newVoter.locationId
        voter.oldLocationId = newVoter.oldLocationId
        voter.locationName = newVoter.locationName
        voter.wardId = newVoter.wardId
        voter.wardName = newVoter.wardName
        voter.llgId = newVoter.llgId
        voter.llgName = newVoter.llgName
        voter.districtId = newVoter.districtId
        voter.provinceId = newVoter.provinceId
        voter.regionId = newVoter.regionId
        voter.lName = newVoter.lName
        voter.fName = newVoter.fName
        voter.gender = newVoter.gender
        voter.dob = newVoter.dob
        voter.isYOB = newVoter.isYOB
        voter.isProvisional = false
        voter.isPWD = newVoter.isPWD
        voter.religion = newVoter.religion
        voter.fatherLName = newVoter.fatherLName
        voter.fatherFName = newVoter.fatherFName
        voter.motherLName = newVoter.motherLName
        voter.motherFName = newVoter.motherFName
        voter.provOfBirthFK = newVoter.provOfBirthFK
        voter.provinceOfBirth = newVoter.provinceOfBirth
        voter.provinceName = newVoter.provinceName
        voter.districtName = newVoter.districtName
        voter.placeOfBirth = newVoter.placeOfBirth
        voter.occupationFK = newVoter.occupationFK
        voter.occupation = newVoter.occupation
        voter.clanFK = newVoter.clanFK
        voter.clan = newVoter.clan
        voter.subClan = newVoter.subClan
        voter.address = newVoter.address
        voter.natID = newVoter.natID
        voter.natIDRecordNo = newVoter.natIDRecordNo
        voter.status = newVoter.status
        voter.houseFK = newVoter.houseFK
        voter.houseSortOrder = newVoter.houseSortOrder
        voter.restHouse = newVoter.restHouse
        voter.sectionNo = newVoter.sectionNo
        voter.lotNo = newVoter.lotNo
        voter.streetName = newVoter.streetName
        voter.suburbTownSettlement = newVoter.suburbTownSettlement
        voter.familyFK = newVoter.familyFK
        voter.familySortOrder = newVoter.familySortOrder
        voter.familyNumber = newVoter.familyNumber
        voter.familyName = newVoter.familyName
        voter.dpo = newVoter.dpo
        voter.lat = newVoter.lat
        voter.longitude = newVoter.Long
        voter.posted = newVoter.posted
        voter.updatedDate = ""
        voter.comment = newVoter.comment
        voter.electorProfileImage = newVoter.electorProfileImage
        voter.electorProfileImageUrl = newVoter.electorProfileImageUrl
        voter.electorFormImage = newVoter.electorFormImage
        voter.electorFormImageUrl = newVoter.electorFormImageUrl
        voter.electorProfileImageFile = newVoter.electorProfileImageFile
        voter.electorFormImageFile = newVoter.electorFormImageFile
        voter.enrolmentFormNumber = newVoter.enrolmentFormNumber
        voter.createdBy = newVoter.createdBy
        voter.isSyncToServer = 1
        voter.isDataUploaded = 1
        voter.isImageUploaded = 1
        voter.updatedDateTime = 1
        voter.syncStatus = 0
        voter.rejectRecordUpdated = 0
        voter.syncDateTime = ""
        voter.duplicateId = 0
        voter.transferOutRegionId = 0
        voter.transferOutProvinceId = 0
        voter.transferOutDistrictId = 0
        voter.transferOutLLGId = 0
        voter.transferOutWardId = 0
        voter.transferOutLocationId = 0
        voter.transferInRegionId = newVoter.transferInRegionId
        voter.transferInProvinceId = newVoter.transferInProvinceId
        voter.transferInDistrictId = newVoter.transferInDistrictId
        voter.transferInLLGId = newVoter.transferInLLGId
        voter.transferInWardId = newVoter.transferInWardId
        voter.transferInLocationId = newVoter.transferInLocationId
        voter.errorMessage = newVoter.errorMessage
        voter.rejectedReason = ""
        voter.isRejected = false
        voter.isHeader = false
        /**Getting values from file downloaded. The values are used for mka data,this are added to the database.*/
        voter.mkaDistrictId = newVoter.mkaDistrictId
        voter.mkaDistrictName = newVoter.mkaDistrictName
        voter.mkaLlgId = newVoter.mkallgId
        voter.mkaLlgName = newVoter.mkallgName
        voter.mkaWardId = newVoter.mkaWardId
        voter.mkaWardName = newVoter.mkaWardName
        voter.mkaLocationId = newVoter.mkaLocationId
        voter.mkaLocationName = newVoter.mkaLocationName
        voter.mkaClanId = newVoter.mkaClanId
        voter.mkaClanName = newVoter.mkaClanName
        voter.clanId = newVoter.clanId
        voter.clanName = newVoter.clanName


        return voter
    }

    private fun getDownloadedTransferInVotersList(file: File): ArrayList<NewVotersList> {

        val fileInputStream = FileInputStream(file)
        val filteredVotersList: ArrayList<NewVotersList> = ArrayList()

        try {
            val objectMapper: ObjectMapper = JsonMapper.builder()
                .enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS)
                .enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES)
                .build()

            val offLineTransferInVotersResult: NewVoterDownloadModel =
                objectMapper.readValue(
                    fileInputStream,
                    NewVoterDownloadModel::class.java
                )

            val resultArray: ArrayList<NewVotersList>? =
                offLineTransferInVotersResult.downloadedVotersList

            return resultArray!!


        } catch (e: Exception) {
            return filteredVotersList
        }


    }

    private val stopServiceReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            securedSharedPreferenceWrapper.putInt(ApiConstants.LOCATION_PAGE_NO, 1)
            stopNotification()
        }
    }


    private fun callLocationsAPI() {

        val downloadLocationsObject = JsonObject()
        downloadLocationsObject.addProperty(ApiConstants.PAGENO, locationPageNumber)
        downloadLocationsObject.addProperty(ApiConstants.PAGESIZE, 1000)

        val call =
            ApiClient.getLocationsRetrofitInterface()?.callAllLocationsAPI(downloadLocationsObject)

        WSClient<AllLocationsModel>().request(
            this,
            ApiConstants.DPO_DOWNLOAD_LOCATIONS_LIST,
            call,
            object :
                ISuccessHandler<AllLocationsModel> {
                override fun successResponse(
                    requestCode: Int,
                    mResponse: AllLocationsModel
                ) {


                    if (mResponse.status == true && mResponse.data != null) {
                        val allLocationsData = mResponse.data
                        val totalLocationsCount = allLocationsData?.total!!


                        if (allLocationsData.locationsList!!.size > 0) {
                            downloadLocationsList(
                                allLocationsData.locationsList!!,
                                totalLocationsCount
                            )
                        } else {

                            securedSharedPreferenceWrapper.putBoolean(
                                ApiConstants.LOCATION_DATA_DOWNLOADED,
                                true
                            )

                            if (securedSharedPreferenceWrapper.getBoolean(
                                    ApiConstants.DPO_VOTERS_DATA_DOWNLOADED,
                                    false
                                ) && securedSharedPreferenceWrapper.getBoolean(
                                    ApiConstants.GEO_LIST_DATA_DOWNLOADED,
                                    false
                                ) && securedSharedPreferenceWrapper.getBoolean(
                                    ApiConstants.CLAN_LIST_DATA_DOWNLOADED,
                                    false
                                )
                            ) {
                                stopNotification()
                                securedSharedPreferenceWrapper.putInt(
                                    ApiConstants.DOWNLOADED_PERCENTAGE,
                                    0
                                )
                                securedSharedPreferenceWrapper.putBoolean(
                                    ApiConstants.DOWNLOAD_IN_PROGRESS,
                                    false
                                )
                                securedSharedPreferenceWrapper.putBoolean(
                                    ApiConstants.DPO_SETUP_COMPLETED,
                                    true
                                )
                                val intent = Intent(ApiConstants.ALL_DPO_DATA_COMPLETED_BROADCAST)
                                sendBroadcast(intent)
                            }

                        }

                    } else {
                        if (securedSharedPreferenceWrapper.getBoolean(
                                ApiConstants.DPO_VOTERS_DATA_DOWNLOADED,
                                false
                            )
                        )
                            stopNotification()
                    }

                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                    if (securedSharedPreferenceWrapper.getBoolean(
                            ApiConstants.DPO_VOTERS_DATA_DOWNLOADED,
                            false
                        )
                    )
                        stopNotification()
                }
            })

    }

    private fun downloadLocationsList(
        locationsList: ArrayList<LocationList>,
        totalLocationsCount: Int
    ) {

        val exceptionHandler = CoroutineExceptionHandler { _, t ->
            t.printStackTrace()
        }
        GlobalScope.launch(Dispatchers.Main + exceptionHandler) {
            var result: Int
            withContext(Dispatchers.IO) {
                dataBaseInstance.locationListDao().insertAll(locationsList)
                result = dataBaseInstance.locationListDao().all.size
            }

            val progressIntent = Intent(ApiConstants.SINGLE_PAGE_DATA_COMPLETED)
            progressIntent.putExtra("Total_Records", totalLocationsCount)
            progressIntent.putExtra("Downloaded_Records", result)
            sendBroadcast(progressIntent)

            if (result == totalLocationsCount) {

                securedSharedPreferenceWrapper.putBoolean(
                    ApiConstants.LOCATION_DATA_DOWNLOADED,
                    true
                )
                if (securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.DPO_VOTERS_DATA_DOWNLOADED,
                        false
                    ) && securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.GEO_LIST_DOWNLOADED,
                        false
                    ) && securedSharedPreferenceWrapper.getBoolean(
                        ApiConstants.CLAN_LIST_DATA_DOWNLOADED,
                        false
                    )
                ) {
                    stopNotification()
                    securedSharedPreferenceWrapper.putInt(ApiConstants.DOWNLOADED_PERCENTAGE, 0)
                    securedSharedPreferenceWrapper.putBoolean(
                        ApiConstants.DOWNLOAD_IN_PROGRESS,
                        false
                    )
                    securedSharedPreferenceWrapper.putBoolean(
                        ApiConstants.DPO_SETUP_COMPLETED,
                        true
                    )
                    val intent = Intent(ApiConstants.ALL_DPO_DATA_COMPLETED_BROADCAST)
                    sendBroadcast(intent)
                }


            } else {

                locationPageNumber++
                securedSharedPreferenceWrapper.putInt(
                    ApiConstants.LOCATION_PAGE_NO,
                    locationPageNumber
                )
                callLocationsAPI()


            }

        }

    }

    lateinit var geoListData: GeoListData
    private fun callGetGeoListAPI() {
        val call =
            ApiClient.getRetrofitInterface()?.callGetGeoListAPI()

        WSClient<GeoListModel>().request(this, ApiConstants.DPO_GET_GEO_LIST, call, object :
            ISuccessHandler<GeoListModel> {
            override fun successResponse(
                requestCode: Int,
                mResponse: GeoListModel
            ) {
                geoListData = mResponse.data!!
                saveGeoDataList()
            }

        }, object : IFailureHandler {
            override fun failureResponse(requestCode: Int, message: String) {
            }
        })

    }

    private fun saveGeoDataList() {

        val exceptionHandler = CoroutineExceptionHandler { _, t ->
            t.printStackTrace()
        }
        GlobalScope.launch(Dispatchers.Main + exceptionHandler) {
            withContext(Dispatchers.IO) {
                val mRegionListDAO = dataBaseInstance.regionListDao()
                mRegionListDAO.deleteAll()
                mRegionListDAO.insertAll(geoListData.regionList!!)

                val mProvinceListDAO = dataBaseInstance.provinceListDao()
                mProvinceListDAO.deleteAll()
                mProvinceListDAO.insertAll(geoListData.provinceList!!)

                val mDistrictListDAO = dataBaseInstance.districtListDao()
                mDistrictListDAO.deleteAll()
                mDistrictListDAO.insertAll(geoListData.districtList!!)

                val mLLGListDAO = dataBaseInstance.llgListDao()
                mLLGListDAO.deleteAll()
                mLLGListDAO.insertAll(geoListData.llgList!!)

                val mWardListDAO = dataBaseInstance.wardListDao()
                mWardListDAO.deleteAll()
                mWardListDAO.insertAll(geoListData.wardList!!)

            }

            securedSharedPreferenceWrapper.putBoolean(ApiConstants.GEO_LIST_DOWNLOADED, true)
        }

    }

    private fun callGetOccupationListAPI() {

        val call =
            ApiClient.getRetrofitInterface()?.callGetOccupationListAPI()

        WSClient<Response<ArrayList<OccupationList>>>().request(
            this,
            ApiConstants.DPO_GET_OCCUPATION_LIST,
            call,
            object :
                ISuccessHandler<Response<ArrayList<OccupationList>>> {
                override fun successResponse(
                    requestCode: Int,
                    mResponse: Response<ArrayList<OccupationList>>
                ) {
                    saveOccupationList(mResponse.data!!)
                }

            },
            object : IFailureHandler {
                override fun failureResponse(requestCode: Int, message: String) {
                }
            })

    }

    private fun saveOccupationList(occupationList: ArrayList<OccupationList>) {

        val exceptionHandler = CoroutineExceptionHandler { _, t ->
            t.printStackTrace()
        }
        GlobalScope.launch(Dispatchers.Main + exceptionHandler) {
            withContext(Dispatchers.IO) {
                dataBaseInstance.occupationListDao().deleteAll()
                dataBaseInstance.occupationListDao().insertAll(occupationList)
            }
            securedSharedPreferenceWrapper.putBoolean(ApiConstants.OCCUPATION_DOWNLOADED, true)
            callGetGeoListAPI()
        }

    }


    private fun startNotificationWithNotification() {

        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("voter_list_service", "Voter List Download Service")
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setContentText("Downloading voter data")
            .build()
        startForeground(102, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.GREEN
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    private fun stopNotification() {
        stopForeground(true)
        stopSelf()
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(stopServiceReceiver)
    }


}