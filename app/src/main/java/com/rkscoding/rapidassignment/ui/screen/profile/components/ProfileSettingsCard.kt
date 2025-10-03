package com.rkscoding.rapidassignment.ui.screen.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rkscoding.rapidassignment.R

@Composable
fun ProfileSettingsCard(
    modifier: Modifier = Modifier,
    onProfileSettingsClicked: () -> Unit = {},
    onNotificationClicked: () -> Unit = {},
    onLogOutClicked: () -> Unit = {}
) {
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
                    offset = DpOffset(
                        x = 0.dp,
                        y = (6).dp
                    ),
                    alpha = .3f
                )
            )
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Settings",
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = modifier.size(16.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = {
                                onProfileSettingsClicked.invoke()
                            }),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Profile Settings",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = modifier.size(24.dp)
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = {
                                onNotificationClicked.invoke()
                            }),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Notifications",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = modifier.size(24.dp)
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = {
                                onLogOutClicked.invoke()
                            }),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Log Out",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = modifier.size(24.dp)
                    )
                }
            }
        }
    }
}