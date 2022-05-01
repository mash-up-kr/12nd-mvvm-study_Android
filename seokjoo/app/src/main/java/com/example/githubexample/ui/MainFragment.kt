package com.example.githubexample.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.githubexample.R
import com.example.githubexample.databinding.FragmentMainBinding
import com.example.githubexample.entities.GithubResult
import com.example.githubexample.model.remote.RemoteDataSourceImpl
import com.example.githubexample.ui.recyclerview.GithubAdapter
import com.example.githubexample.viewmodel.GithubViewModel
import com.example.githubexample.viewmodel.GithubViewModelFactory
import kotlinx.coroutines.flow.collectLatest

class MainFragment : Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val githubAdapter by lazy { GithubAdapter(::onItemClick) }
    private lateinit var callback: OnBackPressedCallback
    private val githubViewModel: GithubViewModel by lazy {
        ViewModelProvider(this, GithubViewModelFactory(RemoteDataSourceImpl(), this)).get(GithubViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DataBindingUtil.bind(view) ?: throw IllegalStateException("fail to bind")

        setSearchViewBackButtonListener()
        initView()
        submitRecyclerViewList()
    }

    private fun submitRecyclerViewList() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                githubViewModel.uiState.collectLatest {
                    githubAdapter.submitList(it.items)
                }
            }
        }
    }

    private fun onItemClick(item: GithubResult.Item) {
        parentFragmentManager.commit {
            replace<DetailFragment>(R.id.fragment_container_view_tag, args = bundleOf("githubData" to item))
            addToBackStack("MainFragment")
            setReorderingAllowed(true)
        }
    }

    private fun initView() {
        binding.apply {
            recylcerview.adapter = githubAdapter
            lifecycleOwner = viewLifecycleOwner
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                githubViewModel.getRepositoryList(query)
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
    }

    private fun setSearchViewBackButtonListener() {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.searchView.isIconified) {
                    requireActivity().onBackPressed()
                } else {
                    binding.searchView.isIconified = true
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onDestroyView() {
        binding.unbind()
        _binding = null
        super.onDestroyView()
    }
}
