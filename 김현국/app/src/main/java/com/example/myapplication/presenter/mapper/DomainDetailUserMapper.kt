package com.example.myapplication.presenter.mapper

import com.example.myapplication.domain.model.DomainOwner
import com.example.myapplication.presenter.model.PresenterOwner

/**
 * @author 김현국
 * @created 2022/05/16
 */

fun mapperDomainDetailUserToPresenterModel(
    domainOwnerList: List<DomainOwner>
): List<PresenterOwner> {
    return domainOwnerList.map { domainOwner ->
        PresenterOwner(
            login = domainOwner.login,
            image = domainOwner.image
        )
    }
}
