package com.mash_up.mvvmstudy.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mash_up.mvvmstudy.repository.model.Repository
import com.mash_up.mvvmstudy.repository.MainRepository

class MainViewModel : ViewModel() {
    private val mainRepository = MainRepository()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _repositories: MutableLiveData<List<Repository>> = MutableLiveData()
    val repositories: LiveData<List<Repository>>
        get() = _repositories

    private val _networkErrorState: MutableLiveData<Unit> = MutableLiveData()
    val networkErrorState: LiveData<Unit>
        get() = _networkErrorState
    var networkErrorInfo: String = ""

    fun getRepositories(query: String) {
        mainRepository.getRepositories(
            query = query,
            onSuccess = { response ->
                _repositories.value = response.repositories
            },
            onError = { errorInfo ->
                networkErrorInfo = errorInfo
                _networkErrorState.value = Unit
            }
        )
    }
}