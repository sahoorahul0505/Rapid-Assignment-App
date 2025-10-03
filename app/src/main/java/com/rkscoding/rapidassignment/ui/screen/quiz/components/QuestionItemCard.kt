package com.rkscoding.rapidassignment.ui.screen.quiz.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.rkscoding.rapidassignment.data.remote.dto.response.UserQuizQuestionResponse
import com.rkscoding.rapidassignment.ui.theme.indigo
import com.rkscoding.rapidassignment.ui.theme.lightRose

@Composable
fun QuestionItemCard(
    question: UserQuizQuestionResponse,
    questionNumber: Int,
    selected : Boolean,
    onSelectedOptionClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .border(width = 1.dp, color = indigo, shape = RoundedCornerShape(16.dp))
            .dropShadow(
                shape = RoundedCornerShape(20.dp),
                shadow = Shadow(
                    radius = 0.dp,
                    color = indigo,
                    offset = DpOffset(x = 0.dp, y = 6.dp)
                )
            )
            .clip(RoundedCornerShape(16.dp))
            .background(lightRose)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 8.dp, horizontal = 18.dp)
        ) {
            Row {
                Text(
                    text = "Q$questionNumber: ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(text = question.questionText, fontSize = 18.sp, color = Color.Black)
            }
            Column(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                question.options.forEachIndexed { index, opts ->

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OptionBox(
                            selected = selected,
                            onSelectedChange = {}
                        )
                        Text(
                            text = opts,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun QuestionItemCardPrev() {
    val question = UserQuizQuestionResponse(
        questionId = "vcuhgckqbqchbqohgb",
        questionText = "jygfouiqygof8ygqef",
        options = listOf("gwucg", "gafgyq", "yficutfqi", "ugfdgoq8ygf")
    )
    QuestionItemCard(
        question, selected = false, questionNumber = 1
    )
}