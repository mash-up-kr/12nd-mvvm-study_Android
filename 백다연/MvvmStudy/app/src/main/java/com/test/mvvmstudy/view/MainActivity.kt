package com.test.mvvmstudy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import com.test.mvvmstudy.R
import com.test.mvvmstudy.adapter.SearchResultAdapter
import com.test.mvvmstudy.data.ResultDetail
import com.test.mvvmstudy.databinding.ActivityMainBinding
import com.test.mvvmstudy.presenter.SearchContract
import com.test.mvvmstudy.presenter.SearchPresenter

class MainActivity : AppCompatActivity(), SearchContract.View {
    private lateinit var searchPresenter: SearchPresenter
    private lateinit var binding : ActivityMainBinding
    private val adapter : SearchResultAdapter by lazy {
        SearchResultAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchPresenter = SearchPresenter()
        searchPresenter.takeView(this)

        setAdapter()
    }

    private fun setAdapter() {
        binding.searchRecyclerview.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.queryHint = getString(R.string.search)

        searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchPresenter.getSearchResult(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun showResult(result: List<ResultDetail>) {
        adapter.submitList(result)
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }


}