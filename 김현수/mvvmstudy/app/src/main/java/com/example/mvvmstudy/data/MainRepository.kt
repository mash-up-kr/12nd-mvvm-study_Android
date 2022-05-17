package com.example.mvvmstudy.data

import com.example.mvvmstudy.network.RetrofitClass


class MainRepository {

    // Retrofit 내부에서 디스패처를 IO로 바꿔줌
    suspend fun searchRepositories(query: String) =
        RetrofitClass.retrofitService
            .searchRepositories(query,50)
}

