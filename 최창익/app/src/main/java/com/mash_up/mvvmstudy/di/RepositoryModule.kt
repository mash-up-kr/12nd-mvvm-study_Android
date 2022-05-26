package com.mash_up.mvvmstudy.di

import com.mash_up.mvvmstudy.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository() : MainRepository =
        MainRepository()
}