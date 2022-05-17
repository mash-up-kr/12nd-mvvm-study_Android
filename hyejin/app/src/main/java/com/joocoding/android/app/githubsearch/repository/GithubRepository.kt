package com.joocoding.android.app.githubsearch.repository

import androidx.lifecycle.LiveData
import com.joocoding.android.app.githubsearch.model.response.Repository
import com.joocoding.android.app.githubsearch.network.GithubRetrofit

object GithubRepository {
    private val githubRetrofit = GithubRetrofit()

    val repositories: LiveData<List<Repository>>
        get() = githubRetrofit.repositories

    fun getRepository(query: String = "") {
        githubRetrofit.getRepository(query)
    }


}