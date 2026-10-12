package com.pngelectoral.ui.retrofit

interface ISuccessHandler<T> {

    fun successResponse(requestCode: Int, mResponse: T)

}
