package com.example.repository.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonDetailsData(
    val id: String,
    val name: String,
    val image: String?,
    val stats: List<PokemonStats>,
    val types: List<String>,
    val height: String,
    val weight: String,
) : Parcelable

@Parcelize
data class PokemonStats(
    val name: String,
    val stat: Int,
) : Parcelable
