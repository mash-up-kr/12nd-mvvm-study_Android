package com.example.myapplication.di

import com.example.myapplication.domain.repository.DetailUserRepository
import com.example.myapplication.domain.repository.SearchRepoRepository
import com.example.myapplication.domain.usecase.GetRepositoryListWithQueryUseCase
import com.example.myapplication.domain.usecase.GetUserFollowerUseCase
import com.example.myapplication.domain.usecase.GetUserFollowingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author 김현국
 * @created 2022/05/16
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetRepositoryListWithQueryUseCase(searchRepoRepository: SearchRepoRepository): GetRepositoryListWithQueryUseCase {
        return GetRepositoryListWithQueryUseCase(repository = searchRepoRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserFollowingUseCase(detailUserRepository: DetailUserRepository): GetUserFollowingUseCase {
        return GetUserFollowingUseCase(repository = detailUserRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserFollowerUseCase(detailUserRepository: DetailUserRepository): GetUserFollowerUseCase {
        return GetUserFollowerUseCase(repository = detailUserRepository)
    }
}
