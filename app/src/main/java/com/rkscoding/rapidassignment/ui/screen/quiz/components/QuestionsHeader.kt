package com.rkscoding.rapidassignment.ui.screen.quiz.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rkscoding.rapidassignment.R
import com.rkscoding.rapidassignment.ui.theme.indigo

@Composable
//@Preview
fun QuestionsHeader(onRetryClick: () -> Unit = {}) {
    var currentRotationAngle by remember { mutableFloatStateOf(0f) }
    val iconRotate by animateFloatAsState(
        targetValue = currentRotationAngle,
        animationSpec = tween(durationMillis = 600, easing = LinearEasing),
        label = "RetryIconRotation"
    )
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Questions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        IconButton(
            onClick = {
                currentRotationAngle -= 360f
                onRetryClick.invoke()
            },
            modifier = Modifier
                .size(28.dp)
                .rotate(iconRotate)
        ) {
            Icon(
                painter = painterResource(R.drawable.retry),
                contentDescription = null,
                tint = indigo
            )
        }
    }
}