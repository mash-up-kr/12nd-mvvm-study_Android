package com.mashup.mvvm.ui.main

import com.mashup.mvvm.base.BaseViewModel
import com.mashup.mvvm.data.Response
import com.mashup.mvvm.data.model.Repository
import com.mashup.mvvm.data.repository.GithubRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : BaseViewModel() {
    private val _repositories = MutableStateFlow<List<Repository>>(emptyList())
    val repositories: StateFlow<List<Repository>> = _repositories.asStateFlow()

    fun fetchGithubRepository(query: String) = mashupViewModelScope {
        when (val data = githubRepository.getRepositories(query)) {
            is Response.Success -> {
                _repositories.emit(data.data?.items ?: emptyList())
            }
            is Response.Error -> {
                data.message?.let { message ->
                    showToastMessage(message)
                }
            }
        }
    }
}