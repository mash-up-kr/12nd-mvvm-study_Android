package com.example.githubexample.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("bindImage")
    fun ImageView.bindImage(url: String?) {
        url?.let {
            Glide.with(this)
                .load(it)
                .into(this)
        }
    }

    @JvmStatic
    @BindingAdapter("bindStars")
    fun TextView.bindStars(stars: Int) {
        this.text = "$stars stars"
    }
}
