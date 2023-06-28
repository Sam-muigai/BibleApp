package com.samkt.bibleapp.feature_bible.data.dto.verses

data class VerseDataDto(
    val bibleId: String,
    val bookId: String,
    val content: String,
    val copyright: String,
    val id: String,
    val next: NextDto,
    val number: String,
    val previous: PreviousDto,
    val reference: String,
    val verseCount: Int
)