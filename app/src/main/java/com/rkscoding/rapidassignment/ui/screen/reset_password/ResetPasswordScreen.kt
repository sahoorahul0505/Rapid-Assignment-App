package com.rkscoding.rapidassignment.ui.screen.reset_password

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rkscoding.rapidassignment.data.common.UiEvent
import com.rkscoding.rapidassignment.data.common.UiState
import com.rkscoding.rapidassignment.navigation.NavRoutes
import com.rkscoding.rapidassignment.ui.common_components.AppTextField
import com.rkscoding.rapidassignment.ui.common_components.AuthButtonSection
import com.rkscoding.rapidassignment.ui.common_components.MySnackBar
import com.rkscoding.rapidassignment.ui.common_components.OtpTextField
import com.rkscoding.rapidassignment.ui.common_components.PasswordTextField
import com.rkscoding.rapidassignment.ui.theme.indigo
import com.rkscoding.rapidassignment.ui.theme.purple_blue
import com.rkscoding.rapidassignment.ui.theme.soft_white
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ResetPasswordScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ResetPasswordViewModel = hiltViewModel(),
) {

    val otpUiState by viewModel.sendOtpState.collectAsStateWithLifecycle()
    val resetPasswordState by viewModel.resetUiState.collectAsStateWithLifecycle()
    val countdown by viewModel.countdown.collectAsStateWithLifecycle()
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(uiEvent.message)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navEvent.collectLatest { navEvent ->
            when (navEvent) {
                ResetPasswordViewModel.ResetPasswordNavEvents.NavigateToLogin -> {
                    navController.navigate(NavRoutes.LoginRoute) {
                        popUpTo(NavRoutes.LoginRoute) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    BackHandler {
        navController.navigate(NavRoutes.LoginRoute) {
            popUpTo(NavRoutes.LoginRoute) {
                inclusive = true
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(top = 0),
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState
            ) {
                MySnackBar(text = it.visuals.message)
            }
        }
    ) { paddingValues ->
        val gradient = listOf(
            indigo,
            indigo,
            purple_blue,
            soft_white,
            soft_white,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(gradient))
                .padding(paddingValues)
                .statusBarsPadding()
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Set a new password",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = soft_white,
                            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                        )
                        .innerShadow(
                            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                            shadow = Shadow(
                                radius = 8.dp,
                                color = Color.White,
                                offset = DpOffset(x = 0.dp, y = 8.dp)
                            )
                        )
                        .padding(vertical = 24.dp)
                ) {
                    // Email
                    AppTextField(
                        value = formState.email,
                        onValueChange = { viewModel.onFormChange(copy = formState.copy(email = it)) },
                        placeholder = {
                            Text(
                                text = "Email", fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                            )
                        }
                    )
                    Text(
                        text = "We'll send a verification code to this email.",
                        fontSize = 13.sp, color = indigo, modifier = Modifier.padding(start = 20.dp)
                    )
                    // Otp
                    OtpTextField(
                        value = formState.otp,
                        onValueChange = { viewModel.onFormChange(copy = formState.copy(otp = it)) },
                        onClick = {
                            viewModel.sendForgotPasswordOtp()
                        },
                        countdown = countdown,
                        isLoading = otpUiState is UiState.Loading,
                    )
                    // password
                    PasswordTextField(
                        value = formState.password,
                        onValueChange = { viewModel.onFormChange(copy = formState.copy(password = it)) },
                        placeholder = "Password"
                    )
                    // Confirm password
                    PasswordTextField(
                        value = formState.confirmPassword,
                        onValueChange = { viewModel.onFormChange(copy = formState.copy(confirmPassword = it)) },
                        placeholder = "Confirm password"
                    )
                }
                // auth button section
                AuthButtonSection(
                    buttonText = "Confirm",
                    onPrimaryBtnClick = {
                        viewModel.resetPassword()
                    },
                    onSecondaryBtnClick = {
                        viewModel.onBackToLoginClicked()
                    },
                    isLoading = resetPasswordState is UiState.Loading
                )
            }
        }
    }
}