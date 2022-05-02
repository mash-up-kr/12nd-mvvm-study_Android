package com.example.week1.data.dataclass

import com.google.gson.annotations.SerializedName

data class GithubRepoList(
    @SerializedName("total_count")
    val total_count: Int,
    @SerializedName("items")
    val items: List<GithubRepo>
)

data class GithubRepo(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("owner")
    val owner: GithubRepoOwner
)

data class GithubRepoOwner(
    @SerializedName("id")
    val id: Long,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatar_url: String
)