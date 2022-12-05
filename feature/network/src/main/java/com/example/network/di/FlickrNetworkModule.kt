package com.example.network.di


import com.example.network.FlickrApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Credentials
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

@Module
@InstallIn(ActivityRetainedComponent::class)
object FlickrNetworkModule {

    private val json = Json { ignoreUnknownKeys = true }

    @ExperimentalSerializationApi
    @Provides
    @ActivityRetainedScoped
    fun provideRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl("https://www.flickr.com/")
            .build()
    }

    @Provides
    @ActivityRetainedScoped
    fun provideFlickrApiService(retrofit: Retrofit): FlickrApiService {
        return FlickrApiService(retrofit)
    }

}
