package com.mashup.mvvm.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Owner(
    val id: Int,
    val login: String,
    val avatarUrl: String,
)
