package com.example.week1.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.week1.data.network.NetworkState
import com.example.week1.presentation.adapter.GithubRepoAdapter
import com.example.week1.presentation.BaseActivity
import com.example.week1.databinding.ActivityMainBinding
import com.example.week1.presentation.viewmodel.RepoListViewModel

class MainActivity : BaseActivity() {

    private var backWaitTime: Long = 0
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: RepoListViewModel
    private val githubRepoAdapter: GithubRepoAdapter by lazy {
        GithubRepoAdapter { repo ->
            val intent = Intent(this, RepoDetailActivity::class.java)
            intent.putExtra("repo", repo)
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

        viewModel = getViewModel()
        viewModel.repoList.observe(this) {
            githubRepoAdapter.submitList(it)
        }

        viewModel.networkState.observe(this) {
            if (it == NetworkState.LOADING) onProgress()
            else offProgress()
        }

    }

    private fun initActionBar() {
        setSupportActionBar(binding.searchBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRecyclerView() {
        binding.searchRecyclerview.adapter = githubRepoAdapter
    }

    private fun getViewModel(): RepoListViewModel =
        ViewModelProviders.of(this).get(RepoListViewModel::class.java)

    private fun updateQuery() {
        binding.searchEt.setOnEditorActionListener { _, action, _ ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyBoard()

                val query = binding.searchEt.text.toString()
                if (query.isEmpty()) {
                    Toast.makeText(this@MainActivity, "검색어를 입력해 주세요.", Toast.LENGTH_LONG).show()
                } else {
                    viewModel.getQueryData(query)
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