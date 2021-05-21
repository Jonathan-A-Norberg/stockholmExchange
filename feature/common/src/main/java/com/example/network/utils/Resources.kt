package com.example.network.utils


sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val error: com.example.network.utils.Error) : Resource<T>()
}