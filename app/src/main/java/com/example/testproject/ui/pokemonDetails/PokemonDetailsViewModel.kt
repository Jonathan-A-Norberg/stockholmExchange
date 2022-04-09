package com.example.testproject.ui.pokemonDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.Error
import com.example.common.utils.Resource
import com.example.repository.PokemonRepository
import com.example.repository.data.PokemonDetailsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private var _state = MutableStateFlow(PokemonDetailsState())
    val state: StateFlow<PokemonDetailsState> = _state

    fun insertUrl(url: String) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    url = url,
                    loading = true,
                    error = null,
                    pokemon = null,
                )
            )
            when (val res = pokemonRepository.getPokemonDetails(url = url)) {
                is Resource.Error -> {
                    _state.emit(
                        _state.value.copy(
                            error = res.error,
                            loading = false
                        )
                    )
                }
                is Resource.Success -> {

                    _state.emit(
                        _state.value.copy(
                            pokemon = res.data,
                            loading = false
                        )
                    )
                }
            }
        }
    }

    fun onTryAgainClicked() {
        insertUrl(url = _state.value.url!!)
    }
}

data class PokemonDetailsState(
    val pokemon: PokemonDetailsData? = null,
    val loading: Boolean = true,
    val error: Error? = null,
    val url: String? = null,
)