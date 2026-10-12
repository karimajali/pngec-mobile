package com.pngelectoral.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.pngelectoral.ui.model.TransferInVotersModel
import com.pngelectoral.ui.repository.SettingsRepository
import com.pngelectoral.ui.retrofit.Response
import com.pngelectoral.ui.retrofit.WsConstants
import com.pngelectoral.utility.CommonMethods

class HomeViewModel(var app: Application) : AndroidViewModel(app) {

    private val settingsRepository = SettingsRepository()
    var logoutResponse = MutableLiveData<Response<Any>>()

    fun callLogoutAPI(
        mContext: Context,
        inputParam: JsonObject
    ) {
        if (CommonMethods.checkInternetConnection(getApplication())) {
            settingsRepository.callLogoutAPI(mContext, inputParam,logoutResponse)
        } else {
            val response = Response<Any>()
            response.status = WsConstants.STATUS_FAILURE
            response.message = WsConstants.FAILURE
            logoutResponse.value = response
        }
    }

}