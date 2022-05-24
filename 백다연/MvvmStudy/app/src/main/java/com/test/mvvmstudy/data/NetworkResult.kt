package com.test.mvvmstudy.data

sealed class NetworkResult {
    data class SuccessDataResult(val data : SearchResult) : NetworkResult()
    data class ErrorDataResult(val exception : Throwable) : NetworkResult()
    object Loading : NetworkResult()
    object Empty : NetworkResult()
}