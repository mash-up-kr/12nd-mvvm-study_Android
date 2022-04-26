package com.test.mvvmstudy.presenter

import android.util.Log
import android.widget.Toast
import com.test.mvvmstudy.api.Retrofit
import com.test.mvvmstudy.data.Result
import com.test.mvvmstudy.data.ResultDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPresenter : SearchContract.Presenter {

    private var searchView : SearchContract.View? = null

    override fun takeView(view: SearchContract.View) {
        searchView = view
    }

    override fun getSearchResult(query: String) {
        searchView?.showLoading()
        val searchApiCall = Retrofit.githubApi.getSearchList(query)
        searchApiCall.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                if (response.isSuccessful) {
                    val list = response.body()!!.items
                    searchView?.showResult(list)
                }
                searchView?.hideLoading()
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                Log.d("fail", t.message.toString())
            }

        })
    }
}