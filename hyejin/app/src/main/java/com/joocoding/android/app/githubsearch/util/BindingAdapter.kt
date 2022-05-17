package com.joocoding.android.app.githubsearch.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

//TODO: ErrorRes, PlaceHolderRes 초깃값 지정해주기, ResourceNum이 0으로 나타나던데, 방법 찾아보기
@BindingAdapter("imageUrl")
fun loadImage(
    imageView: ImageView,
    url: String
) {
    Glide.with(imageView.context)
        .load(url)
        .into(imageView)
}

/*
*     Glide.with(root)
                .load(repository.owner.avatarUrl)
                .into(detailImg)*/