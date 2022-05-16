package com.example.week1.data.datasource

import com.example.week1.data.model.GithubRepoList

interface RepoSearchRemoteSource {
    suspend fun getRepoList(query: String): GithubRepoList
}