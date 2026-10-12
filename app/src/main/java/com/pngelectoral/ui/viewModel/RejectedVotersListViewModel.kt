package com.pngelectoral.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.pngelectoral.ui.model.TransferInVotersModel
import com.pngelectoral.ui.repository.RejectedVotersListRepository
import com.pngelectoral.ui.repository.TransferInVoterListRepository
import com.pngelectoral.ui.retrofit.WsConstants
import com.pngelectoral.utility.CommonMethods

class RejectedVotersListViewModel(var app: Application) : AndroidViewModel(app) {

    var rejectedVotersListResponse = MutableLiveData<TransferInVotersModel>()
    private val rejectedVotersListRepository = RejectedVotersListRepository()

    fun callRejectedVotersListAPI(
        mContext: Context,
        rejectedVotersListObject: JsonObject
    ) {
        if (CommonMethods.checkInternetConnection(getApplication())) {
            rejectedVotersListRepository.callRejectedVotersListAPI(mContext,
                rejectedVotersListObject, rejectedVotersListResponse)
        } else {
            val response = TransferInVotersModel()
            response.status = WsConstants.STATUS_FAILURE
            response.message = WsConstants.FAILURE
            rejectedVotersListResponse.value = response
        }
    }

}