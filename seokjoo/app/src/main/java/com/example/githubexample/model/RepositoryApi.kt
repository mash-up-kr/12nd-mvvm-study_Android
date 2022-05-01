package com.example.githubexample.model

import com.example.githubexample.entities.GithubResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryApi {
    @GET("search/repositories")
    suspend fun getSearchRepository(@Query("q") query: String): Response<GithubResult>
}
