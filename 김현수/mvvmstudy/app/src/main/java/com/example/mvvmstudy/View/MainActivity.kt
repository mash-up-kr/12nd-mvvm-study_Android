package com.example.mvvmstudy.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmstudy.Adapter.MainAdapter
import com.example.mvvmstudy.R
import com.example.mvvmstudy.ViewModel.MainViewModel
import com.example.mvvmstudy.data.toDetail
import com.example.mvvmstudy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()
    private val adapter = MainAdapter(itemClickedListener = {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("detail", it.toDetail())
        }
        startActivity(intent)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initScrollListener()
        observing()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.item_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search.."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p: String?): Boolean {
                if (model.getCurSearch() != p.toString()) {
                    model.initSearch()
                }
                model.setCurSearch(p.toString())
                model.searchRepositories(p.toString(), model.getCurPage().toString())
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

    private fun initScrollListener() {
        binding.listRepo.adapter = adapter
        binding.listRepo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1
                if (!binding.listRepo.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    model.searchRepositories(model.getCurSearch(), model.getCurPage().toString())
                }
            }
        })
    }

    private fun observing() {
        model.getRepositories().observe(this) {
            if (!it.isNullOrEmpty()) {
                adapter.submitList(it)
            }
        }
//        model.isLoading.observe(this) { loading ->
//            binding.progressBar.isGone = loading
//        }
    }


}