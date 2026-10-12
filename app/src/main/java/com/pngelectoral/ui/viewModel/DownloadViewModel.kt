package com.pngelectoral.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.pngelectoral.ui.model.GeoListModel
import com.pngelectoral.ui.model.OccupationList
import com.pngelectoral.ui.model.VotersList
import com.pngelectoral.ui.repository.DownloadRepository
import com.pngelectoral.ui.retrofit.Response
import com.pngelectoral.ui.retrofit.WsConstants
import com.pngelectoral.utility.CommonMethods

class DownloadViewModel(var app: Application) : AndroidViewModel(app) {

    var votersListResponse = MutableLiveData<Response<ArrayList<VotersList>>>()
    var geoListResponse = MutableLiveData<GeoListModel>()
    var occupationListResponse = MutableLiveData<Response<ArrayList<OccupationList>>>()

    private val downloadRepository = DownloadRepository()

    fun callDownloadVotersListAPI(
        mContext: Context,
        userId: String
    ) {
        if (CommonMethods.checkInternetConnection(getApplication())) {
            downloadRepository.callDownloadVotersListAPI(mContext, userId,votersListResponse)
        } else {
            val response = Response<ArrayList<VotersList>>()
            response.status = WsConstants.STATUS_FAILURE
            response.message = WsConstants.FAILURE
            votersListResponse.value = response
        }
    }

    fun callGetGeoListAPI(mContext: Context) {

        if (CommonMethods.checkInternetConnection(getApplication())) {
            downloadRepository.callGetGeoListAPI(mContext, geoListResponse)
        } else {
            val response = GeoListModel()
            response.status = WsConstants.STATUS_FAILURE
            response.message = WsConstants.FAILURE
            geoListResponse.value = response
        }

    }

    fun callGetOccupationListAPI(
        mContext: Context
    ) {
        if (CommonMethods.checkInternetConnection(getApplication())) {
            downloadRepository.callGetOccupationListAPI(mContext, occupationListResponse)
        } else {
            val response = Response<ArrayList<OccupationList>>()
            response.status = WsConstants.STATUS_FAILURE
            response.message = WsConstants.FAILURE
            occupationListResponse.value = response
        }
    }

}