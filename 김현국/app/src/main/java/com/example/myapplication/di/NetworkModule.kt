package com.example.myapplication.di

import com.example.myapplication.BuildConfig
import com.example.myapplication.util.NetworkInterceptor
import com.orhanobut.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author 김현국
 * @created 2022/05/24
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val BASE_URL = "https://api.github.com/"

    @Provides
    @Singleton
    fun provideNetworkInterceptor(): NetworkInterceptor {
        return NetworkInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttp3Client(networkInterceptor: NetworkInterceptor): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(networkInterceptor)
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
            OkHttpClient.Builder()
                .addInterceptor(networkInterceptor)
                .build()
        }
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
}
