package com.rkscoding.rapidassignment.ui.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rkscoding.rapidassignment.ui.theme.darkTeal
import com.rkscoding.rapidassignment.ui.theme.indigo

@Composable
fun MySnackBar(modifier: Modifier = Modifier, text: String) {
    Box(
        modifier = modifier
            .padding(bottom = 56.dp, start = 24.dp, end = 24.dp)
            .height(64.dp)
            .border(width = 1.dp, color = indigo, shape = RoundedCornerShape(16.dp))
            .dropShadow(
                shape = RoundedCornerShape(16.dp),
                shadow = Shadow(
                    radius = 0.dp,
                    color = indigo,
                    offset = DpOffset(x = 0.dp, y = 4.dp)
                )
            )
            .clip(RoundedCornerShape(16.dp))
    ) {
        Snackbar(
            modifier.fillMaxSize(),
            containerColor = Color.White
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            )
        }
    }
}
