package com.joocoding.android.app.githubsearch.service

import com.joocoding.android.app.githubsearch.model.response.Repositories
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/repositories")
    fun getRepository(
        @Query("q") query: String = "mash-up",
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc"
    ): Call<Repositories>

}