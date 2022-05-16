package com.example.week1.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.week1.data.model.GithubRepo
import com.example.week1.databinding.ActivityRepoDetailBinding
import com.example.week1.presentation.base.BaseActivity

class RepoDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityRepoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()

        if (intent.hasExtra("repo")) {
            bindUI(intent.getSerializableExtra("repo") as GithubRepo)
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

    private fun bindUI(repo: GithubRepo) {
        with(binding) {
            Glide.with(root)
                .load(repo.owner.avatarUrl)
                .into(detailImg)
            detailName.text = repo.name
            detailStarCnt.text = repo.stargazersCount.toString()
            detailDescription.text = repo.description
            detailLang.text = repo.language
            detailUpdated.text = repo.updatedAt
        }
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