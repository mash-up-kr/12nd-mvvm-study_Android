package com.example.myapplication.domain

/**
 * @author 김현국
 * @created 2022/05/03
 */
sealed class Results<out T> {

    data class Success<R : Any>(val value: R) : Results<R>()

    data class Failure(
        val message: String
    ) : Results<Nothing>()

    object Loading : Results<Nothing>()
}
