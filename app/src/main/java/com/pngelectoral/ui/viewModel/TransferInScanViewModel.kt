package com.pngelectoral.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.pngelectoral.ui.repository.ValidateEnrolmentFormNumberRepository
import com.pngelectoral.ui.retrofit.Response
import com.pngelectoral.ui.retrofit.WsConstants
import com.pngelectoral.utility.CommonMethods

class TransferInScanViewModel(var app: Application) : AndroidViewModel(app) {

    var validateEnrolmentFormNumberResponse = MutableLiveData<Response<Any>>()
    private val validateEnrolmentFormNumberRepository = ValidateEnrolmentFormNumberRepository()

    fun callValidateEnrolmentFormNumberAPI(
        mContext: Context,
        inputParam: JsonObject
    ) {
        if (CommonMethods.checkInternetConnection(getApplication())) {
            validateEnrolmentFormNumberRepository.callValidateEnrolmentFormNumberAPI(mContext, inputParam, validateEnrolmentFormNumberResponse)
        } else {
            val response = Response<Any>()
            response.status = WsConstants.STATUS_FAILURE
            response.message = WsConstants.FAILURE
            validateEnrolmentFormNumberResponse.value = response
        }
    }

}