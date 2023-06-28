package com.samkt.bibleapp.feature_bible.data.repository

import com.samkt.bibleapp.feature_bible.data.BibleApi
import com.samkt.bibleapp.feature_bible.data.dto.books.BookDataDto
import com.samkt.bibleapp.feature_bible.data.dto.chapter.ChapterDataDto
import com.samkt.bibleapp.feature_bible.data.dto.verses.VerseDto
import com.samkt.bibleapp.feature_bible.domain.repository.BibleRepository
import com.samkt.bibleapp.feature_bible.util.Resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class BibleRepositoryImpl @Inject constructor(
    private val api: BibleApi
):BibleRepository {
    override fun getAllBooks(): Flow<Resources<List<BookDataDto>>> = flow{
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

    override fun getAllChapters(bookId: String): Flow<Resources<List<ChapterDataDto>>> = flow {
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
}