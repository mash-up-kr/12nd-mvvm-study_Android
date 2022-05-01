package com.example.githubexample.model.remote

import com.example.githubexample.entities.GithubResult
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getRepositoryList(query: String): Response<GithubResult>
}
