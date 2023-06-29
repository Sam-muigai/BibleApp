package com.samkt.bibleapp.feature_bible.data

import com.samkt.bibleapp.feature_bible.data.dto.daily_verse.DailyVerseDto
import retrofit2.http.GET


interface DailyVerseApi {

    @GET(Endpoints.DAILY_VERSE)
    suspend fun getDailyVerse(): DailyVerseDto


    companion object {
        const val BASE_URL = "https://beta.ourmanna.com"
    }

}