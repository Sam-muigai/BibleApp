package com.samkt.bibleapp.feature_bible.presentation.book_screen


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.bibleapp.feature_bible.domain.repository.BibleRepository
import com.samkt.bibleapp.feature_bible.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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

    private fun getAllBooks() {
        viewModelScope.launch {
            val verse = async {
                repository.getDailyVerse().onEach {
                    when (it) {
                        is Resources.Loading -> {
                            _screenState.value = _screenState.value.copy(
                                verse_loading = true
                            )
                            Log.d("DailyVerse", "loading")
                        }

                        is Resources.Success -> {
                            _screenState.value = _screenState.value.copy(
                                verse_loading = false,
                                verse = it.data
                            )
                            Log.d("DailyVerse", it.data?.text ?: "")
                        }

                        is Resources.Error -> {
                            _screenState.value = _screenState.value.copy(
                                verse_loading = false,
                                errorMessage = it.message
                            )
                            Log.d("DailyVerse", "error_occurred")
                        }
                    }
                }.launchIn(this)

            }
            val books = async {
                repository.getAllBooks().onEach {
                    when (it) {
                        is Resources.Loading -> {
                            _screenState.value = _screenState.value.copy(
                                book_loading = true
                            )
                        }

                        is Resources.Success -> {
                            _screenState.value = _screenState.value.copy(
                                book_loading = false,
                                books = it.data ?: emptyList()
                            )

                        }

                        is Resources.Error -> {
                            _screenState.value = _screenState.value.copy(
                                book_loading = false,
                                errorMessage = it.message
                            )
                        }
                    }

                }.launchIn(this)
            }
        }
    }


//    private fun getAllBooks() {
//        viewModelScope.launch {
//            val books = async {
//                repository.getAllBooks().onEach {
//                    when (it) {
//                        is Resources.Loading -> {
//                            _screenState.value = _screenState.value.copy(
//                                book_loading = true
//                            )
//                        }
//
//                        is Resources.Success -> {
//                            _screenState.value = _screenState.value.copy(
//                                book_loading = false,
//                                books = it.data ?: emptyList()
//                            )
//
//                        }
//
//                        is Resources.Error -> {
//                            _screenState.value = _screenState.value.copy(
//                                book_loading = false,
//                                errorMessage = it.message
//                            )
//                        }
//                    }
//
//                }.launchIn(this)
//            }
//            val dailyVerse  = async {
//                repository.getDailyVerse().onEach {
//                    when (it) {
//                        is Resources.Loading -> {
//                           _screenState.value = _screenState.value.copy(
//                               verse_loading = true
//                           )
//                            Log.d("DailyVerse","loading")
//                        }
//
//                        is Resources.Success -> {
//                            _screenState.value = _screenState.value.copy(
//                                verse_loading = false,
//                                verse = it.data
//                            )
//                            Log.d("DailyVerse",it.data?.text ?: "")
//                        }
//
//                        is Resources.Error -> {
//                           _screenState.value = _screenState.value.copy(
//                               verse_loading = false,
//                               errorMessage = it.message
//                           )
//                            Log.d("DailyVerse","error_occurred")
//                        }
//                    }
//                }
//            }
//            books.await()
//            dailyVerse.await()
//        }
//    }


}