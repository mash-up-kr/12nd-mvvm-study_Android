package com.example.mvvmstudy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmstudy.data.MainRepository
import com.example.mvvmstudy.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _githubList = MutableStateFlow<List<Repository>>(emptyList())
    val githubList: StateFlow<List<Repository>> = _githubList.asStateFlow()

    private var searchQuery: String = ""

    fun setCurSearch(q: String) {
        searchQuery = q
    }

    fun getCurSearch(): String {
        return searchQuery
    }

    fun initSearch() {
        _githubList.value = emptyList()
    }

    fun searchRepositories(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            runCatching {
                _githubList.value = mainRepository.searchRepositories(query).repositories
            }.onSuccess {
                _isLoading.value = false
            }.onFailure { e ->
                Log.e("api", e.toString())
                _isLoading.value = false
            }
        }
    }

}