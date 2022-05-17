package com.test.mvvmstudy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.mvvmstudy.data.SearchResultDetail
import com.test.mvvmstudy.repository.SearchRepository

class SearchViewModel : ViewModel() {

    private val repository = SearchRepository()

    private val _result = MutableLiveData<List<SearchResultDetail>>()
    val resultList: LiveData<List<SearchResultDetail>>
        get() = _result

    val isLoading = MutableLiveData<Boolean>()

    fun getSearchData(query: String) {
        repository.searchRepository(query,
            onSuccess = { result -> _result.value = result },
            onFailure = { error -> Log.d("error", error) })
    }

}