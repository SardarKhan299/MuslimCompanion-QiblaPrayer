package com.qibla.qiblacompass.prayertime.finddirection.common

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.qibla.qiblacompass.prayertime.finddirection.R
import java.util.Calendar

class PrayerReminder(val context: Context, val prayerTimes: List<String>) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setAlarms(){
            Log.d(PrayerReminder::class.simpleName, "setAlarms: ")
            createAlarm(1, prayerTimes[0], context.getString(R.string.fajr))
            createAlarm(2, prayerTimes[1], context.getString(R.string.zuhar))
            createAlarm(3, prayerTimes[2], context.getString(R.string.asr))
            createAlarm(4, prayerTimes[3], context.getString(R.string.maghrib))
            createAlarm(5, prayerTimes[4], context.getString(R.string.isha))
    }

    private fun createAlarm(id: Int, time:String, prayerName: String){

        try {
            val now = Calendar.getInstance().timeInMillis
            val timePart = time.split(" ")
            val timeParts = timePart[0].split(":")
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
            calendar.set(Calendar.MINUTE, timeParts[1].toInt())
            calendar.set(Calendar.SECOND, 0)

            Log.d(PrayerReminder::class.simpleName, "createAlarm: ${calendar.timeInMillis}")

            if(calendar.timeInMillis<now){
                Log.d(PrayerReminder::class.simpleName, "createAlarm: No need to set alarm for $prayerName")
            }else {
                Log.d(PrayerReminder::class.simpleName, "createAlarm: Set Alarm For $prayerName at ${calendar.timeInMillis}")
                val intent = Intent(context, AlarmReceiver::class.java)
                intent.putExtra("title", prayerName)
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    id,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )



                if (canScheduleExactAlarms(context)) {
                    Log.d(
                        PrayerReminder::class.simpleName,
                        "createAlarm: Can schedule exact Alaram"
                    )
                    alarmManager.setAlarmClock(
                        AlarmManager.AlarmClockInfo(
                            calendar.timeInMillis,
                            pendingIntent
                        ), pendingIntent
                    )
                } else {
                    Log.d(PrayerReminder::class.simpleName, "createAlarm: No permission...")
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                }
            }

        }catch (e:Exception){
            Log.d(PrayerReminder::class.simpleName, "createAlarm: ${e.message}")
        }

    }

    private fun cancelAlarm(id: Int, prayerName: String){
//        val intent = Intent(context, AlarmReceiver::class.java)
//        intent.putExtra("title", context.getString(R.string.prayer_notification_title, prayerName))
//        intent.putExtra("message", context.getString(R.string.prayer_notification_message, prayerName))
//        val pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT)
//        alarmManager.cancel(pendingIntent)

    }

    fun canScheduleExactAlarms(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            return am.canScheduleExactAlarms()
        } else {
            true
        }
    }


}