package com.mash_up.mvvmstudy.repository.model

data class DetailUiModel(
    val userName: String,
    val repositoryName: String,
    val stargazerCount: String,
    val description: String,
    val language: String,
    val lastUpdated: String,
    val avatarUrl: String
)