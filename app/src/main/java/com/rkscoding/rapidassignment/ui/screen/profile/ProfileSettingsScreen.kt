package com.rkscoding.rapidassignment.ui.screen.profile

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.rkscoding.rapidassignment.R
import com.rkscoding.rapidassignment.data.common.UiState
import com.rkscoding.rapidassignment.navigation.NavRoutes
import com.rkscoding.rapidassignment.ui.theme.indigo
import com.rkscoding.rapidassignment.ui.theme.purple_blue
import com.rkscoding.rapidassignment.ui.viewModels.ProfileViewModel

@Composable
fun ProfileSettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val uiState by viewModel.profileUiState.collectAsStateWithLifecycle()

    BackHandler {
        navController.navigate(NavRoutes.ProfileRoute) {
            popUpTo(NavRoutes.ProfileRoute) {
                inclusive = true
            }
        }
    }
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) { paddingValue ->
        val gradientBg = listOf(
            indigo,
            purple_blue
        )
        val imageGradient = listOf(
            Color.Transparent,
            indigo.copy(.6f)
        )
        val infiniteTransition = rememberInfiniteTransition()
        val textAlpha = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(800, easing = FastOutLinearInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "alphaAnim"
        )
        Box(
            modifier = modifier
                .padding(paddingValue)
                .fillMaxSize()
                .background(brush = Brush.linearGradient(gradientBg))
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                when (val state = uiState) {
                    UiState.Idle -> Unit
                    UiState.Loading -> {
                        Box(
                            modifier = modifier
                                .fillMaxSize()
                                .background(Color.White.copy(.7f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = modifier
                                    .padding(56.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(32.dp))
                                    .background(Color.White)
                            ) {
                                Column(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(24.dp)
                                ) {
                                    Text(
                                        text = "Fetching profile...",
                                        style = TextStyle(
                                            fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = indigo
                                        ),
                                        modifier = modifier.alpha(textAlpha.value)
                                    )
                                    CircularProgressIndicator(
                                        modifier = modifier.size(56.dp),
                                        strokeWidth = 8.dp,
                                        trackColor = Color.White,
                                        color = indigo
                                    )
                                }
                            }
                        }
                    }

                    is UiState.Error -> {
                        Box(
                            modifier = modifier
                                .fillMaxSize()
                                .background(Color.White.copy(.7f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = modifier
                                    .padding(56.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(32.dp))
                                    .background(Color.White)
                            ) {
                                Column(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(24.dp)
                                ) {
                                    Text(
                                        text = "Something went wrong",
                                        style = TextStyle(
                                            fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color.Red
                                        )
                                    )
                                }
                            }
                        }
                    }
                    is UiState.Success -> {
                        val data = state.data
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 56.dp)
                        ) {
                            Box(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .dropShadow(
                                        shape = RoundedCornerShape(32.dp),
                                        shadow = Shadow(
                                            radius = 16.dp,
                                            color = Color.Black,
                                            spread = 8.dp,
                                            alpha = .1f,
                                        )
                                    )
                                    .clip(RoundedCornerShape(32.dp))
                                    .background(Color.White)
                            ) {
                                if (data.profilePic != null) {
                                    AsyncImage(
                                        model = data.profilePic,
                                        contentDescription = null,
                                        modifier = modifier.fillMaxSize()
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.demo_profile_pic_croped),
                                        contentDescription = null,
                                        modifier = modifier.fillMaxSize()
                                    )
                                }

                                Box(
                                    modifier = modifier
                                        .fillMaxSize()
                                        .background(brush = Brush.verticalGradient(imageGradient))
                                )
                            }

                            Spacer(modifier.size(32.dp))
                            Text(
                                text = data.name,
                                style = TextStyle(
                                    fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = Color.White
                                )
                            )
                            Spacer(modifier.size(8.dp))
                            Text(
                                text = data.email,
                                style = TextStyle(
                                    fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color.White.copy(.7f)
                                )
                            )
                        }
                        Spacer(modifier.weight(1f))
                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 32.dp,
                                        topEnd = 32.dp,
                                        bottomStart = 0.dp,
                                        bottomEnd = 0.dp
                                    )
                                )
                                .background(Color.White)
                        ) {
                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .navigationBarsPadding()
                                    .padding(32.dp)
                                    .padding(bottom = 32.dp)
                            ) {
                                Row(
                                    modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.education_cap),
                                        contentDescription = null,
                                        tint = indigo,
                                        modifier = modifier.size(32.dp)
                                    )
                                    Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    fontSize = 20.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.Black
                                                )
                                            ) {
                                                append("Roll Number: ")
                                            }
                                            withStyle(
                                                style = SpanStyle(
                                                    fontSize = 20.sp,
                                                    fontWeight = FontWeight.Medium,
                                                    color = Color.Black
                                                )
                                            ) {
                                                append(data.rollNumber)
                                            }
                                        }
                                    )
                                }

                                HorizontalDivider(
                                    thickness = 4.dp,
                                    color = Color.LightGray.copy(.2f),
                                    modifier = modifier.padding(24.dp)
                                )

                                Row(
                                    modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.open_book),
                                        contentDescription = null,
                                        tint = indigo,
                                        modifier = modifier.size(32.dp)
                                    )
                                    Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    fontSize = 20.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.Black
                                                )
                                            ) {
                                                append("Branch: ")
                                            }
                                            withStyle(
                                                style = SpanStyle(
                                                    fontSize = 20.sp,
                                                    fontWeight = FontWeight.Medium,
                                                    color = Color.Black
                                                )
                                            ) {
                                                append(data.branch)
                                            }
                                        }
                                    )
                                }

                                Spacer(modifier.size(32.dp))
                                Button(
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(containerColor = indigo),
                                    modifier = modifier
                                        .align(Alignment.CenterHorizontally)
                                        .dropShadow(
                                            shape = RoundedCornerShape(32.dp),
                                            shadow = Shadow(
                                                radius = 16.dp,
                                                spread = 2.dp,
                                                color = indigo,
                                                alpha = .4f,
                                                offset = DpOffset(x = 6.dp, y = 6.dp)
                                            )
                                        )
                                ) {
                                    Text(
                                        text = "Edit Profile",
                                        style = TextStyle(
                                            fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White
                                        ),
                                        modifier = modifier.padding(8.dp)
                                    )
                                }
                            }
                        }


                    }
                }

            }
        }
    }
}