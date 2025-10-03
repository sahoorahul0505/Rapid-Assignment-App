package com.rkscoding.rapidassignment.ui.screen.quiz.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.rkscoding.rapidassignment.R
import com.rkscoding.rapidassignment.ui.theme.indigo

@Composable
fun OptionBox(selected: Boolean, onSelectedChange: (Boolean) -> Unit) {
    val borderColor = if (selected) indigo else Color.Gray
    val shadowColor = if (selected) indigo else Color.Gray
    val iconColor = if (selected) indigo else Color.Gray
    Box(
        modifier = Modifier
            .padding(vertical = 6.dp)
            .size(24.dp)
            .border(width = 2.dp, shape = RoundedCornerShape(8.dp), color = borderColor)
            .dropShadow(
                shape = RoundedCornerShape(8.dp),
                shadow = Shadow(
                    radius = 4.dp,
                    color = shadowColor,
                    alpha = .4f,
                    offset = DpOffset(x = 0.dp, 6.dp)
                )
            )
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .clickable {
                onSelectedChange(!selected)
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.tik),
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(18.dp)
        )
    }
}

//@Preview(showBackground = true)
@Composable
private fun OptionBoxPrev() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        OptionBox(selected = true, onSelectedChange = {})
    }
}