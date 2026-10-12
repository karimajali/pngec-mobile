package com.pngelectoral.ui.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.pngelectoral.ui.retrofit.WsConstants
import com.pngelectoral.ui.model.LoginResponseModel
import com.pngelectoral.ui.model.TransferInVotersModel
import com.pngelectoral.ui.retrofit.*

class DPOGeoStructureUpdateRepository {

    fun callUpdateGeoStructureAPI(
        mContext: Context,
        dpoGeoStructureUpdateObj: JsonObject,
        dpoGeoStructureUpdateResponse: MutableLiveData<Response<Any>>
    ) {

        val call =
            ApiClient.getRetrofitInterface()?.callUpdateGeoStructureAPI(dpoGeoStructureUpdateObj)

        WSClient<Response<Any>>().request(mContext,ApiConstants.DPO_GEO_STRUCTURE_UPDATE, call, object :
            ISuccessHandler<Response<Any>> {
            override fun successResponse(
                requestCode: Int,
                mResponse: Response<Any>
            ) {
                dpoGeoStructureUpdateResponse.value = mResponse
            }

        }, object : IFailureHandler {
            override fun failureResponse(requestCode: Int, message: String) {
                val response = Response<Any>()
                response.status = WsConstants.STATUS_FAILURE
                response.message = message
                dpoGeoStructureUpdateResponse.value = response

            }
        })


    }


}