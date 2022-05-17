package com.example.githubexample.viewmodel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.githubexample.model.remote.RemoteDataSource

class GithubViewModelFactory(private val remote: RemoteDataSource, owner: SavedStateRegistryOwner) : AbstractSavedStateViewModelFactory(owner, null) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        return if (modelClass.isAssignableFrom(GithubViewModel::class.java)) GithubViewModel(
            remote,
            handle
        ) as T else throw IllegalArgumentException()
    }
}
