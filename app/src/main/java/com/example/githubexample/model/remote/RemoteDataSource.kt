package com.example.githubexample.model.remote

import com.example.githubexample.ui.MainContract

interface RemoteDataSource {
    fun getRepositoryList(query: String, modelResult: MainContract.Presenter.ModelResult)
}
