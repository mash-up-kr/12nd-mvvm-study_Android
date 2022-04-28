package com.example.myapplication.presenter.search

import com.example.myapplication.model.Repository

/**
 * @author 김현국
 * @created 2022/04/25
 */
interface SearchAdapterContract {
    // View - Presenter 연결 인터페이스
    interface View {
        fun setOnClickListener(clickListener: OnItemClick)
    }

    // Presenter - Model 연결 인터페이스
    interface Model {
        fun setData(repos: List<Repository>)
    }
}
