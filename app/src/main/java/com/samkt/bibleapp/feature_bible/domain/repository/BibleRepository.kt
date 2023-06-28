package com.samkt.bibleapp.feature_bible.domain.repository

import com.samkt.bibleapp.feature_bible.data.dto.books.BookDataDto
import com.samkt.bibleapp.feature_bible.data.dto.chapter.ChapterDataDto
import com.samkt.bibleapp.feature_bible.data.dto.verses.VerseDto
import com.samkt.bibleapp.feature_bible.util.Resources
import kotlinx.coroutines.flow.Flow

interface BibleRepository{

    fun getAllBooks(): Flow<Resources<List<BookDataDto>>>

    fun getAllChapters(bookId:String):Flow<Resources<List<ChapterDataDto>>>

    suspend fun getVerse(chapterId:String):VerseDto

}