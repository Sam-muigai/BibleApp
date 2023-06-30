package com.samkt.bibleapp.feature_bible.data.repository

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.samkt.bibleapp.feature_bible.data.BibleApi
import com.samkt.bibleapp.feature_bible.data.DailyVerseApi
import com.samkt.bibleapp.feature_bible.data.dto.books.BookDataDto
import com.samkt.bibleapp.feature_bible.data.dto.chapter.ChapterDataDto
import com.samkt.bibleapp.feature_bible.data.dto.verses.VerseDto
import com.samkt.bibleapp.feature_bible.domain.repository.BibleRepository
import com.samkt.bibleapp.feature_bible.domain.repository.BookResponse
import com.samkt.bibleapp.feature_bible.domain.repository.ChapterResponse
import com.samkt.bibleapp.feature_bible.presentation.workers.GetVerseWorker
import com.samkt.bibleapp.feature_bible.presentation.workers.GreetingWorker
import com.samkt.bibleapp.feature_bible.presentation.workers.REFERENCE
import com.samkt.bibleapp.feature_bible.presentation.workers.VERSE
import com.samkt.bibleapp.feature_bible.util.Resources
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BibleRepositoryImpl @Inject constructor(
    private val api: BibleApi,
    private val dailyVerseApi: DailyVerseApi,
    private val context: Context
) : BibleRepository {
    override fun getAllBooks(): BookResponse = flow {
        emit(Resources.Loading)
        try {
            val books = api.getAllBooks().data
            emit(Resources.Success(books))
        } catch (e: IOException) {
            emit(Resources.Error("No internet connection"))
        } catch (e: Exception) {
            emit(Resources.Error(e.localizedMessage ?: "Unknown error occurred"))
        }
    }

    override fun getAllChapters(bookId: String): ChapterResponse = flow {
        emit(Resources.Loading)
        try {
            val chapters = api.getAllChapters(bookId = bookId).data
            emit(Resources.Success(chapters))
        } catch (e: IOException) {
            emit(Resources.Error("No internet connection"))
        } catch (e: Exception) {
            emit(Resources.Error(e.localizedMessage ?: "Unknown error occurred"))
        }
    }

    override suspend fun getVerse(chapterId: String): VerseDto {
        TODO("Not yet implemented")
    }


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

        val getVerseWorker = OneTimeWorkRequestBuilder<GetVerseWorker>()
            .setInputData(data)
            .setConstraints(constraints)
            .addTag("getVerseWork")
            .build()

        workManager.enqueueUniqueWork(
            "get_verse_work",
            ExistingWorkPolicy.KEEP,
            getVerseWorker
        )

//        val getPeriodicVerseWorker = PeriodicWorkRequestBuilder<GetVerseWorker>(15, TimeUnit.SECONDS)
//            .setInputData(data)
//            .setConstraints(constraints)
//            .addTag("getVerseWork")
//            .build()

//        workManager.enqueueUniquePeriodicWork(
//            "get_verses_work",
//            ExistingPeriodicWorkPolicy.KEEP,
//            getPeriodicVerseWorker
//        )


    }
}