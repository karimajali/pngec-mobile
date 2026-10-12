package com.pngelectoral.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.pngelectoral.ui.model.TransferInVotersModel
import com.pngelectoral.ui.repository.DPOGeoStructureUpdateRepository
import com.pngelectoral.ui.repository.TransferInVoterListRepository
import com.pngelectoral.ui.retrofit.Response
import com.pngelectoral.ui.retrofit.WsConstants
import com.pngelectoral.utility.CommonMethods

class DpoGeoStructureUpdateViewModel(var app: Application) : AndroidViewModel(app) {

    var dpoGeoStructureUpdateResponse = MutableLiveData<Response<Any>>()
    private val dpoGeoStructureUpdateRepository = DPOGeoStructureUpdateRepository()

    fun callUpdateGeoStructureAPI(
        mContext: Context,
        dpoGeoStructureUpdateObj: JsonObject
    ) {
        if (CommonMethods.checkInternetConnection(getApplication())) {
            dpoGeoStructureUpdateRepository.callUpdateGeoStructureAPI(mContext,
                dpoGeoStructureUpdateObj, dpoGeoStructureUpdateResponse)
        } else {
            val response = Response<Any>()
            response.status = WsConstants.STATUS_FAILURE
            response.message = WsConstants.FAILURE
            dpoGeoStructureUpdateResponse.value = response
        }
    }

}