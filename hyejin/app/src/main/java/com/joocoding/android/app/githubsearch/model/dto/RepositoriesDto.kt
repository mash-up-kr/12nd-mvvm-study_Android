package com.joocoding.android.app.githubsearch.data.dto

import com.google.gson.annotations.SerializedName
import com.joocoding.android.app.githubsearch.model.data.Repository
import java.io.Serializable


data class RepositoriesDto(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val repositories: List<Repository>
) : Serializable
