package com.github.sookhee.mvvmstudy

/**
 *  ResultState.kt
 *
 *  Created by Minji Jeong on 2022/05/02
 *  Copyright © 2022 MashUp All rights reserved.
 */

sealed class ResultState {
    object Loading: ResultState()
    data class Success<T>(val data: T) : ResultState()
    data class Error(val message: String) : ResultState()
}
