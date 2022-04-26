package com.mashup.mvvm.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(imageUrl: String) {
    if (imageUrl.isBlank()) return
    Glide.with(this)
        .load(imageUrl)
        .into(this)
}