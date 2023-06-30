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
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.samkt.bibleapp.feature_bible.presentation.book_screen.BookScreen
import com.samkt.bibleapp.feature_bible.presentation.book_screen.BookScreenViewModel
import com.samkt.bibleapp.feature_bible.presentation.services.GreetingNotification
import com.samkt.bibleapp.feature_bible.presentation.workers.GetVerseWorker
import com.samkt.bibleapp.feature_bible.presentation.workers.GreetingWorker
import com.samkt.bibleapp.ui.theme.BibleAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<BookScreenViewModel>()
            BibleAppTheme {
                LaunchedEffect(
                    key1 = Unit,
                    block = {
                        viewModel.getTodayVerse()
                    }
                )
                BookScreen(
                    onClick = {

                    }
                )
            }
        }
    }
}





