package com.samkt.bibleapp.feature_bible.data.repository

import android.util.Log
import com.samkt.bibleapp.feature_bible.data.BibleApi
import com.samkt.bibleapp.feature_bible.data.DailyVerseApi
import com.samkt.bibleapp.feature_bible.data.dto.books.BookDataDto
import com.samkt.bibleapp.feature_bible.data.dto.chapter.ChapterDataDto
import com.samkt.bibleapp.feature_bible.data.dto.verses.VerseDto
import com.samkt.bibleapp.feature_bible.domain.repository.BibleRepository
import com.samkt.bibleapp.feature_bible.domain.repository.BookResponse
import com.samkt.bibleapp.feature_bible.domain.repository.ChapterResponse
import com.samkt.bibleapp.feature_bible.domain.repository.DailyVerseResponse
import com.samkt.bibleapp.feature_bible.util.Resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class BibleRepositoryImpl @Inject constructor(
    private val api: BibleApi,
    private val dailyVerseApi:DailyVerseApi
):BibleRepository {
    override fun getAllBooks(): BookResponse = flow{
        emit(Resources.Loading)
        try {
            val books = api.getAllBooks().data
            emit(Resources.Success(books))
        }catch (e: IOException){
            emit(Resources.Error("No internet connection"))
        }catch (e :Exception){
            emit(Resources.Error(e.localizedMessage ?: "Unknown error occurred"))
        }
    }

    override fun getAllChapters(bookId: String): ChapterResponse = flow {
        emit(Resources.Loading)
        try {
            val chapters = api.getAllChapters(bookId = bookId).data
            emit(Resources.Success(chapters))
        }catch (e: IOException){
            emit(Resources.Error("No internet connection"))
        }catch (e :Exception){
            emit(Resources.Error(e.localizedMessage ?: "Unknown error occurred"))
        }
    }

    override suspend fun getVerse(chapterId: String): VerseDto {
        TODO("Not yet implemented")
    }
    override fun getDailyVerse(): DailyVerseResponse  = flow{
        emit(Resources.Loading)
        try {
            val details = dailyVerseApi.getDailyVerse().verse.details
            emit(Resources.Success(details))
            Log.d("DailyVerse","$details")
        }catch (e: IOException){
            emit(Resources.Error("No internet connection"))
        }catch (e :Exception){
            emit(Resources.Error(e.localizedMessage ?: "Unknown error occurred"))
        }
    }
}