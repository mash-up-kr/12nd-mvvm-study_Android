package com.example.myapplication.network

import com.example.myapplication.model.RepoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author 김현국
 * @created 2022/04/25
 */
interface ApiInterface {

    @GET("search/repositories")
    fun getRepositories(
        @Query("q") q: String?
    ): Call<RepoResponse>
}
