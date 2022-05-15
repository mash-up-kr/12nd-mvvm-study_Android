package com.example.githubexample.model.remote

import androidx.paging.PagingData
import com.example.githubexample.entities.GithubResult
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getRepositoryList(query: String): Flow<PagingData<GithubResult.Item>>
}
