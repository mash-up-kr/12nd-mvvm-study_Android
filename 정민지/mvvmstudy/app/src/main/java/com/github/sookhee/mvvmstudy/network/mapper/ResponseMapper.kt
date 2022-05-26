package com.github.sookhee.mvvmstudy.network.mapper

import android.annotation.SuppressLint
import com.github.sookhee.mvvmstudy.model.GithubRepositoryModel
import com.github.sookhee.mvvmstudy.network.spec.GithubRepositoryResponse
import java.text.SimpleDateFormat

/**
 *  ResponseMapper.kt
 *
 *  Created by Minji Jeong on 2022/05/18
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

object ResponseMapper {
    @SuppressLint("SimpleDateFormat")
    fun mapToGithubRepositoryModelList(response: List<GithubRepositoryResponse>): List<GithubRepositoryModel> {
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss")

        return response.map { repository ->
            GithubRepositoryModel(
                id = repository.id,
                repoName = repository.name,
                repoLastUpdate = repository.lastUpdate?.let { dateFormat.format(it) } ?: "",
                language = repository.language ?: "",
                ownerName = repository.owner.name,
                profileImage = repository.owner.profileImage
            )
        }
    }
}
