package com.example.myapplication.ui

/**
 * @author 김현국
 * @created 2022/05/03
 */
sealed class Results<out T> {
    data class Success<out R>(val value: R) : Results<R>()
    data class Failure(
        val message: String?,
        val throwable: Throwable?
    ) : Results<Nothing>()

    object Loading : Results<Nothing>()
}
