package com.example.repository.di

import com.example.network.PokemonApiService
import com.example.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object PokemonRepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun providePokemonRepository(apiService: PokemonApiService): PokemonRepository {
        return PokemonRepository(apiService)
    }


}