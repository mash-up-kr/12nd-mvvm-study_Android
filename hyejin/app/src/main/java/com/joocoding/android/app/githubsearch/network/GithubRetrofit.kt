package com.joocoding.android.app.githubsearch.network

import android.app.DownloadManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joocoding.android.app.githubsearch.MainActivity
import com.joocoding.android.app.githubsearch.model.response.Repositories
import com.joocoding.android.app.githubsearch.model.response.Repository
import com.joocoding.android.app.githubsearch.repository.GithubRepository
import com.joocoding.android.app.githubsearch.service.SearchService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "GithubRetrofit"
private const val BASE_URL = "https://api.github.com/"

class GithubRetrofit {
    private val _repositories: MutableLiveData<List<Repository>> = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>>
        get() = _repositories

    //by lazy, init 차이
    private val searchService: SearchService by lazy {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(SearchService::class.java)
    }


    private fun searchRepository(repositoryRequest: Call<Repositories>) {

        repositoryRequest.enqueue(object : Callback<Repositories> {
            override fun onFailure(call: Call<Repositories>, t: Throwable) {
                Log.e(TAG, "Failed to fetch photos", t)
            }

            override fun onResponse(
                call: Call<Repositories>,
                response: Response<Repositories>
            ) {
                Log.d(TAG, "Response received")
                val repositories: Repositories? = response.body()
                var repository: List<Repository> =
                    repositories?.repositories?.filterNot {
                        it.owner.avatarUrl.isBlank()
                    } ?: emptyList()

                Log.i(TAG, "repositoryResponse= $repository")
                _repositories.postValue(repository)

            }

        })

    }

    fun getRepository(query: String = "") {
        searchRepository(searchService.getRepository(query))
    }


}