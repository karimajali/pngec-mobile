package com.pngelectoral.ui.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.retrofit.WsConstants
import com.pngelectoral.ui.retrofit.*

class DownloadVotersListRepository {

    fun callGetActiveProvinceListAPI(
        mContext: Context,
        geoListResponse: MutableLiveData<Response<ArrayList<ProvinceList>>>
    ) {

        val call =
            ApiClient.getRetrofitInterface()?.callGetActiveProvinceListAPI()

        WSClient<Response<ArrayList<ProvinceList>>>().request(mContext,ApiConstants.DPO_GET_ACTIVE_PROVINCE_LIST, call, object :
            ISuccessHandler<Response<ArrayList<ProvinceList>>> {
            override fun successResponse(
                requestCode: Int,
                mResponse: Response<ArrayList<ProvinceList>>
            ) {
                geoListResponse.value = mResponse
            }

        }, object : IFailureHandler {
            override fun failureResponse(requestCode: Int, message: String) {
                val response = Response<ArrayList<ProvinceList>>()
                response.status = WsConstants.STATUS_FAILURE
                response.message = message
                geoListResponse.value = response

            }
        })
    }


}