package com.example.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemotePokemonDetailsData(
    val id: Int,
    val name: String,
    val sprites: RemotePokemonDetailsSprites,
    val stats: List<RemotePokemonDetailsStats>,
    val types: List<RemotePokemonDetailsTypes>,
    val height: Int,
    val weight: Int,
)

@Serializable
data class RemotePokemonDetailsSprites(
    val front_default: String?,
    val front_shiny: String?,
    val other: RemotePokemonDetailsSpritesOther,
)

@Serializable
data class RemotePokemonDetailsSpritesOther(
    val dream_world: RemotePokemonDetailsSpritesImage,
    val home: RemotePokemonDetailsSpritesImage,
    @SerialName("official-artwork")
    val artwork: RemotePokemonDetailsSpritesImage,
)

@Serializable
data class RemotePokemonDetailsSpritesImage(
    val front_default: String?,
)

@Serializable
data class RemotePokemonDetailsStats(
    val base_stat: Int,
    val stat: RemotePokemonDetailsStatsName,
)

@Serializable
data class RemotePokemonDetailsStatsName(
    val name: String,
)
@Serializable
data class RemotePokemonDetailsTypes(
    val type: RemotePokemonDetailsType,
)
@Serializable
data class RemotePokemonDetailsType(
    val name: String,
)