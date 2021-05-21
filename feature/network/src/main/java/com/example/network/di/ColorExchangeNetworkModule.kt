package com.example.network.di

import com.example.network.BuildConfig
import com.example.network.ColorExchangeApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import timber.log.Timber


private const val BASE_URL = BuildConfig.BASE_URL

@Module
@InstallIn(ActivityRetainedComponent::class)
object ColorExchangeNetworkModule {

    @ExperimentalSerializationApi
    @Provides
    @ActivityRetainedScoped
    fun provideRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory(contentType))
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @ActivityRetainedScoped
    fun provideColorExchangeApiService(retrofit: Retrofit): ColorExchangeApiService {
        return ColorExchangeApiService(retrofit)
    }


}