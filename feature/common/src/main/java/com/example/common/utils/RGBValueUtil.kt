package com.example.common.utils

import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import kotlin.math.pow
import kotlin.math.sqrt

fun getRGBValue(color: String): RGBValue {
    val parseColor = Color.parseColor(color)
    return RGBValue(parseColor.red, parseColor.green, parseColor.blue)
}

fun calcRGBDistance(rgbValue: RGBValue, rgbValueList: List<RGBValue>): Double {
    if(rgbValueList.isEmpty()) return 0.0
    var currentDistance = Double.MAX_VALUE

    rgbValueList.forEach {
        val redDistance = (rgbValue.red - it.red).toDouble().pow(2.0)
        val greenDistance = (rgbValue.green - it.green).toDouble().pow(2.0)
        val blueDistance = (rgbValue.blue - it.blue).toDouble().pow(2.0)
        val sum = redDistance + greenDistance + blueDistance
        val sqrt = sqrt(sum)
        if (currentDistance > sqrt) {
            currentDistance = sqrt
        }
    }
    return currentDistance
}

data class RGBValue(
    val red: Int = 0,
    val green: Int = 0,
    val blue: Int = 0
)