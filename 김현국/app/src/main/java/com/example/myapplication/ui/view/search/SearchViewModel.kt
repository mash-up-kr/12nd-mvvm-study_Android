package com.example.myapplication.ui.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.base.BaseResponse
import com.example.myapplication.data.repository.SearchRepoRepository
import com.example.myapplication.ui.Results
import com.example.myapplication.ui.model.PresenterRepository
import com.orhanobut.logger.Logger

/**
 * @author 김현국
 * @created 2022/05/01
 */
class SearchViewModel(
    private val searchRepository: SearchRepoRepository
) : ViewModel() {

    private var _repoList: MutableLiveData<Results<List<PresenterRepository>>> = MutableLiveData()
    val repoList: LiveData<Results<List<PresenterRepository>>>
        get() = _repoList

    fun getRepoListWithQuery(q: String) {
        searchRepository.getRepoList(
            q = q,
            object : BaseResponse<List<PresenterRepository>> {
                override fun onSuccess(data: List<PresenterRepository>) {
                    _repoList.value = Results.Success(data)
                }

                override fun onError(throwable: Throwable) {
                    Logger.e(throwable, throwable.message ?: "예상치 못한 오류 메세지")
                    _repoList.value = Results.Failure("오류 발생", null)
                }

                override fun onLoading() {
                    _repoList.value = Results.Loading
                }
            }
        )
    }
}
