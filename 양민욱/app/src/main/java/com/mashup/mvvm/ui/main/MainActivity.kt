package com.mashup.mvvm.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.mashup.mvvm.databinding.ActivityMainBinding
import com.mashup.mvvm.ui.main.adapter.RepositoryAdapter

class MainActivity : AppCompatActivity() {
    private val viewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            LayoutInflater.from(this)
        )
    }
    private val repositoryAdapter: RepositoryAdapter by lazy { RepositoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        setUiOfRepositoryRecyclerView()
    }

    private fun setUiOfRepositoryRecyclerView() {
        viewBinding.rvRepository.adapter = repositoryAdapter
    }
}