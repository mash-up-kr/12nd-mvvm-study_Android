package com.example.githubexample.ui

import com.example.githubexample.entities.GithubResult

interface MainContract {
    interface View {
        // 뷰 초기화
        fun initView()

        // 키보드 숨기기
        fun hideKeyboard()

        // 뒤로가기 버튼 세팅
        fun setSearchViewBackButtonListener()

        // 리스트 전달
        fun submitList(list: List<GithubResult.Item>)

        // 에러 logging
        fun showError(t: Throwable)

        // progress bar
        fun activeProgressbar(active: Boolean)
    }

    interface Presenter {
        // 레포지토리 요청
        fun getRepositoryList(query: String)

        interface ModelResult {
            fun onSuccess(list: List<GithubResult.Item>)
            fun onFailure(t: Throwable)
        }
    }
}
