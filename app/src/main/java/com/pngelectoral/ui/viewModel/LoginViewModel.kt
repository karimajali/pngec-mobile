package com.pngelectoral.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.pngelectoral.ui.retrofit.WsConstants
import com.pngelectoral.ui.model.LoginResponseModel
import com.pngelectoral.ui.repository.LoginRepository
import com.pngelectoral.ui.retrofit.Response
import com.pngelectoral.utility.CommonMethods

class LoginViewModel(var app: Application) : AndroidViewModel(app) {

    var loginResponse = MutableLiveData<LoginResponseModel>()
    var forgotPasswordResponse = MutableLiveData<Response<Any>>()
    private val loginRepository = LoginRepository()

    fun callLoginAPI(
        mContext: Context,
        loginObj: JsonObject
    ) {
        if (CommonMethods.checkInternetConnection(getApplication())) {
            loginRepository.callLoginAPI(mContext, loginObj, loginResponse)
        } else {
            val response = LoginResponseModel()
            response.status = WsConstants.STATUS_FAILURE
            response.message = WsConstants.FAILURE
            loginResponse.value = response
        }
    }

    fun callForgotPasswordAPI(
        mContext: Context,
        inputParam: JsonObject
    ) {
        if (CommonMethods.checkInternetConnection(getApplication())) {
            loginRepository.callForgotPasswordAPI(mContext, inputParam, forgotPasswordResponse)
        } else {
            val response = Response<Any>()
            response.status = WsConstants.STATUS_FAILURE
            response.message = WsConstants.FAILURE
            forgotPasswordResponse.value = response
        }
    }

}