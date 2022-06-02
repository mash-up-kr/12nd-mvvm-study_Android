package com.example.week1.presentation.view

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.week1.R

object BindingAdapter {

    @JvmStatic
    @BindingAdapter(
        value = ["imageUrl", "imagePlaceHolder"],
        requireAll = false      // 모든 속성이 없어도 됨
    )
    fun bindImage(imageView: ImageView, url: String?, placeholder: Drawable?) {
        val ph = placeholder ?: ContextCompat.getDrawable(imageView.context, R.drawable.ic_default)

        Glide.with(imageView.context)
            .load(url)
            .placeholder(ph)
            .error(ph)
            .into(imageView)
    }
}