package com.example.requestsample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.requestsample.domain.usecases.GetLyricsUseCase
import com.example.requestsample.ui.model.LyricsUiModel
import com.example.requestsample.ui.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
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
            updateStart()
//            state.update { it.copy(loading = true) }
            getLyricsUseCase(artist, title).onSuccess { domain ->
                updateEnd { copy(lyrics = domain.toUi()) }
//                state.update { it.copy(lyrics = domain.toUi(), loading = false) }
            }.onFailure { throwable ->
                updateEnd { copy(error = throwable) }
//                state.update { it.copy(error = throwable, loading = false) }
            }
        }
    }

    private fun updateStart(copy: (LyricsState.() -> LyricsState)? = null) {
        state.update { copy?.invoke(state.value)?.copy(loading = true) ?: it.copy(loading = true) }
    }

    private fun updateEnd(copy: (LyricsState.() -> LyricsState)? = null) {
        state.update { copy?.invoke(state.value)?.copy(loading = false) ?: it }
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