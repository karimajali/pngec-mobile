package com.pngelectoral.base

import androidx.multidex.MultiDexApplication
import com.google.firebase.analytics.FirebaseAnalytics

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
            com.pngelectoral.datasync.SyncV2Worker.enqueuePeriodicSync(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getFirebaseAnalytics(): FirebaseAnalytics? {
        return firebaseAnalytics
    }

    public fun getApplication(): MainApplication? {
        return sApplication
    }


}
