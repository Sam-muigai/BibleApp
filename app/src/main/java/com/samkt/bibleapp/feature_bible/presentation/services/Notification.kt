package com.samkt.bibleapp.feature_bible.presentation.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.samkt.bibleapp.MainActivity
import com.samkt.bibleapp.R

class GreetingNotification(
    private val context: Context,
    private val verse:String,
    private val reference:String,
){

    private val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    private val pendingIntent: PendingIntent =
        PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

    private val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_book)
        .setContentTitle(reference)
        .setContentText(verse)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    private val notificationManager = NotificationManagerCompat.from(context)

     fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val channelDescription = "Good morning text"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = channelDescription
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun showNotification(){
        notificationManager.notify(NOTIF_ID,builder.build())
    }

    companion object{
        const val CHANNEL_ID = "channelID"
        const val CHANNEL_NAME = "channelName"
        const val NOTIF_ID = 0
    }
}