package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.Results
import com.example.myapplication.domain.model.DomainRepository
import com.example.myapplication.domain.repository.SearchRepoRepository
import kotlinx.coroutines.flow.Flow

/**
 * @author 김현국
 * @created 2022/05/02
 */
class GetRepositoryListWithQueryUseCase(
    private val repository: SearchRepoRepository
) {
    operator fun invoke(query: String): Flow<Results<List<DomainRepository>>> {
        return repository.getRepoList(q = query)
    }
}
