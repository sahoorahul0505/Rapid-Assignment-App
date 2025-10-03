package com.rkscoding.rapidassignment.ui.screen.ai_chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rkscoding.rapidassignment.ui.theme.indigo
import com.rkscoding.rapidassignment.ui.theme.purple_blue

@Composable
fun AiChatScreen(modifier: Modifier = Modifier) {
    val brush = listOf(
        indigo.copy(.5f),
        purple_blue.copy(.5f)
    )
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) { paddingValue ->
        Box(
            modifier = modifier
                .padding(paddingValue)
                .fillMaxSize()
                .background(Color.White)
        ){

        }
    }
}