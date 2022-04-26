package com.example.week1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.week1.R
import com.example.week1.model.GithubRepoList
import com.example.week1.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getGithubRepoList("hello")
    }

    private fun getGithubRepoList(query: String) {
        RetrofitService.client.getRepoList(query)
            .enqueue(object : Callback<GithubRepoList> {
                override fun onResponse(
                    call: Call<GithubRepoList>,
                    response: Response<GithubRepoList>
                ) {
                    if (response.isSuccessful) {
                        // 성공
                    }
                }

                override fun onFailure(call: Call<GithubRepoList>, t: Throwable) {
                    // 실패
                }
            })
    }
}