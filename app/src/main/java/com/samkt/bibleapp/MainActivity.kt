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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.samkt.bibleapp.feature_bible.presentation.book_screen.BookScreen
import com.samkt.bibleapp.feature_bible.presentation.book_screen.BookScreenViewModel
import com.samkt.bibleapp.feature_bible.presentation.chapter_screen.ChapterScreen
import com.samkt.bibleapp.feature_bible.presentation.services.GreetingNotification
import com.samkt.bibleapp.feature_bible.presentation.workers.GetVerseWorker
import com.samkt.bibleapp.feature_bible.presentation.workers.GreetingWorker
import com.samkt.bibleapp.ui.theme.BibleAppTheme
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Route
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BibleAppTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<MainViewModel>()
    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.getTodayVerse()
        }
    )
    NavHost(navController = navController, startDestination = Routes.BOOK_SCREEN) {
        composable(route = Routes.BOOK_SCREEN) {
            BookScreen { id, book ->
                navController.navigate(Routes.CHAPTER_SCREEN + "?chapterId=$id?book=$book")
            }
        }
        composable(
            route = Routes.CHAPTER_SCREEN + "?chapterId={chapterId}?book={book}",
            arguments = listOf(
                navArgument("chapterId") {
                    type = NavType.StringType
                },
                navArgument("book"){
                    type = NavType.StringType
                }
            )) {
            val book = it.arguments?.getString("book") ?: ""
            ChapterScreen(book = book)
        }
    }
}


object Routes {
    const val BOOK_SCREEN = "book_screen"

    const val CHAPTER_SCREEN = "chapter_screen"
}


