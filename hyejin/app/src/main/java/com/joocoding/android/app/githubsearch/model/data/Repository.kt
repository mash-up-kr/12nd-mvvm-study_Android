package com.joocoding.android.app.githubsearch.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Repository(
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("private")
    val private: Boolean,
    @SerializedName("description")
    val description: String?,
    @SerializedName("url")
    val url: String,
    @SerializedName("forks")
    val forks: Long,
    @SerializedName("stargazers_count")
    val stargazersCount: Long,
    @SerializedName("language")
    val language: String,
    @SerializedName("updated_at")
    val updatedAt: String

) : Serializable

fun Repository.toDetail() =
    Detail(
        detailName = name,
        detailImage = owner.avatarUrl,
        detailStarCnt = stargazersCount.toString(),
        detailDescription = description,
        detailLang = language,
        detailUpdated = updatedAt
    )
