package com.urban.dictionary.service

sealed class ServiceResult<out R> {
    data class Success<out T>(val data: T) : ServiceResult<T>()
    data class Error(val exception: Exception) : ServiceResult<Nothing>()
}