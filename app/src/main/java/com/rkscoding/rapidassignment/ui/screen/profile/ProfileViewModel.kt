package com.rkscoding.rapidassignment.ui.screen.profile

import androidx.lifecycle.viewModelScope
import com.rkscoding.rapidassignment.data.common.BaseViewModel
import com.rkscoding.rapidassignment.data.common.UiEvent
import com.rkscoding.rapidassignment.data.common.UiState
import com.rkscoding.rapidassignment.data.remote.dto.response.UserProfileResponse
import com.rkscoding.rapidassignment.data.remote.reposotory.UserProfileRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val repository: UserProfileRepository) : BaseViewModel() {

    private val _profileUiState = MutableStateFlow<UiState<UserProfileResponse>>(UiState.Idle)
    val profileUiState = _profileUiState.asStateFlow()

    private val _navEvent = MutableSharedFlow<ProfileNavigationEvent>(replay = 1)
    val navEvent = _navEvent.asSharedFlow()

    fun loadProfile(){
        viewModelScope.launch {
            _profileUiState.value = UiState.Loading
            try {
                val result = repository.fetchUserProfile()
                _profileUiState.value = UiState.Success(result.data!!)
                sendUiEvent(event = UiEvent.ShowSnackBar(result.message), scoped = this)
            }catch (e : Exception) {
                _profileUiState.value = UiState.Error(e.message ?: "Unknown Error")
                sendUiEvent(event = UiEvent.ShowSnackBar(e.message ?: "Unknown Error"), scoped = this)
            }
        }
    }

    fun updateProfile(){

    }




    sealed class ProfileNavigationEvent {
        object NavigateToProfileUpdate : ProfileNavigationEvent()
        object NavigateToLogin : ProfileNavigationEvent()
    }
}