package com.joocoding.android.app.githubsearch.network

import com.joocoding.android.app.githubsearch.data.dto.RepositoriesDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/repositories")
    fun getRepository(
        @Query("q") query: String = "mash-up",
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc"
    ): Call<RepositoriesDto>

}