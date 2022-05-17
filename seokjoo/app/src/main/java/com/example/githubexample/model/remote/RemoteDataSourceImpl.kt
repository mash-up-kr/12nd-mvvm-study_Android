package com.example.githubexample.model.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubexample.entities.GithubResult
import com.example.githubexample.model.GithubPagingSource
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl : RemoteDataSource {
    override fun getRepositoryList(query: String): Flow<PagingData<GithubResult.Item>> = Pager(
        config = PagingConfig(pageSize = 50, enablePlaceholders = false),
        pagingSourceFactory = { GithubPagingSource(query = query) }
    ).flow
}
