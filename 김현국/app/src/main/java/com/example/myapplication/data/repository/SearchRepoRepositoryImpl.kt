package com.example.myapplication.data.repository

import com.example.myapplication.data.mapper.mapperSearchResponseToDomainModel
import com.example.myapplication.data.source.remote.RepositoryRemoteDataSource
import com.example.myapplication.domain.Results
import com.example.myapplication.domain.model.DomainRepository
import com.example.myapplication.domain.repository.SearchRepoRepository
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author 김현국
 * @created 2022/05/01
 */
class SearchRepoRepositoryImpl @Inject constructor(
    private val remote: RepositoryRemoteDataSource
) : SearchRepoRepository {
    override fun getRepoList(q: String): Flow<Results<List<DomainRepository>>> {
        return flow {
            emit(Results.Loading)
            val response = remote.getRepositories(q = q)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(Results.Success(mapperSearchResponseToDomainModel(response = body)))
            } else {
                emit(Results.Failure(response.message()))
            }
        }.flowOn(Dispatchers.IO)
    }
}
