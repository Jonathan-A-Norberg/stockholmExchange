package com.example.repository.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ColorPickerData(
    val name: String,
    val color: Int,
    val colorHexName: String,
    val numTrades: Int,
    val tradesToday: Int = 0,
    val risk: Int
) : Parcelable