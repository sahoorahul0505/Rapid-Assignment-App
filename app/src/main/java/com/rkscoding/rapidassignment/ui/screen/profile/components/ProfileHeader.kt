package com.rkscoding.rapidassignment.ui.screen.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.rkscoding.rapidassignment.data.remote.dto.response.UserProfileResponse
import com.rkscoding.rapidassignment.ui.theme.rose

@Composable
fun ProfileHeader(modifier: Modifier = Modifier, profile: UserProfileResponse) {
    Box(
        modifier = modifier
            .size(120.dp)
            .clip(CircleShape)
            .border(width = 4.dp, shape = CircleShape, color = Color.White)
            .background(rose.copy(.5f))
    ) {
        AsyncImage(
            model = profile.profilePic,
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }

    Spacer(modifier.size(24.dp))
    Text(
        text = profile.name,
        style = TextStyle(
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    )
}