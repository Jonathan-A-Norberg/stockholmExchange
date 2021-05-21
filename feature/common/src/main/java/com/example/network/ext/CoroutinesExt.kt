package com.example.network.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


fun <T> mutableSharedFlowReplay(): MutableSharedFlow<T> {
    return MutableSharedFlow(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
}
fun <T> mutableSharedFlowNoBuffer(): MutableSharedFlow<T> {
    return MutableSharedFlow(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
}

fun <T> LifecycleOwner.collectFlow(flow: Flow<T>, observer: (T) -> Unit) =
    collectFlowWhenStarted(flow, observer)

fun <T> LifecycleOwner.collectFlowWhenStarted(flow: Flow<T>, observer: (T) -> Unit) {
    lifecycleScope.launchWhenStarted {
        flow.collect {
            coroutineContext.ensureActive()
            observer(it)
        }
    }
}
fun <T> ViewModel.collectFlow(flow: Flow<T>, observer: (T) -> Unit) =
    collectFlowWhenStarted(flow, observer)

fun <T> ViewModel.collectFlowWhenStarted(flow: Flow<T>, observer: (T) -> Unit) {
    viewModelScope.launch {
        flow.collect {
            coroutineContext.ensureActive()
            observer(it)
        }
    }
}
