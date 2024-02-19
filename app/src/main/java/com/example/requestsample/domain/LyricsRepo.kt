package com.example.requestsample.domain

import com.example.requestsample.domain.model.LyricsDomainModel

interface LyricsRepo {
    suspend fun getLyrics(artist: String, title: String): Result<LyricsDomainModel>
}