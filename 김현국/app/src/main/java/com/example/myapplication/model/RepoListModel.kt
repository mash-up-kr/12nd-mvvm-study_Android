package com.example.myapplication.model

import com.example.myapplication.network.ApiClient
import com.example.myapplication.network.ApiInterface
import com.example.myapplication.presenter.search.SearchContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author 김현국
 * @created 2022/04/25
 */
class RepoListModel : SearchContract.Model {
    override fun getRepoList(
        onFinishedListener: SearchContract.Model.OnFinishedListener,
        q: String
    ) {
        val service: ApiInterface? = ApiClient().getInstance()?.create(ApiInterface::class.java)
        val call: Call<RepoResponse>? = service?.getRepositories(q = q)

        call?.enqueue(object : Callback<RepoResponse> {
            override fun onResponse(
                call: Call<RepoResponse>,
                response: Response<RepoResponse>
            ) {
                if (!response.isSuccessful) {
                    return
                }
                val repoList: List<Repository> = response.body()?.items!!
                onFinishedListener.onFinished(repos = repoList)
            }

            override fun onFailure(call: Call<RepoResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }
}
