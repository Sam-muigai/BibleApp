package com.samkt.bibleapp.feature_bible.data.repository

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.samkt.bibleapp.feature_bible.data.DailyVerseApi
import com.samkt.bibleapp.feature_bible.domain.repository.DailyVerseRepository
import com.samkt.bibleapp.feature_bible.presentation.workers.GetVerseWorker
import com.samkt.bibleapp.feature_bible.presentation.workers.REFERENCE
import com.samkt.bibleapp.feature_bible.presentation.workers.VERSE
import java.util.concurrent.TimeUnit

class DailyVerseRepositoryImpl(
    private val context: Context,
    private val dailyVerseApi: DailyVerseApi
) : DailyVerseRepository {
    override suspend fun getVerseReminder() {
        val workManager by lazy {
            WorkManager.getInstance(context)
        }
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val verseFromApi = dailyVerseApi.getDailyVerse().verse.details.text
        val referenceFromApi = dailyVerseApi.getDailyVerse().verse.details.reference

        val data = Data.Builder()
            .putString(VERSE, verseFromApi)
            .putString(REFERENCE, referenceFromApi)
            .build()


        val getPeriodicVerseWorker = PeriodicWorkRequestBuilder<GetVerseWorker>(1, TimeUnit.HOURS)
            .setInputData(data)
            .setConstraints(constraints)
            .addTag("getVerseWork")
            .build()

        workManager.enqueueUniquePeriodicWork(
            "get_verses_work",
            ExistingPeriodicWorkPolicy.KEEP,
            getPeriodicVerseWorker
        )
    }
}