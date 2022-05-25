package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.Results
import com.example.myapplication.domain.model.DomainOwner
import com.example.myapplication.domain.repository.DetailUserRepository
import kotlinx.coroutines.flow.Flow

/**
 * @author 김현국
 * @created 2022/05/16
 */
class GetUserFollowingUseCase(
    private val repository: DetailUserRepository
) {
    operator fun invoke(username: String): Flow<Results<List<DomainOwner>>> {
        return repository.getUserFollowing(username = username)
    }
}
