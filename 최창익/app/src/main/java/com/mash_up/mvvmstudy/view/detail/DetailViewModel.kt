package com.mash_up.mvvmstudy.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mash_up.mvvmstudy.repository.model.DetailUiModel
import com.mash_up.mvvmstudy.repository.model.Repository
import com.mash_up.mvvmstudy.repository.model.toDetailUiModel

class DetailViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _repository : Repository? = savedStateHandle.get<Repository>(REPOSITORY)

    private val _uiState: MutableLiveData<DetailUiModel> = MutableLiveData()
    val uiState: LiveData<DetailUiModel>
        get() = _uiState

    init {
        _uiState.value = _repository?.toDetailUiModel()
    }

    companion object {
        const val REPOSITORY = "repository"
    }
}