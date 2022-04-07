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
    val id: String,
    val name: String,
    val image: String?,
    val stats: List<PokemonDataItemStats>,
    val types: List<String>,
    val weight: String,
) : Parcelable
@Parcelize
data class PokemonDataItemStats(
    val name: String,
    val stat: Int,
) : Parcelable