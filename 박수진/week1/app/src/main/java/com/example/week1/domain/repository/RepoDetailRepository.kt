package com.example.week1.domain.repository

import androidx.lifecycle.LiveData
import com.example.week1.data.dataclass.GithubRepo
import com.example.week1.data.network.NetworkState
import com.example.week1.data.source.RepoDetailDataSource

class RepoDetailRepository {
    lateinit var repoDetailDataSource: RepoDetailDataSource

    fun fetchRepoDetail(owner: String, repo: String): LiveData<GithubRepo> {
        repoDetailDataSource = RepoDetailDataSource()
        repoDetailDataSource.fetchRepoDetail(owner, repo)

        return repoDetailDataSource.repoDetailResponse
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return repoDetailDataSource.networkState
    }
}