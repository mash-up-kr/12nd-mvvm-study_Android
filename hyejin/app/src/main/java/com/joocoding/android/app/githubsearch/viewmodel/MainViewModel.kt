package com.joocoding.android.app.githubsearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joocoding.android.app.githubsearch.model.Event
import com.joocoding.android.app.githubsearch.model.GithubRepository
import com.joocoding.android.app.githubsearch.model.Response
import com.joocoding.android.app.githubsearch.model.data.Repository


class MainViewModel : ViewModel() {

    private val githubRepository = GithubRepository()

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> = _repositories

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> = _toastMessage

    fun getRepository(query: String = "") {
        Thread {
            when (val data = githubRepository.getRepositories(query)) {
                is Response.Success -> {
                    _repositories.postValue(data.data.repositories)
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


