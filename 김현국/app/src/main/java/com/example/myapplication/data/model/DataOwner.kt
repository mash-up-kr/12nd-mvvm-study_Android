package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author 김현국
 * @created 2022/04/25
 */
data class DataOwner(

    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val image: String
)
