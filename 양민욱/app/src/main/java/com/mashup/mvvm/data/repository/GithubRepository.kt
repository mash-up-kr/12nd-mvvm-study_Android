package com.mashup.mvvm.data.repository

import com.mashup.mvvm.data.Response
import com.mashup.mvvm.data.error.CODE_SERVICE_NOT_AVAILABLE
import com.mashup.mvvm.dto.RepositoriesDto
import com.mashup.mvvm.network.GithubApi
import java.io.IOException

class GithubRepository(
    private val githubApi: GithubApi
) {
    fun getRepositories(query: String): Response<RepositoriesDto>? {
        return try {
            val response = githubApi.getRepositories(query).execute()

            when {
                response.isSuccessful -> {
                    response.body()?.run {
                        Response.Success(
                            code = response.code(),
                            data = this
                        )
                    }
                }
                response.code() == CODE_SERVICE_NOT_AVAILABLE -> {
                    Response.Error(
                        throwable = IllegalStateException("현재 api 서버가 불안정합니다.")
                    )
                }
                else -> Response.Error()
            }
        } catch (e: IOException) {
            null
        }
    }
}