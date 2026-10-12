package com.pngelectoral.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.pngelectoral.utility.sharedPreference.SecuredSharedPreferenceWrapper


abstract class BaseActivity : AppCompatActivity(), CommonActivityComponentHandler {

    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(this, "png_electoral")
    }


    override fun onStart() {
        super.onStart()
        val decor = getWindow().getDecorView()
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }


    override fun onResume() {
        Log.d("onResume", "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("onPause", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("onStop", "onStop")
        super.onStop()
    }


    override fun onRestart() {
        super.onRestart()
        Log.d("onRestart", "onRestart")
    }

    var currentRunningFragment: BaseFragment<*>? = null


    override fun setCurrentFragment(mCurrentFragment: BaseFragment<*>) {
        this.currentRunningFragment = mCurrentFragment
    }

    override fun onBackPressed() {
        if (currentRunningFragment != null && currentRunningFragment!!.onBackPressed()!!) {

        } else {
            super.onBackPressed()
        }
    }

    fun push(
        fragment: BaseFragmentTab<*>,
        containerId: Int,
        needToAddBackStack: Boolean,
        needAnimation: Boolean?
    ) {
        // hideKeyBoardAndProgress();
        val ft = supportFragmentManager.beginTransaction()
        if (needToAddBackStack) {
            ft.replace(containerId, fragment).addToBackStack(fragment.javaClass.simpleName)
                .commitAllowingStateLoss()
        } else {
            ft.replace(containerId, fragment).commitAllowingStateLoss()
        }
    }

    fun pop() {
        // hideKeyBoardAndProgress();
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            finish()
        }
    }

    fun popUpTo(upToName: String) {
        // hideKeyBoardAndProgress();
        supportFragmentManager.popBackStackImmediate(
            upToName,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    fun popAll() {
        //hideKeyBoardAndProgress();
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


}