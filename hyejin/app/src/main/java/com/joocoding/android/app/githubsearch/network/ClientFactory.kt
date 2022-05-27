package com.joocoding.android.app.githubsearch.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object ClientFactory {
    private const val BASE_URL = "https://api.github.com/"
    private val okHttpClient by lazy { createOkHttpClient() }
    private val retrofit by lazy { createRetrofit() }

    @Provides
    fun createOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(8, TimeUnit.SECONDS)
            writeTimeout(8, TimeUnit.SECONDS)
            readTimeout(8, TimeUnit.SECONDS)
            addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Accept", "application/vnd.github.v3+json").build()


                chain.proceed(newRequest)
            }
        }.build()

    @Provides
    fun createRetrofit(): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
        }.build()

    @Provides
    fun createService(): SearchService {
        return retrofit.create(SearchService::class.java)
    }

}