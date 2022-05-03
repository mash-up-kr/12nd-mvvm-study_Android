package com.example.week1.presentation.ui

import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.week1.data.dataclass.GithubRepo
import com.example.week1.databinding.ActivityRepoDetailBinding
import com.example.week1.presentation.BaseActivity

class RepoDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityRepoDetailBinding
    private lateinit var repo: GithubRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()

        if (intent.hasExtra("repo")) {
            repo = intent.getSerializableExtra("repo") as GithubRepo
            bindUI(repo)
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

    private fun bindUI(it: GithubRepo) {
        with(binding) {
            Glide.with(root)
                .load(it.owner.avatarUrl)
                .into(detailImg)
            detailName.text = it.name
            detailStarCnt.text = it.stargazersCount.toString()
            detailDescription.text = it.description
            detailLang.text = it.language
            detailUpdated.text = it.updatedAt
        }
    }
}