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

    private var searchView: SearchContract.FragmentView? = null
    private var searchModel: SearchContract.Model? = null
    private var adapterModel: SearchAdapterContract.Model? = null
    private var adapterView: SearchAdapterContract.View? = null

    init {
        searchModel = RepoListModel()
    }

    override fun takeView(view: SearchContract.FragmentView) {
        searchView = view
    }

    override fun dropView() {
        searchView = null
    }

    override fun getRepoList(q: String) {
        searchView?.showLoading()
        searchModel?.getRepoList(onFinishedListener = this, q = q)
    }

    override fun getRepoList() {
        searchModel?.getRepoList(onFinishedListener = this)
    }

    override fun setRepoAdapterModel(model: SearchAdapterContract.Model) {
        adapterModel = model
    }

    override fun setRepoAdapterView(view: SearchAdapterContract.View) {
        adapterView = view
        adapterView!!.setOnClickListener(this)
    }

    override fun onFinished(repos: List<Repository>) {
        if (searchView != null) {
            searchView!!.hideLoading()
            adapterModel?.setData(repos)
            adapterView?.notifyAdapter()
        }
    }

    override fun onFailure(t: Throwable) {
        if (searchView != null) {
            searchView!!.hideLoading()
        }
    }

    override fun onItemClick(position: Int) {
        searchView!!.navigateFragment()
    }
}
