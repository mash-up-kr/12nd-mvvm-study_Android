package com.github.sookhee.mvvmstudy.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.sookhee.mvvmstudy.ResultState
import com.github.sookhee.mvvmstudy.model.GithubRepositoryModel
import com.github.sookhee.mvvmstudy.network.GithubAPI
import com.github.sookhee.mvvmstudy.network.RetrofitClient
import com.github.sookhee.mvvmstudy.network.spec.GithubRepositoryResponse
import com.github.sookhee.mvvmstudy.repository.GithubRepository
import com.github.sookhee.mvvmstudy.repository.OnNetworkCallbackListener

/**
 *  MainViewModel.kt
 *
 *  Created by Minji Jeong on 2022/05/02
 *  Copyright © 2022 MashUp All rights reserved.
 */

class MainViewModel : ViewModel(), OnNetworkCallbackListener {
    private val _repositoryResultState = MutableLiveData<ResultState<List<GithubRepositoryModel>>>()
    val repositoryResultState: LiveData<ResultState<List<GithubRepositoryModel>>> = _repositoryResultState

    private val request by lazy { RetrofitClient.buildService(GithubAPI::class.java) }
    private val githubRepository = GithubRepository(request, this)

    override fun onSuccess(list: List<GithubRepositoryResponse>) {
        _repositoryResultState.value = ResultState.Success(mapToGithubRepositoryModelList(list))
    }

    override fun onFailure(throwable: Throwable) {
        _repositoryResultState.value = ResultState.Error(throwable.message.toString())
    }

    fun requestDataToGithubAPI(keyword: String) {
        _repositoryResultState.value = ResultState.Loading
        if (keyword.isNotEmpty()) {
            githubRepository.getGithubRepositoryListWithQuery(keyword)
        } else {
            githubRepository.getGithubRepositoryList()
        }
    }

    private fun mapToGithubRepositoryModelList(response: List<GithubRepositoryResponse>): List<GithubRepositoryModel> {
        return response.map { repository ->
            GithubRepositoryModel(
                id = repository.id,
                repoName = repository.name,
                repoLastUpdate = repository.lastUpdate ?: "",
                language = repository.language ?: "",
                ownerName = repository.owner.name,
                profileImage = repository.owner.profileImage
            )
        }
    }
}
