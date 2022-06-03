package com.example.myapplication.presenter.view.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.presenter.UiState
import com.example.myapplication.presenter.adapter.SearchAdapter
import com.example.myapplication.presenter.model.PresenterRepository
import com.example.myapplication.presenter.view.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * @author 김현국
 * @created 2022/04/25
 */
@AndroidEntryPoint
class SearchFragment :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    (PresenterRepository) -> Unit {
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter(itemListener = this) }
    private val model: SearchViewModel by viewModels()
    private val mainModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        initUI()
    }

    private fun initUI() = with(binding) {
        rvSearch.adapter = searchAdapter
        rvSearch.itemAnimator = null
    }

    private fun initObserve() = with(viewLifecycleOwner.lifecycleScope) {
        launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                model.repoList.collect { state ->
                    when (state) {
                        is UiState.Success -> {
                            hideLoading()
                            searchAdapter.submitList(state.data)
                        }
                        is UiState.Loading -> {
                            showLoading()
                        }
                        is UiState.Error -> {
                            hideLoading()
                            showToast(state.error)
                        }
                        is UiState.Empty -> {
                        }
                    }
                }
            }
        }

        launch {
            mainModel.query.collect { query ->
                if (query.isNotBlank())
                    model.getRepoListWithQuery(q = query)
            }
        }
    }

    private fun hideLoading() = with(binding) {
        avSearchIndicator.cancelAnimation()
        avSearchIndicator.visibility = View.GONE
    }

    private fun showLoading() = with(binding) {
        avSearchIndicator.visibility = View.VISIBLE
        avSearchIndicator.playAnimation()
    }

    override fun invoke(repository: PresenterRepository) {
        val action =
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(presenterRepository = repository)
        findNavController().navigate(action)
    }
}
