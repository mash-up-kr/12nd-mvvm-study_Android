package com.mashup.mvvm.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mashup.mvvm.R
import com.mashup.mvvm.data.repository.GithubRepository
import com.mashup.mvvm.databinding.ActivityDetailBinding
import com.mashup.mvvm.databinding.ActivityMainBinding
import com.mashup.mvvm.ui.main.MainViewModel

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

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.repository.observe(this) {

        }
    }
}