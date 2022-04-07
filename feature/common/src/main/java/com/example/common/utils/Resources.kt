package com.example.common.utils


sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val error: com.example.common.utils.Error) : Resource<T>()
}