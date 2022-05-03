package com.example.week1.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.week1.data.dataclass.GithubRepo
import com.example.week1.data.network.NetworkState
import com.example.week1.domain.repository.RepoDetailRepository

class RepoDetailViewModel(
    private val repoDetailRepository: RepoDetailRepository,
    private val owner: String,
    private val repo: String
): ViewModel() {

    val githubRepoUser: LiveData<GithubRepo> by lazy {
        repoDetailRepository.fetchGithubUser(owner, repo)
    }

    val networkState: LiveData<NetworkState> by lazy {
        repoDetailRepository.getNetworkState()
    }

}