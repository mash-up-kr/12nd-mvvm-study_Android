package com.github.sookhee.mvvmstudy.network.spec

import com.google.gson.annotations.SerializedName

/**
 *  GithubRepositoryResponse.kt
 *
 *  Created by Minji Jeong on 2022/04/25
 *  Copyright © 2022 MashUp All rights reserved.
 */

data class GithubRepositoryResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("language")
    val language: String?,
    @SerializedName("owner")
    val owner: GithubUserResponse,
)
