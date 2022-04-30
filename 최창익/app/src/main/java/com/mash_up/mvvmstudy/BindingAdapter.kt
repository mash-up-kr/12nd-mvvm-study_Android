package com.mash_up.mvvmstudy

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl", "errorImageRes", "placeHolderImageRes", requireAll = false)
fun loadImage(
    imageView: ImageView,
    url: String,
    @DrawableRes errorImageRes: Int = R.drawable.mashup,
    @DrawableRes placeHolderImageRes: Int = R.drawable.mashup
) {
    Glide.with(imageView.context)
        .load(url)
        .placeholder(ContextCompat.getDrawable(imageView.context, placeHolderImageRes))
        .error(ContextCompat.getDrawable(imageView.context, errorImageRes))
        .apply(RequestOptions().fitCenter())
        .into(imageView)
}