package com.mash_up.mvvmstudy.model

import com.mash_up.mvvmstudy.BASE_URL
import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ClientFactory {
    private val okHttpClient = createOkHttpClient()
    private val retrofit = createRetrofit()

    private fun createOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(8, TimeUnit.SECONDS)
            writeTimeout(8, TimeUnit.SECONDS)
            readTimeout(8, TimeUnit.SECONDS)
            addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Accept", "application/vnd.github.v3+json").build()

                Logger.i("Request's Header is : ${newRequest.headers()} From Url : ${newRequest.url()}")

                chain.proceed(newRequest)
            }
        }.build()

    private fun createRetrofit(): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
        }.build()

    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
