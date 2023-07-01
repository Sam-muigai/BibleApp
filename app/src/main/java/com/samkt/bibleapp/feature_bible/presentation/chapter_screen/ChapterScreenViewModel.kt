package com.samkt.bibleapp.feature_bible.presentation.chapter_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.bibleapp.feature_bible.domain.repository.BibleRepository
import com.samkt.bibleapp.feature_bible.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChapterScreenViewModel @Inject constructor(
    private val repository: BibleRepository,
    savedStateHandle: SavedStateHandle
):ViewModel(){

    private var _state = MutableStateFlow(ChapterScreenState())
    val state = _state.asStateFlow()

    init {
        val bookId = savedStateHandle.get<String>("chapterId")
        getAllChapters(bookId = bookId)
    }

    private fun getAllChapters(bookId: String?) {
        bookId?.let { id ->
            viewModelScope.launch {
                repository.getAllChapters(id).onEach{
                    when(it){
                        is Resources.Success ->{
                            val chapters = it.data?.filter {chapterData ->
                                chapterData.number != "intro"
                            }
                            _state.value = ChapterScreenState(data = chapters ?: emptyList())
                        }
                        is Resources.Error -> {
                            _state.value = ChapterScreenState(errorMessage = it.message)
                        }
                        is Resources.Loading ->{
                            _state.value = ChapterScreenState(loading = true)
                            ///Loading happens fast a little delay helps simulate loading
                            delay(2000)
                        }
                    }
                }.launchIn(this)
            }
        } ?: run { _state.value = ChapterScreenState(errorMessage = "null id") }
    }


}