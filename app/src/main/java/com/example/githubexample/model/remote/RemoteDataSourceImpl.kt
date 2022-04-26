package com.example.githubexample.model.remote

import com.example.githubexample.entities.GithubResult
import com.example.githubexample.model.NetworkManager
import com.example.githubexample.ui.MainContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSourceImpl : RemoteDataSource {

    override fun getRepositoryList(query: String, modelResult: MainContract.Presenter.ModelResult) {
        val call = NetworkManager.repositoryApi.getSearchRepository(query)

        call.enqueue(object : Callback<GithubResult> {
            override fun onResponse(call: Call<GithubResult>, response: Response<GithubResult>) {
                if (response.isSuccessful) {
                    modelResult.onSuccess(response.body()!!.items)
                } else {
                    modelResult.onFailure(IllegalStateException("code is not 200 ~ 300"))
                }
            }

            override fun onFailure(call: Call<GithubResult>, t: Throwable) {
                modelResult.onFailure(t)
            }
        })
    }

}
