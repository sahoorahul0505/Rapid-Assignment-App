package com.rkscoding.rapidassignment.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.rkscoding.rapidassignment.ui.common_components.MySnackBar
import com.rkscoding.rapidassignment.ui.common_components.AppTextField
import com.rkscoding.rapidassignment.ui.common_components.AuthButtonSection
import com.rkscoding.rapidassignment.ui.common_components.OtpTextField
import com.rkscoding.rapidassignment.ui.common_components.PasswordTextField
import com.rkscoding.rapidassignment.ui.theme.indigo
import com.rkscoding.rapidassignment.ui.theme.purple_blue
import com.rkscoding.rapidassignment.ui.theme.soft_white
import com.rkscoding.rapidassignment.ui.viewModels.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val loginUiState by viewModel.loginUiState.collectAsStateWithLifecycle()
    val otpUiState by viewModel.otpUiState.collectAsStateWithLifecycle()
    val countdown by viewModel.countdown.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        uiEvent.message
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navEvent.collectLatest { navEvent ->
            when (navEvent) {
                LoginViewModel.LoginNavigationEvent.NavigateToHome -> {
                    navController.navigate(NavRoutes.HomeRoute) {
                        popUpTo(NavRoutes.LoginRoute) {
                            inclusive = true
                        }
                    }
                }

                LoginViewModel.LoginNavigationEvent.NavigateToSignup -> {
                    navController.navigate(NavRoutes.SignupRoute) {
                        popUpTo(NavRoutes.LoginRoute) {
                            inclusive = true
                        }
                    }
                }

                LoginViewModel.LoginNavigationEvent.NavigateToResetPassword -> {
                    navController.navigate(NavRoutes.ResetPasswordRoute)
                }
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(top = 0),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) {
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
                        text = "Welcome back\nLogin to continue",
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
                    // password
                    PasswordTextField(
                        value = formState.password,
                        onValueChange = { viewModel.onFormChange(copy = formState.copy(password = it)) },
                        placeholder = "Password"
                    )
                    Text(
                        text = "Forgot password?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = indigo,
                        modifier = modifier
                            .clickable(
                                indication = null,
                                interactionSource = null,
                                onClick = { viewModel.onForgotPasswordClicked() })
                            .padding(top = 8.dp, end = 20.dp)
                            .align(Alignment.End)
                    )
                    // Otp
                    OtpTextField(
                        value = formState.otp,
                        onValueChange = { viewModel.onFormChange(copy = formState.copy(otp = it)) },
                        onClick = {
                            viewModel.sendOtpForLogin()
                        },
                        countdown = countdown,
                        isLoading = otpUiState is UiState.Loading,
                    )
                }
                // auth button section
                AuthButtonSection(
                    buttonText = "Login",
                    onPrimaryBtnClick = {
                        viewModel.login()
                    },
                    onSecondaryBtnClick = {
                        viewModel.onSignupClicked()
                    },
                    isLoading = loginUiState is UiState.Loading
                )
            }
        }
//        Column(
//            modifier = modifier
//                .padding(paddingValues)
//                .fillMaxSize()
//                .padding(horizontal = 24.dp)
//                .padding(bottom = 24.dp)
//        ) {
//            AuthScreenHeader(
//                text = "Login",
//                onButtonClick = {
//                    viewModel.onRegisterClicked()
//                }
//            )
//            Spacer(modifier.size(8.dp))
//            AppTextField(
//                value = formState.email,
//                onValueChange = { viewModel.onFormChange(formState.copy(email = it)) },
//                label = {
//                    Text(text = "Email", fontWeight = FontWeight.SemiBold)
//                },
//                placeholder = {
//                    Text(text = "name@example.com", fontSize = 14.sp, color = Color.Gray)
//                },
//                singleLine = true
//            )
//            Text(
//                text = "We'll send a verification code to this email.", style = TextStyle(
//                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
//                )
//            )
//
//            PasswordTextField(
//                label = "Password",
//                placeholder = "Enter password here",
//                value = formState.password,
//                onValueChange = { viewModel.onFormChange(formState.copy(password = it)) }
//            )
//            Text(
//                text = "Forgot password?", style = TextStyle(
//                    fontSize = MaterialTheme.typography.titleMedium.fontSize
//                ),
//                modifier = modifier
//                    .clickable(
//                        indication = null,
//                        interactionSource = null,
//                        onClick = { viewModel.onForgotPasswordClicked() })
//                    .padding(top = 8.dp)
//                    .align(Alignment.End)
//            )
//
//            OtpTextField(
//                value = formState.otp,
//                onValueChange = { viewModel.onFormChange(formState.copy(otp = it)) },
//                onClick = {
//                    viewModel.sendOtpForLogin()
//                },
//                countdown = countdown,
//                state = otpUiState is UiState.Loading
//            )
//
//            Spacer(modifier.size(24.dp))
//            AuthButton(
//                buttonText = "Login",
//                onButtonClick = {
//                    viewModel.login()
//                },
//                isLoading = loginUiState is UiState.Loading
//            )
//        }
    }
}
