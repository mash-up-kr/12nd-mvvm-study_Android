package com.example.week1.presentation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week1.data.model.GithubRepo
import com.example.week1.data.model.NetworkState
import com.example.week1.data.repository.RepoSearchRepositoryImpl
import com.example.week1.domain.usecase.GetRepoSearchUseCase

class RepoSearchViewModel : ViewModel() {

    private val getRepoSearchUseCase = GetRepoSearchUseCase(RepoSearchRepositoryImpl())

    private val _repoList = MutableLiveData<List<GithubRepo>>()
    val repoList: LiveData<List<GithubRepo>>
        get() = _repoList

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    fun getRepoList(query: String) {
        _networkState.postValue(NetworkState.LOADING)
        getRepoSearchUseCase(query, viewModelScope) { repoList ->
            _repoList.value = repoList.items
            _networkState.value = NetworkState.LOADED
        }
    }
}