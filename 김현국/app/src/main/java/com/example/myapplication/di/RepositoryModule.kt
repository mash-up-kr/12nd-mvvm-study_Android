package com.example.myapplication.di

import com.example.myapplication.data.repository.DetailUserRepositoryImpl
import com.example.myapplication.data.repository.SearchRepoRepositoryImpl
import com.example.myapplication.domain.repository.DetailUserRepository
import com.example.myapplication.domain.repository.SearchRepoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author 김현국
 * @created 2022/05/16
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideSearchRepoRepository(impl: SearchRepoRepositoryImpl): SearchRepoRepository

    @Singleton
    @Binds
    abstract fun provideDetailUserRepository(impl: DetailUserRepositoryImpl): DetailUserRepository
}
