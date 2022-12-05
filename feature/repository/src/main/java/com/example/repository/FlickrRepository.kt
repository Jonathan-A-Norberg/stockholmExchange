package com.example.repository

import com.example.common.utils.Resource
import com.example.network.FlickrApiService
import com.example.repository.data.FlickrData
import com.example.repository.extensions.toFlickrData
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class FlickrRepository @Inject constructor(
    private val apiService: FlickrApiService
) {
    suspend fun getFlickrList(searchText: String?, page: Int?): Resource<FlickrData> = coroutineScope {
        return@coroutineScope when (val res =
            apiService.getFlickrListFromSearch(searchText = searchText, page = page)) {
            is Resource.Error -> Resource.Error(res.error)
            is Resource.Success -> {
                Resource.Success(res.data.photos.toFlickrData())
            }
        }
    }
    suspend fun getFlickrListRecent(page: Int?): Resource<FlickrData> = coroutineScope {
        return@coroutineScope when (val res =
            apiService.getFlickrListFromLatest(page = page)) {
            is Resource.Error -> Resource.Error(res.error)
            is Resource.Success -> {
                Resource.Success(res.data.photos.toFlickrData())
            }
        }
    }
}