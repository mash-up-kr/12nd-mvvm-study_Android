package com.example.githubexample.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetworkManager {
    private const val BASE_URL = "https://api.github.com/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val repositoryApi: RepositoryApi = retrofit.create()

}
