package com.github.sookhee.mvvmstudy.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.sookhee.mvvmstudy.databinding.ActivityDetailBinding

/**
 *  DetailActivity.kt
 *
 *  Created by Minji Jeong on 2022/04/25
 *  Copyright © 2022 MashUp All rights reserved.
 */

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
    }
}
