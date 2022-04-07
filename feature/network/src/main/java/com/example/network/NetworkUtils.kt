package com.example.network

import com.example.common.utils.Resource
import com.example.common.utils.Error.Api
import org.json.JSONException
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T : Any> wrapApiCallToResource(call: suspend () -> T): Resource<T> {
    return try {
        val result = call()
        Resource.Success(result)
    } catch (e: HttpException) {
        when (e.code()) {
            400 -> Resource.Error(Api.BadRequest)
            401 -> Resource.Error(Api.Unauthorized)
            404 -> Resource.Error(Api.NotFound)
            500 -> Resource.Error(Api.ServerError)
            else -> Resource.Error(
                Api.HttpError(
                    errorCode = e.code(),
                    errorBody = e.message()
                )
            )
        }
    } catch (e: IOException) {
        Timber.e(e)
        Resource.Error(Api.NetworkInterrupted)
    } catch (e: SocketTimeoutException) {
        Timber.e(e)
        Resource.Error(Api.NetworkTimeout)
    } catch (e: JSONException) {
        Timber.e(e)
        Resource.Error(Api.FailedToParse)
    }
}