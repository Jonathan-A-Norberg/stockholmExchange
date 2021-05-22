package com.example.stockholmcolorexchange.ui.colorPickerList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.utils.Error
import com.example.network.utils.Resource
import com.example.network.utils.calcRGBDistance
import com.example.network.utils.getRGBValue
import com.example.repository.ColorExchangeRepository
import com.example.repository.data.ColorPickerData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

@HiltViewModel
class ColorPickerViewModel @Inject constructor(
    private val context: Context,
    private val colorExchangeRepository: ColorExchangeRepository
) : ViewModel() {

    private var _state = MutableStateFlow(ColorPickerState())
    val state: StateFlow<ColorPickerState> = _state

    init {
        Timber.e("getColor")
        getColorPickerDataList()
    }

    private fun getColorPickerDataList() {
        viewModelScope.launch {
            when (val res = colorExchangeRepository.getColorPickerList()) {
                is Resource.Success -> {
                    val rgbValuePair = res.data.map { Pair(it.colorHexName, getRGBValue(it.colorHexName)) }

                    _state.emit(
                        state.value.copy(
                            loading = false,
                            colorPickerList = res.data.map { colorPickerData ->
                                val allOtherRGBs = rgbValuePair.filterNot { it.first == colorPickerData.colorHexName }.map { it.second }
                                colorPickerData.copy(
                                    tradesToday = calcRGBDistance(rgbValuePair.first { it.first == colorPickerData.colorHexName }.second, allOtherRGBs).toInt()
                                )
                            }.sortedBy { it.tradesToday }
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
        getColorPickerDataList()
    }
}

data class ColorPickerState(
    val colorPickerList: List<ColorPickerData> = emptyList(),
    val error: Error? = null,
    val loading: Boolean = true
)

