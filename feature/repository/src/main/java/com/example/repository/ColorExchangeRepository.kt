package com.example.repository

import com.example.network.ColorExchangeApiService
import com.example.network.utils.Resource
import com.example.repository.data.ColorPickerData
import com.example.repository.extensions.toColorPickerData
import javax.inject.Inject

class ColorExchangeRepository @Inject constructor(
    private val apiService: ColorExchangeApiService
){
    suspend fun getColorPickerList(): Resource<List<ColorPickerData>> {
        return when (val res = apiService.getColorPickerList()) {
            is Resource.Error -> Resource.Error(res.error)
            is Resource.Success -> Resource.Success(res.data.map{it.toColorPickerData()}.sortedBy { it.colorHexName })
        }
    }
}