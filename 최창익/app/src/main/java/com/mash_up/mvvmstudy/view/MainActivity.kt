package com.mash_up.mvvmstudy.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.mash_up.mvvmstudy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = RepositoryAdapter()

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initSearchView()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.pbMain.visibility = View.VISIBLE
            } else {
                binding.pbMain.visibility = View.INVISIBLE
            }
        }

        viewModel.repositories.observe(this) { repositories ->
            if (!repositories.isNullOrEmpty()) {
                adapter.submitList(repositories)
            }
        }

        viewModel.networkErrorState.observe(this) {
            Toast.makeText(
                this@MainActivity,
                "다음과 같은 이유로 문제가 발생했습니다. ${viewModel.networkErrorInfo}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initRecyclerView() {
        binding.rvMain.adapter = this.adapter
    }

    private fun initSearchView() {
        binding.svMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) return false

                viewModel.getRepositories(query)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}
