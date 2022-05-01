package com.test.mvvmstudy.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.test.mvvmstudy.R
import com.test.mvvmstudy.adapter.SearchResultAdapter
import com.test.mvvmstudy.databinding.FragmentSearchBinding
import com.test.mvvmstudy.model.ResultDetail
import com.test.mvvmstudy.presenter.SearchContract
import com.test.mvvmstudy.presenter.SearchPresenter

class SearchFragment : Fragment(), SearchContract.View {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchPresenter: SearchPresenter
    private val adapter: SearchResultAdapter by lazy {
        SearchResultAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        searchPresenter = SearchPresenter()
        searchPresenter.takeView(this)
        setRecyclerviewAdapter()
        adapterClickListener()
    }

    private fun setRecyclerviewAdapter() {
        binding.searchRecyclerview.adapter = adapter
    }

    private fun adapterClickListener() {
        adapter.itemClick = object : SearchResultAdapter.ItemClick{
            override fun onClick(view: View, searchData: ResultDetail) {
                val bundle = Bundle()
                bundle.putSerializable("searchData", searchData)
                Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_searchDetailFragment, bundle)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search,menu)
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.queryHint = getString(R.string.search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    progressbarVisible(true)
                    searchPresenter.getSearchResult(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu,inflater)
    }

    override fun progressbarVisible(visible: Boolean) {
        if (visible) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.INVISIBLE
    }

    override fun showResult(result: List<ResultDetail>) {
        adapter.submitList(result) {
            progressbarVisible(false)
        }
    }
}