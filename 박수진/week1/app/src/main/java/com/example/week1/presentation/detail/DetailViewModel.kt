package com.example.week1.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.week1.data.model.GithubRepo

class DetailViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _repo: MutableLiveData<GithubRepo> = savedStateHandle.getLiveData(REPO_DETAIL)
    val repo: LiveData<GithubRepo> = _repo

    companion object {
        const val REPO_DETAIL = "repo_detail"
    }
}