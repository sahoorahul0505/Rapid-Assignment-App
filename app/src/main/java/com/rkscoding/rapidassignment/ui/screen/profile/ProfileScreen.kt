package com.rkscoding.rapidassignment.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rkscoding.rapidassignment.data.common.UiState
import com.rkscoding.rapidassignment.navigation.NavRoutes
import com.rkscoding.rapidassignment.ui.screen.profile.components.ErrorDialog
import com.rkscoding.rapidassignment.ui.screen.profile.components.ProfileHeader
import com.rkscoding.rapidassignment.ui.screen.profile.components.ProfileScreenLoadingSkeleton
import com.rkscoding.rapidassignment.ui.screen.profile.components.ProfileSettingsCard
import com.rkscoding.rapidassignment.ui.screen.profile.components.StatsCard
import com.rkscoding.rapidassignment.ui.theme.indigo
import com.rkscoding.rapidassignment.ui.theme.purple_blue
import com.rkscoding.rapidassignment.ui.viewModels.ProfileViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val uiState by viewModel.profileUiState.collectAsStateWithLifecycle()
    var showErrorDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.navEvent.collectLatest { navEvent ->
            when(navEvent){
                ProfileViewModel.ProfileNavigationEvent.NavigateToLogin -> {
                    navController.navigate(NavRoutes.LoginRoute){
                        popUpTo(NavRoutes.LoginRoute){
                            inclusive = true
                        }
                    }
                }
                ProfileViewModel.ProfileNavigationEvent.NavigateToProfileSettings -> {
                    navController.navigate(NavRoutes.ProfileSettingsRoute)
                }
                else -> null
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) { paddingValue ->
        Box(
            modifier = modifier
                .padding(paddingValue)
                .fillMaxSize()
                .background(Color.White)
        ) {
            GradientHeader()
            Column(
                modifier = modifier
                    .statusBarsPadding()
                    .padding(vertical = 16.dp, horizontal = 24.dp)
            ) {

                Column(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (val state = uiState) {
                        UiState.Idle -> Unit
                        UiState.Loading -> {
                            ProfileScreenLoadingSkeleton()
                        }

                        is UiState.Error -> {
                            LaunchedEffect(state) {
                            showErrorDialog = true
                            }
                            ProfileScreenLoadingSkeleton()
                        }

                        is UiState.Success -> {
                            val data = state.data
                            // picture h
                            ProfileHeader(profile = data)
                            Spacer(modifier.size(56.dp))
                            // Stats
                            StatsCard(
                                onStatsClick = {}
                            )
                        }
                    }
                }
                Spacer(modifier.size(32.dp))
                // Settings
                ProfileSettingsCard(
                    onProfileSettingsClicked = {
                        viewModel.onProfileSettingsClicked()
                    },
                    onNotificationClicked = {},
                    onLogOutClicked = {
                        viewModel.logOut()
                    }
                )
            }
        }
        if (showErrorDialog) {
            ErrorDialog(
                onDismiss = {
                    showErrorDialog = false
                }
            )
        }
    }
}





@Composable
fun GradientHeader() {
    val brush = listOf(
        indigo,
        purple_blue,
    )
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(.4f)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 32.dp,
                    bottomEnd = 32.dp
                )
            )
            .background(brush = Brush.verticalGradient(brush))
    )
}