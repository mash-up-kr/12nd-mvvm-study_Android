package com.example.myapplication.presenter.view.search

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.myapplication.domain.usecase.GetRepositoryListWithQueryUseCase

/**
 * @author 김현국
 * @created 2022/05/10
 */
class SearchViewModelFactory(
    savedStateRegistryOwner: SavedStateRegistryOwner,
    private val getRepositoryListWithQueryUseCase: GetRepositoryListWithQueryUseCase
) :
    AbstractSavedStateViewModelFactory(
        savedStateRegistryOwner,
        null
    ) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(handle, getRepositoryListWithQueryUseCase) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}
