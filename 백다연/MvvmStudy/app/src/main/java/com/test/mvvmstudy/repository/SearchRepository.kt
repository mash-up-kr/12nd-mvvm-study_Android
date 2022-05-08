package com.test.mvvmstudy.repository

import android.util.Log
import com.test.mvvmstudy.api.Retrofit
import com.test.mvvmstudy.data.SearchResult
import com.test.mvvmstudy.data.SearchResultDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository {

    fun searchRepository(
        query: String,
        onSuccess: (List<SearchResultDetail>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val searchApiCall = Retrofit.githubApi.getSearchList(query)
        searchApiCall.enqueue(object : Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it.items) }
                }
                onFailure(response.message())
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                Log.d("fail", t.message.toString())
                onFailure(t.message.toString())
            }

        })
    }
}