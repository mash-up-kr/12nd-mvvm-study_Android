package com.example.mvvmstudy.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mvvmstudy.R
import com.example.mvvmstudy.view.adapter.MainAdapter
import com.example.mvvmstudy.data.toDetail
import com.example.mvvmstudy.databinding.ActivityMainBinding
import com.example.mvvmstudy.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter = MainAdapter(itemClickedListener = {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("detail", it.toDetail())
        }
        startActivity(intent)
    })
    private lateinit var dialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.listRepo.adapter = adapter
        dialog = LoadingDialog(this)

        subscribeToObservables()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.item_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search.."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchQeury: String?): Boolean {
                if (viewModel.getCurSearch() != searchQeury.toString()) {
                    viewModel.initSearch()
                }
                viewModel.setCurSearch(searchQeury.toString())
                viewModel.searchRepositories(searchQeury.toString())
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun subscribeToObservables() {
        lifecycleScope.launch {
            viewModel.githubList.collect() { githubList ->
                adapter.submitList(githubList.toList())
            }
        }
        viewModel.isLoading.observe(this) { loading ->
            showLoading(loading)
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) dialog.show() else dialog.dismiss()
    }
}