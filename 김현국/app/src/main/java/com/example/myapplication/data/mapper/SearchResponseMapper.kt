package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.DataOwner
import com.example.myapplication.data.response.SearchRepositoryResponse
import com.example.myapplication.ui.model.PresenterOwner
import com.example.myapplication.ui.model.PresenterRepository

/**
 * @author 김현국
 * @created 2022/05/03
 */

fun searchRepositoryResponseToPresenterModel(
    response: SearchRepositoryResponse
): List<PresenterRepository> {

    return response.items.map { dataRepository ->
        PresenterRepository(
            id = dataRepository.id,
            name = dataRepository.name,
            language = dataRepository.language,
            owner = ownerToPresenterModel(dataRepository.owner),
            stars = dataRepository.stars,
            description = dataRepository.description,
            lastUpdated = dataRepository.lastUpdated

        )
    }
}

fun ownerToPresenterModel(owner: DataOwner): PresenterOwner {
    return PresenterOwner(
        login = owner.login,
        image = owner.image
    )
}
