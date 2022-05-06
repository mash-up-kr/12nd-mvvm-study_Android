package com.example.mvvmstudy.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmstudy.Network.RetrofitClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository {

    private val repos = mutableListOf<Repository>()

    private val _repositoryList = MutableLiveData<List<Repository>>()
    val repositoryList: LiveData<List<Repository>>
        get() = _repositoryList

    fun searchRepositories(query: String, page: String) {
        RetrofitClass.retrofitService
            .searchRepositories(query, page, 50)
            .enqueue(object : Callback<Repositories> {
                override fun onResponse(
                    call: Call<Repositories>,
                    response: Response<Repositories>
                ) {
                    when (response?.code() ?: 0) {
                        200 -> {
                            val body = response.body() as Repositories
                            repos.addAll(body.repositories)
                            _repositoryList.value = repos
                            Log.d("api", "Ok")
                        }
                        304 -> {
                            Log.d("api", "Not modified")
                        }
                        422 -> {
                            Log.d("api", "Validation failed")
                        }
                        503 -> {
                            Log.d("api", "Service unavailable")
                        }
                    }
                }

                override fun onFailure(call: Call<Repositories>, t: Throwable) {
                    Log.d("api", "Failed")
                }
            })
    }

    fun reposClear() {
        repos.clear()
        _repositoryList.value = repos
    }

}

