package com.example.myapplication.network

import com.example.myapplication.util.addHeader
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @author 김현국
 * @created 2022/04/25
 */
class NetworkInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        lateinit var response: Response
        val request = chain.request()
            .addHeader("accept", "application/vnd.github.v3+json")

        try {
            response = chain.proceed(request)
        } catch (e: IOException) {
            print(e)
        }
        return response
    }
}
