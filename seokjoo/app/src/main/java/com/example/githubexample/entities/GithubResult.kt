package com.example.githubexample.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class GithubResult(
    @SerializedName("items")
    val items: List<Item> = emptyList(),
) {
    @Parcelize
    data class Item(
        @SerializedName("archive_url")
        val archiveUrl: String = "",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("full_name")
        val fullName: String = "",
        @SerializedName("git_url")
        val gitUrl: String = "",
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("stargazers_count")
        val stargazersCount: Int = 0,
        @SerializedName("description")
        val description: String = "",
        @SerializedName("updated_at")
        val updatedAt: String = "",
        @SerializedName("language")
        val language: String? = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("owner")
        val owner: Owner = Owner(),
        @SerializedName("url")
        val url: String = "",
    ) : Parcelable {
        @Parcelize
        data class Owner(
            @SerializedName("avatar_url")
            val avatarUrl: String = "",
            @SerializedName("id")
            val id: Int = 0,
            @SerializedName("url")
            val url: String = ""
        ) : Parcelable
    }
}
