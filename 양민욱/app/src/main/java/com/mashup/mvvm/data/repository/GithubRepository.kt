package com.mashup.mvvm.data.repository

import com.mashup.mvvm.data.Response
import com.mashup.mvvm.dto.RepositoriesDto
import com.mashup.mvvm.network.GithubApi

class GithubRepository(
    private val githubApi: GithubApi
) {
    suspend fun getRepositories(query: String): Response<RepositoriesDto> {
        val networkResponse = githubApi.getRepositories(query)

        return when {
            networkResponse.isSuccessful -> {
                Response.Success(
                    code = networkResponse.code(),
                    data = networkResponse.body()
                )
            }
            else -> Response.Error(
                code = networkResponse.code(),
                message = networkResponse.message()
            )
        }
    }
}