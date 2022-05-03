package com.example.week1.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.week1.data.dataclass.GithubRepoList
import com.example.week1.data.network.RetrofitService
import com.example.week1.presentation.adapter.GithubRepoAdapter
import com.example.week1.presentation.BaseActivity
import com.example.week1.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    private var backWaitTime: Long = 0
    private lateinit var binding: ActivityMainBinding
    private val githubRepoAdapter: GithubRepoAdapter by lazy {
        GithubRepoAdapter { repo ->
            val intent = Intent(this, RepoDetailActivity::class.java)
            intent.putExtra("owner", repo.owner.login)
            intent.putExtra("repo", repo.name)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()
        initRecyclerView()
        hideKeyBoard()

        updateQuery()

    }

    private fun initActionBar() {
        setSupportActionBar(binding.searchBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRecyclerView() {
        binding.searchRecyclerview.adapter = githubRepoAdapter
    }

    private fun getGithubRepoList(query: String) {
        onProgress()
        RetrofitService.client.getGithubRepoList(query)
            .enqueue(object : Callback<GithubRepoList> {
                override fun onResponse(
                    call: Call<GithubRepoList>,
                    response: Response<GithubRepoList>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.items.let { githubRepoAdapter.submitList(it) }
                        offProgress()
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
                    getGithubRepoList(query)
                }
                handled = true
            }
            handled
        }
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