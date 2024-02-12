package com.qibla.qiblacompass.prayertime.finddirection.common

import java.text.SimpleDateFormat
import java.util.Locale

class CommonMethods {

    companion object{
         fun convertTimeToMilliseconds(timeString: String): Long {
            return try {
                val format = SimpleDateFormat("hh:mm a", Locale.US)
                val date = format.parse(timeString)
                date?.time ?: -1
            } catch (e: Exception) {
                -1
            }
        }

         fun formatTime(seconds: Long): String {
            val hours = seconds / 3600
            val minutes = (seconds % 3600) / 60
            val secondsRemaining = seconds % 60
            return String.format("%02d hr %02d min %02d sec", hours, minutes, secondsRemaining)
        }
    }
}