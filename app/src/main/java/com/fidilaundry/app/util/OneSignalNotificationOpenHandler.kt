package com.fidilaundry.app.util

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.onesignal.OSNotificationOpenedResult
import com.onesignal.OneSignal

class OneSignalNotificationOpenHandler(private val context : Context) : OneSignal.OSNotificationOpenedHandler {

    override fun notificationOpened(result: OSNotificationOpenedResult?) {
        if (result == null) return
        val type = result.action.type
        val data = result.notification.additionalData

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val notifsActive = notificationManager.activeNotifications
            for (notif in notifsActive) {
            }
        } else {

        }
    }
}