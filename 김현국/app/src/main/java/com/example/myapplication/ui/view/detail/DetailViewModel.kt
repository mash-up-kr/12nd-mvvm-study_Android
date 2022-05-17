package com.example.myapplication.ui.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myapplication.base.BaseResponse
import com.example.myapplication.data.repository.DetailUserRepository
import com.example.myapplication.ui.Results
import com.example.myapplication.ui.model.PresenterOwner
import com.orhanobut.logger.Logger

/**
 * @author 김현국
 * @created 2022/05/01
 */
class DetailViewModel(
    val savedStateHandle: SavedStateHandle,
    private val detailUserRepository: DetailUserRepository
) : ViewModel() {

    val USER_NAME = "userName"

    private val _userFollowingList: MutableLiveData<Results<List<PresenterOwner>>> =
        MutableLiveData()
    private val _userFollowerList: MutableLiveData<Results<List<PresenterOwner>>> =
        MutableLiveData()

    val userFollowingList: LiveData<Results<List<PresenterOwner>>>
        get() = _userFollowingList
    val userFollowerList: LiveData<Results<List<PresenterOwner>>>
        get() = _userFollowerList

    fun getUserFollowing(username: String) {
        savedStateHandle[USER_NAME] = username
        detailUserRepository.getUserFollowing(
            username = username,
            callback = object : BaseResponse<List<PresenterOwner>> {
                override fun onSuccess(data: List<PresenterOwner>) {
                    _userFollowingList.value = Results.Success(data)
                }

                override fun onError(throwable: Throwable) {
                    Logger.e(throwable, throwable.message ?: "예상치 못한 오류 메세지")
                    _userFollowingList.value = Results.Failure("오류 발생", null)
                }

                override fun onLoading() {
                    _userFollowingList.value = Results.Loading
                }
            }
        )
    }

    fun getUserFollower(username: String) {
        savedStateHandle[USER_NAME] = username
        detailUserRepository.getUserFollower(
            username = username,
            callback = object : BaseResponse<List<PresenterOwner>> {
                override fun onSuccess(data: List<PresenterOwner>) {
                    _userFollowerList.value = Results.Success(data)
                }

                override fun onError(throwable: Throwable) {
                    Logger.e(throwable, throwable.message ?: "예상치 못한 오류 메세지")
                    _userFollowerList.value = Results.Failure("오류 발생", null)
                }

                override fun onLoading() {
                    _userFollowerList.value = Results.Loading
                }
            }

        )
    }
}
