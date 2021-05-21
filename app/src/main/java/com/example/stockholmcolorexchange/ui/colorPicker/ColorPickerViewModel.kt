package com.example.stockholmcolorexchange.ui.colorPicker

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
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
                is Resource.Success -> _state.emit(
                    state.value.copy(
                        colorPickerList = res.data
                    )
                )
                is Resource.Error -> _state.emit(
                    state.value.copy(
                        error = res.error
                    )
                )
            }
        }
    }

    fun onColorPickerClicked(colorPickerData: ColorPickerData) {

    }
}

data class ColorPickerState(
    val colorPickerList: List<ColorPickerData> = emptyList(),
    val error: Error? = null
)