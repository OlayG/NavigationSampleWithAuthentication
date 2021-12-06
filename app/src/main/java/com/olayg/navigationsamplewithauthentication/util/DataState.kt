package com.olayg.navigationsamplewithauthentication.util

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()
    data class Error(val errorMsg: String) : DataState<Nothing>()
    data class Success<T>(val item: T) : DataState<T>()
}
