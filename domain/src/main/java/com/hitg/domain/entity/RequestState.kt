package com.hitg.domain.entity

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class RequestState<out T : Any> {

    object Loading : RequestState<Nothing>()
    data class Success<out T : Any>(val data: T) : RequestState<T>()
    data class Error(val throwable: Throwable) : RequestState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$throwable]"
            is Loading -> "Loading..."
        }
    }
}