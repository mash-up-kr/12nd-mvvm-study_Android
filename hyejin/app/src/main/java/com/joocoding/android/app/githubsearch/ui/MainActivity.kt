package com.joocoding.android.app.githubsearch.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.joocoding.android.app.githubsearch.R
import com.joocoding.android.app.githubsearch.databinding.ActivityMainBinding
import com.joocoding.android.app.githubsearch.model.data.toDetail
import com.joocoding.android.app.githubsearch.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val mainAdapter: MainAdapter by lazy {
        MainAdapter(clickEvent = { repository ->
            repository.run {
                startActivity(DetailActivity.newIntent(this@MainActivity, repository.toDetail()))
            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = mainViewModel
        binding.recyclerView.adapter = mainAdapter
        initSearchView()
        observeViewModel()
        supportActionBar?.hide()

    }


    private fun observeViewModel() {
        lifecycleScope.launchWhenCreated {
            mainViewModel.repositories.collectLatest { githubList ->
                (binding.recyclerView.adapter as? MainAdapter)?.setItem(githubList)

            }
        }

        mainViewModel.toastMessage.observe(this) { toastMessageEvent ->
            toastMessageEvent.getContentIfNotHandled()
                ?.let { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        }
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

