package com.fps69.abworkmanager.Api

import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import com.fps69.abworkmanager.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class NotificationService : FirebaseMessagingService(){

    var chanelID ="AbWorkManager"

    override fun onMessageReceived(message: RemoteMessage) {

        val manager = getSystemService(NOTIFICATION_SERVICE)
        // Creating channel
        createNotificationChannel(manager as NotificationManager)


        val notification = NotificationCompat.Builder(this, chanelID)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["body"])
            .setSmallIcon(R.drawable.logo4)
            .setAutoCancel(false)
            .setContentIntent(null)
            .build()


        manager.notify(Random.nextInt(),notification)
    }

    private fun createNotificationChannel(manager: NotificationManager) {
        val chanel = NotificationChannel(chanelID,"abWorkManager", NotificationManager.IMPORTANCE_HIGH)
            .apply {
                description = "New Work"
                enableLights(true)
                enableVibration(true)
            }
        manager.createNotificationChannel(chanel)
    }
}