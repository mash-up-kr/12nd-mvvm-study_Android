package com.mash_up.mvvmstudy.repository

import com.mash_up.mvvmstudy.repository.model.Repositories
import com.mash_up.mvvmstudy.repository.remote.ClientFactory
import com.mash_up.mvvmstudy.repository.remote.GitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository {
    private val api: GitService = ClientFactory.createService(GitService::class.java)

    fun getRepositories(
        query: String,
        onSuccess: (Repositories) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getRepositories(query).enqueue(object : Callback<Repositories> {
            override fun onResponse(call: Call<Repositories>, response: Response<Repositories>) {
                val resultBody = response.body()

                if (response.isSuccessful && resultBody != null) {
                    onSuccess(resultBody)
                } else {
                    onError(
                        "code : ${response.code()}, 다음과 같은 에러가 발생했습니다. ${
                            response.errorBody().toString()
                        }"
                    )
                }
            }

            override fun onFailure(call: Call<Repositories>, t: Throwable) {
                onError("$t")
            }

        })
    }
}