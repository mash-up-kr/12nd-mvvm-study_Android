package com.github.sookhee.mvvmstudy.ui.main

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.sookhee.mvvmstudy.ResultState
import com.github.sookhee.mvvmstudy.databinding.ActivityMainBinding
import com.github.sookhee.mvvmstudy.model.GithubRepositoryModel
import com.github.sookhee.mvvmstudy.ui.detail.DetailActivity
import com.github.sookhee.mvvmstudy.ui.showIf
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 *  MainActivity.kt
 *
 *  Created by Minji Jeong on 2022/04/25
 *  Copyright © 2022 MashUp All rights reserved.
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val repositoryAdapter by lazy {
        RepositoryAdapter().apply {
            onItemClick = {
                startActivity(DetailActivity.newIntent(this@MainActivity, it))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initView()
        observeData()

        viewModel.requestDataToGithubAPI(EMPTY_STRING)
    }

    private fun initView() {
        setRepositoryRecyclerView()
        setSearchEditText()
        setOnClickListener()
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.repositoryResultState.collect { result ->
                    when (result) {
                        is ResultState.Loading -> showProgress()
                        is ResultState.Success -> {
                            hideProgress()
                            setDataToRecyclerView(result.data)
                        }
                        is ResultState.Error -> {
                            hideProgress()
                            showErrorMessageToast(result.message)
                        }
                    }
                }
            }
        }
    }

    private fun setRepositoryRecyclerView() {
        binding.repositoryRecyclerView.adapter = repositoryAdapter
    }

    private fun setSearchEditText() {
        binding.searchEditText.apply {
            addTextChangedListener {
                binding.clearTextButton.showIf(it?.length ?: 0 > 0)
            }

            setOnEditorActionListener { _, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    val keyword = binding.searchEditText.text.toString()
                    viewModel.requestDataToGithubAPI(keyword)

                    return@setOnEditorActionListener true
                }

                return@setOnEditorActionListener false
            }
        }
    }

    private fun setOnClickListener() {
        binding.searchButton.setOnClickListener {
            val keyword = binding.searchEditText.text.toString()
            viewModel.requestDataToGithubAPI(keyword)
        }

        binding.clearTextButton.setOnClickListener {
            binding.searchEditText.text = null
        }
    }

    private fun setDataToRecyclerView(list: List<GithubRepositoryModel>) {
        repositoryAdapter.submitList(list)
    }

    private fun showErrorMessageToast(message: String) {
        Toast.makeText(this, "error: $message", Toast.LENGTH_SHORT).show()
    }

    private fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progress.visibility = View.GONE
    }

    companion object {
        private val TAG = MainActivity::class.simpleName

        private const val EMPTY_STRING = ""
    }
}
