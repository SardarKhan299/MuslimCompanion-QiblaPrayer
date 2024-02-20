package com.qibla.qiblacompass.prayertime.finddirection.common

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.SplashActivity

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val namazTitle = intent?.getStringExtra("title") ?: return

        Log.d(AlarmReceiver::class.simpleName, "onReceive: Alarm Time $namazTitle")
        context?.let { ctx ->

            val alarmSoundUri =
                Uri.parse("android.resource://" + ctx.packageName + "/" + R.raw.tasbih_sound)

            val intent = Intent(ctx, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            lateinit var pendingIntent : PendingIntent

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getActivity(ctx, 1200, intent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
            } else {
                pendingIntent = PendingIntent.getActivity(ctx, 1200, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            }
            createChannel(ctx, alarmSoundUri)
            var notificationBuilder: NotificationCompat.Builder? = null
            notificationBuilder =
                NotificationCompat.Builder(ctx, ctx.getString(R.string.notification_channel_id))
                    .setContentText("Namaz Time $namazTitle")
                    .setContentTitle("Its Time for $namazTitle Prayer.")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setOngoing(false)
                    .setAutoCancel(true)
                    .setSound(alarmSoundUri)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setWhen(System.currentTimeMillis())

            val notificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            var incomingCallNotification: Notification? = null
            if (notificationBuilder != null) {
                incomingCallNotification = notificationBuilder.build()
            }

            notificationManager.notify(
                0 /* ID of notification */,
                incomingCallNotification
            )

        }
    }

    fun createChannel(context: Context, alarmSoundUri: Uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
            val channel = NotificationChannel(
                 context.getString(R.string.notification_channel_id),
                context.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Voip Notifications Guard"
            channel.setShowBadge(true)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            channel.enableLights(true)
            channel.enableVibration(true)
            channel.setSound(alarmSoundUri, audioAttributes)

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}
