package com.github.sookhee.mvvmstudy.network

import com.github.sookhee.mvvmstudy.network.spec.GithubRepositoryListResponse
import com.github.sookhee.mvvmstudy.network.spec.GithubRepositoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  GithubAPI.kt
 *
 *  Created by Minji Jeong on 2022/04/24
 *  Copyright © 2022 MashUp All rights reserved.
 */

interface GithubAPI {
    @GET("/repositories")
    fun getRepository(): Call<List<GithubRepositoryResponse>>

    @GET("/search/repositories")
    fun getRepositoryListWithQuery(
        @Query("q") keyword: String,
    ): Call<GithubRepositoryListResponse>
}
