package com.rkscoding.rapidassignment.data.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    protected fun sendUiEvent(event: UiEvent, scoped: CoroutineScope){
        scoped.launch {
            _uiEvent.send(event)
        }
    }

    private val _countdown = MutableStateFlow(0)
    val countdown: StateFlow<Int> = _countdown

    private var job: Job? = null

    fun startCountdown(seconds: Int = 60) {
        job?.cancel()
        job = viewModelScope.launch {
            for (i in seconds downTo 0) {
                _countdown.value = i
                delay(1000)
            }
        }
    }
}