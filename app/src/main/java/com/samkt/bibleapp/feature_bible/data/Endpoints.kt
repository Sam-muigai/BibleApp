package com.samkt.bibleapp.feature_bible.data

object Endpoints{

    const val BOOKS = "/v1/bibles/{bibleId}/books"

    const val CHAPTERS = "/v1/bibles/{bibleId}/books/{bookId}/chapters"

    const val VERSES = "v1/bibles/{bibleId}/chapters/{chapterId}"
}