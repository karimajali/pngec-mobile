package com.pngelectoral.ui.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.pngelectoral.ui.retrofit.WsConstants
import com.pngelectoral.ui.model.LoginResponseModel
import com.pngelectoral.ui.model.TransferInVotersModel
import com.pngelectoral.ui.retrofit.*

class RejectedVotersListRepository {

    fun callRejectedVotersListAPI(
        mContext: Context,
        rejectedVotersListObject: JsonObject,
        rejectedVotersListResponse: MutableLiveData<TransferInVotersModel>
    ) {

        val call =
            ApiClient.getRetrofitInterface()?.callRejectedVotersListAPI(rejectedVotersListObject)

        WSClient<TransferInVotersModel>().request(mContext,ApiConstants.DPO_REJECTED_VOTERS_LIST, call, object :
            ISuccessHandler<TransferInVotersModel> {
            override fun successResponse(
                requestCode: Int,
                mResponse: TransferInVotersModel
            ) {
                rejectedVotersListResponse.value = mResponse
            }

        }, object : IFailureHandler {
            override fun failureResponse(requestCode: Int, message: String) {
                val response = TransferInVotersModel()
                response.status = WsConstants.STATUS_FAILURE
                response.message = message
                rejectedVotersListResponse.value = response

            }
        })


    }


}