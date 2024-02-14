package com.qibla.qiblacompass.prayertime.finddirection.common

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
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
    }
}