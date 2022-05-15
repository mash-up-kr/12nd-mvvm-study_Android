package com.example.githubexample.model

import com.example.githubexample.entities.GithubResult
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryApi {
    @GET("search/repositories")
    suspend fun getSearchRepository(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): GithubResult
}
