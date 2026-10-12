package com.pngelectoral.ui.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.pngelectoral.ui.model.GeoListModel
import com.pngelectoral.ui.retrofit.WsConstants
import com.pngelectoral.ui.model.LoginResponseModel
import com.pngelectoral.ui.model.OccupationList
import com.pngelectoral.ui.model.VotersList
import com.pngelectoral.ui.retrofit.*

class DownloadRepository {

    fun callDownloadVotersListAPI(
        mContext: Context,
        userId: String,
        loginResponse: MutableLiveData<Response<ArrayList<VotersList>>>
    ) {

        val call =
            ApiClient.getRetrofitInterface()?.callDownloadVotersListAPI(userId)

        WSClient<Response<ArrayList<VotersList>>>().request(mContext,ApiConstants.DPO_DOWNLOAD_VOTERS_LIST, call, object :
            ISuccessHandler<Response<ArrayList<VotersList>>> {
            override fun successResponse(
                requestCode: Int,
                mResponse: Response<ArrayList<VotersList>>
            ) {
                loginResponse.value = mResponse
            }

        }, object : IFailureHandler {
            override fun failureResponse(requestCode: Int, message: String) {
                val response = Response<ArrayList<VotersList>>()
                response.status = WsConstants.STATUS_FAILURE
                response.message = message
                loginResponse.value = response

            }
        })


    }

    fun callGetGeoListAPI(
        mContext: Context,
        geoListResponse: MutableLiveData<GeoListModel>
    ) {

        val call =
            ApiClient.getRetrofitInterface()?.callGetGeoListAPI()

        WSClient<GeoListModel>().request(mContext,ApiConstants.DPO_GET_GEO_LIST, call, object :
            ISuccessHandler<GeoListModel> {
            override fun successResponse(
                requestCode: Int,
                mResponse: GeoListModel
            ) {
                geoListResponse.value = mResponse
            }

        }, object : IFailureHandler {
            override fun failureResponse(requestCode: Int, message: String) {
                val response = GeoListModel()
                response.status = WsConstants.STATUS_FAILURE
                response.message = message
                geoListResponse.value = response

            }
        })


    }

    fun callGetOccupationListAPI(
        mContext: Context,
        occupationListResponse: MutableLiveData<Response<ArrayList<OccupationList>>>
    ) {

        val call =
            ApiClient.getRetrofitInterface()?.callGetOccupationListAPI()

        WSClient<Response<ArrayList<OccupationList>>>().request(mContext,ApiConstants.DPO_GET_OCCUPATION_LIST, call, object :
            ISuccessHandler<Response<ArrayList<OccupationList>>> {
            override fun successResponse(
                requestCode: Int,
                mResponse: Response<ArrayList<OccupationList>>
            ) {
                occupationListResponse.value = mResponse
            }

        }, object : IFailureHandler {
            override fun failureResponse(requestCode: Int, message: String) {
                val response = Response<ArrayList<OccupationList>>()
                response.status = WsConstants.STATUS_FAILURE
                response.message = message
                occupationListResponse.value = response

            }
        })


    }


}