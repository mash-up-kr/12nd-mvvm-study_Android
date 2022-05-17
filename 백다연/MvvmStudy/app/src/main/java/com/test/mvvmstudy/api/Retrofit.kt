package com.test.mvvmstudy.api

import com.test.mvvmstudy.BuildConfig
import com.test.mvvmstudy.util.GITHUB_DOMAIN
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import java.util.logging.Level

object Retrofit {

    private val logging = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else HttpLoggingInterceptor.Level.NONE
    }

    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(GITHUB_DOMAIN)
        .client(client)
        .build()

    val githubApi: GithubApi = retrofit.create()

}