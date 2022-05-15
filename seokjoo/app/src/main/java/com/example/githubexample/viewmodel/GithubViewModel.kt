package com.example.githubexample.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubexample.entities.GithubResult
import com.example.githubexample.model.remote.RemoteDataSource
import com.example.githubexample.ui.UiAction
import com.example.githubexample.ui.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GithubViewModel(private val remote: RemoteDataSource, private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val initialQuery: String = savedStateHandle.get(LAST_SEARCH_QUERY) ?: INITIAL_QUERY
    private val lastScrolledQuery: String = savedStateHandle.get(LAST_QUERY_SCROLLED) ?: INITIAL_QUERY
    private val actionFlow = MutableSharedFlow<UiAction>()

    private val searches = actionFlow
        .filterIsInstance<UiAction.Search>()
        .onStart { emit(UiAction.Search(query = initialQuery)) }

    private val scroll = actionFlow
        .filterIsInstance<UiAction.Scroll>()
        .distinctUntilChanged()
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            replay = 1
        ).onStart {
            emit(UiAction.Scroll(currentQuery = lastScrolledQuery))
        }
    val pagingData = searches.flatMapLatest {
        getRepositoryList(it.query)
    }.cachedIn(viewModelScope)

    val uiState = combine(
        searches,
        scroll,
        ::Pair
    ).map { (search, scroll) ->
        UiState(
            query = search.query,
            lastQueryScrolled = scroll.currentQuery,
            hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = UiState(),
        started = SharingStarted.WhileSubscribed(5000L)
    )

    val accept: (UiAction) -> Unit = { action ->
        viewModelScope.launch {
            actionFlow.emit(action)
        }
    }

    private fun getRepositoryList(query: String): Flow<PagingData<GithubResult.Item>> {
        return remote.getRepositoryList(query)
    }

    companion object {
        const val INITIAL_QUERY = "mash-up"
        const val LAST_SEARCH_QUERY = "last_search_query"
        const val LAST_QUERY_SCROLLED = "last_query_scrolled"
    }
}
