package com.example.myapplication.ui.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    val detailUserRepository: DetailUserRepository
) : ViewModel() {
    private var _userFollowingList: MutableLiveData<Results<List<PresenterOwner>>> = MutableLiveData()
    private var _userFollowerList: MutableLiveData<Results<List<PresenterOwner>>> = MutableLiveData()

    val userFollowingList: LiveData<Results<List<PresenterOwner>>>
        get() = _userFollowingList
    val userFollowerList: LiveData<Results<List<PresenterOwner>>>
        get() = _userFollowerList

    fun getUserFollowing(username: String) {
        detailUserRepository.getUserFollowing(
            username = username,
            callback = object : BaseResponse<List<PresenterOwner>> {
                override fun onSuccess(data: List<PresenterOwner>) {
                    Logger.t("following").d(data)
                    _userFollowingList.value = Results.Success(data)
                }

                override fun onError(throwable: Throwable) {
                    if (throwable.message != null) {
                        Logger.e(throwable, throwable.message!!)
                    }
                    _userFollowingList.value = Results.Failure("오류 발생", null)
                }

                override fun onLoading() {
                    _userFollowingList.value = Results.Loading
                }
            }
        )
    }
    fun getUserFollower(username: String) {
        detailUserRepository.getUserFollower(
            username = username,
            callback = object : BaseResponse<List<PresenterOwner>> {
                override fun onSuccess(data: List<PresenterOwner>) {
                    Logger.t("follower").d(data)
                    _userFollowerList.value = Results.Success(data)
                }

                override fun onError(throwable: Throwable) {
                    if (throwable.message != null) {
                        Logger.e(throwable, throwable.message!!)
                    }
                    _userFollowerList.value = Results.Failure("오류 발생", null)
                }

                override fun onLoading() {
                    _userFollowerList.value = Results.Loading
                }
            }

        )
    }
}
