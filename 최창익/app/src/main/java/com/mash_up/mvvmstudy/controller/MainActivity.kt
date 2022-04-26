package com.mash_up.mvvmstudy.controller

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.mash_up.mvvmstudy.databinding.ActivityMainBinding
import com.mash_up.mvvmstudy.model.MainModel
import com.mash_up.mvvmstudy.view.RepositoryAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = RepositoryAdapter()
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

                binding.pbMain.visibility = View.VISIBLE

                model.fetchGithubData(query,
                    onSuccess = { response ->
                        if (response != null) {
                            adapter.submitList(response.repositories)
                        }

                        binding.pbMain.visibility = View.INVISIBLE
                    },
                    onFailure = { errorMessage ->
                        binding.pbMain.visibility = View.INVISIBLE
                        Toast.makeText(
                            this@MainActivity,
                            "다음과 같은 이유로 문제가 발생했습니다. $errorMessage",
                            Toast.LENGTH_SHORT
                        ).show()
                    })

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}
