package com.github.sookhee.mvvmstudy.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sookhee.mvvmstudy.ResultState
import com.github.sookhee.mvvmstudy.model.GithubRepositoryModel
import com.github.sookhee.mvvmstudy.network.GithubAPI
import com.github.sookhee.mvvmstudy.network.RetrofitClient
import com.github.sookhee.mvvmstudy.repository.GithubRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 *  MainViewModel.kt
 *
 *  Created by Minji Jeong on 2022/05/02
 *  Copyright © 2022 MashUp All rights reserved.
 */

class MainViewModel : ViewModel() {
    private val _repositoryResultState =
        MutableStateFlow<ResultState<List<GithubRepositoryModel>>>(ResultState.Success(emptyList()))
    val repositoryResultState: StateFlow<ResultState<List<GithubRepositoryModel>>> =
        _repositoryResultState

    private val request by lazy { RetrofitClient.buildService(GithubAPI::class.java) }
    private val githubRepository = GithubRepository(request)

    fun requestDataToGithubAPI(keyword: String) {
        _repositoryResultState.value = ResultState.Loading
        viewModelScope.launch {
            _repositoryResultState.value = if (keyword.isNotEmpty()) {
                githubRepository.getGithubRepositoryListWithQuery(keyword)
            } else {
                githubRepository.getGithubRepositoryList()
            }
        }
    }
}
