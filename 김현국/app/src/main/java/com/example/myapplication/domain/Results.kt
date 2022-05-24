package com.example.myapplication.domain

/**
 * @author 김현국
 * @created 2022/05/03
 */
sealed class Results<out R> {
    data class Success<out T>(val value: T) : Results<T>()
    data class Failure(
        val message: String
    ) : Results<Nothing>()

    object Loading : Results<Nothing>()
}
