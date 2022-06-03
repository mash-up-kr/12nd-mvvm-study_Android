package com.example.myapplication.presenter.view.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.Results
import com.example.myapplication.domain.usecase.GetUserFollowerUseCase
import com.example.myapplication.domain.usecase.GetUserFollowingUseCase
import com.example.myapplication.presenter.UiState
import com.example.myapplication.presenter.mapper.mapperDomainDetailUserToPresenterModel
import com.example.myapplication.presenter.model.PresenterOwner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author 김현국
 * @created 2022/05/01
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getUserFollowerUseCase: GetUserFollowerUseCase,
    private val getUserFollowingUseCase: GetUserFollowingUseCase
) : ViewModel() {

    companion object {
        val USER_NAME = "userName"
    }

    private val _userFollowingList =
        MutableStateFlow<UiState<List<PresenterOwner>>>(UiState.Empty)
    private val _userFollowerList = MutableStateFlow<UiState<List<PresenterOwner>>>(UiState.Empty)

    val userFollowingList = _userFollowingList.asStateFlow()
    val userFollowerList = _userFollowerList.asStateFlow()

    private fun getUserFollowing(username: String) {
        savedStateHandle[USER_NAME] = username
        viewModelScope.launch {
            getUserFollowingUseCase(username = username).collect { result ->
                when (result) {
                    is Results.Success -> {
                        val presenterResult = mapperDomainDetailUserToPresenterModel(result.value)
                        _userFollowingList.value = UiState.Success(presenterResult)
                    }
                    is Results.Failure -> {
                        _userFollowingList.value = UiState.Error("오류 발생")
                    }
                    is Results.Loading -> {
                        _userFollowingList.value = UiState.Loading
                    }
                }
            }
        }
    }

    private fun getUserFollower(username: String) {
        savedStateHandle[USER_NAME] = username
        viewModelScope.launch {
            getUserFollowerUseCase(username = username).collect { result ->
                when (result) {
                    is Results.Success -> {
                        val presenterResult = mapperDomainDetailUserToPresenterModel(result.value)
                        _userFollowerList.value = UiState.Success(presenterResult)
                    }
                    is Results.Failure -> {
                        _userFollowerList.value = UiState.Error("오류 발생")
                    }
                    is Results.Loading -> {
                        _userFollowerList.value = UiState.Loading
                    }
                }
            }
        }
    }

    fun getUserFollow(username: String) {
        if (!savedStateHandle.contains(USER_NAME)) {
            getUserFollowing(username = username)
            getUserFollower(username = username)
        }
    }
}
