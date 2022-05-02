package com.test.mvvmstudy.api

import com.test.mvvmstudy.util.GITHUB_DOMAIN
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object Retrofit {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(GITHUB_DOMAIN)
        .build()

    val githubApi : GithubApi = retrofit.create()

}