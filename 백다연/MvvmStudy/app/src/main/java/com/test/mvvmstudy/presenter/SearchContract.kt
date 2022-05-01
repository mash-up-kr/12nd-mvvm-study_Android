package com.test.mvvmstudy.presenter

import com.test.mvvmstudy.model.ResultDetail


interface SearchContract {
    interface View {
        fun progressbarVisible(visible: Boolean)
        fun showResult(result: List<ResultDetail>)
    }

    interface Presenter {
        fun takeView(view: View)
        fun getSearchResult(query: String)

        interface Model {
            fun getSearchResult(
                query: String,
                onSuccess: (List<ResultDetail>) -> Unit,
                onFailure: (String) -> Unit
            )
        }
    }
}

