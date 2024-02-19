package com.example.requestsample.di

import com.example.requestsample.data.repo.LyricsRepoImpl
import com.example.requestsample.domain.LyricsRepo
import dagger.Binds
import dagger.Component.Factory
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    @Singleton
    fun bindLyricsRepo(lyricsRepo: LyricsRepoImpl): LyricsRepo
}