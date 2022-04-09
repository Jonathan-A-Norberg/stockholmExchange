package com.example.repository

import com.example.common.utils.Resource
import com.example.network.PokemonApiService
import com.example.network.data.RemotePokemonDetailsData
import com.example.repository.data.PokemonData
import com.example.repository.data.PokemonDataItem
import com.example.repository.data.PokemonDetailsData
import com.example.repository.extensions.toPokemonData
import com.example.repository.extensions.toPokemonDataItem
import com.example.repository.extensions.toPokemonDetailsData
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import timber.log.Timber
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val apiService: PokemonApiService
) {
    suspend fun getPokemonList(next: String?): Resource<PokemonData> = coroutineScope {
        return@coroutineScope when (val res = apiService.getPokemonList(next = next)) {
            is Resource.Error -> Resource.Error(res.error)
            is Resource.Success -> {
                Resource.Success(res.data.toPokemonData())
            }
        }
    }

    suspend fun getPokemonListDetails(url: String): Resource<PokemonDataItem> = coroutineScope {
        return@coroutineScope when (val res = apiService.getPokemonDetails(url = url)) {
            is Resource.Error -> Resource.Error(res.error)
            is Resource.Success -> {

                Resource.Success(res.data.toPokemonDataItem(url = url))
            }
        }
    }

    suspend fun getPokemonDetails(url: String): Resource<PokemonDetailsData> = coroutineScope {
        return@coroutineScope when (val res = apiService.getPokemonDetails(url = url)) {
            is Resource.Error -> Resource.Error(res.error)
            is Resource.Success -> {

                Resource.Success(res.data.toPokemonDetailsData())
            }
        }
    }
}