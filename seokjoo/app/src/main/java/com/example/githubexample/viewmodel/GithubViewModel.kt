package com.example.githubexample.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubexample.entities.GithubResult
import com.example.githubexample.model.remote.RemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GithubViewModel(private val remote: RemoteDataSource, private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _uiState = MutableStateFlow(GithubResult())
    val uiState = _uiState.asStateFlow()

    fun getRepositoryList(query: String) {
        viewModelScope.launch {
            _uiState.value = remote.getRepositoryList(query).body() ?: GithubResult()
        }
    }
}
