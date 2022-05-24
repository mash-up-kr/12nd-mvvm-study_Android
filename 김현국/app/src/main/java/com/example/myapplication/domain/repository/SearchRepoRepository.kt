package com.example.myapplication.domain.repository

import com.example.myapplication.domain.Results
import com.example.myapplication.domain.model.DomainRepository
import kotlinx.coroutines.flow.Flow

/**
 * @author 김현국
 * @created 2022/05/01
 */
interface SearchRepoRepository {
    fun getRepoList(q: String): Flow<Results<List<DomainRepository>>>
}
