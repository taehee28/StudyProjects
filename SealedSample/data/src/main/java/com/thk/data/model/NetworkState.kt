package com.thk.data.model

sealed class NetworkState<out T> {
    data class Success<out T>(val data: T): NetworkState<T>()
    data class Error(val code: Int, val message: String): NetworkState<Nothing>()
    data class Exception(val throwable: Throwable): NetworkState<Nothing>()
}
