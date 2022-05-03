package com.example.week1.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.week1.data.dataclass.GithubRepo
import com.example.week1.data.network.NetworkState
import com.example.week1.databinding.ActivityRepoDetailBinding
import com.example.week1.domain.repository.RepoDetailRepository
import com.example.week1.presentation.BaseActivity
import com.example.week1.presentation.viewmodel.RepoDetailViewModel

class RepoDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityRepoDetailBinding
    private lateinit var viewModel: RepoDetailViewModel
    private lateinit var repoDetailRepository: RepoDetailRepository
    private lateinit var owner: String
    private lateinit var repo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()

        if (intent.hasExtra("owner")) {
            owner = intent.getStringExtra("owner").toString()
            repo = intent.getStringExtra("repo").toString()
        } else {
            Log.d("Error", "intent has no extra")
            finish()
        }

        repoDetailRepository = RepoDetailRepository()

        viewModel = getViewModel(owner, repo)
        viewModel.githubRepoUser.observe(this) {
            bindUI(it)
        }

        viewModel.networkState.observe(this) {
            if (it == NetworkState.LOADING) onProgress()
            else offProgress()
        }
    }

    private fun initActionBar() {
        setSupportActionBar(binding.detailBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun getViewModel(owner: String, repo: String): RepoDetailViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RepoDetailViewModel(repoDetailRepository, owner, repo) as T
            }
        })[RepoDetailViewModel::class.java]
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