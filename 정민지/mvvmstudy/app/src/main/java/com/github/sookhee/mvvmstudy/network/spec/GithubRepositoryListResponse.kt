package com.github.sookhee.mvvmstudy.network.spec

import com.google.gson.annotations.SerializedName

/**
 *  GithubRepositoryListResponse.kt
 *
 *  Created by Minji Jeong on 2022/04/25
 *  Copyright © 2022 MashUp All rights reserved.
 */

data class GithubRepositoryListResponse(
    @SerializedName("items")
    val items: List<GithubRepositoryResponse>
)
