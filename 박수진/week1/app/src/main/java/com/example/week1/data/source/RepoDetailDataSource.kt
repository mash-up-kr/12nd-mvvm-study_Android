package com.example.week1.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.week1.data.dataclass.GithubRepo
import com.example.week1.data.network.NetworkState
import com.example.week1.data.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoDetailDataSource {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _repoDetailResponse = MutableLiveData<GithubRepo>()
    val repoDetailResponse: LiveData<GithubRepo>
        get() = _repoDetailResponse

    fun fetchGithubUser(owner: String, repo: String) {
        _networkState.postValue(NetworkState.LOADING)
        RetrofitService.client.getGithubRepoDetail(owner, repo)
            .enqueue(object : Callback<GithubRepo> {
                override fun onResponse(
                    call: Call<GithubRepo>,
                    response: Response<GithubRepo>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { _repoDetailResponse.postValue(it) }
                        _networkState.postValue(NetworkState.LOADED)
                    } else {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.e("Error", response.message())
                    }
                }

                override fun onFailure(call: Call<GithubRepo>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }
            })
    }
}