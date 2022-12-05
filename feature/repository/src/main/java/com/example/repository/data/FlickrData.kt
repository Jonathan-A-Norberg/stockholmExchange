package com.example.repository.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FlickrData(
    val page: Int,
    var pages: Int,
    val photoList: List<FlickrDataItem>,
) : Parcelable

@Parcelize
data class FlickrDataItem(
    val title: String,
    val urlMedium: String,
    val urlSmall: String,
) : Parcelable