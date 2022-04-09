package com.example.repository.extensions

import com.example.network.data.RemotePokemonDetailsData
import com.example.network.data.RemotePokemonListData
import com.example.network.data.RemotePokemonListItemData
import com.example.repository.data.PokemonData
import com.example.repository.data.PokemonDataItem
import com.example.repository.data.PokemonDetailsData
import com.example.repository.data.PokemonStats


fun RemotePokemonListData.toPokemonData(): PokemonData {
    return PokemonData(
        url = next,
        list = results.map { pokemon ->
            pokemon.toPokemonDataItem()
        }
    )
}

fun RemotePokemonListItemData.toPokemonDataItem(): PokemonDataItem {
    return PokemonDataItem(
        id = null,
        name = name.replaceFirstChar { it.uppercaseChar() },
        url = url,
        image = null,
    )
}


fun RemotePokemonDetailsData.toPokemonDataItem(url: String): PokemonDataItem {
    return PokemonDataItem(
        id = id.toString(),
        name = name.replaceFirstChar { it.uppercaseChar() },
        image = sprites.other.dream_world.front_default
            ?: sprites.other.home.front_default ?: sprites.front_default
            ?: sprites.front_shiny ?: sprites.other.artwork.front_default,
        url = url
    )
}
fun RemotePokemonDetailsData.toPokemonDetailsData(): PokemonDetailsData {
    return PokemonDetailsData(
        id = id.toString(),
        name = name.replaceFirstChar { it.uppercaseChar() },
        image = sprites.other.dream_world.front_default
            ?: sprites.other.home.front_default ?: sprites.front_default
            ?: sprites.front_shiny ?: sprites.other.artwork.front_default,
        stats = stats.map {
            PokemonStats(
                name = it.stat.name.replaceFirstChar { it.uppercaseChar() },
                stat = it.base_stat
            )
        },
        types = types.map { it.type.name.replaceFirstChar { it.uppercaseChar() } },
        height = (height.toFloat() / 10).toString(),
        weight = (weight.toFloat() / 10).toString()
    )
}


