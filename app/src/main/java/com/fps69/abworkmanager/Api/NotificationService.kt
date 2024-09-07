package com.fps69.abworkmanager.Api

import android.app.NotificationChannel
import android.app.NotificationManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService : FirebaseMessagingService(){


    var chanelID ="AbWorkManager"

    override fun onMessageReceived(message: RemoteMessage) {

        val manager = getSystemService(NOTIFICATION_SERVICE)
        // Creating channel
        createNotificationChannel(manager as NotificationManager)
    }

    private fun createNotificationChannel(manager: NotificationManager) {
        val chanel = NotificationChannel(chanelID,"abWorkManager", NotificationManager.IMPORTANCE_HIGH)
            .apply {
                description = "New Work"
                enableLights(true)
            }
        manager.createNotificationChannel(chanel)
    }
}