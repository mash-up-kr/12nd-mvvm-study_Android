package com.example.githubexample.model

import com.example.githubexample.entities.GithubResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryApi {
    @GET("search/repositories")
    fun getSearchRepository(@Query("q") query: String): Call<GithubResult>
}
