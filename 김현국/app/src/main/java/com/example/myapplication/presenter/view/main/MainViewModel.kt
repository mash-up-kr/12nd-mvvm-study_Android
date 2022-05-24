package com.example.myapplication.presenter.view.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * @author 김현국
 * @created 2022/05/02
 */
class MainViewModel : ViewModel() {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun updateQuery(q: String) {
        _query.value = q
    }
}
