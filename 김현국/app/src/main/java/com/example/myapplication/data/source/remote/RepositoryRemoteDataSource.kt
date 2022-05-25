package com.example.myapplication.data.source.remote

import com.example.myapplication.data.response.SearchRepositoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author 김현국
 * @created 2022/05/01
 */
interface RepositoryRemoteDataSource {
    @GET("search/repositories")
    suspend fun getRepositories(@Query("q") q: String): Response<SearchRepositoryResponse>
}
