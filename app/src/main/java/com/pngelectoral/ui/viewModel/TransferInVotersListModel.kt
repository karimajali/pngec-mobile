package com.pngelectoral.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.pngelectoral.ui.model.TransferInVotersModel
import com.pngelectoral.ui.repository.TransferInVoterListRepository
import com.pngelectoral.ui.retrofit.Response
import com.pngelectoral.ui.retrofit.WsConstants
import com.pngelectoral.utility.CommonMethods

class TransferInVotersListModel(var app: Application) : AndroidViewModel(app) {

    var transferInVotersListResponse = MutableLiveData<TransferInVotersModel>()
    private val transferInVoterListRepository = TransferInVoterListRepository()

    fun callTransferInVotersListAPI(
        mContext: Context,
        transferInVoterListObj: JsonObject
    ) {
        if (CommonMethods.checkInternetConnection(getApplication())) {
            transferInVoterListRepository.callTransferInVotersListAPI(mContext,
                transferInVoterListObj, transferInVotersListResponse)
        } else {
            val response = TransferInVotersModel()
            response.status = WsConstants.STATUS_FAILURE
            response.message = WsConstants.FAILURE
            transferInVotersListResponse.value = response
        }
    }

}