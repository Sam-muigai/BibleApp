package com.samkt.bibleapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.samkt.bibleapp.feature_bible.presentation.book_screen.BookScreen
import com.samkt.bibleapp.feature_bible.presentation.services.GreetingNotification
import com.samkt.bibleapp.feature_bible.presentation.workers.GreetingWorker
import com.samkt.bibleapp.ui.theme.BibleAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val workManager by lazy {
        WorkManager.getInstance(this)
    }
    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresStorageNotLow(true)
        .setRequiresBatteryNotLow(true)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        createPeriodicWorkRequest()
        setContent {
            BibleAppTheme {
                BookScreen()
            }
        }
    }

    private fun createOneTimeWorkRequest() {
        // 1
        val greetingWorker = OneTimeWorkRequestBuilder<GreetingWorker>()
            .setConstraints(constraints)
            .addTag("imageWork")
            .build()
        // 2
        workManager.enqueueUniqueWork(
            "oneTimeImageDownload",
            ExistingWorkPolicy.KEEP,
            greetingWorker
        )
    }

    private fun createPeriodicWorkRequest() {
        // 1
        val greetingWorker = PeriodicWorkRequestBuilder<GreetingWorker>(
            12, TimeUnit.HOURS)
            .setConstraints(constraints)
            .addTag("imageWork")
            .build()
        // 2
        workManager.enqueueUniquePeriodicWork(
            "oneTimeImageDownload",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            greetingWorker
        )
    }


}





