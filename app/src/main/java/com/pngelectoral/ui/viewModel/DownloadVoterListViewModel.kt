package com.pngelectoral.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.pngelectoral.ui.model.ProvinceList
import com.pngelectoral.ui.model.VotersList
import com.pngelectoral.ui.repository.DownloadVotersListRepository
import com.pngelectoral.ui.retrofit.Response
import com.pngelectoral.ui.retrofit.WsConstants
import com.pngelectoral.utility.CommonMethods

class DownloadVoterListViewModel(var app: Application) : AndroidViewModel(app) {

    private val downloadVotersListRepository = DownloadVotersListRepository()
    var provinceListResponse = MutableLiveData<Response<ArrayList<ProvinceList>>>()


    fun callGetActiveProvinceListAPI(
        mContext: Context
    ) {
        if (CommonMethods.checkInternetConnection(getApplication())) {
            downloadVotersListRepository.callGetActiveProvinceListAPI(mContext, provinceListResponse)
        } else {
            val response = Response<ArrayList<ProvinceList>>()
            response.status = WsConstants.STATUS_FAILURE
            response.message = WsConstants.FAILURE
            provinceListResponse.value = response
        }
    }



}