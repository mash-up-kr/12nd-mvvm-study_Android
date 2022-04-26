package com.test.mvvmstudy.api

import com.test.mvvmstudy.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private val retrofit: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.GITHUB_DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val githubApi : GithubApi by lazy {
        retrofit.build().create(GithubApi::class.java)
    }
}