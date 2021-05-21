package com.example.network.utils


sealed class Error {
    sealed class Api : Error() {
        object FailedToParse : Api()
        object NetworkTimeout : Api()
        object NetworkInterrupted : Api()
        object Unauthorized : Api()
        object NotFound : Api()
        object BadRequest : Api()
        object ServerError : Api()
        object Generic : Api()
        data class HttpError(val errorCode: Int, val errorBody: String?) : Api()
    }

    sealed class Db : Error() {
        object NotFound : Db()
    }

    object DeviceOffline : Error()
}

