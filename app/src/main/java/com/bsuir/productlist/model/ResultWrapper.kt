package com.bsuir.productlist.model

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()

    sealed class Error {
        data class Generic(val code: Int? = null, val error: String? = null): ResultWrapper<Nothing>()
        object Network: ResultWrapper<Nothing>()
    }

}