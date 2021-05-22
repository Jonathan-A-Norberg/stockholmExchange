package com.example.network

import com.example.network.data.RemoteColorPickerData
import com.example.network.data.RemoteColorPickerHeaderData
import com.example.network.utils.Resource
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET


interface SCEApi {

    @GET("list.json")
    suspend fun getColorPickerList(): RemoteColorPickerHeaderData

}

class ColorExchangeApiService(retrofit: Retrofit) {

    private val api: SCEApi = retrofit.create()

    suspend fun getColorPickerList(): Resource<List<RemoteColorPickerData>> {
        return wrapApiCallToResource {
            api.getColorPickerList().colors
        }
    }
}