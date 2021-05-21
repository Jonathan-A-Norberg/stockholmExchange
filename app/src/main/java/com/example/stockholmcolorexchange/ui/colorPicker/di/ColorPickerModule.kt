package com.example.stockholmcolorexchange.ui.colorPicker.di

import android.content.Context
import com.example.repository.ColorExchangeRepository
import com.example.stockholmcolorexchange.ui.colorPicker.ColorPickerViewModel
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
    fun provideColorPickerViewModel(context: Context, colorExchangeRepository: ColorExchangeRepository): ColorPickerViewModel {
        return ColorPickerViewModel(context, colorExchangeRepository)
    }
}
