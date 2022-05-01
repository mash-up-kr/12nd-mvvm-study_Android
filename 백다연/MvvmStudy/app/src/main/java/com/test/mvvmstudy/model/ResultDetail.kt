package com.test.mvvmstudy.model

import com.google.gson.annotations.SerializedName

data class ResultDetail(
    val id : Int,
    val name : String,
    val language : String,
    @SerializedName("full_name")
    val fullName : String,
    val owner : OwnerInfo
)

data class OwnerInfo(
    @SerializedName("avatar_url")
    val imgUrl : String
)
