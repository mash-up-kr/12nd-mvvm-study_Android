package com.test.mvvmstudy.presenter

import com.test.mvvmstudy.data.ResultDetail


interface SearchContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showResult(result : List<ResultDetail>)
    }

    interface Presenter {
        fun takeView(view : View)
        fun getSearchResult(query : String)
    }
}