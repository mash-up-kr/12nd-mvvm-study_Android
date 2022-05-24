package com.example.myapplication.domain.repository

import com.example.myapplication.domain.Results
import com.example.myapplication.domain.model.DomainOwner
import kotlinx.coroutines.flow.Flow

/**
 * @author 김현국
 * @created 2022/05/12
 */
interface DetailUserRepository {
    fun getUserFollowing(username: String): Flow<Results<List<DomainOwner>>>

    fun getUserFollower(username: String): Flow<Results<List<DomainOwner>>>
}
