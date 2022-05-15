package com.example.myapplication.ui.view.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.data.repository.SearchRepoRepository
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.ui.Results
import com.example.myapplication.ui.adapter.SearchAdapter
import com.example.myapplication.ui.model.PresenterRepository
import com.example.myapplication.ui.view.main.MainViewModel

/**
 * @author 김현국
 * @created 2022/04/25
 */
class SearchFragment :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    (PresenterRepository) -> Unit {

    private lateinit var searchAdapter: SearchAdapter
    private val model: SearchViewModel by viewModels {
        SearchViewModelFactory(
            this,
            SearchRepoRepository()
        )
    }
    private val mainModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserve()
        initUI()
    }

    private fun initUI() = with(binding) {
        searchAdapter = SearchAdapter(itemListener = this@SearchFragment)
        rvSearch.adapter = searchAdapter
        rvSearch.itemAnimator = null

        if (!model.savedStateHandle.contains(model.QUERY))
            model.getRepoListWithQuery("kotlin")
    }

    private fun initObserve() {
        model.repoList.observe(viewLifecycleOwner, repoListObserver)
        mainModel.query.observe(viewLifecycleOwner, queryObserver)
    }

    private val repoListObserver = Observer<Results<List<PresenterRepository>>> { result ->
        when (result) {
            is Results.Success -> {
                hideLoading()
                searchAdapter.submitList(result.value)
            }
            is Results.Failure -> {
                hideLoading()
                showToast(result.message ?: "예상치 못한 오류 발생")
            }
            is Results.Loading -> {
                showLoading()
            }
        }
    }

    private val queryObserver = Observer<String> {
        model.getRepoListWithQuery(it)
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
