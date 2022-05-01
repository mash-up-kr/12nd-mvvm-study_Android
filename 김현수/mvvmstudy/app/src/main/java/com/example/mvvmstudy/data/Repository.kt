package com.example.mvvmstudy.data

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("name")
    val name:String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("description")
    val description : String?,
    @SerializedName("language")
    val language : String,
    @SerializedName("stargazer_count")
    val stargazerCount : Int
)
