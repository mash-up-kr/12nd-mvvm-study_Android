package com.example.week1.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.week1.data.dataclass.GithubRepo
import com.example.week1.data.dataclass.GithubRepoList
import com.example.week1.data.network.NetworkState
import com.example.week1.data.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoListDataSource {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _repoListResponse = MutableLiveData<List<GithubRepo>>()
    val repoListResponse: LiveData<List<GithubRepo>>
        get() = _repoListResponse

    fun getRepoList(query: String) {
        _networkState.postValue(NetworkState.LOADING)
        RetrofitService.client.getGithubRepoList(query)
            .enqueue(object : Callback<GithubRepoList> {
                override fun onResponse(
                    call: Call<GithubRepoList>,
                    response: Response<GithubRepoList>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { _repoListResponse.postValue(it.items) }
                        _networkState.postValue(NetworkState.LOADED)
                    } else {
                        Log.e("Error", response.message())
                        _networkState.postValue(NetworkState.ERROR)
                    }
                }

                override fun onFailure(call: Call<GithubRepoList>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }
            })
    }
}