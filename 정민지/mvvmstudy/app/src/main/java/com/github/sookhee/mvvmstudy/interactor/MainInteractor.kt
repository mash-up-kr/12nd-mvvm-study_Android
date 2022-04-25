package com.github.sookhee.mvvmstudy.interactor

import android.util.Log
import com.github.sookhee.mvvmstudy.network.spec.GithubRepositoryResponse
import com.github.sookhee.mvvmstudy.network.GithubAPI
import com.github.sookhee.mvvmstudy.network.RetrofitClient
import com.github.sookhee.mvvmstudy.network.spec.GithubRepositoryListResponse
import com.github.sookhee.mvvmstudy.ui.main.MainContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *  MainInteractor.kt
 *
 *  Created by Minji Jeong on 2022/04/25
 *  Copyright © 2022 MashUp All rights reserved.
 */

class MainInteractor : MainContract.Interactor {
    override fun getGithubRepositoryList(onNetworkCallbackListener: MainContract.Interactor.OnNetworkCallbackListener) {
        val request = RetrofitClient.buildService(GithubAPI::class.java)
        val call = request.getRepository()

        call.enqueue(object : Callback<List<GithubRepositoryResponse>> {
            override fun onResponse(
                call: Call<List<GithubRepositoryResponse>>,
                response: Response<List<GithubRepositoryResponse>>
            ) {
                Log.d(TAG, "onResponse: $response")

                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: ${response.body()}")
                    response.body()?.let {
                        onNetworkCallbackListener.onSuccess(it)
                    }
                } else if (response.code() != 200) {
                    onNetworkCallbackListener.onFailure(Throwable("${response.code()}"))
                }
            }

            override fun onFailure(call: Call<List<GithubRepositoryResponse>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                onNetworkCallbackListener.onFailure(t)
            }
        })
    }

    override fun getGithubRepositoryListWithQuery(
        onNetworkCallbackListener: MainContract.Interactor.OnNetworkCallbackListener,
        keyword: String
    ) {
        val request = RetrofitClient.buildService(GithubAPI::class.java)
        val call = request.getRepositoryListWithQuery(keyword)

        call.enqueue(object : Callback<GithubRepositoryListResponse> {
            override fun onResponse(
                call: Call<GithubRepositoryListResponse>,
                response: Response<GithubRepositoryListResponse>
            ) {
                Log.d(TAG, "onResponse: $response")

                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: ${response.body()?.items}")
                    response.body()?.items?.let {
                        onNetworkCallbackListener.onSuccess(it)
                    }
                }
            }

            override fun onFailure(call: Call<GithubRepositoryListResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                onNetworkCallbackListener.onFailure(t)
            }
        })
    }

    companion object {
        private val TAG = MainInteractor::class.simpleName
    }
}
