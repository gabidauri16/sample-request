package com.example.requestsample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.requestsample.domain.usecases.GetLyricsUseCase
import com.example.requestsample.ui.model.LyricsUiModel
import com.example.requestsample.ui.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val getLyricsUseCase: GetLyricsUseCase
) : ViewModel() {
    val state = MutableStateFlow(LyricsState())

    fun onEvent(event: Event) {
        when (event) {
            is Event.GetLyricsEvent -> getLyrics(event.artist, event.title)
        }
    }

    private fun getLyrics(artist: String, title: String) {
        viewModelScope.launch {
            state.update { it.copy(loading = true) }
            getLyricsUseCase(artist, title).onSuccess { domain ->
                state.update { it.copy(lyrics = domain.toUi(), loading = false) }
            }.onFailure { throwable ->
                state.update { it.copy(error = throwable, loading = false) }
            }
        }
    }

    data class LyricsState(
        val lyrics: LyricsUiModel? = null,
        val loading: Boolean = false,
        val error: Throwable? = null
    )

    sealed class Event {
        data class GetLyricsEvent(val artist: String, val title: String) : Event()
    }
}