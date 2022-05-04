package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.DataOwner
import com.example.myapplication.ui.model.PresenterOwner

/**
 * @author 김현국
 * @created 2022/05/03
 */

fun detailUserToPresenterModel(
    dataOwnerList: List<DataOwner>
): List<PresenterOwner> {
    val list = mutableListOf<PresenterOwner>()

    dataOwnerList.forEach { dataOwner ->
        list.add(
            PresenterOwner(
                login = dataOwner.login,
                image = dataOwner.image
            )
        )
    }
    return list
}
