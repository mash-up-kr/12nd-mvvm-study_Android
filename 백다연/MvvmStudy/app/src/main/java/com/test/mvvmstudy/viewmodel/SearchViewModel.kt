package com.test.mvvmstudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.mvvmstudy.data.NetworkResult
import com.test.mvvmstudy.repository.SearchRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = SearchRepository()

    private val _resultStateFlow: MutableStateFlow<NetworkResult> =
        MutableStateFlow(NetworkResult.Empty)
    val resultStateFlow: StateFlow<NetworkResult>
        get() = _resultStateFlow

    fun getSearchData(query: String) {
        viewModelScope.launch {
            _resultStateFlow.value = NetworkResult.Loading
            repository.searchRepository(query)
                .catch { exception ->
                    _resultStateFlow.value = NetworkResult.ErrorDataResult(exception = exception)
                }.collectLatest { result ->
                    _resultStateFlow.value = NetworkResult.SuccessDataResult(data = result)
                }
        }
    }
}