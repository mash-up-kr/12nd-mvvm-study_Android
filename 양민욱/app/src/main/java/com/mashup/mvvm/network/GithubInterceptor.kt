package com.mashup.mvvm.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

object GithubInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            Request.Builder()
                .addHeader("accept", "application/vnd.github.v3+json")
                .build()
        )
    }
}