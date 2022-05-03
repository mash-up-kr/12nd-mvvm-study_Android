package com.joocoding.android.app.githubsearch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.joocoding.android.app.githubsearch.databinding.ActivityMainBinding
import com.joocoding.android.app.githubsearch.model.response.Repositories
import com.joocoding.android.app.githubsearch.model.response.Repository
import com.joocoding.android.app.githubsearch.network.GithubRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initRecycler()
        getRepository(GithubRetrofit.searchService.searchRepositories())
        initSearchView()
        supportActionBar?.hide()
    }

    private fun initRecycler() {
        mainAdapter = MainAdapter()
        binding.recyclerView.adapter = mainAdapter

    }

    private fun getRepository(repositoryRequest: Call<Repositories>) {
        repositoryRequest.enqueue(object : Callback<Repositories> {
            override fun onFailure(call: Call<Repositories>, t: Throwable) {
                Log.e(TAG, "Failed to fetch photos", t)
            }

            override fun onResponse(
                call: Call<Repositories>,
                response: Response<Repositories>
            ) {
                Log.d(TAG, "Response received")
                val repositories: Repositories? = response.body()
                var repository: List<Repository> =
                    repositories?.repositories?.filterNot {
                        it.owner.avatarUrl.isBlank()
                    } ?: emptyList()

                Log.i(TAG, "repositoryResponse= $repository")
                mainAdapter.datas = repository as MutableList<Repository>
                mainAdapter.notifyDataSetChanged()
            }

        })
    }


    private fun initSearchView() {
        var request: Call<Repositories>
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(queryText: String?): Boolean {
                    queryText?.let {
                        request = GithubRetrofit.searchService.searchRepositories(query = it)
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


