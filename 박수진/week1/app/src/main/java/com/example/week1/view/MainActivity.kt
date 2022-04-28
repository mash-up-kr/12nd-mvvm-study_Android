package com.example.week1.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week1.adapter.GithubRepoAdapter
import com.example.week1.base.BaseActivity
import com.example.week1.databinding.ActivityMainBinding
import com.example.week1.model.GithubRepoList
import com.example.week1.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    private var backWaitTime: Long = 0
    private lateinit var binding: ActivityMainBinding
    private val githubRepoAdapter: GithubRepoAdapter by lazy {
        GithubRepoAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()
        initRecyclerView()
        hideKeyBoard()
        initEditText()

        Handler(Looper.getMainLooper()).postDelayed({
            getGithubRepoList("kotlin")
            updateQuery()
        }, 200)
    }

    private fun initActionBar() {
        setSupportActionBar(binding.searchBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRecyclerView() {
        binding.searchRecyclerview.adapter = githubRepoAdapter
    }

    private fun initEditText() {
        binding.searchEt.text = null
    }

    private fun getGithubRepoList(query: String) {
        RetrofitService.client.getRepoList(query)
            .enqueue(object : Callback<GithubRepoList> {
                override fun onResponse(
                    call: Call<GithubRepoList>,
                    response: Response<GithubRepoList>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.items.let { githubRepoAdapter.submitList(it) }
                    }
                }

                override fun onFailure(call: Call<GithubRepoList>, t: Throwable) {
                    Log.e("retrofit", t.toString())
                }
            })
    }

    private fun updateQuery() {
        binding.searchEt.setOnEditorActionListener { _, action, _ ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyBoard()

                val query = binding.searchEt.text.toString()
                if (query.isEmpty()) {
                    Toast.makeText(this@MainActivity, "검색어를 입력해 주세요.", Toast.LENGTH_LONG).show()
                } else {
                    startProgress(query)
                }
                handled = true
            }
            handled
        }
    }

    private fun startProgress(query: String) {
        onProgress()
        getGithubRepoList(query)
        Handler(Looper.getMainLooper()).postDelayed({
            offProgress()
        }, 2000)
    }

    private fun hideKeyBoard() {
        val imm: InputMethodManager =
            getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchRecyclerview.windowToken, 0)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backWaitTime >= 2000 ) {
            backWaitTime = System.currentTimeMillis()
            Toast.makeText(this@MainActivity, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show()
        } else {
            finish()
        }
    }

}