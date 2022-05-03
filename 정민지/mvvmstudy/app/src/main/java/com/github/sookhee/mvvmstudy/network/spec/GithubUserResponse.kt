package com.github.sookhee.mvvmstudy.network.spec

import com.google.gson.annotations.SerializedName

/**
 *  GithubUserResponse.kt
 *
 *  Created by Minji Jeong on 2022/04/25
 *  Copyright © 2022 MashUp All rights reserved.
 */

data class GithubUserResponse(
    @SerializedName("login")
    val name: String,
    @SerializedName("avatar_url")
    val profileImage: String,
)
