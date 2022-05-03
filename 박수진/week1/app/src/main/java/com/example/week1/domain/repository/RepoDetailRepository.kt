package com.example.week1.domain.repository

import androidx.lifecycle.LiveData
import com.example.week1.data.dataclass.GithubUser
import com.example.week1.data.network.NetworkState
import com.example.week1.data.source.RepoDetailDataSource

class RepoDetailRepository {
    lateinit var repoDetailDataSource: RepoDetailDataSource

    fun fetchGithubUser(username: String): LiveData<GithubUser> {
        repoDetailDataSource = RepoDetailDataSource()
        repoDetailDataSource.fetchGithubUser(username)

        return repoDetailDataSource.repoDetailResponse
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return repoDetailDataSource.networkState
    }
}