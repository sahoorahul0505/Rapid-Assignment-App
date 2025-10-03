package com.rkscoding.rapidassignment.ui.screen.reset_password

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.rkscoding.rapidassignment.data.common.BaseViewModel
import com.rkscoding.rapidassignment.data.common.UiEvent
import com.rkscoding.rapidassignment.data.common.UiState
import com.rkscoding.rapidassignment.data.remote.dto.request.PasswordResetOtpRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.PasswordResetRequest
import com.rkscoding.rapidassignment.data.remote.reposotory.UsersAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(private val repository: UsersAuthRepository) : BaseViewModel() {

    private val _formState = MutableStateFlow(ResetPasswordFormState())
    val formState = _formState.asStateFlow()

    private val _resetUiState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val resetUiState = _resetUiState.asStateFlow()

    private val _sendOtpState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val sendOtpState = _sendOtpState.asStateFlow()

    private val _navEvent = MutableSharedFlow<ResetPasswordNavEvents>()
    val navEvent = _navEvent.asSharedFlow()

    fun onFormChange(copy: ResetPasswordFormState) {
        viewModelScope.launch {
            _formState.value = copy
        }
    }

    fun onBackToLoginClicked(){
        viewModelScope.launch {
            _navEvent.emit(ResetPasswordNavEvents.NavigateToLogin)
        }
    }

    fun sendForgotPasswordOtp() {
        val currentForm = _formState.value
        viewModelScope.launch {
            val otpValidationFormError = validateOtpForm(currentForm)
            if (otpValidationFormError != null) {
                sendUiEvent(event = UiEvent.ShowSnackBar(otpValidationFormError), scoped = this)
                return@launch
            }

            _sendOtpState.value = UiState.Loading
            try {
                val result = repository.sendOtpForResetPassword(
                    PasswordResetOtpRequest(
                        email = currentForm.email
                    )
                )
                _sendOtpState.value = UiState.Success(result.message)
                sendUiEvent(event = UiEvent.ShowSnackBar(result.message), scoped = this)
                startCountdown()
            } catch (e: Exception) {
                _sendOtpState.value = UiState.Error(e.message ?: "Unknown Error")
                sendUiEvent(event = UiEvent.ShowSnackBar(e.localizedMessage ?: "Unknown Error"), scoped = this)
            }
        }
    }

    fun resetPassword() {
        val currentForm = _formState.value
        viewModelScope.launch {
            val resetPasswordValidationFormError = validateResetPasswordForm(currentForm)
            if (resetPasswordValidationFormError != null) {
                sendUiEvent(event = UiEvent.ShowSnackBar(resetPasswordValidationFormError), scoped = this)
                return@launch
            }

            _resetUiState.value = UiState.Loading
            try {
                val result = repository.resetPassword(
                    PasswordResetRequest(
                        email = currentForm.email,
                        newPassword = currentForm.password,
                        otp = currentForm.otp
                    )
                )
                _resetUiState.value = UiState.Success(result.message)
                sendUiEvent(event = UiEvent.ShowSnackBar(result.message), scoped = this)
                _navEvent.emit(ResetPasswordNavEvents.NavigateToLogin)
            } catch (e: Exception) {
                _resetUiState.value = UiState.Error(e.message ?: "Unknown Error")
                sendUiEvent(event = UiEvent.ShowSnackBar(e.localizedMessage ?: "Unknown Error"), scoped = this)
            }
        }
    }


    sealed class ResetPasswordNavEvents {
        object NavigateToLogin : ResetPasswordNavEvents()
    }

    private fun validateResetPasswordForm(form: ResetPasswordFormState): String? {
        return when {
            Patterns.EMAIL_ADDRESS.matcher(form.email).matches().not() -> {
                "Please enter valid Email address."
            }

            form.email.isBlank() || form.password.isBlank() || form.confirmPassword.isBlank() -> {
                "Please fill all fields"
            }

            form.password != form.confirmPassword -> {
                "New password and confirm password must match"
            }

            form.otp.isBlank() -> {
                "Please enter OTP"
            }

            else -> null
        }
    }

    private fun validateOtpForm(form: ResetPasswordFormState): String? {
        return when {
            Patterns.EMAIL_ADDRESS.matcher(form.email).matches().not() -> {
                "Please enter valid Email address"
            }

            else -> null
        }
    }

    data class ResetPasswordFormState(
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val otp: String = ""
    )
}