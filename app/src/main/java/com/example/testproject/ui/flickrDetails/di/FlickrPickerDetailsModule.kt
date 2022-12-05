package com.example.testproject.ui.flickrDetails.di

import com.example.testproject.ui.flickrDetails.FlickrDetailsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object FlickrPickerDetailsModule {

    @Provides
    @ActivityRetainedScoped
    fun provideFlickrDetailsViewModel(): FlickrDetailsViewModel {
        return FlickrDetailsViewModel()
    }
}
