package com.mashup.mvvm.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mashup.mvvm.ServiceLocator
import com.mashup.mvvm.base.BaseActivity
import com.mashup.mvvm.data.repository.GithubRepository
import com.mashup.mvvm.databinding.ActivityMainBinding
import com.mashup.mvvm.extensions.showToast
import com.mashup.mvvm.ui.detail.DetailActivity
import com.mashup.mvvm.ui.main.adapter.RepositoryAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

    override fun injectViewModel(): MainViewModel {
        return ViewModelProvider(
            viewModelStore,
            MainViewModelFactory(ServiceLocator.injectGithubRepository())
        ).get(MainViewModel::class.java)
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val githubRepository: GithubRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(githubRepository = githubRepository) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}