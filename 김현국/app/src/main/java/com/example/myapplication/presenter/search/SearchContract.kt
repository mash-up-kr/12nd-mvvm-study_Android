package com.example.myapplication.presenter.search

import com.example.myapplication.base.BaseFragmentView
import com.example.myapplication.base.BasePresenter
import com.example.myapplication.model.Repository

/**
 * @author 김현국
 * @created 2022/04/25
 */
interface SearchContract {
    // Presenter - Model 연결 인터페이스
    interface Model {

        // Presenter 구현 & View 호출
        interface OnFinishedListener {
            fun onFinished(repos: List<Repository>)
            fun onFailure(t: Throwable)
        }

        // Presenter - Model  데이터 요청
        fun getRepoList(onFinishedListener: OnFinishedListener, q: String)
        fun getRepoList(onFinishedListener: OnFinishedListener)
    }

    // View - Presenter 연결 인터페이스 , View 구현 & Presenter 호출
    interface FragmentView : BaseFragmentView {
        override fun showLoading()
        override fun hideLoading()
        override fun showToast(message: String)
        fun navigateFragment()
    }

    // View - Presenter 호출, Presenter 구현 / View 호출
    interface Presenter : BasePresenter<FragmentView> {
        fun getRepoList(q: String)
        fun getRepoList()
        fun setRepoAdapterModel(model: SearchAdapterContract.Model)
        fun setRepoAdapterView(view: SearchAdapterContract.View)
    }
}
