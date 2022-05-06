package com.mash_up.mvvmstudy.view.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mash_up.mvvmstudy.R
import com.mash_up.mvvmstudy.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var adapter: DetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        initToolbar()
        initAdapter()
        observeLiveData()
    }

    private fun initAdapter() {
        this.adapter = DetailAdapter()
        binding.rvDetail.adapter = this.adapter
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun observeLiveData() {
        viewModel.uiState.observe(this) { uiModel ->
            binding.uiState = uiModel
            binding.toolbarDetail.title = uiModel.repositoryName
            this.adapter.submitList(uiModel.feeds)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return false
            }
        }

        return super.onOptionsItemSelected(item)
    }
}