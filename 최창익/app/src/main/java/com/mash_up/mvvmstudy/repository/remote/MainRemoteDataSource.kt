package com.mash_up.mvvmstudy.repository.remote

class MainRemoteDataSource {
    private val api: GitService = ClientFactory.createService(GitService::class.java)

    suspend fun getRepositories(query: String) =
        api.getRepositories(query)
}