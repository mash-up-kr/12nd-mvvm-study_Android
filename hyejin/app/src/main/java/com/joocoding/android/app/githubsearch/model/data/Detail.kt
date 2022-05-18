package com.joocoding.android.app.githubsearch.model.data

import java.io.Serializable

data class Detail(
    val detailName: String,
    val detailImage: String,
    val detailStarCnt: String,
    val detailDescription: String?,
    val detailLang: String,
    val detailUpdated: String
) : Serializable
