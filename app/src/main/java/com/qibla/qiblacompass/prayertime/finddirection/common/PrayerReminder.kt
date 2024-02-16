package com.qibla.qiblacompass.prayertime.finddirection.common

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.qibla.qiblacompass.prayertime.finddirection.R
import java.util.Calendar

class PrayerReminder(val context: Context, val prayerTimes: List<String>) {

    fun setAlarms(){
            Log.d(PrayerReminder::class.simpleName, "setAlarms: ")
//            createAlarm(1, prayerTimes.fajr, context.getString(R.string.fajr))
//            createAlarm(2, prayerTimes.dhuhr, context.getString(R.string.zuhar))
//            createAlarm(3, prayerTimes.asr, context.getString(R.string.asr))
//            createAlarm(4, prayerTimes.maghrib, context.getString(R.string.maghrib))
//            createAlarm(5, prayerTimes.isha, context.getString(R.string.isha))
    }

    private fun createAlarm(id: Int, time:String, prayerName: String){
        val now = Calendar.getInstance().timeInMillis
        val timeParts = time.split(":")
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
        calendar.set(Calendar.MINUTE, timeParts[1].toInt())
        calendar.set(Calendar.SECOND, 0)
        if (calendar.timeInMillis < now){
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
//        val intent = Intent(context, AlarmReceiver::class.java)
//        intent.putExtra("title", context.getString(R.string.prayer_notification_title, prayerName))
//        intent.putExtra("message", context.getString(R.string.prayer_notification_message, prayerName))
//        val pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT)
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    private fun cancelAlarm(id: Int, prayerName: String){
//        val intent = Intent(context, AlarmReceiver::class.java)
//        intent.putExtra("title", context.getString(R.string.prayer_notification_title, prayerName))
//        intent.putExtra("message", context.getString(R.string.prayer_notification_message, prayerName))
//        val pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT)
//        alarmManager.cancel(pendingIntent)

    }


}