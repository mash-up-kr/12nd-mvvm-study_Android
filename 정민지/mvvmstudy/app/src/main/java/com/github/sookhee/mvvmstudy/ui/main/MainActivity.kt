package com.github.sookhee.mvvmstudy.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.github.sookhee.mvvmstudy.databinding.ActivityMainBinding
import com.github.sookhee.mvvmstudy.interactor.MainInteractor
import com.github.sookhee.mvvmstudy.model.GithubRepositoryModel
import com.github.sookhee.mvvmstudy.ui.detail.DetailActivity

/**
 *  MainActivity.kt
 *
 *  Created by Minji Jeong on 2022/04/25
 *  Copyright © 2022 MashUp All rights reserved.
 */

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var binding: ActivityMainBinding

    private val interactor by lazy { MainInteractor() }
    private val presenter by lazy { MainPresenter(this, interactor) }
    private val repositoryAdapter by lazy {
        RepositoryAdapter().apply {
            onItemClick = {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(EXTRA_REPOSITORY_ID, it.id)

                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initView()
        presenter.requestDataToGithubAPI(EMPTY_STRING)
    }

    override fun setDataToRecyclerView(list: List<GithubRepositoryModel>) {
        repositoryAdapter.submitList(list)
    }

    override fun showErrorMessageToast(message: String) {
        Toast.makeText(this, "error: $message", Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progress.visibility = View.GONE
    }

    private fun initView() {
        with(binding) {
            repositoryRecyclerView.adapter = repositoryAdapter

            searchButton.setOnClickListener {
                val keyword = binding.searchEditText.text.toString()
                presenter.requestDataToGithubAPI(keyword)
            }

            clearTextButton.setOnClickListener {
                binding.searchEditText.text = null
            }

            searchEditText.addTextChangedListener {
                if (it?.length ?: 0 > 0) binding.clearTextButton.visibility = View.VISIBLE
                else binding.clearTextButton.visibility = View.GONE
            }

            searchEditText.setOnEditorActionListener { _, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    val keyword = binding.searchEditText.text.toString()
                    presenter.requestDataToGithubAPI(keyword)

                    return@setOnEditorActionListener true
                }

                return@setOnEditorActionListener false
            }
        }
    }

    companion object {
        const val EXTRA_REPOSITORY_ID = "EXTRA_REPOSITORY_ID"
        const val EMPTY_STRING = ""
    }
}
