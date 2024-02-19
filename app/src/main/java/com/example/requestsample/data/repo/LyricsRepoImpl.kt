package com.example.requestsample.data.repo

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.requestsample.data.LyricsService
import com.example.requestsample.domain.LyricsRepo
import com.example.requestsample.domain.model.LyricsDomainModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class LyricsRepoImpl @Inject constructor(private val service: LyricsService) : LyricsRepo {

    override suspend fun getLyrics(artist: String, title: String) = runCatching {
        delay(1000)
        service.getLyrics(artist, title).body()!!.toDomain()
    }

}