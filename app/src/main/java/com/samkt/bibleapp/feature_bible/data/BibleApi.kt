package com.samkt.bibleapp.feature_bible.data

import com.samkt.bibleapp.feature_bible.data.dto.books.BookDto
import com.samkt.bibleapp.feature_bible.data.dto.chapter.ChapterDto
import com.samkt.bibleapp.feature_bible.data.dto.verses.VerseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface BibleApi {

    @GET(Endpoints.BOOKS)
    suspend fun getAllBooks(
        @Path("bibleId") bibleId:String = KING_JAMES
    ):BookDto


    @GET(Endpoints.CHAPTERS)
    suspend fun getAllChapters(
        @Path("bibleId") bibleId: String = KING_JAMES,
        @Path("bookId") bookId: String
    ):ChapterDto


    @GET(Endpoints.VERSES)
    suspend fun getAllVerses(
        @Path("bibleId") bibleId: String = KING_JAMES,
        @Path("chapterId") chapterId:String
    ):VerseDto



    companion object {
        const val KING_JAMES = "de4e12af7f28f599-01"
        const val BASE_URL = "https://api.scripture.api.bible"
    }
}