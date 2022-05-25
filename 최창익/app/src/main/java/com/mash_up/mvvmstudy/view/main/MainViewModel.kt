package com.mash_up.mvvmstudy.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mash_up.mvvmstudy.repository.MainRepository
import com.mash_up.mvvmstudy.repository.model.Repository
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch
import java.io.IOException

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
                when(exception) {
                    is IOException -> {
                        _networkErrorState.value = exception.toString()
                    }

                    else -> {
                        Logger.e("unknown error : $exception")
                    }
                }

            }
    }
}