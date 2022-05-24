package com.joocoding.android.app.githubsearch.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joocoding.android.app.githubsearch.model.Event
import com.joocoding.android.app.githubsearch.model.GithubRepository
import com.joocoding.android.app.githubsearch.model.Response
import com.joocoding.android.app.githubsearch.model.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val githubRepository = GithubRepository()

    private val _repositories = MutableStateFlow<List<Repository>>(emptyList())
    val repositories: StateFlow<List<Repository>> = _repositories.asStateFlow()

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> = _toastMessage

    fun getRepository(query: String = "") {
        viewModelScope.launch {
            runCatching {
                githubRepository.getRepositories(query)

            }.onSuccess { data ->
                when (data) {
                    is Response.Success -> {
                        _repositories.value = data.data?.repositories ?: emptyList()
                    }
                    is Response.Error -> {
                        data.message?.let { message ->
                            _toastMessage.postValue(Event(message))
                        }
                    }

                }

            }.onFailure { e ->
                Log.e("getRepository", e.toString())
            }


        }
    }
}





