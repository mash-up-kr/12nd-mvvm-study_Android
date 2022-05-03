package com.example.githubexample.model

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubexample.entities.GithubResult
import retrofit2.HttpException
import java.io.IOException

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
        return try {
            val response = NetworkManager.repositoryApi.getSearchRepository(query, position, params.loadSize)
            val item = response.body()?.items ?: emptyList()

            val nextKey = if (item.isEmpty()) {
                null
            } else {
                position + (params.loadSize) / PER_PAGE
            }
            LoadResult.Page(
                data = item,
                nextKey = nextKey,
                prevKey = if (position == STARTING_KEY) null else position - 1
            )
        } catch (e: IOException) {
            Log.d("GithubPagingSource", "load: IOException")
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.d("GithubPagingSource", "load: HttpException")
            LoadResult.Error(e)
        } catch (e: Exception) {
            Log.d("GithubPagingSource", "load: Other Exception")
            LoadResult.Error(e)
        }
    }

    companion object {
        const val STARTING_KEY = 1
        const val PER_PAGE = 50
    }
}
