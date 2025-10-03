package com.rkscoding.rapidassignment.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.rkscoding.rapidassignment.R
import com.rkscoding.rapidassignment.data.common.UiEvent
import com.rkscoding.rapidassignment.data.common.UiState
import com.rkscoding.rapidassignment.data.model.QuizDetails
import com.rkscoding.rapidassignment.navigation.NavRoutes
import com.rkscoding.rapidassignment.navigation.NavSerializer
import com.rkscoding.rapidassignment.ui.common_components.MySearchBar
import com.rkscoding.rapidassignment.ui.common_components.RecentQuizCard
import com.rkscoding.rapidassignment.ui.viewModels.QuizViewModel
import com.rkscoding.rapidassignment.ui.theme.darkRed
import com.rkscoding.rapidassignment.ui.theme.purple_blue
import com.rkscoding.rapidassignment.ui.theme.indigo
import com.rkscoding.rapidassignment.ui.theme.rose
import com.rkscoding.rapidassignment.ui.theme.roseSoft
import com.rkscoding.rapidassignment.ui.viewModels.HomeViewModel
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    quizViewModel: QuizViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val profileUiState = homeViewModel.profileUiState.collectAsStateWithLifecycle()
    val quizQuestionUiState = quizViewModel.quizQuestionState.collectAsStateWithLifecycle()
    val quizDetailsState by quizViewModel.quizState.collectAsStateWithLifecycle()
    val quizCodeFormState by quizViewModel.quizCodeFormState.collectAsStateWithLifecycle()
    val brush = listOf(
        indigo,
        purple_blue,
    )

    LaunchedEffect(Unit) {
        quizViewModel.uiEvent.collectLatest {
            when (it) {
                is UiEvent.ShowSnackBar -> {

                }
            }
        }
    }

    val context = LocalContext.current
    val currQuizState = quizDetailsState
    var quizCode by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        quizViewModel.quizNavEvent.collectLatest { quizNavEvent ->
            when (quizNavEvent) {
                is QuizViewModel.QuizNavEvent.NavigateToQuiz -> {
                    val quizJsonString = NavSerializer.encode(quizNavEvent.quizDetails)
                    navController.navigate(NavRoutes.QuizRoute(quizDetailsJson = quizJsonString))
                }

                is QuizViewModel.QuizNavEvent.NavigateToResult -> {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "quizDetailsParcel",
                        value = QuizDetails(
                            subject = "Java",
                            topic = "data Types",
                            quizCode = "JAVDT8399",
                            teacherName = "Updated rahul Sahoo",
                            totalMarks = 30
                        )
                    )
                    val quizResultJsonString = NavSerializer.encode(quizNavEvent.quizResult)
                    navController.navigate(NavRoutes.QuizResultRoute(quizResultJson = quizResultJsonString))
                }

                else -> Unit
            }
        }
    }
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) { paddingValues ->
        Column(
            modifier = modifier
                .background(Color.White)
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 32.dp,
                            bottomEnd = 32.dp
                        )
                    )
                    .background(brush = Brush.verticalGradient(brush))
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(top = 16.dp, bottom = 24.dp, start = 16.dp, end = 16.dp)
                ) {
                    Row(
                        modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            when (val state = profileUiState.value) {
                                UiState.Idle -> {}
                                UiState.Loading -> {
                                    Row(
                                        modifier = modifier
                                            .shimmer()
                                            .fillMaxWidth(.7f)
                                            .height(64.dp)
                                    ) {
                                        Box(
                                            modifier = modifier
                                                .size(64.dp)
                                                .clip(CircleShape)
                                                .background(Color.Black.copy(alpha = .2f))
                                        )
                                        Column(
                                            modifier
                                                .fillMaxHeight()
                                                .padding(top = 2.dp, bottom = 2.dp, start = 16.dp),
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Box(
                                                modifier = modifier
                                                    .height(16.dp)
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(Color.Black.copy(alpha = .2f))
                                            )

                                            Box(
                                                modifier = modifier
                                                    .height(16.dp)
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(Color.Black.copy(alpha = .2f))
                                            )
                                        }
                                    }
                                }

                                is UiState.Error -> {
                                    Row(
                                        modifier = modifier
                                            .fillMaxWidth(.7f)
                                            .height(64.dp)
                                    ) {
                                        IconButton(
                                            onClick = {
                                                homeViewModel.fetchUserProfile()
                                            },
                                            modifier = modifier.size(64.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.refresh),
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = modifier.size(64.dp)
                                            )
                                        }
                                        Column(
                                            modifier
                                                .shimmer()
                                                .fillMaxHeight()
                                                .padding(top = 2.dp, bottom = 2.dp, start = 16.dp),
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "Please refresh",
                                                style = TextStyle(
                                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = Color.White
                                                )
                                            )

                                            Text(
                                                text = "Something went wrong",
                                                style = TextStyle(
                                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                                    fontWeight = FontWeight.Normal,
                                                    color = Color.White.copy(alpha = .8f)
                                                )
                                            )
                                        }
                                    }
                                }

                                is UiState.Success -> {
                                    val profile = state.data
                                    Box(
                                        modifier = modifier
                                            .wrapContentSize()
                                            .border(width = 1.dp, shape = CircleShape, color = roseSoft)
                                            .clip(CircleShape)
                                            .background(purple_blue)
                                    ) {
                                        if (profile.profilePic != null) {
                                            AsyncImage(
                                                model = profile.profilePic,
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,
                                                modifier = modifier
                                                    .size(64.dp)
                                                    .clip(CircleShape)
                                            )
                                        } else {
                                            Image(
                                                painter = painterResource(id = R.drawable.demo_profile_pic_croped),
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,
                                                modifier = modifier
                                                    .size(64.dp)
                                                    .clip(CircleShape)
                                            )
                                        }
                                    }
                                    Column(
                                        modifier
                                            .fillMaxHeight()
                                            .padding(top = 2.dp, bottom = 2.dp, start = 16.dp),
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        val firstName =
                                            profile.name.trim().split("\\s+".toRegex()).firstOrNull() ?: "Unknown"
                                        Text(
                                            text = "Hello, $firstName",
                                            style = TextStyle(
                                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.White
                                            )
                                        )

                                        Text(
                                            text = "Let's start your quiz now",
                                            style = TextStyle(
                                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                                fontWeight = FontWeight.Normal,
                                                color = Color.White.copy(alpha = .8f)
                                            )
                                        )
                                    }
                                }
                            }
                        }
                        val count = remember { mutableIntStateOf(1) }
                        BadgedBox(
                            modifier = modifier
                                .clip(CircleShape)
                                .clickable {

                                }
                                .padding(8.dp),
                            badge = {
                                if (count.intValue > 0) {
                                    Badge(
                                        containerColor = rose
                                    )
                                }
                            }
                        ) {

                            Icon(
                                painter = painterResource(id = R.drawable.notification),
                                contentDescription = "notifications",
                                tint = Color.White,
                                modifier = modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = modifier.size(16.dp))
                    MySearchBar(
                        query = "",
                        onQueryChange = {},
                        placeHolder = "Search by quiz code...",
                        modifier = modifier.fillMaxWidth()
                    )
                    Spacer(modifier = modifier.size(24.dp))
                    RecentQuizCard(
                        onClick = {
                            quizViewModel.onRecentQuizClicked()
                        }
                    )
                }
            }
            Spacer(modifier.size(16.dp))

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .dropShadow(
                        shape = RoundedCornerShape(20.dp),
                        shadow = Shadow(
                            radius = 0.dp,
                            alpha = 1f,
                            color = indigo,
                            offset = DpOffset(x = (-6).dp, y = (6).dp)
                        )
                    )
                    .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = indigo)
                    .clip(RoundedCornerShape(16.dp))
                    .background(roseSoft)
            ) {
                Column(
                    modifier = modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Join Quiz",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = darkRed
                        )
                    )
                    Spacer(modifier.size(16.dp))
                    TextField(
                        value = quizCodeFormState.quizCode,
                        onValueChange = { quizViewModel.onFormChange(copy = quizCodeFormState.copy(quizCode = it)) },
                        placeholder = {
                            Text(
                                text = "Enter quiz code...",
                                color = Color.Gray
                            )
                        },
                        modifier = modifier
                            .fillMaxWidth()
                            .dropShadow(
                                shape = RoundedCornerShape(20.dp),
                                shadow = Shadow(
                                    radius = 0.dp,
                                    color = indigo,
                                    offset = DpOffset(x = 0.dp, y = 4.dp)
                                )
                            )
                            .border(width = 1.dp, color = indigo, shape = RoundedCornerShape(15.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                    Spacer(modifier.size(24.dp))
                    Button(
                        onClick = {
                            quizViewModel.getQuizDetails()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = indigo
                        ),
                        modifier = modifier
                            .dropShadow(
                                shape = RoundedCornerShape(32.dp),
                                shadow = Shadow(
                                    radius = 0.dp,
                                    alpha = 1f,
                                    color = indigo,
                                    offset = DpOffset(x = (0).dp, y = (4).dp)
                                )
                            )
                            .border(width = 1.dp, color = indigo, shape = RoundedCornerShape(32.dp))
                    ) {
                        if (currQuizState is UiState.Loading) {
                            CircularProgressIndicator()
                        } else {
                            Text(
                                text = "Join",
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                                    fontWeight = FontWeight.Bold
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