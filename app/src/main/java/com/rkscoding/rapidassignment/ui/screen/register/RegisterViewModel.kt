package com.rkscoding.rapidassignment.ui.screen.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkscoding.rapidassignment.data.common.Resource
import com.rkscoding.rapidassignment.data.model.request.RegisterRequest
import com.rkscoding.rapidassignment.data.reposotory.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _formState = MutableStateFlow(RegisterFormState())
    val formState = _formState.asStateFlow()

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState = _registerState.asStateFlow()

    private val _registerUiEvent = MutableSharedFlow<RegisterUiEvent>()
    val registerUiEvent = _registerUiEvent.asSharedFlow()

    fun onFormChange(updated: RegisterFormState) {
        _formState.value = updated
    }

    fun onRegisterClicked() {
        val currentForm = _formState.value

        viewModelScope.launch {
            // Validate the form before making the API call
            val validationError = validateForm(currentForm)
            if (validationError != null) {
                _registerUiEvent.emit(RegisterUiEvent.ShowSnackBar(validationError))
                return@launch
            }

            _registerState.value = RegisterState.Loading
            val result = repository.register(
                RegisterRequest(
                    email = currentForm.email,
                    password = currentForm.password,
                    name = currentForm.name,
                    rollNumber = currentForm.rollNumber,
                    branch = currentForm.branch
                )
            )

            when (result) {
                is Resource.Success -> {
                    _registerState.value = RegisterState.Success(result.data.message)
                    _registerUiEvent.emit(RegisterUiEvent.NavigateToLogin)
                }

                is Resource.Error -> {
                    _registerState.value = RegisterState.Error(result.message)
                    _registerUiEvent.emit(RegisterUiEvent.ShowSnackBar(result.message))
                }

                else -> Unit
            }
        }
    }

    private fun validateForm(form: RegisterFormState): String? {
        return when {
            // Check if any field is empty
             form.email.isBlank() || form.password.isBlank() || form.confirmPassword.isBlank() || form.name.isBlank()
                    || form.rollNumber.isBlank() || form.branch.isBlank() -> {
                "All fields are required"
            }

            // Check if password and confirm password match
            form.password != form.confirmPassword -> {
                "Password and confirm password should be same"
            }

            // Check if email is valid
            Patterns.EMAIL_ADDRESS.matcher(form.email).matches().not() -> {
                "Please enter a valid email address"
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
        val branch: String = ""
    )

    sealed class RegisterState {
        object Idle : RegisterState()
        object Loading : RegisterState()
        data class Success(val message: String) : RegisterState()
        data class Error(val message: String) : RegisterState()
    }

    sealed class RegisterUiEvent {
        object NavigateToLogin : RegisterUiEvent()
        data class ShowSnackBar(val message: String) : RegisterUiEvent()
    }
}