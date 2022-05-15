package com.github.sookhee.mvvmstudy.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.sookhee.mvvmstudy.databinding.ActivityDetailBinding
import com.github.sookhee.mvvmstudy.model.GithubRepositoryModel
import com.github.sookhee.mvvmstudy.ui.EXTRA_REPOSITORY_KEY

/**
 *  DetailActivity.kt
 *
 *  Created by Minji Jeong on 2022/04/25
 *  Copyright © 2022 MashUp All rights reserved.
 */

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initView()
        observeData()
    }

    private fun initView() {
        initToolbar()
    }

    private fun observeData() {
        viewModel.repositoryData.observe(this) {
            setViewData(it)
        }
    }

    private fun initToolbar() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setViewData(repositoryData: GithubRepositoryModel) {
        with(binding) {
            repositoryName.text = repositoryData.repoName
            ownerNameText.text = repositoryData.ownerName
            lastUpdateText.text = repositoryData.repoLastUpdate
            languageText.text = repositoryData.language

            Glide.with(root)
                .load(repositoryData.profileImage)
                .into(ownerProfileImage)
        }
    }

    companion object {
        fun newIntent(context: Context, repository: GithubRepositoryModel): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_REPOSITORY_KEY, repository)
            }
        }
    }
}
