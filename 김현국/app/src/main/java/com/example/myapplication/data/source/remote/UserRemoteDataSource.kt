package com.example.myapplication.data.source.remote

import com.example.myapplication.data.model.DataOwner
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author 김현국
 * @created 2022/05/03
 */
interface UserRemoteDataSource {
    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String): Response<List<DataOwner>>

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(@Path("username") username: String): Response<List<DataOwner>>
}
