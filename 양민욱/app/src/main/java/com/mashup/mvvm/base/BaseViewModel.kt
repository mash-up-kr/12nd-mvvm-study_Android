package com.mashup.mvvm.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel : ViewModel() {

    private val _isLoading = MutableSharedFlow<Boolean>()
    val isLoading: SharedFlow<Boolean> = _isLoading.asSharedFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage: SharedFlow<String> = _toastMessage.asSharedFlow()

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "CoroutineExceptionHandler got $exception")
        exception.message?.run { showToastMessage(this) }
    }

    protected fun mashupViewModelScope(
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(coroutineContext + handler + coroutineDispatcher) {
        try {
            showProgressbar()
            block()
        } finally {
            hideProgressbar()
        }
    }

    protected fun showProgressbar() = viewModelScope.launch {
        _isLoading.emit(true)
    }

    protected fun hideProgressbar() = viewModelScope.launch {
        _isLoading.emit(false)
    }

    protected fun showToastMessage(message: String) = viewModelScope.launch {
        _toastMessage.emit(message)
    }

    companion object {
        private val TAG = BaseViewModel::class.java.simpleName
    }
}