package com.example.network.data

import kotlinx.serialization.Serializable

@Serializable
data class RemotePokemonListData(
    val next: String?,
    val results: List<RemotePokemonListItemData>,
)
@Serializable
data class RemotePokemonListItemData(
    val name: String,
    val url: String,
)