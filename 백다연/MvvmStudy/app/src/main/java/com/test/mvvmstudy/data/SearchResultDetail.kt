package com.test.mvvmstudy.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchResultDetail(
    val id: Int,
    val name: String,
    val language: String?,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("stargazers_count")
    val starCount: Int?,
    val description: String?,
    @SerializedName("updated_at")
    val updateAt: String,
    val owner: OwnerInfo
) : Serializable

data class OwnerInfo(
    @SerializedName("avatar_url")
    val imgUrl: String?
)
