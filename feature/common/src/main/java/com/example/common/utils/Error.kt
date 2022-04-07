package com.example.common.utils

import com.example.common.R


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

fun Error.getErrorMessageRes() = when (this) {
    Error.Api.FailedToParse -> R.string.error_failed_to_parse
    Error.Api.NetworkTimeout -> R.string.error_network_time_out
    Error.Api.NetworkInterrupted -> R.string.error_network_interrupted
    Error.Api.Unauthorized -> R.string.error_unauthorized
    Error.Api.NotFound -> R.string.error_resource_not_found
    Error.Api.BadRequest -> R.string.error_could_not_connect_bad_request
    Error.Api.ServerError -> R.string.error_could_not_connect_bad_server_error
    Error.Api.Generic -> R.string.error_general
    is Error.Api.HttpError -> R.string.error_could_not_connect
    Error.Db.NotFound -> R.string.error_database
    Error.DeviceOffline -> R.string.error_offline
}
