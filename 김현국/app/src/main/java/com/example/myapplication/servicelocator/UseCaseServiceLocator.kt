package com.example.myapplication.servicelocator

import com.example.myapplication.domain.repository.DetailUserRepository
import com.example.myapplication.domain.repository.SearchRepoRepository
import com.example.myapplication.domain.usecase.GetRepositoryListWithQueryUseCase
import com.example.myapplication.domain.usecase.GetUserFollowerUseCase
import com.example.myapplication.domain.usecase.GetUserFollowingUseCase

/**
 * @author 김현국
 * @created 2022/05/16
 */
object UseCaseServiceLocator {
    fun provideGetRepositoryListWithQueryUseCase(searchRepoRepository: SearchRepoRepository): GetRepositoryListWithQueryUseCase {
        return GetRepositoryListWithQueryUseCase(repository = searchRepoRepository)
    }

    fun provideGetUserFollowingUseCase(detailUserRepository: DetailUserRepository): GetUserFollowingUseCase {
        return GetUserFollowingUseCase(repository = detailUserRepository)
    }

    fun provideGetUserFollowerUseCase(detailUserRepository: DetailUserRepository): GetUserFollowerUseCase {
        return GetUserFollowerUseCase(repository = detailUserRepository)
    }
}
