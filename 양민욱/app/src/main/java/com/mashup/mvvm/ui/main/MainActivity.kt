package com.mashup.mvvm.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.mashup.mvvm.base.BaseActivity
import com.mashup.mvvm.databinding.ActivityMainBinding
import com.mashup.mvvm.extensions.getViewModel
import com.mashup.mvvm.extensions.showToast
import com.mashup.mvvm.ui.detail.DetailActivity
import com.mashup.mvvm.ui.main.adapter.RepositoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {

    private val viewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            LayoutInflater.from(this)
        )
    }
    private val repositoryAdapter: RepositoryAdapter by lazy {
        RepositoryAdapter { repository ->
            repository?.run {
                startActivity(DetailActivity.newIntent(this@MainActivity, repository))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        setUi()
        observeViewModel()
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
            viewModel.fetchGithubRepository(viewBinding.etSearchRepository.text.toString())
        }
    }

    private fun setUiOfRepositoryRecyclerView() {
        viewBinding.rvRepository.apply {
            setHasFixedSize(true)
            adapter = repositoryAdapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.repositories.collectLatest { repositories ->
                repositoryAdapter.submitList(repositories)
            }
        }
        lifecycleScope.launch {
            viewModel.toastMessage.collectLatest { toast ->
                showToast(toast)
            }
        }
    }

    override fun injectViewModel(): MainViewModel = getViewModel(MainViewModel::class.java)
}