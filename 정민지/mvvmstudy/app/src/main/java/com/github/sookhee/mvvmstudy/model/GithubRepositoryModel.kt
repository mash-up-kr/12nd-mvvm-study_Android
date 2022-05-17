package com.github.sookhee.mvvmstudy.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *  GithubRepositoryModel.kt
 *
 *  Created by Minji Jeong on 2022/04/25
 *  Copyright © 2022 MashUp All rights reserved.
 */

@Parcelize
data class GithubRepositoryModel(
    val id: Int,
    val repoName: String,
    val repoLastUpdate: String,
    val language: String,
    val ownerName: String,
    val profileImage: String,
) : Parcelable
