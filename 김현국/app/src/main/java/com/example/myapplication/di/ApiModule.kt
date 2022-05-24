package com.example.myapplication.di

import com.example.myapplication.data.source.remote.RepositoryRemoteDataSource
import com.example.myapplication.data.source.remote.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author 김현국
 * @created 2022/05/24
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideRepositoryRemoteDataSource(retrofit: Retrofit): RepositoryRemoteDataSource {
        return retrofit.create(RepositoryRemoteDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(retrofit: Retrofit): UserRemoteDataSource {
        return retrofit.create(UserRemoteDataSource::class.java)
    }
}
