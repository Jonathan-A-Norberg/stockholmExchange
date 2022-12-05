package com.example.repository.di

import com.example.network.FlickrApiService
import com.example.repository.FlickrRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object FlickrRepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideFlickrRepository(apiService: FlickrApiService): FlickrRepository {
        return FlickrRepository(apiService)
    }


}