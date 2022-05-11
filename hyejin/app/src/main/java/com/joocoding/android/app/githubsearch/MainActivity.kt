package com.joocoding.android.app.githubsearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.joocoding.android.app.githubsearch.databinding.ActivityMainBinding
import com.joocoding.android.app.githubsearch.model.response.Repository
import com.joocoding.android.app.githubsearch.model.response.toDetail
import com.joocoding.android.app.githubsearch.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = mainViewModel
        initRecycler()
        initSearchView()
        supportActionBar?.hide()

    }

    private fun initRecycler() {
        mainAdapter = MainAdapter(clickEvent = { repository: Repository ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_KEY_REPO, repository.toDetail())
            startActivity(intent)
        }
        )

        binding.recyclerView.adapter = mainAdapter

        mainViewModel.repositories.observe(
            this,
            Observer {
                (binding.recyclerView.adapter as? MainAdapter)?.setItem(it)
            })
    }

    private fun initSearchView() {
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(queryText: String?): Boolean {
                    queryText?.let {
                        Log.i(TAG, "initSearchView, queryText=$queryText")
                        mainViewModel.getRepository(queryText)

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



