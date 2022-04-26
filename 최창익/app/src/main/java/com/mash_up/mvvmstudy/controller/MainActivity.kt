package com.mash_up.mvvmstudy.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.mash_up.mvvmstudy.view.MainAdapter
import com.mash_up.mvvmstudy.databinding.ActivityMainBinding
import com.mash_up.mvvmstudy.model.MainModel
import com.mash_up.mvvmstudy.model.MainUiModel
import com.orhanobut.logger.Logger

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = MainAdapter()
    private val model = MainModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initSearchView()
    }

    private fun initRecyclerView() {
        binding.rvMain.adapter = this.adapter
    }

    private fun initSearchView() {
        binding.svMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) return false

                model.fetchGithubData(query) { response ->
                    if (response != null) {
                        adapter.submitList(response.repositories)
                    }
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}
