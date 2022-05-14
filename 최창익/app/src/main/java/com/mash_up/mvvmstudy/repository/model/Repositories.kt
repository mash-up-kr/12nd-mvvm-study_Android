package com.mash_up.mvvmstudy.repository.model

import com.google.gson.annotations.SerializedName
import com.mash_up.mvvmstudy.R
import java.io.Serializable

data class Repositories(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResult: Boolean,
    @SerializedName("items")
    val repositories: List<Repository>
)

data class Repository(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("language")
    val language: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("description")
    val description: String?,
    @SerializedName("updated_at")
    val updatedAt: String,
): Serializable

data class Owner(
    @SerializedName("login")
    val login: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("avatar_url")
    val avatarUrl: String
): Serializable

fun Repository.toUiModel() =
    listOf(
        DetailUiModel(
            title = owner.login,
            content = stargazersCount.toString(),
            imageUrl = owner.avatarUrl
        ),
        DetailUiModel(
            title = "Description",
            content = description ?: "",
            imageUrl = "android.resource://com.mash_up.mvvmstudy/${R.drawable.ic_baseline_description_24}"
        ),
        DetailUiModel(
            title = "Language",
            content = language ?: "",
            imageUrl = "android.resource://com.mash_up.mvvmstudy/${R.drawable.ic_baseline_announcement_24}"
        ),
        DetailUiModel(
            title = "Last Updated",
            content = updatedAt,
            imageUrl = "android.resource://com.mash_up.mvvmstudy/${R.drawable.ic_baseline_alarm_on_24}"
        ),
    )
