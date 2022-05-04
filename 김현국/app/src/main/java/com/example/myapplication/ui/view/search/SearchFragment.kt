package com.example.myapplication.ui.view.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
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
    private lateinit var model: SearchViewModel
    private val mainModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val searchRepoRepository = SearchRepoRepository()
        model = SearchViewModel(searchRepository = searchRepoRepository)
        model.getRepoListWithQuery("kotlin")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserve()
        initUI()
    }

    private fun initUI() = with(binding) {
        searchAdapter = SearchAdapter(itemListener = this@SearchFragment)
        rvSearch.adapter = searchAdapter
        rvSearch.itemAnimator = null
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

    private fun hideLoading() {
        binding.avSearchIndicator.cancelAnimation()
        binding.avSearchIndicator.visibility = View.GONE
    }

    private fun showLoading() {
        binding.avSearchIndicator.visibility = View.VISIBLE
        binding.avSearchIndicator.playAnimation()
    }

    override fun invoke(repository: PresenterRepository) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(
            name = repository.name,
            login = repository.owner.login,
            image = repository.owner.image,
            stars = repository.stars ?: 0,
            description = repository.description,
            language = repository.language,
            lastUpdate = repository.lastUpdated
        )
        findNavController().navigate(action)
    }
}
