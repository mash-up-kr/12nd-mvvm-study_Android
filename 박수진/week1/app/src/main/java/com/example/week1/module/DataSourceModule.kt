package com.example.week1.module

import com.example.week1.data.datasource.SearchRemoteSource
import com.example.week1.data.datasource.SearchRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindSearchRemoteSource(searchRemoteSourceImpl: SearchRemoteSourceImpl): SearchRemoteSource
}