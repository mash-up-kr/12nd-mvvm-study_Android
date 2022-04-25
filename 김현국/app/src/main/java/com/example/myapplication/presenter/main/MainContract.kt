package com.example.myapplication.presenter.main

import com.example.myapplication.base.BaseActivityView
import com.example.myapplication.base.BasePresenter

/**
 * @author 김현국
 * @created 2022/04/25
 */

interface MainContract {
    interface View : BaseActivityView
    interface Presenter : BasePresenter<View>
}
