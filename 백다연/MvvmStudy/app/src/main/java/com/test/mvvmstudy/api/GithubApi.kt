package com.test.mvvmstudy.api
import com.test.mvvmstudy.data.Result
import com.test.mvvmstudy.data.ResultDetail

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories")
    fun getSearchList(
        @Query("q") query : String
    ) : Call<Result>
}