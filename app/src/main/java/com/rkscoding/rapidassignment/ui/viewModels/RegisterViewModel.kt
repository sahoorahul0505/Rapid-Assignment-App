package com.rkscoding.rapidassignment.ui.viewModels

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.rkscoding.rapidassignment.data.common.BaseViewModel
import com.rkscoding.rapidassignment.data.common.UiEvent
import com.rkscoding.rapidassignment.data.common.UiState
import com.rkscoding.rapidassignment.data.remote.dto.request.RegisterOtpRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.UserRegisterRequest
import com.rkscoding.rapidassignment.data.remote.dto.response.SessionResponse
import com.rkscoding.rapidassignment.data.remote.reposotory.UsersAuthRepository
import com.rkscoding.rapidassignment.data.remote.session.SessionToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UsersAuthRepository,
    private val session: SessionToken
) : BaseViewModel() {
    private val _formState = MutableStateFlow(RegisterFormState())
    val formState = _formState.asStateFlow()

    private val _registerUiState = MutableStateFlow<UiState<SessionResponse>>(UiState.Idle)
    val signupUiState = _registerUiState.asStateFlow()

    private val _otpUiState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val otpUiState = _otpUiState.asStateFlow()

    private val _navEvent = MutableSharedFlow<RegisterNavigationEvent>(replay = 1)
    val navEvent = _navEvent.asSharedFlow()


    fun onFormChange(copy: RegisterFormState) {
        _formState.value = copy
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            _navEvent.emit(RegisterNavigationEvent.NavigateToLogin)
        }
    }

    fun sendOtpForSignup() {
        val currentForm = _formState.value

        viewModelScope.launch {
            val otpFormValidationError = validateOtpForm(currentForm)
            if (otpFormValidationError != null) {
                sendUiEvent(event = UiEvent.ShowSnackBar(otpFormValidationError), scoped = this)
                return@launch
            }

            _otpUiState.value = UiState.Loading
            try {
                val result = repository.sendOtpForRegistration(
                    RegisterOtpRequest(
                        email = currentForm.email,
                        name = currentForm.name,
                    )
                )
                _otpUiState.value = UiState.Success(result.message)
                sendUiEvent(event = UiEvent.ShowSnackBar(result.message), scoped = this)
                startCountdown()
            } catch (e: Exception) {
                _otpUiState.value = UiState.Error(e.message ?: "Something went wrong")
                sendUiEvent(event = UiEvent.ShowSnackBar(e.message ?: "Something went wrong"), scoped = this)
            }
        }
    }

    fun signup() {
        val currentForm = _formState.value

        viewModelScope.launch {
            // Validate the form before making the API call
            val validationFormError = validateRegisterForm(currentForm)
            if (validationFormError != null) {
                sendUiEvent(event = UiEvent.ShowSnackBar(validationFormError), scoped = this)
                return@launch
            }


            _registerUiState.value = UiState.Loading
            try {
                val result = repository.register(
                    UserRegisterRequest(
                        email = currentForm.email,
                        password = currentForm.password,
                        name = currentForm.name,
                        rollNumber = currentForm.rollNumber,
                        branch = currentForm.branch,
                        otp = currentForm.otp
                    )
                )
                val token = result.data?.token
                if (token != null) {
                    _registerUiState.value = UiState.Success(result.data)
                    session.storeToken(token)
                    sendUiEvent(event = UiEvent.ShowSnackBar(message = result.message), scoped = this)
                    _navEvent.emit(RegisterNavigationEvent.NavigateToHome)
                } else {
                    _registerUiState.value = UiState.Error("Missing Token from server")
                    _navEvent.emit(RegisterNavigationEvent.NavigateToLogin)
                    sendUiEvent(
                        event = UiEvent.ShowSnackBar(message = "Missing Token from server. \n Please try to Login."),
                        scoped = this
                    )
                    return@launch
                }
            } catch (e: Exception) {
                _registerUiState.value = UiState.Error(message = e.localizedMessage ?: "Unknown Error")
                sendUiEvent(event = UiEvent.ShowSnackBar(message = e.message ?: "Unknown Error"), scoped = this)
            }
        }
    }

    sealed class RegisterNavigationEvent {
        object NavigateToHome : RegisterNavigationEvent()
        object NavigateToLogin : RegisterNavigationEvent()
    }

    // Form validator
    private fun validateRegisterForm(form: RegisterFormState): String? {
        return when {
            // Check if any field is empty
            form.email.isBlank() || form.password.isBlank() || form.confirmPassword.isBlank() || form.name.isBlank()
                    || form.rollNumber.isBlank() || form.branch.isBlank() -> {
                "All fields are required"
            }

            // Check if password and confirm password match
            form.password != form.confirmPassword -> {
                "Password and confirm password must match"
            }

            // Check if email is valid
            Patterns.EMAIL_ADDRESS.matcher(form.email).matches().not() -> {
                "Please enter a valid Email address"
            }

            form.otp.isBlank() -> {
                "Please enter OTP"
            }

            else -> null
        }
    }

    private fun validateOtpForm(form: RegisterFormState): String? {
        return when {
            Patterns.EMAIL_ADDRESS.matcher(form.email).matches().not() -> {
                "Please enter valid Email address."
            }

            form.email.isBlank() || form.name.isBlank() -> {
                "Please enter Name and Email first."
            }

            else -> null
        }
    }

    data class RegisterFormState(
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val name: String = "",
        val rollNumber: String = "",
        val branch: String = "",
        val otp: String = ""
    )
}

