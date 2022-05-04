package com.mash_up.mvvmstudy.view.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mash_up.mvvmstudy.databinding.ActivityDetailBinding
import com.mash_up.mvvmstudy.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        observeLiveData()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun observeLiveData() {
        viewModel.uiState.observe(this) { uiModel ->
            binding.uiState = uiModel
            binding.toolbarDetail.title = uiModel.repositoryName
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return false
            }
        }

        return super.onOptionsItemSelected(item)
    }
}