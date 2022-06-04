package com.example.week1.data.repository

import com.example.week1.data.datasource.SearchRemoteSource
import com.example.week1.data.model.GithubRepoList
import com.example.week1.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteSource: SearchRemoteSource
) : SearchRepository {

    override suspend fun getSearchList(query: String): GithubRepoList =
        searchRemoteSource.getSearchList(query)
}