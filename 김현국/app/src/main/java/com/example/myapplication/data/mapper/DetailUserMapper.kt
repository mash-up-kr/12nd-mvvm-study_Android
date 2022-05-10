package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.DataOwner
import com.example.myapplication.ui.model.PresenterOwner

/**
 * @author 김현국
 * @created 2022/05/03
 */

fun mapperDetailUserToPresenterModel(
    dataOwnerList: List<DataOwner>
): List<PresenterOwner> {

    return dataOwnerList.map { dataOwner ->
        PresenterOwner(
            login = dataOwner.login,
            image = dataOwner.image
        )
    }
}
