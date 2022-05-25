package com.example.mvvmstudy.data

import com.example.mvvmstudy.network.IGithubService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class MainRepository @Inject constructor(
    private val githubService: IGithubService
) {
    suspend fun searchRepositories(query: String) =
        githubService.searchRepositories(query, 50)
}


