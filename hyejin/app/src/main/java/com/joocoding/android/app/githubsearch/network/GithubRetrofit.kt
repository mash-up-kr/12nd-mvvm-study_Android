package com.joocoding.android.app.githubsearch.network

import com.joocoding.android.app.githubsearch.service.SearchService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubRetrofit {
    private const val BASE_URL = "https://api.github.com/"
    val githubRetrofit: SearchService

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        githubRetrofit = retrofit.create(SearchService::class.java)
    }


}