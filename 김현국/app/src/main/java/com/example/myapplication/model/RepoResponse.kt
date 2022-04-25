package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

/**
 * @author 김현국
 * @created 2022/04/25
 */
data class RepoResponse(
    @SerializedName("items")
    val items: List<Repository>
)
