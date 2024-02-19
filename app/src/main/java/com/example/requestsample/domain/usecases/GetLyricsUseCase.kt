package com.example.requestsample.domain.usecases

import com.example.requestsample.domain.LyricsRepo
import com.example.requestsample.domain.model.LyricsDomainModel
import javax.inject.Inject

class GetLyricsUseCase @Inject constructor(private val repo: LyricsRepo) {
    suspend operator fun invoke(artist: String, title: String) = repo.getLyrics(artist, title)
}