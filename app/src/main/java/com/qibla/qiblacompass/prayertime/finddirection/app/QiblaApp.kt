package com.qibla.qiblacompass.prayertime.finddirection.app

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QiblaApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(QiblaApp::class.simpleName, "onCreate: ")
    }
}