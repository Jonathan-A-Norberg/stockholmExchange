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
            if (state.value.nextUrl != null) {
                _state.emit(state.value.copy(loadingPaging = true))
            } else {
                _state.emit(state.value.copy(loading = true))
            }


            when (val res = pokemonRepository.getPokemonList(next = state.value.nextUrl)) {
                is Resource.Success -> {
                    _state.emit(
                        state.value.copy(
                            loading = false,
                            loadingPaging = false,
                            nextUrl = res.data.url,
                            pokemonList = state.value.pokemonList + res.data.list
                        )
                    )
                    getPokemonDetailsList(res.data.list)
                }
                is Resource.Error -> _state.emit(
                    state.value.copy(
                        loading = false,
                        loadingPaging = false,
                        error = res.error
                    )
                )
            }
        }
    }

    private fun getPokemonDetailsList(list: List<PokemonDataItem>) {
        list.forEach { pokemon ->
            viewModelScope.launch {
                when(val res = pokemonRepository.getPokemonListDetails(pokemon.url)){
                    is Resource.Error -> {}
                    is Resource.Success -> {
                        val pokemonList = _state.value.pokemonList.toMutableList()
                        pokemonList[pokemonList.indexOf(pokemon)] = res.data
                        _state.emit(_state.value.copy(
                            pokemonList = pokemonList.toList()
                        ))
                    }
                }
            }
        }
    }

    fun onTryAgainClicked() {
        _state.tryEmit(
            state.value.copy(
                loading = true,
                error = null,
            )
        )
        getPokemonDataList()
    }

    fun onScrolledToBottom() {
        if (!state.value.loadingPaging && !state.value.loading) {
            if (state.value.pokemonList.isNotEmpty() && state.value.nextUrl != null) {
                getPokemonDataList()
            }

        }
    }
}

data class PokemonState(
    val pokemonList: List<PokemonDataItem> = emptyList(),
    val nextUrl: String? = null,
    val error: Error? = null,
    val loading: Boolean = true,
    val loadingPaging: Boolean = false
)

