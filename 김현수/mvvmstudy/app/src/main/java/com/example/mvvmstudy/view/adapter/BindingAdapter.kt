package com.example.mvvmstudy.view.adapter

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mvvmstudy.R

@BindingAdapter("imageUrl")
fun loadImage(
    imageView: ImageView,
    url: String
) {
    Glide.with(imageView.context)
        .load(url)
        .placeholder(ContextCompat.getDrawable(imageView.context, R.drawable.ic_baseline_storage))
        .error(ContextCompat.getDrawable(imageView.context, R.drawable.ic_baseline_storage))
        .apply(RequestOptions.fitCenterTransform())
        .into(imageView)
}