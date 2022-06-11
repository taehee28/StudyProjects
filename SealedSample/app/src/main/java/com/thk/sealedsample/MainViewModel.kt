package com.thk.sealedsample

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thk.data.model.NetworkState
import com.thk.data.model.Post
import com.thk.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Post>>>(UiState.Init)
    val uiState
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(3000)
            _uiState.value = when (val result = postRepository.getPosts()) {
                is NetworkState.Success -> UiState.Success(result.data)
                is NetworkState.Error -> UiState.Error(result.message)
                is NetworkState.Exception -> UiState.Error(result.throwable.message)
            }
        }
    }
}

sealed class UiState<out T> {
    object Init: UiState<Nothing>()
    data class Success<out T> (val data: T): UiState<T>()
    data class Error(val message: String?): UiState<Nothing>()
}