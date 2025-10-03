package com.rkscoding.rapidassignment.ui.screen.quiz

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rkscoding.rapidassignment.R
import com.rkscoding.rapidassignment.data.model.QuizDetails
import com.rkscoding.rapidassignment.data.remote.dto.response.QuizSubmitResponse
import com.rkscoding.rapidassignment.data.remote.dto.response.UsersQuizDetailsResponse
import com.rkscoding.rapidassignment.navigation.NavRoutes
import com.rkscoding.rapidassignment.ui.screen.quiz.components.CircularProgressBar
import com.rkscoding.rapidassignment.ui.screen.quiz.components.QuizDetailsCard
import com.rkscoding.rapidassignment.ui.theme.darkRed
import com.rkscoding.rapidassignment.ui.theme.indigo
import com.rkscoding.rapidassignment.ui.theme.lightRose
import com.rkscoding.rapidassignment.ui.theme.popGreen
import com.rkscoding.rapidassignment.ui.theme.popRed
import com.rkscoding.rapidassignment.ui.theme.popYellow
import com.rkscoding.rapidassignment.ui.theme.roseSoft

@Composable
fun QuizResultScreen(quizResult: QuizSubmitResponse, navController: NavHostController) {

    val quizDetails =
        navController.previousBackStackEntry?.savedStateHandle?.get<QuizDetails>(key = "quizDetailsParcel")
    BackHandler {
        navController.navigate(NavRoutes.HomeRoute) {
            popUpTo(NavRoutes.HomeRoute) {
                inclusive = true
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(top = 0.dp),
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(width = 1.dp, color = indigo, shape = RoundedCornerShape(9.dp))
                        .dropShadow(
                            shape = RoundedCornerShape(12.dp),
                            shadow = Shadow(
                                radius = 0.dp,
                                color = indigo,
                                offset = DpOffset(x = -4.dp, y = 4.dp)
                            )
                        )
                        .clip(RoundedCornerShape(9.dp))
//                        .clickable{
//
//                        }
                        .background(color = Color.White, shape = RoundedCornerShape(9.dp)),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = null,
                        tint = indigo,
                        modifier = Modifier.size(24.dp).rotate(180f)
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(top = 16.dp)
//                .statusBarsPadding()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    quizDetails?.let {
                        QuizDetailsCard(
                            quiz = UsersQuizDetailsResponse(
                                subject = it.subject,
                                topic = it.topic,
                                quizCode = it.quizCode,
                                teacherName = it.teacherName,
                                totalMarks = it.totalMarks
                            ),
                            otherContent = {
                                Text(
                                    text = "Submitted at: ${quizResult.submittedAt}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Gray
                                )
                            }
                        )
                    }
                }
                item {
                    ResultCard(result = quizResult)
                }
            }
        }
    }
}

@Composable
fun ResultCard(result: QuizSubmitResponse) {
    Box(
        modifier = Modifier
            .padding(vertical = 24.dp)
            .fillMaxWidth()
//            .wrapContentHeight()
            .height(440.dp)
//            .clip(RoundedCornerShape(16.dp))
            .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = indigo)
            .dropShadow(
                shape = RoundedCornerShape(20.dp),
                shadow = Shadow(
                    radius = 0.dp,
                    color = indigo,
                    offset = DpOffset(x = (-6).dp, y = 6.dp)
                )
            )
            .background(lightRose, shape = RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.size(8.dp))
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterHorizontally)
            ) {
                CircularProgressBar(
                    progress = result.percentage.toFloat(),
                    max = 100f,
                    color = indigo,
                    backgroundColor = indigo.copy(.1f),
                    stroke = 24.dp,
                    modifier = Modifier
                        .size(140.dp)
                )

                Text(
                    text = "${result.percentage}%",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = indigo,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MiniStatsCard(
                    title = "You Scored",
                    value = result.score,
                    strokeColor = indigo
                )
                MiniStatsCard(
                    title = "Total Marks",
                    value = result.totalMarks,
                    strokeColor = indigo
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MiniStatsCard(
                    title = "Correct",
                    value = 2,
                    strokeColor = popGreen.copy(.4f)
                )
                MiniStatsCard(
                    title = "Wrong",
                    value = 0,
                    strokeColor = popRed.copy(.4f)
                )
                MiniStatsCard(
                    title = "Skipped",
                    value = 0,
                    strokeColor = popYellow.copy(.4f)
                )
            }
        }
    }
}

@Composable
fun RowScope.MiniStatsCard(title: String, value: Any, strokeColor: Color) {
    Box(
        modifier = Modifier
            .height(100.dp)
            .weight(1f)
            .dropShadow(
                shape = RoundedCornerShape(12.dp),
                shadow = Shadow(
                    radius = 0.dp,
                    color = strokeColor,
                    offset = DpOffset(x = 0.dp, y = 5.dp)
                )
            )
            .border(width = 1.dp, color = strokeColor, shape = RoundedCornerShape(12.dp))
            .background(roseSoft, shape = RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = darkRed,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = indigo.copy(.5f))
            Text(
                text = value.toString(),
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = darkRed,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ResultCardPrev() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        val result = QuizSubmitResponse(
            quizCode = "GKSQ6086",
            score = 2,
            totalMarks = 2,
            percentage = 100.0,
            correctAnswersCount = 2,
            wrongAnswersCount = 0,
            skippedQuestionsCount = 0,
            submittedAt = "01 10 2025, 14:10"
        )
        ResultCard(result = result)
    }
}