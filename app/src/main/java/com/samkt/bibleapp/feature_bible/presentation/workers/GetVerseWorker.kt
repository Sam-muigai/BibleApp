package com.samkt.bibleapp.feature_bible.presentation.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkContinuation
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.samkt.bibleapp.feature_bible.domain.repository.BibleRepository
import com.samkt.bibleapp.feature_bible.presentation.services.GreetingNotification
import com.samkt.bibleapp.feature_bible.util.Resources
import kotlinx.coroutines.delay
import java.lang.Exception
import javax.inject.Inject

const val VERSE = "verse"
const val REFERENCE = "reference"


class GetVerseWorker @Inject constructor(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val verse = inputData.getString(VERSE) ?: "No verse today"
        val reference = inputData.getString(REFERENCE) ?: "No verse today"

        val notification by lazy {
            GreetingNotification(
                context = applicationContext,
                verse = verse,
                reference = reference
            )
        }
        return try {

            notification.createNotification()

            delay(3000)

            notification.showNotification()

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}