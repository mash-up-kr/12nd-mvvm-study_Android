package com.example.week1.data.dataclass

import com.google.gson.annotations.SerializedName

data class GithubUser(
    @SerializedName("id")
    val id: Long,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
