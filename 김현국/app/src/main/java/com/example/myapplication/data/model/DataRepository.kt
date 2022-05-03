package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author 김현국
 * @created 2022/04/22
 */
data class DataRepository(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("owner")
    val owner: DataOwner,

    @SerializedName("language")
    val language: String?,

    @SerializedName("stargazers_count")
    val stars: Int?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("updated_at")
    val lastUpdated: String?
)
