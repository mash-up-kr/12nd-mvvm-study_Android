package com.example.myapplication.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author 김현국
 * @created 2022/04/25
 */
const val BASE_URL = "https://api.github.com/"
class ApiClient {
    private var ourInstance: ApiClient? = null
    private var retrofit: Retrofit? = null

    init {
        if (ourInstance == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    fun getInstance(): Retrofit? {
        if (ourInstance == null) {
            ourInstance = ApiClient()
        }
        return retrofit
    }

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor())
            .build()
    }
}
