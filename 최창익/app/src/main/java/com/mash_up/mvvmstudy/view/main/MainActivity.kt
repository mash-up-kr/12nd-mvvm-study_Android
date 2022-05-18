package com.mash_up.mvvmstudy.view.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.mash_up.mvvmstudy.R
import com.mash_up.mvvmstudy.databinding.ActivityMainBinding
import com.mash_up.mvvmstudy.view.detail.DetailActivity
import com.mash_up.mvvmstudy.view.detail.DetailViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initRecyclerView()
        initSearchView()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.pbMain.isVisible = isLoading
        }

        viewModel.repositories.observe(this) { repositories ->
            if (!repositories.isNullOrEmpty()) {
                adapter.submitList(repositories)
            }
        }

        viewModel.networkErrorState.observe(this) { errorMessage ->
            Toast.makeText(
                this@MainActivity,
                "다음과 같은 이유로 문제가 발생했습니다. $errorMessage",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initRecyclerView() {
        this.adapter = MainAdapter { repository ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailViewModel.REPOSITORY, repository)
            }

            startActivity(intent)
        }
        binding.rvMain.adapter = this.adapter
    }

    private fun initSearchView() {
        binding.svMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) return false

                viewModel.getRepositoriesCoroutine(query)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}
