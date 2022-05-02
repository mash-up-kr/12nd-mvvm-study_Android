package com.github.sookhee.mvvmstudy.ui.main

import androidx.lifecycle.ViewModel
import com.github.sookhee.mvvmstudy.model.GithubRepositoryModel
import com.github.sookhee.mvvmstudy.network.spec.GithubRepositoryResponse

/**
 *  MainViewModel.kt
 *
 *  Created by Minji Jeong on 2022/05/02
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class MainViewModel: ViewModel() {

    fun requestDataToGithubAPI(keyword: String) {
        // TODO: STATUS LOADING
        if (keyword.isNotEmpty()) {
//            interactor.getGithubRepositoryListWithQuery(keyword)
        } else {
//            interactor.getGithubRepositoryList()
        }
    }

    private fun mapToGithubRepositoryModelList(response: List<GithubRepositoryResponse>): List<GithubRepositoryModel> {
        val list = mutableListOf<GithubRepositoryModel>()

        response.forEach {
            list.add(
                GithubRepositoryModel(
                    id = it.id,
                    name = it.name,
                    language = it.language ?: "",
                    profileImage = it.owner.profileImage
                )
            )
        }

        return list
    }
}
