package com.example.myapplication.network

import com.example.myapplication.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author 김현국
 * @created 2022/04/25
 */
const val BASE_URL = "https://api.github.com/"

object ApiClient {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun getClient(): OkHttpClient {
        if (BuildConfig.DEBUG) {
            return OkHttpClient.Builder()
                .addInterceptor(NetworkInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        } else {
            return OkHttpClient.Builder()
                .addInterceptor(NetworkInterceptor())
                .build()
        }
    }

    fun <T> buildApi(apiInterface: Class<T>): T {
        return retrofit.create(apiInterface)
    }
}
