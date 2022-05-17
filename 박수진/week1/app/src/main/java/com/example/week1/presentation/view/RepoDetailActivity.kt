package com.example.week1.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.week1.R
import com.example.week1.data.model.GithubRepo
import com.example.week1.databinding.ActivityRepoDetailBinding
import com.example.week1.presentation.base.BaseActivity

class RepoDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityRepoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repo_detail)

        initActionBar()

        if (intent.hasExtra("repo")) {
            binding.repo = intent.getSerializableExtra("repo") as GithubRepo
        } else {
            Log.e("Error", "intent has no extra")
            finish()
        }
    }

    private fun initActionBar() {
        setSupportActionBar(binding.detailBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}