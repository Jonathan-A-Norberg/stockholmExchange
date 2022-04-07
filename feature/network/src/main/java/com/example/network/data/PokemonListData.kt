package com.example.network.data

import kotlinx.serialization.Serializable

@Serializable
data class RemotePokemonData(
    val next: String,
    val results: List<RemotePokemonDataItem>,
)
@Serializable
data class RemotePokemonDataItem(
    val name: String,
    val url: String,
)