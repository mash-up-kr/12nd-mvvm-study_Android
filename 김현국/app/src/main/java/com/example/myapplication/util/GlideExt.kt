package com.example.myapplication.util

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * @author 김현국
 * @created 2022/05/10
 */
fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).into(this)
}
