package com.rkscoding.rapidassignment.ui.viewModels

import androidx.lifecycle.viewModelScope
import com.rkscoding.rapidassignment.data.common.BaseViewModel
import com.rkscoding.rapidassignment.data.common.UiState
import com.rkscoding.rapidassignment.data.remote.dto.response.UserProfileResponse
import com.rkscoding.rapidassignment.data.remote.reposotory.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val profileRepository: UserProfileRepository
) : BaseViewModel() {

    private val _profileUiState = MutableStateFlow<UiState<UserProfileResponse>>(UiState.Idle)
    val profileUiState = _profileUiState.asStateFlow()

    init {
        fetchUserProfile()
    }

    fun fetchUserProfile() {
        viewModelScope.launch {
            _profileUiState.value = UiState.Loading
            try {
                val result = profileRepository.fetchUserProfile()
                if (result.statusCode == 200 && result.data != null){
                    _profileUiState.value = UiState.Success(result.data)
                }else{
                    _profileUiState.value = UiState.Error(result.message)
                }
            }catch (e : Exception){
                _profileUiState.value = UiState.Error(e.message ?: "An error occurred")
            }
        }
    }
}