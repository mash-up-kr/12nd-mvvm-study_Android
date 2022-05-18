package com.mash_up.mvvmstudy.model

import com.orhanobut.logger.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel {
    private val service = ClientFactory.createService(GitService::class.java)

    fun fetchGithubData(
        query: String,
        onSuccess: (Repositories?) -> Unit,
        onError: (String) -> Unit
    ) {
        service.getRepositories(
            query = query
        ).enqueue(object : Callback<Repositories> {
            override fun onResponse(call: Call<Repositories>, response: Response<Repositories>) {
                Logger.i("responseBody is ${response.body()}")

                if (response.isSuccessful) {
                    onSuccess(response.body())
                } else {
                    onError(
                        "Code Number: ${response.code()} Error Body: ${
                            response.errorBody().toString()
                        }"
                    )
                }
            }

            override fun onFailure(call: Call<Repositories>, t: Throwable) {
                Logger.e("onFailure is $t")
                onError(t.localizedMessage ?: "알 수 없는 오류")
            }
        })
    }
}