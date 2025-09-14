package com.rkscoding.rapidassignment.ui.screen.register

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rkscoding.rapidassignment.R
import com.rkscoding.rapidassignment.data.common.UiEvent
import com.rkscoding.rapidassignment.data.common.UiState
import com.rkscoding.rapidassignment.ui.components.MyTextField
import com.rkscoding.rapidassignment.ui.theme.darkTeal
import com.rkscoding.rapidassignment.ui.theme.lightTeal
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun RegisterScreen(modifier: Modifier = Modifier, viewModel: RegisterViewModel = hiltViewModel()) {

    val regUiState by viewModel.registerUiState.collectAsStateWithLifecycle()
    val otpUiState by viewModel.otpUiState.collectAsStateWithLifecycle()
    val countDown by viewModel.countdown.collectAsStateWithLifecycle()
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {

                UiEvent.NavigationBack -> {

                }

                is UiEvent.ShowSnackBar -> {
                    scope.launch {
                        snackBarHostState.showSnackbar(event.message, duration = SnackbarDuration.Short)
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) {
                Snackbar(
                    modifier = modifier
                        .padding(bottom = 56.dp, start = 24.dp, end = 24.dp)
                        .height(64.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Text(
                        text = it.visuals.message,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        ) {

            // Header
            RegisterHeader()
            Spacer(modifier.size(8.dp))
            Column(
                modifier = modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .imePadding()
            ) {


                MyTextField(
                    value = formState.name,
                    onValueChange = { viewModel.onFormChange(formState.copy(name = it)) },
                    label = {
                        Text(text = "Full name", fontWeight = FontWeight.SemiBold)
                    },
                    placeholder = {
                        Text(text = "e.g., Jordan Smith", fontSize = 14.sp, color = Color.Gray)
                    },
                    singleLine = true
                )

                MyTextField(
                    value = formState.email,
                    onValueChange = { viewModel.onFormChange(formState.copy(email = it)) },
                    label = {
                        Text(text = "Email", fontWeight = FontWeight.SemiBold)
                    },
                    placeholder = {
                        Text(text = "name@example.com", fontSize = 14.sp, color = Color.Gray)
                    },
                    singleLine = true
                )
                Text(
                    text = "We'll send a verification code to this email.", style = TextStyle(
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                    )
                )

                MyTextField(
                    value = formState.otp,
                    onValueChange = { viewModel.onFormChange(formState.copy(otp = it)) },
                    label = {
                        Text(text = "OTP", fontWeight = FontWeight.SemiBold)
                    },
                    placeholder = {
                        Text(text = "Enter otp here", fontSize = 14.sp, color = Color.Gray)
                    },
                    textStyle = TextStyle(letterSpacing = 6.sp, fontWeight = FontWeight.Bold),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    trailingIcon = {
                        Box(
                            modifier = modifier
                                .height(52.dp)
                                .width(116.dp)
                                .padding(end = 2.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .clickable(
                                    enabled = countDown == 0,
                                    onClick = {
                                        viewModel.onSendOtpClicked()
                                    }
                                )
                                .background(lightTeal.copy(alpha = .5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (otpUiState is UiState.Loading) {
                                LinearProgressIndicator(
                                    modifier = modifier
                                        .height(2.dp)
                                        .fillMaxWidth()
                                        .align(Alignment.TopCenter), color = darkTeal
                                )
                                LinearProgressIndicator(
                                    modifier = modifier
                                        .height(2.dp)
                                        .fillMaxWidth()
                                        .align(Alignment.BottomCenter), color = darkTeal
                                )
                            }

                            if (countDown > 0) {
                                Text(
                                    text = "$countDown",
                                    style = TextStyle(
                                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            } else {
                                Text(
                                    text = if (otpUiState is UiState.Loading) "Sending..." else "Send OTP",
                                    style = TextStyle(
                                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }

                        }
                    }
                )

                var visible by remember { mutableStateOf(true) }
                MyTextField(
                    value = formState.password,
                    onValueChange = { viewModel.onFormChange(formState.copy(password = it)) },
                    label = {
                        Text(text = "Password", fontWeight = FontWeight.SemiBold)
                    },
                    placeholder = {
                        Text(text = "At least 8 character long", fontSize = 14.sp, color = Color.Gray)
                    },
                    singleLine = true,
                    trailingIcon = {
                        Box(
                            modifier = modifier
                                .height(52.dp)
                                .width(116.dp)
                                .padding(end = 2.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .clickable(
                                    onClick = { visible = !visible }
                                )
                                .background(lightTeal.copy(alpha = .5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier.padding(vertical = 6.dp, horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.eye_outline),
                                    contentDescription = "password visible",
                                    modifier = modifier.size(24.dp)
                                )
                                Text(
                                    text = if (visible) "Show" else "Hide",
                                    style = TextStyle(
                                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }
                    },
                    visualTransformation = if (!visible) VisualTransformation.None else PasswordVisualTransformation()
                )

                MyTextField(
                    value = formState.confirmPassword,
                    onValueChange = { viewModel.onFormChange(formState.copy(confirmPassword = it)) },
                    label = {
                        Text(text = "Confirm password", fontWeight = FontWeight.SemiBold)
                    },
                    placeholder = {
                        Text(text = "Re-enter your password", fontSize = 14.sp, color = Color.Gray)
                    },
                    trailingIcon = {
                        Box(
                            modifier = modifier
                                .height(52.dp)
                                .width(116.dp)
                                .padding(end = 2.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .clickable(
                                    onClick = { visible = !visible }
                                )
                                .background(lightTeal.copy(alpha = .5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier.padding(vertical = 6.dp, horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.eye_outline),
                                    contentDescription = "password visible",
                                    modifier = modifier.size(24.dp)
                                )
                                Text(
                                    text = if (visible) "Show" else "Hide",
                                    style = TextStyle(
                                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }
                    },
                    visualTransformation = if (!visible) VisualTransformation.None else PasswordVisualTransformation()
                )

                MyTextField(
                    value = formState.rollNumber,
                    onValueChange = { viewModel.onFormChange(formState.copy(rollNumber = it)) },
                    label = {
                        Text(text = "Roll Number", fontWeight = FontWeight.SemiBold)
                    },
                    placeholder = {
                        Text(text = "e.g., 2201297103", fontSize = 14.sp, color = Color.Gray)
                    },
                    singleLine = true
                )

                MyTextField(
                    value = formState.branch,
                    onValueChange = { viewModel.onFormChange(formState.copy(branch = it)) },
                    label = {
                        Text(text = "Branch", fontWeight = FontWeight.SemiBold)
                    },
                    placeholder = {
                        Text(text = "e.g., Computer Science and Engineering", fontSize = 14.sp, color = Color.Gray)
                    },
                    singleLine = true
                )

                Spacer(modifier.size(16.dp))
                Button(
                    onClick = {
                        viewModel.onRegisterClicked()
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = lightTeal.copy(alpha = .8f),
                        contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
                    ),
                    contentPadding = PaddingValues(0.dp),
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier
                            .fillMaxWidth()
                            .height(56.dp), contentAlignment = Alignment.Center
                    )
                    {
                        if (regUiState is UiState.Loading) {
                            LinearProgressIndicator(
                                modifier = modifier
                                    .height(2.dp)
                                    .fillMaxWidth()
                                    .align(Alignment.TopCenter), color = darkTeal
                            )
                            LinearProgressIndicator(
                                modifier = modifier
                                    .height(2.dp)
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter), color = darkTeal
                            )
                        }
                        Text(
                            text = if (regUiState is UiState.Loading) "Please wait..." else "Register",
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}