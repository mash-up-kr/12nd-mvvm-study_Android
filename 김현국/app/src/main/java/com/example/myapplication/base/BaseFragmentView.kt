package com.example.myapplication.base

/**
 * @author 김현국
 * @created 2022/04/25
 */
interface BaseFragmentView {
    fun showLoading()
    fun hideLoading()
    fun showToast(message: String)
}
