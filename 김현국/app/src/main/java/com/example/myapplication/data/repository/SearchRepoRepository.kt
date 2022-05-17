package com.example.myapplication.data.repository

import com.example.myapplication.base.BaseResponse
import com.example.myapplication.data.mapper.searchRepositoryResponseToPresenterModel
import com.example.myapplication.data.response.SearchRepositoryResponse
import com.example.myapplication.data.source.remote.RepositoryRemoteDataSource
import com.example.myapplication.network.ApiClient
import com.example.myapplication.ui.model.PresenterRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author 김현국
 * @created 2022/05/03
 */
class SearchRepoRepository {
    fun getRepoList(
        q: String,
        callback: BaseResponse<List<PresenterRepository>>
    ) {
        callback.onLoading()
        val service: RepositoryRemoteDataSource =
            ApiClient.buildApi(RepositoryRemoteDataSource::class.java)
        val call: Call<SearchRepositoryResponse> = service.getRepositories(q = q)

        call.enqueue(object : Callback<SearchRepositoryResponse> {
            override fun onResponse(
                call: Call<SearchRepositoryResponse>,
                response: Response<SearchRepositoryResponse>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    callback.onSuccess(
                        data = searchRepositoryResponseToPresenterModel(body)
                    )
                }
            }

            override fun onFailure(call: Call<SearchRepositoryResponse>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
}
