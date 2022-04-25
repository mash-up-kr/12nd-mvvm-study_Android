package com.mashup.mvvm.dto

import com.mashup.mvvm.data.model.Repository
import kotlinx.serialization.Serializable

@Serializable
data class RepositoriesDto(
    val totalCount: Int,
    val items: List<Repository>
)