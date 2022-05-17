package com.example.myapplication.data.response

import com.example.myapplication.data.model.DataRepository
import com.google.gson.annotations.SerializedName

/**
 * @author 김현국
 * @created 2022/05/01
 */
data class SearchRepositoryResponse(
    @SerializedName("items")
    val items: List<DataRepository>
)
