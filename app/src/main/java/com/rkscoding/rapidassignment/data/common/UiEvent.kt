package com.rkscoding.rapidassignment.data.common

sealed class UiEvent {
    data class ShowSnackBar(val message : String) : UiEvent()
    object NavigationBack : UiEvent()
}