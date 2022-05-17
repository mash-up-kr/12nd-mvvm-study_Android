package com.example.myapplication.presenter.view.detail

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.myapplication.domain.usecase.GetUserFollowerUseCase
import com.example.myapplication.domain.usecase.GetUserFollowingUseCase

/**
 * @author 김현국
 * @created 2022/05/10
 */
class DetailViewModelFactory(
    savedStateRegistryOwner: SavedStateRegistryOwner,
    private val getUserFollowerUseCase: GetUserFollowerUseCase,
    private val getUserFollowingUseCase: GetUserFollowingUseCase
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
            return DetailViewModel(handle, getUserFollowingUseCase, getUserFollowerUseCase) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}
