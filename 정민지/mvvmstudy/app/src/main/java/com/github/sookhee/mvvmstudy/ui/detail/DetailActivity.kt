package com.github.sookhee.mvvmstudy.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.sookhee.mvvmstudy.databinding.ActivityDetailBinding
import com.github.sookhee.mvvmstudy.model.GithubRepositoryModel
import com.github.sookhee.mvvmstudy.ui.main.MainActivity

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
        repositoryData = intent.getParcelableExtra(MainActivity.EXTRA_REPOSITORY_KEY) ?: return

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
        binding.repositoryName.text = repositoryData.repoName

        binding.ownerNameText.text = repositoryData.ownerName
        binding.lastUpdateText.text = repositoryData.repoLastUpdate
        binding.languageText.text = repositoryData.language

        Glide.with(binding.root)
            .load(repositoryData.profileImage)
            .into(binding.ownerProfileImage)
    }
}
