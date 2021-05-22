package com.example.stockholmcolorexchange.ui.colorPickerList.di

import com.example.repository.ColorExchangeRepository
import com.example.stockholmcolorexchange.ui.colorPickerList.ColorPickerViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object ColorPickerModule {

    @Provides
    @ActivityRetainedScoped
    fun provideColorPickerViewModel(colorExchangeRepository: ColorExchangeRepository): ColorPickerViewModel {
        return ColorPickerViewModel(colorExchangeRepository)
    }
}
