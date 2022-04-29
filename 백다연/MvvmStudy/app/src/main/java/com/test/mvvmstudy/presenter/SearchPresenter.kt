package com.test.mvvmstudy.presenter

import android.util.Log
import android.widget.Toast
import com.test.mvvmstudy.api.Retrofit
import com.test.mvvmstudy.model.DataListModel
import com.test.mvvmstudy.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPresenter : SearchContract.Presenter {

    private lateinit var searchView: SearchContract.View

    override fun takeView(view: SearchContract.View) {
        searchView = view
    }

    override fun getSearchResult(query: String) {
        DataListModel().getSearchResult(query,
            onSuccess = { result -> searchView.showResult(result) },
            onFailure = { error -> Log.d("error", error) })
    }
}