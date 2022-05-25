package com.joocoding.android.app.githubsearch.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.joocoding.android.app.githubsearch.R
import com.joocoding.android.app.githubsearch.constant.KEY_REPOSITORY
import com.joocoding.android.app.githubsearch.databinding.ActivityDetailBinding
import com.joocoding.android.app.githubsearch.model.data.Detail
import com.joocoding.android.app.githubsearch.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.vm = viewModel
        supportActionBar?.hide()

    }

    companion object {
        fun newIntent(context: Context, detail: Detail): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(KEY_REPOSITORY, detail)
            }
        }

        private const val TAG = "DetailActivity"

    }
}