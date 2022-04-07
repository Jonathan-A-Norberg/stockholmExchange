package com.example.network

import com.example.common.utils.Resource
import com.example.network.data.RemotePokemonData
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface SCEApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): RemotePokemonData

    @GET
    suspend fun getPokemonListUrl(
        @Url next: String,
    ): RemotePokemonData

}

class PokemonApiService(retrofit: Retrofit) {

    private val api: SCEApi = retrofit.create()

    suspend fun getPokemonList(next: String?): Resource<RemotePokemonData> {
        return wrapApiCallToResource {
            if (next == null) {
                api.getPokemonList(
                    offset = 0,
                    limit = 100
                )

            } else {
                api.getPokemonListUrl(next = next)
            }
        }
    }
}