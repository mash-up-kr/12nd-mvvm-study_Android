package com.example.mvvmstudy.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mvvmstudy.data.Detail


class DetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

   val detail = savedStateHandle.getLiveData<Detail>("detail")
}