package com.example.week1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week1.adapter.GithubRepoAdapter
import com.example.week1.databinding.ActivityMainBinding
import com.example.week1.model.GithubRepoList
import com.example.week1.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.searchBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
                        val repoList = response.body()!!.items
                        binding.searchRecyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
                        binding.searchRecyclerview.adapter = GithubRepoAdapter(repoList)
                    }
                }

                override fun onFailure(call: Call<GithubRepoList>, t: Throwable) {

                }
            })
    }
}