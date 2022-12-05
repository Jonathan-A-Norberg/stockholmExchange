package com.example.testproject.ui.flickrDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.Error
import com.example.common.utils.Resource
import com.example.repository.FlickrRepository
import com.example.repository.data.FlickrDataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlickrDetailsViewModel @Inject constructor(
    private val flickrRepository: FlickrRepository
) : ViewModel() {

    // This is a different way of doing state handling i like the other way in listView
    private var _state = MutableStateFlow(FlickrDetailsState())
    val state: StateFlow<FlickrDetailsState> = _state

    fun insertUrl(url: String) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    url = url,
                    loading = false,
                    error = null,
                )
            )

        }
    }

    fun onTryAgainClicked() {
        insertUrl(url = _state.value.url!!)
    }
}

data class FlickrDetailsState(
    val loading: Boolean = false,
    val error: Error? = null,
    val url: String? = null,
)