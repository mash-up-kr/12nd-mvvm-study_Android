package com.joocoding.android.app.githubsearch.model

sealed class Response<T> {
    data class Success<T>(
        val code: Int,
        val data: T
    ) : Response<T>()

    data class Error<T>(
        val throwable: Throwable? = null,
        val message: String? = null
    ) : Response<T>()
}
