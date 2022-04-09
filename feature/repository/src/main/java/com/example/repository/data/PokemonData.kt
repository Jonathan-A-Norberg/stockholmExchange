package com.example.repository.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonData(
    val url: String?,
    val list: List<PokemonDataItem>,
) : Parcelable

@Parcelize
data class PokemonDataItem(
    val id: String?,
    val name: String,
    val url: String,
    val image: String?,
) : Parcelable

