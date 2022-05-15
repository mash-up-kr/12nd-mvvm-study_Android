package com.example.mvvmstudy.Network

import com.example.mvvmstudy.data.Repositories
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IGithubService {
    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: String = "1",
        @Query("per_page") per_page: Int = 30
    ): Call<Repositories>
}