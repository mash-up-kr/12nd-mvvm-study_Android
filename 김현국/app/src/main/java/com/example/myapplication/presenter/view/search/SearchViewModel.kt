package com.example.myapplication.presenter.view.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.Results
import com.example.myapplication.domain.usecase.GetRepositoryListWithQueryUseCase
import com.example.myapplication.presenter.UiState
import com.example.myapplication.presenter.mapper.mapperDomainRepositoryToPresenterModel
import com.example.myapplication.presenter.model.PresenterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author 김현국
 * @created 2022/05/01
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getRepositoryListWithQueryUseCase: GetRepositoryListWithQueryUseCase
) : ViewModel() {

    companion object {
        const val QUERY = "query"
    }

    private val _repoList = MutableStateFlow<UiState<List<PresenterRepository>>>(UiState.Empty)
    val repoList = _repoList.asStateFlow()

    init {
        if (!savedStateHandle.contains(QUERY)) {
            getRepoListWithQuery("kotlin")
        }
    }

    fun getRepoListWithQuery(q: String) {
        if (q != savedStateHandle[QUERY]) {
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
}
