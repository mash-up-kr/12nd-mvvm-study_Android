package com.example.mvvmstudy.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Repository(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("description")
    val description: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("stargazers_count")
    val stargazerCount: String?,
    @SerializedName("updated_at")
    val updatedAt: String
) : Serializable

fun Repository.toDetail() =
    Detail(
        userName = owner.login,
        avatarUrl = owner.avatarUrl,
        repositoryName = name,
        stargazerCount = stargazerCount,
        language = language,
        lastUpdated = updatedAt,
        description = description
    )
