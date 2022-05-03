package com.example.week1.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.week1.data.dataclass.GithubRepo
import com.example.week1.data.network.NetworkState
import com.example.week1.domain.repository.RepoListRepository

class RepoListViewModel : ViewModel() {

    private val repoListRepository = RepoListRepository()

    var repoList: LiveData<List<GithubRepo>> = repoListRepository.getRepoList("")

    val networkState: LiveData<NetworkState> by lazy {
        repoListRepository.getNetworkState()
    }

    fun getQueryData(query: String) {
        repoList = repoListRepository.getRepoList(query)
    }
}