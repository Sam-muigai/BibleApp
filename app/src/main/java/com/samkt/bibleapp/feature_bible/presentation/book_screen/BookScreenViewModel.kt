package com.samkt.bibleapp.feature_bible.presentation.book_screen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.bibleapp.feature_bible.domain.repository.BibleRepository
import com.samkt.bibleapp.feature_bible.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookScreenViewModel @Inject constructor(
    private val repository: BibleRepository
) : ViewModel() {

    private var _screenState = MutableStateFlow(BookScreenState())
    val screenState = _screenState.asStateFlow()


    init {
        getAllBooks()

    }


    fun getTodayVerse() {
        viewModelScope.launch {
            delay(3000)
            repository.getVerseReminder()
        }
    }


    private fun getAllBooks() {
        viewModelScope.launch {
            repository.getAllBooks().onEach {
                when (it) {
                    is Resources.Loading -> {
                        _screenState.value = _screenState.value.copy(
                            loading = true
                        )
                    }

                    is Resources.Success -> {
                        _screenState.value = _screenState.value.copy(
                            loading = false,
                            data = it.data ?: emptyList()
                        )

                    }

                    is Resources.Error -> {
                        _screenState.value = _screenState.value.copy(
                            loading = false,
                            errorMessage = it.message
                        )
                    }
                }

            }.launchIn(this)

        }
    }



}