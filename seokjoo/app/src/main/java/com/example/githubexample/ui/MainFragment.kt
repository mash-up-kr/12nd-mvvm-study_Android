package com.example.githubexample.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState

import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.example.githubexample.R
import com.example.githubexample.databinding.FragmentMainBinding
import com.example.githubexample.entities.GithubResult
import com.example.githubexample.model.remote.RemoteDataSourceImpl
import com.example.githubexample.ui.recyclerview.GithubAdapter
import com.example.githubexample.viewmodel.GithubViewModel
import com.example.githubexample.viewmodel.GithubViewModelFactory
import kotlinx.coroutines.flow.*

class MainFragment : Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val githubAdapter by lazy { GithubAdapter(::onItemClick) }
    private val githubViewModel: GithubViewModel by lazy {
        ViewModelProvider(requireActivity(), GithubViewModelFactory(RemoteDataSourceImpl(), this)).get(GithubViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DataBindingUtil.bind(view) ?: throw IllegalStateException("fail to bind")

        initView()
    }

    private fun initView() {
        binding.recylcerview.adapter = githubAdapter
        binding.bindState(
            uiState = githubViewModel.uiState,
            pagingData = githubViewModel.pagingData,
            uiActions = githubViewModel.accept
        )
    }

    private fun FragmentMainBinding.bindState(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<GithubResult.Item>>,
        uiActions: (UiAction) -> Unit
    ) {
        bindSearch(
            uiState = uiState,
            onQueryChanged = uiActions
        )

        bindList(
            recyclerviewAdapter = githubAdapter,
            uiState = uiState,
            pagingData = pagingData,
            onScrollChanged = uiActions
        )
    }

    private fun FragmentMainBinding.bindList(
        recyclerviewAdapter: GithubAdapter,
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<GithubResult.Item>>,
        onScrollChanged: (UiAction) -> Unit
    ) {

        recylcerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(UiAction.Scroll(currentQuery = uiState.value.query))
            }
        })

        val notLoading = recyclerviewAdapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map { it.source.refresh is LoadState.NotLoading }

        val hasNotScrolledForCurrentSearch = uiState
            .map { it.hasNotScrolledForCurrentSearch }
            .distinctUntilChanged()

        val shouldScrollTop = combine(
            notLoading,
            hasNotScrolledForCurrentSearch,
            Boolean::and
        ).distinctUntilChanged()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                pagingData.collectLatest(recyclerviewAdapter::submitData)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                shouldScrollTop.collect { shouldScrollTop ->
                    if (shouldScrollTop) recylcerview.scrollToPosition(0)
                }
            }
        }
    }

    private fun FragmentMainBinding.bindSearch(uiState: StateFlow<UiState>, onQueryChanged: (UiAction) -> Unit) {
        searchView.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput(onQueryChanged)
                hideKeyboard()
                true
            } else {
                false
            }
        }

        searchView.setOnKeyListener { _, keycode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keycode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput(onQueryChanged)
                hideKeyboard()
                true
            } else {
                false
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                uiState
                    .map { it.query }
                    .distinctUntilChanged()
                    .collect(searchView::setText)
            }
        }
    }

    private fun FragmentMainBinding.updateRepoListFromInput(onQueryChanged: (UiAction) -> Unit) {
        searchView.text.trim().let {
            if (it.isNotEmpty()) {
                recylcerview.scrollToPosition(0)
                onQueryChanged(UiAction.Search(query = it.toString()))
            }
        }
    }


    private fun onItemClick(item: GithubResult.Item) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(item)
        findNavController().navigate(action)
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager = requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
    }

    override fun onDestroyView() {
        binding.unbind()
        _binding = null
        super.onDestroyView()
    }
}
