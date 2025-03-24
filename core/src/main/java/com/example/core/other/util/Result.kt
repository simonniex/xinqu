package com.example.core.other.util

sealed class ResultState<out T> {
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val exception: Throwable, val message: String? = null) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()
    object Empty : ResultState<Nothing>()
}

inline fun <T> ResultState<T>.onSuccess(action: (T) -> Unit): ResultState<T> {
    if (this is ResultState.Success) action(data)
    return this
}

inline fun <T> ResultState<T>.onError(action: (Throwable, String?) -> Unit): ResultState<T> {
    if (this is ResultState.Error) action(exception, message)
    return this
}

inline fun <T> ResultState<T>.onLoading(action: () -> Unit): ResultState<T> {
    if (this is ResultState.Loading) action()
    return this
}

inline fun <T> ResultState<T>.onEmpty(action: () -> Unit): ResultState<T> {
    if (this is ResultState.Empty) action()
    return this
}
