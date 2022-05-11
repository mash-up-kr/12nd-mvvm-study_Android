package com.github.sookhee.mvvmstudy.repository

import com.github.sookhee.mvvmstudy.network.spec.GithubRepositoryResponse

/**
 *  OnNetworkCallbackListener.kt
 *
 *  Created by Minji Jeong on 2022/05/03
 *  Copyright © 2022 MashUp All rights reserved.
 */

interface OnNetworkCallbackListener {
    fun onSuccess(list: List<GithubRepositoryResponse>)
    fun onFailure(throwable: Throwable)
}