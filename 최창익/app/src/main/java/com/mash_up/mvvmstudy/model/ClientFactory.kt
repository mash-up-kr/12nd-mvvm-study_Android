package com.mash_up.mvvmstudy.model

import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ClientFactory {
    private val okHttpClient = createOkHttpClient()
    private val retrofit = createRetrofit()

    // TODO: Header 넣어야됨
    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            connectTimeout(8, TimeUnit.SECONDS)
            writeTimeout(8, TimeUnit.SECONDS)
            readTimeout(8, TimeUnit.SECONDS)
            addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Accept", "application/vnd.github.v3+json").build()
                chain.proceed(request)
            }
            addInterceptor { chain ->
                val request = chain.request()

                Logger.i("Headers : ${request.headers()} From Url : ${request.url()}")

                chain.proceed(request)
            }
        }

        return builder.build()
    }

    private fun createRetrofit(): Retrofit {
        val baseUrl = "https://api.github.com"

        return Retrofit.Builder().apply {
            baseUrl(baseUrl)
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
        }.build()
    }

    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
