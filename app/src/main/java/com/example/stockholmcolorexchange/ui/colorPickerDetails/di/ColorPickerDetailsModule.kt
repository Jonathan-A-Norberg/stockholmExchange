package com.example.stockholmcolorexchange.ui.colorPickerDetails.di

import android.content.Context
import com.example.repository.ColorExchangeRepository
import com.example.stockholmcolorexchange.ui.colorPickerDetails.ColorPickerDetailsViewModel
import com.example.stockholmcolorexchange.ui.colorPickerList.ColorPickerViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object ColorPickerDetailsModule {

    @Provides
    @ActivityRetainedScoped
    fun provideColorPickerDetailsViewModel(): ColorPickerDetailsViewModel {
        return ColorPickerDetailsViewModel()
    }
}
