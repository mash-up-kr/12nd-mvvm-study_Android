package com.test.mvvmstudy.di

import com.test.mvvmstudy.api.GithubApi
import com.test.mvvmstudy.repository.SearchRepository
import com.test.mvvmstudy.repository.SearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideSearchRepository(githubApi: GithubApi): SearchRepository {
        return SearchRepositoryImpl(githubApi)
    }
}