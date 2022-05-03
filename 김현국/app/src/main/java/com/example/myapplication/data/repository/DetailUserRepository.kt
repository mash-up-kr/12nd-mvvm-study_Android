package com.example.myapplication.data.repository

import com.example.myapplication.base.BaseResponse
import com.example.myapplication.data.mapper.detailUserToPresenterModel
import com.example.myapplication.data.model.DataOwner
import com.example.myapplication.data.source.remote.UserRemoteDataSource
import com.example.myapplication.network.ApiClient
import com.example.myapplication.ui.model.PresenterOwner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author 김현국
 * @created 2022/05/03
 */
class DetailUserRepository {
    fun getUserFollowing(
        username: String,
        callback: BaseResponse<List<PresenterOwner>>
    ) {
        callback.onLoading()
        val service: UserRemoteDataSource = ApiClient.buildApi(UserRemoteDataSource::class.java)
        val call: Call<List<DataOwner>> = service.getUserFollowing(username = username)

        call.enqueue(object : Callback<List<DataOwner>> {
            override fun onResponse(
                call: Call<List<DataOwner>>,
                response: Response<List<DataOwner>>
            ) {
                if (response.isSuccessful) {
                    callback.onSuccess(
                        data = detailUserToPresenterModel(response.body()!!)
                    )
                }
            }

            override fun onFailure(call: Call<List<DataOwner>>, t: Throwable) {
                callback.onError(t)
            }
        })
    }

    fun getUserFollower(
        username: String,
        callback: BaseResponse<List<PresenterOwner>>
    ) {
        callback.onLoading()
        val service: UserRemoteDataSource = ApiClient.buildApi(UserRemoteDataSource::class.java)
        val call: Call<List<DataOwner>> = service.getUserFollowers(username = username)

        call.enqueue(object : Callback<List<DataOwner>> {
            override fun onResponse(
                call: Call<List<DataOwner>>,
                response: Response<List<DataOwner>>
            ) {
                if (response.isSuccessful) {
                    callback.onSuccess(
                        data = detailUserToPresenterModel(response.body()!!)
                    )
                }
            }

            override fun onFailure(call: Call<List<DataOwner>>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
}
