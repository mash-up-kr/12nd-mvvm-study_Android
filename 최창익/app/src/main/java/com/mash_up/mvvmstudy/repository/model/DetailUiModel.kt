package com.mash_up.mvvmstudy.repository.model

sealed interface DetailFeed {
    data class Profile(
        val avatarUrl: String,
        val ownerName: String,
        val stargazerCount: String
    ) : DetailFeed

    data class Description(
        val description: String?
    ) : DetailFeed

    data class Language(
        val language: String?
    ) : DetailFeed

    data class LastUpdated(
        val lastUpdated: String
    ) : DetailFeed
}

data class DetailUiModel(
    val repositoryName: String,
    val feeds: List<DetailFeed>
)