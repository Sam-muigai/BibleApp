package com.samkt.bibleapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.bibleapp.feature_bible.domain.repository.DailyVerseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DailyVerseRepository
):ViewModel(){

    fun getTodayVerse() {
        viewModelScope.launch {
            delay(3000)
            repository.getVerseReminder()
        }
    }

}