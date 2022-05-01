package com.mashup.mvvm.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Repository(
    val id: Int,
    val name: String,
    val owner: Owner,
    val language: String?
)
