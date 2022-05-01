package com.example.githubexample.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.githubexample.R
import com.example.githubexample.databinding.FragmentMainBinding
import com.example.githubexample.entities.GithubResult
import com.example.githubexample.model.remote.RemoteDataSourceImpl
import com.example.githubexample.ui.recyclerview.GithubAdapter

class MainFragment : Fragment(R.layout.fragment_main), MainContract.View {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val githubAdapter by lazy { GithubAdapter(::onItemClick) }
    private val mainPresenter = MainPresenter(this, RemoteDataSourceImpl())
    private lateinit var callback: OnBackPressedCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSearchViewBackButtonListener()
        initView()
    }

    private fun onItemClick(item: GithubResult.Item) {
        Log.d("TAG", "onItemClick: $item")
    }

    override fun initView() {
        activeProgressbar(false)

        binding.apply {
            recylcerview.adapter = githubAdapter
            lifecycleOwner = viewLifecycleOwner
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainPresenter.getRepositoryList(query)
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    override fun hideKeyboard() {
        val imm: InputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
    }

    override fun submitList(list: List<GithubResult.Item>) {
        githubAdapter.submitList(list)
    }

    override fun showError(t: Throwable) {
        Log.d("TAG", "showError: $t")
    }

    override fun activeProgressbar(active: Boolean) =
        if (active) binding.progressbar.visibility = View.VISIBLE else binding.progressbar.visibility = View.GONE

    override fun setSearchViewBackButtonListener() {
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
        super.onDestroyView()
        _binding = null
    }
}
