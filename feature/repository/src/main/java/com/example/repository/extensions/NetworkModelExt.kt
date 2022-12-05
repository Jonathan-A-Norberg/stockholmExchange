package com.example.repository.extensions

import com.example.network.data.RemotePhoto
import com.example.network.data.RemotePhotos
import com.example.repository.data.FlickrData
import com.example.repository.data.FlickrDataItem

fun RemotePhotos.toFlickrData(): FlickrData {
    return FlickrData(
        page = page,
        pages = pages,
        photoList = photo.map { flickr ->
            flickr.toFlickrDataItem()
        }
    )
}

fun RemotePhoto.toFlickrDataItem(): FlickrDataItem {
    return FlickrDataItem(
        title = title,
        urlMedium = "https://live.staticflickr.com/${server}/${id}_${secret}_w.jpg\n",
        urlSmall = "https://live.staticflickr.com/${server}/${id}_${secret}_s.jpg\n",
    )
}



