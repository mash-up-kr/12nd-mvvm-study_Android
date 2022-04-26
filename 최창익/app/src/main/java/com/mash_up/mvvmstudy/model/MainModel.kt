package com.mash_up.mvvmstudy.model

import com.orhanobut.logger.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel {
    private val service = ClientFactory.createService(GitService::class.java)

    fun fetchGithubData(query: String, refreshUi: (Repositories?) -> Unit) {
        service.getRepositories(
            query = query
        ).enqueue(object : Callback<Repositories> {
            override fun onResponse(call: Call<Repositories>, response: Response<Repositories>) {
                Logger.i("response : ${response.body()}")
                refreshUi(response.body())
            }

            override fun onFailure(call: Call<Repositories>, t: Throwable) {
                Logger.e("error : $t")
            }
        })
    }
}