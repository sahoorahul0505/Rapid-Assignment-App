package com.rkscoding.rapidassignment.ui.screen.quiz.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressBar(
    progress: Float,
    max: Float,
    color: Color,
    backgroundColor: Color,
    stroke: Dp,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.wrapContentSize()){
        Canvas(modifier = modifier) {
            drawArc(
                color = backgroundColor,
                startAngle =-90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round)
            )

            drawArc(
                color = color,
                startAngle =-90f,
                sweepAngle = (progress / max) * 360f,
                useCenter = false,
                style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round)
            )
        }
    }

}