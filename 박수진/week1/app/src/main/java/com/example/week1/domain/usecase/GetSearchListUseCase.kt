package com.example.week1.domain.usecase

import com.example.week1.data.model.GithubRepoList
import com.example.week1.domain.repository.SearchRepository
import kotlinx.coroutines.*

class GetSearchListUseCase(
    private val searchRepository: SearchRepository
) {

    operator fun invoke(
        query: String,
        scope: CoroutineScope,
        onResult: (GithubRepoList) -> Unit
    ) {
        scope.launch {
            val githubRepoList = searchRepository.getSearchList(query)
            onResult(githubRepoList)
        }
    }
}