package com.example.myapplication.network

import com.example.myapplication.BuildConfig
import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
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
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())
        .build()

    private fun getClient(): OkHttpClient {

        if (BuildConfig.DEBUG) {
            return OkHttpClient.Builder()
                .addInterceptor(NetworkInterceptor())
                .addInterceptor(
                    HttpLoggingInterceptor { message ->
                        try {
                            JSONArray(message)
                            Logger.t("OKHTTP_JSON_ARRAY").json(message)
                        } catch (e: JSONException) {
                            try {
                                JSONObject(message)
                                Logger.t("OKHTTP_JSON_OBJECT").json(message)
                            } catch (e: JSONException) {
                                Logger.t("OKHTTP_TAG").i(message)
                            }
                        }
                    }.setLevel(HttpLoggingInterceptor.Level.BODY)
                ).build()
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
