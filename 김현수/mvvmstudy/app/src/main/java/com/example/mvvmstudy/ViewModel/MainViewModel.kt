package com.example.mvvmstudy.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmstudy.data.MainRepository
import com.example.mvvmstudy.data.Repository

class MainViewModel : ViewModel() {

    private val mainRepository = MainRepository()

    private var page: Int = 1

    private var search: String = ""

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun setCurSearch(q: String) {
        search = q
    }

    fun getCurSearch(): String {
        return search
    }

    fun getCurPage(): Int =
        page++

    fun initSearch() {
        page = 1
        mainRepository.reposClear()
    }

    fun searchRepositories(query: String, page: String) {
        _isLoading.value = true
        mainRepository.searchRepositories(query, page)

    }

    fun getRepositories(): LiveData<List<Repository>> {
        return mainRepository.repositoryList
    }

}