package com.example.week1.domain.repository

import androidx.lifecycle.LiveData
import com.example.week1.data.dataclass.GithubRepo
import com.example.week1.data.network.NetworkState
import com.example.week1.data.source.RepoListDataSource

class RepoListRepository {

    private val repoListDataSource = RepoListDataSource()

    fun getRepoList(query: String): LiveData<List<GithubRepo>> {
        repoListDataSource.getRepoList(query)
        return repoListDataSource.repoListResponse
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return repoListDataSource.networkState
    }
}