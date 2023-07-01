package com.samkt.bibleapp.feature_bible.domain.repository

import com.samkt.bibleapp.feature_bible.data.dto.books.BookDataDto
import com.samkt.bibleapp.feature_bible.data.dto.chapter.ChapterDataDto
import com.samkt.bibleapp.feature_bible.data.dto.daily_verse.Details
import com.samkt.bibleapp.feature_bible.data.dto.verses.VerseDataDto
import com.samkt.bibleapp.feature_bible.data.dto.verses.VerseDto
import com.samkt.bibleapp.feature_bible.util.Resources
import kotlinx.coroutines.flow.Flow


typealias BookResponse = Flow<Resources<List<BookDataDto>>>

typealias ChapterResponse = Flow<Resources<List<ChapterDataDto>>>

typealias VersesResponse = Flow<Resources<VerseDataDto>>


interface BibleRepository{

    fun getAllBooks(): BookResponse

    fun getAllChapters(bookId:String): ChapterResponse

    suspend fun getVerseReminder()

    fun getVerses(chapterId:String):VersesResponse


}