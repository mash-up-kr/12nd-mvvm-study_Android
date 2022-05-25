package com.test.mvvmstudy.repository

import com.test.mvvmstudy.api.GithubApi
import com.test.mvvmstudy.data.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val githubApi: GithubApi) : SearchRepository {
    override fun getSearchItem(query: String): Flow<SearchResult> = flow {
        emit(githubApi.getSearchList(query))
    }
}