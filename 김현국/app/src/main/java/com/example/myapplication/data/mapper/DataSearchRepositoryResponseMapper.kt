package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.DataOwner
import com.example.myapplication.data.response.SearchRepositoryResponse
import com.example.myapplication.domain.model.DomainOwner
import com.example.myapplication.domain.model.DomainRepository

/**
 * @author 김현국
 * @created 2022/05/01
 * with domain layer
 */

fun mapperSearchResponseToDomainModel(response: SearchRepositoryResponse): List<DomainRepository> {
    return response.items.map { dataRepository ->
        DomainRepository(
            id = dataRepository.id,
            name = dataRepository.name,
            language = dataRepository.language,
            owner = ownerToDomainModel(dataRepository.owner),
            stars = dataRepository.stars,
            description = dataRepository.description,
            lastUpdated = dataRepository.lastUpdated
        )
    }
}

fun ownerToDomainModel(owner: DataOwner): DomainOwner {
    return DomainOwner(
        login = owner.login,
        image = owner.image
    )
}
