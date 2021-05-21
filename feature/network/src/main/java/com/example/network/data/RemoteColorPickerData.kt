package com.example.network.data

import kotlinx.serialization.Serializable

@Serializable
data class RemoteColorPickerHeaderData(
    val colors: List<RemoteColorPickerData>,
)
@Serializable
data class RemoteColorPickerData(
    val name: String,
    val color: String,
    val numTrades: String,
    val risk: String
)