package com.example.repository.extensions

import android.graphics.Color
import com.example.network.data.RemoteColorPickerData
import com.example.repository.data.ColorPickerData


fun RemoteColorPickerData.toColorPickerData(): ColorPickerData {
    return ColorPickerData(
        name = name,
        color = Color.parseColor(color),
        colorName = color,
        numTrades = numTrades.toInt(),
        risk = risk.toInt()

    )
}
