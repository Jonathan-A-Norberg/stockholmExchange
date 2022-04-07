package com.example.repository.extensions

import com.example.network.data.RemotePokemonDetailsData
import com.example.network.data.RemotePokemonListData
import com.example.network.data.RemotePokemonListItemData
import com.example.repository.data.PokemonData
import com.example.repository.data.PokemonDataItem
import com.example.repository.data.PokemonDataItemStats


fun RemotePokemonListData.toPokemonData(detailsData: List<RemotePokemonDetailsData>): PokemonData {
    return PokemonData(
        url = next,
        list = results.mapNotNull { pokemon ->
            val firstDetailsData = detailsData.firstOrNull { it.name == pokemon.name }
            if (firstDetailsData == null) {
                null
            } else {
                pokemon.toPokemonDataItem(detailsData = firstDetailsData)
            }
        }
    )
}

fun RemotePokemonListItemData.toPokemonDataItem(detailsData: RemotePokemonDetailsData): PokemonDataItem {
    return PokemonDataItem(
        id = detailsData.id.toString(),
        name = name.replaceFirstChar { it.uppercaseChar() },
        image = detailsData.sprites.other.dream_world.front_default
            ?: detailsData.sprites.other.home.front_default ?: detailsData.sprites.front_default
            ?: detailsData.sprites.front_shiny ?: detailsData.sprites.other.artwork.front_default,
        stats = detailsData.stats.map {
            PokemonDataItemStats(
                name = it.stat.name.replaceFirstChar { it.uppercaseChar() },
                stat = it.base_stat
            )
        },
        types = detailsData.types.map { it.type.name.replaceFirstChar { it.uppercaseChar() } },
        height = (detailsData.height.toFloat() / 10 ).toString(),
        weight = (detailsData.weight.toFloat() / 10 ).toString()
    )
}


