package com.mashup.mvvm.data.repository

import com.mashup.mvvm.data.Response
import com.mashup.mvvm.network.GithubApi
import com.mashup.mvvm.data.dto.RepositoriesDto
import javax.inject.Inject

class GithubRepository @Inject constructor(
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