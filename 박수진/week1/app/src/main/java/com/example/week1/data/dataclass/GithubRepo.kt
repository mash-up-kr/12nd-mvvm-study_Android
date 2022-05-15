package com.example.week1.data.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GithubRepoList(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("items")
    val items: List<GithubRepo>
)

data class GithubRepo(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("owner")
    val owner: GithubRepoOwner
) : Serializable

data class GithubRepoOwner(
    @SerializedName("id")
    val id: Long,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
) : Serializable