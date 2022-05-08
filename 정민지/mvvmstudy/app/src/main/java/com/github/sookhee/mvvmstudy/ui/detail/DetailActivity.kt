package com.github.sookhee.mvvmstudy.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.sookhee.mvvmstudy.databinding.ActivityDetailBinding
import com.github.sookhee.mvvmstudy.model.GithubRepositoryModel

/**
 *  DetailActivity.kt
 *
 *  Created by Minji Jeong on 2022/04/25
 *  Copyright © 2022 MashUp All rights reserved.
 */

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var repositoryData: GithubRepositoryModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        repositoryData = intent.getParcelableExtra(EXTRA_REPOSITORY_KEY) ?: return

        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setToolbar()
        setViewData()
    }

    private fun setToolbar() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setViewData() {
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
        private const val EXTRA_REPOSITORY_KEY = "EXTRA_REPOSITORY_KEY"

        fun newIntent(context: Context, repository: GithubRepositoryModel): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_REPOSITORY_KEY, repository)
            }
        }
    }
}
