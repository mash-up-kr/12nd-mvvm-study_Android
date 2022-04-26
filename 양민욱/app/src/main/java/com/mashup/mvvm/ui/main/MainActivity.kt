package com.mashup.mvvm.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.mashup.mvvm.R
import com.mashup.mvvm.ServiceLocator
import com.mashup.mvvm.data.error.CODE_SERVICE_NOT_AVAILABLE
import com.mashup.mvvm.databinding.ActivityMainBinding
import com.mashup.mvvm.dto.RepositoriesDto
import com.mashup.mvvm.extensions.showToast
import com.mashup.mvvm.network.GithubApi
import com.mashup.mvvm.ui.main.adapter.RepositoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val viewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            LayoutInflater.from(this)
        )
    }
    private val repositoryAdapter: RepositoryAdapter by lazy { RepositoryAdapter() }
    private val githubApi: GithubApi by lazy { ServiceLocator.injectGithubApi() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        setUi()
    }

    private fun setUi() {
        setUiOfEditText()
        setUiOfSearchButton()
        setUiOfRepositoryRecyclerView()
    }

    private fun setUiOfEditText() {
        viewBinding.etSearchRepository.doOnTextChanged { text, _, _, _ ->
            viewBinding.btnSearch.isEnabled = text?.isNotBlank() ?: false
        }
    }

    private fun setUiOfSearchButton() {
        viewBinding.btnSearch.setOnClickListener {
            fetchGithubRepository(viewBinding.etSearchRepository.text.toString())
        }
    }

    private fun setUiOfRepositoryRecyclerView() {
        viewBinding.rvRepository.apply {
            setHasFixedSize(true)
            adapter = repositoryAdapter
        }
    }

    private fun fetchGithubRepository(query: String) {
        githubApi.getRepositories(query).enqueue(
            object : Callback<RepositoriesDto> {
                override fun onResponse(
                    call: Call<RepositoriesDto>,
                    response: Response<RepositoriesDto>
                ) {
                    when {
                        response.isSuccessful -> {
                            response.body()?.run {
                                repositoryAdapter.submitList(items)
                            }
                        }
                        response.code() == CODE_SERVICE_NOT_AVAILABLE -> {
                            showToast(messageRes = R.string.error_service_not_available)
                        }
                    }
                }

                override fun onFailure(call: Call<RepositoriesDto>, t: Throwable) {
                    Log.e(TAG, t.message ?: t.stackTraceToString())
                }
            }
        )
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}