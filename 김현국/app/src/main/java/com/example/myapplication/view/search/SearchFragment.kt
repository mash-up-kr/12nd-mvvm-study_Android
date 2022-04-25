package com.example.myapplication.view.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.RepoAdapter
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.presenter.search.SearchContract
import com.example.myapplication.presenter.search.SearchPresenter
/**
 * @author 김현국
 * @created 2022/04/25
 */
class SearchFragment :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    SearchContract.FragmentView {

    private lateinit var searchPresenter: SearchPresenter
    private lateinit var repoAdapter: RepoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchPresenter.takeView(this)
        initUI(this.requireContext())
        arguments?.let {
            if (it.getSerializable("q") != null) {
                val q = it.getString("q")
                if (!q.isNullOrBlank())
                    searchPresenter.getRepoList(q)
            }
        }
    }

    private fun initUI(context: Context) {
        repoAdapter = RepoAdapter()
        binding.rvSearch.layoutManager = LinearLayoutManager(context)
        binding.rvSearch.adapter = repoAdapter

        searchPresenter.setRepoAdapterModel(repoAdapter)
        searchPresenter.setRepoAdapterView(repoAdapter)
    }

    override fun initPresenter() {
        searchPresenter = SearchPresenter()
    }

    override fun showLoading() {
        binding.avSearchIndicator.visibility = View.VISIBLE
        binding.avSearchIndicator.playAnimation()
    }

    override fun hideLoading() {
        binding.avSearchIndicator.cancelAnimation()
        binding.avSearchIndicator.visibility = View.GONE
    }

    override fun showToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateFragment() {
        findNavController().navigate(R.id.detailFragment)
    }
}
