package com.github.sookhee.mvvmstudy.ui.main

import com.github.sookhee.mvvmstudy.model.GithubRepositoryModel
import com.github.sookhee.mvvmstudy.network.spec.GithubRepositoryResponse

/**
 *  MainPresenter.kt
 *
 *  Created by Minji Jeong on 2022/04/25
 *  Copyright © 2022 MashUp All rights reserved.
 */

class MainPresenter(
    private var mainView: MainContract.View,
    private var interactor: MainContract.Interactor
) : MainContract.Presenter, MainContract.Interactor.OnNetworkCallbackListener {

    override fun requestDataToGithubAPI(keyword: String) {
        mainView.showProgress()
        if (keyword.isNotEmpty()) {
            interactor.getGithubRepositoryListWithQuery(this, keyword)
        } else {
            interactor.getGithubRepositoryList(this)
        }
    }

    override fun onSuccess(list: List<GithubRepositoryResponse>) {
        val repositoryList = mapToGithubRepositoryModelList(list)
        mainView.setDataToRecyclerView(repositoryList)

        mainView.hideProgress()
    }

    override fun onFailure(throwable: Throwable) {
        mainView.hideProgress()
        mainView.showErrorMessageToast(throwable.message ?: "")
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
