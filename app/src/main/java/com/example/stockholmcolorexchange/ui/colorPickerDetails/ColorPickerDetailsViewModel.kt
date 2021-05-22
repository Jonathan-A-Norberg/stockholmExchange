package com.example.stockholmcolorexchange.ui.colorPickerDetails

import androidx.lifecycle.ViewModel
import com.example.repository.data.ColorPickerData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ColorPickerDetailsViewModel @Inject constructor(
) : ViewModel() {

    private var _state = MutableStateFlow(ColorPickerDetailsState())
    val state: StateFlow<ColorPickerDetailsState> = _state

    fun insertColorPickerData(colorPickerData: ColorPickerData) {
        _state.tryEmit(
            state.value.copy(
                colorPickerData = colorPickerData,
            )
        )
    }
}

data class ColorPickerDetailsState(
    val colorPickerData: ColorPickerData?  = null,
)