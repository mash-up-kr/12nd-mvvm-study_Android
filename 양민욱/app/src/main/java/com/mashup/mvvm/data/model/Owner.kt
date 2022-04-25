package com.mashup.mvvm.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Owner(
    val id: Int,
    val login: String,
    @JsonNames("avatar_url") val avatarUrl: String,
)
