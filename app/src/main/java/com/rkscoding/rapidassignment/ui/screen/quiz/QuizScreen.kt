package com.rkscoding.rapidassignment.ui.screen.quiz

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rkscoding.rapidassignment.data.common.UiState
import com.rkscoding.rapidassignment.data.model.QuizDetails
import com.rkscoding.rapidassignment.data.remote.dto.response.UsersQuizDetailsResponse
import com.rkscoding.rapidassignment.navigation.NavRoutes
import com.rkscoding.rapidassignment.navigation.NavSerializer
import com.rkscoding.rapidassignment.ui.screen.quiz.components.QuestionCardLoadingSkeleton
import com.rkscoding.rapidassignment.ui.screen.quiz.components.QuestionItemCard
import com.rkscoding.rapidassignment.ui.screen.quiz.components.QuestionsHeader
import com.rkscoding.rapidassignment.ui.screen.quiz.components.QuizDetailsCard
import com.rkscoding.rapidassignment.ui.viewModels.QuizViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun QuizScreen(
    navController: NavHostController,
    quizDetails: UsersQuizDetailsResponse,
    quizViewModel: QuizViewModel = hiltViewModel(),
) {
    val quizDetailState by quizViewModel.quizState.collectAsStateWithLifecycle()
    val questionState by quizViewModel.quizQuestionState.collectAsStateWithLifecycle()
    val quizFormState by quizViewModel.quizCodeFormState.collectAsStateWithLifecycle()
    val quizSubmitState by quizViewModel.quizSubmissionState.collectAsStateWithLifecycle()
    val currentQuestionState = questionState
    val currentSubmitState = quizSubmitState

    LaunchedEffect(Unit) {
        quizViewModel.onFormChange(copy = quizFormState.copy(quizCode = quizDetails.quizCode))
        quizViewModel.getUserQuizQuestions()
    }
    BackHandler {
        navController.navigate(NavRoutes.HomeRoute) {
            popUpTo(NavRoutes.HomeRoute) {
                inclusive = true
            }
        }
    }

    LaunchedEffect(Unit) {
        quizViewModel.quizNavEvent.collectLatest { quizNavEvent ->
            when (quizNavEvent) {
                QuizViewModel.QuizNavEvent.NavigateToHome -> {}
                is QuizViewModel.QuizNavEvent.NavigateToResult -> {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "quizDetailsParcel",
                        value = QuizDetails(
                            subject = quizDetails.subject,
                            topic = quizDetails.topic,
                            quizCode = quizDetails.quizCode,
                            teacherName = quizDetails.teacherName,
                            totalMarks = quizDetails.totalMarks
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .statusBarsPadding()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    QuizDetailsCard(quiz = quizDetails){}
                }
                item {
                    QuestionsHeader(
                        onRetryClick = {
                            quizViewModel.getUserQuizQuestions()
                        }
                    )
                }
                when (currentQuestionState) {
                    UiState.Idle -> Unit
                    UiState.Loading -> {
                        items(4) {
                            QuestionCardLoadingSkeleton()
                        }
                    }

                    is UiState.Error -> {}
                    is UiState.Success -> {
                        val questions = currentQuestionState.data
                        items(questions) { question ->
                            QuestionItemCard(
                                question = question,
                                questionNumber = 1,
                                selected = false,
                                onSelectedOptionClick = {}
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                        }
                    }
                }
            }
        }
    }
}