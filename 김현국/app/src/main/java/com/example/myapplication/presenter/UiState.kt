package com.example.myapplication.presenter

/**
 * @author 김현국
 * @created 2022/05/17
 */
sealed class UiState<out T : Any> {
    data class Success<out T : Any>(val data: T) : UiState<T>()
    data class Error(val error: String) : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    object Empty : UiState<Nothing>()
}
