package com.mashup.mvvm.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mashup.mvvm.constants.KEY_REPOSITORY
import com.mashup.mvvm.data.model.Repository

class DetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val repository = savedStateHandle.getLiveData<Repository>(KEY_REPOSITORY)
}