package com.pngelectoral.base

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager

abstract class BaseFragmentTab<T : ViewDataBinding> : BaseFragment<T>() {

    var mFragmentManager: FragmentManager? = null

    fun pushInsideTab(fragment: BaseFragmentTab<*>, containerId: Int, addBackStack: Boolean, animation: Boolean?) {
        fragment.mFragmentManager = mFragmentManager
        val mFragmentTransaction = fragment.mFragmentManager!!.beginTransaction()

        mFragmentTransaction.replace(containerId, fragment)
        if (addBackStack) {
            mFragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        }
        mFragmentTransaction.commit()
    }

    fun popTab() {
        if (mFragmentManager != null && mFragmentManager!!.backStackEntryCount > 0) {
            mFragmentManager!!.popBackStackImmediate()
        }
    }

    override fun onBackPressed(): Boolean? {
        if (mFragmentManager != null && mFragmentManager!!.backStackEntryCount > 0) {
            mFragmentManager!!.popBackStackImmediate()
            return true
        }
        return false
    }

    fun popUpToRoot() {
        var backStackCount = 0
        if (mFragmentManager != null) {
            backStackCount = mFragmentManager!!.backStackEntryCount
        }
        if (backStackCount > 0) {
            for (i in 0 until backStackCount) {
                mFragmentManager!!.popBackStackImmediate()
            }
        }
    }
}