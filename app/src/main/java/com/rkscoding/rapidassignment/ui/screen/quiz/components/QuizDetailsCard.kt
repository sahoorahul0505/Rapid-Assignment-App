package com.rkscoding.rapidassignment.ui.screen.quiz.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rkscoding.rapidassignment.data.remote.dto.response.UsersQuizDetailsResponse
import com.rkscoding.rapidassignment.ui.theme.darkRed
import com.rkscoding.rapidassignment.ui.theme.indigo
import com.rkscoding.rapidassignment.ui.theme.purple_blue
import com.rkscoding.rapidassignment.ui.theme.roseSoft

@Composable
fun QuizDetailsCard(quiz: UsersQuizDetailsResponse, otherContent: @Composable (ColumnScope.()-> Unit)) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .dropShadow(
                shape = RoundedCornerShape(20.dp),
                shadow = Shadow(
                    radius = 0.dp,
                    color = indigo,
                    offset = DpOffset(x = (-6).dp, y = 6.dp)
                )
            )
            .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = indigo)
            .clip(RoundedCornerShape(16.dp))
            .background(roseSoft)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Text(
                text = quiz.subject,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = darkRed
            )
            Text(
                text = quiz.topic,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = darkRed.copy(.8f)
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = indigo, thickness = 2.dp)
            Text(
                text = "Quiz Code: ${quiz.quizCode}",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = purple_blue
            )

            Text(
                text = "Assigned By: ${quiz.teacherName}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )

            otherContent()
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun QuizDetailsCardPrev() {
    QuizDetailsCard(
        quiz = UsersQuizDetailsResponse(subject = "Java", topic = "Data Types", "JAVDT8399", "Rahul Sahoo", 2)
    ){}
}