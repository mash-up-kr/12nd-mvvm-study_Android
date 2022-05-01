package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

/**
 * @author 김현국
 * @created 2022/04/22
 */
data class Repository(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("owner")
    val owner: Owner,

    @SerializedName("language")
    val language: String?
)
