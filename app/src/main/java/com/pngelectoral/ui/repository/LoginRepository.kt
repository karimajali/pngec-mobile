package com.pngelectoral.ui.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.pngelectoral.ui.retrofit.WsConstants
import com.pngelectoral.ui.model.LoginResponseModel
import com.pngelectoral.ui.retrofit.*

class LoginRepository {

    fun callLoginAPI(
        mContext: Context,
        loginObj: JsonObject,
        loginResponse: MutableLiveData<LoginResponseModel>
    ) {

        val call =
            ApiClient.getRetrofitInterface()?.callLoginAPI(loginObj)

        WSClient<LoginResponseModel>().request(mContext,ApiConstants.DPO_LOGIN, call, object :
            ISuccessHandler<LoginResponseModel> {
            override fun successResponse(
                requestCode: Int,
                mResponse: LoginResponseModel
            ) {
                loginResponse.value = mResponse
            }

        }, object : IFailureHandler {
            override fun failureResponse(requestCode: Int, message: String) {
                val response = LoginResponseModel()
                response.status = WsConstants.STATUS_FAILURE
                response.message = message
                loginResponse.value = response

            }
        })


    }

    fun callForgotPasswordAPI(
        mContext: Context,
        inputParam: JsonObject,
        forgotPasswordResponse: MutableLiveData<Response<Any>>
    ) {

        val call =
            ApiClient.getRetrofitInterface()?.callForgotPasswordAPI(inputParam)

        WSClient<Response<Any>>().request(mContext,ApiConstants.DPO_FORGOT_PASSWORD, call, object :
            ISuccessHandler<Response<Any>> {
            override fun successResponse(
                requestCode: Int,
                mResponse: Response<Any>
            ) {
                forgotPasswordResponse.value = mResponse
            }

        }, object : IFailureHandler {
            override fun failureResponse(requestCode: Int, message: String) {
                val response = Response<Any>()
                response.status = WsConstants.STATUS_FAILURE
                response.message = message
                forgotPasswordResponse.value = response

            }
        })


    }

}