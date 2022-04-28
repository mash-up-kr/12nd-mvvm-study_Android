package com.example.myapplication.presenter.main

/**
 * @author 김현국
 * @created 2022/04/25
 */
class MainPresenter : MainContract.Presenter {
    private var mainView: MainContract.View? = null
    override fun takeView(view: MainContract.View) {
        mainView = view
    }
}
