package com.example.myapplication.presenter.search

import com.example.myapplication.model.RepoListModel
import com.example.myapplication.model.Repository

/**
 * @author 김현국
 * @created 2022/04/25
 */
class SearchPresenter :
    SearchContract.Presenter,
    SearchContract.Model.OnFinishedListener,
    OnItemClick {

    private lateinit var searchView: SearchContract.FragmentView
    private val searchModel: SearchContract.Model = RepoListModel()
    private lateinit var adapterModel: SearchAdapterContract.Model
    private lateinit var adapterView: SearchAdapterContract.View

    override fun takeView(view: SearchContract.FragmentView) {
        searchView = view
    }

    override fun getRepoList(q: String) {
        searchView.showLoading()
        searchModel.getRepoList(onFinishedListener = this, q = q)
    }

    override fun getRepoList() {
        searchModel.getRepoList(onFinishedListener = this)
    }

    override fun setRepoAdapterModel(model: SearchAdapterContract.Model) {
        adapterModel = model
    }

    override fun setRepoAdapterView(view: SearchAdapterContract.View) {
        adapterView = view
        adapterView.setOnClickListener(this)
    }

    override fun onFinished(repos: List<Repository>) {
        adapterModel.setData(repos)
         searchView.hideLoading()
    }

    override fun onFailure(t: Throwable) {
        searchView.hideLoading()
    }

    override fun onItemClick(position: Int) {
        searchView.navigateFragment()
    }
}
