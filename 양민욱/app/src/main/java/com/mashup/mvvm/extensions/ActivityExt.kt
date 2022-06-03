package com.mashup.mvvm.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <T : ViewModel> AppCompatActivity.getViewModel(vmClass: Class<T>): T {
    return ViewModelProvider(this).get(vmClass)
}