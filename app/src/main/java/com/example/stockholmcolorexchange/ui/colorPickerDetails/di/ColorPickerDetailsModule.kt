package com.example.stockholmcolorexchange.ui.colorPickerDetails.di

import com.example.stockholmcolorexchange.ui.colorPickerDetails.ColorPickerDetailsViewModel
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
