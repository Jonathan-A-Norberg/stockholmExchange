package com.example.network.di


import com.example.network.PokemonApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit



@Module
@InstallIn(ActivityRetainedComponent::class)
object PokemonNetworkModule {

    private val json = Json { ignoreUnknownKeys = true }

    @ExperimentalSerializationApi
    @Provides
    @ActivityRetainedScoped
    fun provideRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl("https://pokeapi.co/api/v2/")
            .build()
    }

    @Provides
    @ActivityRetainedScoped
    fun providePokemonApiService(retrofit: Retrofit): PokemonApiService {
        return PokemonApiService(retrofit)
    }


}