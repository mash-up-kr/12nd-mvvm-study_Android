package com.example.week1.data.datasource

import com.example.week1.data.api.GithubApi
import com.example.week1.data.model.GithubRepoList
import javax.inject.Inject

class SearchRemoteSourceImpl @Inject constructor(
    private val githubApi: GithubApi
) : SearchRemoteSource {

    override suspend fun getSearchList(query: String): GithubRepoList =
        githubApi.getSearchList(query)
}