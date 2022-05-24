package com.example.mvvmstudy.network

import com.example.mvvmstudy.data.Repositories
import retrofit2.http.GET
import retrofit2.http.Query

interface IGithubService {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("per_page") per_page: Int = 50
    ): Repositories
}