package com.github.sookhee.mvvmstudy.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sookhee.mvvmstudy.ResultState
import com.github.sookhee.mvvmstudy.model.GithubRepositoryModel
import com.github.sookhee.mvvmstudy.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  MainViewModel.kt
 *
 *  Created by Minji Jeong on 2022/05/02
 *  Copyright © 2022 MashUp All rights reserved.
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {
    private val _repositoryResultState =
        MutableStateFlow<ResultState<List<GithubRepositoryModel>>>(ResultState.Success(emptyList()))
    val repositoryResultState: StateFlow<ResultState<List<GithubRepositoryModel>>> =
        _repositoryResultState

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
