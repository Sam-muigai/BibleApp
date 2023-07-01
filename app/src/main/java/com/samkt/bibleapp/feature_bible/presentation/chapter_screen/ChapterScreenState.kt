package com.samkt.bibleapp.feature_bible.presentation.chapter_screen

import com.samkt.bibleapp.feature_bible.data.dto.chapter.ChapterDataDto

data class ChapterScreenState(
    val data:List<ChapterDataDto> = emptyList(),
    val loading:Boolean = false,
    val errorMessage:String? = null

)
