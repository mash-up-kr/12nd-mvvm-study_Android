package com.joocoding.android.app.githubsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.joocoding.android.app.githubsearch.databinding.ActivityDetailBinding
import com.joocoding.android.app.githubsearch.databinding.ActivityMainBinding
import com.joocoding.android.app.githubsearch.model.response.Repository

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        supportActionBar?.hide()
        if (intent.hasExtra(EXTRA_KEY_REPO)) {
            drawDetail(intent.getSerializableExtra(EXTRA_KEY_REPO) as Repository)
        } else {
            Log.e(TAG, "intent has no extra")
            finish()
        }
    }

    private fun drawDetail(repository: Repository) {
        with(binding) {
            Glide.with(root)
                .load(repository.owner.avatarUrl)
                .into(detailImg)
            detailName.text = repository.name
            detailStarCnt.text = repository.stargazersCount.toString()
            detailDescription.text = repository.description
            detailLang.text = repository.language
            detailUpdated.text = repository.updatedAt
        }
    }

    companion object {
        private const val TAG = "DetailActivity"
        private const val EXTRA_KEY_REPO = "extra_key_repo"
    }
}