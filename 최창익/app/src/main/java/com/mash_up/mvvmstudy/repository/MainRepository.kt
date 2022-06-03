package com.mash_up.mvvmstudy.repository

import com.mash_up.mvvmstudy.repository.model.Repositories
import com.mash_up.mvvmstudy.repository.remote.ClientFactory
import com.mash_up.mvvmstudy.repository.remote.GitService
import com.mash_up.mvvmstudy.repository.remote.MainRemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainRepository (
    private val mainRemoteDataSource: MainRemoteDataSource
) {
    suspend fun getRepositories(
        query: String
    ): Result<Repositories?> {
        val response = mainRemoteDataSource.getRepositories(query)

        return if (response.isSuccessful) {
            Result.success(response.body())
        } else {
            Result.failure(
                IOException(
                    "code : ${response.code()}, 다음과 같은 에러가 발생했습니다. ${
                        response.errorBody().toString()
                    }"
                )
            )
        }
    }
}