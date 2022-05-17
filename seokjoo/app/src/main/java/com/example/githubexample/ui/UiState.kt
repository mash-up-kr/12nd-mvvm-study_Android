package com.example.githubexample.ui

data class UiState(
    val query: String = "",
    val lastQueryScrolled: String = "",
    val hasNotScrolledForCurrentSearch: Boolean = false
)
