package com.example.myapplication.util

import okhttp3.Request

/**
 * @author 김현국
 * @created 2022/04/25
 */
fun Request.addHeader(key: String, value: String): Request {
    return this.newBuilder().addHeader(key, value).build()
}
