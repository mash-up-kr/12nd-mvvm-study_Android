package com.example.week1.data.datasource

import com.example.week1.data.model.GithubRepoList
import com.example.week1.data.api.RetrofitService

class RepoSearchRemoteSourceImpl : RepoSearchRemoteSource {

    override suspend fun getRepoList(query: String): GithubRepoList =
        RetrofitService.client.getRepoSearchList(query)
}