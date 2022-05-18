package com.joocoding.android.app.githubsearch.model

import com.joocoding.android.app.githubsearch.data.dto.RepositoriesDto
import com.joocoding.android.app.githubsearch.network.ClientFactory
import com.joocoding.android.app.githubsearch.network.SearchService

class GithubRepository {
    private val githubApi: SearchService = ClientFactory.createService(SearchService::class.java)

    suspend fun getRepositories(query: String): Response<RepositoriesDto> {
        val response = githubApi.getRepository(query)

        return when {
            response.isSuccessful -> {
                Response.Success(
                    code = response.code(),
                    data = response.body()
                )
            }
            else -> Response.Error(
                code = response.code(),
                message = response.message()

            )

        }
    }


}