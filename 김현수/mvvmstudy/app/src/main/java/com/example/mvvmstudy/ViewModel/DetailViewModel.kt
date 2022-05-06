package com.example.mvvmstudy.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mvvmstudy.data.Detail


class DetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _detail: Detail? = savedStateHandle.get<Detail>("detail")

    private val _ui: MutableLiveData<Detail> = MutableLiveData()
    val ui: LiveData<Detail>
        get() = _ui

    init {
        _ui.value = _detail!!
    }
}