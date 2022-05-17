package com.example.myapplication.presenter.view.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.servicelocator.RepositoryServiceLocator
import com.example.myapplication.servicelocator.UseCaseServiceLocator
import com.example.myapplication.presenter.UiState
import com.example.myapplication.presenter.adapter.SearchAdapter
import com.example.myapplication.presenter.model.PresenterRepository
import com.example.myapplication.presenter.view.main.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author 김현국
 * @created 2022/04/25
 */
class SearchFragment :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    (PresenterRepository) -> Unit {
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter(itemListener = this) }
    private val searchRepoRepository by lazy { RepositoryServiceLocator.provideSearchRepoRepository() }
    private val model: SearchViewModel by viewModels {
        SearchViewModelFactory(
            this,
            UseCaseServiceLocator.provideGetRepositoryListWithQueryUseCase(searchRepoRepository = searchRepoRepository)
        )
    }
    private val mainModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        initUI()
    }

    private fun initUI() = with(binding) {
        rvSearch.adapter = searchAdapter
        rvSearch.itemAnimator = null

        with(model) {
            if (!savedStateHandle.contains(QUERY))
                getRepoListWithQuery("kotlin")
        }
    }

    private fun initObserve() = with(viewLifecycleOwner.lifecycleScope) {
        launch {
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
