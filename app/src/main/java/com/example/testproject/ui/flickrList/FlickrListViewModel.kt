package com.example.testproject.ui.flickrList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.Error
import com.example.common.utils.Resource
import com.example.repository.FlickrRepository
import com.example.repository.data.FlickrData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlickrListViewModel @Inject constructor(
    private val flickrRepository: FlickrRepository
) : ViewModel() {

    private val _flickrData = MutableStateFlow<FlickrData?>(null)
    private val _isLoading = MutableStateFlow<Boolean>(true)
    private val _isLoadingPaging = MutableStateFlow<Boolean>(false)
    private val _error = MutableStateFlow<Error?>(null)
    private val _searchText = MutableStateFlow<String>("")

    val viewStateFlow: StateFlow<FlickrViewState> = combine(
        _flickrData,
        _isLoading,
        _isLoadingPaging,
        _error,
        _searchText,
    ) { flickrData, isLoading, isLoadingPaging, error, searchText ->
        FlickrViewState(
            flickrData = flickrData,
            isLoading = isLoading,
            isLoadingPaging = isLoadingPaging,
            error = error,
            searchText = searchText,
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, FlickrViewState())

    init {
        getFlickrDataList()
    }

    private fun getFlickrDataList(paging: Boolean = false) {
        viewModelScope.launch {
            _isLoading.emit(!paging)
            _isLoadingPaging.emit(paging)
            _error.emit(null)

            val flickrData = _flickrData.value
            val page = flickrData?.page?.plus(1)?.takeIf { it <= flickrData.pages }
            val res = if (_searchText.value.isEmpty()) {
                flickrRepository.getFlickrListRecent(page = page)
            } else {
                flickrRepository.getFlickrList(
                    searchText = _searchText.value,
                    page = page
                )
            }

            when (res) {
                is Resource.Success -> {
                    _isLoading.emit(false)
                    _isLoadingPaging.emit(false)
                    _error.emit(null)
                    if (paging) {
                        _flickrData.emit(
                            res.data.copy(photoList = flickrData?.photoList?.plus(res.data.photoList) ?: emptyList())
                        )

                    } else {
                        _flickrData.emit(res.data)

                    }
                }
                is Resource.Error -> {
                    _isLoading.emit(false)
                    _isLoadingPaging.emit(false)
                    _error.emit(res.error)
                }
            }
        }
    }

    fun onTryAgainClicked() {
        getFlickrDataList()
    }

    fun onScrolledToBottom() {
        if (!_isLoadingPaging.value && !_isLoading.value) {
            if (_flickrData.value?.photoList?.isNotEmpty() == true) {
                getFlickrDataList(paging = true)
            }

        }
    }

    fun onSearchTextUpdated(searchText: String) {
        _searchText.tryEmit(searchText)
        getFlickrDataList()
    }
}

data class FlickrViewState(
    val flickrData: FlickrData? = null,
    val searchText: String = "",
    val error: Error? = null,
    val isLoading: Boolean = true,
    val isLoadingPaging: Boolean = true,
)

