package com.mashup.mvvm.network

import com.mashup.mvvm.dto.RepositoriesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("/search/repositories")
    suspend fun getRepositories(
        @Query("q") query: String
    ): Response<RepositoriesDto>
}