package com.example.repository

import com.example.common.utils.Resource
import com.example.network.PokemonApiService
import com.example.network.data.RemotePokemonDetailsData
import com.example.repository.data.PokemonData
import com.example.repository.extensions.toPokemonData
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

                val result = res.data.results.map { listItem ->
                    async {
                        return@async apiService.getPokemonDetails(url = listItem.url)
                    }
                }
                    .awaitAll()
                    .map {
                       return@map when(it){
                            is Resource.Error -> null
                            is Resource.Success -> it.data
                        }
                    }

                val toPokemonData = res.data.toPokemonData(detailsData = result.filterNotNull())
                Resource.Success(toPokemonData)
            }
        }
    }
}