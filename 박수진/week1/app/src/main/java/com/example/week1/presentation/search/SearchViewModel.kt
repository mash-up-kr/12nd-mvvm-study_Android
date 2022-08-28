package com.example.week1.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week1.data.model.GithubRepo
import com.example.week1.data.model.NetworkState
import com.example.week1.domain.usecase.GetSearchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchListUseCase: GetSearchListUseCase
) : ViewModel() {

    private val _searchList = MutableLiveData<List<GithubRepo>>()
    val searchList: LiveData<List<GithubRepo>>
        get() = _searchList

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    fun setRepoList(query: String) {
        _networkState.value = NetworkState.LOADING
        getSearchListUseCase(query, viewModelScope) { searchList ->
            _searchList.value = searchList.items
            _networkState.value = NetworkState.LOADED
        }
    }
}