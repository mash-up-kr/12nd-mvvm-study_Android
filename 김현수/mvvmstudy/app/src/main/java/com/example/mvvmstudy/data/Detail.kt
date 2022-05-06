package com.example.mvvmstudy.data

import java.io.Serializable

data class Detail(
    val userName: String,
    val avatarUrl: String,
    val repositoryName: String,
    val stargazerCount: String?,
    val language: String?,
    val lastUpdated: String,
    val description: String?
) : Serializable