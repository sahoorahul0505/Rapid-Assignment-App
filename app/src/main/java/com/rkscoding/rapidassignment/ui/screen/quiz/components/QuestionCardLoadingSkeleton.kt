package com.rkscoding.rapidassignment.ui.screen.quiz.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun QuestionCardLoadingSkeleton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shimmer()
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth()
                .height(120.dp)
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp))
                .dropShadow(
                    shape = RoundedCornerShape(20.dp),
                    shadow = Shadow(
                        radius = 0.dp,
                        color = Color.Gray,
                        offset = DpOffset(x = 0.dp, y = 6.dp)
                    )
                )
                .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
        )
    }
}