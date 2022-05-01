package com.github.sookhee.mvvmstudy.model

/**
 *  GithubRepositoryModel.kt
 *
 *  Created by Minji Jeong on 2022/04/25
 *  Copyright © 2022 MashUp All rights reserved.
 */

data class GithubRepositoryModel(
    val id: Int,
    val name: String,
    val language: String,
    val profileImage: String,
)
