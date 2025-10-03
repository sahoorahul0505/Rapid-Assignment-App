package com.rkscoding.rapidassignment.ui.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rkscoding.rapidassignment.ui.theme.indigo

@Composable
fun AuthButtonSection(
    buttonText: String = "",
    isLoading : Boolean,
    onPrimaryBtnClick: () -> Unit = {},
    onSecondaryBtnClick: () -> Unit = {}
) {
    var label = ""
    var secondaryBtnText = ""
    when (buttonText) {
        "Sign Up" -> {
            label = "Already have your account?"
            secondaryBtnText = "Login"
        }
        "Login" -> {
            label = "Don't have account?"
            secondaryBtnText = "SignUp"
        }
        "Confirm" ->{
            label = "Password remembered?"
            secondaryBtnText = "Back to login"
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow(
                shape = RoundedCornerShape(32.dp),
                shadow = Shadow(
                    radius = 12.dp,
                    alpha = .1f
                )
            )
            .clip(shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .background(
                Color.White,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthButton(
            modifier = Modifier.fillMaxWidth(),
            buttonText = buttonText,
            onButtonClick = {
                onPrimaryBtnClick.invoke()
            },
            isLoading = isLoading
        )
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.size(12.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    onSecondaryBtnClick.invoke()
                }
                .background(indigo.copy(.2f))
                .padding(horizontal = 12.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = secondaryBtnText,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = indigo
                )
            )
        }
    }
}

@Composable
fun AuthButton(modifier: Modifier = Modifier, buttonText: String, onButtonClick: () -> Unit, isLoading: Boolean) {
    Button(
        onClick = {
            onButtonClick.invoke()
        },
//        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = indigo,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier
                .fillMaxWidth()
                .height(56.dp), contentAlignment = Alignment.Center
        )
        {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    trackColor = Color.Transparent
                )
            } else {
                Text(
                    text = buttonText,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}