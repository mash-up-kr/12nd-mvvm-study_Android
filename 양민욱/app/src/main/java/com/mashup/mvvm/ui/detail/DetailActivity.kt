package com.mashup.mvvm.ui.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mashup.mvvm.R
import com.mashup.mvvm.constants.KEY_REPOSITORY
import com.mashup.mvvm.data.model.Repository
import com.mashup.mvvm.data.repository.GithubRepository
import com.mashup.mvvm.databinding.ActivityDetailBinding
import com.mashup.mvvm.databinding.ActivityMainBinding
import com.mashup.mvvm.extensions.loadImage
import com.mashup.mvvm.ui.main.MainViewModel
import com.mashup.mvvm.utils.toDateString

class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(
            this,
            SavedStateViewModelFactory(application, this, intent.extras)
        ).get(DetailViewModel::class.java)
    }

    private val viewBinding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        setUi()
        observeViewModel()
    }

    private fun setUi() {
        setUiOfBackButton()
    }

    private fun setUiOfBackButton() {
        viewBinding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.repository.observe(this) { repository ->
            setUiOfTitle(repository)
            setUiOfOwner(repository)
            setUiOfLanguage(repository)
            setUiOfLastUpdated(repository)
        }
    }

    private fun setUiOfTitle(repository: Repository) = with(viewBinding) {
        tvRepositoryName.text = repository.name
    }

    private fun setUiOfOwner(repository: Repository) = with(viewBinding) {
        imgOwner.loadImage(repository.owner.avatarUrl)
        tvOwnerName.text = repository.owner.login
    }

    private fun setUiOfLanguage(repository: Repository) = with(viewBinding) {
        tvLanguage.text = repository.language ?: getString(R.string.unknown_repository_language)
    }

    private fun setUiOfLastUpdated(repository: Repository) = with(viewBinding) {
        tvLastUpdated.text = repository.updatedAt.toDateString()
    }

    companion object {
        fun newIntent(context: Context, repository: Repository): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(KEY_REPOSITORY, repository)
            }
        }
    }
}