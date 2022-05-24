package com.example.myapplication.data.repository

import com.example.myapplication.data.mapper.mapperDetailUserToDomainModel
import com.example.myapplication.data.source.remote.UserRemoteDataSource
import com.example.myapplication.domain.Results
import com.example.myapplication.domain.model.DomainOwner
import com.example.myapplication.domain.repository.DetailUserRepository
import com.example.myapplication.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * @author 김현국
 * @created 2022/05/12
 */
class DetailUserRepositoryImpl : DetailUserRepository {
    override fun getUserFollowing(username: String): Flow<Results<List<DomainOwner>>> {
        return flow {
            emit(Results.Loading)
            val service: UserRemoteDataSource = ApiClient.buildApi(UserRemoteDataSource::class.java)
            val response = service.getUserFollowing(username = username)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(Results.Success(mapperDetailUserToDomainModel(dataOwnerList = body)))
            } else {
                emit(Results.Failure(response.message()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getUserFollower(username: String): Flow<Results<List<DomainOwner>>> {
        return flow {
            emit(Results.Loading)
            val service: UserRemoteDataSource = ApiClient.buildApi(UserRemoteDataSource::class.java)
            val response = service.getUserFollowers(username = username)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(Results.Success(mapperDetailUserToDomainModel(dataOwnerList = body)))
            } else {
                emit(Results.Failure(response.message()))
            }
        }.flowOn(Dispatchers.IO)
    }
}
