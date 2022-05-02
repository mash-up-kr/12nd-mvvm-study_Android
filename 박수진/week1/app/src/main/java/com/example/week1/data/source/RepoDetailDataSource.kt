package com.example.week1.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.week1.data.dataclass.GithubUser
import com.example.week1.data.network.NetworkState
import com.example.week1.data.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoDetailDataSource {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _repoDetailResponse = MutableLiveData<GithubUser>()
    val repoDetailResponse: LiveData<GithubUser>
        get() = _repoDetailResponse

    fun fetchGithubUser(username: String) {
        _networkState.postValue(NetworkState.LOADING)
        RetrofitService.client.getGithubUser(username)
            .enqueue(object : Callback<GithubUser> {
                override fun onResponse(
                    call: Call<GithubUser>,
                    response: Response<GithubUser>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { _repoDetailResponse.postValue(it) }
                        _networkState.postValue(NetworkState.LOADED)
                    } else {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.e("Error", response.message())
                    }
                }

                override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }
            })
    }
}