package com.example.githubexample.model.remote

import com.example.githubexample.entities.GithubResult
import com.example.githubexample.model.NetworkManager
import retrofit2.Response

class RemoteDataSourceImpl : RemoteDataSource {
    override suspend fun getRepositoryList(query: String): Response<GithubResult> {
        return NetworkManager.repositoryApi.getSearchRepository(query)
    }
}
