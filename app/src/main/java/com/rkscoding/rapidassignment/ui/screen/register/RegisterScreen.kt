package com.rkscoding.rapidassignment.ui.screen.register

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegisterScreen(modifier: Modifier = Modifier, viewModel: RegisterViewModel = hiltViewModel()) {

    val uiState by viewModel.registerState.collectAsStateWithLifecycle()
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.registerUiEvent.collectLatest { event ->
            when (event) {
                RegisterViewModel.RegisterUiEvent.NavigateToLogin -> {

                }

                is RegisterViewModel.RegisterUiEvent.ShowSnackBar -> {
                    val message = event.message
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    when(uiState){
        is RegisterViewModel.RegisterState.Error -> {
            val message = (uiState as RegisterViewModel.RegisterState.Error).message
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        RegisterViewModel.RegisterState.Loading -> {

        }
        is RegisterViewModel.RegisterState.Success -> {
            val message = (uiState as RegisterViewModel.RegisterState.Success).message
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        else -> null
    }

    Box(modifier.fillMaxSize()) {
        Column(
            modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = "Register"
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = formState.email,
                onValueChange = {
                    viewModel.onFormChange(formState.copy(email = it))
                },
                label = {
                    Text(text = "Email")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = formState.name,
                onValueChange = {
                    viewModel.onFormChange(formState.copy(name = it))
                },
                label = {
                    Text(text = "Name")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = formState.password,
                onValueChange = {
                    viewModel.onFormChange(formState.copy(password = it))
                },
                label = {
                    Text(text = "Password")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = formState.confirmPassword,
                onValueChange = {
                    viewModel.onFormChange(formState.copy(confirmPassword = it))
                },
                label = {
                    Text(text = "Confirm Password")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = formState.rollNumber,
                onValueChange = {
                    viewModel.onFormChange(formState.copy(rollNumber = it))
                },
                label = {
                    Text(text = "Roll Number")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = formState.branch,
                onValueChange = {
                    viewModel.onFormChange(formState.copy(branch = it))
                },
                label = {
                    Text(text = "Branch")
                }
            )

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    viewModel.onRegisterClicked()
                }
            ) {
                if(uiState is RegisterViewModel.RegisterState.Loading){
                    Text(text = "Loading...")
                    return@Button
                }else{
                    Text(text = "Register")
                    return@Button
                }
            }
        }
    }
}