package com.mash_up.mvvmstudy.di

import com.mash_up.mvvmstudy.repository.remote.MainRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Singleton
    @Provides
    fun provideMainRemoteDataSource() : MainRemoteDataSource =
        MainRemoteDataSource()
}