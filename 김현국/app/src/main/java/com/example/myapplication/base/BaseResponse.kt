package com.example.myapplication.base

/**
 * @author 김현국
 * @created 2022/05/01
 */
interface BaseResponse<T> {
    fun onSuccess(data: T)
    fun onError(throwable: Throwable)
    fun onLoading()
}
