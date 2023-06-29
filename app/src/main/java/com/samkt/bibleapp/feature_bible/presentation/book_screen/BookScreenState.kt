package com.samkt.bibleapp.feature_bible.presentation.book_screen


import com.samkt.bibleapp.feature_bible.data.dto.books.BookDataDto
import com.samkt.bibleapp.feature_bible.data.dto.daily_verse.Details

data class BookScreenState(
    val book_loading:Boolean = false,
    val verse_loading:Boolean = false,
    val verse: Details? = null,
    val books:List<BookDataDto> = emptyList(),
    val errorMessage:String? = null
)