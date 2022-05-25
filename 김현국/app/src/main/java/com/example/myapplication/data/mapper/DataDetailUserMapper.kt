package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.DataOwner
import com.example.myapplication.domain.model.DomainOwner

/**
 * @author 김현국
 * @created 2022/05/03
 */

fun mapperDetailUserToDomainModel(dataOwnerList: List<DataOwner>): List<DomainOwner> {
    return dataOwnerList.map { dataOwner ->
        DomainOwner(
            login = dataOwner.login,
            image = dataOwner.image
        )
    }
}
