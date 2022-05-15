package com.github.sookhee.mvvmstudy

/**
 *  ResultState.kt
 *
 *  Created by Minji Jeong on 2022/05/02
 *  Copyright © 2022 MashUp All rights reserved.
 */

sealed class ResultState<out T> {
    object Loading : ResultState<Nothing>()
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error<T>(val message: String) : ResultState<T>()
}
