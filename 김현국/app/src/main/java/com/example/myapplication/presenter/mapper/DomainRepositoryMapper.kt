package com.example.myapplication.presenter.mapper

import com.example.myapplication.domain.model.DomainOwner
import com.example.myapplication.domain.model.DomainRepository
import com.example.myapplication.presenter.model.PresenterOwner
import com.example.myapplication.presenter.model.PresenterRepository

/**
 * @author 김현국
 * @created 2022/05/02
 */

fun mapperDomainRepositoryToPresenterModel(
    repoList: List<DomainRepository>
): List<PresenterRepository> {
    return repoList.map { domainRepository ->
        PresenterRepository(
            id = domainRepository.id,
            name = domainRepository.name,
            owner = domainOwnerToPresenterModel(domainRepository.owner),
            language = domainRepository.language,
            stars = domainRepository.stars,
            description = domainRepository.description,
            lastUpdated = domainRepository.lastUpdated
        )
    }
}

fun domainOwnerToPresenterModel(
    owner: DomainOwner
): PresenterOwner {
    return PresenterOwner(
        login = owner.login,
        image = owner.image
    )
}
