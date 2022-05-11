package com.github.sookhee.mvvmstudy.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.sookhee.mvvmstudy.model.GithubRepositoryModel
import com.github.sookhee.mvvmstudy.ui.EXTRA_REPOSITORY_KEY

/**
 *  DetailViewModel.kt
 *
 *  Created by Minji Jeong on 2022/05/11
 *  Copyright © 2022 MashUp All rights reserved.
 */

class DetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val repositoryData = savedStateHandle.getLiveData<GithubRepositoryModel>(EXTRA_REPOSITORY_KEY)
}
