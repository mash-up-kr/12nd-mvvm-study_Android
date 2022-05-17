package com.example.myapplication.presenter.view.search

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.Results
import com.example.myapplication.domain.usecase.GetRepositoryListWithQueryUseCase
import com.example.myapplication.presenter.UiState
import com.example.myapplication.presenter.mapper.mapperDomainRepositoryToPresenterModel
import com.example.myapplication.presenter.model.PresenterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author 김현국
 * @created 2022/05/01
 */
class SearchViewModel(
    val savedStateHandle: SavedStateHandle,
    private val getRepositoryListWithQueryUseCase: GetRepositoryListWithQueryUseCase
) : ViewModel() {
    val QUERY = "query"

    private val _repoList = MutableStateFlow<UiState<List<PresenterRepository>>>(UiState.Loading)
    val repoList = _repoList.asStateFlow()

    fun getRepoListWithQuery(q: String) {
        savedStateHandle[QUERY] = q
        viewModelScope.launch {
            getRepositoryListWithQueryUseCase(
                query = q
            ).collect { result ->
                when (result) {
                    is Results.Success -> {
                        val presentationResult = mapperDomainRepositoryToPresenterModel(result.value)
                        _repoList.value = UiState.Success(presentationResult)
                    }
                    is Results.Failure -> {
                        _repoList.value = UiState.Error("오류 발생")
                    }
                    is Results.Loading -> {
                        _repoList.value = UiState.Loading
                    }
                }
            }
        }
    }
}
