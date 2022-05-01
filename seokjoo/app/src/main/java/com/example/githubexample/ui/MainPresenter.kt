package com.example.githubexample.ui

import com.example.githubexample.entities.GithubResult
import com.example.githubexample.model.remote.RemoteDataSource

class MainPresenter(
    private val view: MainContract.View,
    private val remote: RemoteDataSource
) : MainContract.Presenter, MainContract.Presenter.ModelResult {
    override fun getRepositoryList(query: String) {
        view.activeProgressbar(true)
        remote.getRepositoryList(query, this)
    }

    override fun onSuccess(list: List<GithubResult.Item>) {
        view.submitList(list)
        view.activeProgressbar(false)
    }

    override fun onFailure(t: Throwable) {
        view.showError(t)
        view.activeProgressbar(false)
    }

}
