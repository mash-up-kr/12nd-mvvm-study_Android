package com.example.mvvmstudy.IO

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClass {
    var retrofitService : IGithubService

    init {
          val okHttpClient = OkHttpClient.Builder().addInterceptor(
                 HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        )
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(logger)
            .build()

        retrofitService = retrofit.create(IGithubService::class.java)

    }
}