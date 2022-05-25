package com.joocoding.android.app.githubsearch.network

import com.joocoding.android.app.githubsearch.data.dto.RepositoriesDto

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/repositories")
    suspend fun getRepository(
        @Query("q") query: String = "mash-up",
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc"
    ): Response<RepositoriesDto>

}