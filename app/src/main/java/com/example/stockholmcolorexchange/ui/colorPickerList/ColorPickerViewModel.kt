package com.example.stockholmcolorexchange.ui.colorPickerList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.utils.Error
import com.example.network.utils.Resource
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

    private var _state = MutableStateFlow<ColorPickerState>(ColorPickerState())
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
                                    tradesToday = calcRGBDistance(rgbValuePair.first { it.first == colorPickerData.colorHexName }.second, allOtherRGBs)
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

    private fun getRGBValue(color: String): RGBValue {
        val red = java.lang.Long.parseLong(color.slice(1..2), 16)
        val green = java.lang.Long.parseLong(color.slice(3..4), 16)
        val blue = java.lang.Long.parseLong(color.slice(5..6), 16)

        return RGBValue(red, green, blue)
    }

    private fun calcRGBDistance(rgbValue: RGBValue, rgbValueList: List<RGBValue>): Long {
        var currentDistance = Long.MAX_VALUE

        rgbValueList.forEach {
            val redDistance = (rgbValue.red - it.red).toDouble().pow(2.0)
            val greenDistance = (rgbValue.green - it.green).toDouble().pow(2.0)
            val blueDistance = (rgbValue.blue - it.blue).toDouble().pow(2.0)
            val sum = redDistance + greenDistance + blueDistance
            val sqrt = sqrt(sum)
            if (currentDistance > sqrt) {
                currentDistance = sqrt.toLong()
            }
        }
        return currentDistance
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

data class RGBValue(
    val red: Long = 0,
    val green: Long = 0,
    val blue: Long = 0
)