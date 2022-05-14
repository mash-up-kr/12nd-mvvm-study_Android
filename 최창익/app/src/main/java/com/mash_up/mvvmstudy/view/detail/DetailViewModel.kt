package com.mash_up.mvvmstudy.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mash_up.mvvmstudy.repository.model.Repository
import com.mash_up.mvvmstudy.repository.model.DetailUiModel
import com.mash_up.mvvmstudy.repository.model.toUiModel

class DetailViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _repository : Repository? = savedStateHandle.get<Repository>(REPOSITORY)

    private val _detailUiState: MutableLiveData<List<DetailUiModel>> = MutableLiveData()
    val detailUiState: LiveData<List<DetailUiModel>>
        get() = _detailUiState

    init {
        _detailUiState.value = _repository?.toUiModel()
    }

    companion object {
        const val REPOSITORY = "repository"
    }
}
