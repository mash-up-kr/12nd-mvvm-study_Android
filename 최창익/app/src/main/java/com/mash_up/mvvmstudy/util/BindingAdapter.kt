package com.mash_up.mvvmstudy.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mash_up.mvvmstudy.R

//TODO: ErrorRes, PlaceHolderRes 초깃값 지정해주기, ResourceNum이 0으로 나타나던데, 방법 찾아보기
@BindingAdapter("imageUrl")
fun loadImage(
    imageView: ImageView,
    url: String
) {
    Glide.with(imageView.context)
        .load(url)
        .placeholder(ContextCompat.getDrawable(imageView.context, R.drawable.mashup))
        .error(ContextCompat.getDrawable(imageView.context, R.drawable.mashup))
        .apply(RequestOptions().fitCenter())
        .into(imageView)
}