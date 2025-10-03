package com.rkscoding.rapidassignment.ui.screen.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rkscoding.rapidassignment.R
import com.rkscoding.rapidassignment.ui.theme.indigo
import com.valentinilk.shimmer.shimmer

@Composable
fun ProfileScreenLoadingSkeleton(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(120.dp)
            .shimmer()
            .clip(CircleShape)
            .background(Color.LightGray)
    )
    Spacer(modifier = modifier.size(24.dp))
    Box(
        modifier = modifier
            .height(20.dp)
            .fillMaxWidth(.8f)
            .shimmer()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
    )
    Spacer(modifier.size(56.dp))
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .dropShadow(
                shape = RoundedCornerShape(24.dp),
                shadow = Shadow(
                    radius = 16.dp,
                    color = Color.Black.copy(alpha = .16f),
                    spread = 16.dp,
                    alpha = .3f
                )
            )
            .clip(RoundedCornerShape(24.dp))
            .clickable(
                onClick = {}
            )
            .background(Color.White)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Stats",
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.retry),
                        contentDescription = null,
                        tint = indigo,
                        modifier = modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = modifier.size(8.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = null,
                        tint = indigo,
                        modifier = modifier.size(24.dp)
                    )
                    Text(
                        text = "Points:",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Box(
                        modifier = modifier
                            .height(20.dp)
                            .width(100.dp)
                            .shimmer()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray)
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.crown),
                        contentDescription = null,
                        tint = Color(0xFFFFD333),
                        modifier = modifier.size(24.dp)
                    )
                    Text(
                        text = "Rank :",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Box(
                        modifier = modifier
                            .height(20.dp)
                            .width(100.dp)
                            .shimmer()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray)
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.task_complete),
                        contentDescription = null,
                        tint = indigo,
                        modifier = modifier.size(24.dp)
                    )
                    Text(
                        text = "Quizzes Completed :",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Box(
                        modifier = modifier
                            .height(20.dp)
                            .width(100.dp)
                            .shimmer()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray)
                    )
                }
            }
        }
    }
}