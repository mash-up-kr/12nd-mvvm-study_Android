package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.Results
import com.example.myapplication.domain.model.DomainOwner
import com.example.myapplication.domain.repository.DetailUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author 김현국
 * @created 2022/05/16
 */
class GetUserFollowerUseCase @Inject constructor(
    private val repository: DetailUserRepository
) {
    operator fun invoke(username: String): Flow<Results<List<DomainOwner>>> {
        return repository.getUserFollower(username = username)
    }
}
