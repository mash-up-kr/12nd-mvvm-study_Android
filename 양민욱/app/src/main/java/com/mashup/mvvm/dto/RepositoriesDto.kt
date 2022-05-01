package com.mashup.mvvm.dto

import com.mashup.mvvm.data.model.Repository
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class RepositoriesDto(
    @JsonNames("total_count") val totalCount: Int,
    val items: List<Repository>
)