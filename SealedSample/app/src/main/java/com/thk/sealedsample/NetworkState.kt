package com.thk.sealedsample

sealed class NetworkState<out T> {
    object Init: NetworkState<Nothing>()
    object Loading: NetworkState<Nothing>()
    data class Success<out T>(val data: T): NetworkState<T>()
    data class Error(val throwable: Throwable?): NetworkState<Nothing>()
}
