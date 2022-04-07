package com.example.network

import com.example.common.utils.Resource
import com.example.network.data.RemotePokemonDetailsData
import com.example.network.data.RemotePokemonListData
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): RemotePokemonListData

    @GET
    suspend fun getPokemonListUrl(
        @Url next: String,
    ): RemotePokemonListData
    @GET
    suspend fun getPokemonDetails(
        @Url url: String,
    ): RemotePokemonDetailsData

}

class PokemonApiService(retrofit: Retrofit) {

    private val api: PokemonApi = retrofit.create()

    suspend fun getPokemonList(next: String?): Resource<RemotePokemonListData> {
        return wrapApiCallToResource {
            if (next == null) {
                api.getPokemonList(
                    offset = 0,
                    limit = 25
                )

            } else {
                api.getPokemonListUrl(next = next)
            }
        }
    }

    suspend fun getPokemonDetails(url: String): Resource<RemotePokemonDetailsData> {
        return wrapApiCallToResource {
            api.getPokemonDetails(url = url)
        }
    }
}