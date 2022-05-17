package com.github.sookhee.mvvmstudy.network

import androidx.viewbinding.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  RetrofitClient.kt
 *
 *  Created by Minji Jeong on 2022/04/24
 *  Copyright © 2022 MashUp All rights reserved.
 */

object RetrofitClient {
    private const val BASE_URL = "https://api.github.com/"

    private val levelType: Level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
    private val logging = HttpLoggingInterceptor().apply {
        setLevel(levelType)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MMM-dd'T'HH:mm:ssSSZ")
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
