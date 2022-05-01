package com.mashup.mvvm

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mashup.mvvm.network.GithubApi
import com.mashup.mvvm.network.GithubInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object ServiceLocator {
    private const val GITHUB_HOST_URL = "https://api.github.com"
    private const val TIME_OUT_DURATION_SECOND = 10L

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit by lazy {
        getGithubRetrofitClient()
    }
    private val githubApi: GithubApi by lazy {
        retrofit.create(GithubApi::class.java)
    }

    private fun getGithubRetrofitClient(): Retrofit {
        val contentType = "application/json".toMediaType()
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(GithubInterceptor)
            .writeTimeout(TIME_OUT_DURATION_SECOND, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_DURATION_SECOND, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            okHttpClientBuilder
                .addInterceptor(HttpLoggingInterceptor())
        }

        return Retrofit.Builder()
            .baseUrl(GITHUB_HOST_URL)
            .client(okHttpClientBuilder.build())
            .addConverterFactory(
                json.asConverterFactory(contentType)
            )
            .build()
    }

    fun injectGithubApi(): GithubApi = githubApi
}