package com.example.testproject.ui.pokemonDetails

import androidx.lifecycle.ViewModel
import com.example.repository.data.PokemonData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
) : ViewModel() {

    private var _state = MutableStateFlow(PokemonDetailsState())
    val state: StateFlow<PokemonDetailsState> = _state

    fun insertPokemonData(pokemonData: PokemonData) {
        _state.tryEmit(
            state.value.copy(
                pokemonData = pokemonData,
            )
        )
    }
}

data class PokemonDetailsState(
    val pokemonData: PokemonData?  = null,
)