package com.example.myapplication.ui.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author 김현국
 * @created 2022/05/02
 */
class MainViewModel : ViewModel() {
    private var _query = MutableLiveData<String>()
    val query: LiveData<String>
        get() = _query
    fun updateQuery(q: String) {
        _query.value = q
    }
}
