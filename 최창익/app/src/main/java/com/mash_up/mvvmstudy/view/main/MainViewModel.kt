package com.mash_up.mvvmstudy.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mash_up.mvvmstudy.repository.model.Repository
import com.mash_up.mvvmstudy.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val mainRepository = MainRepository()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _repositories: MutableLiveData<List<Repository>> = MutableLiveData()
    val repositories: LiveData<List<Repository>>
        get() = _repositories

    private val _networkErrorState: MutableLiveData<String> = MutableLiveData()
    val networkErrorState: LiveData<String>
        get() = _networkErrorState

    fun getRepositories(query: String) = viewModelScope.launch {
        runCatching { mainRepository.getRepositories(query) }
            .onSuccess { successResult ->
                val repositories = successResult.getOrNull()?.repositories ?: listOf()
                _repositories.value = repositories
            }
            .onFailure { exception ->
                _networkErrorState.value = exception.toString()
            }
    }
}