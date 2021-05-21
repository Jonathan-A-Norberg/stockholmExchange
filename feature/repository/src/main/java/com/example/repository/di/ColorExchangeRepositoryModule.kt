package com.example.repository.di

import com.example.network.ColorExchangeApiService
import com.example.repository.ColorExchangeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object ColorExchangeRepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideColorExchangeRepository(apiService: ColorExchangeApiService): ColorExchangeRepository {
        return ColorExchangeRepository(apiService)
    }


}