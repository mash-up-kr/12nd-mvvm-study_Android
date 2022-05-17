package com.example.mvvmstudy.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mvvmstudy.data.Detail

class DetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val detail = savedStateHandle.getLiveData<Detail>("detail")
}