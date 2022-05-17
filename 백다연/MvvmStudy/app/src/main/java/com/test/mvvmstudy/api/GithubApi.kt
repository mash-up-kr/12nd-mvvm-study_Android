package com.test.mvvmstudy.api

import com.test.mvvmstudy.data.NetworkResult
import com.test.mvvmstudy.data.SearchResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories")
    suspend fun getSearchList(
        @Query("q") query : String
    ) : SearchResult
}