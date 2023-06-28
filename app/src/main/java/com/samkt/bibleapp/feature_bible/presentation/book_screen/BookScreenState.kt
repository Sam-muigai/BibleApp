package com.samkt.bibleapp.feature_bible.presentation.book_screen

import com.samkt.bibleapp.feature_bible.data.dto.books.BookDataDto

data class BookScreenState(
    val loading:Boolean = false,
    val data:List<BookDataDto> = emptyList(),
    val errorMessage:String? = null
)