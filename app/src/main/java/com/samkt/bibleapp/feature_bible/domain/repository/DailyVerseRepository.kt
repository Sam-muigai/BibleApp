package com.samkt.bibleapp.feature_bible.domain.repository


interface DailyVerseRepository {
    suspend fun getVerseReminder()
}