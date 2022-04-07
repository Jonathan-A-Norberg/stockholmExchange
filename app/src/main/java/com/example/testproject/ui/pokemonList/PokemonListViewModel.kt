package com.example.testproject.ui.pokemonList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.Error
import com.example.common.utils.Resource
import com.example.repository.PokemonRepository
import com.example.repository.data.PokemonDataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private var _state = MutableStateFlow(PokemonState())
    val state: StateFlow<PokemonState> = _state

    init {
        getPokemonDataList()
    }

    private fun getPokemonDataList() {
        viewModelScope.launch {
            when (val res = pokemonRepository.getPokemonList()) {
                is Resource.Success -> {
                    _state.emit(
                        state.value.copy(
                            loading = false,
                            nextUrl = res.data.url,
                            pokemonList = res.data.list
                        )
                    )
                }
                is Resource.Error -> _state.emit(
                    state.value.copy(
                        loading = false,
                        error = res.error
                    )
                )
            }
        }
    }

    fun tryAgainClicked() {
        _state.tryEmit(
            state.value.copy(
                loading = true,
                error = null,
            )
        )
        getPokemonDataList()
    }
}

data class PokemonState(
    val pokemonList: List<PokemonDataItem> = emptyList(),
    val nextUrl: String? = null,
    val error: Error? = null,
    val loading: Boolean = true
)

