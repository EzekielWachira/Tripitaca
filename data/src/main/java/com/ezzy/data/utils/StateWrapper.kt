package com.ezzy.data.utils

sealed class StateWrapper<out T> {
    data class Success<out T>(val data: T) : StateWrapper<T>()
    data class Failure<T>(
        val errorMessage: String
    ) : StateWrapper<T>()

    object Loading : StateWrapper<Nothing>()

    object Empty : StateWrapper<Nothing>()
////    object Complete : StateWrapper<Nothing>()
}