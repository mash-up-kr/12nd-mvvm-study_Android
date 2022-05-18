package com.joocoding.android.app.githubsearch.model

import com.joocoding.android.app.githubsearch.data.dto.RepositoriesDto
import com.joocoding.android.app.githubsearch.model.error.CODE_SERVICE_NOT_AVAILABLE
import com.joocoding.android.app.githubsearch.network.ClientFactory
import com.joocoding.android.app.githubsearch.network.SearchService

import java.io.IOException

class GithubRepository {
    private val githubApi: SearchService = ClientFactory.createService(SearchService::class.java)

    fun getRepositories(query: String): Response<RepositoriesDto>? {
        return try {
            val response = githubApi.getRepository(query).execute()

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