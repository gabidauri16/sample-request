package com.example.requestsample.data.dto

import com.example.requestsample.domain.model.LyricsDomainModel
import com.google.gson.annotations.SerializedName

data class LyricsDto(
    @SerializedName("lyrics")
    val lyrics: String
) {
    fun toDomain() = LyricsDomainModel(lyrics)
}