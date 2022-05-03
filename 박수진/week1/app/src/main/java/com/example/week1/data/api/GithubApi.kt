package com.example.week1.data.api

import com.example.week1.data.dataclass.GithubRepoList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories")
    fun getGithubRepoList(
        @Query("q") query: String
    ): Call<GithubRepoList>
}