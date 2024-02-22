package com.qibla.qiblacompass.prayertime.finddirection.common

import android.app.AlarmManager
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.qibla.qiblacompass.prayertime.finddirection.BuildConfig
import com.qibla.qiblacompass.prayertime.finddirection.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
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

        fun convertTimeToUnixTime(time:String): Long {
            val timePart = time.split(" ")
            val timeParts = timePart[0].split(":")
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
            calendar.set(Calendar.MINUTE, timeParts[1].toInt())
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.timeInMillis
        }


        fun convertTimeToUnixTimeDay(time:String): Long {
            val timePart = time.split(" ")
            val timeParts = timePart[0].split(":")
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_MONTH,1)
            calendar.set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
            calendar.set(Calendar.MINUTE, timeParts[1].toInt())
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.timeInMillis
        }
         fun formatTime(seconds: Long): String {
            val hours = seconds / 3600
            val minutes = (seconds % 3600) / 60
            val secondsRemaining = seconds % 60
            return String.format("%02d hr %02d min %02d sec", hours, minutes, secondsRemaining)
        }

        fun getCurrentDateFormatted():String{
            val formatter = SimpleDateFormat("E, d MMMM ")
            val current = formatter.format(Date())
            return current
        }

        fun rateApp(ctx: Context) {
            val uri = Uri.parse("market://details?id=" + ctx.packageName)
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            try {
                ctx.startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(ctx, "Couldn't launch the market", Toast.LENGTH_LONG).show()
            }

            //AppRatingDialog.setInstalltionTime(Long.MAX_VALUE); //never show the rating dialog :)
        }

        fun shareApp(ctx: Context) {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, ctx.getString(R.string.app_name))
                var shareMessage =
                    ctx.getString(R.string.salam_download_the_best_muslim_app_for_free_now_at)
                shareMessage =
                    """ ${shareMessage} https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                """.trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                ctx.startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                Log.d(CommonMethods::class.simpleName, "shareApp: ${e.message}")
                //e.toString();
            }
        }

        fun canScheduleExactAlarms(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                Log.d(CommonMethods::class.simpleName, "canScheduleExactAlarms: ${am.canScheduleExactAlarms()} ")
                return am.canScheduleExactAlarms()
            } else {
                true
            }
        }

        fun enableBootReceiver(context:Context){
            val receiver = ComponentName(context, SampleBootReceiver::class.java)

            context.packageManager.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        }


//        private fun addAlarm(
//            alarmId: Int,
//            routineId: String,
//            timeInSec: Long,
//            isNotification: Boolean = false
//        ) {
//            if (timeInSec != 0L) {
//
//                val alarmTimeInMilliSec = (timeInSec - DateTimeUtil.getParentOffset()) * 1000
//
//                if (DateTimeUtil.getParentTimestamp() > timeInSec) {
//                    LogUtil.e("routine time is already passed")
//                    return
//                }
//
//                LogUtil.i(
//                    "$TAG " + String.format(
//                        "setting alarm (%d) for routine (%s) on time (%s)",
//                        alarmId,
//                        routineId,
//                        Date(alarmTimeInMilliSec)
//                    )
//
//                )
//
//                val deltaTimeInMinsUpdated =
//                    (alarmTimeInMilliSec - System.currentTimeMillis()) / (1000 * 60)
//                LogUtil.i(
//                    "$TAG " + String.format(
//                        "delta in min(s)=%d",
//                        deltaTimeInMinsUpdated
//                    )
//                )
//
//                val am = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//                val intent = if (isNotification) getPendingRoutineReminderNotificationIntent(
//                    alarmId,
//                    routineId
//                ) else getPendingIntent(alarmId, routineId)
//
//                try {
//                    if (AppUtil.canScheduleExactAlarms(context)) {
//                        am.setAlarmClock(
//                            AlarmManager.AlarmClockInfo(
//                                alarmTimeInMilliSec,
//                                intent
//                            ), intent
//                        )
//                    } else {
//                        LogUtil.e("exact alarms permission not granted")
////                    am.setExact(
////                        AlarmManager.RTC_WAKEUP,
////                        alarmTimeInMilliSec,
////                        intent
////                    )
//                    }
//                } catch (e: SecurityException) {
//                    LogUtil.e("error in setting alarm for routine $routineId")
//                    Sentry.captureException(e)
//                    e.printStackTrace()
//                }
//            }
//        }
    }
}