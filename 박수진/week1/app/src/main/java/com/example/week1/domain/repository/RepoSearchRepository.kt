package com.example.week1.domain.repository

import com.example.week1.data.model.GithubRepoList

interface RepoSearchRepository {
    suspend fun getRepoList(query: String): GithubRepoList
}