package com.example.week1.data.datasource

import com.example.week1.data.model.GithubRepoList

interface SearchRemoteSource {
    suspend fun getSearchList(query: String): GithubRepoList
}