package com.bsuir.productlist.util

import com.bsuir.productlist.model.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(Dispatchers.IO) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.Error.Network
                is HttpException -> {
                    val code = throwable.code()
                    val response = throwable.response()?.errorBody()?.source().toString()
                    ResultWrapper.Error.Generic(code, response)
                }
                else -> {
                    ResultWrapper.Error.Generic(null, null)
                }
            }
        }
    }
}

suspend fun <T> ResultWrapper<T>.onSuccess(action: suspend (ResultWrapper.Success<T>) -> Unit): ResultWrapper<T> {
    if (this is ResultWrapper.Success) {
        action.invoke(this)
    }
    return this
}

internal fun <T> ResultWrapper<T>.successValueOrNull() = when(this) {
    is ResultWrapper.Success -> this.value
    else -> null
}