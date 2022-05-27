package com.joocoding.android.app.githubsearch.model

import com.joocoding.android.app.githubsearch.data.dto.RepositoriesDto
import com.joocoding.android.app.githubsearch.network.SearchService
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubApi: SearchService
) {

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