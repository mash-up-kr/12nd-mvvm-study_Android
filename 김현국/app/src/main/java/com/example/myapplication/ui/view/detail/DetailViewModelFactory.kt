package com.example.myapplication.ui.view.detail

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.myapplication.data.repository.DetailUserRepository

/**
 * @author 김현국
 * @created 2022/05/10
 */
class DetailViewModelFactory(
    savedStateRegistryOwner: SavedStateRegistryOwner,
    private val detailUserRepository: DetailUserRepository
) : AbstractSavedStateViewModelFactory(
    savedStateRegistryOwner, null
) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(handle, detailUserRepository) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}
