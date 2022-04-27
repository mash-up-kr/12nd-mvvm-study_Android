package com.example.week1.base

import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    fun onProgress() {
        (application as BaseApplication).getInstance().onProgress(this)
    }

    fun offProgress() {
        (application as BaseApplication).getInstance().offProgress()
    }
}