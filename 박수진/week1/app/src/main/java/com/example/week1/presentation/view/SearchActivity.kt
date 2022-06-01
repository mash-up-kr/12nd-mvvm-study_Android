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
import com.example.week1.databinding.ActivitySearchBinding

class SearchActivity : BaseActivity() {

    private var backWaitTime: Long = 0
    private val viewModel: SearchViewModel by viewModels()

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

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
        searchAdapter = SearchAdapter { repo ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("repo", repo)
            startActivity(intent)
        }
        binding.searchRecyclerview.adapter = searchAdapter
    }

    private fun observeLiveData() {
        viewModel.run {
            searchList.observe(this@SearchActivity) { searchList ->
                searchAdapter.submitList(searchList)
            }
            networkState.observe(this@SearchActivity) {
                if (it == NetworkState.LOADING) onProgress()
                else offProgress()
            }
        }
    }

    private fun updateQuery() {
        binding.searchEt.setOnEditorActionListener { _, action, _ ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyBoard()

                val query = binding.searchEt.text.toString()
                if (query.isEmpty()) {
                    Toast.makeText(this@SearchActivity, "검색어를 입력해 주세요.", Toast.LENGTH_LONG).show()
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
            Toast.makeText(this@SearchActivity, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show()
        } else {
            finish()
        }
    }

}