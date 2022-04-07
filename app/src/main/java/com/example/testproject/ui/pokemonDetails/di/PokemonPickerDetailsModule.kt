package com.example.testproject.ui.pokemonDetails.di

import com.example.testproject.ui.pokemonDetails.PokemonDetailsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object PokemonPickerDetailsModule {

    @Provides
    @ActivityRetainedScoped
    fun providePokemonDetailsViewModel(): PokemonDetailsViewModel {
        return PokemonDetailsViewModel()
    }
}
