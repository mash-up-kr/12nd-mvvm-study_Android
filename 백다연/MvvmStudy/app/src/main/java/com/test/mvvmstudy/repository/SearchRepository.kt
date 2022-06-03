package com.test.mvvmstudy.repository

import com.test.mvvmstudy.data.SearchResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getSearchItem(query: String) : Flow<SearchResult>

}