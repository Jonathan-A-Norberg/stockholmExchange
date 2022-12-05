package com.example.network.data

import kotlinx.serialization.Serializable


@Serializable
data class RemotePhotosRoot(
    var photos: RemotePhotos,
)

@Serializable
data class RemotePhotos(
    var page: Int,
    var pages: Int,
    var photo: List<RemotePhoto> = emptyList(),
)

@Serializable
data class RemotePhoto(
    var title: String,
    var id: String,
    var secret: String,
    var server: String,
)