package com.rkscoding.rapidassignment.ui.viewModels

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.rkscoding.rapidassignment.data.common.BaseViewModel
import com.rkscoding.rapidassignment.data.common.UiEvent
import com.rkscoding.rapidassignment.data.common.UiState
import com.rkscoding.rapidassignment.data.remote.dto.request.LoginOtpRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.LoginRequest
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
class LoginViewModel @Inject constructor(
    private val repository: UsersAuthRepository,
    private val session: SessionToken
) : BaseViewModel() {

    private val _loginUiState = MutableStateFlow<UiState<SessionResponse>>(UiState.Idle)
    val loginUiState = _loginUiState.asStateFlow()

    private val _otpUiState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val otpUiState = _otpUiState.asStateFlow()

    private val _formState = MutableStateFlow(LoginFormState())
    val formState = _formState.asStateFlow()

    private val _navEvent = MutableSharedFlow<LoginNavigationEvent>(replay = 1)
    val navEvent = _navEvent.asSharedFlow()

    fun onFormChange(copy: LoginFormState) {
        viewModelScope.launch {
            _formState.value = copy
        }
    }


    fun onSignupClicked(){
        viewModelScope.launch {
            _navEvent.emit(LoginNavigationEvent.NavigateToSignup)
        }
    }

    fun onForgotPasswordClicked(){
        viewModelScope.launch {
            _navEvent.emit(LoginNavigationEvent.NavigateToResetPassword)
        }
    }

    fun sendOtpForLogin() {
        val currentForm = _formState
        viewModelScope.launch {
            val validationFormError = validateRequestOtpForm(currentForm.value)
            if (validationFormError != null) {
                sendUiEvent(event = UiEvent.ShowSnackBar(validationFormError), scoped = this)
                return@launch
            }

            _otpUiState.value = UiState.Loading
            try {
                val result = repository.sendOtpForLogin(
                    LoginOtpRequest(
                        currentForm.value.email,
                        currentForm.value.password
                    )
                )
                _otpUiState.value = UiState.Success(result.message)
                sendUiEvent(event = UiEvent.ShowSnackBar(result.message), scoped = this)
                startCountdown()
            } catch (e: Exception) {
                _otpUiState.value = UiState.Error(e.message ?: "Unknown Error")
                sendUiEvent(event = UiEvent.ShowSnackBar(e.localizedMessage ?: "Unknown Error"), scoped = this)
            }
        }
    }

    fun login() {
        val currentForm = _formState
        viewModelScope.launch {
            val formValidationError = validateLoginForm(currentForm.value)
            if (formValidationError != null) {
                sendUiEvent(event = UiEvent.ShowSnackBar(formValidationError), scoped = this)
                return@launch
            }

            _loginUiState.value = UiState.Loading
            try {
                val result = repository.login(
                    LoginRequest(
                        email = currentForm.value.email,
                        password = currentForm.value.password,
                        otp = currentForm.value.otp
                    )
                )
                val token = result.data?.token
                if (token != null) {
                    _loginUiState.value = UiState.Success(result.data)
                    session.storeToken(token)
                    sendUiEvent(event = UiEvent.ShowSnackBar(result.message), scoped = this)
                    _navEvent.emit(LoginNavigationEvent.NavigateToHome)
                } else {
                    _loginUiState.value = UiState.Error("Missing session token from server. ")
                    sendUiEvent(
                        event = UiEvent.ShowSnackBar("Missing session token from server. \n Please try to Login again"),
                        scoped = this
                    )
                    return@launch
                }
            } catch (e: Exception) {
                _loginUiState.value = UiState.Error(e.message ?: "Unknown Error")
                sendUiEvent(event = UiEvent.ShowSnackBar(e.message ?: "Unknown Error"), scoped = this)
            }
        }
    }

    sealed class LoginNavigationEvent {
        object NavigateToSignup : LoginNavigationEvent()
        object NavigateToHome : LoginNavigationEvent()
        object NavigateToResetPassword : LoginNavigationEvent()
    }

    private fun validateLoginForm(form: LoginFormState): String? {
        return when {
            form.email.isBlank() || form.password.isBlank() -> {
                "Please fill all fields"
            }

            Patterns.EMAIL_ADDRESS.matcher(form.email).matches().not() -> {
                "Please enter valid Email address"
            }

            form.otp.isBlank() -> {
                "Please enter OTP"
            }

            else -> null
        }
    }

    private fun validateRequestOtpForm(form: LoginFormState): String? {
        return when {
            Patterns.EMAIL_ADDRESS.matcher(form.email).matches().not() -> {
                "Please enter valid Email address."
            }

            form.email.isBlank() || form.password.isBlank() -> {
                "Please fill all fields"
            }

            else -> null
        }
    }


    data class LoginFormState(
        val email: String = "",
        val password: String = "",
        val otp: String = ""
    )
}