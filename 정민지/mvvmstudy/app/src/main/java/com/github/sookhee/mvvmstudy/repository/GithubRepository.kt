package com.github.sookhee.mvvmstudy.repository

import android.util.Log
import com.github.sookhee.mvvmstudy.network.GithubAPI
import com.github.sookhee.mvvmstudy.network.RetrofitClient
import com.github.sookhee.mvvmstudy.network.spec.GithubRepositoryListResponse
import com.github.sookhee.mvvmstudy.network.spec.GithubRepositoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *  GithubRepository.kt
 *
 *  Created by Minji Jeong on 2022/04/25
 *  Copyright © 2022 MashUp All rights reserved.
 */

class GithubRepository(
    private val request: GithubAPI,
    private val onNetworkCallbackListener: OnNetworkCallbackListener,
) {
    fun getGithubRepositoryList() {
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
                } else {
                    onNetworkCallbackListener.onFailure(Throwable(response.message()))
                }
            }

            override fun onFailure(call: Call<List<GithubRepositoryResponse>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                onNetworkCallbackListener.onFailure(t)
            }
        })
    }

    fun getGithubRepositoryListWithQuery(keyword: String) {
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
                } else {
                    onNetworkCallbackListener.onFailure(Throwable(response.message()))
                }
            }

            override fun onFailure(call: Call<GithubRepositoryListResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                onNetworkCallbackListener.onFailure(t)
            }
        })
    }

    companion object {
        private val TAG = GithubRepository::class.simpleName
    }
}
