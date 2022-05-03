package com.joocoding.android.app.githubsearch.network

import com.joocoding.android.app.githubsearch.service.SearchService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubRetrofit {
    private const val BASE_URL = "https://api.github.com/"

    //by lazy, init 차이
    val searchService: SearchService by lazy {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(SearchService::class.java)
    }

}