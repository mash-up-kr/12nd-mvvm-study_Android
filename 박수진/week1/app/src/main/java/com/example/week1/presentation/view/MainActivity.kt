package com.example.week1.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.week1.R
import com.example.week1.data.model.NetworkState
import com.example.week1.presentation.base.BaseActivity
import com.example.week1.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private var backWaitTime: Long = 0
    private val viewModel: RepoSearchViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var repoAdapter: RepoSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initActionBar()
        initAdapter()
        hideKeyBoard()

        observeLiveData()
        updateQuery()
    }

    private fun initActionBar() {
        setSupportActionBar(binding.searchBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initAdapter() {
        repoAdapter = RepoSearchAdapter { repo ->
            val intent = Intent(this, RepoDetailActivity::class.java)
            intent.putExtra("repo", repo)
            startActivity(intent)
        }
        binding.searchRecyclerview.adapter = repoAdapter
    }

    private fun observeLiveData() {
        viewModel.repoList.observe(this) { repoList ->
            repoAdapter.submitList(repoList)
        }

        viewModel.networkState.observe(this) {
            if (it == NetworkState.LOADING) onProgress()
            else offProgress()
        }
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
                    viewModel.setRepoList(query)
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