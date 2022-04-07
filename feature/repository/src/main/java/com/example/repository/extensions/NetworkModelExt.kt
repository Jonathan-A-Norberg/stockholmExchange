package com.example.repository.extensions

import com.example.network.data.RemotePokemonData
import com.example.network.data.RemotePokemonDataItem
import com.example.repository.data.PokemonData
import com.example.repository.data.PokemonDataItem


fun RemotePokemonData.toPokemonData(): PokemonData {
    return PokemonData(
        url = next,
        list = results.map { it.toPokemonDataItem() }
    )
}

fun RemotePokemonDataItem.toPokemonDataItem(): PokemonDataItem {
    return PokemonDataItem(
        url = url,
        name = name
    )
}


