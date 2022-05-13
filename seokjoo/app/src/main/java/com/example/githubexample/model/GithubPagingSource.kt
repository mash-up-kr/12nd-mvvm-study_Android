package com.example.githubexample.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubexample.entities.GithubResult

class GithubPagingSource(
    private val query: String
) : PagingSource<Int, GithubResult.Item>() {
    override fun getRefreshKey(state: PagingState<Int, GithubResult.Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubResult.Item> {
        val position = params.key ?: STARTING_KEY
        runCatching {
            NetworkManager.repositoryApi.getSearchRepository(query, position, params.loadSize)
        }.onSuccess { searchItem ->
            val item = searchItem.items
            val nextKey = if (item.isEmpty()) {
                null
            } else {
                position + (params.loadSize) / PER_PAGE
            }
            return LoadResult.Page(
                data = item,
                nextKey = nextKey,
                prevKey = if (position == STARTING_KEY) null else position - 1
            )
        }.onFailure { error ->
            return LoadResult.Error(error)
        }.also {
            return LoadResult.Invalid()
        }
    }

    companion object {
        const val STARTING_KEY = 1
        const val PER_PAGE = 50
    }
}
