package com.example.network

import com.example.common.utils.Resource
import com.example.network.data.RemotePhotos
import com.example.network.data.RemotePhotosRoot
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query


interface FlickrApi {

    @GET("services/rest/")
    suspend fun getFlickrListFromSearch(
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") apiKey: String = "6c9d634e8a5108c95a04409dbf5682ab", //TODO this should be encrypted
        @Query("text") text: String?,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: String = "1",
        @Query("safe_search") safeSearch: String = "1",
        @Query("page") page: Int?,
    ): RemotePhotosRoot

    @GET("services/rest/")
    suspend fun getFlickrListRecent(
        @Query("method") method: String = "flickr.photos.getRecent",
        @Query("api_key") apiKey: String = "6c9d634e8a5108c95a04409dbf5682ab", //TODO this should be encrypted
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: String = "1",
        @Query("safe_search") safeSearch: String = "1",
        @Query("page") page: Int?,
    ): RemotePhotosRoot

}

class FlickrApiService(retrofit: Retrofit) {

    private val api: FlickrApi = retrofit.create()

    suspend fun getFlickrListFromSearch(searchText: String?, page: Int?): Resource<RemotePhotosRoot> {
        return wrapApiCallToResource {
            api.getFlickrListFromSearch(
                text = searchText,
                page = page
            )
        }
    }
    suspend fun getFlickrListFromLatest(page: Int?): Resource<RemotePhotosRoot> {
        return wrapApiCallToResource {
            api.getFlickrListRecent(page = page)
        }
    }

}