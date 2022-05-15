package com.joocoding.android.app.githubsearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.joocoding.android.app.githubsearch.model.response.Repository
import com.joocoding.android.app.githubsearch.repository.GithubRepository

class MainViewModel : ViewModel() {

    val repositories: LiveData<List<Repository>>
        get() = GithubRepository.repositories

    fun getRepository(query: String = "") {
        GithubRepository.getRepository(query)
    }


}