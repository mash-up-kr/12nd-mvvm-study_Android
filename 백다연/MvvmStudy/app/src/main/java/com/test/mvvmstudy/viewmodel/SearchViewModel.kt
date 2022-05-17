package com.test.mvvmstudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.mvvmstudy.data.NetworkResult
import com.test.mvvmstudy.repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
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
                .catch { e ->
                    _resultStateFlow.value = NetworkResult.ErrorDataResult(exception = e)
                }.collect { result ->
                    _resultStateFlow.value = NetworkResult.SuccessDataResult(data = result)
                }
        }
    }
}