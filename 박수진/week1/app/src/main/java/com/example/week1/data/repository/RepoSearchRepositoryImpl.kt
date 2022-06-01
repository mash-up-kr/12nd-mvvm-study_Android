package com.example.week1.data.repository

import com.example.week1.data.model.GithubRepoList
import com.example.week1.data.datasource.SearchRemoteSourceImpl
import com.example.week1.domain.repository.SearchRepository

class RepoSearchRepositoryImpl : SearchRepository {

    private val searchRemoteSourceImpl = SearchRemoteSourceImpl()

    override suspend fun getSearchList(query: String): GithubRepoList =
        searchRemoteSourceImpl.getSearchList(query)
}