package com.fiap.meurole.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.fiap.meurole.R

object NotificationUtils {

    data class NotifChannel(val id: String, val name: String, val description: String)

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context, notifChannel: NotifChannel) {

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            notifChannel.id,
            notifChannel.name,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = notifChannel.description
            enableLights(true)
            enableVibration(true)
            vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        }
        notificationManager.createNotificationChannel(channel)
    }

    fun notificationSimple(
        context: Context,
        title: String,
        message: String,
        notifChannel: NotifChannel,
        intent: Intent
    ) {
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_ONE_SHOT
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context, notifChannel)
        }
        val notificationBuilder = NotificationCompat.Builder(
            context,
            notifChannel.id
        )
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setColor(
                ActivityCompat.getColor(
                    context,
                    R.color.cello
                )
            )
            .setDefaults(Notification.DEFAULT_ALL)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1, notificationBuilder.build())
    }
}