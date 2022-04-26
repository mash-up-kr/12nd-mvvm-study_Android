package com.mashup.mvvm.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.mashup.mvvm.ServiceLocator
import com.mashup.mvvm.data.Response
import com.mashup.mvvm.data.repository.GithubRepository
import com.mashup.mvvm.databinding.ActivityMainBinding
import com.mashup.mvvm.extensions.showToast
import com.mashup.mvvm.ui.main.adapter.RepositoryAdapter

class MainActivity : AppCompatActivity() {
    private val viewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            LayoutInflater.from(this)
        )
    }
    private val repositoryAdapter: RepositoryAdapter by lazy { RepositoryAdapter() }
    private val githubRepository: GithubRepository by lazy {
        GithubRepository(ServiceLocator.injectGithubApi())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        setUi()
    }

    private fun setUi() {
        setUiOfEditText()
        setUiOfSearchButton()
        setUiOfRepositoryRecyclerView()
    }

    private fun setUiOfEditText() {
        viewBinding.etSearchRepository.doOnTextChanged { text, _, _, _ ->
            viewBinding.btnSearch.isEnabled = text?.isNotBlank() ?: false
        }
    }

    private fun setUiOfSearchButton() {
        viewBinding.btnSearch.setOnClickListener {
            fetchGithubRepository(viewBinding.etSearchRepository.text.toString())
        }
    }

    private fun setUiOfRepositoryRecyclerView() {
        viewBinding.rvRepository.apply {
            setHasFixedSize(true)
            adapter = repositoryAdapter
        }
    }

    private fun fetchGithubRepository(query: String) {
        Thread {
            when (val data = githubRepository.getRepositories(query)) {
                is Response.Success -> {
                    viewBinding.rvRepository.post {
                        repositoryAdapter.submitList(data.data.items)
                    }
                }
                is Response.Error -> {
                    viewBinding.root.post {
                        data.message?.let { message ->
                            showToast(message)
                        }
                    }
                }
                else -> {
                }
            }
        }.start()
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}