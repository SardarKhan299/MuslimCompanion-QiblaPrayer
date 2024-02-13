package com.qibla.qiblacompass.prayertime.finddirection.app

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.qibla.qiblacompass.prayertime.finddirection.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QiblaApp: Application() {

    companion object{
        var selectedPrayerPos = 0
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(QiblaApp::class.simpleName, "onCreate: ")
        FirebaseApp.initializeApp(this)
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds =  if (BuildConfig.DEBUG) {
                0 // fetch every time in debug mode
            } else {
                300
            }
        }

        // Initialize Remote Config default values.
        val defaults = mutableMapOf<String, Any>(
            "MAKKAH_LIVE_URL1" to "https://www.youtube-nocookie.com/embed/xZtG7Bn2B5c?autoplay=1&playsinline=1"
        )
        remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(defaults)
        }

    }
}