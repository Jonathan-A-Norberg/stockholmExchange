package com.example.repository

import com.example.network.PokemonApiService
import com.example.common.utils.Resource
import com.example.repository.data.PokemonData
import com.example.repository.extensions.toPokemonData
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val apiService: PokemonApiService
){
    suspend fun getPokemonList(): Resource<PokemonData> {
        return when (val res = apiService.getPokemonList(next = null)) {
            is Resource.Error -> Resource.Error(res.error)
            is Resource.Success -> Resource.Success(res.data.toPokemonData())
        }
    }
}