package com.pngelectoral.ui.activity

import ApiConstants
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.master.permissionhelper.PermissionHelper
import com.pngelectoral.LocationHelper2
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.databinding.ActivitySplashBinding
import com.pngelectoral.ui.viewModel.SplashViewModel
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton


class SplashActivity : BaseActivity() {

    private var binding: ActivitySplashBinding? = null
    private var splashViewModel: SplashViewModel? = null

    private var locationPermissionHelperClassifier: PermissionHelper? = null
    private var locationHelper: LocationHelper2? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_splash
        )
        splashViewModel =
            ViewModelProvider(this).get(SplashViewModel::class.java)
        binding?.splashViewModel = splashViewModel

        locationHelper = LocationHelper2(this)
        checkLocationPermission()

    }

    private fun checkLocationPermission() {

        locationPermissionHelperClassifier = PermissionHelper(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            11
        )
        locationPermissionHelperClassifier!!.denied { isSystemDenied: Boolean? ->

            val builder: AlertDialog.Builder = AlertDialog.Builder(this@SplashActivity)
            builder.setTitle(getString(R.string.app_name))
            builder.setMessage(getString(R.string.msg_we_need_permission_allow_from_settings))
            builder.setPositiveButton(
                R.string.ok
            ) { dialogInterface, i -> locationPermissionHelperClassifier!!.openAppDetailsActivity() }
            builder.setNegativeButton(
                R.string.cancel
            ) { dialogInterface, i -> finish() }

            builder.setCancelable(false)

            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()
        }
        locationPermissionHelperClassifier!!.requestAll {
            getCurrentLocation()
        }

    }

    private fun getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(
                this@SplashActivity, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@SplashActivity, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                11
            )
        } else {
            checkGPSStatus()
        }

    }


    private fun showAlert() {

        alert("You need to enable the location to continue, please allow it") {
            okButton {
                checkLocationPermission()
            }
        }.show()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            checkGPSStatus()
        } else {
            showAlert()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 11) {
            if (locationPermissionHelperClassifier != null) {
                locationPermissionHelperClassifier!!.onRequestPermissionsResult(
                    requestCode,
                    permissions,
                    grantResults
                )
            }
        }
    }

    //    lateinit var locationManager: LocationManager
    private fun checkGPSStatus() {

//        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//        val gpsLocationListener: LocationListener = object : LocationListener {
//            override fun onLocationChanged(location: Location) {
//                securedSharedPreferenceWrapper.putString(
//                    ApiConstants.LATITUDE,
//                    location.latitude.toString()
//                )
//
//                securedSharedPreferenceWrapper.putString(
//                    ApiConstants.LONGITUDE,
//                    location.longitude.toString()
//                )
//
//                navigateToNextScreen()
//            }
//
//            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
//            override fun onProviderEnabled(provider: String) {}
//            override fun onProviderDisabled(provider: String) {}
//        }
//
//        if (hasGps) {
//            locationManager.requestLocationUpdates(
//                LocationManager.GPS_PROVIDER,
//                5000,
//                0F,
//                gpsLocationListener
//            )
//        }


//        if (CommonMethods.checkInternetConnection(application)) {
//            if (locationHelper == null) {
//                locationHelper = LocationHelper2(this)
//            }
//            locationHelper!!.fetchSingleLocation { location ->
//
//                securedSharedPreferenceWrapper.putString(
//                    ApiConstants.LATITUDE,
//                    location.latitude.toString()
//                )
//
//                securedSharedPreferenceWrapper.putString(
//                    ApiConstants.LONGITUDE,
//                    location.longitude.toString()
//                )
//
//                navigateToNextScreen()
//            }
//        } else {
//            navigateToNextScreen()
//        }

        navigateToNextScreen()
    }

    private fun navigateToNextScreen() {
        ApiConstants.HOME_FILTER_LOCATION_ID = 0
        ApiConstants.HOME_FILTER_LOCATION = ""
        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            //startActivity(Intent(this, BiometricActivity::class.java))
            finish()
        }, 100)
    }

//    private fun stopLocation() {
//        if (locationHelper != null) {
//            locationHelper!!.stopLocationUpdates()
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
//        stopLocation()
    }


}