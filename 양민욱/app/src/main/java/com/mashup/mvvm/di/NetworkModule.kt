package com.mashup.mvvm.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mashup.mvvm.BuildConfig
import com.mashup.mvvm.network.GithubApi
import com.mashup.mvvm.network.GithubInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val GITHUB_HOST_URL = "https://api.github.com"
    private const val TIME_OUT_DURATION_SECOND = 10L

    @Singleton
    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(GithubInterceptor)
            .writeTimeout(TIME_OUT_DURATION_SECOND, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_DURATION_SECOND, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            okHttpClientBuilder
                .addInterceptor(HttpLoggingInterceptor())
        }
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(GITHUB_HOST_URL)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(contentType)
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideGithubApi(retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }
}