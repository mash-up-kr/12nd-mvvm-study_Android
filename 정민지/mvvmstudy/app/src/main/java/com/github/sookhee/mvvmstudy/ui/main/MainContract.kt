package com.github.sookhee.mvvmstudy.ui.main

import com.github.sookhee.mvvmstudy.model.GithubRepositoryModel
import com.github.sookhee.mvvmstudy.network.spec.GithubRepositoryResponse

/**
 *  MainContract.kt
 *
 *  Created by Minji Jeong on 2022/04/25
 *  Copyright © 2022 MashUp All rights reserved.
 */

class MainContract {
    interface View {
        fun setDataToRecyclerView(list: List<GithubRepositoryModel>)
        fun showErrorMessageToast(message: String)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun requestDataToGithubAPI(keyword: String)
    }

    interface Interactor {
        interface OnNetworkCallbackListener {
            fun onSuccess(list: List<GithubRepositoryResponse>)
            fun onFailure(throwable: Throwable)
        }

        fun getGithubRepositoryList(onNetworkCallbackListener: OnNetworkCallbackListener)
        fun getGithubRepositoryListWithQuery(onNetworkCallbackListener: OnNetworkCallbackListener, keyword: String)
    }
}
