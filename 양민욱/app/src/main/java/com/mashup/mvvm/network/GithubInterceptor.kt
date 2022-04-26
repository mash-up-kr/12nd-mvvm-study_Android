package com.mashup.mvvm.network

import okhttp3.Interceptor
import okhttp3.Response

object GithubInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .apply { header("accept", "application/vnd.github.v3+json") }
                .build()
        )
    }
}