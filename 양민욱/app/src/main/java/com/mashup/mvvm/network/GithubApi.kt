package com.mashup.mvvm.network

import com.mashup.mvvm.dto.RepositoriesDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("/search/repositories")
    fun getRepositories(
        @Query("q") query: String
    ): Call<RepositoriesDto>
}