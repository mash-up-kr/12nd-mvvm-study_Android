package com.example.week1.data.repository

import com.example.week1.data.model.GithubRepoList
import com.example.week1.data.datasource.RepoSearchRemoteSourceImpl
import com.example.week1.domain.repository.RepoSearchRepository

class RepoSearchRepositoryImpl : RepoSearchRepository {

    private val repoSearchRemoteSourceImpl = RepoSearchRemoteSourceImpl()

    override suspend fun getRepoList(query: String): GithubRepoList =
        repoSearchRemoteSourceImpl.getRepoList(query)
}