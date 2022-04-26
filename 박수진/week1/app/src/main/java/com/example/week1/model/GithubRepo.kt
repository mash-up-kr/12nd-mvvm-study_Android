package com.example.week1.model

import java.io.Serializable

data class GithubRepo(
    val id: Long,
    val name: String,
    val language: String
): Serializable
