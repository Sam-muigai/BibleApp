package com.samkt.bibleapp.feature_bible.presentation.workers

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.samkt.bibleapp.MainActivity
import com.samkt.bibleapp.feature_bible.presentation.services.GreetingNotification
import kotlinx.coroutines.delay

class GreetingWorker(appContext: Context, workerParams: WorkerParameters):CoroutineWorker(appContext,workerParams) {

    private val intent = Intent(appContext, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    private val pendingIntent: PendingIntent =
        PendingIntent.getActivity(appContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

    private val notification by lazy {
        GreetingNotification(appContext,pendingIntent)
    }

    override suspend fun doWork(): Result {
        notification.createNotification()
        delay(2000)
        notification.showNotification()
        return Result.success()
    }

}