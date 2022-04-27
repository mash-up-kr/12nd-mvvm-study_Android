package com.mash_up.mvvmstudy

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("app:imageUrl")
fun loadImage(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
        .load(url)
        .placeholder(ContextCompat.getDrawable(imageView.context, R.drawable.mashup))
        .error(ContextCompat.getDrawable(imageView.context, R.drawable.mashup))
        .apply(RequestOptions().fitCenter())
        .into(imageView)
}