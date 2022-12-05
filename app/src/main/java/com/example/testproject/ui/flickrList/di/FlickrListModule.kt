package com.example.testproject.ui.flickrList.di

import com.example.repository.FlickrRepository
import com.example.testproject.ui.flickrList.FlickrListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object FlickrListModule {

    @Provides
    @ActivityRetainedScoped
    fun provideFlickrViewModel(flickrRepository: FlickrRepository): FlickrListViewModel {
        return FlickrListViewModel(flickrRepository)
    }
}
