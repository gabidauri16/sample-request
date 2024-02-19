package com.example.requestsample.ui.model

import com.example.requestsample.domain.model.LyricsDomainModel

data class LyricsUiModel(
    val lyrics: String
)

fun LyricsDomainModel.toUi() = LyricsUiModel(lyrics)