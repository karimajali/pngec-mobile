package com.pngelectoral.ui.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.pngelectoral.ui.retrofit.WsConstants
import com.pngelectoral.ui.model.LoginResponseModel
import com.pngelectoral.ui.model.TransferInVotersModel
import com.pngelectoral.ui.retrofit.*

class SettingsRepository {


    fun callLogoutAPI(
        mContext: Context,
        inputParam: JsonObject,
        logoutResponse: MutableLiveData<Response<Any>>
    ) {

        val call =
            ApiClient.getRetrofitInterface()?.callLogoutAPI(inputParam)

        WSClient<Response<Any>>().request(mContext,ApiConstants.DPO_LOG_OUT, call, object :
            ISuccessHandler<Response<Any>> {
            override fun successResponse(
                requestCode: Int,
                mResponse: Response<Any>
            ) {
                logoutResponse.value = mResponse
            }

        }, object : IFailureHandler {
            override fun failureResponse(requestCode: Int, message: String) {
                val response = Response<Any>()
                response.status = WsConstants.STATUS_FAILURE
                response.message = message
                logoutResponse.value = response

            }
        })


    }

    fun callGetUpdatedVoterListAPI(
        mContext: Context,
        downloadStagingVotersDataObj: JsonObject,
        updatedVotersListResponse: MutableLiveData<TransferInVotersModel>
    ) {

        val call =
            ApiClient.getRetrofitInterface()?.callGetUpdatedVoterListAPI(downloadStagingVotersDataObj)

        WSClient<TransferInVotersModel>().request(mContext,ApiConstants.DPO_UPDATED_VOTERS_LIST, call, object :
            ISuccessHandler<TransferInVotersModel> {
            override fun successResponse(
                requestCode: Int,
                mResponse: TransferInVotersModel
            ) {
                updatedVotersListResponse.value = mResponse
            }

        }, object : IFailureHandler {
            override fun failureResponse(requestCode: Int, message: String) {
                val response = TransferInVotersModel()
                response.status = WsConstants.STATUS_FAILURE
                response.message = message
                updatedVotersListResponse.value = response

            }
        })


    }

}