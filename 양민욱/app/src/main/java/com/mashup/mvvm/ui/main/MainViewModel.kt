package com.mashup.mvvm.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.mvvm.data.Event
import com.mashup.mvvm.data.Response
import com.mashup.mvvm.data.model.Repository
import com.mashup.mvvm.data.repository.GithubRepository

class MainViewModel(
    private val githubRepository: GithubRepository
) : ViewModel() {
    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> = _repositories

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> = _toastMessage

    fun fetchGithubRepository(query: String) {
        Thread {
            when (val data = githubRepository.getRepositories(query)) {
                is Response.Success -> {
                    _repositories.postValue(data.data.items)
                }
                is Response.Error -> {
                    data.message?.let { message ->
                        _toastMessage.postValue(Event(message))
                    }
                }
                else -> {
                }
            }
        }.start()
    }
}