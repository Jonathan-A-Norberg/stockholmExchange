package com.example.testproject.ui.pokemonList.di

import com.example.repository.PokemonRepository
import com.example.testproject.ui.pokemonList.PokemonListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object PokemonListModule {

    @Provides
    @ActivityRetainedScoped
    fun providePokemonViewModel(pokemonRepository: PokemonRepository): PokemonListViewModel {
        return PokemonListViewModel(pokemonRepository)
    }
}
