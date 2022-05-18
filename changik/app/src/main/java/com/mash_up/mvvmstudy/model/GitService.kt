package com.mash_up.mvvmstudy.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GitService {
    @GET("/search/repositories")
    fun getRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "best match",
        @Query("order") order: String = "desc",
        @Query("per_page") per_page: String = "30",
        @Query("page") page: String = "0",
    ): Call<Repositories>
}