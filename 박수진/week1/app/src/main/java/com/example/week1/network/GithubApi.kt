package com.example.week1.network

import com.example.week1.model.GithubRepoList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories")
    fun getRepoList(
        @Query("q") query: String
    ): Call<GithubRepoList>
}