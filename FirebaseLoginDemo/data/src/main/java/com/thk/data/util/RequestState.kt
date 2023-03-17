package com.thk.data.util

sealed class RequestState<out T> {
    data class Success<T>(val data: T) : RequestState<T>()
    data class Failure(val message: String) : RequestState<Nothing>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun failure(message: String) = Failure(message)
    }

    inline fun onSuccess(block: (T) -> Unit): RequestState<T> = apply {
        if (this is Success) {
            block(this.data)
        }
    }

    inline fun onFailure(block: (String) -> Unit): RequestState<T> = apply {
        if (this is Failure) {
            block(this.message)
        }
    }
}
