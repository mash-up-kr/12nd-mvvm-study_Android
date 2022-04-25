package com.example.myapplication.presenter.search

import com.example.myapplication.model.Repository

/**
 * @author 김현국
 * @created 2022/04/25
 */
interface SearchAdapterContract {
    interface View {
        fun notifyAdapter()
        fun setOnClickListener(clickListener: OnItemClick)
    }

    interface Model {
        fun setData(repos: List<Repository>)
    }
}
