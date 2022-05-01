package com.example.githubexample.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("bindImage")
    fun ImageView.bindImage(url: String) {
        Glide.with(this)
            .load(url)
            .into(this)
    }
}
