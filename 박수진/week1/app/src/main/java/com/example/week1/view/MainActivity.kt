package com.example.week1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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
    private val githubRepoAdapter: GithubRepoAdapter by lazy {
        GithubRepoAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.searchBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Handler(Looper.getMainLooper()).postDelayed({
            getGithubRepoList("kotlin")
            textWatcher()
        }, 200)
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
                        binding.searchRecyclerview.apply {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = githubRepoAdapter
                        }
                        githubRepoAdapter.submitList(repoList)
                    }
                }

                override fun onFailure(call: Call<GithubRepoList>, t: Throwable) {
                    // do nothing
                }
            })
    }

    fun textWatcher() {
        binding.searchEt.addTextChangedListener (object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                val query = binding.searchEt.toString()
                if (query == "") {
                    Toast.makeText(this@MainActivity, "검색어를 입력해 주세요.", Toast.LENGTH_LONG).show()
                    return
                }
                getGithubRepoList(query)
            }
        })
    }

}