package com.joocoding.android.app.githubsearch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.joocoding.android.app.githubsearch.databinding.ActivityMainBinding
import com.joocoding.android.app.githubsearch.model.response.RepositoriesResponse
import com.joocoding.android.app.githubsearch.model.response.RepositoryResponse
import com.joocoding.android.app.githubsearch.network.GithubRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initRecycler()
        getRepository(GithubRetrofit.githubRetrofit.searchRepositories())
        initSearchView()
        supportActionBar?.hide()
    }

    private fun initRecycler() {
        mainAdapter = MainAdapter()
        binding.recyclerView.adapter = mainAdapter

    }

    private fun getRepository(repositoryRequest: Call<RepositoriesResponse>) {
        repositoryRequest.enqueue(object : Callback<RepositoriesResponse> {
            override fun onFailure(call: Call<RepositoriesResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch photos", t)
            }

            override fun onResponse(
                call: Call<RepositoriesResponse>,
                response: Response<RepositoriesResponse>
            ) {
                Log.d(TAG, "Response received")
                val repositoriesResponse: RepositoriesResponse? = response.body()
                var repositoryResponse: List<RepositoryResponse> =
                    repositoriesResponse?.repositories
                        ?: mutableListOf()

                repositoryResponse = repositoryResponse.filterNot {
                    it.owner.avatarUrl.isBlank()
                }
                Log.i(TAG, "repositoryResponse= $repositoryResponse")
                mainAdapter.datas = repositoryResponse as MutableList<RepositoryResponse>
                mainAdapter.notifyDataSetChanged()
            }

        })
    }


    private fun initSearchView() {
        var request: Call<RepositoriesResponse>
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(queryText: String?): Boolean {
                    queryText?.let {
                        request = GithubRetrofit.githubRetrofit.searchRepositories(query = it)
                        getRepository(request)
                    }
                    return true
                }

                override fun onQueryTextSubmit(queryText: String?): Boolean {
                    return false
                }

            })
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}


