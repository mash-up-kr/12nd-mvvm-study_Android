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
    val uiState: StateFlow<UiState>

    val accept: (UiAction) -> Unit

    val pagingData: Flow<PagingData<GithubResult.Item>>

    init {
        val initialQuery: String = savedStateHandle.get(LAST_SEARCH_QUERY) ?: INITIAL_QUERY
        val lastScrolledQuery: String = savedStateHandle.get(LAST_QUERY_SCROLLED) ?: INITIAL_QUERY
        val actionFlow = MutableSharedFlow<UiAction>()

        val searches = actionFlow
            .filterIsInstance<UiAction.Search>()
            .onStart { emit(UiAction.Search(query = initialQuery)) }

        val scroll = actionFlow
            .filterIsInstance<UiAction.Scroll>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                replay = 1
            ).onStart {
                emit(UiAction.Scroll(currentQuery = lastScrolledQuery))
            }

        pagingData = searches
            .flatMapLatest { getRepositoryList(it.query) }
            .cachedIn(viewModelScope)

        uiState = combine(
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

        accept = { action ->
            viewModelScope.launch {
                actionFlow.emit(action)
            }
        }
    }

    private fun getRepositoryList(query: String): Flow<PagingData<GithubResult.Item>> = remote.getRepositoryList(query)


    companion object {
        const val INITIAL_QUERY = "mash-up"
        const val LAST_SEARCH_QUERY = "last_search_query"
        const val LAST_QUERY_SCROLLED = "last_query_scrolled"
    }
}
