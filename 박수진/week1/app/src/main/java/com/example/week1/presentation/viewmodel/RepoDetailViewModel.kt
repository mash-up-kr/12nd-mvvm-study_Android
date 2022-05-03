package com.example.week1.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.week1.data.dataclass.GithubUser
import com.example.week1.data.network.NetworkState
import com.example.week1.domain.repository.RepoDetailRepository

class RepoDetailViewModel(
    private val repoDetailRepository: RepoDetailRepository,
    private val username: String
): ViewModel() {

    val githubRepoUser: LiveData<GithubUser> by lazy {
        repoDetailRepository.fetchGithubUser(username)
    }

    val networkState: LiveData<NetworkState> by lazy {
        repoDetailRepository.getNetworkState()
    }

}