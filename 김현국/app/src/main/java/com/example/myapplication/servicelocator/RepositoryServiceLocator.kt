package com.example.myapplication.servicelocator

import com.example.myapplication.data.repository.DetailUserRepositoryImpl
import com.example.myapplication.data.repository.SearchRepoRepositoryImpl
import com.example.myapplication.domain.repository.DetailUserRepository
import com.example.myapplication.domain.repository.SearchRepoRepository

/**
 * @author 김현국
 * @created 2022/05/16
 */
object RepositoryServiceLocator {
    private val detailUserRepository: DetailUserRepository by lazy {
        DetailUserRepositoryImpl()
    }

    private val searchRepoRepository: SearchRepoRepository by lazy {
        SearchRepoRepositoryImpl()
    }

    fun provideSearchRepoRepository(): SearchRepoRepository = searchRepoRepository

    fun provideDetailUserRepository(): DetailUserRepository = detailUserRepository
}
