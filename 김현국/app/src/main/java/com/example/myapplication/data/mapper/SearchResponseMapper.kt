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
    val list = mutableListOf<PresenterRepository>()

    response.items.forEach {
        list.add(
            PresenterRepository(
                id = it.id,
                name = it.name,
                language = it.language,
                owner = ownerToPresenterModel(it.owner),
                stars = it.stars,
                description = it.description,
                lastUpdated = it.lastUpdated

            )
        )
    }
    return list
}
fun ownerToPresenterModel(owner: DataOwner): PresenterOwner {
    return PresenterOwner(
        login = owner.login,
        image = owner.image
    )
}
