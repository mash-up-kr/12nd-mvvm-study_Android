package com.example.week1.model

import java.io.Serializable

data class GithubRepoList(
    val total_count: Int,
    val items: List<GithubRepo>
): Serializable