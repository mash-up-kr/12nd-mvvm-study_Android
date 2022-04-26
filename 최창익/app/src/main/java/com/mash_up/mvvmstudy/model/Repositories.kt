package com.mash_up.mvvmstudy.model

import com.google.gson.annotations.SerializedName

data class Repositories(
    @SerializedName("total_count")
    val totalCount : Int,
    @SerializedName("incomplete_results")
    val incompleteResult : Boolean,
    @SerializedName("items")
    val repositories: List<Repository>
)

data class Repository(
    @SerializedName("id")
    val id : Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("owner")
    val owner: Owner
)

data class Owner(
    @SerializedName("login")
    val login: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("avatar_url")
    val avatarUrl: String,

)