package com.example.mvvmstudy.data

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("login")
    val login : String,
    @SerializedName("avatar_url")
    val avatar_Url : String
)
