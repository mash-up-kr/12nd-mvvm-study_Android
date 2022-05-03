package com.joocoding.android.app.githubsearch.model.response

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)
