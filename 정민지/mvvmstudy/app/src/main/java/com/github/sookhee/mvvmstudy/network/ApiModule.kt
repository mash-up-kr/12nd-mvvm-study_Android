package com.github.sookhee.mvvmstudy.network

import androidx.viewbinding.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  ApiModule.kt
 *
 *  Created by Minji Jeong on 2022/04/24
 *  Copyright © 2022 MashUp All rights reserved.
 */

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Provides
    fun provideLevel(): Level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE

    @Provides
    fun provideLoggingInterceptor(levelType: Level): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(levelType)
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setDateFormat("yyyy-MMM-dd'T'HH:mm:ssSSZ")
        .create()

    @Provides
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Provides
    fun provideGithubApiService(retrofit: Retrofit): GithubAPI {
        return retrofit.create(GithubAPI::class.java)
    }

    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }
}
