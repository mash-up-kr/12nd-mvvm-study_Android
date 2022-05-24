package com.example.week1.data.api

import com.example.week1.data.model.GithubRepoList
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories")
    suspend fun getRepoSearchList(
        @Query("q") query: String
    ): GithubRepoList
}