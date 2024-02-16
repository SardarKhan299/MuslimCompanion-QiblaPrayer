package com.qibla.qiblacompass.prayertime.finddirection.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SampleBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Set the alarm here.
            Log.d(SampleBootReceiver::class.simpleName, "onReceive: Mobile Restart...")
        }
    }
}