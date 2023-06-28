package com.samkt.bibleapp.feature_bible.util

sealed class Resources<out T>(val data:T? = null,val message:String? = null){
    class Success<T>(data: T?):Resources<T>(data = data)
    class Error(message: String?):Resources<Nothing>(message = message)
    object Loading:Resources<Nothing>()
}
