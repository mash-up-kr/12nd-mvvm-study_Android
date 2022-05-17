package com.test.mvvmstudy.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object BindingAdapter {
    @JvmStatic
    @androidx.databinding.BindingAdapter("imageUrl", "placeholder")
    fun loadImage(imageView: ImageView, url: String?, placeholder: Drawable) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(placeholder)
            .error(placeholder)
            .apply(RequestOptions().fitCenter())
            .into(imageView)
    }
}