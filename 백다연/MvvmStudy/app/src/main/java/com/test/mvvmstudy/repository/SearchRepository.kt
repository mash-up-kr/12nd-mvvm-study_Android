package com.test.mvvmstudy.repository

import android.util.Log
import com.test.mvvmstudy.api.Retrofit
import com.test.mvvmstudy.data.Result
import com.test.mvvmstudy.data.ResultDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository {

    fun searchRepository(
        query: String, onSuccess: (List<ResultDetail>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val searchApiCall = Retrofit.githubApi.getSearchList(query)
        searchApiCall.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it.items) }
                }
                onFailure(response.message())
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                Log.d("fail", t.message.toString())
                onFailure(t.message.toString())
            }

        })
    }
}