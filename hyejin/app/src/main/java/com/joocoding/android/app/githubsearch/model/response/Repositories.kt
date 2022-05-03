package com.joocoding.android.app.githubsearch.model.response

import com.google.gson.annotations.SerializedName


data class Repositories(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val repositories: List<Repository>
)
