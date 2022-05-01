package com.example.mvvmstudy.IO

import com.example.mvvmstudy.data.Repositories
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IGithubService {
    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
        ): Call<Repositories>
}