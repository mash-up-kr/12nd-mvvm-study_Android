package com.example.week1.data.datasource

import com.example.week1.data.model.GithubRepoList
import com.example.week1.data.api.RetrofitService

class SearchRemoteSourceImpl : SearchRemoteSource {

    override suspend fun getSearchList(query: String): GithubRepoList =
        RetrofitService.client.getSearchList(query)
}