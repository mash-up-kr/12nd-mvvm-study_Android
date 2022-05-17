package com.joocoding.android.app.githubsearch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.joocoding.android.app.githubsearch.databinding.ActivityDetailBinding
import com.joocoding.android.app.githubsearch.model.response.Detail

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        supportActionBar?.hide()
        if (intent.hasExtra(EXTRA_KEY_REPO)) {
            val detail = (intent.getSerializableExtra(EXTRA_KEY_REPO) as? Detail)
            binding.detail = detail
        } else {
            Log.e(TAG, "intent has no extra")
            finish()
        }
    }

    companion object {
        private const val TAG = "DetailActivity"
        const val EXTRA_KEY_REPO = "extra_key_repo"
    }
}