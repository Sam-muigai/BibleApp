package com.samkt.bibleapp.feature_bible.presentation.workers

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.samkt.bibleapp.MainActivity
import com.samkt.bibleapp.feature_bible.presentation.services.GreetingNotification
import kotlinx.coroutines.delay
import java.lang.Exception

class GreetingWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val verse = inputData.getString(VERSE) ?: "No verse today"
        val reference = inputData.getString(REFERENCE) ?: "No verse today"
        val notification by lazy {
            GreetingNotification(
                context = applicationContext,
                reference = reference,
                verse = verse
            )
        }
        return try {
            notification.createNotification()
            delay(1000)
            notification.showNotification()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}