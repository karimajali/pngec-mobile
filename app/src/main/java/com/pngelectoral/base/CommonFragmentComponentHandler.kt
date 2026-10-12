package com.pngelectoral.base

interface CommonFragmentComponentHandler {

    val layoutId: Int
    fun onBackPressed(): Boolean?

    fun init()
}
