package com.example.mvvmstudy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmstudy.data.MainRepository
import com.example.mvvmstudy.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _githubList = MutableStateFlow<List<Repository>>(emptyList())
    val githubList: StateFlow<List<Repository>> = _githubList.asStateFlow()

    private val mainRepository = MainRepository()

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
            runCatching {
                _isLoading.value = true
            }.onSuccess {
                _githubList.value = mainRepository.searchRepositories(query).repositories
            }.onFailure { e->
                Log.e("api", e.toString())
            }.also {
                _isLoading.value = false
            }
        }
    }

}