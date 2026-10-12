package com.pngelectoral.ui.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.pngelectoral.ui.retrofit.WsConstants
import com.pngelectoral.ui.model.LoginResponseModel
import com.pngelectoral.ui.retrofit.*

class ValidateEnrolmentFormNumberRepository {


    fun callValidateEnrolmentFormNumberAPI(
        mContext: Context,
        inputParam: JsonObject,
        validateEnrolmentFormNumberResponse: MutableLiveData<Response<Any>>
    ) {

        val call =
            ApiClient.getRetrofitInterface()?.callValidateEnrolmentFormNumberAPI(inputParam)

        WSClient<Response<Any>>().request(mContext,ApiConstants.DPO_VALIDATE_ENROLMENT_FORM_NUMBER, call, object :
            ISuccessHandler<Response<Any>> {
            override fun successResponse(
                requestCode: Int,
                mResponse: Response<Any>
            ) {
                validateEnrolmentFormNumberResponse.value = mResponse
            }

        }, object : IFailureHandler {
            override fun failureResponse(requestCode: Int, message: String) {
                val response = Response<Any>()
                response.status = WsConstants.STATUS_FAILURE
                response.message = message
                validateEnrolmentFormNumberResponse.value = response

            }
        })


    }

}