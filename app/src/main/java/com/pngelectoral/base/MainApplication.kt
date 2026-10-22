package com.pngelectoral.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.multidex.MultiDexApplication
import com.google.firebase.analytics.FirebaseAnalytics
import com.pngelectoral.datasync.SyncV2Worker

class MainApplication : MultiDexApplication() {

    private var firebaseAnalytics: FirebaseAnalytics? = null
    private var sApplication: MainApplication? = null

    override fun onCreate() {
        super.onCreate()
        sApplication = this
        // Obtain the Firebase Analytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        // Initialize WorkManager Periodic Background Sync (every 15 min when CONNECTED)
        try {
            SyncV2Worker.enqueuePeriodicSync(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Real-Time NetInfo Listener: Instantly auto-syncs the millisecond internet connects (zero delay)
        registerRealtimeNetworkSyncListener()
    }

    private fun registerRealtimeNetworkSyncListener() {
        try {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()

            cm.registerNetworkCallback(request, object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    try {
                        SyncV2Worker.enqueueWardSync(applicationContext, wardId = 0, isImmediate = true)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getFirebaseAnalytics(): FirebaseAnalytics? {
        return firebaseAnalytics
    }

    fun getApplication(): MainApplication? {
        return sApplication
    }
}
