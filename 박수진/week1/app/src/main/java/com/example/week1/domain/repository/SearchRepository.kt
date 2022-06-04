package com.example.week1.domain.repository

import com.example.week1.data.model.GithubRepoList

interface SearchRepository {
    suspend fun getSearchList(query: String): GithubRepoList
}