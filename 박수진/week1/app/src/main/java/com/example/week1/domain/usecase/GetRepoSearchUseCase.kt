package com.example.week1.domain.usecase

import com.example.week1.data.model.GithubRepoList
import com.example.week1.domain.repository.RepoSearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GetRepoSearchUseCase(
    private val repoSearchRepository: RepoSearchRepository
) {

    operator fun invoke(
        query: String,
        scope: CoroutineScope,
        onResult: (GithubRepoList) -> Unit
    ) {
        scope.launch {
            val deferred = async(Dispatchers.IO) {
                repoSearchRepository.getRepoList(query)
            }
            onResult(deferred.await())
        }
    }
}